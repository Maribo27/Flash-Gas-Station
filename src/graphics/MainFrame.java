package graphics;

import controller.GasStation;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Maria on 09.09.2017.
 */
public class MainFrame {
    private JFrame frame = new JFrame("Супергеройская АЗС");
    private GasStation controller;

    public MainFrame(GasStation controller){
        this.controller = controller;
        JButton startGame = new JButton("Старт");
        JButton help = new JButton("Помощь");
        JButton store = new JButton("Магазин улучшений");
        JButton exit = new JButton("Выход");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(565,825));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setContentPane(new Background(0));
        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(startGame);
        buttonPanel.add(store);
        buttonPanel.add(help);
        buttonPanel.add(exit);
        buttonPanel.setOpaque(false);
        frame.add(buttonPanel, BorderLayout.EAST);
        frame.setVisible(true);

        startGame.addActionListener(e -> {
            frame.dispose();
            GameField newGame = new GameField(controller);
            frame = newGame.getFrame();
        });
        exit.addActionListener(e -> {
            frame.dispose();
            System.exit(0);
        });
        help.addActionListener(e -> {
            JFrame helpFrame = new JFrame("Помощь");
            helpFrame.setLayout(new BorderLayout());
            helpFrame.setSize(new Dimension(100,100));
            helpFrame.setLocationRelativeTo(null);
            helpFrame.setResizable(false);
            helpFrame.setVisible(true);
        });
        store.addActionListener(e -> {
            JFrame storeFrame = new JFrame("Магазин");
            storeFrame.setLayout(new BorderLayout());
            storeFrame.setSize(new Dimension(100,100));
            storeFrame.setLocationRelativeTo(null);
            storeFrame.setResizable(false);
            storeFrame.setVisible(true);
        });
    }
}
