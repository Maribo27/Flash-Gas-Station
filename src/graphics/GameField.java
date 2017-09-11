package graphics;

import controller.GasStation;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Maria on 09.09.2017.
 */
class GameField {
    private JFrame frame = new JFrame("Gas Station");
    private GasStation controller;
    private int currentGoal = 0;
    private Game game = new Game();
    GameField(GasStation controller){
        this.controller = controller;
        frame = new JFrame("Gas Station");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(1200,675));
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setSize(new Dimension(150,675));
        mainPanel.setPreferredSize(new Dimension(150,675));
        mainPanel.setMinimumSize(new Dimension(150,675));
        mainPanel.add(new JLabel("Очередь машин:"));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setSize(new Dimension(1024,150));

        JLabel levelLabel = new JLabel("Уровень: " + controller.getLevel());
        JLabel goalLabel = new JLabel("Цель: " + currentGoal + "/" + controller.getGoal() + "$");
        JLabel carsLabel = new JLabel("Осталось машин: " + controller.getCarNumbers());
        JButton pauseButton = new JButton("Пауза");

        pauseButton.addActionListener(e -> {
            JDialog pauseFrame = new JDialog(frame, "Пауза", true);
            JLabel pauseLabel = new JLabel("Пауза");
            pauseFrame.add(pauseLabel);
            pauseFrame.setSize(100,100);
            pauseFrame.setLocationRelativeTo(null);
            pauseFrame.setAlwaysOnTop(true);
            pauseFrame.setFocusable(true);
            pauseFrame.setVisible(true);
            pauseFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        });

        topPanel.add(levelLabel);
        topPanel.add(goalLabel);
        topPanel.add(carsLabel);
        topPanel.add(pauseButton);

        JPanel shopPanel = new JPanel();
        shopPanel.setLayout(new FlowLayout());
        shopPanel.setSize(new Dimension(125,675));
        shopPanel.setPreferredSize(new Dimension(125,675));
        shopPanel.setMinimumSize(new Dimension(125,675));
        shopPanel.add(new JLabel("Магазин:"));

        frame.setContentPane(new Background(1));
        Container container1 = frame.getContentPane();
        container1.setLayout(new BorderLayout());

        JButton thing1, thing2, food1, food2;

        thing1 = new JButton(Images.THING1);
        thing2 = new JButton(Images.THING2);
        food1 = new JButton(Images.FOOD1);
        food2 = new JButton(Images.FOOD2);

        shopPanel.add(thing1);
        shopPanel.add(thing2);
        shopPanel.add(food1);
        shopPanel.add(food2);


        mainPanel.setOpaque(false);
        topPanel.setOpaque(false);
        shopPanel.setOpaque(false);
        frame.add(mainPanel, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(shopPanel, BorderLayout.WEST);

        ImageIcon flash = Images.PERSON;

        game.canvas.setPreferredSize(new Dimension(flash.getIconWidth(), flash.getIconHeight()));
        frame.add(game.canvas, BorderLayout.SOUTH);
        game.start();
        frame.setVisible(true);
    }

    JFrame getFrame(){
        return frame;
    }
}
