package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 50;
    public boolean active = false;
    public static final int midWidth = WIDTH / 2;
    public static final int midHeight = HEIGHT / 2;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void initialize() {
        StdDraw.clear();
        StdDraw.clear(Color.BLACK);
        StdDraw.setCanvasSize(WIDTH, HEIGHT);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.text(midWidth, midHeight, "BallMaster 9000");
        Font smallfont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(smallfont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight - 5, "Young Blood");
        //StdDraw.text(midWidth, midHeight - 2, "Old Blood");
        //StdDraw.text(midWidth, midHeight - 3, "Ankles Broken");
    }

   /** public void display(String s) {
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.line(0, Game.HEIGHT - 5, Game.WIDTH, Game.HEIGHT - 5);
        StdDraw.textLeft(1, Game.HEIGHT - 1, location());
    }*/

    public String location(TETile[][] world) {
        int xPos = (int) StdDraw.mouseX();
        int yPos = (int) StdDraw.mouseY();
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

    public void playWithKeyboard() {
        initialize();


    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        String code = input.toUpperCase();
        for (int i = 0; i < code.length(); i++) {
            char x = code.charAt(i);
            if (x == 'N') {
                code = code.replace("N", "");
            }
            if (x == 'S') {
                code = code.replace("S", "");
            }
        }
        Long seed = Long.valueOf(code);
        WorldMaker.RANDOM = new Random(seed);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }


        WorldMaker.start(world);
        active  = true;
        HUD.world = world;
        return world;
    }
}
