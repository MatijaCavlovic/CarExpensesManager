package com.example.carexpensesmanager.feature.Details.ExpenseDetails;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carexpensesmanager.feature.DBEntity.RegistrationExpense;
import com.example.carexpensesmanager.feature.DBEntity.ServiceExpense;
import com.example.carexpensesmanager.feature.DBEntity.ServiceExpenseElement;
import com.example.carexpensesmanager.feature.Persistance.DataStorageSingleton;
import com.example.carexpensesmanager.feature.R;

import java.util.Collection;
import java.util.List;

public class ServiceExpenseDetails extends AppCompatActivity {

    private TextView dateTv;
    private TextView priceTv;
    private Button deleteExpenseBtn;
    private TextView descriptionTv;
   // private TableLayout expenseTable;
    private LinearLayout expenseElementLayout;
    int expenseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_expense_details);

        dateTv = findViewById(R.id.dateTV);
        priceTv = findViewById(R.id.priceTV);
        descriptionTv = findViewById(R.id.descriptionTextView);
    //    expenseTable = findViewById(R.id.expenseSpecificationTable);
        expenseElementLayout = findViewById(R.id.expenseElementsLayout);

        deleteExpenseBtn = findViewById(R.id.deleteExpenseBtn);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            expenseId = extras.getInt("expense");
        }
        else{
            Toast.makeText(getApplicationContext(),"Trošak nije pravilno odabran",Toast.LENGTH_LONG).show();
            return;
        }

        ServiceExpense serviceExpense = DataStorageSingleton.dataStorage.getServiceExpense(expenseId);

        if (serviceExpense==null){
            Toast.makeText(getApplicationContext(),"Došlo je do pogreške u pristupu podatcima",Toast.LENGTH_LONG).show();
            return;
        }

        Collection<ServiceExpenseElement> expenses = DataStorageSingleton.dataStorage.getAllServiceExpenseElements(expenseId);
        if (expenses==null){
            Toast.makeText(getApplicationContext(),"Došlo je do pogreške u pristupu podatcima",Toast.LENGTH_LONG).show();
            return;
        }

        dateTv.setText(serviceExpense.getDateString());
        priceTv.setText(String.format("%.2f",serviceExpense.getPrice())+" HRK");
        descriptionTv.setText(serviceExpense.getDescription());
        this.fillExpenseTable(expenses);

        deleteExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ServiceExpenseDetails.this);
                alert.setTitle("Brisanje");
                alert.setTitle("Želite li izbrisati trošak?");
                alert.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("NE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

    }

    private void delete(){
        if (DataStorageSingleton.dataStorage.deleteServiceExpense(expenseId)){
            Toast.makeText(getApplicationContext()
                    ,String.format("Trošak izbrisan")
                    ,Toast.LENGTH_LONG).show();
            onBackPressed();

        }
        else{
            Toast.makeText(getApplicationContext(),"Došlo je do greške. Molimo pokušajte ponovno.",Toast.LENGTH_LONG).show();
        }

    }

    private void fillExpenseTable(Collection<ServiceExpenseElement> expenses){

        int index = 1;
        for (ServiceExpenseElement element:expenses){
            String description = element.getDescription();
            String price = String.format("%.2f HRK",element.getPrice());
       //     TableRow tableRow = new TableRow(getApplicationContext());

         /*   TextView descriptionTv = new TextView(getApplicationContext());
            TextView priceTv = new TextView(getApplicationContext());*/

            View expenseView = View.inflate(getApplicationContext(),R.layout.expense_element,null);
            expenseElementLayout.addView(expenseView);
            TextView descriptionTv = expenseView.findViewById(R.id.descriptionTv1);
            TextView priceTv = expenseView.findViewById(R.id.priceTv1);
            TextView orderTv = expenseView.findViewById(R.id.order);

            orderTv.setText(index + ".");
            descriptionTv.setText(description);
            priceTv.setText(price);
            index++;

          /*  descriptionTv.setTextSize(20);
            priceTv.setTextSize(20);
            tableRow.addView(descriptionTv);
            tableRow.addView(priceTv);

            expenseTable.addView(tableRow);*/
        }
    }
}
