package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class WorldMaker {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;
    //private static int xPos = 0;
    //private static int yPos = 0;
    protected static Random RANDOM = new Random(69);
    private static int feature = 0;

    //makes vertical hall *GET MOUNTAINS TO OVERLAP
    public static void addVert(TETile[][] world, int xPos, int yPos) {
        int xPath = xPos + 1;
        int yPath = yPos; //fixed array out of bounds
        int wall1 = xPos;
        int wall2 = xPos + 2;
        int s = WorldMaker.checkhallsize(HEIGHT, yPath);
        if (WorldMaker.isEmpty(world, xPos, yPos, xPos + 2, yPos + s)) {
            for (int i = 0; i < s; i += 1) {
                world[xPath][yPath] = Tileset.SAND;
                world[wall1][yPath] = Tileset.WALL;
                world[wall2][yPath] = Tileset.WALL;
                yPath += 1;
            }
            if (!(s == 0)) {
                world[xPath][yPath] = Tileset.MOUNTAIN; //adds moutain
                feature += 1;
            }

        }
    }

    public static int checkhallsize(int dimension, int path) {
        if ((dimension -  path - 4) <= 0) {
            return 0;
        }
        return (RANDOM.nextInt(dimension - path - 4)) % 10 + 4;
        //size: 5 - 15 makes sure there is space
    }

    // makes horizontal hall *GET MOUNTAINS TO OVERLAP
    public static void addHori(TETile[][] world, int xPos, int yPos) {
        int xPath = xPos; //fixed array out of bounds
        int yPath = yPos + 1;
        int wall1 = yPos;
        int wall2 = yPos + 2;
        int s = WorldMaker.checkhallsize(WIDTH, xPath);
        if (isEmpty(world, xPos, yPos, xPos + s, yPos + 2)) {
            for (int i = 0; i < s; i += 1) {
                world[xPath][yPath] = Tileset.SAND;
                world[xPath][wall1] = Tileset.WALL;
                world[xPath][wall2] = Tileset.WALL;
                xPath += 1;
            }
            if (!(s == 0)) {
                world[xPath][yPath] = Tileset.MOUNTAIN; //adds mountain
                feature += 1;
                addtohall(world);
            }


        }
    }

    public static int checkroomsize(int dimension, int start) {
        if (dimension -  start - 3 <= 0) {
            return 0;
        }
        return (RANDOM.nextInt(dimension - start - 3)) % 10 + 3; //size: 4 - 10
        // makes sure there is space for room
    }

    // makes room YA NEED TO FIGURE OUT HOW TO WORK WITH OPENINGS --> WE DID!!!!
    public static void addRoom(TETile[][] world, int xPos, int yPos) {
        int botLX = xPos;
        int botLY = yPos;
        int wide = WorldMaker.checkroomsize(WIDTH, botLX);
        int length = WorldMaker.checkroomsize(HEIGHT, botLY);
        if (!(wide == 0 || length == 0)) { //check if there is space in isEmpty for phase 2
            if (isEmpty(world, xPos, yPos, xPos + wide, yPos + length)) {
                for (int i = 0; i <= wide; i++) {
                    for (int j = 0; j <= length; j++) {
                        world[botLX + i][botLY + j] = Tileset.GRASS;
                        if (i == 0 || j == 0 || i == wide || j == length) {
                            world[botLX + i][botLY + j] = Tileset.WALL;
                        }
                    }
                }
                WorldMaker.flower(world, botLX, botLY, wide, length);
                feature += 1;

            }
        }
    }

    public static void flower(TETile[][] world, int botLX, int botLY, int wide, int length) {
        //randomly adds flower to room
        int doornum = RANDOM.nextInt(1) + 2; //creates random # of dooors from 2
        while (doornum >= 1) {
            int doorposx = RANDOM.nextInt(wide - 2) + botLX + 1;
            //sets random x position for opening that doesn't include corners
            int doorposy = RANDOM.nextInt(length - 2) + botLY + 1;
            //sets random x position for opening that doesn't include corners
            int randpos = RANDOM.nextInt(2);
            switch (0) { //assigns doors to one of the 2 sides of the room
                case 0:
                    world[doorposx][botLY + length] = Tileset.FLOWER;
                    world[botLX + wide][doorposy] = Tileset.FLOWER;
                    break;
                case 1:
                    world[botLX + wide][doorposy] = Tileset.FLOWER;
                    break;
                default:
                    break;
            }
            doornum -= 1;
            WorldMaker.halltoroom(world);
        }
    }

    public static void halltoroom(TETile[][] world) {
        for (int x = 1; x < WIDTH - 1; x++) {
            for (int y = 1; y < HEIGHT - 1; y++) {
                if (world[x][y].equals(Tileset.FLOWER)) { //checks for door
                    if (world[x + 1][y].equals(Tileset.WALL)
                            || world[x - 1][y].equals(Tileset.WALL)) {
                            //checks if we need vert hall
                        WorldMaker.addVert(world, x - 1, y + 1);
                    } else if (world[x][y + 1].equals(Tileset.WALL)
                            || world[x][y - 1].equals(Tileset.WALL)) {
                        //checks if we need horiz hall
                        WorldMaker.addHori(world, x + 1, y - 1);
                    }
                }
            }
        }
    }

    //ADD ROOM TO HALLWAY *** YOU NEED TO CHECK AREAS AROUND MOUNTAIN AND THEN MAKE ROOM
    public static void addtohall(TETile[][] world) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (world[x][y].equals(Tileset.MOUNTAIN)) {
                    if (world[x - 1][y].equals(Tileset.SAND)) {
                        world[x][y + 1] = Tileset.WALL;
                        world[x][y - 1] = Tileset.WALL;
                        WorldMaker.addRandomFeature(world, x + 1, y - 1);
                    } else if (world[x][y - 1].equals(Tileset.SAND)) {
                        world[x - 1][y] = Tileset.WALL;
                        world[x + 1][y] = Tileset.WALL;
                        WorldMaker.addRandomFeature(world, x - 1, y + 1);
                    }
                }
            }
        }
    }

    public static void randomFeature(TETile[][] world) {
        //int feature = RANDOM.nextInt(3);
        int type = 2;
        switch (type) {
            case 0:
                WorldMaker.addVert(world, RANDOM.nextInt(WIDTH - 2), RANDOM.nextInt(HEIGHT - 21));
                break;
            case 1:
                WorldMaker.addHori(world, RANDOM.nextInt(WIDTH - 21), RANDOM.nextInt(HEIGHT - 2));
                break;
            case 2:
                WorldMaker.addRoom(world, RANDOM.nextInt(WIDTH - 11), RANDOM.nextInt(HEIGHT - 11));
                break;
            default:
                break;
        }
    }

    public static void fillWithRandomFeatures(TETile[][] world) {

        while (feature <= 40) {
            randomFeature(world);
        }
    }

    public static void checkmountain(TETile[][] world) {
        for (int x = 1; x < WIDTH; x++) {
            for (int y = 1; y < HEIGHT ; y++) {
                if (world[x][y].equals(Tileset.MOUNTAIN)) {
                    if (x == WIDTH - 1 || y == HEIGHT - 1) {
                        world[x][y] = Tileset.WALL;
                    } //checks sand on all sides
                    if (world[x][y - 1].equals(Tileset.SAND)) {
                        if (world[x][y + 1].equals(Tileset.WALL)) {//vert halls
                            world[x][y + 1] = Tileset.FLOWER;
                            world[x][y] = Tileset.SAND;
                        }
                        else if (world[x][y + 1].equals(Tileset.SAND)) { //
                            world[x][y] = Tileset.SAND;
                        }
                    } else if (world[x - 1][y].equals(Tileset.SAND)) {
                        if (world[x + 1][y].equals(Tileset.WALL)) { //horiz halls
                            world[x + 1][y] = Tileset.FLOWER;
                            world[x][y] = Tileset.SAND;
                        }
                        else if (world[x + 1][y].equals(Tileset.SAND)) {
                            world[x][y] = Tileset.SAND;
                        }
                    } if (world[x][y + 1].equals(Tileset.NOTHING) || world[x + 1][y].equals(Tileset.NOTHING)) {
                        world[x][y] = Tileset.WALL;
                    }
                }
            }
        }
    }

    public static void checkflower(TETile[][]world) {
        for (int x = 1; x < WIDTH; x++) {
            for (int y = 1; y < HEIGHT; y++) {
                if (world[x][y].equals(Tileset.FLOWER)) {
                    if (x == WIDTH - 1 || y == HEIGHT - 1) {
                        world[x][y] = Tileset.WALL;
                    } //checks sand on all sides
                    else if (!(world[x + 1][y].equals(Tileset.SAND) || world[x][y + 1].equals(Tileset.SAND)
                            || world[x - 1][y].equals(Tileset.SAND) || world[x][y - 1].equals(Tileset.SAND))
                            || (world[x][y + 1].equals(Tileset.NOTHING))) {
                        world[x][y] = Tileset.WALL;
                    }
                }
            }
        }
    }

    // no more randy shit everywhere
    public static void addRandomFeature(TETile[][] world, int xPos, int yPos) {
        int type = RANDOM.nextInt(3);
        switch (2) {
            case 0:
                WorldMaker.addVert(world, xPos, yPos);
                break;
            case 1:
                WorldMaker.addHori(world, xPos, yPos);
                break;
            case 2:
                WorldMaker.addRoom(world, xPos, yPos);
                break;
            default:
                break;
        }
    }
     // starter room
    public static void start(TETile[][] world) {
        WorldMaker.addRoom(world, 0, 0);
        WorldMaker.checkmountain(world);
        try {
            WorldMaker.checkflower(world);
        }
        catch (IndexOutOfBoundsException e) {

        }

    }




    //public static void addCorner(TETile[][] world, int xPos, int yPos) {
    //}

    public static void overlap(TETile[][] world, int xpos, int ypos) {
        //if (isEmpty(world, xpos, ypos)) {

        //}
    }

    //TRY TO GET MOUNTAINS TO OVERLAP
    public static boolean isEmpty(TETile[][] world, int xinit, int yinit, int xfin, int yfin) {
        for (int x = xinit; x <= xfin; x++) {
            for (int y = yinit; y <= yfin; y++) {
                if (!(world[x][y].equals(Tileset.NOTHING))) {
                    return false;
                }
            }
        }
        return true;
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


        start(world);
        //fillWithRandomFeatures(world);

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

