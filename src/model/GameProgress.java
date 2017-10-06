package model;

import controller.GasStation;

import java.util.List;

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

    public int getGoal(){return levelInfo.goal;}
    public int getCarNumbers(){return levelInfo.countOfCars;}
    public int getCountOfPumps(){return levelInfo.countOfPumps;}
    public int getCurrentThing(){
        return characteristics.currentThing;
    }
    public int getCurrentLevel(){
        return levelInfo.currentLevel;
    }
    public void changeLevel(int level){
        levelInfo.changeState(level);
    }
    public int getCurrentGoal(){
        return levelInfo.currentGoal;
    }
    public void setCurrentGoal(int goal){
        levelInfo.currentGoal = goal;
    }

    public List<Pump> getPumps(){
        return levelInfo.pumps;
    }

    public void setPumps(List<Pump> pumps){
        levelInfo.pumps = pumps;
    }

    public void changeThing(int count){
        characteristics.changeThing(count);
    }
}
