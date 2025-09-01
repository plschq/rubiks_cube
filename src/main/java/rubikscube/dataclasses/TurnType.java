package rubikscube.dataclasses;


import java.util.Random;


public final class TurnType {

    public static final TurnType F = new TurnType(1);  // Forward / clockwise
    public static final TurnType B = new TurnType(-1);  // Backward / counterclockwise
    public static final TurnType D = new TurnType(2);  // Double (forward / clockwise)

    public final int value;
    public final boolean isClockwise;
    public final boolean isDouble;


    public TurnType(int value) {
        this.value = value;
        this.isClockwise = value >= 0;
        this.isDouble = Math.abs(value) == 2;
    }


    public TurnType inverted() {
        return new TurnType(-this.value);
    }

    public static TurnType random() {
        return new TurnType(new int[] {1, -1, 2}[new Random().nextInt(3)]);
    }

}
