package controller;

import graphics.GameField;
import graphics.frames.LevelSelection;
import graphics.frames.MainFrame;
import model.GameProgress;
import model.Pump;
import model.XMLParser.XMLFile;

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

    public void showLevelScreen(){
        new XMLFile("db\\level.xml", this).readFile();
        LevelSelection levelSelection = new LevelSelection(model.getSavedLevel(), this);
        view.changeFrame(levelSelection.getLevelPanel());
    }

    public void startGame(int level){
        model.setLevel(level);
        GameField gameField = new GameField(this);
        gameField.initGameField();
        view.changeFrame(gameField.getFrame());
    }

    public int getGoal(){return model.getGoal();}
    public int getCarNumbers(){return model.getCarNumbers();}
    public int getCountOfPumps(){return model.getCountOfPumps();}

    public void setSavedLevel(int level){
        model.setSavedLevel(level);
    }
    public int getCurrentLevel(){
        return model.getCurrentLevel();
    }
    public void changeLevel(int level){
        model.nullBalance();
        model.setLevel(level);

        if (level > model.getSavedLevel()) {
            try {
                new XMLFile("db\\level.xml", this).writeFile();
            } catch (IOException | TransformerException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
    }


    public int getSpeed(){return model.getSpeed();}
    public int getCoefficient(){return model.getCoefficient();}

    public int getBalance(){return model.getBalance();}
    public void setBalance(int goal){model.setBalance(goal);}


    public List<Pump> getPumps(){
        return model.getPumps();
    }
    public void setPumps(List<Pump> pumps){
        model.setPumps(pumps);
    }

    public int getThingInHand(){
        return model.getThingInHand();
    }
    public void setThingInHand(int count){
        model.setThingInHand(count);
    }

    public void freezeFrame(boolean isFreeze){
        view.freezeFrame(isFreeze);
    }
    public void disposeFrame(){
        view.disposeFrame();
    }
}
