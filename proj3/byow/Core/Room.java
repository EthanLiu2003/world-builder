package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashSet;

public class Room {
    Position p;
    int width;
    int height;

    public Room(Position p, int width, int height) {
        this.p = p;
        this.width = width;
        this.height = height;
    }

    public Position centerPosition() {
        return new Position(p.x + (width / 2) + 1, p.y + (height / 2) + 1);
    }

    public void createRoom(Position p, TETile[][] world) { // draw floors and walls
        HashSet<Position> toReturn = new HashSet<>();
        for (int x = p.x; x < p.x + width; x++) {
            for (int y = p.y; y < p.y + height; y++) {
                world[x][y] = Tileset.FLOOR;
            }
        }
    }
}
