package model;

import java.util.List;

/**
 * Created by Maria on 09.09.2017.
 */
public class GameProgress {
    private Characteristics characteristics = new Characteristics();
    private Level levelInfo = new Level();

    public GameProgress(){
        characteristics = new Characteristics();
        levelInfo = new Level();
    }

    public int getSpeed(){return levelInfo.speed;}
    public int getCoefficient(){return levelInfo.coefficient;}
    public int getGoal(){return levelInfo.goal;}
    public int getCarNumbers(){return levelInfo.countOfCars;}
    public int getCountOfPumps(){return levelInfo.countOfPumps;}

    public int getCurrentLevel(){
        return levelInfo.currentLevel;
    }
    public void setLevel(int level){
        levelInfo.setLevel(level);
    }

    public int getBalance(){
        return levelInfo.balance;
    }
    public void setBalance(int cash){
        levelInfo.balance += cash;
    }
    public void nullBalance(){
        levelInfo.balance = 0;
    }

    public List<Pump> getPumps(){
        return levelInfo.pumps;
    }
    public void setPumps(List<Pump> pumps){
        levelInfo.pumps = pumps;
    }

    public int getThingInHand(){
        return characteristics.thingInHand;
    }
    public void setThingInHand(int thing){
        characteristics.thingInHand = thing;
    }
}
