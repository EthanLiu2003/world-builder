package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;


public class Path {

    private int width;
    private int height;

    public Path(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void connect(Position p1, Position p2, TETile[][] world) {
        if (p1.x > p2.x) {
            Position save = p1;
            p1 = p2;
            p2 = save;
        }
        int yDistance = p1.y - p2.y;
        int x = p1.x;
        int y = p1.y;
        world[x][p1.y] = Tileset.FLOOR;
        while (x != p2.x && x < (width - 1)) {
            x += 1;
            world[x][p1.y] = Tileset.FLOOR;
        }
        if (yDistance > 0) {
            world[p2.x][y] = Tileset.FLOOR;
            while (y != p2.y && y > 1) {
                y -= 1;
                world[p2.x][y] = Tileset.FLOOR;
            }
        } else if (yDistance < 0) {
            world[p2.x][y] = Tileset.FLOOR;
            while (y != p2.y && y < (height - 1)) {
                y += 1;
                world[p2.x][y] = Tileset.FLOOR;
            }
        }
    }
}
