package rubikscube.colorpacks;


import rubikscube.ColorPack;
import javafx.scene.paint.Color;


public class DefaultColorPack extends ColorPack {
    public DefaultColorPack() {
        this.LEFT = Color.web("#00f");
        this.RIGHT = Color.web("#0f0");
        this.TOP = Color.web("#fff");
        this.BOTTOM = Color.web("#ff0");
        this.FRONT = Color.web("#f80");
        this.BACK = Color.web("#f00");
    }
}
