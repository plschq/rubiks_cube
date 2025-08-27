package rubikscube;


import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Objects;


public final class Main extends Application {
    final int windowStartWidth = 700;
    final int windowStartHeight = 700;
    final int pieceSize = 100;
    final int stickerSize = 80;
    final int stickerDepth = 10;
    final int cubeRotateSpeed = 1;
    final int rotateSliceSpeed = 200;

    final Color backgroundColor = Color.web("#18181b");
    final Color pieceColor = Color.web("#111");
    final Color lightColor = Color.web("#ccc");
    final Color[] defaultStickersColors = new Color[] {
        Color.web("#00f"),
        Color.web("#0f0"),
        Color.web("#fff"),
        Color.web("#ff0"),
        Color.web("#f80"),
        Color.web("#f00")
    };
    final Color[] stickersColors = this.defaultStickersColors;

    boolean isSliceRotating = false;

    final ArrayList<Piece> allPieces = new ArrayList<>();
    final ArrayList<Sticker> allStickers = new ArrayList<>();

    final Camera mainCamera = new PerspectiveCamera(true);
    final AmbientLight mainLight = new AmbientLight();
    final Group rootGroup = new Group();
    final Scene mainScene = new Scene(
        this.rootGroup,
        this.windowStartWidth,
        this.windowStartHeight,
        true,
        SceneAntialiasing.BALANCED);
    final Cube cube = new Cube(new AnchorPane());
    final CubeRotateController cubeRotateController = new CubeRotateController(this);
    final AnchorPane rotateSliceParent = new AnchorPane();


