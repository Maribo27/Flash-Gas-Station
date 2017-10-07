package graphics;

import controller.GasStation;
import graphics.Consts.Consts;
import graphics.Consts.Thing;
import model.Car;
import model.Pump;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

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
    private int seconds = 0;
    private int timeCount = 0;
    private int increaseLevel;
    private int carServed = 0;

    private boolean isDelete = false;
    private boolean isReady = false;

    private Timer timer;

    private JFrame pauseFrame;

    private List<Pump> pumpList;
    private List<Car> carList = new ArrayList<>();

    private JLabel goalLabel;
    private JLabel carLabel;

    private JButton pauseButton;




    public GameField(GasStation controller, int level){
        this.controller = controller;
        this.level = level;
    }

    public void initGameField(){
        initFrame();
        initPanels();
        gameCanvas = new GameCanvas(controller);
        frame.add(gameCanvas.canvas, BorderLayout.CENTER);
        gameCanvas.start();
        timer.start();
    }

    private void initFrame(){
        frame = new Background(Consts.GAME_BACKGROUND);
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
            controller.setThingInHand(1);
            gameCanvas.changePosition(-104);
        });
        batarang.setContentAreaFilled(false);
        steeringWheel = new JButton(new ImageIcon(Thing.STEERING_WHEEL));
        steeringWheel.addActionListener(e -> {
            controller.setThingInHand(2);
            gameCanvas.changePosition(-104);
        });
        steeringWheel.setContentAreaFilled(false);
        fastFood = new JButton(new ImageIcon(Thing.FASTFOOD));
        fastFood.addActionListener(e -> {
            controller.setThingInHand(3);
            gameCanvas.changePosition(-104);
        });
        fastFood.setContentAreaFilled(false);
        coffee = new JButton(new ImageIcon(Thing.COFFEE));
        coffee.addActionListener(e -> {
            controller.setThingInHand(4);
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

        JScrollPane scrollCarPanel = new JScrollPane(carsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Map<Integer, JButton> cars = new HashMap<>();

        int []carCounter = new int[controller.getCarNumbers()];
        for (int car = 0; car < controller.getCarNumbers(); car++){
            carCounter[car] = car;
        }
        for (int car : carCounter){
            JButton button = new JButton("" + car, Consts.CAR);
            button.setEnabled(true);
            button.setContentAreaFilled(false);

            cars.put(car, button);

            button.addActionListener(e -> {
                pumpList = controller.getPumps();
                for (Pump pump : pumpList){
                    if (pump.isFree()) {
                        pump.setFree(false);
                        pump.initCar(carList.get(car).getPatience());
                        carServed++;
                        updateState();
                        isDelete = true;
                        break;
                    }
                }

                if (isDelete) {
                    carsPanel.remove(button);
                    carsPanel.repaint();
                    carsPanel.revalidate();
                    cars.remove(Integer.parseInt(button.getText()));
                    isDelete = false;
                }
            });
        }

        timer = new Timer(1000, e -> {
            seconds++;
            if (timeCount < controller.getCarNumbers() && (seconds % controller.getSpeed() == 0 || seconds == 1)) {
                carList.add(new Car());
                carsPanel.add(cars.get(timeCount));
                carsPanel.revalidate();
                timeCount++;
            }

            if (carList.size() != 0){
                for (Car car : carList) {
                    if (car.getPatience() > 0) {
                        car.setPatience(controller.getCoefficient());
                    }
                }
            }

            pumpList = controller.getPumps();
            isReady = true;
            for (Pump pump : pumpList){
                if (!pump.isFree()){
                    isReady = false;
                    if (pump.getPayment() > 0) {
                        pump.reducePayment();
                    }
                    else {
                        pump.setFree(true);
                        updateState();
                        continue;
                    }
                    if (pump.getProgress() < 100){
                        pump.increaseProgress();
                        isReady = false;
                    }
                    else {
                        pump.nullProgress();
                        controller.setBalance(pump.getPayment());
                        pump.setFree(true);
                        updateState();
                        isReady = true;
                    }
                }
            }
            controller.setPumps(pumpList);

            if (cars.size() == 0 && isReady) {
                    timer.stop();
                    String state;

                    if (controller.getBalance() > controller.getGoal()){
                        state = "О да, Флэш быстр как никогда";
                        increaseLevel = 1;
                    } else {
                        state = "Какое огорчение, Флэш, ты не смог";
                        increaseLevel = 0;
                    }
                    levelEnd(state);
                }
        });

        carsPanel.setOpaque(false);
        scrollCarPanel.setOpaque(false);
        scrollCarPanel.getViewport().setOpaque(false);
        mainPanel.add(scrollCarPanel);
    }

    private void initInformation(){
        JLabel levelLabel;

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setSize(new Dimension(1024,150));


        levelLabel = new JLabel("Уровень: " + level);
        goalLabel = new JLabel("Цель: " + controller.getBalance() + "/" + controller.getGoal() + "$");
        carLabel = new JLabel("Машин прошло: " + carServed + "/" + controller.getCarNumbers());

        pauseButton = new JButton("Пауза");
        pauseButton.addActionListener(e -> {
            controller.freezeFrame(false);
            pauseFrame = new JFrame("Пауза");
            timer.stop();
            JLabel pauseLabel = new JLabel("Пауза");
            pauseFrame.add(pauseLabel);
            pauseFrame.setSize(100,100);
            pauseFrame.setLocationRelativeTo(null);
            pauseFrame.setAlwaysOnTop(true);
            pauseFrame.setVisible(true);
            JButton okButton = new JButton("Back");
            okButton.addActionListener(e1 -> {
                timer.start();
                pauseFrame.dispose();
                controller.freezeFrame(true);
            });
            pauseFrame.add(okButton);
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

    private void updateState(){
        topPanel.remove(goalLabel);
        topPanel.remove(carLabel);
        topPanel.remove(pauseButton);

        goalLabel = new JLabel("Цель: " + controller.getBalance() + "/" + controller.getGoal() + "$");
        carLabel = new JLabel("Машин прошло: " + carServed + "/" + controller.getCarNumbers());
        topPanel.add(goalLabel);
        topPanel.add(carLabel);
        topPanel.add(pauseButton);
        topPanel.repaint();
        topPanel.revalidate();
        frame.repaint();
        frame.revalidate();
    }

    private void levelEnd(String state){
        JFrame endFrame;

        endFrame = new JFrame("Конец");
        endFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        endFrame.setLayout(new FlowLayout());
        endFrame.setSize(new Dimension(300,100));
        endFrame.setLocationRelativeTo(null);
        endFrame.setResizable(false);
        endFrame.setAlwaysOnTop(true);
        JLabel information = new JLabel(state);
        JButton okButton = new JButton("okey");
        okButton.addActionListener(e -> {
            controller.changeLevel(controller.getCurrentLevel() + increaseLevel);
            controller.showLevelScreen();
            endFrame.dispose();
        });

        endFrame.add(information);
        endFrame.add(okButton);
        endFrame.setVisible(true);
    }
}
