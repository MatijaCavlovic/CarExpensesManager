package com.example.carexpensesmanager.feature.Persistance;

import android.content.Context;

import com.example.carexpensesmanager.feature.DBEntity.Car;
import com.example.carexpensesmanager.feature.DBEntity.User;

import java.io.File;
import java.util.Collection;

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
}
