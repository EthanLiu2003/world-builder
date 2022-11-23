package byow.Core;

import byow.TileEngine.Tileset;

public class Interact {
    private ZaWarudo world;
    private boolean avatarPlaced;
    private int xAvatar;
    private int yAvatar;
    int xPos;
    int yPos;

    public Interact(ZaWarudo world) {
        this.world = world;
    }


    /**
     * Place the avatar in a random position inside the map. Keep track of the position.
     */
    public Position placeAvatar(int width, int height) {
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                if (!avatarPlaced && world.world[x][y] == Tileset.FLOOR) {
                    xAvatar = x;
                    yAvatar = y;
                    world.world[x][y] = Tileset.AVATAR; // set the tile to be avatar, must track this old spot
                    avatarPlaced = true;
                }
            }
        }
        return new Position(xAvatar, yAvatar);
    }

    /**
     * Move the avatar from its old position to the new position.
     * Return the position of the avatar after the move.
     */
    public Position move(char keyboardInput, Position p) {
        xAvatar = p.x; // reset avatar position
        yAvatar = p.y;
        if (keyboardInput == 'W' || keyboardInput == 'w') {
            if (world.world[xAvatar][yAvatar + 1] == Tileset.WALL) {
                return p; // do nothing
            }
            world.world[xAvatar][yAvatar + 1] = Tileset.AVATAR;
            world.world[xAvatar][yAvatar] = Tileset.FLOOR; // replace old position
            return new Position(xAvatar, yAvatar + 1);
        } else if (keyboardInput == 'S' || keyboardInput == 's') {
            if (world.world[xAvatar][yAvatar - 1] == Tileset.WALL) {
                return p; // do nothing
            }
            world.world[xAvatar][yAvatar - 1] = Tileset.AVATAR;
            world.world[xAvatar][yAvatar] = Tileset.FLOOR; // replace old position
            return new Position(xAvatar, yAvatar - 1);
        } else if (keyboardInput == 'A' || keyboardInput == 'a') {
            if (world.world[xAvatar - 1][yAvatar] == Tileset.WALL) {
                return p; // do nothing
            }
            world.world[xAvatar - 1][yAvatar] = Tileset.AVATAR;
            world.world[xAvatar][yAvatar] = Tileset.FLOOR; // replace old position
            return new Position(xAvatar - 1, yAvatar);
        } else if (keyboardInput == 'D' || keyboardInput == 'd') {
            if (world.world[xAvatar + 1][yAvatar] == Tileset.WALL) {
                return p; // do nothing
            }
            world.world[xAvatar + 1][yAvatar] = Tileset.AVATAR;
            world.world[xAvatar][yAvatar] = Tileset.FLOOR; // replace old position
            return new Position(xAvatar + 1, yAvatar);
        }
        return p;
    }

    /** Heads Up Display Class */
//    public void HUD(int width, int height) {
//        for (int x = 0; x < width - 1; x++) {
//            for (int y = 0; y < height - 1; y++) {
//                int xPos = (int) StdDraw.mouseX();
//                int yPos = (int) StdDraw.mouseY();
//                System.out.println("xpos " + xPos);
//                if (x == xPos && y == yPos) {
//                    StdDraw.textLeft(height, width - 2, world.world[x][y].description());
//                }
//            }
//        }
////        return world.world[x][y].description();
//    }
}
