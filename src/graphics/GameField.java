package graphics;

import controller.GasStation;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Maria on 09.09.2017.
 */
public class GameField {

    private JPanel frame;
    private JPanel shopPanel;
    private JPanel mainPanel;
    private JPanel topPanel;

    private GasStation controller;

    private GameCanvas gameCanvas;

    private int level;
    private int currentGoal = 0;

    private Timer timer;

    private JDialog pauseFrame;

    public GameField(GasStation controller, int level){
        this.controller = controller;
        this.level = level;
        initFrame();
        initPanels();
        gameCanvas = new GameCanvas(controller);
        frame.add(gameCanvas.canvas, BorderLayout.CENTER);
        gameCanvas.start();
        timer.start();
    }

    private void initFrame(){
        frame = new Background(Images.GAME_BACKGROUND);
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(1200,700));
        frame.setVisible(true);
    }

    private void initStore(){
        JButton batarang;
        JButton steeringWheel;
        JButton fastFood;
        JButton coffee;

        shopPanel = new JPanel();
        shopPanel.setLayout(new FlowLayout());
        shopPanel.setSize(new Dimension(125,675));
        shopPanel.setPreferredSize(new Dimension(125,675));
        shopPanel.setMinimumSize(new Dimension(125,675));
        shopPanel.add(new JLabel("Магазин:"));

        batarang = new JButton(new ImageIcon(Thing.BATARANG));
        batarang.addActionListener(e -> {
            controller.changeThing(1);
            gameCanvas.changePosition(-104);
        });
        batarang.setContentAreaFilled(false);
        steeringWheel = new JButton(new ImageIcon(Thing.STEERING_WHEEL));
        steeringWheel.addActionListener(e -> {
            controller.changeThing(2);
            gameCanvas.changePosition(-104);
        });
        steeringWheel.setContentAreaFilled(false);
        fastFood = new JButton(new ImageIcon(Thing.FASTFOOD));
        fastFood.addActionListener(e -> {
            controller.changeThing(3);
            gameCanvas.changePosition(-104);
        });
        fastFood.setContentAreaFilled(false);
        coffee = new JButton(new ImageIcon(Thing.COFFEE));
        coffee.addActionListener(e -> {
            controller.changeThing(4);
            gameCanvas.changePosition(-104);
        });
        coffee.setContentAreaFilled(false);

        shopPanel.add(batarang);
        shopPanel.add(steeringWheel);
        shopPanel.add(fastFood);
        shopPanel.add(coffee);
        shopPanel.setOpaque(false);
    }
    private void initCars(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setSize(new Dimension(150,675));
        mainPanel.setPreferredSize(new Dimension(150,675));
        mainPanel.setMinimumSize(new Dimension(150,675));
        mainPanel.add(new JLabel("Очередь машин:"));
        mainPanel.setOpaque(false);

        JPanel carsPanel = new JPanel();
        carsPanel.setLayout(new BoxLayout(carsPanel, BoxLayout.Y_AXIS));

        for (int counterOfCarsButton = 0; counterOfCarsButton < controller.getCarNumbers(); counterOfCarsButton++)
            carsPanel.add(new JButton(counterOfCarsButton + "", Images.CAR));

        for (int counter = 0; counter < carsPanel.getComponentCount(); counter++)
        {
            carsPanel.getComponent(counter).setEnabled(false);
            ((JButton)carsPanel.getComponent(counter)).setContentAreaFilled(false);
        }

        timer = new Timer(5000, e -> {
            for (int counter = 0; counter < carsPanel.getComponentCount(); counter++)
            {
                if (!carsPanel.getComponent(counter).isEnabled()){
                    carsPanel.getComponent(counter).setEnabled(true);
                    break;
                }
                if (counter == controller.getCarNumbers())  timer.stop();
            }
        });



        carsPanel.setOpaque(false);
        JScrollPane scrollCarPanel = new JScrollPane(carsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollCarPanel.setOpaque(false);
        scrollCarPanel.getViewport().setOpaque(false);
        mainPanel.add(scrollCarPanel);
    }
    private void initInformation(){
        JLabel levelLabel;
        JLabel goalLabel;
        JButton pauseButton;

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setSize(new Dimension(1024,150));

        levelLabel = new JLabel("Уровень: " + level);
        goalLabel = new JLabel("Цель: " + currentGoal + "/" + controller.getGoal() + "$");

        pauseButton = new JButton("Пауза");
        pauseButton.addActionListener(e -> {
            pauseFrame = new JDialog(new JFrame(), "Пауза", true);
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
        topPanel.add(pauseButton);
        topPanel.setOpaque(false);
    }

    private void initPanels(){
        initCars();
        initInformation();
        initStore();
        frame.add(mainPanel, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(shopPanel, BorderLayout.WEST);
    }

    public JPanel getFrame(){
        return frame;
    }
}
