package com.example.carexpensesmanager.feature.AddComponents;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carexpensesmanager.feature.AddComponents.ExpenseTypeFragment.FuelInputFragment;
import com.example.carexpensesmanager.feature.AddComponents.ExpenseTypeFragment.InsuranceInputFragment;
import com.example.carexpensesmanager.feature.R;
import com.example.carexpensesmanager.feature.AddComponents.ExpenseTypeFragment.RegistrationInputFragment;
import com.example.carexpensesmanager.feature.AddComponents.ExpenseTypeFragment.SaveInterface;
import com.example.carexpensesmanager.feature.AddComponents.ExpenseTypeFragment.ServiceInputFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddExpense extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner expenseTypeSpinner;
    Button cancelBtn;
    Button saveBtn;

    ArrayAdapter<CharSequence> adapter;
    List<CharSequence> expenseTypesList;
    TextView tv;
    Fragment fragment=null;
    SaveInterface saveHelper=null;
    int carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        tv = findViewById(R.id.textView2);
        expenseTypeSpinner = findViewById(R.id.expenseTypeSpinner);
        cancelBtn = findViewById(R.id.cancelBtn);
        saveBtn = findViewById(R.id.addExpenseBtn);

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

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            carId = extras.getInt("car");
        }
        else{
            Toast.makeText(getApplicationContext(),"Automobil nije pravilno odabran",Toast.LENGTH_LONG).show();
            return;
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHelper.save(carId);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CharSequence cs = (CharSequence) parent.getItemAtPosition(position);
        String string = new String(String.valueOf(cs));

       if (string.equals("Osiguranje")){
           InsuranceInputFragment iiFragment = new InsuranceInputFragment();
           fragment = iiFragment;
           saveHelper = iiFragment;
       }
       else if (string.equals("Gorivo")){
           FuelInputFragment fuelInputFragment = new FuelInputFragment();
           fragment = fuelInputFragment;
           saveHelper = fuelInputFragment;
       }
       else if(string.equals("Registracija")) {
           RegistrationInputFragment registrationInputFragment = new RegistrationInputFragment();
           fragment = registrationInputFragment;
           saveHelper = registrationInputFragment;
       }
       else{
           ServiceInputFragment serviceInputFragment = new ServiceInputFragment();
           fragment = serviceInputFragment;
           saveHelper = serviceInputFragment;
       }

        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
