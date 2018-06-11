package com.example.carexpensesmanager.feature;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carexpensesmanager.feature.DBEntity.Car;
import com.example.carexpensesmanager.feature.Persistance.DataStorageSingleton;

public class CarDetails extends AppCompatActivity {

    TextView carNameTv;
    Button deleteCarBtn;
    Button addExpenseBtn;
    int carId;
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        carNameTv = findViewById(R.id.carName);
        deleteCarBtn = findViewById(R.id.deleteCarBtn);
        addExpenseBtn = findViewById(R.id.addExpenseBtn);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            carId = extras.getInt("car");
        }
        else{
            Toast.makeText(getApplicationContext(),"Automobil nije pravilno odabran",Toast.LENGTH_LONG).show();
            return;
        }

        car = DataStorageSingleton.dataStorage.getCar(carId);
        if (car==null){
            Toast.makeText(getApplicationContext(),"Došlo je do pogreške u pristupu podatcima",Toast.LENGTH_LONG).show();
            return;
        }
        carNameTv.setText(car.getName());

        deleteCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CarDetails.this);
                alert.setTitle("Brisanje");
                alert.setTitle("Želite li izbisati automobil?");
                alert.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("NE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });

        addExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarDetails.this,AddExpense.class);
                intent.putExtra("car",carId);
                startActivity(intent);
            }
        });

    }

    private void delete(){
        if (DataStorageSingleton.dataStorage.deleteCar(car.getId())){
            Toast.makeText(getApplicationContext()
                    ,String.format("Automobil %s izbrisan",car.getName())
                    ,Toast.LENGTH_LONG).show();
            onBackPressed();

        }
        else{
            Toast.makeText(getApplicationContext(),"Došlo je do greške. Molimo pokušajte ponovno.",Toast.LENGTH_LONG).show();
        }
    }
}
