package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class HUD {
    private boolean gameOver = false;
    int xPos = (int) StdDraw.mouseX();
    int yPos = (int) StdDraw.mouseY();
<<<<<<< HEAD
    static TETile[][]world;


    public void intialize(int width, int height) {
        /* Sets up StdDraw
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        width = Game.WIDTH;
        height = Game.HEIGHT;
=======
    int width = Game.WIDTH;
    int height = Game.HEIGHT;

    public void MemoryGame(int width, int height, long seed) { //probs don't need first 2 @params
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
>>>>>>> f0e338aa92245a5af6e014faf62a4c76692d759e
        StdDraw.setCanvasSize(width,5 );
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void location(TETile[][] world) {
        if (world[xPos][yPos].equals(Tileset.SAND)) {
<<<<<<< HEAD
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
=======
            display("This is a hallway");
        }
        if (world[xPos][yPos].equals(Tileset.GRASS)) {
            display("This is a room");
        }
        if (world[xPos][yPos].equals(Tileset.FLOWER)) {
            display( "This is a Doorway");
        }
    //return "This is nowhere";
    }

    public void display(String s) {
        int midWidth = width / 2;
        int midHeight = height / 2;

        StdDraw.clear();
        StdDraw.clear(Color.black);

        // Draw the GUI
        if (!gameOver) {
            Font smallFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(smallFont);
            StdDraw.textLeft(1, height - 1, s);
            //StdDraw.text(midWidth, height - 1, playerTurn ? "Type!" : "Watch!");
            //StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.line(0, height - 2, width, height - 2);
        }

        // Draw the actual text
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight, s);
        StdDraw.show();

>>>>>>> f0e338aa92245a5af6e014faf62a4c76692d759e
    }
}
