package rubikscube;


import javafx.scene.shape.Box;


public class Piece {
    public Main mainClassInstance;
    public XYZ relativePosition;
    public Box box;


    Piece(Main classInstance, XYZ relativePosition) {
        this.mainClassInstance = classInstance;
        this.relativePosition = relativePosition;
        this.box = new Box(
                this.mainClassInstance.pieceSize,
                this.mainClassInstance.pieceSize,
                this.mainClassInstance.pieceSize
        );
        this.box.translateXProperty().set(this.relativePosition.x*this.mainClassInstance.pieceSize);
        this.box.translateYProperty().set(this.relativePosition.y*this.mainClassInstance.pieceSize);
        this.box.translateZProperty().set(this.relativePosition.z*this.mainClassInstance.pieceSize);
        this.box.setMaterial(SimpleColorMaterial.create(this.mainClassInstance.pieceColor));
    }
}
