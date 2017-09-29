package graphics;

import controller.GasStation;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class LevelSelection {
    private int currentLevel;
    private GasStation controller;
    private JPanel levelPanel;

    public LevelSelection(int currentLevel, GasStation controller){
        this.controller = controller;
        this.currentLevel = currentLevel;
    }
    public JPanel getLevelPanel(){
        levelPanel = new Background(Images.GAME_BACKGROUND);
        levelPanel.setLayout(new GridLayout(2,5));
        levelPanel.setSize(new Dimension(1200,700));
        initLevels();
        return levelPanel;
    }

    private void initLevels(){
        Map<Integer, JButton> levels = new HashMap<>();
        int []buttonNames = {1,2,3,4,5,6,7,8,9,10};
        for (int name : buttonNames){
            JButton button = new JButton("" + name);
            button.setEnabled(false);
            button.setContentAreaFilled(false);
            levels.put(name, button);
            button.addActionListener(e -> controller.startGame(name));
            levelPanel.add(button);
        }

        for (int level = 1; level <= currentLevel; level++){
            JButton button = levels.get(level);
            button.setEnabled(true);
        }
    }
}
