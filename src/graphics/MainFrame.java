package graphics;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Maria on 09.09.2017.
 */
public class MainFrame {
    private JFrame frame = new JFrame("Gas Station");

    public MainFrame(){
        JButton startGame = new JButton("Старт");
        JButton exit = new JButton("Выход");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(800,617));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setContentPane(new Background(0));
        Container container = frame.getContentPane();
        container.setLayout(new FlowLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(startGame);
        buttonPanel.add(exit);
        buttonPanel.setOpaque(true);
        frame.add(buttonPanel);

        frame.setVisible(true);

        startGame.addActionListener(e -> {
            frame.dispose();
            GameField newGame = new GameField();
            frame = newGame.getFrame();
        });
        exit.addActionListener(e -> {
            frame.dispose();
            System.exit(0);
        });
    }
}
