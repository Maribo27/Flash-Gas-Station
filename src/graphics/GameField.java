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
    GameField(GasStation controller){
        this.controller = controller;
        frame = new JFrame("Gas Station");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(1200,1024));
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setSize(new Dimension(176,1024));
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
        shopPanel.setSize(new Dimension(150,1024));
        shopPanel.setPreferredSize(new Dimension(150,1024));
        shopPanel.setMinimumSize(new Dimension(150,1024));
        shopPanel.add(new JLabel("Магазин:"));

        frame.setContentPane(new Background(1));
        Container container1 = frame.getContentPane();
        container1.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(shopPanel, BorderLayout.WEST);
        frame.setVisible(true);
    }

    JFrame getFrame(){
        return frame;
    }
}
