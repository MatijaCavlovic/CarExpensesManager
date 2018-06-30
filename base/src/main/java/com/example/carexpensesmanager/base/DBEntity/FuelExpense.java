package com.example.carexpensesmanager.base.DBEntity;

import java.util.Date;

public class FuelExpense extends Expense {

    private String place;

    public FuelExpense(){
        this(0,0,null,0,null);
    }

    public  FuelExpense(int id,int car_id,Date date,double price, String place){
        super(id,car_id,date,price,"Gorivo");
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
