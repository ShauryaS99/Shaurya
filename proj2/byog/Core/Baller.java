package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.Serializable
import java.util.Random;

public abstract class Baller implements Serializable {
    public int xpos;
    public int ypos;
    public Random randy;
    private static final long serialVersionUID = 4L;

}
