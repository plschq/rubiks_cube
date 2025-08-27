package rubikscube;


import javafx.scene.paint.Color;
import javafx.scene.shape.Box;


public class Sticker {
    public Main mainClassInstance;
    public Axis axis;
    public Direction facing;
    public Color color;
    public XYZ relativePosition;
    public Box box;


    Sticker(Main classInstance, Axis axis, Direction facing, Color color, XYZ relativePosition) {
        this.mainClassInstance = classInstance;
        this.axis = axis;
        this.facing = facing;
        this.color = color;
        this.relativePosition = relativePosition;
        this.box = new Box();
        this.load();
    }
    public void load() {
        this.box.setWidth(this.mainClassInstance.stickerDepth);
        this.box.setHeight(this.mainClassInstance.stickerSize);
        this.box.setDepth(this.mainClassInstance.stickerSize);
        this.box.translateXProperty().set(this.relativePosition.x*this.mainClassInstance.pieceSize*1.5);
        this.box.translateYProperty().set(this.relativePosition.y*this.mainClassInstance.pieceSize);
        this.box.translateZProperty().set(this.relativePosition.z*this.mainClassInstance.pieceSize);
        if (this.axis.axisID == 1) {
            this.box.setWidth(this.mainClassInstance.stickerSize);
            this.box.setHeight(this.mainClassInstance.stickerDepth);
            this.box.setDepth(this.mainClassInstance.stickerSize);
            this.box.translateXProperty().set(this.relativePosition.x*this.mainClassInstance.pieceSize);
            this.box.translateYProperty().set(this.relativePosition.y*this.mainClassInstance.pieceSize*1.5);
            this.box.translateZProperty().set(this.relativePosition.z*this.mainClassInstance.pieceSize);
        } else if (this.axis.axisID == 2) {
            this.box.setWidth(this.mainClassInstance.stickerSize);
            this.box.setHeight(this.mainClassInstance.stickerSize);
            this.box.setDepth(this.mainClassInstance.stickerDepth);
            this.box.translateXProperty().set(this.relativePosition.x*this.mainClassInstance.pieceSize);
            this.box.translateYProperty().set(this.relativePosition.y*this.mainClassInstance.pieceSize);
            this.box.translateZProperty().set(this.relativePosition.z*this.mainClassInstance.pieceSize*1.5);
        }
        this.box.setMaterial(SimpleColorMaterial.create(this.color));
    }
}
