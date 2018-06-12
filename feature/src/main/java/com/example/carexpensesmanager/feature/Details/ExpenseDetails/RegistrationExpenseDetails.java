package com.example.carexpensesmanager.feature.Details.ExpenseDetails;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carexpensesmanager.feature.DBEntity.InsuranceExpense;
import com.example.carexpensesmanager.feature.DBEntity.RegistrationExpense;
import com.example.carexpensesmanager.feature.Persistance.DataStorageSingleton;
import com.example.carexpensesmanager.feature.R;

public class RegistrationExpenseDetails extends AppCompatActivity {


    TextView dateTv;
    TextView priceTv;
    Button deleteExpenseBtn;

    int expenseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_expense_details);

        dateTv = findViewById(R.id.dateTV);
        priceTv = findViewById(R.id.priceTV);

        deleteExpenseBtn = findViewById(R.id.deleteExpenseBtn);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            expenseId = extras.getInt("expense");
        }
        else{
            Toast.makeText(getApplicationContext(),"Trošak nije pravilno odabran",Toast.LENGTH_LONG).show();
            return;
        }

        RegistrationExpense registrationExpense = DataStorageSingleton.dataStorage.getRegistrationExpense(expenseId);

        if (registrationExpense==null){
            Toast.makeText(getApplicationContext(),"Došlo je do pogreške u pristupu podatcima",Toast.LENGTH_LONG).show();
            return;
        }

        dateTv.setText(registrationExpense.getDateString());
        priceTv.setText(String.format("%.2f",registrationExpense.getPrice())+" HRK");

        deleteExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(RegistrationExpenseDetails.this);
                alert.setTitle("Brisanje");
                alert.setTitle("Želite li izbisati trošak?");
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
        if (DataStorageSingleton.dataStorage.deleteRegistrationExpense(expenseId)){
            Toast.makeText(getApplicationContext()
                    ,String.format("Trošak izbrisan")
                    ,Toast.LENGTH_LONG).show();
            onBackPressed();

        }
        else{
            Toast.makeText(getApplicationContext(),"Došlo je do greške. Molimo pokušajte ponovno.",Toast.LENGTH_LONG).show();
        }
    }
}
