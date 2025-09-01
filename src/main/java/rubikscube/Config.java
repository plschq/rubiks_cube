package rubikscube;


import javafx.animation.Interpolator;
import javafx.scene.paint.Color;

import rubikscube.dataclasses.XY;


public final class Config {

    public static final int MIN_WINDOW_WIDTH = 300;
    public static final int MIN_WINDOW_HEIGHT = 300;
    public static final int INITIAL_WINDOW_WIDTH = 500;
    public static final int INITIAL_WINDOW_HEIGHT = 500;
    public static final int SEED_BASE = 62;

    public static final double LIGHT_DISTANCE = 500;
    public static final double PIECE_SIZE = 100;
    public static final double STICKER_SIZE = PIECE_SIZE * 0.9;
    public static final double STICKER_THICKNESS = 10;
    public static final double CUBE_ROTATE_SENSITIVITY = 1;
    public static final double CUBE_ZOOM_SENSITIVITY = 2;
    public static final double INITIAL_CAMERA_DISTANCE = 1500;
    public static final double INITIAL_TURN_DURATION = 200;
    public static final double TURN_DURATION_DELTA = 50;

    public static final boolean DEV_MODE = false;
    public static final boolean REFLECT_LIGHT = true;

    public static final Color COLOR_L = Color.web("#28b463");
    public static final Color COLOR_R = Color.web("#2e86c1");
    public static final Color COLOR_U = Color.web("#eeeeee");
    public static final Color COLOR_D = Color.web("#f1c40f");
    public static final Color COLOR_F = Color.web("#e74c3c");
    public static final Color COLOR_B = Color.web("#e67e22");
    public static final Color BG_COLOR = Color.web("#18181b");
    public static final Color CUBE_COLOR = Color.web("#333");
    public static final Color LIGHT_COLOR = Color.web("#fff");

    public static final XY INITIAL_CUBE_ROTATION = new XY(30, -30);
    public static final String WINDOW_TITLE = "Rubik's Cube [v2.0.0]";
    public static final Interpolator TURN_INTERPOLATOR = Interpolator.LINEAR;
    public static final String BASE64 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ&#";

}
