import java.awt.*;

public class Bullet extends Entity {
    public Point startPoint;
    public static Image bulletImage;

    public Bullet(int x, int y) {
        super(bulletImage, x, y, 1, 0, bulletImage.getWidth(null));
    }

    // updates the bullet position and checks if the bullet hit an enemy
    public void update() {
        Bullet bulletCopy = copy(x + speed, y);
        if (!bulletCopy.intersect() && !bulletCopy.collidesEnemy()) {
            x += speed;
            if (travelledDistance() >= 600) Canvas.activeBullets.remove(this);
        } else {
            Canvas.activeBullets.remove(this);
        }
    }

    // makes a copy of the bullet entity at a different position
    public Bullet copy(int newX, int newY) {
        Bullet copy = new Bullet(newX, newY);
        copy.speed = 0;
        copy.velocity = 0;
        copy.image = image;
        copy.state = state;
        return copy;
    }

    // measure the distance the bullet travelled
    public double travelledDistance() {
        int dx = startPoint.x - x;
        int dy = startPoint.y - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // method to check if the bullet hit-box hit and enemy
    public boolean collidesEnemy() {
        boolean isInside = false;
        for (Enemy e : Canvas.enemies) {
            int x2 = x + image.getWidth(null);
            int y2 = y + image.getHeight(null);
            int eX2 = e.x + e.image.getWidth(null);
            int eY2 = e.y + e.image.getHeight(null);
            boolean widthIsPositive = Math.min(x2, eX2) > Math.max(x, e.x);
            boolean heightIsPositive = Math.min(y2, eY2) > Math.max(y, e.y);
            if (widthIsPositive && heightIsPositive) {
                isInside = true;
                e.damage();
            }
        }
        return isInside;
    }
}
