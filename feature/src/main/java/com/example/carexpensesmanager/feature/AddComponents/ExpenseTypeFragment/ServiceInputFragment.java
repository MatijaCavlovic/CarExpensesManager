package com.example.carexpensesmanager.feature.AddComponents.ExpenseTypeFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.Toast;

import com.example.carexpensesmanager.feature.DBEntity.ServiceExpense;
import com.example.carexpensesmanager.feature.AddComponents.DatePicker;
import com.example.carexpensesmanager.feature.Persistance.DataStorageSingleton;
import com.example.carexpensesmanager.feature.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

public class ServiceInputFragment extends Fragment implements SaveInterface {

    private Button dateSelectBtn;
    private EditText dateEditText;
    private EditText priceEditText;
    private EditText descriptionEditText;
    private Button addSpecificationBtn;
    private LinearLayout specificationLayout;
    View view;
    ViewGroup container;
    List<View> expenseElements;

    private int day,month,year;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_service_input,container,false);
        this.container = container;
        dateSelectBtn = view.findViewById(R.id.selectDateBtn);
        dateEditText = view.findViewById(R.id.serviceDate);
        priceEditText = view.findViewById(R.id.priceEditView);
        descriptionEditText = view.findViewById(R.id.descriptionEditView);
        addSpecificationBtn = view.findViewById(R.id.addServiceSpecificationBtn);
        specificationLayout = view.findViewById(R.id.specificationsLayout);
        expenseElements = new ArrayList<>();

        dateSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker(getContext(),dateEditText);
            }
        });

        addSpecificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View expenseElement = LayoutInflater.from(getContext()).inflate(R.layout.item_specification, container, false);
               /* EditText spec = new EditText(getContext());
                spec.setText("Nova");*/
                specificationLayout.addView(expenseElement);
                expenseElements.add(expenseElement);
                final ImageButton removeBtn = expenseElement.findViewById(R.id.removeExpenseElementBtn);
                removeBtn.setTag(expenseElement);
                removeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View elementToRemove = (View) removeBtn.getTag();
                        expenseElements.remove(elementToRemove);
                        specificationLayout.removeView(elementToRemove);
                        setPrice();
                    }
                });

                EditText elementPrice = expenseElement.findViewById(R.id.expenseElementPriceEditText);
                elementPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        setPrice();
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void save(int carId) {
        ServiceExpense serviceExpense = new ServiceExpense();
        double price;
        String dateString;
        String descriptionString;
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

        descriptionString = descriptionEditText.getText().toString();

        if (descriptionString==null || descriptionString.isEmpty()){
            Toast.makeText(getContext(),"Opis servisa nije dodan.",Toast.LENGTH_LONG).show();
            return;
        }

        serviceExpense.setCarId(carId);
        serviceExpense.setPrice(price);
        serviceExpense.setDate(date);
        serviceExpense.setDescription(descriptionString);

        boolean success = DataStorageSingleton.dataStorage.addServiceExpense(serviceExpense);
        if (success){
            Toast.makeText(getContext(),"Trošak uspješno unesen",Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(getContext(), "Došlo je do greške. Molimo pokušajte ponovno",Toast.LENGTH_LONG).show();
        }
    }

    private void setPrice(){
        double price = expenseElements.stream().mapToDouble(new ToDoubleFunction<View>() {
            @Override
            public double applyAsDouble(View value) {
                EditText et = value.findViewById(R.id.expenseElementPriceEditText);
                if (et.getText().toString()==null || et.getText().toString().isEmpty())
                    return 0;
                Log.d("Stavka",et.getText().toString());
                return Double.parseDouble(et.getText().toString());
            }
        } ).sum();

        priceEditText.setText(price + "");
    }
}
