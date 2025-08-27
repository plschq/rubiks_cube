package rubikscube;


public class Direction {
    public static final Direction POSITIVE = new Direction((byte) 1);
    public static final Direction NEGATIVE = new Direction((byte) -1);
    public final byte direction;


    Direction(byte direction) {
        if (direction >= 0) {
            this.direction = 1;
        } else {
            this.direction = -1;
        }
    }
    public boolean isPositive() {
        return this.direction >= 0;
    }
    public boolean isNegative() {
        return this.direction < 0;
    }
}
