package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Room {
    public void createRandomRoom(Random random, Position p, TETile[][] world) {
        int xSize = random.nextInt(5);
        int ySize = random.nextInt(5);
        for(int x = p.x; x < p.x + xSize; x++) {
            for(int y = p.y; y < p.y + ySize; y++) {
                if(x == p.x || y == p.y) {
                    world[x][y] = Tileset.WALL;
                } else {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }
}
