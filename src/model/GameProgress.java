package model;

import controller.GasStation;

/**
 * Created by Maria on 09.09.2017.
 */
public class GameProgress {
    private Characteristics characteristics = new Characteristics();
    private Level levelInfo = new Level();

    public GameProgress(GasStation controller){
        characteristics = new Characteristics();
        levelInfo = new Level();
    }

    public int getLevel(){return levelInfo.currentLevel;}
    public int getGoal(){return levelInfo.goal;}
    public int getCarNumbers(){return levelInfo.countOfCars;}
    public int getCountOfPumps(){return levelInfo.countOfPumps;}
    /*public int getFlashSpeed(){return characteristics.FlashSpeed;}
    public int getPumpSpeed(){return characteristics.pumpSpeed;}
    public int getPatience(){return characteristics.patience;}
    public String getDescription(){return levelInfo.description;}

    public void increaseFlashSpeed(){
        characteristics.FlashSpeed++;
    }
    public void increasePumpSpeed(){
        characteristics.pumpSpeed++;
    }
    public void increasePatience(){
        characteristics.patience++;
    }

    public void increaseLevel(){
        levelInfo.increaseLevel();
    }
*/
    public int getCurrentThing(){
        return characteristics.currentThing;
    }

    public void changeThing(int count){
        characteristics.changeThing(count);
    }
}
