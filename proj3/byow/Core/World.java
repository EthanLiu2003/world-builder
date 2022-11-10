package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class World {

    private TETile[][] world;
    private Random seed;
    private int height;
    private int width;
    private ArrayList<ArrayList> RoomPosAndSize = new ArrayList<>();
    private ArrayList<ArrayList> HallwayPosAndSize = new ArrayList<>();

    public World(TETile[][] world, Random seed, int height, int width) {
        this.world = world;
        this.seed = seed;
        this.height = height;
        this.width = width;
    }

    public void generateWorld(Random seed, TETile[][] world, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++)
                world[x][y] = Tileset.NOTHING;
        }
    }

    public void fillWithRooms(Random seed, TETile[][] world, int width, int height) {
        //fill with random number of rooms of random size with position determined by random number generator
        int numRooms = seed.nextInt(8, 12);
        System.out.println(numRooms);
        for (int num = 0; num < numRooms; num++) {
            int xSize = seed.nextInt(4, 10);
            int ySize = seed.nextInt(4, 10);
            Position pos = new Position(seed.nextInt(width - xSize), seed.nextInt(height - ySize));
            while (isOccupiedSpace(world, xSize, ySize, pos)) {
                pos = new Position(seed.nextInt(width - xSize), seed.nextInt(height - ySize));
            }
            RoomsAndHallways room = new RoomsAndHallways(world);
            room.createRoom(pos, world, xSize, ySize);
            ArrayList<Integer> curr = new ArrayList<>(Arrays.asList(pos.x, pos.y, xSize, ySize));
            RoomPosAndSize.add(curr);
        }
    }

    public void fillWithHallways(Random seed, TETile[][] world) {
        for (int i = 0; i < RoomPosAndSize.size(); i++) {
            ArrayList<Integer> curr = RoomPosAndSize.get(i);
            int xPos = curr.get(0);
            int yPos = curr.get(1);
            int xSize = curr.get(2);
            int ySize = curr.get(3);
            int newXPos = seed.nextInt(xPos, xPos + (xSize - 1));
            int newYPos = seed.nextInt(yPos, yPos + (ySize - 1));
            Position pos = new Position(newXPos, newYPos);
            while (world[pos.x][pos.y] != Tileset.WALL && !isCornerTile(world, pos)) {
                int nextnewXPos = seed.nextInt(xPos, xPos + (xSize - 1));
                int nextnewYPos = seed.nextInt(yPos, yPos + (ySize - 1));
                pos = new Position(nextnewXPos, nextnewYPos);
            }
            RoomsAndHallways hallway = new RoomsAndHallways(world);
            int hallwaylength = seed.nextInt(25);
            String direction = whichDirectionHallway(world, pos);
            if (direction == "right") {
                hallway.createRightHallway(pos, world, hallwaylength, width);
            } else if (direction == "left") {
                hallway.createLeftHallway(pos, world, hallwaylength);
            } else if (direction == "up") {
                hallway.createUpHallway(pos, world, hallwaylength, height);
            } else {
                hallway.createDownHallway(pos, world, hallwaylength);
            }
            ArrayList<Integer> hallwayStuff = new ArrayList<>(Arrays.asList(pos.x, pos.y, hallwaylength));
            HallwayPosAndSize.add(hallwayStuff);
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
//        return world[p.x][p.y] != Tileset.NOTHING;
    }

    public boolean isCornerTile(TETile[][] world, Position p) {
        if (world[p.x + 1][p.y] == Tileset.FLOOR || world[p.x - 1][p.y] == Tileset.FLOOR || world[p.x][p.y + 1] == Tileset.FLOOR
                || world[p.x][p.y - 1] == Tileset.FLOOR) {
            return false;
        }
        return true;
    }

    public String whichDirectionHallway(TETile[][] world, Position p) {
        if (world[p.x - 1][p.y] == Tileset.FLOOR) {
            return "right";
        } else if (world[p.x + 1][p.y] == Tileset.FLOOR) {
            return "left";
        } else if (world[p.x][p.y + 1] == Tileset.FLOOR) {
            return "down";
        } else {
            return "up";
        }
    }
}
