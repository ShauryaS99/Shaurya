package byog.Core;


import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class WorldMaker implements Serializable{

    private static final int WIDTH = Game.WIDTH;
    private static final int HEIGHT = Game.HEIGHT - 2;
    protected static Random RANDOM = new Random();
    private static final long serialVersionUID = 2L;
    public TETile[][] world;

    public WorldMaker(TETile[][]world, long seed) {
        RANDOM = new Random(seed);
        this.world = world;
    }


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
            }

        }
    }

    public static int checkhallsize(int dimension, int path) {
        if ((dimension -  path - 4) <= 0) {
            return 0;
        }
        return (RANDOM.nextInt(dimension - path - 4)) % 5 + 4;
        //size: 4 - 8 makes sure there is space
    }

    public static void turnhall(TETile[][] world, int xPos, int yPos, boolean horiz) {
        int offset = RANDOM.nextInt(2) + 1; //offset from 1 - 2
        if (horiz) {
            addHori(world, xPos - offset, yPos);
        }
        else {
            addVert(world, xPos, yPos - offset);
        }
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
                addtohall(world);
            }
        }
    }

    public static int checkroomsize(int dimension, int start) {
        if (dimension -  start - 5 <= 0) {
            return 0;
        }
        return (RANDOM.nextInt(dimension - start - 5)) % 6 + 5; //size: 5 - 8
        // makes sure there is space for room
    }

    public static void offsetroom(TETile[][] world, int xPos, int yPos, boolean horiz) {
        int offset = RANDOM.nextInt(3) + 1; //offset from 1 - 3
        if (horiz) {
            addRoom(world, xPos - offset, yPos);
        }
        else {
            addRoom(world, xPos, yPos - offset);
        }
    }

    // makes room YA NEED TO FIGURE OUT HOW TO WORK WITH OPENINGS --> WE DID!!!!
    public static void addRoom(TETile[][] world, int xPos, int yPos) {
        int botLX = xPos;
        int botLY = yPos;
        int wide = checkroomsize(WIDTH, botLX);
        int length = checkroomsize(HEIGHT, botLY);
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
                flower(world, botLX, botLY, wide, length);
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
                        int offset = RANDOM.nextInt(3) + 1; //offset from 1 - 3
                        WorldMaker.addVert(world, x - 1, y + 1);
                    } else if (world[x][y + 1].equals(Tileset.WALL)
                            || world[x][y - 1].equals(Tileset.WALL)) {
                        //checks if we need horiz hall
                        int offset = RANDOM.nextInt(3) + 1; //offset from 1 - 3
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
                        WorldMaker.addRandomFeature(world, x + 1, y - 1, false);
                    } else if (world[x][y - 1].equals(Tileset.SAND)) {
                        world[x - 1][y] = Tileset.WALL;
                        world[x + 1][y] = Tileset.WALL;
                        WorldMaker.addRandomFeature(world, x - 1, y + 1, true);
                    }
                }
            }
        }
    }



    public static void checkmountain(TETile[][] world) {
        for (int x = 1; x < WIDTH; x++) {
            for (int y = 1; y < HEIGHT; y++) {
                if (world[x][y].equals(Tileset.MOUNTAIN)) {
                    if (x == WIDTH - 1 || y == HEIGHT - 1) {
                        world[x][y] = Tileset.WALL;
                    } else  { //check if you need to change if statements to else if statments!!
                        if (world[x][y - 1].equals(Tileset.SAND)) {
                            if (world[x][y + 1].equals(Tileset.WALL)) { //vert halls
                                world[x][y + 1] = Tileset.FLOWER;
                                world[x][y] = Tileset.SAND;
                            } else if (world[x][y + 1].equals(Tileset.SAND)) { //
                                world[x][y] = Tileset.SAND;
                            }
                        } else if (world[x - 1][y].equals(Tileset.SAND)) {
                            if (world[x + 1][y].equals(Tileset.WALL)) { //horiz halls
                                world[x + 1][y] = Tileset.FLOWER;
                                world[x][y] = Tileset.SAND;
                            } else if (world[x + 1][y].equals(Tileset.SAND)) {
                                world[x][y] = Tileset.SAND;
                            }
                        }
                        if (world[x][y + 1].equals(Tileset.NOTHING)
                                || world[x + 1][y].equals(Tileset.NOTHING)) {
                            world[x][y] = Tileset.WALL;
                        }
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
                    } else if (!(world[x + 1][y].equals(Tileset.SAND)
                            || world[x][y + 1].equals(Tileset.SAND)
                            || world[x - 1][y].equals(Tileset.SAND)
                            || world[x][y - 1].equals(Tileset.SAND))
                            || (world[x][y + 1].equals(Tileset.NOTHING))) {
                        world[x][y] = Tileset.WALL;
                        //checks sand on all sides
                    }
                }
            }
        }
    }

    // no more randy shit everywhere
    public static void addRandomFeature(TETile[][] world, int xPos, int yPos, boolean horiz) {
        int type = 1;
        if (xPos > 10 && yPos > 10) {
            type = RANDOM.nextInt(2);
        }
        switch (type) {
            case 0:
                turnhall(world, xPos, yPos, horiz);
                break;
            case 1:
            case 2:
                offsetroom(world, xPos, yPos, horiz);
                break;
            default:
                break;
        }
    }
     // starter room
    public void start() {
        addRoom(this.world, 0, 0);
        checkmountain(this.world);
        checkflower(this.world);
        purge(this.world);
    }

    public static void purge(TETile[][] world) {
        for (int x = 1; x < WIDTH; x++) {
            for (int y = 1; y < HEIGHT; y++) {
                if (!(world[x][y].equals(Tileset.NOTHING) || world[x][y].equals(Tileset.WALL))) {
                    world[x][y] = Tileset.FLOOR; //makes floors
                    if (world[x + 1][y].equals(Tileset.NOTHING)) { //checks for nothing tiles next to floor
                        world[x + 1][y] = Tileset.WALL;
                    }
                    if (world[x][y + 1].equals(Tileset.NOTHING)) {
                        world[x][y + 1] = Tileset.WALL;
                    }
                    if (world[x - 1][y].equals(Tileset.NOTHING)) {
                        world[x - 1][y] = Tileset.WALL;
                    }
                    if (world[x][y - 1].equals(Tileset.NOTHING)) {
                        world[x][y - 1] = Tileset.WALL;
                    }
                    if (world[x - 1][y - 1].equals(Tileset.NOTHING)) { //checks for corner nothing tiles
                        world[x - 1][y - 1] = Tileset.WALL;
                    }
                    if (world[x + 1][y - 1].equals(Tileset.NOTHING)) {
                        world[x + 1][y - 1] = Tileset.WALL;
                    }
                    if (world[x + 1][y + 1].equals(Tileset.NOTHING)) {
                        world[x + 1][y + 1] = Tileset.WALL;
                    }
                    if (world[x -1][y + 1].equals(Tileset.NOTHING)) {
                        world[x - 1][y + 1] = Tileset.WALL;
                    }
                }
            }
        }

    }


    //TRY TO GET MOUNTAINS TO OVERLAP
    public static boolean isEmpty(TETile[][] world, int xinit, int yinit, int xfin, int yfin) {
        if (xinit < 0 || yinit < 0 || xfin >= WIDTH || yfin >= HEIGHT) {
            return false;
        }
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
       /** System.out.println("fix main");
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        //HUD hud = new HUD();

        start(world);
        //fillWithRandomFeatures(world);

        // draws the world to the screen
        ter.renderFrame(world);*/
    }

}

    /**
     * connect features
     * make sure that they don't overlap
     * put openings in room
     * make world traversable [final step]
     * */

