package graphics.drawImages;

import java.awt.*;

/**
 * Created by Maria on 11.09.2017.
 */
public class ImageDraw {
    private Image image;

    public ImageDraw(Image image) {
        this.image = image;
    }

    public int getWidth() {
        return image.getWidth(null);
    }

    public void draw(Graphics g, int x, int y) {
        g.drawImage(image, x, y, null);
    }
}
