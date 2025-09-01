package rubikscube.controllers;


import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import rubikscube.App;
import rubikscube.Window;


public final class FullscreenController extends Controller {

    public static void init() {
        App.scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode().equals(KeyCode.F11)) {
                Window.stage.setFullScreen(!Window.stage.fullScreenProperty().get());
            }
        });
    }

}
