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


    public Player(int xpos, int ypos, Random randy) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.randy = randy;
    }
    public void moveinput(TETile[][] world, char option) {
        switch (option) {
            case 'W':
                boolean possiblemove = move(world, this.xpos, this.ypos + 1);
                if (possiblemove) {
                    world[this.xpos][this.ypos - 1] = Tileset.FLOOR;
                }
                break;
            case 'A':
                possiblemove = move(world, this.xpos - 1, this.ypos);
                if (possiblemove) {
                    world[this.xpos + 1][this.ypos] = Tileset.FLOOR;
                }
                break;
            case 'S':
                possiblemove = move(world, this.xpos, this.ypos - 1);
                if (possiblemove) {
                    world[this.xpos][this.ypos + 1] = Tileset.FLOOR;
                }
                break;
            case 'D':
                possiblemove = move(world, this.xpos + 1, this.ypos);
                if (possiblemove) {
                    world[this.xpos - 1][this.ypos] = Tileset.FLOOR;
                }
                break;
            default:
                break;
        }
    }
    public boolean move(TETile[][] world, int x, int y) {
        if (!(world[x][y].equals(Tileset.WALL))) {
            this.xpos = x;
            this.ypos = y;
            world[x][y] = Tileset.PLAYER;
            return true;
        }
        return false;
    }
    public void create(TETile[][] world) {
        boolean noplayer = true;
        while (noplayer) {
            xpos = randy.nextInt(Game.WIDTH - 2) + 1;
            ypos = randy.nextInt(Game.HEIGHT - 2) + 1;
            if (world[xpos][ypos].equals(Tileset.FLOOR)) { //checks for floor
                world[xpos][ypos] = Tileset.PLAYER;
                noplayer = false;
            }
        }

    }
}
