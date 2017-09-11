package graphics;

import java.awt.*;

/**
 * Created by Maria on 11.09.2017.
 */
public class Flash {
    private Image image;

    Flash(Image image) {
        this.image = image;
    }

    int getWidth() {
        return image.getWidth(null);
    }

    public int getHeight() {
        return image.getHeight(null);
    }

    void draw(Graphics g, int x, int y) {
        g.drawImage(image,x,y,null);
    }
}
