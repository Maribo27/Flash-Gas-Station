package graphics;

import controller.GasStation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

/**
 * Created by Maria on 11.09.2017.
 */
public class GameCanvas implements Runnable {

	public static final int BATARANG = 1;
	public static final int STEERING_WHEEL = 2;
	public static final int FASTFOOD = 3;
	public static final int COFFEE = 4;
	private GasStation controller;
	Canvas canvas = new Canvas();
	private int newPosition;
	private int things;
	private boolean isThing = false;
	GameCanvas (GasStation controller){
		this.controller = controller;
	}

	private boolean running;
	private boolean leftPressed = false;

	private static ImageDraw flash, background, rightFlash, pump, thing;

	private static int cord_X = 0;

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
			pump.draw(g, 20 + (cord_X + 20) * count, 230);
		}

        if (leftPressed) flash.draw(g, cord_X, Images.CORD_Y);
        else rightFlash.draw(g, cord_X, Images.CORD_Y);

		if (isThing) thing.draw(g, cord_X, Images.CORD_Y - 10);

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
			if (things != 0) isThing = true;
			else isThing = false;
		}
	}

	private ImageDraw getImage(String path) {
		BufferedImage sourceImage = null;

		try {
			sourceImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

        //assert sourceImage != null;

        return new ImageDraw(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
	}

	private class MouseInputAdapter extends MouseAdapter {
	    public void mouseClicked(MouseEvent event) {
	        boolean smallCord = event.getX() > cord_X && event.getX() < cord_X + flash.getWidth();

			if (smallCord) return;
			newPosition = event.getX();
			if (newPosition != cord_X) newPosition -= flash.getWidth() / 2;
        }
    }

    void changePosition(int newCord){
		newPosition = newCord;
		things = 0;
		if (newPosition > cord_X) newPosition -= flash.getWidth();
		render();
		update();
	}
}
