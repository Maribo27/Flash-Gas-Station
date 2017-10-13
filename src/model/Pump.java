package model;

public class Pump {
    private static final int THING_PRICE = 15;
    private static final int PROGRESS_AT_SECOND = 10;
    private static final int PAYMENT_COEFFICIENT = 1;

    private boolean isFree;
    private boolean haveThing;

    private int progress;
    private int payment;
    private int thingToBuy;

    Pump(){
        isFree = true;
    }

    public void initCar(int patience){
        progress = 0;
        this.payment = patience + 10;
        thingToBuy = (int) (Math.random() * 5);
        haveThing = thingToBuy == 0;
    }

    public boolean isFree() {
        return isFree;
    }
    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }

    public void increaseProgress(){
        progress += PROGRESS_AT_SECOND;
    }
    public void nullProgress(){ progress = 0;}
    public int getProgress(){
        return progress;
    }

    public void reducePayment(){
        payment -= PAYMENT_COEFFICIENT;
    }
    public int getPayment(){
        return payment;
    }

    public int getThingToBuy(){
        return thingToBuy;
    }

    public void giveThing(){
        if (haveThing) return;
        haveThing = true;
        payment += THING_PRICE;
    }

    public boolean isHaveThing(){
        return haveThing;
    }
}
