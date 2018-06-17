package com.example.carexpensesmanager.feature.DBEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceExpense extends Expense {

    private String description;

    public ServiceExpense(){
        this(0,0,null,0,null);
    }

    public  ServiceExpense(int id,int car_id,Date date,double price, String description){
        super(id,car_id,date,price,"Servis");
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
