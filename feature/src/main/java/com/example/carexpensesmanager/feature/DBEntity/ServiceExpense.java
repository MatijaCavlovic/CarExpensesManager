package com.example.carexpensesmanager.feature.DBEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceExpense extends Expense {

    public ServiceExpense(){
        this(0,0,null,0);
    }

    public  ServiceExpense(int id,int car_id,Date date,double price){
        super(id,car_id,date,price,"Servis");

    }

}
