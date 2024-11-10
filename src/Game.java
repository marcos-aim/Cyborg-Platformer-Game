import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.nanoTime;

public class Game {
    public boolean isWon = false;
    public static double startTime;
    protected boolean isRunning = true;
    // load all the Images when the game starts
    public void loadImages(){
        // load Player Images
        try {
            // load Player Idle sprites
            Player.idleSprites = new Image[]{
                    ImageIO.read(new File("src/Sprites/Player/idle/Cyborg_idle_1.png")),
                    ImageIO.read(new File("src/Sprites/Player/idle/Cyborg_idle_2.png")),
                    ImageIO.read(new File("src/Sprites/Player/idle/Cyborg_idle_3.png")),
                    ImageIO.read(new File("src/Sprites/Player/idle/Cyborg_idle_4.png"))
            };
            // load Player running sprites
            Player.runningSprites = new Image[]{
                    ImageIO.read(new File("src/Sprites/Player/run/Cyborg_run_1.png")),
                    ImageIO.read(new File("src/Sprites/Player/run/Cyborg_run_2.png")),
                    ImageIO.read(new File("src/Sprites/Player/run/Cyborg_run_3.png")),
                    ImageIO.read(new File("src/Sprites/Player/run/Cyborg_run_4.png")),
                    ImageIO.read(new File("src/Sprites/Player/run/Cyborg_run_5.png")),
                    ImageIO.read(new File("src/Sprites/Player/run/Cyborg_run_6.png"))
            };
            // load Player hurt sprites
            Player.hurtSprites = new Image[]{
                    ImageIO.read(new File("src/Sprites/Player/hurt/Cyborg_hurt_1.png")),
                    ImageIO.read(new File("src/Sprites/Player/hurt/Cyborg_hurt_2.png"))
            };
            // Extra misc images
            Player.heart = ImageIO.read(new File("src/Sprites/heart.png"));
            Player.box = ImageIO.read(new File("src/Sprites/box.png"));
            Player.ammoBox = ImageIO.read(new File("src/Sprites/ammo.png"));
            Player.shootingSprite = ImageIO.read(new File("src/Sprites/Player/shoot/shootingSprite.png"));
            Bullet.bulletImage = ImageIO.read(new File("src/Sprites/Player/shoot/bullet.png"));
            Canvas.cloud = ImageIO.read(new File("src/Sprites/cloud.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Load Map Images
        try {
            MapBlocks.mapImages = new Image[]{
                    ImageIO.read(new File("src/Tiles/1_FrameTopLeftCorner.png")),
                    ImageIO.read(new File("src/Tiles/2_FrameTopRightCorner.png")),
                    ImageIO.read(new File("src/Tiles/3_FrameBottomLeftCorner.png")),
                    ImageIO.read(new File("src/Tiles/4_FrameBottomRightCorner.png")),
                    ImageIO.read(new File("src/Tiles/5_FrameTopMid.png")),
                    ImageIO.read(new File("src/Tiles/6_FrameLeftMid.png")),
                    ImageIO.read(new File("src/Tiles/7_FrameRightMid.png")),
                    ImageIO.read(new File("src/Tiles/8_FrameBottomMod.png")),
                    ImageIO.read(new File("src/Tiles/9_FrameMid.png")),
                    ImageIO.read(new File("src/Tiles/A_Box.png")),
                    ImageIO.read(new File("src/Tiles/B_HalfSlab.png")),
                    ImageIO.read(new File("src/Tiles/C_IndustrialTabLeft.png")),
                    ImageIO.read(new File("src/Tiles/D_IndustrialSlabMid.png")),
                    ImageIO.read(new File("src/Tiles/E_IndustrialSlabRight.png")),
                    ImageIO.read(new File("src/Tiles/F_LightPole.png")),
                    ImageIO.read(new File("src/Tiles/G_LightTop.png")),
                    ImageIO.read(new File("src/Tiles/H_TreadLeft.png")),
                    ImageIO.read(new File("src/Tiles/I_TreadMid.png")),
                    ImageIO.read(new File("src/Tiles/J_TreadRight.png"))
            };
            // load Font
            Canvas.font = Font.createFont(Font.TRUETYPE_FONT, new File("src/Font/font.TTF"));
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
        // Load Background Images
        try {
            Background.background = new Image[]{
                    ImageIO.read(new File("src/Background/1_Background.png")),
                    ImageIO.read(new File("src/Background/2_Background.png")),
                    ImageIO.read(new File("src/Background/3_Background.png")),
                    ImageIO.read(new File("src/Background/4_Background.png"))
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Load Enemy Sprites
        try {
            // Load idle Sprites
            Enemy.idleSprites = new Image[]{
                    ImageIO.read(new File("src/Sprites/Enemy/Idle/Idle_1.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Idle/Idle_2.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Idle/Idle_3.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Idle/Idle_4.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Idle/Idle_5.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Idle/Idle_6.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Idle/Idle_7.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Idle/Idle_8.png"))
            };
            // Load walking Sprites
            Enemy.walkingSprites = new Image[]{
                    ImageIO.read(new File("src/Sprites/Enemy/Walking/Walk_1.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Walking/Walk_2.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Walking/Walk_3.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Walking/Walk_4.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Walking/Walk_5.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Walking/Walk_6.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Walking/Walk_7.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Walking/Walk_8.png"))
            };
            // Load running Sprites
            Enemy.runningSprites = new Image[]{
                    ImageIO.read(new File("src/Sprites/Enemy/Running/Run_1.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Running/Run_2.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Running/Run_3.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Running/Run_4.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Running/Run_5.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Running/Run_6.png")),
                    ImageIO.read(new File("src/Sprites/Enemy/Running/Run_7.png"))
            };
            // Load hurt Sprites
            Enemy.hurtSprite = ImageIO.read(new File("src/Sprites/Enemy/Hurt.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Spawn all Entities in the map, is also used to reset game state
    public void spawnEntities() {
        if (CyborgPlatform.game.isWon) Player.deathCounter = 0;
        CyborgPlatform.game.isWon = false;
        startTime = System.currentTimeMillis();
        Canvas.player = new Player(20, 300, 3);
        Canvas.enemies = new ArrayList<>();
        Canvas.enemies.add(new Enemy(1475, 230,2));
        Canvas.enemies.add(new Enemy(2570, 196,2));
        Canvas.enemies.add(new Enemy(2750, 320,2));
        Canvas.enemies.add(new Enemy(3060, 470,2));
        Canvas.enemies.add(new Enemy(4219, 100,2));
        Canvas.enemies.add(new Enemy(4900, 530,2));
        Canvas.enemies.add(new Enemy(4970, 530,2));
        Canvas.enemies.add(new Enemy(5040, 539,2));
        Canvas.enemies.add(new Enemy(6397, 196,2));
        Canvas.enemies.add(new Enemy(6540, 520,2));
        Canvas.enemies.add(new Enemy(6600, 520,2));
        Canvas.enemies.add(new Enemy(6660, 520,2));
        Canvas.enemies.add(new Enemy(6720, 520,2));
        Canvas.activeBullets = new ArrayList<>();
    }

    // Timer that repaints at a rate to have 60fps (16 milliseconds)
    public void startTimer(Canvas canvas) {
        double t = nanoTime();
        while (isRunning) {
            if ((nanoTime() - t) > 1000000000. / 60) {
                t = nanoTime();
                canvas.repaint();
            }
        }
    }
}
