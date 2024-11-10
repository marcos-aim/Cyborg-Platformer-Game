/*
Name: Marcos Ibáñez Matles
NetID: mibanezm
Project 3
CSC 171-22 Tues/Thurs|11:05AM-12:20PM
 */

import javax.swing.*;
import java.awt.*;

public class CyborgPlatform {
    // Main method creates the frame, starts the timer, loads all images, and spawns the Map and Entities
    public static Game game;
    public static void main(String[] args) {
        JFrame frame = new JFrame("Platform Game");
        frame.setPreferredSize(new Dimension(1280, 758));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Canvas canvas = new Canvas();
        game = new Game();
        game.loadImages();
        game.spawnEntities();
        MapBlocks.getMap();
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        game.startTimer(canvas);
    }
}
