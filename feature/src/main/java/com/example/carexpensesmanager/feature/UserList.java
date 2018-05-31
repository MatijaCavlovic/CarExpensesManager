package com.example.carexpensesmanager.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carexpensesmanager.feature.DBEntity.User;

import java.util.Collection;

public class UserList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        LinearLayout list = findViewById(R.id.list);

        DatabaseHelper helper = new DatabaseHelper(this);

        Collection<User> users = helper.getAllUsers();


        if (users.isEmpty()){
            TextView message = new TextView(this);
            Toast toast = Toast.makeText(this,"Lista korisnika je prazna",Toast.LENGTH_LONG);
            toast.show();

        }
        for (User user:users){
            String name = user.getName();
            String surname = user.getSurname();

            Button btn = new Button(this);
            btn.setText(name+" "+surname);
            list.addView(btn);
        }
    }
}
