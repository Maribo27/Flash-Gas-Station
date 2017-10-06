package graphics;

import controller.GasStation;
import graphics.Images.Images;
import graphics.Images.Thing;
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
    private int timeCount = 0;

    private boolean isDelete = false;

    private Timer timer;

    private JFrame pauseFrame;

    private List<Pump> pumpList;
    private List<Car> carList = new ArrayList<>();


    private JLabel goalLabel;
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

        JScrollPane scrollCarPanel = new JScrollPane(carsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Map<Integer, JButton> cars = new HashMap<>();

        int []carCounter = new int[controller.getCarNumbers()];
        for (int car = 0; car < controller.getCarNumbers(); car++){
            carCounter[car] = car;
        }
        for (int car : carCounter){
            JButton button = new JButton("" + car, Images.CAR);
            button.setEnabled(true);
            button.setContentAreaFilled(false);

            cars.put(car, button);

            button.addActionListener(e -> {
                pumpList = controller.getPumps();
                for (Pump pump : pumpList){
                    if (pump.isFree()) {
                        pump.setFree(false);
                        pump.initCar(carList.get(car).getPatience());
                        carList.remove(car);
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
            if (timeCount < controller.getCarNumbers() && timer.getDelay() % 5000 == 0) {
                carList.add(new Car());
                carsPanel.add(cars.get(timeCount));
                carsPanel.revalidate();
                timeCount++;
            }

            if (carList.size() != 0){
                for (Car car : carList) {
                    if (car.getPatience() > 0) {
                        car.setPatience();
                    }
                }
            }

            pumpList = controller.getPumps();

            for (Pump pump : pumpList){
                if (!pump.isFree()){
                    if (pump.getPatience() > 0) {
                        pump.setPatience();
                    }
                    else {
                        pump.setFree(true);
                        updateState();
                        continue;
                    }
                    if (pump.getProgress() < 100){
                        pump.setProgress();
                    }
                    else {
                        pump.nullProgress();
                        controller.setCurrentGoal(pump.getPatience());
                        pump.setFree(true);
                        updateState();
                    }
                }
            }
            controller.setPumps(pumpList);
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
        goalLabel = new JLabel("Цель: " + controller.getCurrentGoal() + "/" + controller.getGoal() + "$");

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
        topPanel.remove(pauseButton);

        goalLabel = new JLabel("Цель: " + controller.getCurrentGoal() + "/" + controller.getGoal() + "$");
        topPanel.add(goalLabel);
        topPanel.add(pauseButton);
        topPanel.repaint();
        topPanel.revalidate();
        frame.repaint();
        frame.revalidate();
    }
}
