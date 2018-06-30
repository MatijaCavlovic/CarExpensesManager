package com.example.carexpensesmanager.base.Persistance;

/**
 * @startuml
 *
 */

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


public interface DataStorage {

    public boolean addUser(User user);
    public Collection<User> getAllUsers();

    public User getUser(int id);

    public boolean deleteUser(int id);

    public File getDatabaseFile(Context context);

    public boolean addCar(Car car);

    public Collection<Car> getAllUserCars(int userId);

    public Car getCar(int id);

    public boolean deleteCar(int id);

    public boolean addFuelExpense(FuelExpense expense);

    public boolean addInsuranceExpense(InsuranceExpense expense);

    public boolean addServiceExpense(ServiceExpense expense, List<ServiceExpenseElement> elements);

    public boolean addRegistrationExpense(RegistrationExpense expense);

    public Collection<Expense> getAllCarExpenses(int carId);

    public FuelExpense getFuelExpense(int expenseId);

    public InsuranceExpense getInsuranceExpense(int expenseId);

    public RegistrationExpense getRegistrationExpense(int expenseId);

    public ServiceExpense getServiceExpense(int expenseId);

    public boolean deleteFuelExpense(int expenseId);

    public boolean deleteInsuranceExpense(int expenseId);

    public boolean deleteRegistrationExpense(int expenseId);

    public boolean deleteServiceExpense(int expenseId);

    public double getExpenseSum(int carId);

    public Collection<ServiceExpenseElement> getAllServiceExpenseElements(int serviceId);

    public boolean deleteExpenseElement(int id);




}
