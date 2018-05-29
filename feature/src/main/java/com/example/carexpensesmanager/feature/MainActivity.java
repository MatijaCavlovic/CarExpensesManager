package com.example.carexpensesmanager.feature;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.carexpensesmanager.feature.DBEntity.User;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper helper = new DatabaseHelper(this);
     Button btn;
     Button explorerButton;
     EditText name;
     EditText surname;
     TextView title;
     FilePickerDialog dialog;
     String dbPath;
     String dbcopyPath;

    // public static String DB_FILEPAth = "/data/data/com.example.carexpensesmanager.feature/databases/carManager.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.button);
        explorerButton = findViewById(R.id.explorerBtn);

        name=findViewById(R.id.ime);
        surname=findViewById(R.id.prezime);
        title = findViewById(R.id.textView2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString=name.getText().toString();
                String surnameString = surname.getText().toString();

                User user = new User();

                user.setId(0);
                user.setName(nameString);
                user.setSurname(surnameString);
                helper.insertUser(user);
                String file = getBaseContext().getDatabasePath(helper.getDatabaseName()).toString();


                File dbFile = new File(file);
                if (dbFile.exists()){
                    title.setText("Postoji" + file);
                    System.out.printf("Datoteka Postoji");

                }
                else{
                    title.setText("Ne postoji");
                    System.out.printf("Ne postoji");
                }
            }
        });

        explorerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogProperties properties = new DialogProperties();
                FileChooserUtils.init(properties);

                dialog = new FilePickerDialog(MainActivity.this,properties);
                dialog.setTitle("Select a File");

                dialog.setDialogSelectionListener(new DialogSelectionListener() {
                    @Override
                    public void onSelectedFilePaths(String[] files) {
                        title.setText(files[0]);
                        File file = new File(files[0]);
                        dbcopyPath = files[0];

                        if (file.exists()){
                            title.append(" Postoji");
                        }
                        else{
                            title.append(" ne Postoji");
                        }

                        dbPath = getBaseContext().getDatabasePath(helper.getDatabaseName()).toString();

                        File oldDb = new File(dbPath);
                        File newDb = new File(dbcopyPath+DialogConfigs.DIRECTORY_SEPERATOR+"copy.db");

                        try {
                            copyFileUsingStream(oldDb,newDb);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

                dialog.show();

            }


        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(dialog!=null)
                    {   //Show dialog if the read permission has been granted.
                        dialog.show();
                    }
                }
                else {
                    //Permission has not been granted. Notify the user.
                    Toast.makeText(MainActivity.this,"Potrebna je dozvola za pristup datotečnom sustavu",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

}
