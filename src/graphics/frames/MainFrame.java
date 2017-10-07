package graphics.frames;

import controller.GasStation;
import graphics.consts.Consts;
import graphics.images.Background;

import javax.swing.*;
import java.awt.*;

import static graphics.consts.Consts.*;

/**
 * Created by Maria on 09.09.2017.
 */
public class MainFrame {
    private JFrame frame;
    private JPanel framePanel;

    public MainFrame(GasStation controller){
        framePanel = new JPanel();
        framePanel = new Background(MAIN_BACKGROUND);

        frame = new JFrame("Супергеройская АЗС");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setIconImage(Consts.ICON.getImage());

        JButton startGame = new JButton("Старт");
        startGame.addActionListener(e -> controller.showLevelScreen());

        JButton help = new JButton("Помощь");
        help.addActionListener(e -> {
            JFrame helpFrame = new JFrame("Помощь");
            JLabel information = new JLabel("<html><b>Здесь будет помощь и об авторе инфа:)</b></html>");
            helpFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            helpFrame.setLayout(new BorderLayout());
            helpFrame.setSize(new Dimension(HELP_BORDER, HELP_BORDER));
            helpFrame.setLocationRelativeTo(null);
            helpFrame.add(information);
            helpFrame.setAlwaysOnTop(true);
            helpFrame.setResizable(false);
            helpFrame.setVisible(true);
        });

        JButton exit = new JButton("Выход");
        exit.addActionListener(e -> {
            frame.dispose();
            System.exit(0);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(startGame);
        buttonPanel.add(help);
        buttonPanel.add(exit);
        buttonPanel.setOpaque(false);

        framePanel.add(buttonPanel);
        frame.add(framePanel);
        frame.setVisible(true);
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

    public void freezeFrame(boolean isFreeze){
        frame.setEnabled(isFreeze);
    }

    public void disposeFrame(){frame.dispose();}
}
