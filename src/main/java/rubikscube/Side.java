package rubikscube;


public class Side {
    public final String sideName;
    public static final Side LEFT = new Side("left");
    public static final Side RIGHT = new Side("right");
    public static final Side TOP = new Side("top");
    public static final Side BOTTOM = new Side("bottom");
    public static final Side FRONT = new Side("front");
    public static final Side BACK = new Side("back");


    Side(String sideName) {
        this.sideName = sideName;
    }
}
