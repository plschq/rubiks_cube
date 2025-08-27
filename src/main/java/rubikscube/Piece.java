package rubikscube;


import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;


public class Piece {
    public int size;
    public XYZ position;
    public Color color;
    public Box box;


    public Piece(int size, XYZ position, Color color) {
        this.size = size;
        this.position = position;
        this.color = color;

        this.box = new Box(this.size, this.size, this.size);

        this.box.translateXProperty().set(this.position.x*this.size);
        this.box.translateYProperty().set(this.position.y*this.size);
        this.box.translateZProperty().set(this.position.z*this.size);

        this.setColor(color);
    }


    private void setColor(Color color) {
        this.color = color;

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(this.color);
        material.setSpecularColor(this.color);

        this.box.setMaterial(material);
    }
}
