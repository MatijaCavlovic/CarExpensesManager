package com.example.carexpensesmanager.feature.Persistance;

import com.example.carexpensesmanager.feature.DBEntity.User;

import java.util.Collection;

public interface DataStorage {

    public boolean addUser(User user);
    public Collection<User> getAllUsers();

    public User getUser(int id);
}
