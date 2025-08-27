package rubikscube;


import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Random;


public class Slice {
    public final int id;

    public static final Slice LEFT = new Slice(0);
    public static final Slice RIGHT = new Slice(1);
    public static final Slice TOP = new Slice(2);
    public static final Slice BOTTOM = new Slice(3);
    public static final Slice FRONT = new Slice(4);
    public static final Slice BACK = new Slice(5);

    public static final Slice[] ALL = new Slice[] {
            Slice.LEFT,
            Slice.RIGHT,
            Slice.TOP,
            Slice.BOTTOM,
            Slice.FRONT,
            Slice.BACK
    };

    public static boolean isRotating = false;


    Slice(int sliceID) {
        this.id = sliceID;
    }


    public static Slice random() {
        return new Slice(new Random().nextInt(6));
    }

    public boolean isLeft() {
        return this.id == 0;
    }
    public boolean isRight() {
        return this.id == 1;
    }
    public boolean isTop() {
        return this.id == 2;
    }
    public boolean isBottom() {
        return this.id == 3;
    }
    public boolean isFront() {
        return this.id == 4;
    }
    public boolean isBack() {
        return this.id == 5;
    }

    public ArrayList<Piece> getPieces() {
        ArrayList<Piece> slicePieces = new ArrayList<>();

        if (this.isLeft()) {for (Piece piece : Elements.allPieces) {if (piece.position.x == -1) {slicePieces.add(piece);}}}
        if (this.isRight()) {for (Piece piece : Elements.allPieces) {if (piece.position.x == 1) {slicePieces.add(piece);}}}
        if (this.isTop()) {for (Piece piece : Elements.allPieces) {if (piece.position.y == -1) {slicePieces.add(piece);}}}
        if (this.isBottom()) {for (Piece piece : Elements.allPieces) {if (piece.position.y == 1) {slicePieces.add(piece);}}}
        if (this.isFront()) {for (Piece piece : Elements.allPieces) {if (piece.position.z == -1) {slicePieces.add(piece);}}}
        if (this.isBack()) {for (Piece piece : Elements.allPieces) {if (piece.position.z == 1) {slicePieces.add(piece);}}}

        return slicePieces;
    }
    public ArrayList<Sticker> getStickers() {
        ArrayList<Sticker> sliceStickers = new ArrayList<>();

        if (this.isLeft()) {for (Sticker sticker : Elements.allStickers) {if (sticker.position.x == -1) {sliceStickers.add(sticker);}}}
        if (this.isRight()) {for (Sticker sticker : Elements.allStickers) {if (sticker.position.x == 1) {sliceStickers.add(sticker);}}}
        if (this.isTop()) {for (Sticker sticker : Elements.allStickers) {if (sticker.position.y == -1) {sliceStickers.add(sticker);}}}
        if (this.isBottom()) {for (Sticker sticker : Elements.allStickers) {if (sticker.position.y == 1) {sliceStickers.add(sticker);}}}
        if (this.isFront()) {for (Sticker sticker : Elements.allStickers) {if (sticker.position.z == -1) {sliceStickers.add(sticker);}}}
        if (this.isBack()) {for (Sticker sticker : Elements.allStickers) {if (sticker.position.z == 1) {sliceStickers.add(sticker);}}}

        return sliceStickers;
    }

