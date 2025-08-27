package rubikscube;


public class Axis {
    public static final String[] axes = new String[] {"x", "y", "z"};
    public static final Axis X = new Axis((byte) 0);
    public static final Axis Y = new Axis((byte) 1);
    public static final Axis Z = new Axis((byte) 2);

    public int axisID;
    public String axisString;


    Axis(byte axisID) {
        this.axisID = axisID;
        this.axisString = Axis.axes[this.axisID];
    }
}
