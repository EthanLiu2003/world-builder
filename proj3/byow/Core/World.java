package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Arrays;
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

//    public void fillWithHallways(Random seed, TETile[][] world) { // og code
//        for (int i = 0; i < RoomPosAndSize.size(); i++) {
//            ArrayList<Integer> curr = RoomPosAndSize.get(i);
//            int xPos = curr.get(0);
//            int yPos = curr.get(1);
//            int xSize = curr.get(2);
//            int ySize = curr.get(3);
//            int newXPos = seed.nextInt(xPos, xPos + (xSize - 1));
//            int newYPos = seed.nextInt(yPos, yPos + (ySize - 1));
//            Position pos = new Position(newXPos, newYPos);
//            while (world[pos.x][pos.y] != Tileset.WALL && !isCornerTile(world, pos)) {
//                int nextnewXPos = seed.nextInt(xPos, xPos + (xSize - 1));
//                int nextnewYPos = seed.nextInt(yPos, yPos + (ySize - 1));
//                pos = new Position(nextnewXPos, nextnewYPos);
//            }
//            RoomsAndHallways hallway = new RoomsAndHallways(world);
//            int hallwaylength = seed.nextInt(25); // might be problem --> need hallwaylength to be difference btw two wall positions
//            String direction = whichDirectionHallway(world, pos);
//            if (direction == "right") {
//                hallway.createRightHallway(pos, world, hallwaylength, width);
//            } else if (direction == "left") {
//                hallway.createLeftHallway(pos, world, hallwaylength);
//            } else if (direction == "up") {
//                hallway.createUpHallway(pos, world, hallwaylength, height);
//            } else {
//                hallway.createDownHallway(pos, world, hallwaylength);
//            }
//            ArrayList<Integer> hallwayStuff = new ArrayList<>(Arrays.asList(pos.x, pos.y, hallwaylength));
//            HallwayPosAndSize.add(hallwayStuff);
//        }
//    }

    public void fillWithHallways(Random seed, TETile[][] world) { // need to draw hallway length as diff of two walls of rooms
        for (int i = 0; i + 1 < RoomPosAndSize.size(); i++) {
            ArrayList<Integer> curr = RoomPosAndSize.get(i);
            int x1Pos = curr.get(0);
            int y1Pos = curr.get(1);
            int x1Size = curr.get(2);
            int y1Size = curr.get(3);
            //Position pos1 = new Position(x1Pos, y1Pos);
            int newX1Pos = seed.nextInt(x1Pos, x1Pos + (x1Size - 1)); // random x pos
            int newY1Pos = seed.nextInt(y1Pos, y1Pos + (y1Size - 1)); // random y pos
            Position pos1 = new Position(newX1Pos, newY1Pos); // ensures that the tile chosen is a wall tile
            while (world[pos1.x][pos1.y] != Tileset.WALL || isCornerTile(world, pos1)) {
                int nextnewX1Pos = seed.nextInt(x1Pos, x1Pos + (x1Size - 1));  // keeps choosing random wall
                int nextnewY1Pos = seed.nextInt(y1Pos, y1Pos + (y1Size - 1));
                pos1 = new Position(nextnewX1Pos, nextnewY1Pos);
            }
            System.out.println("pos1: " + isCornerTile(world, pos1));

            // connect each room to the closest room to it that is not connected
            // could use loop to compare each room to each other and find closest
            // if going through another room
            // inside room do nothing but if hits another wall turn into room
            ArrayList<Integer> curr2 = RoomPosAndSize.get(i + 1);
            int x2Pos = curr2.get(0);
            int y2Pos = curr2.get(1);
            int x2Size = curr2.get(2);
            int y2Size = curr2.get(3);
            int newX2Pos = seed.nextInt(x2Pos, x2Pos + (x2Size - 1));
            int newY2Pos = seed.nextInt(y2Pos, y2Pos + (y2Size - 1));
            Position pos2 = new Position(newX2Pos, newY2Pos);
            while (world[pos2.x][pos2.y] != Tileset.WALL|| isCornerTile(world, pos2)) {
                // finds new position that IS a wall and is a corner
                int nextnewX2Pos = seed.nextInt(x2Pos, x2Pos + (x2Size - 1));
                int nextnewY2Pos = seed.nextInt(y2Pos, y2Pos + (y2Size - 1));
                pos2 = new Position(nextnewX2Pos, nextnewY2Pos);
            }
            System.out.println("pos2: " + isCornerTile(world, pos2));
            // p2 CANNOT BE A CORNER
            RoomsAndHallways hallway = new RoomsAndHallways(world);
            //int hallwaylength = seed.nextInt(25); // might be problem --> need hallwaylength to be difference btw two wall positions
            Position startPos = pos1;
            Position startPos2 = pos2;
            if (pos1.x > pos2.x || pos1.y > pos2.y) {
                startPos = pos1;
            }
            if (pos2.x > pos1.x || pos2.y > pos1.y) {
                startPos = pos2;
            }
//            if (pos1.x > pos2.x) {
//                startPos1 = pos1;
//            }
//            if (pos1.y < pos2.y) {
//                startPos1 = pos2;
//            }
//                if (pos2.x > pos1.x || pos2.y > pos1.y) {
//                startPos1 = pos2;
//            }
            String direction = whichDirectionHallway(world, startPos);

            if (direction.equals("right")) {
                hallway.createRightHallway(pos1, pos2, world, width);
//                if (pos1.y > pos2.y) {
//                    hallway.createDownHallway(pos1, pos2, world);
//                }
//                if (pos1.y < pos2.y) {
//                    hallway.createUpHallway(pos1, pos2, world, height);
//                }
            } else if (direction.equals("left")) {
                hallway.createLeftHallway(pos1, pos2, world);
//                if (pos1.y > pos2.y) {
//                    hallway.createDownHallway(pos1, pos2, world);
//                }
//                if (pos1.y < pos2.y) {
//                    hallway.createUpHallway(pos1, pos2, world, height);
//                }
            } else if (direction.equals("up")) {
                hallway.createUpHallway(pos1, pos2, world, height);
//                if (pos1.x > pos2.x) {
//                    hallway.createLeftHallway(pos1, pos2, world);
//                }
//                if (pos1.x < pos2.x) {
//                    hallway.createRightHallway(pos1, pos2, world, width);
//                }
            } else {
                hallway.createDownHallway(pos1, pos2, world);
//                if (pos1.x > pos2.x) {
//                    hallway.createLeftHallway(pos1, pos2, world);
//                }
//                if (pos1.x < pos2.x) {
//                    hallway.createRightHallway(pos1, pos2, world, width);
//                }
            }
            //ArrayList<Integer> hallwayStuff = new ArrayList<>(Arrays.asList(pos1.x, pos1.y, hallwaylength));
            //HallwayPosAndSize.add(hallwayStuff);
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
        if (p.x < width - 1 && world[p.x + 1][p.y] == Tileset.FLOOR
                || p.x > 0 && world[p.x - 1][p.y] == Tileset.FLOOR
                || p.y < height - 1 && world[p.x][p.y + 1] == Tileset.FLOOR
                || p.y > 0 && world[p.x][p.y - 1] == Tileset.FLOOR) {
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
