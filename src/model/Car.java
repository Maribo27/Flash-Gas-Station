package model;

public class Car {
    private int patience;

    public Car(){
        patience = 50;
    }

    public void setPatience(){
        patience -= 5;
    }
    public int getPatience(){
        return patience;
    }
}
