package com.example.carexpensesmanager.feature.DBEntity;

import java.util.Date;

public class FuelExpense extends Expense {

    private Date date;
    private double price;
    private String place;

    public FuelExpense(){
        this(0,0,null,0,null);
    }

    public  FuelExpense(int id,int car_id,Date date,double price, String place){
        this.id = id;
        this.carId = car_id;
        this.date = date;
        this.price = price;
        this.place = place;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
