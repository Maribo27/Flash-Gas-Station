package model;

/**
 * Created by Maria on 15.09.2017.
 */
class Characteristics {
    int currentThing;

    Characteristics(){
        currentThing = 0;
    }

    void setCurrentThing(int count){
        currentThing = count;
    }
    int getCurrentThing(){
        return currentThing;
    }
}
