package byog.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile PLAYER = new TETile('♚', Color.black, Color.lightGray,
            "Player - you have but one goal in mind... the basket");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "Wall - you scream and one as you get blatantly fouled " +
                    "by Zaza Pachulia");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "Floor - the hardwood court squeaks as your brand " +
                    "new air jordans brush against it");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "Nothing - " +
            "you are out of bounds young blood");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "Water - " +
            "You unleash a devastating burst of power as you leap across the paint with your hopstep");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile Ball = new TETile('֍', Color.ORANGE, Color.black, "Ball - you pick up " +
            "the ball, time to go James Harden on these fools ... (RIP Wesley Johnson 1987 - 2018)");
    public static final TETile Hoop = new TETile('℺', Color.red, Color.black, "Hoop - do you have ice " +
            "in your veins? Time to find out");
    public static final TETile DEFENDER = new TETile('Ð', Color.white, Color.black, "Defender - " +
            "put this young blood on skates");
}


