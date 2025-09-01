package rubikscube.controllers;


import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;

import rubikscube.App;
import rubikscube.Config;
import rubikscube.components.Cube;
import rubikscube.dataclasses.XY;


public final class CubeRotateController extends Controller {

    public static final Rotate rotateX = new Rotate(Config.INITIAL_CUBE_ROTATION.x, Rotate.X_AXIS);
    public static final Rotate rotateY = new Rotate(Config.INITIAL_CUBE_ROTATION.y, Rotate.Y_AXIS);

    public static XY lastMouseDragPos;
    public static XY lastMousePressPos;


    public static void applyTo(Cube cube) {
        cube.anchor.getTransforms().addAll(rotateX, rotateY);

        App.scene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getTarget().equals(App.scene)) {
                App.scene.setCursor(Cursor.MOVE);
            }

            lastMousePressPos = new XY(event.getSceneX(), event.getSceneY());
            lastMouseDragPos = new XY(event.getSceneX(), event.getSceneY());
        });

        App.scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (App.scene.getCursor() != Cursor.MOVE) {
                App.scene.setCursor(Cursor.MOVE);
            }

            XY dragDelta = new XY(
                    -event.getSceneX() + lastMouseDragPos.x,
                    -event.getSceneY() + lastMouseDragPos.y
            );

            double newRotateX = rotateX.getAngle() - dragDelta.y * Config.CUBE_ROTATE_SENSITIVITY;
            if (Math.abs(newRotateX) <= 90) {
                rotateX.setAngle(newRotateX);
            }
            rotateY.setAngle(rotateY.getAngle() + dragDelta.x * Config.CUBE_ROTATE_SENSITIVITY);

            lastMouseDragPos = new XY(event.getSceneX(), event.getSceneY());
        });

        App.scene.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if (App.scene.getCursor() == Cursor.MOVE) {
                App.scene.setCursor(Cursor.DEFAULT);
            }
        });
    }

}
