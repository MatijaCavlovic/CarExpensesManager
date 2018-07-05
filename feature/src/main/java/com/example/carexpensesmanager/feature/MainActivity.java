package com.example.carexpensesmanager.feature;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.carexpensesmanager.feature.AddComponents.AddUser;
import com.example.carexpensesmanager.feature.Details.UserList;
import com.example.carexpensesmanager.base.Persistance.DataStorage;
import com.example.carexpensesmanager.base.Persistance.DataStorageSingleton;
import com.example.carexpensesmanager.base.Persistance.DatabaseHelper;
import com.example.carexpensesmanager.base.Persistance.SQLiteManager;
import com.github.angads25.filepicker.view.FilePickerDialog;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
public class MainActivity extends AppCompatActivity {

    public FTPClient mFTPClient = null;
     private Button btn;
     private Button explorerButton;
     private Button listUsersBtn;
     private Button getDbBtn;
     private TextView title;
     private String databaseFile;
     private DataStorage dataStorage;
     private DatabaseHelper helper = new DatabaseHelper(this);
     private int nOfConections;

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
        databaseFile = DataStorageSingleton.dataStorage.getDatabaseFile(getBaseContext()).toString();
        databaseFile = getBaseContext().getDatabasePath(helper.getDatabaseName()).toString();
       // title.setText(databaseFile);

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
                boolean success;
                success =  ftpConnect("192.168.137.1","Matija","kokikoki",21);
                if (success){
                    Toast.makeText(getApplicationContext(),"Podatci uspješno spremljeni",Toast.LENGTH_LONG).show();
                    }
                    else {
                    Toast.makeText(getApplicationContext(),"Greška u pristupu serveru",Toast.LENGTH_LONG).show();

                    }

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
                boolean success;
                success = ftpConnectGet("192.168.137.1","Matija","kokikoki",21);
                if (success){
                    Toast.makeText(getApplicationContext(),"Podatci uspješno dohvaćeni",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Greška u pristupu serveru",Toast.LENGTH_LONG).show();

                }
             /*   new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ftpConnectGet("192.168.137.1","Matija","kokikoki",21);
                    }
                }).start();*/
            }
        });


    }

    public boolean ftpConnect(String host, String username,
                              String password, int port)
    {
        if (nOfConections!=0)
            nOfConections=0;

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
        if (nOfConections!=0)
            nOfConections=0;

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
                    File file = new File(databaseFile);

                  /*  title.setText(databaseFile);
                    if (file.exists()){
                        title.append(" da");
                    }*/
                    fos = new FileOutputStream(databaseFile);
                    fos.flush();
                    mFTPClient.retrieveFile("nova.db",fos);
                    fos.flush();
                    mFTPClient.logout();
                    mFTPClient.disconnect();

                 /*   DataStorage dataStorage = new SQLiteManager(this);
                    DataStorageSingleton.setDataStorage(dataStorage);*/
                }
                catch (Exception e){
                    fos.flush();
                    mFTPClient.logout();
                    mFTPClient.disconnect();
                    nOfConections++;
                    if (nOfConections>=30) return false;
                    ftpConnectGet("192.168.137.1","Matija","kokikoki",21);

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
