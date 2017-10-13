package graphics;

import controller.GasStation;
import graphics.drawImages.ImageDraw;
import model.Pump;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static graphics.Constants.*;

/**
 * Created by Maria on 11.09.2017.
 */
public class GameCanvas implements Runnable {

    private static final int PUMP_Y_LEVEL = 230;
	private static final int CAR_Y_LEVEL = 430;
	private static final int PUMP_BORDER = 60;
	private static final int DIMENSION_BETWEEN_PUMPS = 90;
	private static final int THINGS_Y_LEVEL = 500;
	private static final int ORDERED_THINGS_Y_LEVEL = 250;
	private static final int CORD_Y = 450;
    private final static String FLASH = "pics\\flash.png";
    private final static String FLASH_RIGHT = "pics\\flash_right.png";
    private final static String BACK_PANEL = "pics\\back.png";
    private final static ImageIcon PUMP = new ImageIcon("pics\\pump2.png");
    private static final int INFO_TABLE_WIDTH = 100;
    private static final int INFO_TABLE_HEIGHT = 35;
    private static final int INFO_TABLE_Y = 378;
    private static final int PROGRESS_Y = 410;
    private static final int PAYMENT_Y = 390;

    private GasStation controller;

	Canvas canvas;

	private int newPosition;
    private int currentCordX = 0;

    private List<Integer> pumpsCoordinates = new ArrayList<>();

	private boolean thing;
	private boolean running;
	private boolean leftPressed;

	private ImageDraw flash;
    private ImageDraw background;
    private ImageDraw rightFlash;
    private ImageDraw pump;
	private ImageDraw car;

	private int thingInHand;

    private Map<Integer, ImageDraw> things = new HashMap<>();

    {
        things.put(Things.BATARANG, getImage(Constants.BATARANG));
        things.put(Things.STEERING_WHEEL, getImage(Constants.STEERING_WHEEL));
        things.put(Things.FASTFOOD, getImage(Constants.FASTFOOD));
        things.put(Things.COFFEE, getImage(Constants.COFFEE));
    }

	GameCanvas (GasStation controller){
		this.controller = controller;
        canvas = new Canvas();
	}

	void start() {
		newPosition = currentCordX;
		running = true;
		new Thread(this).start();
	}
	
	public void run() {
		init();
		while(running) {
			render();
			update();
		}
	}
	
	private void init() {
		canvas.addMouseListener(new MouseInputAdapter());
		flash = getImage(FLASH);
		rightFlash = getImage(FLASH_RIGHT);
        background = getImage(BACK_PANEL);
        pump = getImage(PUMP.toString());
        car = getImage(CAR.toString());
		for (int count = 0; count < 5; count++){
			pumpsCoordinates.add(PUMP_BORDER + (DIMENSION_BETWEEN_PUMPS + pump.getWidth()) * count);
		}
	}
	
	private void render() {
		BufferStrategy bs = canvas.getBufferStrategy();

		if (bs == null) {
			canvas.createBufferStrategy(2);
			canvas.requestFocus();
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.clearRect(0,0,canvas.getWidth(), canvas.getHeight());

		background.draw(g, 0, 0);
		for (int count = 0; count < controller.getCountOfPumps(); count++){
			pump.draw(g, pumpsCoordinates.get(count), PUMP_Y_LEVEL);
		}

		List<Pump> pumpList = controller.getPumps();

		for (int countPump = 0; countPump < pumpList.size(); countPump++){
		    if (!pumpList.get(countPump).isFree()) {
		        int tempX = PUMP_BORDER + (DIMENSION_BETWEEN_PUMPS + pump.getWidth()) * countPump;
                int infoTableX = tempX + 60;

                car.draw(g, tempX, CAR_Y_LEVEL);

                boolean haveThing = pumpList.get(countPump).isHaveThing();
                if (!haveThing) {
                    ImageDraw thing = things.get(pumpList.get(countPump).getThingToBuy());
                    thing.draw(g, tempX, ORDERED_THINGS_Y_LEVEL);
                }

                g.setColor(Color.WHITE);
                g.drawRect(infoTableX, INFO_TABLE_Y, INFO_TABLE_WIDTH, INFO_TABLE_HEIGHT);
				g.fillRect(infoTableX, INFO_TABLE_Y, INFO_TABLE_WIDTH, INFO_TABLE_HEIGHT);
                g.setColor(Color.BLACK);

                int progress = pumpList.get(countPump).getProgress();
                g.drawString("Прогресс: " + progress, infoTableX, PROGRESS_Y);

                int payment = pumpList.get(countPump).getPayment();
                g.drawString("Наличные: " + payment + "$", infoTableX, PAYMENT_Y);
			}
		}

        if (leftPressed) {
            flash.draw(g, currentCordX, CORD_Y);
        } else {
            rightFlash.draw(g, currentCordX, CORD_Y);
        }

		if (thing) {
            things.get(thingInHand).draw(g, currentCordX, THINGS_Y_LEVEL);
        }
        for (int pumpNumber = 0; pumpNumber < controller.getCountOfPumps(); pumpNumber++){
            boolean pumpPressed = newPosition > pumpsCoordinates.get(pumpNumber)
                    && currentCordX > pumpsCoordinates.get(pumpNumber)
                    && newPosition < pumpsCoordinates.get(pumpNumber) + pump.getWidth()
                    && currentCordX < pumpsCoordinates.get(pumpNumber) + pump.getWidth()
                    && controller.getThingInHand() == pumpList.get(pumpNumber).getThingToBuy();

            if (pumpPressed){
                thing = false;
                pumpList.get(pumpNumber).giveThing();
                controller.setPumps(pumpList);
            }
        }

		g.dispose();
		bs.show();
	}
	
	private void update() {
        boolean left = newPosition < currentCordX && currentCordX > -flash.getWidth();
        boolean right = newPosition > currentCordX && currentCordX < canvas.getWidth() - flash.getWidth();
        boolean store = currentCordX == -flash.getWidth();

        if (left) {
			currentCordX--;
			leftPressed = true;
		}
        if (right) {
			currentCordX++;
			leftPressed = false;
		}
        if (store) {
			thingInHand = controller.getThingInHand();
			thing = thingInHand != 0;
		}
	}

    void changePosition(int newCord){
        newPosition = newCord;
        if (newPosition > currentCordX) {
            newPosition -= flash.getWidth();
        }
        render();
        update();
    }

	private ImageDraw getImage(String path) {
		BufferedImage sourceImage;

		try {
			sourceImage = ImageIO.read(new File(path));
			return new ImageDraw(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(EXIT_WITH_ERROR);
		}
		return null;
	}

	private class MouseInputAdapter extends MouseAdapter {
	    public void mouseClicked(MouseEvent event) {

	        boolean smallCord = event.getX() > currentCordX && event.getX() < currentCordX + flash.getWidth();

			if (smallCord) {
                return;
            }

			newPosition = event.getX();

			if (newPosition != currentCordX) {
                newPosition -= flash.getWidth() / 2;
            }
        }
    }
}
