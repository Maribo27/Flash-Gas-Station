package model;

public class Pump {
    private boolean isFree;
    Pump(){
        isFree = true;
    }

    public boolean isFree() {
        return isFree;
    }
    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }
}
