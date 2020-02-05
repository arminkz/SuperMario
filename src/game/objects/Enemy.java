package game.objects;

import game.AudioPlayer;
import game.Collider;
import game.ColliderType;
import game.GamePanel;

import java.awt.*;

public class Enemy extends GameObject {

    private int velX;
    private int velY;

    public Enemy(GamePanel gp, int i, int j){
        super(gp);

        this.setX(i * 16);
        this.setY((10-j) * 16);
        this.setWidth(16);
        this.setHeight(16);

        velX = -3;
    }

    public void die() {
        AudioPlayer.playAudio("smb_stomp.wav");
        gp.getDeadObjects().add(this);
    }

    public void updatePhysics() {

        velY -= 2; //gravity

        // collision check
        Rectangle myCollider = new Rectangle(0,0,16,16);

        Rectangle colliderTrasformed =
                new Rectangle(getX() + myCollider.x , getY()+myCollider.y,myCollider.width,myCollider.height);

        Rectangle xColliderTrasformed =
                new Rectangle(getX() + myCollider.x + velX, getY()+myCollider.y,myCollider.width,myCollider.height);

        Rectangle yColliderTrasformed =
                new Rectangle(getX() + myCollider.x, getY() + myCollider.y + velY,myCollider.width,myCollider.height);

        boolean isBlockedX = false;
        boolean isBlockedY = false;

        for (GameObject go : gp.getGameObjects()) {
            for (Collider cld : go.getColliders()) {

                if(cld.getType() == ColliderType.MARIO) {
                    if(colliderTrasformed.intersects(cld.getRect())){
                        go.handleCollide(this,ColliderType.MARIO);
                    }
                }

                if(cld.getType() == ColliderType.BLOCKING_HORIZONTAL_RIGHT && velX > 0) {
                    Rectangle cldRect = cld.getRect();
                    Rectangle colliderTransformed = new Rectangle(go.getX() + cldRect.x,go.getY() + cldRect.y,
                            cldRect.width,cldRect.height);

                    if(colliderTransformed.intersects(xColliderTrasformed)) {
                        isBlockedX = true;
                        setX ( go.getX() + cldRect.x - getWidth() );
                        go.handleCollide(this,cld.getType());

                        // Enemy behaviour
                        velX = -velX;
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

                        // Enemy behaviour
                        velX = -velX;
                    }
                }

                if(cld.getType() == ColliderType.BLOCKING_VERTICAL_DOWN && velY < 0) {
                    Rectangle cldRect = cld.getRect();
                    Rectangle colliderTransformed = new Rectangle(go.getX() + cldRect.x,go.getY() + cldRect.y,
                            cldRect.width,cldRect.height);
                    if(colliderTransformed.intersects(yColliderTrasformed)) {
                        velY = 0;
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


        // collision with ground
        if (getY() < 0) {
            setY(0);
        }

    }

    public void initColliders() {
        this.getColliders().add(new Collider(0,0,16,2, ColliderType.BLOCKING_VERTICAL_UP));
        this.getColliders().add(new Collider(0,14,16,2, ColliderType.BLOCKING_VERTICAL_DOWN));
        this.getColliders().add(new Collider(0,0,2,16, ColliderType.BLOCKING_HORIZONTAL_RIGHT));
        this.getColliders().add(new Collider(14,0,2,16, ColliderType.BLOCKING_HORIZONTAL_LEFT));
    }

    public void initSprites() {
        setSprite(gp.img.enemy);
    }

    public void handleCollide(GameObject colliding, ColliderType type) {
        if (colliding instanceof Mario && type == ColliderType.BLOCKING_VERTICAL_DOWN) {
            die();
            // isFull = false;
        }else if(colliding instanceof Mario){
            ((Mario)colliding).die();
        }
    }
}
