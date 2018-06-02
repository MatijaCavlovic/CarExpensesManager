package com.example.carexpensesmanager.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carexpensesmanager.feature.DBEntity.Car;
import com.example.carexpensesmanager.feature.DBEntity.User;
import com.example.carexpensesmanager.feature.Persistance.DataStorageSingleton;

import org.w3c.dom.Text;

public class AddCar extends AppCompatActivity {


    TextView userNameSurname;
    EditText carName;
    Button addCarBtn;
    Button cancelBtn;
    User user;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        userNameSurname = findViewById(R.id.userTv);
        carName = findViewById(R.id.carName);

        addCarBtn = findViewById(R.id.addCarBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            userId = extras.getInt("user");
        }
        else{
            Toast.makeText(getApplicationContext(),"Korisnik nije pravilno odabran",Toast.LENGTH_LONG).show();
            return;
        }

        user = DataStorageSingleton.dataStorage.getUser(userId);
        if (user==null){
            Toast.makeText(getApplicationContext(),"Došlo je do greške u pristupu podatcima",Toast.LENGTH_LONG).show();
            return;
        }
        userNameSurname.setText(user.getName()+" "+user.getSurname());

        addCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String carNameString = carName.getText().toString();

                Car car = new Car();

                car.setId(0);
                car.setName(carNameString);
                car.setOwnerId(userId);

                if (DataStorageSingleton.dataStorage.addCar(car)){
                    Toast.makeText(getApplicationContext(), "Automobil "+carNameString + "dodan",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(getApplicationContext(), "Došlo je do greške. Molimo pokušajte ponovno",Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
