package rubikscube.components;


import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import rubikscube.Config;
import rubikscube.dataclasses.Side;


public final class Sticker {

    public final Box box = new Box(Config.STICKER_SIZE, Config.STICKER_SIZE, Config.STICKER_SIZE);

    private Side facing;
    private Side colorSide;
    private Color color;
    public final Cube cube;


    public Sticker(Cube cube, Side facing) {
        this.setColor(facing);
        this.setFacing(facing);

        this.cube = cube;
    }


    public void setColor(Side colorSide) {
        this.colorSide = colorSide;

        if (colorSide.isR) {
            this.color = Config.COLOR_R;
        } else if (colorSide.isL) {
            this.color = Config.COLOR_L;
        } else if (colorSide.isU) {
            this.color = Config.COLOR_U;
        } else if (colorSide.isD) {
            this.color = Config.COLOR_D;
        } else if (colorSide.isF) {
            this.color = Config.COLOR_F;
        } else if (colorSide.isB) {
            this.color = Config.COLOR_B;
        }

        PhongMaterial material = new PhongMaterial(this.color);
        if (Config.REFLECT_LIGHT) {
            material.setSpecularColor(this.color);
        }
        this.box.setMaterial(material);
    }

    public Side getColorSide() {
        return this.colorSide;
    }

    public void setFacing(Side facing) {
        this.facing = facing;

        this.box.setWidth(Config.STICKER_SIZE);
        this.box.setHeight(Config.STICKER_SIZE);
        this.box.setDepth(Config.STICKER_SIZE);

        if (this.facing.axis.isX) {
            this.box.setWidth(Config.STICKER_THICKNESS);
        } else if (this.facing.axis.isY) {
            this.box.setHeight(Config.STICKER_THICKNESS);
        } else if (this.facing.axis.isZ) {
            this.box.setDepth(Config.STICKER_THICKNESS);
        }

        this.box.translateXProperty().set(0);
        this.box.translateYProperty().set(0);
        this.box.translateZProperty().set(0);

        if (this.facing.isL) {
            this.box.translateXProperty().set(-Config.PIECE_SIZE / 2);
        } else if (this.facing.isR) {
            this.box.translateXProperty().set(Config.PIECE_SIZE / 2);
        } else if (this.facing.isU) {
            this.box.translateYProperty().set(-Config.PIECE_SIZE / 2);
        } else if (this.facing.isD) {
            this.box.translateYProperty().set(Config.PIECE_SIZE / 2);
        } else if (this.facing.isF) {
            this.box.translateZProperty().set(-Config.PIECE_SIZE / 2);
        } else if (this.facing.isB) {
            this.box.translateZProperty().set(Config.PIECE_SIZE / 2);
        }
    }

    public Side getFacing() {
        return this.facing;
    }

}
