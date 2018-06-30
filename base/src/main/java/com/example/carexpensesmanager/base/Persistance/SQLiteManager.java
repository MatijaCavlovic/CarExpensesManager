package com.example.carexpensesmanager.base.Persistance;

import android.content.Context;

import com.example.carexpensesmanager.base.DBEntity.Car;
import com.example.carexpensesmanager.base.DBEntity.Expense;
import com.example.carexpensesmanager.base.DBEntity.FuelExpense;
import com.example.carexpensesmanager.base.DBEntity.InsuranceExpense;
import com.example.carexpensesmanager.base.DBEntity.RegistrationExpense;
import com.example.carexpensesmanager.base.DBEntity.ServiceExpense;
import com.example.carexpensesmanager.base.DBEntity.ServiceExpenseElement;
import com.example.carexpensesmanager.base.DBEntity.User;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class SQLiteManager implements DataStorage {

    private DatabaseHelper helper;
    private Context context;

    public SQLiteManager(Context context) {
        helper = new DatabaseHelper(context);
        this.context = context;
    }

    @Override
    public boolean addUser(User user) {
        boolean success;
        success = helper.insertUser(user);
        return success;
    }

    @Override
    public Collection<User> getAllUsers() {
        return helper.getAllUsers();
    }

    @Override
    public User getUser(int id) {
        return helper.getUser(id);
    }

    @Override
    public boolean deleteUser(int id) {
        Collection<Car> cars = helper.getAllUserCars(id);

        for (Car c:cars){
            if (!this.deleteCar(c.getId()))
                return false;
        }

        if (helper.deleteUser(id) > 0)
            return true;
        return false;
    }

    @Override
    public File getDatabaseFile(Context context) {
        return context.getDatabasePath(helper.getDatabaseName());
    }

    @Override
    public boolean addCar(Car car) {
        return helper.insertCar(car);
    }

    @Override
    public Collection<Car> getAllUserCars(int userId) {
        return helper.getAllUserCars(userId);
    }

    @Override
    public Car getCar(int id) {
        return helper.getCar(id);
    }

    @Override
    public boolean deleteCar(int id) {

        Collection<Expense> expenses = helper.getAllCarsExpenses(id);

        for (Expense e:expenses){
            if (e.getType().equals("Gorivo"))
                this.deleteFuelExpense(e.getId());
            else if (e.getType().equals("Osiguranje"))
                this.deleteInsuranceExpense(e.getId());
            else if (e.getType().equals("Registracija"))
                this.deleteRegistrationExpense(e.getId());
            else if (e.getType().equals("Servis"))
                this.deleteServiceExpense(e.getId());
            else
                return false;
        }

        if (helper.deleteCar(id) > 0)
            return true;
        return false;
    }

    @Override
    public boolean addFuelExpense(FuelExpense expense) {
        int id = helper.insertExpense(expense,"Gorivo");
        if (id==-1)
            return false;
        expense.setId(id);

        id = helper.insertFuelExpense(expense);
        if (id==-1)
            return false;
        return true;
    }

    @Override
    public boolean addInsuranceExpense(InsuranceExpense expense) {
        int id = helper.insertExpense(expense,"Osiguranje");
        if (id==-1)
            return false;
        expense.setId(id);

        id = helper.insertInsuranceExpense(expense);
        if (id==-1)
            return false;
        return true;
    }

    @Override
    public boolean addServiceExpense(ServiceExpense expense,List<ServiceExpenseElement> elements) {
        int id = helper.insertExpense(expense,"Servis");
        if (id==-1)
            return false;
        expense.setId(id);

        id = helper.insertServiceExpense(expense);
        if (id==-1)
            return false;

        int serviceId = id;
        for (int i=0;i<elements.size();i++) {
            ServiceExpenseElement element = elements.get(i);
            element.setServiceExpenseId(serviceId);
            id = helper.insertServiceExpenseElement(element);
            if (id==-1){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addRegistrationExpense(RegistrationExpense expense) {
        int id = helper.insertExpense(expense,"Registracija");
        if (id==-1)
            return false;
        expense.setId(id);

        id = helper.insertRegistrationExpense(expense);
        if (id==-1)
            return false;


        return true;
    }

    @Override
    public Collection<Expense> getAllCarExpenses(int carId) {
        return helper.getAllCarsExpenses(carId);
    }

    @Override
    public FuelExpense getFuelExpense(int expenseId) {
        return helper.getFuelExpense(expenseId);
    }

    @Override
    public InsuranceExpense getInsuranceExpense(int expenseId) {
        return helper.getInsuranceExpense(expenseId);
    }

    @Override
    public RegistrationExpense getRegistrationExpense(int expenseId) {
        return helper.getRegistrationExpense(expenseId);
    }

    @Override
    public ServiceExpense getServiceExpense(int expenseId) {
        return helper.getServiceExpense(expenseId);
    }

    private boolean deleteExpense(int expenseId){
        if (helper.deleteExpense(expenseId) > 0)
            return true;
        return false;
    }

    @Override
    public boolean deleteFuelExpense(int expenseId) {
        if (helper.deleteFuelExpense(expenseId)<=0){
            return false;
        }

        return this.deleteExpense(expenseId);
    }

    @Override
    public boolean deleteInsuranceExpense(int expenseId) {
        if (helper.deleteInsuranceExpense(expenseId)<=0){
            return false;
        }

        return this.deleteExpense(expenseId);
    }

    @Override
    public boolean deleteRegistrationExpense(int expenseId) {
        if (helper.deleteRegistrationExpense(expenseId)<=0){
            return false;
        }

        return this.deleteExpense(expenseId);
    }

    @Override
    public boolean deleteServiceExpense(int expenseId) {

        Collection<ServiceExpenseElement> elements = helper.getAllServiceExpenseElements(expenseId);
        for (ServiceExpenseElement element:elements){
            if (helper.deleteExpenseElement(element.getId())<=0){
                return false;
            }
        }

        if (helper.deleteServiceExpense(expenseId)<=0){
            return false;
        }

        return this.deleteExpense(expenseId);
    }

    @Override
    public double getExpenseSum(int carId) {
        return helper.getExpenseSum(carId);
    }

    @Override
    public Collection<ServiceExpenseElement> getAllServiceExpenseElements(int serviceId) {
        return helper.getAllServiceExpenseElements(serviceId);
    }

    @Override
    public boolean deleteExpenseElement(int id) {
        return false;
    }


}
