package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class ZaWarudo {
    public TETile[][] world;
    private Random seed;
    private int worldHeight;
    private int worldWidth;
    private HashSet<Position> floorPoints = new HashSet<>();
    private LinkedList<Room> Rooms = new LinkedList<>();
    private boolean doorPlaced;


    public ZaWarudo(TETile[][] world, Random seed, int height, int width) {
        this.world = world;
        this.seed = seed;
        worldHeight = height;
        worldWidth = width;
    }

    public void generateEmptyWorld(Random seed, TETile[][] world, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++)
                world[x][y] = Tileset.NOTHING;
        }
    }


    public void fillWithRooms() {
        int numRooms = seed.nextInt(12, 16);
        for (int num = 0; num < numRooms; num++) {
            int width = seed.nextInt(4, 10);
            int height = seed.nextInt(4, 10);
            while (width % 2 != 1 || height % 2 != 1) {
                width = seed.nextInt(4, 10);
                height = seed.nextInt(4, 10);
            }
            Position pos = new Position(seed.nextInt(worldWidth - (width + 1)), seed.nextInt(worldHeight - (height + 1)));
            while (isOccupiedSpace(world, width, height, pos)) {
                pos = new Position(seed.nextInt(worldWidth - (width + 1)), seed.nextInt(worldHeight - (height + 1)));
            }
            Room room = new Room(pos, width, height);
            room.createRoom(pos, world);
            Rooms.addFirst(room);
            floorPoints.addAll(room.getRoomPositions(pos, width, height));
        }
    }

    public void connectRooms() {
        LinkedList<Room> zaRooms = new LinkedList<>(Rooms);
        while (zaRooms.size() > 1) {
            Room firstRoom = zaRooms.removeFirst();
            Room secondRoom = zaRooms.removeFirst();
            Position first = firstRoom.centerPosition();
            Position second = secondRoom.centerPosition();
            Path thePath = new Path(worldWidth, worldHeight);
            thePath.connect(first, second, world);
            int addBack = seed.nextInt();
            if (addBack % 2 == 0) {
                zaRooms.addLast(firstRoom);
            } else if (addBack % 2 != 0) {
                zaRooms.addLast(secondRoom);
            }
        }
    }

    public void addWalls() {
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight - 2; y++) {
                if (x == 0 || x == worldWidth - 1 || y == 0 || y == worldHeight - 1) {
                    if (world[x][y] == Tileset.FLOOR) {
                        world[x][y] = Tileset.WALL;
                    }
                }
                if (world[x][y] == Tileset.FLOOR) {
                    shouldBeWall(x, y);
                }
            }
        }
    }

    public boolean isOccupiedSpace(TETile[][] world, int width, int height, Position p) {
        for (int x = p.x; x < p.x + width; x++) {
            for (int y = p.y; y < p.y + height; y++) {
                if (world[x][y] != Tileset.NOTHING) {
                    return true;
                }
            }
        }
        return false;
    }

    public void shouldBeWall(int x, int y) {
        if (x - 1 >= 0) {
            if (world[x - 1][y] == Tileset.NOTHING) {
                world[x - 1][y] = Tileset.WALL;
            }
            if (world[x - 1][y - 1] == Tileset.NOTHING && y - 1 >= 0) {
                world[x - 1][y - 1] = Tileset.WALL;
            }
            if (world[x - 1][y + 1] == Tileset.NOTHING && y + 1 < worldHeight) {
                world[x - 1][y + 1] = Tileset.WALL;
            }
        }
        if (x + 1 < worldWidth) {
            if (world[x + 1][y] == Tileset.NOTHING) {
                world[x + 1][y] = Tileset.WALL;
            }
            if (world[x + 1][y - 1] == Tileset.NOTHING && y - 1 >= 0) {
                world[x + 1][y - 1] = Tileset.WALL;
            }
            if (world[x + 1][y + 1] == Tileset.NOTHING && y + 1 < worldHeight) {
                world[x + 1][y + 1] = Tileset.WALL;
            }
        }
        if (world[x][y - 1] == Tileset.NOTHING && y - 1 >= 0) {
            world[x][y - 1] = Tileset.WALL;
        }
        if (world[x][y + 1] == Tileset.NOTHING && y + 1 < worldHeight) {
            world[x][y + 1] = Tileset.WALL;
        }
    }

    public void placeDoor(int width, int height) {
        for (int x = 0; x < width - 1; x++) {
            for (int y = 0; y < height - 1; y++) {
                if (!doorPlaced && world[x][y] == Tileset.WALL && !isCorner(x, y)) {
                    world[x][y] = Tileset.LOCKED_DOOR; // set the tile to be avatar, must track this old spot
                    doorPlaced = true;
                }
            }
        }
    }

    public boolean isCorner(int x, int y) {
        if (world[x][y + 1] == Tileset.FLOOR || world[x][y - 1] == Tileset.FLOOR ||
                world[x - 1][y] == Tileset.FLOOR || world[x + 1][y] == Tileset.FLOOR) {
            return false;
        }
        return true;
    }
}
