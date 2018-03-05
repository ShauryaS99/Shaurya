package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Player {

    protected static Random RANDOM = new Random();
    public int xpos;
    public int ypos;

    public Player(int xpos, int ypos) {
        this.xpos = xpos;
        this.ypos = ypos;
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
    public boolean move(TETile[][] world, int xpos, int ypos) {
        if (!(world[xpos][ypos].equals(Tileset.WALL))){
            this.xpos = xpos;
            this.ypos = ypos;
            world[xpos][ypos] = Tileset.PLAYER;
            return true;
        }
        return false;
    }
    public void create(TETile[][] world) {
        boolean noplayer = true;
        while(noplayer) {
            xpos = RANDOM.nextInt(Game.WIDTH - 2) + 1;
            ypos = RANDOM.nextInt(Game.HEIGHT - 2) + 1;
            if (world[xpos][ypos].equals(Tileset.FLOOR)) { //checks for floor
                world[xpos][ypos] = Tileset.PLAYER;
                noplayer = false;
            }
        }

    }
}
