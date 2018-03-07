package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;
    private boolean active = false;
    public static final int MIDWIDTH = WIDTH / 2;
    public static final int MIDHEIGHT = HEIGHT / 2;
    private TETile[][] world;
    private Player p;
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
        StdDraw.text(MIDWIDTH, MIDHEIGHT, "BallMaster 9000");
        Font smallfont = new Font("Monaco", Font.PLAIN, 30);
        StdDraw.setFont(smallfont);
        StdDraw.text(MIDWIDTH, MIDHEIGHT - 2, "New Game (N)");
        StdDraw.text(MIDWIDTH, MIDHEIGHT - 4, "Load Game (L)");
        StdDraw.text(MIDWIDTH, MIDHEIGHT - 6, "Quit (Q)");
    }

    public void display() {
        StdDraw.clear();
        ter.renderFrame(world);
        StdDraw.enableDoubleBuffering();
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.white);
        //StdDraw.line(0, Game.HEIGHT - 2, Game.WIDTH, Game.HEIGHT - 2);
        StdDraw.textLeft(1, Game.HEIGHT - 1, location());
        StdDraw.show();
    }

    public void newgame() {
        StdDraw.clear(Color.BLACK);
        Font smallfont = new Font("Monaco", Font.PLAIN, 30);
        StdDraw.setFont(smallfont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(MIDWIDTH, MIDHEIGHT - 2, "Seed: ");
        String input = "";
        char key = ' ';
        int x = 5;
        while (key != 'S') {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            key = StdDraw.nextKeyTyped();
            key = Character.toUpperCase(key);
            String keyString = String.valueOf(key);
            input += String.valueOf(key);
            StdDraw.text(MIDWIDTH + x, MIDHEIGHT - 2, keyString);
            x += 1;
        }
        input = 'N' + input;
        playWithInputString(input);
    }

    private static void saveWorld(TETile[][] w) {
        File f = new File("./game.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(w);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private static void savePlayer(Player p) {
        File f = new File("./player.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(p);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private static Player loadPlayer() {
        File f = new File("./player.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                Player loadPlayer = (Player) os.readObject();
                os.close();
                return loadPlayer;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        return null;
    }

    private static TETile[][] loadWorld() {
        File f = new File("./game.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                TETile[][] loadWorld = (TETile[][]) os.readObject();
                os.close();
                return loadWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        /* In the case no World has been saved yet, we return a new one. */
        return null;
    }

    public String location() {
        int xPos = (int) StdDraw.mouseX();
        int yPos = (int) StdDraw.mouseY();
        if (xPos == WIDTH) {
            xPos = WIDTH - 1;
        }
        if (yPos == HEIGHT) {
            yPos = HEIGHT - 1;
        }
        if (world[xPos][yPos].equals(Tileset.FLOOR)) {
            return Tileset.FLOOR.description();
        }
        if (world[xPos][yPos].equals(Tileset.WALL)) {
            return Tileset.WALL.description();
        }
        return Tileset.NOTHING.description();
    }

    public void playWithKeyboard() {
        initialize();
        char option = ' ';
        while (option != 'N' && option != 'L' && option != 'Q') {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            option = StdDraw.nextKeyTyped();
            option = Character.toUpperCase(option);
        }


        if (option == 'N') {
            newgame();
        } else if (option == 'L') {
            ter.initialize(WIDTH, HEIGHT);
            world = loadWorld();
            p = loadPlayer();
        } else if (option == 'Q') {
            System.exit(0);
        }

        //Player player = new Player(0,0);
        //player.create(world);
        ter.renderFrame(world);

        active  = true;
        while (active) {
            option = ' ';
            while (option != 'W' && option != 'A' && option != 'S' && option != 'D') {
                if (!StdDraw.hasNextKeyTyped()) {
                    display();
                    continue;
                }
                if (option == ':') {
                    if (StdDraw.nextKeyTyped() == 'Q') {
                        saveWorld(world);
                        savePlayer(p);
                        System.exit(0);
                        break;
                    }
                    option = ' ';
                    continue;
                }
                option = StdDraw.nextKeyTyped();
                option = Character.toUpperCase(option);
            }
            p.moveinput(world, option);
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
        String inputs = "";
        for (int i = 0; i < code.length(); i++) {
            char x = code.charAt(i);
            if (x == 'N') {
                code = code.replace("N", "");
            }
            if (x == 'S') {
                code = code.replace("S", "");
                for (; i < code.length(); i++) {                     //points char after S
                    inputs += code.charAt(i);
                }
                code = code.replace(inputs, "");
            }
            if (x == 'L') {
                i++;
                for (; i < code.length(); i++) { //points char after s
                    inputs += code.charAt(i);
                }
                //ter.initialize(WIDTH, HEIGHT);
                world = loadWorld();
                p = loadPlayer();
                String move = inputs.toUpperCase();
                if (!(move.length() == 0)) {
                    for (int k = 0; k < move.length(); k++) {
                        char y = move.charAt(k);
                        p.moveinput(world, y);
                        if (y == ':') {
                            if (move.charAt(k + 1) == 'Q') {
                                saveWorld(world);
                                savePlayer(p);
                                break;
                            }
                            break;
                        }
                    }
                }
                //ter.renderFrame(world);
                return world;
            }
        }
        Long seed = Long.valueOf(code);
        //ter.initialize(WIDTH, HEIGHT);
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Random randy = new Random(seed);
        WorldMaker dungeon = new WorldMaker(world, randy);
        dungeon.start();
        String move = inputs.toUpperCase();
        if (move.length() != 0) {
            p = new Player(0, 0, randy);
            p.create(world);
            for (int i = 0; i < move.length(); i++) {
                char x = move.charAt(i);
                p.moveinput(world, x);
                if (x == ':') {
                    if (move.charAt(i + 1) == 'Q') {
                        saveWorld(world);
                        savePlayer(p);
                        System.out.println("world and player saved");
                        break;
                    }
                    break;
                }
            }
        } else {
            p = new Player(0, 0, randy);
            p.create(world);
        }
        //ter.renderFrame(world);
        return world;
    }
}
