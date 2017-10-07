package graphics;

import controller.GasStation;
import graphics.Consts.Consts;
import graphics.Consts.Thing;
import model.Pump;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static graphics.Consts.Consts.*;

/**
 * Created by Maria on 11.09.2017.
 */
public class GameCanvas implements Runnable {

	private GasStation controller;

	Canvas canvas = new Canvas();

	private int newPosition;
    private int cord_X = 0;

    private List<Integer> coordsOfPumps = new ArrayList<>();

	private boolean isThing = false;
	private boolean running;
	private boolean leftPressed = false;

	private static ImageDraw flash;
    private static ImageDraw background;
    private static ImageDraw rightFlash;
    private static ImageDraw pump;
	private static ImageDraw car;

	private int thingInHand;

    private Map<Integer, ImageDraw> things = new HashMap<>();
    {
        things.put(BATARANG, getImage(Thing.BATARANG));
        things.put(STEERING_WHEEL, getImage(Thing.STEERING_WHEEL));
        things.put(FASTFOOD, getImage(Thing.FASTFOOD));
        things.put(COFFEE, getImage(Thing.COFFEE));
    }

	GameCanvas (GasStation controller){
		this.controller = controller;
	}

	void start() {
		newPosition = cord_X;
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
		flash = getImage(Consts.FLASH);
		rightFlash = getImage(Consts.FLASH_RIGHT);
        background = getImage(Consts.BACK_PANEL);
        pump = getImage(Consts.PUMP.toString());
        car = getImage(Consts.CAR.toString());
		for (int count = 0; count < 5; count++){
			coordsOfPumps.add(20 + (60 + pump.getWidth()) * count);
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
			pump.draw(g, coordsOfPumps.get(count), 230);
		}

		List<Pump> pumpList = controller.getPumps();

		for (int countPump = 0; countPump < pumpList.size(); countPump++){
			if (!pumpList.get(countPump).isFree()) {
				car.draw(g, 20 + (60 + car.getWidth()) * countPump, 430);
				if (!pumpList.get(countPump).isHaveThing()) {
                    things.get(pumpList.get(countPump).getThingToBuy()).draw(g, 20 + (60 + car.getWidth()) * countPump, 400);
                }
			}

		}

        if (leftPressed) {
            flash.draw(g, cord_X, Consts.CORD_Y);
        } else {
            rightFlash.draw(g, cord_X, Consts.CORD_Y);
        }

		if (isThing) {
            things.get(thingInHand).draw(g, cord_X, Consts.CORD_Y + 50);
        }
        for (int pumpNumber = 0; pumpNumber < controller.getCountOfPumps(); pumpNumber++){
            boolean pumpPressed = newPosition > coordsOfPumps.get(pumpNumber) && cord_X > coordsOfPumps.get(pumpNumber) &&
                    newPosition < coordsOfPumps.get(pumpNumber) + pump.getWidth() && cord_X < coordsOfPumps.get(pumpNumber) + pump.getWidth() &&
                    controller.getThingInHand() == pumpList.get(pumpNumber).getThingToBuy();

            if (pumpPressed){
                isThing = false;
                pumpList.get(pumpNumber).setHaveThing();
                controller.setPumps(pumpList);
            }
        }

		g.dispose();
		bs.show();
	}
	
	private void update() {
		if (newPosition < cord_X && cord_X > - flash.getWidth()) {
			cord_X--;
			leftPressed = true;
		}
		if (newPosition > cord_X && cord_X < canvas.getWidth() - flash.getWidth()) {
			cord_X++;
			leftPressed = false;
		}
		if (cord_X == - flash.getWidth()) {
			thingInHand = controller.getThingInHand();
			isThing = thingInHand != 0;
		}
	}

	private ImageDraw getImage(String path) {
		BufferedImage sourceImage = null;

		try {
			sourceImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (sourceImage != null) {
			return new ImageDraw(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
		} else {
            return null;
        }
	}

	private class MouseInputAdapter extends MouseAdapter {
	    public void mouseClicked(MouseEvent event) {

	        boolean smallCord = event.getX() > cord_X && event.getX() < cord_X + flash.getWidth();

			if (smallCord) {
                return;
            }

			newPosition = event.getX();

			if (newPosition != cord_X) {
                newPosition -= flash.getWidth() / 2;
            }
        }
    }

    void changePosition(int newCord){
		newPosition = newCord;
		if (newPosition > cord_X) {
            newPosition -= flash.getWidth();
        }
		render();
		update();
	}
}
