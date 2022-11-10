package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class RoomsAndHallways {
    private TETile[][] world;

    public RoomsAndHallways(TETile[][] world) {
        this.world = world;
    }

    public void createRoom(Position p, TETile[][] world, int xSize, int ySize) {
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

    public void createUpHallway(Position p, TETile[][] world, int length, int height) {
        if (p.y + 2 > height) {
            return;
        }
        world[p.x][p.y] = Tileset.FLOOR;
        for (int y = p.y + 1; y < p.y + length; y++) {
            if (y + 2 > height) {
                for (int x = p.x - 1; x < p.x + 2; x++) {
                    if (x == p.x) {
                        world[x][y] = Tileset.FLOOR;
                    } else {
                        world[x][y] = Tileset.WALL;
                    }
                }
                for (int x = p.x - 1; x < p.x + 2; x++) {
                    world[x][y + 1] = Tileset.WALL;
                }
                return;
            }
            if (world[p.x][y] == Tileset.WALL) {
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
        for (int x = p.x + 1; x < p.x + length; x++) {
            if (x + 2 > width) {
                for (int y = p.y - 1; y < p.y + 2; y++) {
                    if (y == p.y) {
                        world[x][y] = Tileset.FLOOR;
                    } else {
                        world[x][y] = Tileset.WALL;
                    }
                }
                for (int y = p.y - 1; y < p.y + 2; y++) {
                    world[x + 1][y] = Tileset.WALL;
                }
                return;
            }
            if (world[x][p.y] == Tileset.WALL) {
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
