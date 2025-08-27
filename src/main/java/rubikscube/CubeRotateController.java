package rubikscube;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;

public class CubeRotateController {
    private final Main mainClassInstance;
    private double mouseOldX;
    private double mouseOldY;
    private EventHandler mousePressedEventHandler;
    private EventHandler mouseDraggedEventHandler;


    CubeRotateController(Main classInstance) {
        this.mainClassInstance = classInstance;
    }
    public void enable() {
        Rotate cubeRotateX = new Rotate(30, 0, 0, 0, Rotate.X_AXIS);
        Rotate cubeRotateY = new Rotate(-30, 0, 0, 0, Rotate.Y_AXIS);
        this.mainClassInstance.cube.element.getTransforms().addAll(cubeRotateX, cubeRotateY);
        this.mainClassInstance.mainScene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            this.mouseOldX = event.getSceneX();
            this.mouseOldY = event.getSceneY();
        });
        this.mainClassInstance.mainScene.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            double mousePosX = event.getSceneX();
            double mousePosY = event.getSceneY();
            cubeRotateX.setAngle(cubeRotateX.getAngle() + (mousePosY - this.mouseOldY) * this.mainClassInstance.cubeRotateSpeed);
            cubeRotateY.setAngle(cubeRotateY.getAngle() - (mousePosX - this.mouseOldX) * this.mainClassInstance.cubeRotateSpeed);
            this.mouseOldX = mousePosX;
            this.mouseOldY = mousePosY;
        });
    }
    public void disable() {}
}
