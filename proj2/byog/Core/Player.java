package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class Player implements Serializable {

    private int xpos;
    private int ypos;
    private static final long serialVersionUID = 3L;
    protected Random randy;
    private boolean lastmove;
    private boolean hasbball = false;
    static int score = 0;


    public Player(int xpos, int ypos, Random randy) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.randy = randy;
    }
    public void moveinput(TETile[][] world, char option) {
        boolean possiblemove;
        switch (option) {
            case 'W':
                if (world[this.xpos][this.ypos + 1].equals(Tileset.WATER)) { //checks if player is moving to portal
                    world[this.xpos][this.ypos] = Tileset.FLOOR;
                    move(world, this.xpos, this.ypos + 1);
                }
                else {
                    possiblemove = move(world, this.xpos, this.ypos + 1);
                    if (possiblemove) {
                        if (lastmove) { //checks if player is on portal
                            world[this.xpos][this.ypos - 1] = Tileset.WATER; //replaces portal MOVES PORTAL FIX
                            lastmove = false;
                        } else {
                            world[this.xpos][this.ypos - 1] = Tileset.FLOOR;
                        }
                    }
                }


                break;
            case 'A':
                if (world[this.xpos - 1][this.ypos].equals(Tileset.WATER)) {
                    world[this.xpos][this.ypos] = Tileset.FLOOR;
                    move(world, this.xpos - 1, this.ypos);
                }
                else {
                    possiblemove = move(world, this.xpos - 1, this.ypos);
                    if (possiblemove) {
                        if (lastmove) {
                            world[this.xpos + 1][this.ypos] = Tileset.WATER;
                            lastmove = false;
                        } else {
                            world[this.xpos + 1][this.ypos] = Tileset.FLOOR;
                        }
                    }
                }

                break;
            case 'S':
                if (world[this.xpos][this.ypos - 1].equals(Tileset.WATER)) {
                    world[this.xpos][this.ypos] = Tileset.FLOOR;
                    move(world, this.xpos, this.ypos - 1);
                }
                else {
                    possiblemove = move(world, this.xpos, this.ypos - 1);
                    if (possiblemove) {
                        if (lastmove) {
                            world[this.xpos][this.ypos + 1] = Tileset.WATER;
                            lastmove = false;
                        } else {
                            world[this.xpos][this.ypos + 1] = Tileset.FLOOR;
                        }
                    }
                }

                break;
            case 'D':
                if (world[this.xpos + 1][this.ypos].equals(Tileset.WATER)) {
                    world[this.xpos][this.ypos] = Tileset.FLOOR;
                    move(world, this.xpos + 1, this.ypos);
                }
                else {
                    possiblemove = move(world, this.xpos + 1, this.ypos);
                    if (possiblemove) {
                        if (lastmove) {
                            world[this.xpos - 1][this.ypos] = Tileset.WATER;
                            lastmove = false;
                        } else {
                            world[this.xpos - 1][this.ypos] = Tileset.FLOOR;
                        }
                    }
                }

                break;
            default:
                break;
        }
    }

    public void isBball() { //needs to update HUD
        hasbball = true;
    }

    public boolean isHoop(TETile[][] world) {
        if (hasbball) {
            score += 1;
            if (score == 3) { //if you win
                System.out.println("YOU WIN!!!!"); //change for HUD
                System.exit(0);
            }
            makeball(world);
            hasbball = false;
            makehoop(world);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean portal(TETile[][] world) {
        for (int i = 0; i < WorldMaker.WIDTH - 1; i++) {
            for (int j = 0; j < WorldMaker.HEIGHT - 1; j++) {
                if (i != this.xpos) {
                    if (world[i][j].equals(Tileset.WATER)) {
                        int teleport = randy.nextInt(3);
                        if (teleport == 2) {
                            this.xpos = i;
                            this.ypos = j;
                            world[i][j] = Tileset.PLAYER;
                            lastmove = true;
                            return true;
                        }
                    }
                }
            }
        }
        portal(world);
        return false;
    }
    public boolean move(TETile[][] world, int x, int y) {
        if (!(world[x][y].equals(Tileset.WALL))) {
            if (world[x][y].equals(Tileset.Hoop)) {
                boolean canhoop = isHoop(world);
                if (canhoop) { //checks if you have the bball
                    this.xpos = x; //sets your x,y pos
                    this.ypos = y;
                    world[x][y] = Tileset.PLAYER;
                }
                return canhoop;
            }
            this.xpos = x;
            this.ypos = y;
            if (world[x][y].equals(Tileset.WATER)) {
                portal(world);
                return true;
            }
            else if (world[x][y].equals(Tileset.Ball)) {
                isBball();
                System.out.println(hasbball);
            }
            world[x][y] = Tileset.PLAYER;
            return true;
        }
        return false;
    }
    public void create(TETile[][] world) {
        boolean noplayer = true;
        while (noplayer) {
            xpos = randy.nextInt(WorldMaker.WIDTH - 2) + 1;
            ypos = randy.nextInt(WorldMaker.HEIGHT - 2) + 1;
            if (world[xpos][ypos].equals(Tileset.FLOOR)) { //checks for floor
                world[xpos][ypos] = Tileset.PLAYER;
                noplayer = false;
            }
        }
        makehoop(world);
        makeball(world);

    }

    public void makehoop(TETile[][] world) { //creates 1 hoop
        int basket = 1;
        while (basket > 0) {
            int xpos = randy.nextInt(WorldMaker.WIDTH - 2) + 1;
            int ypos = randy.nextInt(WorldMaker.HEIGHT - 2) + 1;
            if (world[xpos][ypos].equals(Tileset.FLOOR)) {
                world[xpos][ypos] = Tileset.Hoop;
                basket--;
            }
        }
    }

    public void makeball(TETile[][] world) { //creates 1 basketball
        int bball = 1;
        while (bball > 0) {
            int xpos = randy.nextInt(WorldMaker.WIDTH - 2) + 1;
            int ypos = randy.nextInt(WorldMaker.HEIGHT - 2) + 1;
            if (world[xpos][ypos].equals(Tileset.FLOOR)) {
                world[xpos][ypos] = Tileset.Ball;
                bball--;
            }
        }
    }
}
