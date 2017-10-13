package graphics;

import controller.GasStation;
import graphics.drawImages.Background;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Maria on 09.09.2017.
 */
public class MainFrame {
    private JFrame frame;
    private JPanel framePanel;

    private static final int MAIN_MENU_WIDTH = 565;
    private static final int MAIN_MENU_HEIGHT = 825;
    private static final int HELP_BORDER = 300;

    private final static ImageIcon ICON = new ImageIcon("pics\\icon.png");
    private final static String MAIN_BACKGROUND = "pics\\main.png";
    private final static ImageIcon START_GAME = new ImageIcon("pics\\buttonStart.png");
    private final static ImageIcon NEW_GAME = new ImageIcon("pics\\buttonNew.png");
    private final static ImageIcon HELP = new ImageIcon("pics\\buttonHelp.png");
    private final static ImageIcon EXIT_GAME = new ImageIcon("pics\\buttonExit.png");

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
        frame.setIconImage(ICON.getImage());

        JButton startGame = new JButton(START_GAME);
        startGame.setToolTipText("Старт");
        startGame.addActionListener(e -> controller.showLevelScreen());
        startGame.setContentAreaFilled(false);

        JButton newGame = new JButton(NEW_GAME);
        newGame.setToolTipText("Новая игра");
        newGame.addActionListener(e -> {
            controller.newGame();
            controller.showLevelScreen();
        });
        newGame.setContentAreaFilled(false);

        JButton help = new JButton(HELP);
        help.setContentAreaFilled(false);
        help.setToolTipText("Помощь");
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

        JButton exit = new JButton(EXIT_GAME);
        exit.setToolTipText("Выход");
        exit.setContentAreaFilled(false);
        exit.addActionListener(e -> {
            frame.dispose();
            System.exit(0);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,1,0,3));
        buttonPanel.add(startGame);
        buttonPanel.add(newGame);
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
