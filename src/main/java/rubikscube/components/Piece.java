package rubikscube.components;


import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import rubikscube.App;
import rubikscube.Config;
import rubikscube.controllers.StickerClickController;
import rubikscube.dataclasses.Axis;
import rubikscube.dataclasses.Side;
import rubikscube.dataclasses.XYZ;

import java.util.ArrayList;
import java.util.Arrays;


public final class Piece {

    private XYZ position;
    public final Cube cube;
    public final Sticker[] stickers = new Sticker[3];

    public final AnchorPane anchor = new AnchorPane();

    public final boolean isCenter;
    public final boolean isEdge;
    public final boolean isCorner;


    public Piece(Cube cube, XYZ position) {
        this.setPosition(position);
        this.cube = cube;

        Box box = new Box(Config.PIECE_SIZE, Config.PIECE_SIZE, Config.PIECE_SIZE);
        PhongMaterial material = new PhongMaterial(Config.CUBE_COLOR);
        if (Config.REFLECT_LIGHT) {
            material.setSpecularColor(Config.CUBE_COLOR);
        }
        box.setMaterial(material);
        this.anchor.getChildren().add(box);

        int stickersN = initStickers();
        this.isCenter = stickersN == 1;
        this.isEdge = stickersN == 2;
        this.isCorner = stickersN == 3;
    }


    private int initStickers() {
        if (this.position.x == -1) {this.stickers[0] = new Sticker(this.cube, Side.L);}
        if (this.position.x ==  1) {this.stickers[0] = new Sticker(this.cube, Side.R);}
        if (this.position.y == -1) {this.stickers[1] = new Sticker(this.cube, Side.U);}
        if (this.position.y ==  1) {this.stickers[1] = new Sticker(this.cube, Side.D);}
        if (this.position.z == -1) {this.stickers[2] = new Sticker(this.cube, Side.F);}
        if (this.position.z ==  1) {this.stickers[2] = new Sticker(this.cube, Side.B);}

        int stickersN = 0;
        for (int i = 0; i < 3; i++) {
            if (this.stickers[i] != null) {
                stickersN++;
                StickerClickController.applyTo(this.stickers[i]);
                this.anchor.getChildren().add(this.stickers[i].box);
            }
        }

        return stickersN;
    }

    public void setPosition(XYZ newPosition) {
        this.position = newPosition;

        this.anchor.translateXProperty().set(this.position.x * Config.PIECE_SIZE);
        this.anchor.translateYProperty().set(this.position.y * Config.PIECE_SIZE);
        this.anchor.translateZProperty().set(this.position.z * Config.PIECE_SIZE);
    }

    public XYZ getPosition() {
        return this.position;
    }

    public void rotate(Axis axis, byte angle) {
        assert angle == 90 || angle == -90;

        if (axis.isX) {
            this.setPosition(new XYZ(
                    this.position.x,
                    this.position.z * -angle / 90,
                    this.position.y * angle / 90
            ));

            Sticker ySticker = this.stickers[1];
            this.stickers[1] = this.stickers[2];
            this.stickers[2] = ySticker;

            if (this.stickers[1] != null) {
                if (angle == 90) {
                    this.stickers[1].setFacing(new Side(Axis.Y, 1 - this.stickers[1].getFacing().order));
                } else {
                    this.stickers[1].setFacing(new Side(Axis.Y, this.stickers[1].getFacing().order));
                }
            }
            if (this.stickers[2] != null) {
                if (angle == 90) {
                    this.stickers[2].setFacing(new Side(Axis.Z, this.stickers[2].getFacing().order));
                } else {
                    this.stickers[2].setFacing(new Side(Axis.Z, 1 - this.stickers[2].getFacing().order));
                }
            }
        } else if (axis.isY) {
            this.setPosition(new XYZ(
                    this.position.z * angle / 90,
                    this.position.y,
                    this.position.x * -angle / 90
            ));

            Sticker xSticker = this.stickers[0];
            this.stickers[0] = this.stickers[2];
            this.stickers[2] = xSticker;

            if (this.stickers[0] != null) {
                if (angle == 90) {
                    this.stickers[0].setFacing(new Side(Axis.X, this.stickers[0].getFacing().order));
                } else {
                    this.stickers[0].setFacing(new Side(Axis.X, 1 - this.stickers[0].getFacing().order));
                }
            }
            if (this.stickers[2] != null) {
                if (angle == 90) {
                    this.stickers[2].setFacing(new Side(Axis.Z, 1 - this.stickers[2].getFacing().order));
                } else {
                    this.stickers[2].setFacing(new Side(Axis.Z, this.stickers[2].getFacing().order));
                }
            }
        } else if (axis.isZ) {
            this.setPosition(new XYZ(
                    this.position.y * -angle / 90,
                    this.position.x * angle / 90,
                    this.position.z
            ));

            Sticker xSticker = this.stickers[0];
            this.stickers[0] = this.stickers[1];
            this.stickers[1] = xSticker;

            if (this.stickers[0] != null) {
                if (angle == 90) {
                    this.stickers[0].setFacing(new Side(Axis.X, 1 - this.stickers[0].getFacing().order));
                } else {
                    this.stickers[0].setFacing(new Side(Axis.X, this.stickers[0].getFacing().order));
                }
            }
            if (this.stickers[1] != null) {
                if (angle == 90) {
                    this.stickers[1].setFacing(new Side(Axis.Y, this.stickers[1].getFacing().order));
                } else {
                    this.stickers[1].setFacing(new Side(Axis.Y, 1 - this.stickers[1].getFacing().order));
                }
            }
        }
    }

