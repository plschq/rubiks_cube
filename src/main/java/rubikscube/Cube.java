package rubikscube;


import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


public class Cube {
    public final AnchorPane anchorPane;

    private double mouseOldX;
    private double mouseOldY;

    public boolean isScrambling = false;


    Cube() {
        this.anchorPane = new AnchorPane();
        this.enableMouseController();
    }


    private void enableMouseController() {
        Rotate cubeRotateX = new Rotate(StartSettings.cubeRotateX, 0, 0, 0, Rotate.X_AXIS);
        Rotate cubeRotateY = new Rotate(StartSettings.cubeRotateY, 0, 0, 0, Rotate.Y_AXIS);
        this.anchorPane.getTransforms().addAll(cubeRotateX, cubeRotateY);

        Elements.mainScene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            this.mouseOldX = event.getSceneX();
            this.mouseOldY = event.getSceneY();
        });
        Elements.mainScene.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            double mousePosX = event.getSceneX();
            double mousePosY = event.getSceneY();
            cubeRotateX.setAngle(cubeRotateX.getAngle() + (mousePosY - this.mouseOldY) * StartSettings.cubeRotateSpeed);
            cubeRotateY.setAngle(cubeRotateY.getAngle() - (mousePosX - this.mouseOldX) * StartSettings.cubeRotateSpeed);
            this.mouseOldX = mousePosX;
            this.mouseOldY = mousePosY;
        });
    }

    public void scramble(int steps) {
        this.isScrambling = true;

        Axis[] lastAxisRotated = {Axis.X};

        for (int i = 0; i < steps; i++) {
            Timeout.set(() -> {
                Slice slice = Slice.random();
                while (Axis.bySlice(slice).equals(lastAxisRotated[0])) {
                    slice = Slice.random();
                }
                lastAxisRotated[0] = Axis.bySlice(slice);
                slice.rotate(Direction.random());
                return null;
            }, (StartSettings.sliceRotateSpeed+StartSettings.sliceRotateDelay)*(i+1));
        }

        Timeout.set(() -> {this.isScrambling = false; return null;}, (StartSettings.sliceRotateSpeed)*steps);
    }
    public void rotate() {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis(2000));
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setToAngle(360);
        rotateTransition.setNode(this.anchorPane);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.play();
    }
}
