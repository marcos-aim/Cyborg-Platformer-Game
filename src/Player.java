import java.awt.*;
import java.time.Duration;

public class Player extends Entity {
    protected Point checkPoint = new Point(20, 300);
    protected boolean[] keys = Canvas.keysPressed;
    protected boolean justShot;
    protected double lastShot;
    protected static int deathCounter;


    public static Image[] idleSprites;
    public static Image[] runningSprites;
    public static Image[] hurtSprites;
    public static Image shootingSprite;
    public static Image ammoBox;
    public static Image box;
    public static Image heart;

    public Player(int x, int y, int health) {
        super(idleSprites[0], x, y, health, 10, 30);
    }

    // updates the player position and puts conditions on the player
    public void update() {
        gravity();
        int maxSpeed = 5;
        Entity entityCopy = copy(x + speed, y);
        if (!entityCopy.intersect()) {
            x += speed;
            if (keys[0]) {
                if (speed > -maxSpeed) speed -= 1;
            }
            if (keys[1]) {
                if (speed < maxSpeed) speed += 1;
            }
            if (((!keys[0] && !keys[1]) || (keys[0] && keys[1])) && !isDamaged) speed = 0;
        } else {
            speed /= 2;
        }
        if (isGrounded) {
            jumpCounter = 0;
        }
        if (jumpCounter == 0 && !isGrounded) jumpCounter = 1;
        if (isGrounded || !keys[2] || velocity > 0) acceleration = 0.5;
        if (keys[2] && acceleration > 0.25) acceleration -= 0.02;
        if (y > 900 || health <= 0) {
            kill();
        }
        if (keys[3]) {
            shoot();
        }

        // 1 second damage cool-down delay
        if ((System.currentTimeMillis() - damagedTime) > 1000 && isDamaged) isDamaged = false;
        // 1 second shoot cool-down delay
        if ((System.currentTimeMillis() - lastShot) > 500 && justShot) justShot = false;

        // Check if Player won
        if (x > 8000) CyborgPlatform.game.isWon = true;

        updateState();
        animate();
    }

    // update the camera offset based on the screen size and the map bounds
    public int updateCamera() {
        int camX = x - (1280 - 30) / 2;
        camX = Math.max(0, Math.min(camX, MapBlocks.mapWidth - 1280 - 30));
        return camX;
    }

    // checks the direction of the player
    public boolean isFacingForwards() {
        if (!keys[0] && keys[1]) return true;
        if (keys[0] && !keys[1]) return false;
        return Canvas.isLastDirectionForwards;
    }

    //checks if player is moving
    public boolean isMoving() {
        return (keys[0] || keys[1]);
    }

    // checks if player is jumping
    public boolean isJumping() {
        return (jumpCounter > 0);
    }

    // change player state anf direction
    public void updateState() {
        String newState = "idle";
        if (isGrounded && isMoving()) newState = "running";
        if (isGrounded && !isMoving()) newState = "idle";
        if (!isGrounded) newState = "aerial";
        if (isDamaged) newState = "hurt";
        if (justShot) newState = "shooting";
        state = new EntityState(isFacingForwards(), newState);
    }

    // animates the player based on player state
    public void animate() {
        switch (state.state) {
            case "idle" -> {
                if (lastAnimation >= idleSprites.length) lastAnimation = 0;
                if ((System.currentTimeMillis() - lastTime) > 250) {
                    image = idleSprites[lastAnimation];
                    lastAnimation++;
                    lastTime = System.currentTimeMillis();
                }
            }
            case "running" -> {
                if (lastAnimation >= runningSprites.length) lastAnimation = 0;
                if ((System.currentTimeMillis() - lastTime) > 180) {
                    image = runningSprites[lastAnimation];
                    lastAnimation++;
                    lastTime = System.currentTimeMillis();
                }
            }
            case "aerial" -> {
                if (velocity < 0) image = runningSprites[5];
            }
            case "hurt" -> {
                image = hurtSprites[1];
            }
            case "shooting" -> {
                image = shootingSprite;
            }
        }
    }

    // method for the player to create and shoot a bullet
    public void shoot() {
        if (!justShot && ammo != 0) {
            ammo--;
            justShot = true;
            lastShot = System.currentTimeMillis();
            if (isFacingForwards()) {
                Bullet bullet = new Bullet(x + 47, y + 10);
                bullet.speed = 10;
                bullet.startPoint = new Point(bullet.x, bullet.y);
                Canvas.activeBullets.add(bullet);
            } else {
                Bullet bullet = new Bullet(x - 25, y + 10);
                bullet.speed = -10;
                bullet.startPoint = new Point(bullet.x, bullet.y);
                Canvas.activeBullets.add(bullet);
            }

        }
    }

    // damages player if hit by enemy
    public void damage(Enemy e) {
        if (!isDamaged) {
            health--;
            damagedTime = System.currentTimeMillis();
            isDamaged = true;
            if (x < e.x) {
                speed = -4;
            } else speed = 4;
            velocity = -6;
        }
    }

    // draws health bar ammo, etc
    public void drawGUI(Graphics g) {
        Font font = Canvas.font;
        font = font.deriveFont(60.0f);
        g.setColor(Color.BLACK);
        g.setFont(font);

        // Display remaining health
        if (health > 0) g.drawImage(heart, 20 + Canvas.cameraOffset, 20, null);
        if (health > 1) g.drawImage(heart, 89 + Canvas.cameraOffset, 20, null);
        if (health > 2) g.drawImage(heart, 158 + Canvas.cameraOffset, 20, null);

        // Display remaining Ammo
        g.drawImage(ammoBox, 1040 + Canvas.cameraOffset, 20, null);
        g.drawString(String.valueOf(ammo), 1130 + Canvas.cameraOffset, 68);

        // Display game timer
        long gameTimer = (long) (System.currentTimeMillis() - Game.startTime);
        Duration duration = Duration.ofMillis(gameTimer);
        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);
        long seconds = duration.getSeconds();
        String timer = String.format("%02d" + ":" + "%02d", minutes, seconds);
        g.drawString(timer, 510 + Canvas.cameraOffset, 68);

        font = font.deriveFont(20.0f);
        g.setColor(Color.BLACK);
        g.setFont(font);
        String[] controls = new String[]{
                "Use WASD to Move",
                "You can Double Jump",
                "Use SPACE to Shoot",
                "Press ESCAPE to Restart"
        };
        int printAtY = 250;
        for (String control : controls) {
            g.drawString(control, 470, printAtY);
            printAtY += 30;
        }
        g.drawString("This is The Winner Tunnel", 6800, 310);
        g.drawString("Just Keep Walking!", 6800, 340);
        g.drawString("ATTEMPTS: " + deathCounter, 20 + Canvas.cameraOffset, 110);

    }

    //kills player
    public void kill() {
        deathCounter++;
        CyborgPlatform.game.spawnEntities();
    }
}
