package rubikscube.dataclasses;


import java.util.ArrayList;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.transform.Rotate;

import javafx.util.Duration;
import rubikscube.App;
import rubikscube.Config;
import rubikscube.components.Cube;
import rubikscube.components.Piece;


public final class Turn {

    public static boolean isWaiting = false;
    public static final ArrayList<Turn> queue = new ArrayList<>();

    public final Side side;
    public final TurnType turnType;


    public Turn(Side side, TurnType turnType) {
        this.side = side;
        this.turnType = turnType;
    }


    public static void make(Side side, TurnType turnType) {
        new Turn(side, turnType).make();
    }

    public void make() {
        assert this.side.getCube() != null;
        Cube cube = this.side.getCube();

        if (this.turnType.isDouble) {
            for (int i = 0; i < 2; i++) {
                new Turn(this.side, new TurnType((byte) (this.turnType.value / 2))).make();
            }
            return;
        }

        if (Turn.isWaiting) {
            Turn.queue.add(this);
            return;
        }
        Turn.isWaiting = true;

        Piece[] pieces = this.side.getPieces();
        for (int i = 0; i < 9; i++) {
            cube.turnAnchor.getChildren().add(pieces[i].anchor);
        }

        RotateTransition rotateTransition = new RotateTransition();
        TranslateTransition translateTransition = new TranslateTransition();

        TurnType actualTurnType = this.side.isReversed ? turnType.inverted() : turnType;

        if (this.side.axis.isX) {
            rotateTransition.setAxis(Rotate.X_AXIS);
            rotateTransition.setToAngle(90 * actualTurnType.value);
            translateTransition.setToY(Config.PIECE_SIZE * -0.25);
            translateTransition.setToZ(Config.PIECE_SIZE * 0.25 * actualTurnType.value);
        } else if (this.side.axis.isY) {
            rotateTransition.setAxis(Rotate.Y_AXIS);
            rotateTransition.setToAngle(90 * actualTurnType.value);
            translateTransition.setToX(Config.PIECE_SIZE * -0.25);
            translateTransition.setToZ(Config.PIECE_SIZE * -0.25 * actualTurnType.value);
        } else if (this.side.axis.isZ) {
            rotateTransition.setAxis(Rotate.Z_AXIS);
            rotateTransition.setToAngle(90 * actualTurnType.value);
            translateTransition.setToX(Config.PIECE_SIZE * -0.25 * (1 + actualTurnType.value));
            translateTransition.setToY(Config.PIECE_SIZE * -0.25 * (1 - actualTurnType.value));
        }

        rotateTransition.setDuration(Duration.millis(App.turnDuration));
        translateTransition.setDuration(Duration.millis(App.turnDuration));

        rotateTransition.setNode(cube.turnAnchor);
        translateTransition.setNode(cube.turnAnchor);

        rotateTransition.setInterpolator(Config.TURN_INTERPOLATOR);
        translateTransition.setInterpolator(Config.TURN_INTERPOLATOR);

        rotateTransition.setOnFinished(event -> {
            cube.turnAnchor.rotateProperty().set(0);

            for (int i = 0; i < 9; i++) {
                pieces[i].rotate(this.side.axis, (byte) (90 * actualTurnType.value));
                cube.anchor.getChildren().add(pieces[i].anchor);
            }

            Turn.isWaiting = false;
            if (!Turn.queue.isEmpty()) {
                Turn nextTurn = Turn.queue.get(0);
                Turn.queue.remove(0);
                nextTurn.make();
            }
        });
        translateTransition.setOnFinished(event -> {
            cube.turnAnchor.translateXProperty().set(0);
            cube.turnAnchor.translateYProperty().set(0);
            cube.turnAnchor.translateZProperty().set(0);
        });

        translateTransition.play();
        rotateTransition.play();
    }

    public static Turn random() {
        return new Turn(Side.random(), TurnType.random());
    }

}
