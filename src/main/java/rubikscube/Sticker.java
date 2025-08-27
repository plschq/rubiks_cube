package rubikscube;


import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;


public class Sticker {
    public int relativePieceSize;
    public int size;
    public int depth;
    public Axis facing;
    public XYZ position;
    public Color color;
    public Box box;


    Sticker(int relativePieceSize, int size, int depth, Axis facing, XYZ position, Color color) {
        this.relativePieceSize = relativePieceSize;
        this.size = size;
        this.depth = depth;
        this.facing = facing;
        this.position = position;
        this.color = color;
        this.box = new Box();

        this.load();
    }


    private void load() {
        this.box.setWidth(this.depth);
        this.box.setHeight(this.size);
        this.box.setDepth(this.size);

        this.box.translateXProperty().set(this.position.x*this.relativePieceSize*1.5);
        this.box.translateYProperty().set(this.position.y*this.relativePieceSize);
        this.box.translateZProperty().set(this.position.z*this.relativePieceSize);

        if (this.facing.isY()) {

            this.box.setWidth(this.size);
            this.box.setHeight(this.depth);
            this.box.setDepth(this.size);

            this.box.translateXProperty().set(this.position.x*this.relativePieceSize);
            this.box.translateYProperty().set(this.position.y*this.relativePieceSize*1.5);
            this.box.translateZProperty().set(this.position.z*this.relativePieceSize);

        } else if (this.facing.isZ()) {

            this.box.setWidth(this.size);
            this.box.setHeight(this.size);
            this.box.setDepth(this.depth);

            this.box.translateXProperty().set(this.position.x*this.relativePieceSize);
            this.box.translateYProperty().set(this.position.y*this.relativePieceSize);
            this.box.translateZProperty().set(this.position.z*this.relativePieceSize*1.5);

        }

        this.setColor(this.color);
    }
    public void setColor(Color color) {
        this.color = color;

        PhongMaterial material = new PhongMaterial(this.color);
        material.setSpecularColor(this.color);

        this.box.setMaterial(material);
    }
}
