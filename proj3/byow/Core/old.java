package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class old {

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

//         need two points, two for loops, and track potential points when building room and go through and make sure they're connected
//         diff in y, x
    public void createUpHallway(Position p, TETile[][] world, int length, int height) {
        if (p.y + 2 > height) {
            return;
        }
        world[p.x][p.y] = Tileset.FLOOR; // turn current wall tile to floor tile
        for (int y = p.y + 1; y < p.y + length; y++) { // iterate thru all y + length positions
            if (y + 2 > height) { // ensures within bounds of board
                for (int x = p.x - 1; x < p.x + 2; x++) { // curr x pos row
                    if (x == p.x) {
                        world[x][y] = Tileset.FLOOR;
                    } else {
                        world[x][y] = Tileset.WALL;
                    }
                }
                for (int x = p.x - 1; x < p.x + 2; x++) { // create wall border
                    world[x][y + 1] = Tileset.WALL;
                }
                return;
            }
            if (world[p.x][y] == Tileset.WALL) { // if overlapping walls, convert to floor
                world[p.x][y] = Tileset.FLOOR;
                return;
            }
            for (int x = p.x - 1; x < p.x + 2; x++) {
                if (y == p.y + length - 1) {
                    world[x][y] = Tileset.WALL;
                    continue;
                }
                if (x == p.x) {
                    world[x][y] = Tileset.FLOOR;
                } else {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }



    public void createDownHallway(Position p, TETile[][] world, int length) {
        if (p.y - 2 < 0) {
            return;
        }
        world[p.x][p.y] = Tileset.FLOOR;
        for (int y = p.y - 1; y > p.y - length; y--) {
            if (y - 2 < 0) {
                for (int x = p.x - 1; x < p.x + 2; x++) {
                    if (x == p.x) {
                        world[x][y] = Tileset.FLOOR;
                    } else {
                        world[x][y] = Tileset.WALL;
                    }
                }
                for (int x = p.x - 1; x < p.x + 2; x++) {
                    world[x][y - 1] = Tileset.WALL;
                }
                return;
            }
            if (world[p.x][y] == Tileset.WALL) {
                world[p.x][y] = Tileset.FLOOR;
                return;
            }
            for (int x = p.x - 1; x < p.x + 2; x++) {
                if (y == p.y - length + 1) {
                    world[x][y] = Tileset.WALL;
                    continue;
                }
                if (x == p.x) {
                    world[x][y] = Tileset.FLOOR;
                } else {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }


    public void createRightHallway(Position p, TETile[][] world, int length, int width) {
        if (p.x + 2 > width) {
            return;
        }
        world[p.x][p.y] = Tileset.FLOOR;
        for (int x = p.x + 1; x < p.x + length; x++) { // moves right one at a time
            if (x + 2 > width) { // loop for if at end of world
                for (int y = p.y - 1; y < p.y + 2; y++) { // at specific x,
                    if (y == p.y) {
                        world[x][y] = Tileset.FLOOR;
                    } else {
                        world[x][y] = Tileset.WALL; // builds left wall
                    }
                }
                for (int y = p.y - 1; y < p.y + 2; y++) {
                    world[x + 1][y] = Tileset.WALL;// builds wall around row of floors
                }
                return;
            }
            // not at end of world
            if (world[x][p.y] == Tileset.WALL) { // intersection of walls turn into floor
                world[x][p.y] = Tileset.FLOOR;
                return;
            }
            for (int y = p.y - 1; x < p.y + 1; y++) {
                if (x == p.x + length - 1) {
                    world[x][y] = Tileset.WALL;
                    continue;
                }
                if (y == p.y) {
                    world[x][y] = Tileset.FLOOR;
                } else {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }

    public void createLeftHallway(Position p, TETile[][] world, int length) {
        if (p.x - 2 < 0) {
            return;
        }
        world[p.x][p.y] = Tileset.FLOOR;
        for (int x = p.x - 1; x > p.x - length; x--) {
            if (x - 2 < 0) {
                for (int y = p.y - 1; y < p.y + 2; y++) {
                    if (y == p.y) {
                        world[x][y] = Tileset.FLOOR;
                    } else {
                        world[x][y] = Tileset.WALL;
                    }
                }
                for (int y = p.y - 1; y < p.y + 2; y++) {
                    world[x - 1][y] = Tileset.WALL;
                }
                return;
            }
            if (world[x][p.y] == Tileset.WALL) {
                world[x][p.y] = Tileset.FLOOR;
                return;
            }
            for (int y = p.y - 1; y < p.y + 2; y++) {
                if (x == p.x - length + 1) {
                    world[x][y] = Tileset.WALL;
                    continue;
                }
                if (y == p.y) {
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

}
