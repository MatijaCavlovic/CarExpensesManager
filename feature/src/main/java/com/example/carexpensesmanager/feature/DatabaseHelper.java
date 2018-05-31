package com.example.carexpensesmanager.feature;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carexpensesmanager.feature.DBEntity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    SQLiteDatabase db;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME ="carManager.db";
    private static final String TABLE_NAME ="korisnik";

    private static final String ID = "ID";
    private static final String NAME = "Ime";
    private static final String SURNAME = "Prezime";

    private static final String TABLE_CREATE =
            "create table "+TABLE_NAME+" "+
            "(ID integer primary key not null,"+
            "Ime text not null,"+
            "Prezime text not null)";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db=db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public void insertUser(User user){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "SELECT MAX(ID) AS MAXID FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        int id=-1;
        if (cursor!=null){
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }

        values.put(ID,id+1);
        values.put(NAME,user.getName());
        values.put(SURNAME,user.getSurname());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public Collection<User> getAllUsers(){
        db = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s",TABLE_NAME);

        Cursor cursor = db.rawQuery(query,null);
        List<User> result = new ArrayList<>();

       if (!cursor.moveToFirst()){
           return result;
       }

        while (cursor!=null){
            User user = new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
            result.add(user);

            if (!cursor.moveToNext())
                break;
        }

        return result;
    }
}
