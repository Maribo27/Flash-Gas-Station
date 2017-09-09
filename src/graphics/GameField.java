package graphics;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Maria on 09.09.2017.
 */
class GameField {
    private JFrame frame = new JFrame("Gas Station");
    GameField(){
        frame = new JFrame("Gas Station");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(1200,1024));
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setSize(new Dimension(176,1024));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setSize(new Dimension(1024,100));

        JLabel goalLabel = new JLabel("GOAL: 0/1000$");
        JLabel carsLabel = new JLabel("CARS: 0/10");
        JButton pauseButton = new JButton("PAUSE");

        pauseButton.addActionListener(e -> {
            JDialog pauseFrame = new JDialog(frame, "PAUSE", true);
            JLabel pauseLabel = new JLabel("PAUSE");
            pauseFrame.add(pauseLabel);
            pauseFrame.setSize(100,100);
            pauseFrame.setLocationRelativeTo(null);
            pauseFrame.setAlwaysOnTop(true);
            pauseFrame.setFocusable(true);
            pauseFrame.setVisible(true);
            pauseFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        });

        topPanel.add(goalLabel);
        topPanel.add(carsLabel);
        topPanel.add(pauseButton);

        frame.setContentPane(new Background(1));
        Container container1 = frame.getContentPane();
        container1.setLayout(new FlowLayout());
        frame.add(mainPanel, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    JFrame getFrame(){
        return frame;
    }
}
