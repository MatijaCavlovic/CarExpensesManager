package com.example.carexpensesmanager.feature;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carexpensesmanager.feature.DBEntity.Car;
import com.example.carexpensesmanager.feature.DBEntity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    SQLiteDatabase db;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME ="carManager.db";

    //USER
    private static final String TABLE_NAME_USER ="korisnik";
    private static final String ID_USER = "ID";
    private static final String NAME_USER = "Ime";
    private static final String SURNAME_USER = "Prezime";

    private static final String TABLE_CREATE =
            "create table "+TABLE_NAME_USER+" "+
            "(ID integer primary key not null,"+
            "Ime text not null,"+
            "Prezime text not null)";


    //CAR
    private static final String TABLE_NAME_CAR="automobil";
    private static final String ID_CAR = "ID";
    private static final String NAME_CAR ="Naziv";
    private static final String ID_OWNER = "IDVlasnik";

    private static final String TABLE_CREATE_CAR =
            "create table "+TABLE_NAME_CAR+" "+
                    "(ID integer primary key not null,"+
                    "Naziv text not null,"+
                    "IDVlasnik int not null, "+
                    "foreign key (IDVlasnik) references korisnik(ID))";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE_CAR);
        this.db=db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME_USER;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+TABLE_NAME_CAR;
        db.execSQL(query);

        this.onCreate(db);
    }

    public boolean insertUser(User user){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "SELECT MAX(ID) AS MAXID FROM "+TABLE_NAME_USER;
        Cursor cursor = db.rawQuery(query,null);
        int id=-1;
        if (cursor!=null){
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }

        values.put(ID_USER,id+1);
        values.put(NAME_USER,user.getName());
        values.put(SURNAME_USER,user.getSurname());
        long error;
        error = db.insert(TABLE_NAME_USER,null,values);

        db.close();

        if (error==-1)
            return false;
        return true;
    }

    public Collection<User> getAllUsers(){
        db = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s",TABLE_NAME_USER);

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

        db.close();
        return result;
    }

    public User getUser(int id){
        User result = null;
        db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s=%d",TABLE_NAME_USER,ID_USER,id);
        Cursor cursor = db.rawQuery(query,null);
        if (!cursor.moveToFirst()){
            return result;
        }

        result = new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
        db.close();
        return  result;
    }

    public int deleteUser(int id){
        db = this.getWritableDatabase();
        int result;
        result = db.delete(TABLE_NAME_USER,ID_USER+"="+id+"",null);
        db.close();
        return result;
    }

    public boolean insertCar(Car car){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "SELECT MAX(ID) AS MAXID FROM "+TABLE_NAME_CAR;
        Cursor cursor = db.rawQuery(query,null);
        int id=-1;
        if (cursor!=null){
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }

        values.put(ID_CAR,id+1);
        values.put(NAME_CAR,car.getName());
        values.put(ID_OWNER,car.getOwnerId());

        long error;
        error = db.insert(TABLE_NAME_CAR,null,values);

        db.close();

        if (error==-1)
            return false;
        return true;

    }

    public Collection<Car> getAllUserCars(int userId){
        db = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s WHERE %s=%d",TABLE_NAME_CAR,ID_OWNER,userId);

        Cursor cursor = db.rawQuery(query,null);
        List<Car> result = new ArrayList<>();

        if (!cursor.moveToFirst()){
            return result;
        }

        while (cursor!=null){
            Car car = new Car(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));
            result.add(car);
            if (!cursor.moveToNext())
                break;
        }
        db.close();
        return result;
    }
}
