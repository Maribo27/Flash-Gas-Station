package controller;

import graphics.GameField;
import graphics.LevelSelection;
import graphics.MainFrame;
import model.GameProgress;
import model.Pump;

import java.util.List;

/**
 * Created by Maria on 09.09.2017.
 */
public class GasStation {
    private MainFrame view;
    private GameProgress model;

    public GasStation(){
        view = new MainFrame(this);
        model = new GameProgress(this);
    }

    public void showLevelScreen(){
        LevelSelection levelSelection = new LevelSelection(model.getCurrentLevel() + 1, this);
        view.changeFrame(levelSelection.getLevelPanel());
    }

    public void startGame(int level){
        model.changeLevel(level);
        GameField gameField = new GameField(this, level);
        gameField.initGameField();
        view.changeFrame(gameField.getFrame());
    }

    public int getGoal(){return model.getGoal();}
    public int getCarNumbers(){return model.getCarNumbers();}
    public int getCountOfPumps(){return model.getCountOfPumps();}

    public int getCurrentGoal(){return model.getCurrentGoal();}
    public void setCurrentGoal(int goal){model.setCurrentGoal(goal);}

    public int getCurrentThing(){
        return model.getCurrentThing();
    }

    public List<Pump> getPumps(){
        return model.getPumps();
    }

    public void setPumps(List<Pump> pumps){
        model.setPumps(pumps);
    }

    public void changeThing(int count){
        model.changeThing(count);
    }

    public void freezeFrame(boolean isFreeze){
        view.freezeFrame(isFreeze);
    }
}
