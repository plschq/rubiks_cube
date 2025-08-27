package rubikscube;


import javafx.scene.paint.Color;


public abstract class ColorPack {
    public Color LEFT;
    public Color RIGHT;
    public Color TOP;
    public Color BOTTOM;
    public Color FRONT;
    public Color BACK;

    public Color BACKGROUND;
    public Color PIECE;
    public Color LIGHT;


    public ColorPack() {
        this.LEFT = null;
        this.RIGHT = null;
        this.TOP = null;
        this.BOTTOM = null;
        this.FRONT = null;
        this.BACK = null;

        this.BACKGROUND = null;
        this.PIECE = null;
        this.LIGHT = null;
    }
}
