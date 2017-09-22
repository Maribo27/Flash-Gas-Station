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
        model = new GameProgress(this);
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
    public int getCountOfPumps(){return model.getCountOfPumps();}
    public int getFlashSpeed(){
        return model.getFlashSpeed();
    }
    public int getPumpSpeed(){
        return model.getPumpSpeed();
    }
    public int getPatience(){
        return model.getPatience();
    }
    public String getDescription(){return model.getDescription();}
    public void increaseFlashSpeed(){
        model.increaseFlashSpeed();
    }
    public void increasePumpSpeed(){
        model.increasePumpSpeed();
    }
    public void increasePatience(){
        model.increasePatience();
    }
    public void increaseLevel(){
        model.increaseLevel();
    }

    public int getCurrentThing(){
        return model.getCurrentThing();
    }

    public void changeThing(int count){
        model.changeThing(count);
    }
}
