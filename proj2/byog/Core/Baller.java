package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.Serializable;
import java.util.Random;

public abstract class Baller implements Serializable {
    public int xpos;
    public int ypos;
    public Random randy;
    private boolean lastmove;
    private static final long serialVersionUID = 4L;

    public abstract void moveinput(TETile[][] world, char option);

    public abstract boolean move(TETile[][] world, int x, int y);

    public abstract void create(TETile[][] world);
    //try to implement here

    public abstract boolean portal(TETile[][] world);


    }
