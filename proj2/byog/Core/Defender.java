package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class Defender extends Baller implements Serializable{
    private int xpos;
    private int ypos;
    private static final long serialVersionUID = 5L;
    protected Random randy;
    private boolean lastmove;


    public Defender(int xpos, int ypos, Random randy) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.randy = randy;
    }

    @Override
    public void moveinput(TETile[][] world, char useless) {
        boolean possiblemove;
        int option = randy.nextInt(4);
        switch (option) {
            case 0:
                if (world[this.xpos][this.ypos + 1].equals(Tileset.WATER)) { //checks if defender is moving to portal
                    world[this.xpos][this.ypos] = Tileset.FLOOR;
                    move(world, this.xpos, this.ypos + 1);
                }
                else {
                    possiblemove = move(world, this.xpos, this.ypos + 1);
                    if (possiblemove) {
                        if (lastmove) { //checks if defender is on portal
                            world[this.xpos][this.ypos - 1] = Tileset.WATER; //replaces portal MOVES PORTAL FIX
                            lastmove = false;
                        } else {
                            world[this.xpos][this.ypos - 1] = Tileset.FLOOR;
                        }
                    }
                }
                break;
            case 1:
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
            case 2:
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
            case 3:
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

    @Override
    public boolean move(TETile[][] world, int x, int y) {
        if (world[x][y].equals(Tileset.WALL) || world[x][y].equals(Tileset.Ball)
                || world[x][y].equals(Tileset.Hoop) || world[x][y].equals(Tileset.DEFENDER)) {
            return false;
        }
        else {
            this.xpos = x;
            this.ypos = y;
            if (world[x][y].equals(Tileset.WATER)) {
                portal(world);
                return true;
            }
            world[x][y] = Tileset.DEFENDER;
            return true;
        }

    }

    @Override
    public void create(TETile[][] world) {
        boolean noplayer = true;
        while (noplayer) {
            xpos = randy.nextInt(WorldMaker.WIDTH - 2) + 1;
            ypos = randy.nextInt(WorldMaker.HEIGHT - 2) + 1;
            if (world[xpos][ypos].equals(Tileset.FLOOR)) { //checks for floor
                world[xpos][ypos] = Tileset.DEFENDER;
                noplayer = false;
            }
        }
    }
    //try to implement here

    @Override
    public boolean portal(TETile[][] world) {
        for (int i = 0; i < WorldMaker.WIDTH - 1; i++) {
            for (int j = 0; j < WorldMaker.HEIGHT - 1; j++) {
                if (i != this.xpos) {
                    if (world[i][j].equals(Tileset.WATER)) {
                        int teleport = randy.nextInt(3);
                        if (teleport == 2) {
                            this.xpos = i;
                            this.ypos = j;
                            world[i][j] = Tileset.DEFENDER;
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



}
