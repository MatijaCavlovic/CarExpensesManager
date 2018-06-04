package com.example.carexpensesmanager.feature;

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
    int carId;
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        carNameTv = findViewById(R.id.carName);
        deleteCarBtn = findViewById(R.id.deleteCarBtn);


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
        });
    }
}
