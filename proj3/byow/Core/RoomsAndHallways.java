package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class RoomsAndHallways {
    private TETile[][] world;

    public RoomsAndHallways(TETile[][] world) {
        this.world = world;
    }

    public void createRoom(Position p, TETile[][] world, int xSize, int ySize) { // draw floors and walls
        for (int x = p.x; x < p.x + xSize; x++) {
            for (int y = p.y; y < p.y + ySize; y++) {
                if (x == p.x || y == p.y || x == (p.x - 1) + xSize || y == (p.y - 1) + ySize) {
                    world[x][y] = Tileset.WALL;
                } else {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    public void createUpHallway(Position p1, Position p2, TETile[][] world, int height) {
        // make p1 whichever is lower
        if (p1.y > p2.y) {
            Position save = p1;
            p1 = p2;
            p2 = save;
        }
        if (p1.y + 2 > height) {
            return;
        }
        world[p1.x][p1.y] = Tileset.FLOOR; // turn current wall tile to floor tile
        for (int y = p1.y + 1; y < p2.y; y++) { // iterate thru all y + length positions
            if (y + 2 > height) { // ensures within bounds of board
                for (int x = p1.x - 1; x < p1.x + 2; x++) { // curr x pos row
                    if (x == p1.x) {
                        world[x][y] = Tileset.FLOOR;
                    } else {
                        world[x][y] = Tileset.WALL;
                    }
                }
                for (int x = p1.x - 1; x < p1.x + 2; x++) { // create wall border
                    world[x][y + 1] = Tileset.WALL;
                }
                return;
            }
            if (world[p1.x][y] == Tileset.WALL) { // if overlapping walls, convert to floor
                world[p1.x][y] = Tileset.FLOOR;
                return;
            }
            for (int x = p1.x - 1; x < p1.x + 2; x++) {
                if (y == p1.y) {
                    world[x][y] = Tileset.WALL;
                    continue;
                }
                if (x == p1.x) {
                    world[x][y] = Tileset.FLOOR;
                } else {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }

    public void createDownHallway(Position p1, Position p2, TETile[][] world) {
        // make p1 whichever is higher
        if (p1.y < p2.y) {
            Position save = p1;
            p1 = p2;
            p2 = save;
        }
        if (p1.y - 2 < 0 || p2.y - 2 < 0) {
            return;
        }
        world[p1.x][p1.y] = Tileset.FLOOR; // turn current wall into floor
        for (int y = p1.y - 1; y > p2.y; y--) { // turn next tiles into floors go from first pos to second
            if (y - 2 < 0) { // make sure within bounds of world...
                for (int x = p1.x - 1; x < p1.x + 2; x++) {
                    if (x == p1.x) {
                        world[x][y] = Tileset.FLOOR;
                    } else {
                        world[x][y] = Tileset.WALL;
                    }
                }
//                for (int x = p1.x - 1; x < p1.x + 2; x++) {
//                    world[x][y - 1] = Tileset.WALL;
//                }
//                return;
            }
            if (world[p1.x][y] == Tileset.WALL) {
                world[p1.x][y] = Tileset.FLOOR;
                return;
            }
            for (int x = p1.x - 1; x < p1.x + 2; x++) {
                if (y == p1.y) {
                    world[x][y] = Tileset.WALL;
                    continue;
                }
                if (x == p1.x) {
                    world[x][y] = Tileset.FLOOR;
                } else {
                    world[x][y] = Tileset.WALL;
                }
            }
//            if (p1.x != p2.x) {
//                if (p1.x > p2.x) {
//                    createLeftHallway(p1, p2, world);
//                }
////                else {
////                    createLeftHallway(p2, p1, world);
////                }
//                // conenct to another hallway
//                // if p1.x > p2.x --> call createlefthallway on p1
//            }
        }
    }

    public void createRightHallway(Position p1, Position p2, TETile[][] world, int width) {
        if (p1.y < p2.y) {
            Position save = p1;
            p1 = p2;
            p2 = save;
        }
        if (p1.x + 2 > width) {
            return;
        }
        world[p1.x][p1.y] = Tileset.FLOOR;
        for (int x = p1.x + 1; x < p2.x; x++) { // moves right one at a time
            if (x + 2 > width) { // loop for if at end of world
                for (int y = p1.y - 1; y < p1.y + 2; y++) { // at specific x,
                    if (y == p1.y) {
                        world[x][y] = Tileset.FLOOR;
                    } else {
                        world[x][y] = Tileset.WALL; // builds left wall
                    }
                }
                for (int y = p1.y - 1; y < p1.y + 2; y++) {
                    world[x + 1][y] = Tileset.WALL;// builds wall around row of floors
                }
                return;
            }
            // not at end of world
            if (world[x][p1.y] == Tileset.WALL) { // intersection of walls turn into floor
                world[x][p1.y] = Tileset.FLOOR;
                return;
            }
            for (int y = p1.y - 1; x < p1.y + 1; y++) {
                if (x == p1.x) {
                    world[x][y] = Tileset.WALL;
                    continue;
                }
                if (y == p1.y) {
                    world[x][y] = Tileset.FLOOR;
                } else {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }

    public void createLeftHallway(Position p1, Position p2, TETile[][] world) {
        if (p1.y < p2.y) { // make p1 whichever is larger
            Position save = p1;
            p1 = p2;
            p2 = save;
        }
//        if (p2)// p2 is a corner --> keep on drawing until end i guess
        if (p1.x - 2 < 0) {
            return;
        }
        world[p1.x][p1.y] = Tileset.FLOOR;
        for (int x = p1.x - 1; x > p2.x; x--) {
            if (x - 2 < 0) {
                for (int y = p1.y - 1; y < p1.y + 2; y++) {
                    if (y == p1.y) {
                        world[x][y] = Tileset.FLOOR;
                    } else {
                        world[x][y] = Tileset.WALL;
                    }
                }
                for (int y = p1.y - 1; y < p1.y + 2; y++) {
                    world[x - 1][y] = Tileset.WALL;
                }
                return;
            }
            if (world[x][p1.y] == Tileset.WALL) {
                world[x][p1.y] = Tileset.FLOOR;
                return;
            }
            for (int y = p1.y - 1; y < p1.y + 2; y++) {
                if (x == p1.x) { // maybe p2?
                    world[x][y] = Tileset.WALL;
                    continue;
                }
                if (y == p1.y) {
                    world[x][y] = Tileset.FLOOR;
                } else {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }

    public boolean isOccupiedSpace(TETile[][] world, int width, int height, Position p) {
        return world[p.x][p.y] != Tileset.NOTHING;
    }
}
