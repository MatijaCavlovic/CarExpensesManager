package com.example.carexpensesmanager.feature;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carexpensesmanager.feature.DBEntity.Car;
import com.example.carexpensesmanager.feature.DBEntity.Expense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpensesAdapter extends ArrayAdapter<Expense> {

    private static Map<String,Integer> expenseImageMap = createMap();
    private static Map<String,Integer> createMap(){
        Map<String,Integer> result = new HashMap<>();
        result.put("Gorivo",R.drawable.fuel);
        result.put("Osiguranje",R.drawable.insurance);
        result.put("Servis",R.drawable.tool);
        result.put("Registracija",R.drawable.registration);
        return result;
    }
    public ExpensesAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public ExpensesAdapter(@NonNull Context context, ArrayList<Expense> expenses, AppCompatActivity activity) {
        super(context,0, expenses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Expense expense = getItem(position);
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expense, parent, false);
        }

        TextView tvRow = convertView.findViewById(R.id.tvRow);
        TextView dateTV = convertView.findViewById(R.id.dateTV);
        ImageView imageView = convertView.findViewById(R.id.expenseImage);
        imageView.setImageResource(expenseImageMap.get(expense.getType()));

        tvRow.setText(expense.getPrice()+"");
        dateTV.setText(expense.getDateString());
        tvRow.setTag(expense);

        tvRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expense expense = (Expense) v.getTag();
                Intent intent = new Intent(getContext(),CarDetails.class);
                intent.putExtra("expense",expense.getId());
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }

}
