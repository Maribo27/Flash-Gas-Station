package graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

/**
 * Created by Maria on 11.09.2017.
 */
public class GameCanvas implements Runnable {
	Canvas canvas = new Canvas();

	private boolean running,
            leftPressed = false,
            rightPressed = false;
	
	private static ImageDraw flash, background;

	private static int cord_X = 0, cord_Y = 450;
	
	void start() {
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
		canvas.addKeyListener(new KeyInputHandler());
		canvas.addMouseListener(new MouseInoutAdapter());
		flash = getImage("pics\\flash.png");
        background = getImage("pics\\game.png");
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
        flash.draw(g, cord_X, cord_Y);
		g.dispose();
		bs.show();
	}
	
	private void update() {
		if (leftPressed && cord_X > 0) {
			cord_X--;
		}
		if (rightPressed && cord_X < canvas.getWidth() - flash.getWidth()) {
			cord_X++;
		}
	}

	private ImageDraw getImage(String path) {
		BufferedImage sourceImage = null;
		
		try {
			sourceImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

        assert sourceImage != null;

        return new ImageDraw(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
	}

	private class MouseInoutAdapter extends MouseAdapter {
	    public void mouseClicked(MouseEvent event) {
	        if (event.getX() >= canvas.getWidth() - flash.getWidth())
	            cord_X = event.getX() - flash.getWidth();
	        else cord_X = event.getX();
        }
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
