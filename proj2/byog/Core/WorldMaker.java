package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class WorldMaker {

    private static final int WIDTH = 70;
    private static final int HEIGHT = 70;
    public static int xPos = 0;
    public static int yPos = 0;
    private static final Random RANDOM = new Random();
    public static int feature = 0;
    public static int option = 0;

    //makes vertical hall
    public static void addVertHall(TETile[][] world, int xPos, int yPos) {
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

    public static int checkhallsize(int DIMENSION, int path) {
        if ((DIMENSION -  path - 4) <= 0) {
            return 0;
        }
        return (RANDOM.nextInt(DIMENSION - path - 4)) % 10 + 4; //size: 5 - 15 makes sure there is space for hall
    }

    // makes horizontal hall
    public static void addHoriHall(TETile[][] world, int xPos, int yPos) {
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
            }
        }
    }

    public static int checkroomsize(int DIMENSION,int start) {
        if (DIMENSION -  start - 3 <= 0) {
            return 0;
        }
        return (RANDOM.nextInt(DIMENSION- start - 3)) % 10 + 3; //size: 4 - 10 makes sure there is space for room
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

    public static void flower(TETile[][] world, int botLX, int botLY, int wide, int length) { //randomly adds flower to room
        int doornum = RANDOM.nextInt(2) + 1; //creates random # of dooors from 1-2
        while (doornum >= 1) {
            int doorposx = RANDOM.nextInt(wide - 2) + botLX + 1; //sets random x position for opening that doesn't include corners
            int doorposy = RANDOM.nextInt(length - 2) + botLY + 1; //sets random x position for opening that doesn't include corners
            int randpos = RANDOM.nextInt(2);
            switch(randpos) { //assigns doors to one of the 2 sides of the room
                case 0:
                    world[doorposx][botLY + length] = Tileset.FLOWER;
                    break;
                case 1:
                    world[botLX + wide][doorposy] = Tileset.FLOWER;
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
                    if (world[x+1][y].equals(Tileset.WALL) || world[x-1][y].equals(Tileset.WALL)) { //checks if we need vert hall
                        WorldMaker.addVertHall(world, x - 1, y + 1);
                    }
                    else if (world[x][y + 1].equals(Tileset.WALL) || world[x][y - 1].equals(Tileset.WALL)) { //checks if we need horiz hall
                        WorldMaker.addHoriHall(world, x + 1, y - 1);
                    }
                }
            }
        }
    }

    public static void roomtohall(TETile[][] world) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (world[x][y].equals(Tileset.MOUNTAIN)) {
                    //create room from bottom left corner
                }
            }
        }
    }

    public static void RandomFeature(TETile[][] world) {
        //int feature = RANDOM.nextInt(3);
        int feature = 2;
        switch (feature) {
            case 0:
                WorldMaker.addVertHall(world, RANDOM.nextInt(WIDTH - 2), RANDOM.nextInt(HEIGHT - 21));
                break;
            case 1:
                WorldMaker.addHoriHall(world, RANDOM.nextInt(WIDTH - 21), RANDOM.nextInt(HEIGHT - 2));
                break;
            case 2:
                WorldMaker.addRoom(world, RANDOM.nextInt(WIDTH - 11), RANDOM.nextInt(HEIGHT - 11));
                break;
        }
    }

        public static void FillWithRandomFeatures (TETile[][]world){

            while (feature <= 40) {
                RandomFeature(world);
            }
        }
        //public static void addCorner(TETile[][] world, int xPos, int yPos) {

        //}
        public static void Overlap(TETile[][] world, int xpos, int ypos) {
            //if (isEmpty(world, xpos, ypos)) {

            //}
        }
        public static boolean isEmpty(TETile[][] world, int xintial, int yintial, int xfinal, int yfinal) {
            for (int x = xintial; x <= xfinal; x++) {
                for (int y = yintial; y <= yfinal; y++) {
                    if (!(world[x][y].equals(Tileset.NOTHING))) {
                        return false;
                    }

                }

            }
            return true;
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