    private ArrayList<Sticker> getStickers() {
        ArrayList<Sticker> stickers = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            if (this.stickers[i] != null) {
                stickers.add(this.stickers[i]);
            }
        }

        return stickers;
    }

    public int getTypeId() {
        assert !this.isCenter;

        if (this.isCorner) {
            int cornerId = 0;

            for (Sticker sticker : this.stickers) {
                cornerId += (int) (sticker.getColorSide().order * Math.pow(2, 2 - sticker.getColorSide().axis.id));
            }

            return cornerId;
        }

        ArrayList<Sticker> stickers = this.getStickers();
        int color1 = stickers.get(0).getColorSide().id, color2 = stickers.get(1).getColorSide().id;
        if (color1 > color2) {
            int temp = color1;
            color1 = color2;
            color2 = temp;
        }
        String colors = color1 + "" + color2;

        return new ArrayList<>(Arrays.asList(
                "02", "04", "05", "03",
                "24", "25", "34", "35",
                "12", "14", "15", "13"
        )).indexOf(colors);
    }

    public void setTypeId(int typeId) {
        assert !this.isCenter;

        if (this.isCorner) {
            int zColorOrder = typeId % 2;
            typeId = typeId / 2;
            int yColorOrder = typeId % 2;
            typeId = typeId / 2;
            int xColorOrder = typeId;

            this.stickers[1].setColor(new Side(Axis.Y, yColorOrder));
            if (yColorOrder == 0 && this.position.y == 1 || yColorOrder == 1 && this.position.y == -1) {
                this.stickers[2].setColor(new Side(Axis.X, xColorOrder));
                this.stickers[0].setColor(new Side(Axis.Z, zColorOrder));
            } else {
                this.stickers[0].setColor(new Side(Axis.X, xColorOrder));
                this.stickers[2].setColor(new Side(Axis.Z, zColorOrder));
            }

            return;
        }

        String colors = new ArrayList<>(Arrays.asList(
                "02", "04", "05", "03",
                "24", "25", "34", "35",
                "12", "14", "15", "13"
        )).get(typeId);

        Side color1 = new Side(Integer.parseInt(String.valueOf(colors.charAt(0))));
        Side color2 = new Side(Integer.parseInt(String.valueOf(colors.charAt(1))));

        if (this.stickers[0] != null) {
            this.stickers[0].setColor(color1);
            color1 = null;
        }

        if (this.stickers[1] != null) {
            this.stickers[1].setColor(color1 == null ? color2 : color1);
        }

        if (this.stickers[2] != null) {
            this.stickers[2].setColor(color2);
        }
    }

    public int getOrientation() {
        if (this.isEdge) {
            ArrayList<Sticker> stickers = this.getStickers();

            return (stickers.get(0).getColorSide().axis.id < stickers.get(1).getColorSide().axis.id) ? 0 : 1;
        }

        if (this.isCorner) {
            assert this.stickers[1] != null;
            if (this.stickers[1].getColorSide().axis.isY) {
                return 0;
            }

            int cornerType = (int) (this.position.x * this.position.y * this.position.z);

            return this.stickers[1 - cornerType].getColorSide().axis.isY ? 1 : 2;
        }

        return 0;
    }

    public void setOrientation(int orientation) {
        // int currentOrientation = this.getOrientation();
        // if (currentOrientation == orientation) {
        //     return;
        // }

        if (this.isEdge) {
            assert orientation == 0 || orientation == 1;

            if (this.getOrientation() != orientation) {
                ArrayList<Sticker> stickers = this.getStickers();
                Side sticker1Side = stickers.get(0).getColorSide();
                stickers.get(0).setColor(stickers.get(1).getColorSide());
                stickers.get(1).setColor(sticker1Side);
            }
        } else if (this.isCorner) {
            int cornerType = (int) (this.position.x * this.position.y * this.position.z);
            if (cornerType == 1 && orientation != 0) {
                orientation = 3 - orientation;
            }

            int t = -1;
            if ((new ArrayList<>(Arrays.asList(0, 3, 5, 6)).contains(this.getTypeId()) ? -1 : 1) != cornerType) {
                t = 1;
            }

            Side[] colors = new Side[3];
            for (Sticker sticker : this.stickers) {
                if (sticker.getColorSide().axis.isX) {
                    colors[1 + t] = sticker.getColorSide();
                } else if (sticker.getColorSide().axis.isY) {
                    colors[1] = sticker.getColorSide();
                } else if (sticker.getColorSide().axis.isZ) {
                    colors[1 - t] = sticker.getColorSide();
                }
            }

            this.stickers[0].setColor(colors[App.clip(-orientation, 3)]);
            this.stickers[1].setColor(colors[App.clip(1 - orientation, 3)]);
            this.stickers[2].setColor(colors[App.clip(2 - orientation, 3)]);
        }
    }

}
