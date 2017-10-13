package model;

import java.util.ArrayList;
import java.util.List;

public class Level {
    int countOfCars;
    int countOfPumps;
    int goal;
    int speed;
    int coefficient;
    int levelNumber;

    Level nextLevel;
    Level previousLevel;

    List<Pump> pumps;

    public Level() {
        nextLevel = null;
    }

    public int getCountOfCars() {
        return countOfCars;
    }

    public void setCountOfCars(int countOfCars) {
        this.countOfCars = countOfCars;
    }

    public int getCountOfPumps() {
        return countOfPumps;
    }

    public void setCountOfPumps(int countOfPumps) {
        this.countOfPumps = countOfPumps;
        pumps = new ArrayList<>();

        for (int pump = 0; pump < countOfPumps; pump++){
            pumps.add(new Pump());
        }
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    void setNextLevel(Level nextLevel) {
        this.nextLevel = nextLevel;
    }

    void setPreviousLevel(Level previousLevel) {
        this.previousLevel = previousLevel;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }
}