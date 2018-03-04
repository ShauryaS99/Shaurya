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
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 45);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.text(midWidth, midHeight, "BallMaster 9000");
        Font smallfont = new Font("Monaco", Font.PLAIN, 30);
        StdDraw.setFont(smallfont);
        StdDraw.text(midWidth, midHeight - 2, "New Game (N)");
        StdDraw.text(midWidth, midHeight - 4, "Load Game (L)");
        StdDraw.text(midWidth, midHeight - 6, "Quit (Q)");
    }

   /** public void display(String s) {
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.line(0, Game.HEIGHT - 5, Game.WIDTH, Game.HEIGHT - 5);
        StdDraw.textLeft(1, Game.HEIGHT - 1, location());
    }*/

    public void newgame() {
        StdDraw.clear(Color.BLACK);
        Font smallfont = new Font("Monaco", Font.PLAIN, 30);
        StdDraw.setFont(smallfont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight - 2, "Seed: ");
        String input = "";
        char key = ' ';
        int x = 5;
        while (key != 'S') {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            key = StdDraw.nextKeyTyped();
            String keyString = String.valueOf(key);
            input += String.valueOf(key);
            StdDraw.text(midWidth + x, midHeight - 2, keyString);
            x += 1;
        }
        input = 'N' + input;
        playWithInputString(input);
    }

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

    public TETile[][] giveworld() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }
    public void playWithKeyboard() {
        initialize();
        char option = ' ';
        while (option != 'N' && option != 'L' && option != 'Q' ) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            option = StdDraw.nextKeyTyped();
        }
        if (option == 'N') {
            newgame();
        } else if (option == 'L') {
            //loadgame();
        } else if (option == 'Q'){
            //quitgame
        }
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
        TETile[][] world = giveworld();


        WorldMaker.start(world);
        ter.renderFrame(world);
        active  = true;
        return world;
    }
}
