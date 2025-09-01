package rubikscube.dataclasses;


public class XY {

    public double x;
    public double y;


    public XY(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public static boolean match(XY point1, XY point2) {
        return point1.match(point2);
    }

    public boolean match(XY point) {
        return point.x == this.x && point.y == this.y;
    }

}
