package model;

public class Pump {
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
        this.payment = patience + 15;
        thingToBuy = (int) (Math.random() * 4);
        haveThing = thingToBuy == 0;
    }

    public boolean isFree() {
        return isFree;
    }
    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }

    public void increaseProgress(){
        progress += 10;
    }
    public void nullProgress(){ progress = 0;}
    public int getProgress(){
        return progress;
    }


    public void reducePayment(){
        payment -= 1;
    }
    public int getPayment(){
        return payment;
    }

    public int getThingToBuy(){
        return thingToBuy;
    }

    public void setHaveThing(){
        if (haveThing) return;
        haveThing = true;
        payment += 15;
    }
    public boolean isHaveThing(){
        return haveThing;
    }

}
