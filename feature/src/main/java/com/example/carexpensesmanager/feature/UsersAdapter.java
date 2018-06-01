package com.example.carexpensesmanager.feature;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carexpensesmanager.feature.DBEntity.User;

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
        tvRow.setText(user.getName() + " "+user.getSurname());
        tvRow.setTag(user);

        tvRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = (User) v.getTag();
                Toast.makeText(getContext(),user.getName() + " "+user.getSurname(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(),UserDetails.class);
                intent.putExtra("user",user.getId());
                parentActivity.onBackPressed();
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
