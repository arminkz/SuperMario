package game.objects;

import game.Collider;
import game.ColliderType;
import game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class GameObject {

    private ArrayList<Collider> colliders;
    private BufferedImage sprite;

    private int positionX;
    private int positionY;

    private int width;
    private int height;

    private String name;

    GamePanel gp;
    GameObject(GamePanel gp){
        this.gp = gp;

        initSprites();

        colliders = new ArrayList<>();
        initColliders();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public ArrayList<Collider> getColliders() {
        return colliders;
    }

    public int getX() {
        return positionX;
    }

    public void setX(int positionX) {
        this.positionX = positionX;
    }

    public int getY() {
        return positionY;
    }

    public void setY(int positionY) {
        this.positionY = positionY;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public abstract void updatePhysics();

    public abstract void initColliders();

    public abstract void initSprites();

    public abstract void handleCollide(GameObject colliding, ColliderType type);

}
