package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class HUD {
    int xPos = (int) StdDraw.mouseX();
    int yPos = (int) StdDraw.mouseY();
    static TETile[][]world;


    public void intialize(int width, int height) {
        /* Sets up StdDraw
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
    }

    public String location(TETile[][] world) {
        if (world[xPos][yPos].equals(Tileset.SAND)) {
            return Tileset.SAND.description();
        }
        if (world[xPos][yPos].equals(Tileset.GRASS)) {
            return Tileset.GRASS.description();
        }
        if (world[xPos][yPos].equals(Tileset.FLOWER)) {
            return Tileset.FLOWER.description();
        }
    return Tileset.NOTHING.description();
    }

    public void display(String s) {
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.line(0, Game.HEIGHT - 5, Game.WIDTH, Game.HEIGHT - 5);
        StdDraw.textLeft(1, Game.HEIGHT - 1, location(world));
    }
}
