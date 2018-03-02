package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;

public class HUD {
    int xPos = (int) StdDraw.mouseX();
    int yPos = (int) StdDraw.mouseY();

    public void MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        width = Game.WIDTH;
        height = Game.HEIGHT;

        StdDraw.setCanvasSize(width,5 );
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //Initialize random number generator
    }

    public String location(TETile[][] world) {
        if (world[xPos][yPos].equals(Tileset.SAND)) {
            return "This is a hallway";
        }
        if (world[xPos][yPos].equals(Tileset.GRASS)) {
            return "This is a room";
        }
        if (world[xPos][yPos].equals(Tileset.FLOWER)) {
            return "This is a Doorway";
        }
    return "This is nowhere";
    }

    public void display(String s) {

    }
}
