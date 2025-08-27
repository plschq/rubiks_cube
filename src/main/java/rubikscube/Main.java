// JavaFX 3x3 Rubik's cube 3d project
// Made by me
//
// Versions
// 25.08.2022 - 12.09.2022   (total)
// 25.08.2022 - 10.09.2022   (html, css + js)
// 10.09.2022 - 12.09.2022   (javafx) v1.0 default
//
// Updates
// 12.09.2022                Added scramble
// 13.09.2022                Added Settings class
// 13.09.2022                Added fullscreen mode
// 13.09.2022 - 15.09.2022   Code cleared
// 17.09.2022                Added zoom
// 17.09.2022                Added RTX
// 18.09.2022                Added colorpacks

package rubikscube;


import javafx.application.Application;
import javafx.scene.input.*;
import javafx.stage.Stage;


public final class Main extends Application {
    public boolean isFullscreenModeOn = false;

    Stage stage;


    @Override public void start(Stage stage) {
        Elements.initialize();

        for (Slice slice : Slice.ALL) {
            slice.enableKeyRotateController();
            slice.enableMouseRotateController();
        }

        Elements.mainScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.F11) {
                this.toggleFullscreenMode();
            }
        });
        Elements.mainScene.addEventHandler(ScrollEvent.SCROLL, event -> {
            double cubeTranslate = Elements.cube.anchorPane.getTranslateZ()+event.getDeltaY()*StartSettings.cubeZoomSpeed;
            double lightsTranslate = Elements.mainPointLight1.getTranslateZ()+event.getDeltaY()*StartSettings.cubeZoomSpeed;
            Elements.cube.anchorPane.translateZProperty().set(cubeTranslate);
            Elements.mainPointLight1.translateZProperty().set(lightsTranslate);
            Elements.mainPointLight2.translateZProperty().set(lightsTranslate);
            Elements.mainPointLight3.translateZProperty().set(lightsTranslate);
        });

        this.stage = stage;
        this.stage.setTitle(StartSettings.windowTitle);
        this.stage.setScene(Elements.mainScene);
        this.stage.show();

        // Elements.cube.scramble(20);
        // Elements.cube.rotate();
    }

    public void toggleFullscreenMode() {
        this.isFullscreenModeOn = !this.isFullscreenModeOn;
        this.stage.setFullScreen(this.isFullscreenModeOn);
    }

    public static void main(String[] args) {
        launch();
    }
}
