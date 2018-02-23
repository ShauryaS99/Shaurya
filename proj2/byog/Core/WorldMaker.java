package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class WorldMaker {

    private static final int WIDTH = 70;
    private static final int HEIGHT = 60;
    public static int xPos = 0;
    public static int yPos = 0;
    private static final Random RANDOM = new Random();
    public static int feature = 0;

    //makes vertical hall
    public static void addVertHall(TETile[][] world, int xPos, int yPos) {
        int xPath = xPos;
        int yPath = yPos;
        int wall1 = xPos - 1;
        int wall2 = xPos + 1;
        int s = RANDOM.nextInt(HEIGHT - yPath);
        for (int i = 0; i < s; i += 1) {
            world[xPath][yPath] = Tileset.FLOOR;
            world[wall1][yPath] = Tileset.WALL;
            world[wall2][yPath] = Tileset.WALL;
            yPath += 1;
        }
        feature += 1;

    }

    // makes horizontal hall
    public static void addHoriHall(TETile[][] world, int xPos, int yPos) {
        int xPath = xPos;
        int yPath = yPos;
        int wall1 = yPos - 1;
        int wall2 = yPos + 1;
        int s = RANDOM.nextInt(WIDTH - xPath);
        for (int i = 0; i < s; i += 1) {
            world[xPath][yPath] = Tileset.FLOOR;
            world[xPath][wall1] = Tileset.WALL;
            world[xPath][wall2] = Tileset.WALL;
            xPath += 1;
        }
        feature += 1;
    }

    // makes room YA NEED TO FIGURE OUT HOW TO WORK WITH OPENINGS
    public static void addRoom(TETile[][] world, int xPos, int yPos) {
        int botLX = xPos;
        int botLY = yPos;
        int wide = RANDOM.nextInt(8) + 2;
        int length = RANDOM.nextInt(8) + 2;
        for (int i = 0; i <= wide; i++) {
            for (int j = 0; j <= length; j++) {
                world[botLX + i][botLY + j] = Tileset.GRASS;
                if (i == 0 || j == 0 || i == wide || j == length) {
                    world[botLX + i][botLY + j] = Tileset.WALL;
                }
            }
        }

        //sets coordinates for door
        int door = RANDOM.nextInt(2);
        int doorY = RANDOM.nextInt(length) + botLY - 1;
        int doorX = RANDOM.nextInt(wide) + botLX - 1;

        //adds door to outer layer and connects a hallway
        switch (door) {
            case 0:
                world[wide][doorY] = Tileset.GRASS;
                WorldMaker.addHoriHall(world, wide + 1, doorY);
            case 1:
                world[doorX][length] = Tileset.GRASS;
                WorldMaker.addVertHall(world, doorX, length + 1 );
            case 2:
                world[wide][length] = Tileset.WALL;
        }
        feature += 1;
    }

    public static void RandomFeature(TETile[][] world) {
        int feature = RANDOM.nextInt(2);
        switch (feature) {
            case 0:
                WorldMaker.addVertHall(world, RANDOM.nextInt(WIDTH - 1) + 1, RANDOM.nextInt(HEIGHT - 21));
            case 1:
                WorldMaker.addHoriHall(world, RANDOM.nextInt(WIDTH - 21), RANDOM.nextInt(HEIGHT - 1) + 1);
            case 2:
                WorldMaker.addRoom(world, RANDOM.nextInt(WIDTH - 11), RANDOM.nextInt(HEIGHT - 11));
        }
    }

        public static void FillWithRandomFeatures (TETile[][]world){

            while (feature <= 30) {
                RandomFeature(world);
            }
        }
        //public static void addCorner(TETile[][] world, int xPos, int yPos) {

        //}

        public static boolean isEmpty(TETile[][] world, int xpos, int ypos) {
        if (world[xpos][ypos].equals(Tileset.NOTHING)) {
            return true;
        }
        return false;
        }

        public static void main(String[] args){
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
            FillWithRandomFeatures(world);

            // draws the world to the screen
            ter.renderFrame(world);
        }
    }

    /**
     * connect features
     * make sure that they don't overlap
     * put openings in room
     * make world traversable [final step]
     * */

