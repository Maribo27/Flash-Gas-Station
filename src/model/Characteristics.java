package model;

/**
 * Created by Maria on 15.09.2017.
 */
class Characteristics {
    int FlashSpeed, pumpSpeed, patience, balance;
    int currentThing;

    Characteristics(){
        FlashSpeed = 1;
        pumpSpeed = 1;
        patience = 1;
        balance = 0;
        currentThing = 0;
    }

    void changeThing(int count){
        currentThing = count;
    }
}
