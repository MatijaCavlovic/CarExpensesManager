package com.example.carexpensesmanager.feature.Persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.carexpensesmanager.feature.DBEntity.Car;
import com.example.carexpensesmanager.feature.DBEntity.Expense;
import com.example.carexpensesmanager.feature.DBEntity.FuelExpense;
import com.example.carexpensesmanager.feature.DBEntity.InsuranceExpense;
import com.example.carexpensesmanager.feature.DBEntity.RegistrationExpense;
import com.example.carexpensesmanager.feature.DBEntity.ServiceExpense;
import com.example.carexpensesmanager.feature.DBEntity.User;
import com.example.carexpensesmanager.feature.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    SQLiteDatabase db;
    Context context;
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



    //EXPENSE
    private static final String TABLE_NAME_EXPENSE="trosak";
    private static final String ID_EXPENSE = "ID";
    private static final String ID_CAR_EXPENSE = "IDAutomobil";
    private static final String EXPENSE_ID_EXPENSE_TYPE = "IDVrstaTroska";
    private static final String EXPENSE_DATE = "datum";
    private static final String EXPENSE_PRICE = "cijena";

    private static final String TABLE_CREATE_EXPENSE =
            "create table "+TABLE_NAME_EXPENSE+" "+
                    "(ID integer primary key not null,"+
                    "IDAutomobil integer not null,"+
                    "IDVrstaTroska integer not null,"+
                    "datum text not null,"+
                    "cijena real not null,"+
                    "foreign key (IDVrstaTroska) references vrstaTrosa(ID))";

    //EXPENSE TYPE
    private static final String TABLE_NAME_EXPENSE_TYPE="vrstaTroska";
    private static final String ID_EXPENSE_TYPE = "ID";
    private static final String EXPENSE_TYPE_NAME = "nazivVrsteTroska";

    private static final String TABLE_CREATE_EXPENSE_TYPE =
            "create table "+TABLE_NAME_EXPENSE_TYPE+" "+
                    "(ID integer primary key not null,"+
                    "nazivVrsteTroska text not null)";

    //FUEL EXPENSE
    private static final String TABLE_NAME_EXPENSE_FUEL="trosakGoriva";
    private static final String ID_EXPENSE_FUEL = "ID";
    private static final String EXPENSE_FUEL_PLACE = "mjesto";

    private static final String TABLE_CREATE_EXPENSE_FUEL =
            "create table "+TABLE_NAME_EXPENSE_FUEL+" "+
                    "(ID integer primary key not null,"+
                    "mjesto text not null,"+
                    "foreign key(ID) references trosak(ID))";


    //INSURANCE EXPENSE

    private static final String TABLE_NAME_EXPENSE_INSURANCE="trosakOsiguranja";
    private static final String EXPENSE_INSURANCE_ID = "ID";

    private static final String TABLE_CREATE_EXPENCE_INSURANCE =
            "create table "+TABLE_NAME_EXPENSE_INSURANCE+" "+
                    "(ID integer primary key not null,"+
                    "foreign key(ID) references trosak(ID))";

    private static final String TABLE_NAME_EXPENSE_REGISTRATION="trosakRegistracije";
    private static final String EXPENSE_REGISTRATION_ID = "ID";

    private static final String TABLE_CREATE_EXPENSE_REGISTRATION =
            "create table "+TABLE_NAME_EXPENSE_REGISTRATION+" "+
                    "(ID integer primary key not null,"+
                    "foreign key(ID) references trosak(ID))";

    private static final String TABLE_NAME_EXPENSE_SERVICE="trosakServisa";
    private static final String EXPENSE_SERVICE_ID = "ID";

    private static final String TABLE_CREATE_EXPENSE_SERVICE =
            "create table "+TABLE_NAME_EXPENSE_SERVICE+" "+
                    "(ID integer primary key not null,"+
                    "foreign key(ID) references trosak(ID))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE_CAR);
        db.execSQL(TABLE_CREATE_EXPENSE);
        db.execSQL(TABLE_CREATE_EXPENSE_FUEL);
        db.execSQL(TABLE_CREATE_EXPENCE_INSURANCE);
        db.execSQL(TABLE_CREATE_EXPENSE_REGISTRATION);
        db.execSQL(TABLE_CREATE_EXPENSE_SERVICE);
        db.execSQL(TABLE_CREATE_EXPENSE_TYPE);
        this.db=db;
        this.init();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME_USER;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+TABLE_NAME_CAR;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+TABLE_NAME_EXPENSE;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+TABLE_NAME_EXPENSE_FUEL;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+TABLE_NAME_EXPENSE_INSURANCE;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+TABLE_NAME_EXPENSE_REGISTRATION;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+TABLE_NAME_EXPENSE_SERVICE;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+TABLE_NAME_EXPENSE_TYPE;
        db.execSQL(query);
        this.onCreate(db);
    }

    private void init(){
        boolean openedNow = false;
        if (!db.isOpen()) {
            db = this.getWritableDatabase();
            openedNow = true;
        }
        Collection<String> types = Utils.getExpenseTypesList(context);

        int id = 1;
        for (String s:types){
            ContentValues values = new ContentValues();
            values.put(ID_EXPENSE_TYPE,id++);
            values.put(EXPENSE_TYPE_NAME,s);
            db.insert(TABLE_NAME_EXPENSE_TYPE,null,values);
        }
        if (openedNow){
            db.close();
        }

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

    public Car getCar(int id){
        Car result = null;
        db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s=%d",TABLE_NAME_CAR,ID_CAR,id);
        Cursor cursor = db.rawQuery(query,null);
        if (!cursor.moveToFirst()){
            return result;
        }

        result = new Car(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));
        db.close();
        return  result;
    }

    public int deleteCar(int id){
        db = this.getWritableDatabase();
        int result;
        result = db.delete(TABLE_NAME_CAR,ID_CAR+"="+id+"",null);
        db.close();
        return result;
    }

    public int insertExpense(Expense expense,String type){
        db = this.getWritableDatabase();

        String query = String.format("SELECT %s FROM %s WHERE %s='%s'",ID_EXPENSE_TYPE,TABLE_NAME_EXPENSE_TYPE,EXPENSE_TYPE_NAME,type);
        Cursor cursor = db.rawQuery(query,null);
        int idType;
        if (cursor!=null){
            cursor.moveToFirst();
            idType = cursor.getInt(0);
        }
        else{
            return -1;
        }

        ContentValues values = new ContentValues();

        query = "SELECT MAX(ID) AS MAXID FROM "+TABLE_NAME_EXPENSE;
        cursor = db.rawQuery(query,null);
        int id=-1;
        if (cursor!=null){
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }

        values.put(ID_EXPENSE,id+1);
        values.put(ID_CAR_EXPENSE,expense.getCarId());
        values.put(EXPENSE_ID_EXPENSE_TYPE,idType);
        values.put(EXPENSE_DATE,expense.getDateString());
        values.put(EXPENSE_PRICE,expense.getPrice());

        long error;
        error = db.insert(TABLE_NAME_EXPENSE,null,values);
        db.close();

        if (error==-1)
            return -1;
        return id+1;
    }

    public int insertFuelExpense(FuelExpense expense){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID_EXPENSE,expense.getId());
        values.put(EXPENSE_FUEL_PLACE,expense.getPlace());

        long error;
        error = db.insert(TABLE_NAME_EXPENSE_FUEL,null,values);
        db.close();

        if (error==-1)
            return -1;
        return expense.getId();
    }

    public int insertInsuranceExpense(InsuranceExpense expense){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EXPENSE_INSURANCE_ID,expense.getId());
        long error;
        error = db.insert(TABLE_NAME_EXPENSE_INSURANCE,null,values);
        db.close();

        if (error==-1)
            return -1;
        return expense.getId();
    }

    public int insertServiceExpense(ServiceExpense expense){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EXPENSE_SERVICE_ID,expense.getId());

        long error;
        error = db.insert(TABLE_NAME_EXPENSE_SERVICE,null,values);
        db.close();

        if (error==-1)
            return -1;
        return expense.getId();
    }

    public int insertRegistrationExpense(RegistrationExpense expense){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EXPENSE_REGISTRATION_ID,expense.getId());

        long error;
        error = db.insert(TABLE_NAME_EXPENSE_REGISTRATION,null,values);
        db.close();

        if (error==-1)
            return -1;
        return expense.getId();
    }

    public Collection<Expense> getAllCarsExpenses(int carId){
        db = this.getReadableDatabase();

        String query = String.format("SELECT * from %s join %s on %s.%s=%d and %s.%s=%s.%s",
                                    TABLE_NAME_EXPENSE,TABLE_NAME_EXPENSE_TYPE,TABLE_NAME_EXPENSE,ID_CAR_EXPENSE,
                carId,TABLE_NAME_EXPENSE_TYPE,ID_EXPENSE_TYPE,TABLE_NAME_EXPENSE,EXPENSE_ID_EXPENSE_TYPE);

   //     String query = String.format("SELECT * FROM %s WHERE %s=%d",TABLE_NAME_EXPENSE,ID_CAR_EXPENSE,carId);
      //  String query = String.format("SELECT * FROM %s",TABLE_NAME_EXPENSE);
        Cursor cursor = db.rawQuery(query,null);
        List<Expense> result = new ArrayList<>();

        if (!cursor.moveToFirst()){
            Log.d("poruka","Ne ide");
            return result;
        }
        Log.d("POSLAN ID",carId+"");

        while (cursor!=null){
            Expense expense = new Expense();
            Log.d("u kursoru",cursor.getInt(1)+"");
            expense.setId(cursor.getInt(0));
            expense.setCarId(cursor.getInt(1));
            expense.setDate(cursor.getString(3));
            expense.setPrice(cursor.getDouble(4));
            expense.setType(cursor.getString(6));
            Log.d("u kursoru tip",expense.getType()+"");
            result.add(expense);
            if (!cursor.moveToNext())
                break;
        }
        db.close();
        return result;
    }

    public FuelExpense getFuelExpense(int expenseId){
        FuelExpense result = null;
        db = this.getReadableDatabase();

        String query = String.format("select * from trosak join trosakGoriva on "+
                "trosak.ID=trosakGoriva.ID and trosak.ID = %d",expenseId);

        Cursor cursor = db.rawQuery(query,null);

        if (!cursor.moveToFirst()){
            return result;
        }

        result = new FuelExpense();
        result.setId(cursor.getInt(0));
        result.setCarId(cursor.getInt(1));
        result.setDate(cursor.getString(3));
        result.setPrice(cursor.getDouble(4));
        result.setPlace(cursor.getString(6));

        return result;
    }

    public InsuranceExpense getInsuranceExpense(int expenseId){
        InsuranceExpense result = null;
        db = this.getReadableDatabase();

        String query = String.format("select * from trosak join trosakOsiguranja on "+
                "trosak.ID=trosakOsiguranja.ID and trosak.ID = %d",expenseId);

        Cursor cursor = db.rawQuery(query,null);

        if (!cursor.moveToFirst()){
            return result;
        }

        result = new InsuranceExpense();
        result.setId(cursor.getInt(0));
        result.setCarId(cursor.getInt(1));
        result.setDate(cursor.getString(3));
        result.setPrice(cursor.getDouble(4));

        return result;
    }

    public RegistrationExpense getRegistrationExpense(int expenseId){
        RegistrationExpense result = null;
        db = this.getReadableDatabase();

        String query = String.format("select * from trosak join trosakRegistracije on "+
                "trosak.ID=trosakRegistracije.ID and trosak.ID = %d",expenseId);

        Cursor cursor = db.rawQuery(query,null);

        if (!cursor.moveToFirst()){
            return result;
        }

        result = new RegistrationExpense();
        result.setId(cursor.getInt(0));
        result.setCarId(cursor.getInt(1));
        result.setDate(cursor.getString(3));
        result.setPrice(cursor.getDouble(4));

        return result;
    }

    public ServiceExpense getServiceExpense(int expenseId){
        ServiceExpense result = null;
        db = this.getReadableDatabase();

        String query = String.format("select * from trosak join trosakServisa on "+
                "trosak.ID=trosakServisa.ID and trosak.ID = %d",expenseId);

        Cursor cursor = db.rawQuery(query,null);

        if (!cursor.moveToFirst()){
            return result;
        }

        result = new ServiceExpense();
        result.setId(cursor.getInt(0));
        result.setCarId(cursor.getInt(1));
        result.setDate(cursor.getString(3));
        result.setPrice(cursor.getDouble(4));

        return result;
    }

    public int deleteExpense(int expenseId){
        db = this.getWritableDatabase();
        int result;
        result = db.delete(TABLE_NAME_EXPENSE,ID_EXPENSE+"="+expenseId+"",null);
        db.close();
        return result;
    }

    public int deleteFuelExpense(int expenseId){
        db = this.getWritableDatabase();
        int result;
        result = db.delete(TABLE_NAME_EXPENSE_FUEL,ID_EXPENSE_FUEL+"="+expenseId+"",null);
        db.close();
        return result;
    }

    public int deleteInsuranceExpense(int expenseId){
        db = this.getWritableDatabase();
        int result;
        result = db.delete(TABLE_NAME_EXPENSE_INSURANCE,ID_EXPENSE+"="+expenseId+"",null);
        db.close();
        return result;
    }

    public int deleteRegistrationExpense(int expenseId){
        db = this.getWritableDatabase();
        int result;
        result = db.delete(TABLE_NAME_EXPENSE_REGISTRATION,ID_EXPENSE+"="+expenseId+"",null);
        db.close();
        return result;
    }

    public int deleteServiceExpense(int expenseId){
        db = this.getWritableDatabase();
        int result;
        result = db.delete(TABLE_NAME_EXPENSE_SERVICE,ID_EXPENSE+"="+expenseId+"",null);
        db.close();
        return result;
    }


}
