package com.example.carexpensesmanager.feature.Details.DataAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carexpensesmanager.feature.DBEntity.Expense;
import com.example.carexpensesmanager.feature.Details.ExpenseDetails.FuelExpenseDetails;
import com.example.carexpensesmanager.feature.Details.ExpenseDetails.InsuranceExpenseDetails;
import com.example.carexpensesmanager.feature.Details.ExpenseDetails.RegistrationExpenseDetails;
import com.example.carexpensesmanager.feature.Details.ExpenseDetails.ServiceExpenseDetails;
import com.example.carexpensesmanager.feature.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpensesAdapter extends ArrayAdapter<Expense> {

    private static Map<String,Integer> expenseImageMap = createMap();
    private static Map<String,Integer> createMap(){
        Map<String,Integer> result = new HashMap<>();
        result.put("Gorivo", R.drawable.fuel);
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
        TextView priceTv = convertView.findViewById(R.id.priceTV);
        View element = convertView.findViewById(R.id.element);

        ImageView imageView = convertView.findViewById(R.id.expenseImage);
        imageView.setImageResource(expenseImageMap.get(expense.getType()));

        tvRow.setText(expense.getType()+"");
        dateTV.setText(expense.getDateString());
        priceTv.setText(String.format("%.2f",expense.getPrice())+" HRK");

        element.setTag(expense);
        element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expense expense = (Expense) v.getTag();
             //   Intent intent = new Intent(getContext(),FuelExpenseDetails.class);
                Intent intent = ExpensesAdapter.this.createIntent(expense);
                intent.putExtra("expense",expense.getId());
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    private Intent createIntent(Expense expense){
        String type = expense.getType();
        Intent intent=null;

        switch (type){
            case "Gorivo":
                intent = new Intent(getContext(),FuelExpenseDetails.class);
                break;
            case "Osiguranje":
                intent = new Intent(getContext(),InsuranceExpenseDetails.class);
                break;
            case "Registracija":
                intent = new Intent(getContext(), RegistrationExpenseDetails.class);
                break;
            case "Servis":
                intent = new Intent(getContext(), ServiceExpenseDetails.class);
                break;
        }

        return intent;

    }

}
