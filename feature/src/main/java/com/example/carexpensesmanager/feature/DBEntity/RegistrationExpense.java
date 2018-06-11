package com.example.carexpensesmanager.feature.DBEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrationExpense extends Expense {


    public RegistrationExpense(){
        this(0,0,null,0);
    }

    public RegistrationExpense(int id,int car_id,Date date,double price){
        super(id,car_id,date,price,"Registracija");

    }

}
