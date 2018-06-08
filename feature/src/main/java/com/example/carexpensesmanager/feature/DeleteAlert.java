package com.example.carexpensesmanager.feature;

import android.app.AlertDialog;
import android.arch.core.util.Function;
import android.content.Context;
import android.content.DialogInterface;

import java.util.function.Consumer;

public class DeleteAlert {
    private AlertDialog.Builder alert;
    private Context context;
    private String title;
    private String message;

    public DeleteAlert(Context context, String title, String message, AlertDialog.OnClickListener action) {
        this.alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);

        alert.setPositiveButton("DA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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

}
