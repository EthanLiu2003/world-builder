package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

public class ZaWarudo {
    private TETile[][] world;
    private Random seed;
    private int worldHeight;
    private int worldWidth;
    private HashSet<Position> floorPoints;
    private HashSet<Position> wallPoints;
    private LinkedList<Room> Rooms = new LinkedList<>();

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

    public void print(HashSet<Position> givenPos, TETile type) {
        for (Position p : givenPos) {
            world[p.x][p.y] = type;
        }
    }

    public void fillWithRooms() {
        int numRooms = seed.nextInt(8, 12);
        for (int num = 0; num < numRooms; num++) {
            int width = seed.nextInt(4, 10);
            int height = seed.nextInt(4, 10);
            Position pos = new Position(seed.nextInt(worldWidth - (width + 1)), seed.nextInt(worldHeight - (height + 1)));
            while (isOccupiedSpace(world, width, height, pos)) {
                pos = new Position(seed.nextInt(worldWidth - (width + 1)), seed.nextInt(worldHeight - (height + 1)));
            }
            Room room = new Room(pos, width, height);
            room.createRoom(pos, world);
            Rooms.addFirst(room);
        }
    }

    public void connectRooms() {
        LinkedList<Room> zaRooms = new LinkedList<>(Rooms);
        while (zaRooms.size() > 1) {
            Room firstRoom = zaRooms.removeFirst();
            Room secondRoom = zaRooms.removeFirst();
            Position first = firstRoom.centerPosition();
            Position second = secondRoom.centerPosition();
            Path thePath = new Path();
            floorPoints.addAll(thePath.connect(first, second, seed));
            int addBack = seed.nextInt();
            if(addBack % 2 == 0) {
                zaRooms.addLast(firstRoom);
            } else if (addBack % 2 != 0) {
                zaRooms.addLast(secondRoom);
            }
        }
    }

//    public void addWalls() {
//        for()
//    }

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

}
