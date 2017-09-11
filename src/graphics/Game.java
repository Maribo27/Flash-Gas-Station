package graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

/**
 * Created by Maria on 11.09.2017.
 */
public class Game implements Runnable {
	Canvas canvas = new Canvas();

	private boolean running;
	
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	
	private static Flash hero;
	private static int x = 0;
	private static int y = 0;
	
	void start() {
		running = true;
		new Thread(this).start();
	}
	
	public void run() {
		long lastTime = System.currentTimeMillis();
		long delta;
		
		init();
		
		while(running) {
			delta = System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
			render();
			update(delta);
		}
	}
	
	private void init() {
		canvas.addKeyListener(new KeyInputHandler());
		hero = getSprite();
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
		hero.draw(g, x, y);
		g.dispose();
		bs.show();
	}
	
	private void update(long delta) {
		if (leftPressed && x > 0) {
			x--;
		}
		if (rightPressed && x < canvas.getWidth() - hero.getWidth()) {
			x++;
		}
	}

	private Flash getSprite() {
		BufferedImage sourceImage = null;
		
		try {
			sourceImage = ImageIO.read(new File("pics\\flash.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

        assert sourceImage != null;

        return new Flash(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
	}
	private class KeyInputHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
		} 
		
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
		}
	}
}