    public void changeStickersColors(Direction direction) {
        int[] xPositive = new int[]{5, 7, 6, 11, 12, 18, 20, 19, 3, 4, 10, 16, 17, 0, 2, 1, 8, 9, 13, 15, 14};
        int[] xNegative = new int[]{13, 15, 14, 8, 9, 0, 2, 1, 16, 17, 10, 3, 4, 18, 20, 19, 11, 12, 5, 7, 6};
        int[] yPositive = new int[]{15, 14, 13, 9, 8, 2, 1, 0, 17, 16, 10, 4, 3, 20, 19, 18, 12, 11, 7, 6, 5};
        int[] yNegative = new int[]{7, 6, 5, 12, 11, 20, 19, 18, 4, 3, 10, 17, 16, 2, 1, 0, 9, 8, 15, 14, 13};
        int[] zPositive = new int[]{6, 5, 7, 11, 12, 19, 18, 20, 3, 4, 10, 16, 17, 1, 0, 2, 8, 9, 14, 13, 15};
        int[] zNegative = new int[]{14, 13, 15, 8, 9, 1, 0, 2, 16, 17, 10, 3, 4, 19, 18, 20, 11, 12, 6, 5, 7};

        ArrayList<Sticker> stickers = this.getStickers();
        ArrayList<Color> colors = new ArrayList<>();
        for (Sticker sticker : this.getStickers()) {
            colors.add(sticker.color);
        }

        if (Axis.bySlice(this).isX() && direction.isClockwise()) {
            for (int i = 0; i < stickers.toArray().length; i++) {
                stickers.get(i).setColor(colors.get(xPositive[i]));
            }
        } else if (Axis.bySlice(this).isX() && direction.isCounterclockwise()) {
            for (int i = 0; i < stickers.toArray().length; i++) {
                stickers.get(i).setColor(colors.get(xNegative[i]));
            }
        } else if (Axis.bySlice(this).isY() && direction.isClockwise()) {
            for (int i = 0; i < stickers.toArray().length; i++) {
                stickers.get(i).setColor(colors.get(yPositive[i]));
            }
        } else if (Axis.bySlice(this).isY() && direction.isCounterclockwise()) {
            for (int i = 0; i < stickers.toArray().length; i++) {
                stickers.get(i).setColor(colors.get(yNegative[i]));
            }
        } else if (Axis.bySlice(this).isZ() && direction.isClockwise()) {
            for (int i = 0; i < stickers.toArray().length; i++) {
                stickers.get(i).setColor(colors.get(zPositive[i]));
            }
        } else if (Axis.bySlice(this).isZ() && direction.isCounterclockwise()) {
            for (int i = 0; i < stickers.toArray().length; i++) {
                stickers.get(i).setColor(colors.get(zNegative[i]));
            }
        }
    }
    public void rotate(Direction direction) {
        if (Slice.isRotating) {return;}
        Slice.isRotating = true;

        ArrayList<Piece> slicePieces = this.getPieces();
        ArrayList<Sticker> sliceStickers = this.getStickers();

        for (Piece piece : slicePieces) {
            Elements.sliceRotateParent.getChildren().add(piece.box);
        }
        for (Sticker sticker : sliceStickers) {
            Elements.sliceRotateParent.getChildren().add(sticker.box);
        }

        RotateTransition rotateTransition = new RotateTransition();
        TranslateTransition translateTransition = new TranslateTransition();

        if (Axis.bySlice(this).isX()) {

            rotateTransition.setAxis(Rotate.X_AXIS);
            rotateTransition.setToAngle(90*direction.direction);

            translateTransition.setToY(StartSettings.pieceSize*-0.25);
            translateTransition.setToZ(StartSettings.pieceSize*0.25*direction.direction);

        } else if (Axis.bySlice(this).isY()) {

            rotateTransition.setAxis(Rotate.Y_AXIS);
            rotateTransition.setToAngle(90*direction.direction);

            translateTransition.setToX(StartSettings.pieceSize*-0.25);
            translateTransition.setToZ(StartSettings.pieceSize*-0.25*direction.direction);

        } else if (Axis.bySlice(this).isZ()) {

            rotateTransition.setAxis(Rotate.Z_AXIS);
            rotateTransition.setToAngle(90*direction.direction);

            translateTransition.setToX(StartSettings.pieceSize*(-0.25-direction.direction*0.25));
            translateTransition.setToY(StartSettings.pieceSize*(-0.25+direction.direction*0.25));

        }

        rotateTransition.setDuration(Duration.millis(StartSettings.sliceRotateSpeed));
        translateTransition.setDuration(Duration.millis(StartSettings.sliceRotateSpeed));

        rotateTransition.setNode(Elements.sliceRotateParent);
        translateTransition.setNode(Elements.sliceRotateParent);

        rotateTransition.setInterpolator(StartSettings.sliceRotateInterpolator);
        translateTransition.setInterpolator(StartSettings.sliceRotateInterpolator);

        rotateTransition.setOnFinished(event -> {
            Elements.sliceRotateParent.rotateProperty().set(0);
            this.changeStickersColors(direction);
            for (Piece piece : slicePieces) {
                Elements.cube.anchorPane.getChildren().add(piece.box);
            }
            for (Sticker sticker : sliceStickers) {
                Elements.cube.anchorPane.getChildren().add(sticker.box);
            }
            Slice.isRotating = false;
        });
        translateTransition.setOnFinished(event -> {
            Elements.sliceRotateParent.translateXProperty().set(0);
            Elements.sliceRotateParent.translateYProperty().set(0);
            Elements.sliceRotateParent.translateZProperty().set(0);
        });

        rotateTransition.play();
        translateTransition.play();
    }

