package com.example.carexpensesmanager.feature;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public  class Utils {

    public static Collection<String> getExpenseTypesList(Context context){
        String[] expenseTypeArray = context.getResources().getStringArray(R.array.expenseTypes);
        Collection<String> list = new ArrayList<String>(Arrays.asList(expenseTypeArray));

        return list;
    }
}
