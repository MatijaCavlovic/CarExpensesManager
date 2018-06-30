package com.example.carexpensesmanager.base.Persistance;

public class DataStorageSingleton {

    public static DataStorage dataStorage;

   public static void setDataStorage(DataStorage ds){
       dataStorage = ds;
   }
}
