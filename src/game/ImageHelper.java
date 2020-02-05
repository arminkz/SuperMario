package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHelper {

    public static BufferedImage loadImage(String imageName){
        try {
            BufferedImage bi = ImageIO.read(new File("./assets/images/" + imageName));
            return bi;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage background = loadImage("background.gif");
    public BufferedImage background_grid = loadImage("background_grid.gif");
    public BufferedImage brick_cell = loadImage("brick.png");
    public BufferedImage random_cell = loadImage("random.png");
    public BufferedImage mario = loadImage("mario.png");
    public BufferedImage enemy = loadImage("enemy.png");

}
