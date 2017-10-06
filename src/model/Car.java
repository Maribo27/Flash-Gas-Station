package model;

public class Car {
    private int patience;
    int thing;
    private boolean haveThing;
    public Car(){
        patience = 50;
        haveThing = false;
    }

    public void setPatience(){
        patience -= 5;
    }
    public int getPatience(){
        return patience;
    }
}
