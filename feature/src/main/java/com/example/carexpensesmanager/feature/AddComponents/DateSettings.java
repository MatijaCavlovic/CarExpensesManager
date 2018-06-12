package com.example.carexpensesmanager.feature.AddComponents;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

public class DateSettings implements DatePickerDialog.OnDateSetListener {
    Context context;

    public DateSettings(Context context){
        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
