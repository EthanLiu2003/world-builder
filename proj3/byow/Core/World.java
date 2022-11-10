package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class World {
    public static void generateWorld(Random seed, TETile[][] world, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++)
                world[x][y] = Tileset.NOTHING;
        }
    }

    public void fillWithRooms(Random seed, TETile[][] world, int width, int height) {
        //fill with random number of rooms of random size with position determined by random number generator
    }
}
