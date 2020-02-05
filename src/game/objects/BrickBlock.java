package game.objects;

import game.Collider;
import game.ColliderType;
import game.GamePanel;

public class BrickBlock extends GameObject {

    public BrickBlock(GamePanel gp, int i, int j) {
        super(gp);

        this.setX(i * 16);
        this.setY((10-j) * 16);
        this.setWidth(16);
        this.setHeight(16);

        this.setName("BrickBlock at "+ i + "," + j);
    }

    public void updatePhysics() {
        // this item has no physics
    }

    public void handleCollide(GameObject colliding, ColliderType type) {

    }

    public void initColliders() {
        this.getColliders().add(new Collider(0,0,16,2, ColliderType.BLOCKING_VERTICAL_UP));
        this.getColliders().add(new Collider(0,14,16,2, ColliderType.BLOCKING_VERTICAL_DOWN));
        this.getColliders().add(new Collider(0,0,2,16, ColliderType.BLOCKING_HORIZONTAL_RIGHT));
        this.getColliders().add(new Collider(14,0,2,16, ColliderType.BLOCKING_HORIZONTAL_LEFT));
    }

    @Override
    public void initSprites() {
        setSprite(gp.img.brick_cell);
    }
}
