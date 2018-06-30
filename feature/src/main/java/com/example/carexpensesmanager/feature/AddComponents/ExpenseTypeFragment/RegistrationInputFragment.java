package com.example.carexpensesmanager.feature.AddComponents.ExpenseTypeFragment;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carexpensesmanager.base.DBEntity.RegistrationExpense;
import com.example.carexpensesmanager.feature.AddComponents.DatePicker;
import com.example.carexpensesmanager.base.Persistance.DataStorageSingleton;
import com.example.carexpensesmanager.feature.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrationInputFragment extends Fragment implements SaveInterface {

    private Button dateSelectBtn;
    private EditText dateEditText;
    private EditText priceEditText;

    private int day,month,year;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration_input,container,false);

        dateSelectBtn = view.findViewById(R.id.selectDateBtn);
        dateEditText = view.findViewById(R.id.registrationDate);
        priceEditText = view.findViewById(R.id.priceEditView);
        dateSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DatePicker datePicker = new DatePicker(getContext(),dateEditText);
            }
        });
        return view;
    }

    @Override
    public void save(int carId) {
        RegistrationExpense registrationExpense = new RegistrationExpense();
        double price;
        String dateString;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date;

        try {
            price = Double.parseDouble(priceEditText.getText().toString());
        }
        catch (Exception e){
            Toast.makeText(getContext(),"Cijena nije unešena ili nije ispravna",Toast.LENGTH_LONG).show();
            return;
        }

        dateString = dateEditText.getText().toString();
        if (dateString==null || dateString.isEmpty()){
            Toast.makeText(getContext(),"Datum nije unešen",Toast.LENGTH_LONG).show();
            return;
        }

        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            Toast.makeText(getContext(),"Datum nije odabran ili je u pogrešnom formatu",Toast.LENGTH_LONG).show();
            return;
        }

        registrationExpense.setCarId(carId);
        registrationExpense.setPrice(price);
        registrationExpense.setDate(date);

        boolean success = DataStorageSingleton.dataStorage.addRegistrationExpense(registrationExpense);
        if (success){
            Toast.makeText(getContext(),"Trošak uspješno unesen",Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(getContext(), "Došlo je do greške. Molimo pokušajte ponovno",Toast.LENGTH_LONG).show();
        }
    }
}
