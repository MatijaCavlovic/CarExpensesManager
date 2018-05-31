package com.example.carexpensesmanager.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carexpensesmanager.feature.DBEntity.User;
import com.example.carexpensesmanager.feature.Persistance.DataStorageSingleton;

public class AddUser extends AppCompatActivity {

    Button addBtn;
    Button cancelBtn;
    EditText name;
    EditText surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        addBtn = findViewById(R.id.addUserBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString=name.getText().toString();
                String surnameString = surname.getText().toString();

                User user = new User();

                user.setId(0);
                user.setName(nameString);
                user.setSurname(surnameString);

                if (DataStorageSingleton.dataStorage.addUser(user)){
                    Toast.makeText(getApplicationContext(), "Korisnik "+nameString+" "+surnameString+" dodan",Toast.LENGTH_LONG).show();

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
