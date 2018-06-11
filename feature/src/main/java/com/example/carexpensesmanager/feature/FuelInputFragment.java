package com.example.carexpensesmanager.feature;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carexpensesmanager.feature.DBEntity.Expense;
import com.example.carexpensesmanager.feature.DBEntity.FuelExpense;
import com.example.carexpensesmanager.feature.Persistance.DataStorageSingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FuelInputFragment extends Fragment implements SaveInterface {
    private Button dateSelectBtn;
    private EditText dateEditText;
    private EditText priceEditText;
    private EditText placeEditText;

    private int day,month,year;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_input,container,false);

        dateSelectBtn = view.findViewById(R.id.selectDateBtn);
        dateEditText = view.findViewById(R.id.fuelPurchaseDate);

        priceEditText = view.findViewById(R.id.priceEditView);
        placeEditText = view.findViewById(R.id.placeEditView);

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
        FuelExpense fuelExpense = new FuelExpense();
        double price;
        String place;
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

        place = placeEditText.getText().toString();

        if (place==null || place.isEmpty()){
            Toast.makeText(getContext(),"Mjeto nije unešeno",Toast.LENGTH_LONG).show();
            return;
        }

        dateString = dateEditText.getText().toString();

        if (dateString==null || dateString.isEmpty()){
            Toast.makeText(getContext(),"Mjeto nije unešeno",Toast.LENGTH_LONG).show();
            return;
        }

        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            Toast.makeText(getContext(),"Datum nije odabran ili je u pogrešnom formatu",Toast.LENGTH_LONG).show();
            return;
        }

        fuelExpense.setCarId(carId);
        fuelExpense.setPrice(price);
        fuelExpense.setPlace(place);
        fuelExpense.setDate(date);

        boolean success = DataStorageSingleton.dataStorage.addFuelExpense(fuelExpense);
        if (success){
            Toast.makeText(getContext(),"Trošak uspješno unesen",Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(getContext(), "Došlo je do greške. Molimo pokušajte ponovno",Toast.LENGTH_LONG).show();
        }

    }
}
