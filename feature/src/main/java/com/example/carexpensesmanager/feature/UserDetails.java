package com.example.carexpensesmanager.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carexpensesmanager.feature.DBEntity.Car;
import com.example.carexpensesmanager.feature.DBEntity.User;
import com.example.carexpensesmanager.feature.Persistance.DataStorageSingleton;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetails extends AppCompatActivity {

    TextView name;
    TextView surname;
    Button addCarBtn;
    Button deleteUserBtn;
    ListView listView;
    User user;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        addCarBtn = findViewById(R.id.addCarBtn);
        deleteUserBtn = findViewById(R.id.deleteUserBtn);
        listView = findViewById(R.id.carList);

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
            Toast.makeText(getApplicationContext(),"Ne mogu pristupiti korisniku",Toast.LENGTH_LONG).show();
            return;
        }
        name.setText(user.getName());
        surname.setText(user.getSurname());

        Collection<Car> userCars = DataStorageSingleton.dataStorage.getAllUserCars(userId);
        ArrayList<Car> carList = new ArrayList<>(userCars);
        CarsAdapter adapter = new CarsAdapter(this,carList,this);
        listView.setAdapter(adapter);

        addCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddCar.class);
                intent.putExtra("user",user.getId());
                startActivity(intent);
            }
        });

        deleteUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataStorageSingleton.dataStorage.deleteUser(user.getId())){
                    Toast.makeText(getApplicationContext()
                            ,String.format("Korisnik %s %s izbrisan",user.getName(),user.getSurname())
                            ,Toast.LENGTH_LONG).show();
                    onBackPressed();

                }
                else{
                    Toast.makeText(getApplicationContext(),"Došlo je do greške. Molimo pokušajte ponovno.",Toast.LENGTH_LONG).show();
                }
            }
        });



    }

 /*   @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),UserList.class);
        getApplicationContext().startActivity(intent);
    }*/
}
