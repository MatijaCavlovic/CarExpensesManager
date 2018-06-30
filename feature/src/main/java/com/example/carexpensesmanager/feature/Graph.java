package com.example.carexpensesmanager.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.carexpensesmanager.base.DBEntity.Expense;
import com.example.carexpensesmanager.base.Persistance.DataStorageSingleton;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Graph extends AppCompatActivity {

    private PieChart pieChart ;
    private ArrayList<Entry> entries ;
    private ArrayList<String> PieEntryLabels ;
    private Collection<Expense> expenses;
    private int carId;
    private PieDataSet pieDataSet ;
    private PieData pieData ;
    private Map<String,Double> expensesSumMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            carId = extras.getInt("car");
        }
        else{
            Toast.makeText(getApplicationContext(),"Automobil nije pravilno odabran",Toast.LENGTH_LONG).show();
            return;
        }

        expenses = DataStorageSingleton.dataStorage.getAllCarExpenses(carId);

        initExpenses();

        pieChart = (PieChart) findViewById(R.id.chart1);

        entries = new ArrayList<>();

        PieEntryLabels = new ArrayList<String>();

        AddValuesToPIEENTRY();

        AddValuesToPieEntryLabels();

        pieDataSet = new PieDataSet(entries, "");

        pieData = new PieData(PieEntryLabels, pieDataSet);

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        pieChart.setData(pieData);

        pieChart.animateY(3000);

        pieDataSet.setValueTextSize(15);

        pieChart.setDescription("Struktura troškova po kategorijama");
        pieChart.setDescriptionTextSize(15);


    }

    public void AddValuesToPIEENTRY(){


        /*entries.add(new BarEntry(2f, 0));
        entries.add(new BarEntry(4f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(8f, 3));*/

      /*  entries.add(new BarEntry(expensesSumMap.get("Gorivo").floatValue(), 0));
        entries.add(new BarEntry(expensesSumMap.get("Osiguranje").floatValue(), 1));
        entries.add(new BarEntry(expensesSumMap.get("Registracija").floatValue(), 2));
        entries.add(new BarEntry(expensesSumMap.get("Servis").floatValue(), 3));*/
      int index=0;
        for (Map.Entry<String,Double> entry : expensesSumMap.entrySet()){
            if (entry.getValue()!=0){
                entries.add(new BarEntry(entry.getValue().floatValue(), index));
                index++;
            }
        }
    }

    public void AddValuesToPieEntryLabels(){

       /* PieEntryLabels.add("January");
        PieEntryLabels.add("February");
        PieEntryLabels.add("March");
        PieEntryLabels.add("April");
        PieEntryLabels.add("May");
        PieEntryLabels.add("June");*/

       /* PieEntryLabels.add("Gorivo");
        PieEntryLabels.add("Osiguranje");
        PieEntryLabels.add("Registracija");
        PieEntryLabels.add("Servis");*/

        for (Map.Entry<String,Double> entry : expensesSumMap.entrySet()){
            if (entry.getValue()!=0){
                PieEntryLabels.add(entry.getKey());
            }
        }

    }

    private  void initExpenses(){
        expensesSumMap = new HashMap<>();
        expensesSumMap.put("Gorivo",0.0);
        expensesSumMap.put("Osiguranje",0.0);
        expensesSumMap.put("Registracija",0.0);
        expensesSumMap.put("Servis",0.0);

        for (Expense expense:expenses){
            String expenseType = expense.getType();
            double expenseValue = expensesSumMap.get(expenseType);
            expenseValue+=expense.getPrice();
            expensesSumMap.put(expenseType,expenseValue);
        }


    }
}
