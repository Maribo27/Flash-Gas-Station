package controller;

import graphics.GameField;
import graphics.LevelSelection;
import graphics.MainFrame;
import model.GameProgress;
import model.Level;
import model.Parser.XMLFile;
import model.Pump;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

import static graphics.Constants.EXIT_WITH_ERROR;

/**
 * Created by Maria on 09.09.2017.
 */
public class GasStation {
    private static final String DB_LEVEL_INFORMATION_XML = "db\\levelInformation.xml";

    private MainFrame view;

    private GameProgress model;

    public GasStation(){
        view = new MainFrame(this);
        model = new GameProgress();
        new XMLFile(DB_LEVEL_INFORMATION_XML, this).readFile();
    }

    public void showLevelScreen(){
        model.nullBalance();
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

    public void levelUp(){
        model.levelUp();

        if (model.getCurrentLevel() > model.getSavedLevel()) {
            setSavedLevel(model.getCurrentLevel());
            try {
                new XMLFile(DB_LEVEL_INFORMATION_XML, this).writeFile();
            } catch (IOException | TransformerException | ParserConfigurationException e) {
                System.out.println("Невозможно прочитать базу данных, приложение будет закрыто.");
                System.exit(EXIT_WITH_ERROR);
            }
        }
    }

    public void newGame(){
        model.nullBalance();
        model.setLevel(1);
        setSavedLevel(1);
        try {
            new XMLFile(DB_LEVEL_INFORMATION_XML, this).writeFile();
        } catch (IOException | TransformerException | ParserConfigurationException e) {
            System.out.println("Невозможно прочитать базу данных, приложение будет закрыто");
            System.exit(-1);
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

    public void createLevels(List<Level> levels){
        model.createLevels(levels);
    }
    public List<Level> getLevels(){
        return model.getLevels();
    }

    public int getLevelNumbers() {
        return model.getLevelNumbers();
    }

    public Level getNextLevel(){
        return model.getNextLevel();
    }
}
