package com.example.carexpensesmanager.feature;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FuelInputFragment extends Fragment {
    private Button dateSelectBtn;
    private EditText dateEditText;

    private int day,month,year;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_input,container,false);

        dateSelectBtn = view.findViewById(R.id.selectDateBtn);
        dateEditText = view.findViewById(R.id.fuelPurchaseDate);

        dateSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker(getContext(),dateEditText);
            }
        });
        return view;
    }
}
