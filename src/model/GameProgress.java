package model;

import controller.GasStation;

/**
 * Created by Maria on 09.09.2017.
 */
public class GameProgress {
    private int level, cars, goal;
    private GasStation controller;
    public GameProgress(int level, int cars, int goal, GasStation controller){
        this.level = level;
        this.cars = cars;
        this.controller = controller;
        this.goal = goal;
    }
    public int getLevel(){return level;}
    public int getGoal(){return goal;}
    public int getCarNumbers(){return cars;}
    public void increaseLevel(){
        level++;
        cars += 5;
        goal += 100;
    }
}
