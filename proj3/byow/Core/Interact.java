package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Interact {
    private TETile[][] world;
    private boolean avatarPlaced;
    private int xAvatar;
    private int yAvatar;

    public Interact(TETile[][] world) {
        this.world = world;
    }


    /**
     * Place the avatar in a random position inside the map. Keep track of the position.
     */
    public Position placeAvatar(int width, int height) {
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                if (!avatarPlaced && world[x][y] == Tileset.FLOOR) {
                    xAvatar = x;
                    yAvatar = y;
                    world[x][y] = Tileset.AVATAR; // set the tile to be avatar, must track this old spot
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
            if (world[xAvatar][yAvatar + 1] == Tileset.WALL) {
                return p; // do nothing
            }
            world[xAvatar][yAvatar + 1] = Tileset.AVATAR;
            world[xAvatar][yAvatar] = Tileset.FLOOR; // replace old position
            return new Position(xAvatar, yAvatar + 1);
        } else if (keyboardInput == 'S' || keyboardInput == 's') {
            if (world[xAvatar][yAvatar - 1] == Tileset.WALL) {
                return p; // do nothing
            }
            world[xAvatar][yAvatar - 1] = Tileset.AVATAR;
            world[xAvatar][yAvatar] = Tileset.FLOOR; // replace old position
            return new Position(xAvatar, yAvatar - 1);
        } else if (keyboardInput == 'A' || keyboardInput == 'a') {
            if (world[xAvatar - 1][yAvatar] == Tileset.WALL) {
                return p; // do nothing
            }
            world[xAvatar - 1][yAvatar] = Tileset.AVATAR;
            world[xAvatar][yAvatar] = Tileset.FLOOR; // replace old position
            return new Position(xAvatar - 1, yAvatar);
        } else if (keyboardInput == 'D' || keyboardInput == 'd') {
            if (world[xAvatar + 1][yAvatar] == Tileset.WALL) {
                return p; // do nothing
            }
            world[xAvatar + 1][yAvatar] = Tileset.AVATAR;
            world[xAvatar][yAvatar] = Tileset.FLOOR; // replace old position
            return new Position(xAvatar + 1, yAvatar);
        }
        return p;
    }
}
