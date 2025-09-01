package rubikscube.controllers;


import javafx.scene.input.ScrollEvent;

import rubikscube.App;
import rubikscube.Config;
import rubikscube.components.Cube;


public final class ZoomController extends Controller {

    public static void applyTo(Cube cube) {
        App.scene.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY() * Config.CUBE_ZOOM_SENSITIVITY;
            double cubeTranslate = cube.anchor.getTranslateZ() - delta;
            double lightTranslate = App.light1.getTranslateZ() - delta;

            cube.anchor.setTranslateZ(cubeTranslate);
            App.light1.setTranslateZ(lightTranslate);
            App.light2.setTranslateZ(lightTranslate);
            App.light3.setTranslateZ(lightTranslate);
        });
    }

}
