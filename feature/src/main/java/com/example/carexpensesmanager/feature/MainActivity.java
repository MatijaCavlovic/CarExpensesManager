package com.example.carexpensesmanager.feature;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
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
import com.example.carexpensesmanager.feature.Persistance.DataStorage;
import com.example.carexpensesmanager.feature.Persistance.DataStorageSingleton;
import com.example.carexpensesmanager.feature.Persistance.SQLiteManager;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

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

    public FTPClient mFTPClient = null;

     Button btn;
     Button explorerButton;
     Button listUsersBtn;
     Button getDbBtn;
     TextView title;
     FilePickerDialog dialog;
     String dbPath;
     String dbcopyPath;
     String databaseFile;
     DataStorage dataStorage;
     DatabaseHelper helper = new DatabaseHelper(this);
    // public static String DB_FILEPAth = "/data/data/com.example.carexpensesmanager.feature/databases/carManager.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.button);
        explorerButton = findViewById(R.id.explorerBtn);
        listUsersBtn = findViewById(R.id.listUsersBtn);
        getDbBtn = findViewById(R.id.getDbBtn);

        title = findViewById(R.id.textView2);
        dataStorage = new SQLiteManager(this);
        DataStorageSingleton.setDataStorage(dataStorage);
     //   databaseFile = DataStorageSingleton.dataStorage.getDatabaseFile(getBaseContext()).toString();
        databaseFile = getBaseContext().getDatabasePath(helper.getDatabaseName()).toString();
        title.setText(databaseFile);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddUser.class);
                startActivity(intent);
            }
        });

        explorerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ftpConnect("192.168.137.1","Matija","kokikoki",21);
                    }
                }).start();
            }


        });

        listUsersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserList.class);
                startActivity(intent);
            }
        });

        getDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ftpConnectGet("192.168.137.1","Matija","kokikoki",21);
                    }
                }).start();
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


    public boolean ftpConnect(String host, String username,
                              String password, int port)
    {
        try {

            mFTPClient = new FTPClient();

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            // connecting to the host
            mFTPClient.connect(host, port);

            // now check the reply code, if positive mean connection success
            if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
                // login using username & password
                boolean status = mFTPClient.login(username, password);

                /* Set File Transfer Mode
                 *
                 * To avoid corruption issue you must specified a correct
                 * transfer mode, such as ASCII_FILE_TYPE, BINARY_FILE_TYPE,
                 * EBCDIC_FILE_TYPE .etc. Here, I use BINARY_FILE_TYPE
                 * for transferring text, image, and compressed files.
                 */
                mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                mFTPClient.enterLocalPassiveMode();

                InputStream fis = null;
                try{
//                    Toast.makeText(getApplicationContext(),databaseFile,Toast.LENGTH_LONG).show();
                    fis = new FileInputStream(databaseFile);
             //       title.setText(databaseFile);
                    mFTPClient.storeFile("nova.db",fis);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally{
                    fis.close();
                }


                // tv.setText(status+"");
                return status;
            }
        } catch(Exception e) {
            //    tv.setText("failed");
            e.printStackTrace();
        }

        return false;
    }


    public boolean ftpConnectGet(String host, String username,
                              String password, int port)
    {
        try {
            mFTPClient = new FTPClient();

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            // connecting to the host
            mFTPClient.connect(host, port);


            // now check the reply code, if positive mean connection success
            if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
                // login using username & password
                boolean status = mFTPClient.login(username, password);

                /* Set File Transfer Mode
                 *
                 * To avoid corruption issue you must specified a correct
                 * transfer mode, such as ASCII_FILE_TYPE, BINARY_FILE_TYPE,
                 * EBCDIC_FILE_TYPE .etc. Here, I use BINARY_FILE_TYPE
                 * for transferring text, image, and compressed files.
                 */
                mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                mFTPClient.enterLocalPassiveMode();

                OutputStream fos = null;
                try{

                    fos = new FileOutputStream(databaseFile);

                    mFTPClient.retrieveFile("nova.db",fos);
                    DataStorage dataStorage = new SQLiteManager(this);
                    DataStorageSingleton.setDataStorage(dataStorage);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally{
                    fos.close();
                }


                // tv.setText(status+"");
                return status;
            }
        } catch(Exception e) {
            //    tv.setText("failed");
            e.printStackTrace();
        }

        return false;
    }


}
