package controller;

import graphics.MainFrame;
import model.GameProgress;

/**
 * Created by Maria on 09.09.2017.
 */
public class GasStation {
    private MainFrame view;
    private GameProgress model;
    public GasStation(){
        view = new MainFrame(this);
        model = new GameProgress(1, 12, 200, this);
    }

    public int getLevel(){
        return model.getLevel();
    }
    public int getGoal(){
        return model.getGoal();
    }
    public int getCarNumbers(){
        return model.getCarNumbers();
    }
    public void increaseLevel(){
        model.increaseLevel();
    }
}
