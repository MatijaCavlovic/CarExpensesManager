package com.example.carexpensesmanager.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carexpensesmanager.feature.DBEntity.User;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper helper = new DatabaseHelper(this);
     Button btn;
     EditText name;
     EditText surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.button);

        name=findViewById(R.id.ime);
        surname=findViewById(R.id.prezime);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString=name.getText().toString();
                String surnameString = surname.getText().toString();

                User user = new User();

                user.setId(0);
                user.setName(nameString);
                user.setSurname(surnameString);
                helper.insertUser(user);
            }
        });
    }
}
