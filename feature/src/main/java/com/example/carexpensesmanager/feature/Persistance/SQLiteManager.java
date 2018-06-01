package com.example.carexpensesmanager.feature.Persistance;

import android.content.Context;

import com.example.carexpensesmanager.feature.DBEntity.User;
import com.example.carexpensesmanager.feature.DatabaseHelper;

import java.util.Collection;

public class SQLiteManager implements DataStorage {

    DatabaseHelper helper;
    Context context;

    public SQLiteManager (Context context){
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
        if (helper.deleteUser(id)>0)
            return true;
        return false;
    }


}
