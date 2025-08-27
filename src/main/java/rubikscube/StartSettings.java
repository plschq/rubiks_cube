package rubikscube;


import rubikscube.colorpacks.SmoothColorPack;
import javafx.animation.Interpolator;
import javafx.scene.paint.Color;


public final class StartSettings {
    public static final int windowWidth = 700;
    public static final int windowHeight = 700;
    public static final int pieceSize = 100;
    public static final int stickerSize = 90;
    public static final int stickerDepth = 7;
    public static final int cubeRotateSpeed = 1;
    public static final int cubeRotateX = 30;
    public static final int cubeRotateY = -30;
    public static final int cubeZoomSpeed = 2;
    public static final int sliceRotateSpeed = 200;
    public static final int sliceRotateDelay = 50;
    public static final int pointLightRemoteness = 300;

    public static final boolean RTX = true;

    public static final String windowTitle = "Rubik's Cube [v1.1.0]";
    public static final Interpolator sliceRotateInterpolator = Interpolator.EASE_BOTH;

    public static final Color backgroundColor = Color.web("#18181b");
    public static final Color pieceColor = Color.web("#111");
    public static final Color lightColor = Color.web("#ccc");

    public static final ColorPack colorPack = new SmoothColorPack();
}
