package rubikscube;


import java.util.Random;


public class Axis {
    public static final Axis X = new Axis(0);
    public static final Axis Y = new Axis(1);
    public static final Axis Z = new Axis(2);

    private final int axisID;


    Axis(int axisID) {
        this.axisID = axisID;
    }


    public static Axis bySlice(Slice slice) {
        if (slice.isLeft() || slice.isRight()) {
            return Axis.X;
        } else if (slice.isTop() || slice.isBottom()) {
            return Axis.Y;
        } else if (slice.isFront() || slice.isBack()) {
            return Axis.Z;
        }
        return Axis.X;
    }
    public static Axis random() {
        return new Axis(new Random().nextInt(3));
    }

    public boolean isX() {
        return this.axisID == 0;
    }
    public boolean isY() {
        return this.axisID == 1;
    }
    public boolean isZ() {
        return this.axisID == 2;
    }
}
