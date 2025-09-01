package rubikscube.controllers;


import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import rubikscube.App;
import rubikscube.components.Sticker;
import rubikscube.dataclasses.TurnType;


public final class StickerClickController extends Controller {

    public static boolean isDragging = false;


    public static void applyTo(Sticker sticker) {
        sticker.box.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            App.scene.setCursor(Cursor.HAND);
        });
        sticker.box.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            App.scene.setCursor(Cursor.DEFAULT);
        });
        App.scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            StickerClickController.isDragging = true;
        });
        sticker.box.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if (StickerClickController.isDragging) {
                StickerClickController.isDragging = false;
                return;
            }

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                sticker.cube.sides[sticker.getFacing().id].turn(TurnType.F);
            } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                sticker.cube.sides[sticker.getFacing().id].turn(TurnType.B);
            }
        });
    }

}
