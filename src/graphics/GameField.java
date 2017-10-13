package graphics;

import controller.GasStation;
import graphics.drawImages.Background;
import model.Car;
import model.Pump;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static graphics.Constants.*;

/**
 * Created by Maria on 09.09.2017.
 */
public class GameField {

    private static final int PANEL_WIDTH = 150;
    private static final int EXIT_WITHOUT_ERRORS = 0;
    private static final int END_FRAME_WIDTH = 300;
    private static final int END_FRAME_HEIGHT = 150;
    private JPanel frame;
    private JPanel statusBar;

    private GasStation controller;

    private GameCanvas gameCanvas;

    private int level;
    private int seconds = 0;
    private int timeCount = 0;
    private int carServed = 0;

    private boolean delete;
    private boolean ready;

    private Timer timer;

    private JFrame pauseFrame;

    private List<Pump> pumpList;
    private List<Car> carList = new ArrayList<>();

    private Map<Integer, JButton> cars;

    private JLabel goalLabel;
    private JLabel carLabel;

    private JButton pauseButton;

    private static final int STORE_COORDINATES = -104;
    private static final int STATUS_BAR_HEIGHT = 150;
    private static final int PANEL_HEIGHT = 675;
    private static final int STATUS_BAR_WIDTH = 1024;
    private final static Font BIG_FONT = new Font("TimesRoman", Font.BOLD, 14);
    
    public GameField(GasStation controller) {
        this.controller = controller;
        this.level = controller.getCurrentLevel();
    }

    public void initGameField() {
        this.frame = initPanels();
        gameCanvas = new GameCanvas(controller);
        frame.add(gameCanvas.canvas, BorderLayout.CENTER);
        gameCanvas.start();
        timer.start();
    }

    private JPanel initPanels() {
        JPanel frame = new Background(GAME_BACKGROUND);
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(1200, 700));
        frame.setVisible(true);
        
        // инициация магазина
        
        JButton batarang;
        JButton steeringWheel;
        JButton fastFood;
        JButton coffee;

        JPanel shopPanel = new JPanel();
        shopPanel.setLayout(new FlowLayout());
        shopPanel.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        shopPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        shopPanel.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        JLabel store = new JLabel("Магазин:");
        store.setFont(BIG_FONT);
        shopPanel.add(store);

        batarang = new JButton(new ImageIcon(Constants.BATARANG));
        batarang.addActionListener(e -> {
            controller.setThingInHand(Things.BATARANG);
            gameCanvas.changePosition(STORE_COORDINATES);
        });
        batarang.setContentAreaFilled(false);
        steeringWheel = new JButton(new ImageIcon(Constants.STEERING_WHEEL));
        steeringWheel.addActionListener(e -> {
            controller.setThingInHand(Things.STEERING_WHEEL);
            gameCanvas.changePosition(STORE_COORDINATES);
        });
        steeringWheel.setContentAreaFilled(false);
        fastFood = new JButton(new ImageIcon(Constants.FASTFOOD));
        fastFood.addActionListener(e -> {
            controller.setThingInHand(Things.FASTFOOD);
            gameCanvas.changePosition(STORE_COORDINATES);
        });
        fastFood.setContentAreaFilled(false);
        coffee = new JButton(new ImageIcon(Constants.COFFEE));
        coffee.addActionListener(e -> {
            controller.setThingInHand(Things.COFFEE);
            gameCanvas.changePosition(STORE_COORDINATES);
        });
        coffee.setContentAreaFilled(false);

        shopPanel.add(batarang);
        shopPanel.add(steeringWheel);
        shopPanel.add(fastFood);
        shopPanel.add(coffee);
        shopPanel.setOpaque(false);

        // инициация машин

        JPanel carPanel = new JPanel();
        carPanel.setLayout(new BoxLayout(carPanel, BoxLayout.Y_AXIS));
        carPanel.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        carPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        carPanel.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        JLabel carQueue = new JLabel("Очередь машин:");
        carQueue.setFont(BIG_FONT);
        carPanel.add(carQueue);
        carPanel.setOpaque(false);

