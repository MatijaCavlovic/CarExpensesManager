package com.example.carexpensesmanager.feature;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

    Collection<Car> userCars;
    ArrayList<Car> carList;
    CarsAdapter adapter;

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

        this.adapterInit();

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
                AlertDialog.Builder alert = new AlertDialog.Builder(UserDetails.this);
                alert.setTitle("Brisanje");
                alert.setTitle("Želite li izbisati korisnika?");
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



    }

    private void delete(){
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

    @Override
    protected void onResume() {
        super.onResume();
        this.adapterInit();
    }

    private void adapterInit(){
        userCars = DataStorageSingleton.dataStorage.getAllUserCars(userId);
        carList = new ArrayList<>(userCars);
        adapter = new CarsAdapter(this,carList,this);
        listView.setAdapter(adapter);
    }
}