    @Override public void start(Stage stage) {
        this.mainLight.setColor(this.lightColor);
        this.rootGroup.getChildren().addAll(this.cube.element, this.mainLight);
        this.mainScene.setCamera(this.mainCamera);
        this.mainScene.setFill(this.backgroundColor);
        this.mainCamera.translateXProperty().set(0);
        this.mainCamera.translateYProperty().set(0);
        this.mainCamera.translateZProperty().set(-1500);
        this.mainCamera.setFarClip(0);
        this.mainCamera.setFarClip(10000);
        this.cubeRotateController.enable();
        this.cube.element.getChildren().add(this.rotateSliceParent);

        for (int i=-1; i<=1; i++) {
            for (int j=-1; j<=1; j++) {
                for (int k = -1; k <= 1; k++) {
                    Piece piece = new Piece(this, new XYZ(i, j, k));
                    this.cube.element.getChildren().add(piece.box);
                    this.allPieces.add(piece);
                    this.createStickers(new XYZ(i, j, k));
                }
            }
        }
        for (Sticker sticker : this.allStickers) {
            this.cube.element.getChildren().add(sticker.box);
        }

        this.mainScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if      (KeyCombination.keyCombination("a").match(event)) {this.rotateSlice(Side.LEFT,   Direction.POSITIVE);}
            else if (KeyCombination.keyCombination("d").match(event)) {this.rotateSlice(Side.RIGHT,  Direction.POSITIVE);}
            else if (KeyCombination.keyCombination("w").match(event)) {this.rotateSlice(Side.TOP,    Direction.POSITIVE);}
            else if (KeyCombination.keyCombination("s").match(event)) {this.rotateSlice(Side.BOTTOM, Direction.POSITIVE);}
            else if (KeyCombination.keyCombination("e").match(event)) {this.rotateSlice(Side.FRONT,  Direction.POSITIVE);}
            else if (KeyCombination.keyCombination("q").match(event)) {this.rotateSlice(Side.BACK,   Direction.POSITIVE);}
            else if (KeyCombination.keyCombination("shift+a").match(event)) {this.rotateSlice(Side.LEFT,   Direction.NEGATIVE);}
            else if (KeyCombination.keyCombination("shift+d").match(event)) {this.rotateSlice(Side.RIGHT,  Direction.NEGATIVE);}
            else if (KeyCombination.keyCombination("shift+w").match(event)) {this.rotateSlice(Side.TOP,    Direction.NEGATIVE);}
            else if (KeyCombination.keyCombination("shift+s").match(event)) {this.rotateSlice(Side.BOTTOM, Direction.NEGATIVE);}
            else if (KeyCombination.keyCombination("shift+e").match(event)) {this.rotateSlice(Side.FRONT,  Direction.NEGATIVE);}
            else if (KeyCombination.keyCombination("shift+q").match(event)) {this.rotateSlice(Side.BACK,   Direction.NEGATIVE);}

        });

        stage.setTitle("Rubik's Cube [v1]");
        stage.setScene(this.mainScene);
        stage.show();
    }
    public Axis getAxisBySide(Side side) {
        if (Objects.equals(side.sideName, "left") || Objects.equals(side.sideName, "right")) {
            return Axis.X;
        } else if (Objects.equals(side.sideName, "top") || Objects.equals(side.sideName, "bottom")) {
            return Axis.Y;
        } else if (Objects.equals(side.sideName, "front") || Objects.equals(side.sideName, "back")) {
            return Axis.Z;
        }
        return Axis.X;
    }
    public void createStickers(XYZ position) {
        if (position.x == -1) {
            this.allStickers.add(new Sticker(this, Axis.X, Direction.NEGATIVE, this.stickersColors[0], position));
        }
        if (position.x ==  1) {
            this.allStickers.add(new Sticker(this, Axis.X, Direction.POSITIVE, this.stickersColors[1], position));
        }
        if (position.y == -1) {
            this.allStickers.add(new Sticker(this, Axis.Y, Direction.NEGATIVE, this.stickersColors[2], position));
        }
        if (position.y ==  1) {
            this.allStickers.add(new Sticker(this, Axis.Y, Direction.POSITIVE, this.stickersColors[3], position));
        }
        if (position.z == -1) {
            this.allStickers.add(new Sticker(this, Axis.Z, Direction.NEGATIVE, this.stickersColors[4], position));
        }
        if (position.z ==  1) {
            this.allStickers.add(new Sticker(this, Axis.Z, Direction.POSITIVE, this.stickersColors[5], position));
        }
    }
    public void changeStickersColors(ArrayList<Sticker> stickers, Side side, Direction direction) {
        int[] xPositive = new int[] {5, 7, 6, 11, 12, 18, 20, 19, 3, 4, 10, 16, 17, 0, 2, 1, 8, 9, 13, 15, 14};
        int[] xNegative = new int[] {13, 15, 14, 8, 9, 0, 2, 1, 16, 17, 10, 3, 4, 18, 20, 19, 11, 12, 5, 7, 6};
        int[] yPositive = new int[] {15, 14, 13, 9, 8, 2, 1, 0, 17, 16, 10, 4, 3, 20, 19, 18, 12, 11, 7, 6, 5};
        int[] yNegative = new int[] {7, 6, 5, 12, 11, 20, 19, 18, 4, 3, 10, 17, 16, 2, 1, 0, 9, 8, 15, 14, 13};
        int[] zPositive = new int[] {6, 5, 7, 11, 12, 19, 18, 20, 3, 4, 10, 16, 17, 1, 0, 2, 8, 9, 14, 13, 15};
        int[] zNegative = new int[] {14, 13, 15, 8, 9, 1, 0, 2, 16, 17, 10, 3, 4, 19, 18, 20, 11, 12, 6, 5, 7};
        ArrayList<Color> colors = new ArrayList<>();
        for (Sticker sticker : stickers) {
            colors.add(sticker.color);
        }
        if (this.getAxisBySide(side).axisString.equals("x") && direction.isPositive()) {
            for (int i = 0; i < stickers.toArray().length; i++) {
                stickers.get(i).color = colors.get(xPositive[i]);
            }
        } else if (this.getAxisBySide(side).axisString.equals("x") && direction.isNegative()) {
            for (int i = 0; i < stickers.toArray().length; i++) {
                stickers.get(i).color = colors.get(xNegative[i]);
            }
        } else if (this.getAxisBySide(side).axisString.equals("y") && direction.isPositive()) {
            for (int i = 0; i < stickers.toArray().length; i++) {
                stickers.get(i).color = colors.get(yPositive[i]);
            }
        } else if (this.getAxisBySide(side).axisString.equals("y") && direction.isNegative()) {
            for (int i = 0; i < stickers.toArray().length; i++) {
                stickers.get(i).color = colors.get(yNegative[i]);
            }
        } else if (this.getAxisBySide(side).axisString.equals("z") && direction.isPositive()) {
            for (int i = 0; i < stickers.toArray().length; i++) {
                stickers.get(i).color = colors.get(zPositive[i]);
            }
        } else if (this.getAxisBySide(side).axisString.equals("z") && direction.isNegative()) {
            for (int i = 0; i < stickers.toArray().length; i++) {
                stickers.get(i).color = colors.get(zNegative[i]);
            }
        }
        for (Sticker sticker : stickers) {
            sticker.load();
        }
    }
    public ArrayList<Piece> getPiecesBySide(Side side) {
        ArrayList<Piece> slicePieces = new ArrayList<>();
        if (Objects.equals(side.sideName, "left")) {
            for (Piece piece : this.allPieces) {
                if (piece.relativePosition.x == -1) {
                    slicePieces.add(piece);
                }
            }
        } else if (Objects.equals(side.sideName, "right")) {
            for (Piece piece : this.allPieces) {
                if (piece.relativePosition.x == 1) {
                    slicePieces.add(piece);
                }
            }
        } else if (Objects.equals(side.sideName, "top")) {
            for (Piece piece : this.allPieces) {
                if (piece.relativePosition.y == -1) {
                    slicePieces.add(piece);
                }
            }
        } else if (Objects.equals(side.sideName, "bottom")) {
            for (Piece piece : this.allPieces) {
                if (piece.relativePosition.y == 1) {
                    slicePieces.add(piece);
                }
            }
        } else if (Objects.equals(side.sideName, "front")) {
            for (Piece piece : this.allPieces) {
                if (piece.relativePosition.z == -1) {
                    slicePieces.add(piece);
                }
            }
        } else if (Objects.equals(side.sideName, "back")) {
            for (Piece piece : this.allPieces) {
                if (piece.relativePosition.z == 1) {
                    slicePieces.add(piece);
                }
            }
        }
        return slicePieces;
    }
    public ArrayList<Sticker> getStickersBySide(Side side) {
        ArrayList<Sticker> sliceStickers = new ArrayList<>();
        if (Objects.equals(side.sideName, "left")) {
            for (Sticker sticker : this.allStickers) {
                if (sticker.relativePosition.x == -1) {
                    sliceStickers.add(sticker);
                }
            }
        } else if (Objects.equals(side.sideName, "right")) {
            for (Sticker sticker : this.allStickers) {
                if (sticker.relativePosition.x == 1) {
                    sliceStickers.add(sticker);
                }
            }
        } else if (Objects.equals(side.sideName, "top")) {
            for (Sticker sticker : this.allStickers) {
                if (sticker.relativePosition.y == -1) {
                    sliceStickers.add(sticker);
                }
            }
        } else if (Objects.equals(side.sideName, "bottom")) {
            for (Sticker sticker : this.allStickers) {
                if (sticker.relativePosition.y == 1) {
                    sliceStickers.add(sticker);
                }
            }
        } else if (Objects.equals(side.sideName, "front")) {
            for (Sticker sticker : this.allStickers) {
                if (sticker.relativePosition.z == -1) {
                    sliceStickers.add(sticker);
                }
            }
        } else if (Objects.equals(side.sideName, "back")) {
            for (Sticker sticker : this.allStickers) {
                if (sticker.relativePosition.z == 1) {
                    sliceStickers.add(sticker);
                }
            }
        }
        return sliceStickers;
    }
    public void rotateSlice(Side side, Direction direction) {
        if (this.isSliceRotating) {return;}

        ArrayList<Piece> slicePieces = getPiecesBySide(side);
        ArrayList<Sticker> sliceStickers = getStickersBySide(side);

        for (Piece piece : slicePieces) {
            this.rotateSliceParent.getChildren().add(piece.box);
        }
        for (Sticker sticker : sliceStickers) {
            this.rotateSliceParent.getChildren().add(sticker.box);
        }

        RotateTransition rotateTransition = new RotateTransition();
        TranslateTransition translateTransition = new TranslateTransition();

        if (this.getAxisBySide(side).axisString.equals("x")) {

            rotateTransition.setAxis(Rotate.X_AXIS);
            rotateTransition.setToAngle(90*direction.direction);

            translateTransition.setToY(this.pieceSize*-0.25);
            translateTransition.setToZ(this.pieceSize*0.25*direction.direction);

        } else if (this.getAxisBySide(side).axisString.equals("y")) {

            rotateTransition.setAxis(Rotate.Y_AXIS);
            rotateTransition.setToAngle(90*direction.direction);

            translateTransition.setToX(this.pieceSize*-0.25);
            translateTransition.setToZ(this.pieceSize*-0.25*direction.direction);

        } else if (this.getAxisBySide(side).axisString.equals("z")) {

            rotateTransition.setAxis(Rotate.Z_AXIS);
            rotateTransition.setToAngle(90*direction.direction);

            translateTransition.setToX(this.pieceSize*(-0.25-direction.direction*0.25));
            translateTransition.setToY(this.pieceSize*(-0.25+direction.direction*0.25));

        }

        rotateTransition.setDuration(Duration.millis(this.rotateSliceSpeed));
        translateTransition.setDuration(Duration.millis(this.rotateSliceSpeed));

        rotateTransition.setNode(this.rotateSliceParent);
        translateTransition.setNode(this.rotateSliceParent);

        rotateTransition.setOnFinished(event -> {
            this.rotateSliceParent.rotateProperty().set(0);
            this.isSliceRotating = false;
            this.changeStickersColors(sliceStickers, side, direction);
            for (Piece piece : slicePieces) {
                this.cube.element.getChildren().add(piece.box);
            }
            for (Sticker sticker : sliceStickers) {
                this.cube.element.getChildren().add(sticker.box);
            }

        });
        translateTransition.setOnFinished(event -> {
            this.rotateSliceParent.translateXProperty().set(0);
            this.rotateSliceParent.translateYProperty().set(0);
            this.rotateSliceParent.translateZProperty().set(0);
        });

        this.isSliceRotating = true;
        rotateTransition.play();
        translateTransition.play();
    }
    public void scramble(int steps) {
        Timeout.set(() -> {this.rotateSlice(Side.LEFT, Direction.POSITIVE); return null;}, 1000);
    }
    public static void main(String[] args) {
        launch();
    }
}
