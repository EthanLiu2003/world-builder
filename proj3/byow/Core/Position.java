package byow.Core;

public class Position {
    int x;
    int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position shift(int dx, int dy) {
        return new Position(this.x + dx, this.y + dy);
    }
}
