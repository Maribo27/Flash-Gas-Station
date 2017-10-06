package graphics;

import controller.GasStation;
import model.Pump;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.List;

/**
 * Created by Maria on 11.09.2017.
 */
public class GameCanvas implements Runnable {

	private static final int BATARANG = 1;
	private static final int STEERING_WHEEL = 2;
	private static final int FASTFOOD = 3;
	private static final int COFFEE = 4;

	private GasStation controller;
	Canvas canvas = new Canvas();

	private int newPosition;
	private int things;
    private int cord_X = 0;

	private boolean isThing = false;
	private boolean running;
	private boolean leftPressed = false;

	private static ImageDraw flash;
    private static ImageDraw background;
    private static ImageDraw rightFlash;
    private static ImageDraw pump;
    private static ImageDraw thing;
	private static ImageDraw car;

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
		flash = getImage(Images.FLASH);
		rightFlash = getImage(Images.FLASH_RIGHT);
        background = getImage(Images.BACK_PANEL);
        pump = getImage(Images.PUMP.toString());
        car = getImage(Images.CAR.toString());
	}
	
	private void render() {
		switch (things){
			case BATARANG:
				thing = getImage(Thing.BATARANG);
				break;
			case STEERING_WHEEL:
				thing = getImage(Thing.STEERING_WHEEL);
				break;
			case FASTFOOD:
				thing = getImage(Thing.FASTFOOD);
				break;
			case COFFEE:
				thing = getImage(Thing.COFFEE);
				break;
		}

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
			pump.draw(g, 20 + (60 + pump.getWidth()) * count, 230);
		}

		List<Pump> pumpList = controller.getPumps();

		for (int countPump = 0; countPump < pumpList.size(); countPump++){
			if (!pumpList.get(countPump).isFree()) {
				car.draw(g, 20 + (60 + car.getWidth()) * countPump, 430);
			}
		}

        if (leftPressed) {
            flash.draw(g, cord_X, Images.CORD_Y);
        } else {
            rightFlash.draw(g, cord_X, Images.CORD_Y);
        }

		if (isThing) {
            thing.draw(g, cord_X, Images.CORD_Y - 10);
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
			things = controller.getCurrentThing();
			isThing = things != 0;
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
		things = 0;
		if (newPosition > cord_X) {
            newPosition -= flash.getWidth();
        }
		render();
		update();
	}
}