    public void enableKeyRotateController() {
        Elements.mainScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (Elements.cube.isScrambling) {return;}

            if      (KeyCombination.keyCombination("a").match(event)) {Slice.LEFT.rotate(Direction.CLOCKWISE);}
            else if (KeyCombination.keyCombination("d").match(event)) {Slice.RIGHT.rotate(Direction.CLOCKWISE);}
            else if (KeyCombination.keyCombination("w").match(event)) {Slice.TOP.rotate(Direction.CLOCKWISE);}
            else if (KeyCombination.keyCombination("s").match(event)) {Slice.BOTTOM.rotate(Direction.CLOCKWISE);}
            else if (KeyCombination.keyCombination("e").match(event)) {Slice.FRONT.rotate(Direction.CLOCKWISE);}
            else if (KeyCombination.keyCombination("q").match(event)) {Slice.BACK.rotate(Direction.CLOCKWISE);}
            else if (KeyCombination.keyCombination("shift+a").match(event)) {Slice.LEFT.rotate(Direction.COUNTERCLOCKWISE);}
            else if (KeyCombination.keyCombination("shift+d").match(event)) {Slice.RIGHT.rotate(Direction.COUNTERCLOCKWISE);}
            else if (KeyCombination.keyCombination("shift+w").match(event)) {Slice.TOP.rotate(Direction.COUNTERCLOCKWISE);}
            else if (KeyCombination.keyCombination("shift+s").match(event)) {Slice.BOTTOM.rotate(Direction.COUNTERCLOCKWISE);}
            else if (KeyCombination.keyCombination("shift+e").match(event)) {Slice.FRONT.rotate(Direction.COUNTERCLOCKWISE);}
            else if (KeyCombination.keyCombination("shift+q").match(event)) {Slice.BACK.rotate(Direction.COUNTERCLOCKWISE);}
        });
    }
    public void enableMouseRotateController() {
        for (Sticker sticker : Elements.allStickers) {
            if (sticker.position.x == -1 && sticker.facing.isX()) {
                sticker.box.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (Elements.cube.isScrambling) {return;}
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        Slice.LEFT.rotate(Direction.CLOCKWISE);
                    } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                        Slice.LEFT.rotate(Direction.COUNTERCLOCKWISE);
                    }
                });
            } else if (sticker.position.x == 1 && sticker.facing.isX()) {
                sticker.box.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (Elements.cube.isScrambling) {return;}
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        Slice.RIGHT.rotate(Direction.COUNTERCLOCKWISE);
                    } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                        Slice.RIGHT.rotate(Direction.CLOCKWISE);
                    }
                });
            } else if (sticker.position.y == -1 && sticker.facing.isY()) {
                sticker.box.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (Elements.cube.isScrambling) {return;}
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        Slice.TOP.rotate(Direction.CLOCKWISE);
                    } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                        Slice.TOP.rotate(Direction.COUNTERCLOCKWISE);
                    }
                });
            } else if (sticker.position.y == 1 && sticker.facing.isY()) {
                sticker.box.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (Elements.cube.isScrambling) {return;}
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        Slice.BOTTOM.rotate(Direction.COUNTERCLOCKWISE);
                    } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                        Slice.BOTTOM.rotate(Direction.CLOCKWISE);
                    }
                });
            } else if (sticker.position.z == -1 && sticker.facing.isZ()) {
                sticker.box.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (Elements.cube.isScrambling) {return;}
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        Slice.FRONT.rotate(Direction.CLOCKWISE);
                    } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                        Slice.FRONT.rotate(Direction.COUNTERCLOCKWISE);
                    }
                });
            } else if (sticker.position.z == 1 && sticker.facing.isZ()) {
                sticker.box.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (Elements.cube.isScrambling) {return;}
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        Slice.BACK.rotate(Direction.COUNTERCLOCKWISE);
                    } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                        Slice.BACK.rotate(Direction.CLOCKWISE);
                    }
                });
            }
        }
    }
}
