package model;

public class Car {
    private int patience;

    public Car(){
        patience = 50;
    }

    public void setPatience(int coefficient){
        patience -= coefficient;
    }
    public int getPatience(){
        return patience;
    }
}
