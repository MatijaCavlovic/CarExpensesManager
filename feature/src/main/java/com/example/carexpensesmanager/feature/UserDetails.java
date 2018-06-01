package com.example.carexpensesmanager.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carexpensesmanager.feature.DBEntity.User;
import com.example.carexpensesmanager.feature.Persistance.DataStorageSingleton;

public class UserDetails extends AppCompatActivity {

    TextView name;
    TextView surname;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            userId = extras.getInt("user");
        }
        else{
            Toast.makeText(getApplicationContext(),"Korisnik nije pravilno odabran",Toast.LENGTH_LONG).show();
            return;
        }

        User user = DataStorageSingleton.dataStorage.getUser(userId);

        if (user==null){
            Toast.makeText(getApplicationContext(),"Ne mogu pristupiti korisniku",Toast.LENGTH_LONG);
            return;
        }
        name.setText(user.getName());
        surname.setText(user.getSurname());

    }
}
