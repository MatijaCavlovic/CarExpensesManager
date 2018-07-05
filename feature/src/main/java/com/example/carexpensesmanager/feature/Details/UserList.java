package com.example.carexpensesmanager.feature.Details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carexpensesmanager.base.DBEntity.User;
import com.example.carexpensesmanager.base.Persistance.SQLiteManager;
import com.example.carexpensesmanager.feature.Details.DataAdapter.UsersAdapter;
import com.example.carexpensesmanager.base.Persistance.DataStorage;
import com.example.carexpensesmanager.base.Persistance.DataStorageSingleton;
import com.example.carexpensesmanager.base.Persistance.DatabaseHelper;
import com.example.carexpensesmanager.feature.R;

import java.util.ArrayList;
import java.util.Collection;

public class UserList extends AppCompatActivity {

    DataStorage dataStorage;
    ListView listView;
    //public int choosenUserId;
    ArrayList<User> userList;
    UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        SQLiteManager.DatabaseInit(getApplicationContext());

        this.getIntent().putExtra("choosenUser",-1);

        listView = findViewById(R.id.listView);

        DatabaseHelper helper = new DatabaseHelper(this);
        Collection<User> users = helper.getAllUsers();

        if (users.isEmpty()){
            TextView message = new TextView(this);
            Toast toast = Toast.makeText(this,"Lista korisnika je prazna",Toast.LENGTH_LONG);
            toast.show();

        }

        userList = new ArrayList<>(users);
        adapter = new UsersAdapter(this,userList,this);
        listView.setAdapter(adapter);

     /*   for (User user:users){
            String name = user.getName();
            String surname = user.getSurname();

            Button btn = new Button(this);
            btn.setText(name+" "+surname);

            list.addView(btn);
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        int choosenUserId=-1;
        if (extras!=null){
            choosenUserId = extras.getInt("choosenUser");
        }
        if (choosenUserId==-1){
            return;
        }

        User user = DataStorageSingleton.dataStorage.getUser(choosenUserId);
        int i;
        for (i=0;i<userList.size();i++){
            if (userList.get(i).getId()==choosenUserId)
                break;
        }

        if (user==null){
            userList.remove(i);

        }

        adapter = new UsersAdapter(this,userList,this);
        listView.setAdapter(adapter);
    }
}
