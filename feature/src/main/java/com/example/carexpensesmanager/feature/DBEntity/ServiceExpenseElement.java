package com.example.carexpensesmanager.feature.DBEntity;

public class ServiceExpenseElement {
    private int id;
    private int serviceExpenseId;
    private String description;
    private double price;

    public ServiceExpenseElement(int id,int serviceExpenseId,String description,double price){
        this.id=id;
        this.serviceExpenseId = serviceExpenseId;
        this.description = description;
        this.price = price;
    }

    public ServiceExpenseElement (){
        this(0,0,null,0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceExpenseId() {
        return serviceExpenseId;
    }

    public void setServiceExpenseId(int serviceExpenseId) {
        this.serviceExpenseId = serviceExpenseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
