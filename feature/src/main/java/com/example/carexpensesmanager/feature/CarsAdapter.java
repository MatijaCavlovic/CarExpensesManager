package com.example.carexpensesmanager.feature;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carexpensesmanager.feature.DBEntity.Car;
import com.example.carexpensesmanager.feature.DBEntity.User;

import java.util.ArrayList;

public class CarsAdapter extends ArrayAdapter<Car> {
    AppCompatActivity parentActivity;

    public CarsAdapter(@NonNull Context context, ArrayList<Car> cars) {
        super(context,0, cars);
    }

    public CarsAdapter(@NonNull Context context, ArrayList<Car> cars,AppCompatActivity activity) {
        super(context,0, cars);
        this.parentActivity = activity;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Car car = getItem(position);
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_car, parent, false);
        }


        TextView tvRow = convertView.findViewById(R.id.tvRow);
        tvRow.setText(car.getName());
        tvRow.setTag(car);

        tvRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Car car = (Car) v.getTag();
                Toast.makeText(getContext(),car.getName(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(),CarDetails.class);
                intent.putExtra("car",car.getId());
                parentActivity.onBackPressed();
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }

}
