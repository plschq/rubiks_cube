package rubikscube;


import javafx.scene.*;

import rubikscube.components.Cube;
import rubikscube.controllers.*;

import java.math.BigInteger;


public final class App {

    private static final Camera camera = new PerspectiveCamera(true);
    public static final LightBase light1 = new PointLight(Config.LIGHT_COLOR);
    public static final LightBase light2 = new PointLight(Config.LIGHT_COLOR);
    public static final LightBase light3 = new PointLight(Config.LIGHT_COLOR);
    public static final Group root = new Group();
    public static final Scene scene = new Scene(
            App.root,
            Config.INITIAL_WINDOW_WIDTH,
            Config.INITIAL_WINDOW_HEIGHT,
            true,
            SceneAntialiasing.BALANCED
    );

    public static String savedSeed;
    public static double turnDuration = Config.INITIAL_TURN_DURATION;


    private static void initCamera() {
        App.camera.setTranslateZ(-Config.INITIAL_CAMERA_DISTANCE);
        App.camera.setFarClip(10000);

        App.scene.setCamera(camera);
        App.root.getChildren().add(App.camera);
    }

    private static void initLights() {
        final double sqrt3 = Math.sqrt(3);
        final double translateZ = -Config.LIGHT_DISTANCE / sqrt3;

        App.light1.setTranslateY(-Config.LIGHT_DISTANCE / sqrt3);
        App.light1.setTranslateZ(translateZ);
        App.root.getChildren().addAll(App.light1);

        App.light2.setTranslateX(Config.LIGHT_DISTANCE / 2.);
        App.light2.setTranslateY(Config.LIGHT_DISTANCE / sqrt3 / 2);
        App.light2.setTranslateZ(translateZ);
        App.root.getChildren().addAll(App.light2);

        App.light3.setTranslateX(-Config.LIGHT_DISTANCE / 2.);
        App.light3.setTranslateY(Config.LIGHT_DISTANCE / sqrt3 / 2);
        App.light3.setTranslateZ(translateZ);
        App.root.getChildren().addAll(App.light3);
    }

    private static Cube initCube() {
        Cube cube = new Cube();
        App.root.getChildren().add(cube.anchor);

        CubeRotateController.applyTo(cube);
        KeyboardController.applyTo(cube);
        ZoomController.applyTo(cube);

        return cube;
    }

    public static void init() {
        App.scene.setFill(Config.BG_COLOR);

        App.initCamera();
        App.initLights();
        Cube cube = App.initCube();

        FullscreenController.init();
        Playground.onStart(cube);
    }

    public static int factorial(int n) {
        if (n <= 1) return 1;
        int result = 2;

        for (int i = 3; i <= n; i++) {
            result *= i;
        }

        return result;
    }

    public static String toBaseN(int n, BigInteger number) {
        if (number.equals(BigInteger.ZERO)) {
            return "0";
        }

        StringBuilder stringBuilder = new StringBuilder();
        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divisionResult = number.divideAndRemainder(BigInteger.valueOf(n));
            number = divisionResult[0];
            int digit = divisionResult[1].intValue();
            stringBuilder.append(Config.BASE64.charAt(digit));
        }

        return stringBuilder.reverse().toString();
    }

    public static BigInteger fromBaseN(int n, String symbols) {
        BigInteger result = BigInteger.ZERO;

        for (int i = 0; i < symbols.length(); i++) {
            int digit = Config.BASE64.indexOf(symbols.charAt(i));
            result = result.multiply(BigInteger.valueOf(n)).add(BigInteger.valueOf(digit));
        }

        return result;
    }

    public static int clip(int n, int lim) {
        if (n >= lim) {
            n -= lim;
        } else if (n < 0) {
            n += lim;
        }

        return n;
    }

}
