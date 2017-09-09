package graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Maria on 08.09.2017.
 */
public class Background extends JPanel{
    private String path;
    Background(int mode){
        if (mode == 0) path = Images.MAIN_BACKGROUND;
        else path = Images.GAME_BACKGROUND;
    }
    public void paintComponent(Graphics g){
        Image im = null;
        try {
            im = ImageIO.read(new File(path));
        } catch (IOException ignored) {}
        g.drawImage(im, 0, 0, null);
    }
}