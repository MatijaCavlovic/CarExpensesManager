package com.example.carexpensesmanager.feature.Persistance;

/**
 * @startuml
 *
 */

import android.content.Context;

import com.example.carexpensesmanager.feature.DBEntity.Car;
import com.example.carexpensesmanager.feature.DBEntity.Expense;
import com.example.carexpensesmanager.feature.DBEntity.FuelExpense;
import com.example.carexpensesmanager.feature.DBEntity.InsuranceExpense;
import com.example.carexpensesmanager.feature.DBEntity.RegistrationExpense;
import com.example.carexpensesmanager.feature.DBEntity.ServiceExpense;
import com.example.carexpensesmanager.feature.DBEntity.User;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


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

    public boolean addServiceExpense(ServiceExpense expense);

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

}
