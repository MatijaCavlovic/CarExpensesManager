package com.example.carexpensesmanager.feature;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddExpense extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner expenseTypeSpinner;

    ArrayAdapter<CharSequence> adapter;
    List<CharSequence> expenseTypesList;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        tv = findViewById(R.id.textView2);
        expenseTypeSpinner = findViewById(R.id.expenseTypeSpinner);
        expenseTypeSpinner.setOnItemSelectedListener(this);

        String[] expenseTypeArray = getResources().getStringArray(R.array.expenseTypes);
        Arrays.sort(expenseTypeArray);
        expenseTypesList = new ArrayList<>();

        for (String s:expenseTypeArray){
            expenseTypesList.add(s);
        }

        adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,expenseTypesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseTypeSpinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CharSequence cs = (CharSequence) parent.getItemAtPosition(position);
        String string = new String(String.valueOf(cs));
        Fragment fragment;
       if (string.equals("Osiguranje")){
           fragment = new InsuranceInputFragment();
       }
       else if (string.equals("Gorivo")){
           fragment = new FuelInputFragment();
       }
       else if(string.equals("Registracija")) {
           fragment = new RegistrationInputFragment();
       }
       else{
           fragment = new ServiceInputFragment();
       }

        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
