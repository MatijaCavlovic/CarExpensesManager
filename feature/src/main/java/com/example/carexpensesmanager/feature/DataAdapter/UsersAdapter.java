package com.example.carexpensesmanager.feature.DataAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.carexpensesmanager.feature.DBEntity.User;
import com.example.carexpensesmanager.feature.Persistance.DataStorageSingleton;
import com.example.carexpensesmanager.feature.R;
import com.example.carexpensesmanager.feature.Details.UserDetails;

import java.util.ArrayList;


public class UsersAdapter extends ArrayAdapter<User> {
    AppCompatActivity parentActivity;

    public UsersAdapter(@NonNull Context context, ArrayList<User> users) {
        super(context,0, users);
    }

    public UsersAdapter(@NonNull Context context, ArrayList<User> users,AppCompatActivity activity) {
        super(context,0, users);
        this.parentActivity = activity;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        User user = getItem(position);
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        TextView tvRow = convertView.findViewById(R.id.tvRow);
        TextView numberOfCarsView = convertView.findViewById(R.id.numberOfCarsTv);
        View element = convertView.findViewById(R.id.element);
        element.setTag(user);

        tvRow.setText(user.getName() + " "+user.getSurname());
        int numberOfCars = DataStorageSingleton.dataStorage.getAllUserCars(user.getId()).size();
        numberOfCarsView.setText("Trenutno posjeduje "+numberOfCars+" automobila");
      //  tvRow.setTag(user);

        element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = (User) v.getTag();
                Intent intent = new Intent(getContext(),UserDetails.class);
                intent.putExtra("user",user.getId());
                parentActivity.getIntent().putExtra("choosenUser",user.getId());
             //   parentActivity.onBackPressed();
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
