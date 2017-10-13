package graphics;

import controller.GasStation;
import graphics.drawImages.Background;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class LevelSelection {

    private int currentLevel;
    private GasStation controller;
    private JPanel levelPanel;

    private static final int LEVEL_PANEL_WIDTH = 1200;
    private static final int LEVEL_PANEL_HEIGHT = 700;
    private final static Font BIG_FONT = new Font("TimesRoman", Font.BOLD, 30);



    public LevelSelection(int currentLevel, GasStation controller){
        this.controller = controller;
        this.currentLevel = currentLevel;
    }
    public JPanel getLevelPanel(){
        levelPanel = new Background(Constants.GAME_BACKGROUND);
        int columns = controller.getLevelNumbers() / 2 + controller.getLevelNumbers() % 2;
        levelPanel.setLayout(new GridLayout(2,columns));
        levelPanel.setSize(new Dimension(LEVEL_PANEL_WIDTH, LEVEL_PANEL_HEIGHT));
        initLevels();
        return levelPanel;
    }

    private void initLevels(){

        Map<Integer, JButton> levels = new HashMap<>();
        int levelNumber = 1;
        while (levelNumber <= controller.getLevelNumbers()){
            JButton button = new JButton("" + levelNumber);
            button.setFont(BIG_FONT);
            button.setEnabled(false);
            button.setContentAreaFilled(false);
            levels.put(levelNumber, button);
            int finalLevelNumber = levelNumber;
            button.addActionListener(e -> controller.startGame(finalLevelNumber));
            levelPanel.add(button);
            levelNumber++;
        }

        JButton mainMenu = new JButton("В главное меню");
        mainMenu.setFont(BIG_FONT);
        mainMenu.setContentAreaFilled(false);
        mainMenu.addActionListener(e -> {
            controller.disposeFrame();
            controller = new GasStation();
        });
        levelPanel.add(mainMenu);

        for (int level = 1; level <= currentLevel; level++){
            JButton button = levels.get(level);
            button.setEnabled(true);
        }
    }
}
