package com.example.carexpensesmanager.feature.DBEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class Expense {
    protected int id;
    protected int carId;
    protected double price;
    protected  String type;

    protected Date date;

    public Expense(){
       this(0,0,null,0,null);
    }

    public Expense(int id,int carId,Date date,double price,String type){
        this.id = id;
        this.carId = carId;
        this.date = date;
        this.price = price;
        this.type = type;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate (String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            this.date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public String getDateString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return simpleDateFormat.format(this.date);
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

}
