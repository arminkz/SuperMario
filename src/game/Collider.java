package game;

import java.awt.*;

public class Collider {

    private Rectangle rect;
    private ColliderType type;

    public Collider(int sx,int sy,int w,int h, ColliderType type) {
        this.rect = new Rectangle(sx,sy,w,h);
        this.type = type;
    }

    public ColliderType getType() {
        return type;
    }

    public Rectangle getRect() {
        return rect;
    }
}
