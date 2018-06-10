package com.example.carexpensesmanager.feature.DBEntity;

public abstract class Expense {

    protected int id;
    protected int carId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }
}
