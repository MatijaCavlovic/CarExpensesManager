package com.example.carexpensesmanager.feature.DBEntity;

public  class Expense {
    protected int id;
    protected int carId;

    public Expense(){
       this(0,0);
    }

    public Expense(int id,int carId){
        this.id = id;
        this.carId = carId;
    }

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
