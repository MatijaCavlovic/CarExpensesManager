package com.example.carexpensesmanager.feature.DBEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrationExpense extends Expense {

    private Date date;
    private double price;

    public RegistrationExpense(){
        this(0,0,null,0);
    }

    public RegistrationExpense(int id,int car_id,Date date,double price){
        this.id = id;
        this.carId = car_id;
        this.date = date;
        this.price = price;

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


    public String getDateString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return simpleDateFormat.format(this.date);
    }
}
