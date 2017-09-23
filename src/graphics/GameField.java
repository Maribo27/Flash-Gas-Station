package graphics;

import controller.GasStation;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Maria on 09.09.2017.
 */
class GameField {
    private JFrame frame = new JFrame("Супергеройская АЗС");
    private GasStation controller;
    private int currentGoal = 0;
    private JPanel shopPanel, mainPanel, topPanel;
    private GameCanvas gameCanvas;
    GameField(GasStation controller){
        this.controller = controller;
        initFrame();
        initPanels();
        gameCanvas = new GameCanvas(controller);
        frame.add(gameCanvas.canvas, BorderLayout.CENTER);
        gameCanvas.start();
        frame.setVisible(true);
    }

    private void initFrame(){
        frame = new JFrame("Супергеройская АЗС");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(1200,700));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setContentPane(new Background(1));
        frame.setIconImage(Images.ICON.getImage());
        Container container1 = frame.getContentPane();
        container1.setLayout(new BorderLayout());
    }

    private void initPanels(){
        JLabel levelLabel, goalLabel, carsLabel;
        JButton pauseButton, batarang, steeringWheel, fastFood, coffee;

        //панель очереди машин
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setSize(new Dimension(150,675));
        mainPanel.setPreferredSize(new Dimension(150,675));
        mainPanel.setMinimumSize(new Dimension(150,675));
        mainPanel.add(new JLabel("Очередь машин:"));
        mainPanel.setOpaque(false);

        JPanel carsPanel = new JPanel();
        carsPanel.setLayout(new BoxLayout(carsPanel, BoxLayout.Y_AXIS));
        for (int counterOfCarsButton = 0; counterOfCarsButton < controller.getCarNumbers(); counterOfCarsButton++) {
            carsPanel.add(new JButton(counterOfCarsButton + "", Images.CAR));
        }
        for (int counter = 0; counter < carsPanel.getComponentCount(); counter++)
        {
            carsPanel.getComponent(counter).setEnabled(false);
            ((JButton)carsPanel.getComponent(counter)).setContentAreaFilled(false);
        }

        carsPanel.setOpaque(false);
        JScrollPane scrollCarPanel = new JScrollPane(carsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollCarPanel.setOpaque(false);
        scrollCarPanel.getViewport().setOpaque(false);
        mainPanel.add(scrollCarPanel);


        //панель уровня
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setSize(new Dimension(1024,150));

        levelLabel = new JLabel("Уровень: " + controller.getLevel());
        goalLabel = new JLabel("Цель: " + currentGoal + "/" + controller.getGoal() + "$");
        carsLabel = new JLabel("Осталось машин: " + controller.getCarNumbers());

        pauseButton = new JButton("Пауза");
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
        topPanel.setOpaque(false);

        //панель магазина
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

        frame.add(mainPanel, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(shopPanel, BorderLayout.WEST);
    }

    JFrame getFrame(){
        return frame;
    }
}
