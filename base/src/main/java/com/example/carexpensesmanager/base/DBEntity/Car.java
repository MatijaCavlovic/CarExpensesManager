package com.example.carexpensesmanager.base.DBEntity;

public class Car {

    private int id;

    private String name;
    private int ownerId;

    public Car(){
        this(0,null,0);
    }

    public Car(int id,String name,int ownerId) {
        this.id=id;
        this.name=name;
        this.ownerId=ownerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

}
