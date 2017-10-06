package model;

public class Pump {
    private boolean isFree;
    private boolean haveThing;

    private int progress;
    private int patience;
    private int thing;

    Pump(){
        isFree = true;
    }

    public void initCar(int patience){
        progress = 0;
        this.patience = patience;
        thing = (int) (Math.random() * 4);
        haveThing = thing == 0;
    }

    public boolean isFree() {
        return isFree;
    }
    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }

    public void setProgress(){
        progress += 5;
    }
    public void nullProgress(){ progress = 0;}
    public int getProgress(){
        return progress;
    }


    public void setPatience(){
        patience -= 1;
    }
    public int getPatience(){
        return patience;
    }

    public int getThing(){
        return thing;
    }

    public void setHaveThing(){
        haveThing = true;
    }
    public boolean isHaveThing(){
        return haveThing;
    }

}
