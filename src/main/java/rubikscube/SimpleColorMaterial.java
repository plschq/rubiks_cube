package rubikscube;


import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Color;


public class SimpleColorMaterial {
    public static PhongMaterial create(Color color) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        material.setSpecularColor(color);
        return material;
    }
}
