package graphics;

import java.awt.*;

/**
 * Created by Maria on 11.09.2017.
 */
class ImageDraw {
    private Image image;

    ImageDraw(Image image) {
        this.image = image;
    }

    int getWidth() {
        return image.getWidth(null);
    }

    void draw(Graphics g, int x, int y) {
        g.drawImage(image,x,y,null);
    }
}
