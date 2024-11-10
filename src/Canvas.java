import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Canvas extends JComponent implements KeyListener, ActionListener, MouseListener {
    protected double last = System.nanoTime() / 1000000000.;
    protected static Player player;
    protected static ArrayList<Enemy> enemies;
    protected static ArrayList<Bullet> activeBullets = new ArrayList<>();
    protected static boolean[] keysPressed = new boolean[4];
    protected static boolean isLastDirectionForwards = true;
    protected static int cameraOffset;
    protected static Font font;
    protected static Image cloud;

    public Canvas() {
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
    }


    public void paint(Graphics g) {
        if (!CyborgPlatform.game.isWon) {
            ArrayList<Bullet> bulletsCopy = new ArrayList<Bullet>(activeBullets);
            ArrayList<Enemy> enemiesCopy = new ArrayList<Enemy>(enemies);
            // update the camera offset depending on the players position
            cameraOffset = player.updateCamera();
            g.translate(-cameraOffset, 0);

            //Draw Background and Map images
            Background.drawBackground(g);
            MapBlocks.drawMap(g);

            //update player entity behaviour and position
            player.update();

            // Display the estimated FPS
            displayFPS();

            // Draw the GUI
            player.drawGUI(g);

            //Draw the player and little jumping cloud particle
            if (player.isFacingForwards()) g.drawImage(player.image, player.x, player.y, null);
            else
                g.drawImage(player.image, player.x + 30, player.y, -player.image.getWidth(null), player.image.getHeight(null), null);
            if (player.jumpCounter > 2 && player.velocity < 0)
                g.drawImage(cloud, player.jumpX, player.jumpY + 42, null);

            // Do behaviour for every enemy before drawing
            for (Enemy enemy : enemiesCopy) {
                enemy.doBehavior();
            }
            //Draw every enemy in the ArrayList all at once
            for (Enemy enemy : enemiesCopy) {
                if (enemy.isFacingForwards) g.drawImage(enemy.image, enemy.x, enemy.y, null);
                else
                    g.drawImage(enemy.image, enemy.x + 30, enemy.y, -enemy.image.getWidth(null), enemy.image.getHeight(null), null);
            }

            // Update bullet position
            for (Bullet bullet : bulletsCopy) {
                if (!activeBullets.isEmpty()) bullet.update();
            }
            if (!activeBullets.isEmpty()) {
                // Draw every bullet
                for (Bullet bullet : bulletsCopy) {
                    if (bullet.speed > 0) g.drawImage(bullet.image, bullet.x, bullet.y, null);
                    else
                        g.drawImage(bullet.image, bullet.x + bullet.image.getWidth(null), bullet.y, -bullet.image.getWidth(null), bullet.image.getHeight(null), null);
                }
            }
        } else end(g);
    }

    // uses system time to determine the refresh rate
    public void displayFPS() {
        System.out.println((int) (1 / (System.nanoTime() / 1000000000. - last)));
        last = System.nanoTime() / 1000000000.;
    }

    // Draws the end screen
    public void end(Graphics g) {
        // Draw end screen Background
        g.drawImage(Background.background[0], 0, 0, null);
        g.drawImage(Background.background[1], 0, 0, null);
        // Draw end screen Text
        Font font = Canvas.font;
        font = font.deriveFont(60.0f);
        g.setColor(Color.BLACK);
        g.setFont(font);
        String[] ends = new String[]{
                "You Won!",
                "It Took You",
                Player.deathCounter + " Attempts",
                "Press ESC to Restart,",
                "Or ENTER to exit",
        };
        g.drawString(ends[0], 460, 220);
        g.drawString(ends[1], 375, 300);
        g.drawString(ends[2], 405, 380);
        g.drawString(ends[3], 150, 460);
        g.drawString(ends[4], 270, 540);

    }

    // KeyListeners are put into an array of booleans for better use (some extra stuff)
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> {
                keysPressed[0] = true;
                isLastDirectionForwards = false;
            }
            case KeyEvent.VK_D -> {
                keysPressed[1] = true;
                isLastDirectionForwards = true;
            }
            case KeyEvent.VK_W -> {
                keysPressed[2] = true;
                if (player.jumpCounter <= 1) {
                    player.jump();
                    player.jumpCounter += 2;
                }
            }
            case KeyEvent.VK_SPACE -> {
                keysPressed[3] = true;
            }
            case KeyEvent.VK_ESCAPE -> {
                Player.deathCounter++;
                CyborgPlatform.game.spawnEntities();
            }
            case KeyEvent.VK_ENTER -> {
                System.exit(0);
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> {
                keysPressed[0] = false;
            }
            case KeyEvent.VK_D -> {
                keysPressed[1] = false;
            }
            case KeyEvent.VK_W -> {
                if (player.jumpCounter < 3) player.jumpCounter--;
                keysPressed[2] = false;
            }
            case KeyEvent.VK_SPACE -> {
                keysPressed[3] = false;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getX() + cameraOffset + "," + e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
