import java.awt.*;

public class Entity {
    protected boolean isDamaged;
    protected double damagedTime;
    protected boolean isGrounded = false;
    protected int speed = 0;
    protected Image image;
    protected int x;
    protected int y;
    protected double velocity;
    protected double acceleration = 0.5;
    protected int health;
    protected int ammo;
    protected EntityState state;
    public int jumpCounter = 0;
    public int jumpX;
    public int jumpY;
    public double lastTime = System.currentTimeMillis();
    public int lastAnimation = 0;
    public int hitBox;

    public Entity(Image image, int x, int y, int health, int ammo, int hitBox) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.health = health;
        this.ammo = ammo;
        this.hitBox = hitBox;
        this.state = new EntityState(true, "idle");
    }

    // Method that finds if the entity object is intersecting with MapBlocks objects
    public boolean intersect() {
        int x2 = x + hitBox;
        int y2 = y + image.getHeight(null);
        boolean isInside = false;
        for (int i = 0; i < MapBlocks.map.size(); i++) {
            MapBlocks block = MapBlocks.map.get(i);
            int blockX = block.x;
            int blockY = block.y;
            int blockX2 = block.x + block.image.getWidth(null);
            int blockY2 = block.y + block.image.getHeight(null);
            boolean widthIsPositive = Math.min(x2, blockX2) > Math.max(x, blockX);
            boolean heightIsPositive = Math.min(y2, blockY2) > Math.max(y, blockY);
            if (widthIsPositive && heightIsPositive) {
                isInside = true;
                break;
            }
        }
        return isInside;
    }

    // Default jump method for entities
    public void jump() {
        if (jumpCounter == 1) velocity = -6;
        else velocity = -8;
        jumpX = x;
        jumpY = y;
    }

    // Makes a copy of the entity at a different location
    public Entity copy(int newX, int newY) {
        Entity copy = new Entity(image, newX, newY, health, ammo, hitBox);
        copy.image = image;
        return copy;
    }

    // Uses a copy of the entity to check if gravity would make the entity intersect with map
    public void gravity() {
        Entity entityCopy = copy(x, (int) (y + velocity));
        if (!entityCopy.intersect()) {
            y += velocity;
            velocity += acceleration;
            isGrounded = false;
        } else {
            if (velocity > 1.5) velocity /= 1.5;
            if (velocity < 0) velocity = -(velocity / 4);
            else isGrounded = true;
        }
    }
}

class EntityState {
    protected boolean isFacingForward;
    protected String state;

    public EntityState(boolean isFacingForward, String state) {
        this.isFacingForward = isFacingForward;
        this.state = state;
    }
}