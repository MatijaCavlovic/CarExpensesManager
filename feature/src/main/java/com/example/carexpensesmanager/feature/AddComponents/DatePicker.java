package com.example.carexpensesmanager.feature.AddComponents;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DatePicker implements DatePickerDialog.OnDateSetListener {

    public DatePickerDialog datePickerDialog;
    private Context context;
    private EditText dateEditText;
    private int year,month,day;
    public DatePicker(Context context, EditText dateEditText){
        this.context = context;
        this.dateEditText = dateEditText;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateString = dateEditText.getText().toString();
        final Calendar calendar = Calendar.getInstance();
        try {
            Date date = simpleDateFormat.parse(dateString);
            calendar.setTime(date);
        } catch (ParseException e) {
           //ne postavlja se datum, vec se uzima danasnji dan
            Log.d("date","Danasnji datum postavljen");
        }

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog;
        dialog = new DatePickerDialog(context,this,year,month,day);
        dialog.show();
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        Date date = calendar.getTime();
        String dateString = simpleDateFormat.format(date);
        dateEditText.setText(dateString);
    }
}
