package game.objects;

import game.AudioPlayer;
import game.Collider;
import game.ColliderType;
import game.GamePanel;

import java.util.Random;

public class RandomBlock extends GameObject {

    Random rnd = new Random();

    int velY;
    private int originY;

    private boolean isFull;
    private boolean isMoving;

    public RandomBlock(GamePanel gp, int i, int j) {
        super(gp);

        this.setX(i * 16);
        this.setY((10-j) * 16);
        this.setWidth(16);
        this.setHeight(16);

        originY = getY();
        isFull = true;
        isMoving = false;
    }

    public void updatePhysics() {
        if(isMoving) {
            velY -= 2; // gravity

            setY(getY() + velY);
            if (getY() < originY) {
                setY(originY);
                isMoving = false;
            }
        }
    }

    public void handleCollide(GameObject colliding, ColliderType type) {
        if (colliding instanceof Mario && type == ColliderType.BLOCKING_VERTICAL_UP && isFull) {
            if(rnd.nextBoolean()){
                //Coin
                AudioPlayer.playAudio("smb_coin.wav");
            }else{
                //Power up
                AudioPlayer.playAudio("smb_powerup_appears.wav");
            }
            velY = 8;
            isMoving = true;
            // isFull = false;
        }
    }

    public void initColliders() {
        this.getColliders().add(new Collider(0,0,16,2, ColliderType.BLOCKING_VERTICAL_UP));
        this.getColliders().add(new Collider(0,14,16,2, ColliderType.BLOCKING_VERTICAL_DOWN));
        this.getColliders().add(new Collider(0,0,2,16, ColliderType.BLOCKING_HORIZONTAL_RIGHT));
        this.getColliders().add(new Collider(14,0,2,16, ColliderType.BLOCKING_HORIZONTAL_LEFT));
    }

    @Override
    public void initSprites() {
        setSprite(gp.img.random_cell);
    }

}
