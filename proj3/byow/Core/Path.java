package byow.Core;

import byow.TileEngine.TETile;

import java.util.HashSet;
import java.util.Random;

public class Path {
    public Path() {
    }
    public HashSet<Position> connect(Position first, Position second, Random seed) {
        HashSet<Position> path = new HashSet<>();
        int xDistance = first.x - second.x;
        int yDistance = first.y - second.y;
        int x = first.x;
        int y = first.y;
        int x2 = second.x;
        int y2 = second.y;

        if (xDistance < 0) {
            x = second.x;
            y = second.y;
            x2 = first.x;
            y2 = first.y;
            yDistance = second.y - first.y;
        }
        if (xDistance == 0) {
            if (yDistance < 0) {
                while(y != y2) {
                    y -= 1;
                    path.add(new Position(x, y));
                }
            } else {
                while(y != y2) {
                    y += 1;
                    path.add(new Position(x, y));
                }
            }
            return path;
        }
        if (yDistance == 0) {
            if (xDistance < 0) {
                while(x != x2) {
                    x -= 1;
                    path.add(new Position(x, y));
                }
            } else {
                while(x != x2) {
                    x += 1;
                    path.add(new Position(x, y));
                }
            }
            return path;
        }

        int direction = 0;
        direction = seed.nextInt(2);

        if(direction == 0) {
            int turn = x + (Math.abs(xDistance)/2);
            while(x != turn) {
                x += 1;
                path.add(new Position(x, y));
            }
            if (yDistance < 0) {
                while(y != y2) {
                    y -= 1;
                    path.add(new Position(x,y));
                }
            } else {
                while(y != y2) {
                    y+= 1;
                    path.add(new Position(x,y));
                }
            }
            while(x != x2) {
                x += 1;
                path.add(new Position(x, y));
            }
        }
        if(direction == 1 && yDistance < 0) {
            int turn = y - (Math.abs(yDistance)/2);
            while(y != turn) {
                y -= 1;
                path.add(new Position(x, y));
            }
            if (xDistance < 0) {
                while(x != x2) {
                    x -= 1;
                    path.add(new Position(x,y));
                }
            } else {
                while(x != x2) {
                    x+= 1;
                    path.add(new Position(x,y));
                }
            }
            while(y != y2) {
                y -= 1;
                path.add(new Position(x, y));
            }
        }
        if(direction == 1 && yDistance > 0) {
            int turn = y + (Math.abs(yDistance)/2);
            while(y != turn) {
                y += 1;
                path.add(new Position(x, y));
            }
            if (xDistance < 0) {
                while(x != x2) {
                    x -= 1;
                    path.add(new Position(x,y));
                }
            } else {
                while(x != x2) {
                    x+= 1;
                    path.add(new Position(x,y));
                }
            }
            while(y != y2) {
                y += 1;
                path.add(new Position(x, y));
            }
        }
        return path;
    }
}
