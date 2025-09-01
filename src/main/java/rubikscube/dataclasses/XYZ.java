package rubikscube.dataclasses;


public final class XYZ extends XY {

    public double z;


    public XYZ(double x, double y, double z) {
        super(x, y);

        this.z = z;
    }


    public static boolean match(XYZ point1, XYZ point2) {
        return point1.match(point2);
    }

    public boolean match(XYZ point) {
        return point.x == this.x && point.y == this.y && point.z == this.z;
    }

}
