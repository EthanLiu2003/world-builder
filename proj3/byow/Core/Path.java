package byow.Core;

import byow.TileEngine.TETile;

import java.util.HashSet;
import java.util.Random;

public class Path {
    public static HashSet<Position> connect(Position p1, Position p2, Random seed) {
        HashSet<Position> path = new HashSet<>();

        if (p1.x > p2.x) {
            Position save = p1;
            p1 = p2;
            p2 = save;
        }
        int yDistance = p1.y - p2.y;
        int x = p1.x;
        int y = p1.y;
        while(x != p2.x) {
            x += 1;
            path.add(new Position(x, p1.y));
        }
        if (yDistance < 0) {
            path.add(new Position(p2.x, y));
            while(y != p2.y) {
                y -= 1;
                path.add(new Position(p2.x, y));
            }
        } else if (yDistance > 0) {
            path.add(new Position(p2.x, y));
            while(y != p2.y) {
                y += 1;
                path.add(new Position(p2.x, y));
            }
        }
        return path;
    }
}
