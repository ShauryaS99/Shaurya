package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class WorldMaker {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    public static int xPos = 0;
    public static int yPos = 0;

    //makes vertical hall
    public static void addVertHall(TETile[][] world, int xPos, int yPos, int s) {
        int xPath = xPos;
        int yPath = yPos;
        int wall1 = xPos - 1;
        int wall2 = xPos + 1;
        for (int i = 0; i < s; i += 1) {
            world[xPath][yPath] = Tileset.FLOOR;
            world[wall1][yPath] = Tileset.WALL;
            world[wall2][yPath] = Tileset.WALL;
            yPath += 1;
        }
    }

    // makes horizontal hall
    public static void addHoriHall(TETile[][] world, int xPos, int yPos, int s, TETile t) {
        int xPath = xPos;
        int yPath = yPos;
        int wall1 = yPos - 1;
        int wall2 = yPos + 1;
        for (int i = 0; i < s; i += 1) {
            world[xPath][yPath] = Tileset.FLOOR;
            world[xPath][wall1] = Tileset.WALL;
            world[xPath][wall2] = Tileset.WALL;
            xPath += 1;
        }
    }

    // makes room YA NEED TO FIGURE OUT HOW TO WORK WITH OPENINGS AND SHIT AND HOW U HAVE WALLS FILLED WITH FLOOR
    public static void addRoom(TETile[][] world, int xPos, int yPos, int wide, int length) {
        int botLX = xPos;
        int botLY = yPos;

    }
    public static void addCorner(TETile[][] world, int xPos, int yPos, TETile t) {

    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // draws the world to the screen
        ter.renderFrame(world);
    }
}
