package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maria on 09.09.2017.
 */
public class GameProgress {
    private Characteristics characteristics = new Characteristics();
    private Level currentLevel;
    private Level firstLevel;
    private int balance;
    private int savedLevel;
    private int levelNumbers;

    public GameProgress(){
        characteristics = new Characteristics();
    }

    public void setSavedLevel(int level){
        savedLevel = level;
    }
    public int getSavedLevel(){
        return savedLevel;
    }

    public int getSpeed(){return currentLevel.speed;}
    public int getCoefficient(){return currentLevel.coefficient;}
    public int getGoal(){return currentLevel.goal;}
    public int getCarNumbers(){return currentLevel.countOfCars;}
    public int getCountOfPumps(){return currentLevel.countOfPumps;}

    public int getCurrentLevel(){
        return currentLevel.levelNumber;
    }
    public void setLevel(int level){
        while (currentLevel.levelNumber != level){
            if (currentLevel.levelNumber < level) {
                currentLevel = currentLevel.nextLevel;
            } else {
                currentLevel = currentLevel.previousLevel;
            }
        }
    }

    public int getBalance(){
        return balance;
    }
    public void setBalance(int cash){
        balance += cash;
    }
    public void nullBalance(){
        balance = 0;
    }

    public List<Pump> getPumps(){
        return currentLevel.pumps;
    }
    public void setPumps(List<Pump> pumps){
        currentLevel.pumps = pumps;
    }

    public int getThingInHand(){
        return characteristics.thingInHand;
    }
    public void setThingInHand(int thing){
        characteristics.thingInHand = thing;
    }

    public void createLevels(List<Level> levels){
        currentLevel = levels.get(savedLevel - 1);
        firstLevel = levels.get(0);
        for (int levelNumber = 1; levelNumber < levels.size(); levelNumber++){
            Level parent = levels.get(levelNumber - 1);
            Level current = levels.get(levelNumber);
            parent.setNextLevel(current);
            current.setPreviousLevel(parent);
        }
        levelNumbers = levels.size();
    }

    public List<Level> getLevels(){
        List<Level> levels = new ArrayList<>();
        Level tempLevel = firstLevel;
        for (int level = 0; level < levelNumbers; level++){
            levels.add(tempLevel);
            tempLevel = tempLevel.nextLevel;
        }
        return levels;
    }

    public int getLevelNumbers() {
        return levelNumbers;
    }

    public Level getNextLevel(){
        return currentLevel.nextLevel;
    }

    public void levelUp(){
        currentLevel = currentLevel.nextLevel;
    }
}
