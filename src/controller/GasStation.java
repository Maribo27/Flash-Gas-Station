package controller;

import graphics.GameField;
import graphics.LevelSelection;
import graphics.MainFrame;
import model.GameProgress;
import model.Pump;
import model.XMLParser.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Maria on 09.09.2017.
 */
public class GasStation {
    private MainFrame view;
    private GameProgress model;

    public GasStation(){
        view = new MainFrame(this);
        model = new GameProgress();
    }

    public void disposeFrame(){
        view.disposeFrame();
    }

    public void showLevelScreen(){
        new XMLFile("db\\level.xml", this).readFile();
        LevelSelection levelSelection = new LevelSelection(model.getCurrentLevel(), this);
        view.changeFrame(levelSelection.getLevelPanel());
    }

    public void startGame(int level){
        model.setLevel(level);
        GameField gameField = new GameField(this, level);
        gameField.initGameField();
        view.changeFrame(gameField.getFrame());
    }

    public void changeLevel(int level){
        try {
            new XMLFile("db\\level.xml", this).writeFile();
        } catch (IOException | TransformerException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        model.nullBalance();
        model.setLevel(level);
    }

    public int getGoal(){return model.getGoal();}
    public int getCarNumbers(){return model.getCarNumbers();}
    public int getCountOfPumps(){return model.getCountOfPumps();}

    public int getCurrentLevel(){
        return model.getCurrentLevel();
    }

    public int getSpeed(){return model.getSpeed();}
    public int getCoefficient(){return model.getCoefficient();}

    public int getBalance(){return model.getBalance();}
    public void setBalance(int goal){model.setBalance(goal);}

    public int getThingInHand(){
        return model.getThingInHand();
    }

    public List<Pump> getPumps(){
        return model.getPumps();
    }

    public void setPumps(List<Pump> pumps){
        model.setPumps(pumps);
    }

    public void setThingInHand(int count){
        model.setThingInHand(count);
    }

    public void freezeFrame(boolean isFreeze){
        view.freezeFrame(isFreeze);
    }
}
