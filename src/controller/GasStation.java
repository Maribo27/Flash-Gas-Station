package controller;

import graphics.GameField;
import graphics.LevelSelection;
import graphics.MainFrame;
import model.GameProgress;

import javax.swing.Timer;


/**
 * Created by Maria on 09.09.2017.
 */
public class GasStation {
    private MainFrame view;
    private GameProgress model;
    private int check = 0;
    private int lastLevel = 1;
    public GasStation(){
        view = new MainFrame(this);
        model = new GameProgress(this);
    }

    public void showLevelScreen(){
        LevelSelection levelSelection = new LevelSelection(lastLevel, this);
        view.changeFrame(levelSelection.getLevelPanel());
    }

    public void startGame(int level){
        model.changeLevel(level);
        GameField gameField = new GameField(this, level);
        view.changeFrame(gameField.getFrame());
    }

    public int getGoal(){return model.getGoal();}
    public int getCarNumbers(){return model.getCarNumbers();}
    public int getCountOfPumps(){return model.getCountOfPumps();}

    public int getCurrentThing(){
        return model.getCurrentThing();
    }

    public void changeThing(int count){
        model.changeThing(count);
    }
}
