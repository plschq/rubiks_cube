package rubikscube;


import java.util.Random;


public class Direction {
    public static final Direction CLOCKWISE = new Direction(1);
    public static final Direction COUNTERCLOCKWISE = new Direction(-1);

    public final int direction;


    Direction(int direction) {
        if (direction >= 0) {
            this.direction = 1;
        } else {
            this.direction = -1;
        }
    }


    public boolean isClockwise() {
        return this.direction >= 0;
    }
    public boolean isCounterclockwise() {
        return this.direction < 0;
    }

    public static Direction random() {
        return new Direction(new Random().nextInt(2)-1);
    }
}
