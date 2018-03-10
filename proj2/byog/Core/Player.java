package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public class Player extends Baller implements Serializable {

    protected int xpos;
    protected int ypos;
    private static final long serialVersionUID = 3L;
    protected Random randy;
    private boolean lastmove;
    protected boolean hasbball;
    protected int score;
    protected int health;
    protected int defenders = 9;


    public Player(int xpos, int ypos, Random randy, int score, boolean hasbball, int health) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.randy = randy;
        this.score = score;
        this.hasbball = hasbball;
        this.health = health;
    }

    @Override
    public void moveinput(TETile[][] world, char option) {
        boolean lose = false;
        if (!lose) {
            boolean possiblemove;
            switch (option) {
                case 'W':
                    if (world[this.xpos][this.ypos + 1].equals(Tileset.WATER)) { //checks if player is moving to portal
                        world[this.xpos][this.ypos] = Tileset.FLOOR;
                        move(world, this.xpos, this.ypos + 1);
                    } else {
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
                    if (health <= 0) {
                        lose = true;
                        oneonone(world);
                    }
                    break;
                case 'A':
                    if (world[this.xpos - 1][this.ypos].equals(Tileset.WATER)) {
                        world[this.xpos][this.ypos] = Tileset.FLOOR;
                        move(world, this.xpos - 1, this.ypos);
                    } else {
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
                    if (health <= 0) {
                        lose = true;
                        oneonone(world);
                    }
                    break;
                case 'S':
                    if (world[this.xpos][this.ypos - 1].equals(Tileset.WATER)) {
                        world[this.xpos][this.ypos] = Tileset.FLOOR;
                        move(world, this.xpos, this.ypos - 1);
                    } else {
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
                    if (health <= 0) {
                        lose = true;
                        oneonone(world);
                    }
                    break;
                case 'D':
                    if (world[this.xpos + 1][this.ypos].equals(Tileset.WATER)) {
                        world[this.xpos][this.ypos] = Tileset.FLOOR;
                        move(world, this.xpos + 1, this.ypos);
                    } else {
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
                    if (health <= 0) {
                        lose = true;
                        oneonone(world);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public boolean oneonone(TETile[][] world) {
        if (!hasbball) {
            health -= 1;
            if (health <= 0) {
                char end = ' ';
                if (true) {
                    StdDraw.clear(Color.BLACK);
                    StdDraw.setCanvasSize(25 * 16, 25 * 16);
                    Font font = new Font("Monaco", Font.BOLD, 35);
                    StdDraw.setFont(font);
                    StdDraw.setPenColor(Color.white);
                    StdDraw.setXscale(0, Game.WIDTH);
                    StdDraw.setYscale(0, Game.HEIGHT);
                    StdDraw.clear(Color.BLACK);
                    StdDraw.text(Game.MIDWIDTH, Game.HEIGHT - 5, "GAME OVER");
                    Font smallfont = new Font("Monaco", Font.PLAIN, 20);
                    StdDraw.setFont(smallfont);
                    StdDraw.text(Game.MIDWIDTH, Game.MIDHEIGHT + 6, "You got played SUCKAAAAAA ...  ");
                    StdDraw.text(Game.MIDWIDTH, Game.MIDHEIGHT + 4, "You got " + score + " buckets");
                    StdDraw.show();
                }
                while (end != 'E') {
                    if (!StdDraw.hasNextKeyTyped()) {
                        continue;
                    }
                    end = StdDraw.nextKeyTyped();
                    end = Character.toUpperCase(end);
                    if (end == 'E') {
                        System.exit(0);
                    }
                }
            }
            world[this.xpos][this.ypos] = Tileset.FLOOR;
            for (int i = 1; i < WorldMaker.WIDTH - 1; i++) {
                for (int j = 1; j < WorldMaker.HEIGHT - 1; j++) {
                    if (world[i][j].equals(Tileset.FLOOR)) {
                        world[i][j] = Tileset.PLAYER;
                        this.xpos = i;
                        this.ypos = j;
                        return true;
                    }
                }
            }
        }
        defenders -= 1;
        return false;
    }

    public void isBball() { //needs to update HUD
        hasbball = true;
    }

    public boolean isHoop(TETile[][] world) {
        if (hasbball) {
            score += 1;
            if (score == 3) { //if you win
                char end = ' ';

                    if (true) {
                        StdDraw.clear(Color.BLACK);
                        StdDraw.setCanvasSize(25 * 16, 25 * 16);
                        Font font = new Font("Monaco", Font.BOLD, 35);
                        StdDraw.setFont(font);
                        StdDraw.setPenColor(Color.white);
                        StdDraw.setXscale(0, Game.WIDTH);
                        StdDraw.setYscale(0, Game.HEIGHT);
                        StdDraw.clear(Color.BLACK);
                        StdDraw.text(Game.MIDWIDTH, Game.HEIGHT - 5, "BALLMASTER > 9000");
                        Font smallfont = new Font("Monaco", Font.PLAIN, 20);
                        StdDraw.setFont(smallfont);
                        StdDraw.text(Game.MIDWIDTH, Game.MIDHEIGHT + 6, "Nice moves, we'll bring out the body bags... ");
                        //StdDraw.text(Game.MIDWIDTH, Game.MIDHEIGHT + 4, "You crossed " + 9 - defenders +" defenders");
                        StdDraw.show();
                    }
                    while (end != 'E') {
                        if (!StdDraw.hasNextKeyTyped()) {
                            continue;
                        }
                        end = StdDraw.nextKeyTyped();
                        end = Character.toUpperCase(end);
                        if (end == 'E') {
                            System.exit(0);
                        }
                }

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

    @Override
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

            if (world[x][y].equals(Tileset.DEFENDER)) {
                boolean loselife = oneonone(world);
                if (loselife) {
                    return false;
                }
            }

            this.xpos = x;
            this.ypos = y;

            if (world[x][y].equals(Tileset.WATER)) {
                portal(world);
                return true;

            } else if (world[x][y].equals(Tileset.Ball)) {
                    isBball();
            }
            world[x][y] = Tileset.PLAYER;
            return true;
        }
        return false;
        }

    @Override
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
            if (world[xpos][ypos].equals(Tileset.FLOOR)
                    && world[xpos - 1][ypos - 1].equals(Tileset.FLOOR)
                    && world[xpos - 1][ypos + 1].equals(Tileset.FLOOR)
                    && world[xpos + 1][ypos - 1].equals(Tileset.FLOOR)
                    && world[xpos + 1][ypos + 1].equals(Tileset.FLOOR)) {
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
