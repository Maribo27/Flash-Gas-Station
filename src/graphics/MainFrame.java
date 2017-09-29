package graphics;

import controller.GasStation;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Maria on 09.09.2017.
 */
public class MainFrame {
    private JFrame frame = new JFrame("Супергеройская АЗС");
    private JPanel framePanel = new JPanel();

    public MainFrame(GasStation controller){
        JButton startGame = new JButton("Старт");
        JButton help = new JButton("Помощь");
        JButton exit = new JButton("Выход");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(565,825));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setIconImage(Images.ICON.getImage());

        framePanel = new Background(Images.MAIN_BACKGROUND);
        frame.add(framePanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(startGame);
        buttonPanel.add(help);
        buttonPanel.add(exit);
        buttonPanel.setOpaque(false);

        framePanel.add(buttonPanel);
        frame.setVisible(true);

        startGame.addActionListener(e -> controller.showLevelScreen());

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
    }

    public void changeFrame(JPanel newPanel){
        frame.remove(framePanel);
        framePanel = newPanel;
        frame.add(framePanel);
        frame.setSize(new Dimension(newPanel.getWidth(), newPanel.getHeight()));
        frame.setLocationRelativeTo(null);
        frame.repaint();
        frame.revalidate();
    }
}