        JPanel carsPanel = new JPanel();
        carsPanel.setLayout(new BoxLayout(carsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollCarPanel = new JScrollPane(carsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        cars = new HashMap<>();

        int[] carCounter = new int[controller.getCarNumbers()];
        for (int car = 0; car < controller.getCarNumbers(); car++) {
            carCounter[car] = car;
        }

        for (int car : carCounter) {
            JButton button = new JButton("" + car, CAR);
            button.setEnabled(true);
            button.setContentAreaFilled(false);

            cars.put(car, button);

            button.addActionListener(e -> {
                pumpList = controller.getPumps();
                for (Pump pump : pumpList) {
                    if (pump.isFree()) {
                        pump.setFree(false);
                        pump.initCar(carList.get(car).getPatience());
                        carServed++;
                        updateState();
                        delete = true;
                        break;
                    }
                }

                if (delete) {
                    carsPanel.remove(button);
                    carsPanel.repaint();
                    carsPanel.revalidate();
                    cars.remove(Integer.parseInt(button.getText()));
                    delete = false;
                }
            });
        }

        // инициация таймера

        timer = new Timer(1000, e -> {
            seconds++;

            boolean canAddCar = timeCount < controller.getCarNumbers() && (seconds % controller.getSpeed() == 0 || seconds == 1);
            if (canAddCar) {
                carList.add(new Car());
                carsPanel.add(cars.get(timeCount));
                carsPanel.revalidate();
                timeCount++;
            }

            if (carList.size() != 0) {
                for (Car car : carList) {
                    if (car.getPatience() > 0) {
                        car.setPatience(controller.getCoefficient());
                    }
                }
            }

            pumpList = controller.getPumps();
            ready = true;
            for (Pump pump : pumpList) {
                if (!pump.isFree()) {
                    ready = false;
                    if (pump.getPayment() > 0) {
                        pump.reducePayment();
                    } else {
                        pump.setFree(true);
                        updateState();
                        continue;
                    }
                    if (pump.getProgress() < 100) {
                        pump.increaseProgress();
                        ready = false;
                    } else {
                        pump.nullProgress();
                        controller.setBalance(pump.getPayment());
                        pump.setFree(true);
                        updateState();
                        ready = true;
                    }
                }
            }
            controller.setPumps(pumpList);

            boolean levelStop = cars.size() == 0 && ready;
            if (levelStop) {
                timer.stop();
                String state;
                boolean endGame = false;

                boolean levelComplete = controller.getBalance() > controller.getGoal();
                if (levelComplete) {
                    state = "О да, Флэш быстр как никогда";
                    if (controller.getNextLevel() == null){
                        state = "<html>Еее, Флэш, молодец, но дальше двигаться <br> некуда, прости <br> (и не трогай таймлайн)</html>";
                        endGame = true;
                    }
                    levelEnd(state, endGame);
                } else {
                    levelFail();
                }
            }
        });

        carsPanel.setOpaque(false);
        scrollCarPanel.setOpaque(false);
        scrollCarPanel.getViewport().setOpaque(false);
        carPanel.add(scrollCarPanel);

        // инициация статус бара

        JLabel levelLabel;

        statusBar = new JPanel();
        statusBar.setLayout(new FlowLayout());
        statusBar.setSize(new Dimension(STATUS_BAR_WIDTH, STATUS_BAR_HEIGHT));

        levelLabel = new JLabel("Уровень: " + level);
        goalLabel = new JLabel("Цель: " + controller.getBalance() + "/" + controller.getGoal() + "$");
        carLabel = new JLabel("Машин прошло: " + carServed + "/" + controller.getCarNumbers());

        levelLabel.setFont(BIG_FONT);
        goalLabel.setFont(BIG_FONT);
        carLabel.setFont(BIG_FONT);

        pauseButton = new JButton("Пауза");
        pauseButton.addActionListener(e -> {
            controller.freezeFrame(false);
            pauseFrame = new JFrame("Пауза");
            pauseFrame.setLayout(new GridLayout(3,1,0,3));
            timer.stop();
            pauseFrame.setLocationRelativeTo(null);
            pauseFrame.setAlwaysOnTop(true);
            pauseFrame.setVisible(true);
            pauseFrame.setResizable(false);
            JButton okButton = new JButton("Продолжить игру");
            okButton.addActionListener(e1 -> {
                timer.start();
                pauseFrame.dispose();
                controller.freezeFrame(true);
            });
            JButton selectButton = new JButton("К выбору уровней");
            selectButton.addActionListener(e1 -> {
                pauseFrame.dispose();
                controller.freezeFrame(true);
                controller.showLevelScreen();
            });
            JButton exitButton = new JButton("Выход из игры");
            exitButton.addActionListener(e1 -> System.exit(EXIT_WITHOUT_ERRORS));
            pauseFrame.add(okButton);
            pauseFrame.pack();
            pauseFrame.add(selectButton);
            pauseFrame.add(exitButton);
        });

        statusBar.add(levelLabel);
        statusBar.add(goalLabel);
        statusBar.add(pauseButton);
        statusBar.setOpaque(false);

        frame.add(carPanel, BorderLayout.EAST);
        frame.add(statusBar, BorderLayout.NORTH);
        frame.add(shopPanel, BorderLayout.WEST);
        return frame;
    }

    public JPanel getFrame() {
        return frame;
    }

    private void updateState() {
        statusBar.remove(goalLabel);
        statusBar.remove(carLabel);
        statusBar.remove(pauseButton);

        goalLabel = new JLabel("Цель: " + controller.getBalance() + "/" + controller.getGoal() + "$");
        carLabel = new JLabel("Машин прошло: " + carServed + "/" + controller.getCarNumbers());

        goalLabel.setFont(BIG_FONT);
        carLabel.setFont(BIG_FONT);

        statusBar.add(goalLabel);
        statusBar.add(carLabel);
        statusBar.add(pauseButton);
        statusBar.repaint();
        statusBar.revalidate();
        frame.repaint();
        frame.revalidate();
    }

    private void levelEnd(String state, boolean endGame) {
        JFrame endFrame = getEndFrame();
        JLabel information = new JLabel(state);
        JButton okButton = new JButton("ОК");

        if (endGame){
            endFrame.setTitle("Игра закончена");
            okButton.addActionListener(e -> {
                controller.showLevelScreen();
                endFrame.dispose();
            });
        } else {
            okButton.addActionListener(e -> {
                controller.levelUp();
                controller.showLevelScreen();
                endFrame.dispose();
            });
        }
        endFrame.add(information);
        endFrame.add(okButton);
    }

    private void levelFail() {
        JFrame endFrame = getEndFrame();
        JLabel information = new JLabel("Какое огорчение, Флэш, ты не смог");

        JButton okButton = new JButton("ОК");
        okButton.addActionListener(e -> {
                controller.showLevelScreen();
                endFrame.dispose();
            });

        endFrame.add(information);
        endFrame.add(okButton);
    }

    private JFrame getEndFrame(){
        JFrame endFrame = new JFrame("Статистика:");
        endFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        endFrame.setLayout(new GridLayout(2,1));
        endFrame.setSize(new Dimension(END_FRAME_WIDTH, END_FRAME_HEIGHT));
        endFrame.setLocationRelativeTo(null);
        endFrame.setResizable(false);
        endFrame.setAlwaysOnTop(true);
        endFrame.setVisible(true);
        return endFrame;
    }
}
