package game.objects;

import game.AudioPlayer;
import game.Collider;
import game.ColliderType;
import game.GamePanel;

import java.awt.*;

import static java.lang.Thread.sleep;

public class Mario extends GameObject {

    private static final int CAMERA_MOVE_THRESHOLD = 140;
    private static final int MAX_V = 6;

    private int accX;

    private int velX;
    private int velY;

    private boolean isMidAir = false;

    public Mario(GamePanel gp) {
        super(gp);

        setX(0);
        setY(0);

        setWidth(12);
        setHeight(16);
        this.gp = gp;
    }

    public void moveRight() {
        accX = 1;
    }

    public void moveLeft() {
        accX = -1;
    }

    public void jump() {
        if (!isMidAir) {
            AudioPlayer.playAudio("smb_jump-small.wav");
            velY = 16;
            isMidAir = true;
        }
    }

    public void stop() {
        accX = 0;
    }

    public void die() {
        gp.themeSound.stop();
        AudioPlayer.playAudio("smb_mariodie.wav");
        gp.getPhysicsTimer().stop();
    }

    private String printRect(Rectangle r) {
        return "x: " + r.x + " y: " + r.y + " w: " + r.width + " h: " + r.height;
    }

    public void updatePhysics() {

        // handle velocity in X axis
        velX += accX;
        if (accX == 0 && velX>0) velX--;
        if (accX == 0 && velX<0) velX++;
        if (velX > MAX_V) velX = MAX_V;
        if (velX < -MAX_V) velX = -MAX_V;

        // handle velocity in Y axis
        velY -= 2; //gravity

        // update Y
        //setY( getY() + velY);


        // collision check
        Rectangle myCollider = this.getColliders().get(0).getRect();
        Rectangle xColliderTrasformed =
                new Rectangle(getX() + myCollider.x + velX, getY()+myCollider.y,myCollider.width,myCollider.height);

        Rectangle yColliderTrasformed =
                new Rectangle(getX() + myCollider.x, getY() + myCollider.y + velY,myCollider.width,myCollider.height);

        boolean isBlockedX = false;
        boolean isBlockedY = false;

        for (GameObject go : gp.getGameObjects()) {
            for (Collider cld : go.getColliders()) {

                if(cld.getType() == ColliderType.BLOCKING_HORIZONTAL_RIGHT && velX > 0) {
                    Rectangle cldRect = cld.getRect();
                    Rectangle colliderTransformed = new Rectangle(go.getX() + cldRect.x,go.getY() + cldRect.y,
                            cldRect.width,cldRect.height);

                    if(colliderTransformed.intersects(xColliderTrasformed)) {
                        isBlockedX = true;
                        setX ( go.getX() + cldRect.x - getWidth() );
                        go.handleCollide(this,cld.getType());
                    }
                }

                if(cld.getType() == ColliderType.BLOCKING_HORIZONTAL_LEFT && velX < 0) {
                    Rectangle cldRect = cld.getRect();
                    Rectangle colliderTransformed = new Rectangle(go.getX() + cldRect.x,go.getY() + cldRect.y,
                            cldRect.width,cldRect.height);

                    if(colliderTransformed.intersects(xColliderTrasformed)) {
                        isBlockedX = true;
                        setX ( go.getX() + cldRect.x + cldRect.width);
                        go.handleCollide(this,cld.getType());
                    }
                }

                if(cld.getType() == ColliderType.BLOCKING_VERTICAL_DOWN && velY < 0) {
                    Rectangle cldRect = cld.getRect();
                    Rectangle colliderTransformed = new Rectangle(go.getX() + cldRect.x,go.getY() + cldRect.y,
                            cldRect.width,cldRect.height);
                    if(colliderTransformed.intersects(yColliderTrasformed)) {
                        velY = 0;
                        isMidAir = false;
                        setY ( go.getY() + cldRect.y + cldRect.height );
                        go.handleCollide(this,cld.getType());
                    }
                }

                if(cld.getType() == ColliderType.BLOCKING_VERTICAL_UP && velY > 0) {
                    Rectangle cldRect = cld.getRect();
                    Rectangle colliderTransformed = new Rectangle(go.getX() + cldRect.x,go.getY() + cldRect.y,
                            cldRect.width,cldRect.height);
                    if(colliderTransformed.intersects(yColliderTrasformed)) {
                        isBlockedY = true;
                        setY ( go.getY() + cldRect.y - getHeight() );
                        go.handleCollide(this,cld.getType());
                    }
                }
            }
        }

        if(!isBlockedX){
            setX( getX() + velX );
        }

        if(!isBlockedY){
            setY( getY() + velY );
        }

        // landing on object
//        if ( velY < 0 && isBlockedY){
//            isMidAir = false;
//        }

        // collision with ground
        if (getY() < 0) {
            setY(0);
            isMidAir = false;
        }

        // handle camera movement
        if (getX() - gp.getCameraX() > CAMERA_MOVE_THRESHOLD) {
            gp.updateCamera(getX() - gp.getCameraX() - CAMERA_MOVE_THRESHOLD);
            //gp.updateCamera(velX);
        }

        // cant move behind camera
        if (getX() < gp.getCameraX()) {
            setX(gp.getCameraX());
        }
    }

    public void handleCollide(GameObject colliding, ColliderType type) {

        if(colliding instanceof Enemy) die();

    }

    public void initSprites() {
        setSprite(gp.img.mario);
    }

    public void initColliders() {
        this.getColliders().add(new Collider(0,0,12,16, ColliderType.MARIO));
    }
}
