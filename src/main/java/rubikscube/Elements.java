package rubikscube;


import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;


public final class Elements {
    public static final Camera mainCamera = new PerspectiveCamera(true);
    public static final AmbientLight mainAmbientLight = new AmbientLight(StartSettings.lightColor);
    public static final PointLight mainPointLight1 = new PointLight(StartSettings.lightColor);
    public static final PointLight mainPointLight2 = new PointLight(StartSettings.lightColor);
    public static final PointLight mainPointLight3 = new PointLight(StartSettings.lightColor);
    public static final Group rootGroup = new Group();
    public static final Scene mainScene = new Scene(
            Elements.rootGroup,
            StartSettings.windowWidth,
            StartSettings.windowHeight,
            true
    );
    public static final Cube cube = new Cube();
    public static final AnchorPane sliceRotateParent = new AnchorPane();

    public static final ArrayList<Piece> allPieces = new ArrayList<>();
    public static final ArrayList<Sticker> allStickers = new ArrayList<>();


    public static void initialize() {
        Elements.rootGroup.getChildren().addAll(Elements.cube.anchorPane);
        if (!StartSettings.RTX) {
            Elements.rootGroup.getChildren().add(Elements.mainAmbientLight);
        } else {
            mainPointLight1.setTranslateZ(-StartSettings.pointLightRemoteness);

            mainPointLight2.setTranslateX(-StartSettings.pointLightRemoteness);
            mainPointLight2.setTranslateY(-StartSettings.pointLightRemoteness);
            mainPointLight2.setTranslateZ(-StartSettings.pointLightRemoteness);

            mainPointLight3.setTranslateX(StartSettings.pointLightRemoteness);
            mainPointLight3.setTranslateY(StartSettings.pointLightRemoteness);
            mainPointLight3.setTranslateZ(-StartSettings.pointLightRemoteness);

            Elements.rootGroup.getChildren().addAll(mainPointLight1, mainPointLight2, mainPointLight3);
        }
        Elements.mainScene.setCamera(Elements.mainCamera);
        Elements.mainScene.setFill(StartSettings.backgroundColor);
        Elements.cube.anchorPane.getChildren().add(Elements.sliceRotateParent);
        Elements.mainCamera.translateXProperty().set(0);
        Elements.mainCamera.translateYProperty().set(0);
        Elements.mainCamera.translateZProperty().set(-1500);
        Elements.mainCamera.setFarClip(0);
        Elements.mainCamera.setFarClip(10000);

        for (int i=-1; i<=1; i++) {
            for (int j=-1; j<=1; j++) {
                for (int k = -1; k <= 1; k++) {
                    XYZ position = new XYZ(i, j, k);

                    Piece piece = new Piece(StartSettings.pieceSize, position, StartSettings.pieceColor);
                    Elements.cube.anchorPane.getChildren().add(piece.box);
                    Elements.allPieces.add(piece);

                    if (position.x == -1) {Elements.allStickers.add(new Sticker(StartSettings.pieceSize,
                            StartSettings.stickerSize, StartSettings.stickerDepth, Axis.X, position, StartSettings.colorPack.LEFT));}
                    if (position.x ==  1) {Elements.allStickers.add(new Sticker(StartSettings.pieceSize,
                            StartSettings.stickerSize, StartSettings.stickerDepth, Axis.X, position, StartSettings.colorPack.RIGHT));}
                    if (position.y == -1) {Elements.allStickers.add(new Sticker(StartSettings.pieceSize,
                            StartSettings.stickerSize, StartSettings.stickerDepth, Axis.Y, position, StartSettings.colorPack.TOP));}
                    if (position.y ==  1) {Elements.allStickers.add(new Sticker(StartSettings.pieceSize,
                            StartSettings.stickerSize, StartSettings.stickerDepth, Axis.Y, position, StartSettings.colorPack.BOTTOM));}
                    if (position.z == -1) {Elements.allStickers.add(new Sticker(StartSettings.pieceSize,
                            StartSettings.stickerSize, StartSettings.stickerDepth, Axis.Z, position, StartSettings.colorPack.FRONT));}
                    if (position.z ==  1) {Elements.allStickers.add(new Sticker(StartSettings.pieceSize,
                            StartSettings.stickerSize, StartSettings.stickerDepth, Axis.Z, position, StartSettings.colorPack.BACK));}
                }
            }
        }
        for (Sticker sticker : Elements.allStickers) {
            Elements.cube.anchorPane.getChildren().add(sticker.box);
        }
    }
}
