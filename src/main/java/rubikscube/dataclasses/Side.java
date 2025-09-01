package rubikscube.dataclasses;


import java.util.Random;

import rubikscube.components.Cube;
import rubikscube.components.Piece;


public final class Side {

    public static final Side L = new Side(0);
    public static final Side R = new Side(1);
    public static final Side U = new Side(2);
    public static final Side D = new Side(3);
    public static final Side F = new Side(4);
    public static final Side B = new Side(5);

    public final int id;
    public final char letter;
    public final Axis axis;
    public final boolean isReversed;
    public final int order;

    public final boolean isL;
    public final boolean isR;
    public final boolean isU;
    public final boolean isD;
    public final boolean isF;
    public final boolean isB;

    private Cube cube;


    public Side(int id) {
        assert 0 <= id && id < 6;

        this.id = id;
        switch (this.id) {
            case 1: this.letter = 'R'; break;
            case 2: this.letter = 'U'; break;
            case 3: this.letter = 'D'; break;
            case 4: this.letter = 'F'; break;
            case 5: this.letter = 'B'; break;
            default: this.letter = 'L';
        }
        this.axis = new Axis(this.id / 2);
        this.isReversed = this.id % 2 == 1;
        this.order = id % 2;

        this.isL = this.id == 0;
        this.isR = this.id == 1;
        this.isU = this.id == 2;
        this.isD = this.id == 3;
        this.isF = this.id == 4;
        this.isB = this.id == 5;
    }

    public Side(char letter) {
        assert letter == 'L' || letter == 'R' || letter == 'U' || letter == 'D' || letter == 'F' || letter == 'B';

        this.letter = letter;
        switch (this.letter) {
            case 'R': this.id = 1; break;
            case 'U': this.id = 2; break;
            case 'D': this.id = 3; break;
            case 'F': this.id = 4; break;
            case 'B': this.id = 5; break;
            default: this.id = 0; break;
        }
        this.axis = new Axis(this.id / 2);
        this.isReversed = this.id % 2 == 1;
        this.order = id % 2;

        this.isL = this.id == 0;
        this.isR = this.id == 1;
        this.isU = this.id == 2;
        this.isD = this.id == 3;
        this.isF = this.id == 4;
        this.isB = this.id == 5;
    }

    public Side(Axis axis, int order) {
        assert 0 <= order && order < 2;

        this.id = axis.id * 2 + order;
        switch (this.id) {
            case 1: this.letter = 'R'; break;
            case 2: this.letter = 'U'; break;
            case 3: this.letter = 'D'; break;
            case 4: this.letter = 'F'; break;
            case 5: this.letter = 'B'; break;
            default: this.letter = 'L';
        }
        this.axis = axis;
        this.isReversed = this.id % 2 == 1;
        this.order = order;

        this.isL = this.id == 0;
        this.isR = this.id == 1;
        this.isU = this.id == 2;
        this.isD = this.id == 3;
        this.isF = this.id == 4;
        this.isB = this.id == 5;
    }


    public void setCube(Cube cube) {
        this.cube = cube;
    }

    public Cube getCube() {
        return this.cube;
    }

    public Piece[] getPieces() {
        assert this.cube != null;

        Piece[] pieces = new Piece[9];
        byte currentPieceIndex = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.isL) {
                    pieces[currentPieceIndex] = this.cube.getPiece(new XYZ(-1, i - 1, j - 1));
                } else if (this.isR) {
                    pieces[currentPieceIndex] = this.cube.getPiece(new XYZ(1, i - 1, j - 1));
                } else if (this.isU) {
                    pieces[currentPieceIndex] = this.cube.getPiece(new XYZ(i - 1, -1, j - 1));
                } else if (this.isD) {
                    pieces[currentPieceIndex] = this.cube.getPiece(new XYZ(i - 1, 1, j - 1));
                } else if (this.isF) {
                    pieces[currentPieceIndex] = this.cube.getPiece(new XYZ(i - 1, j - 1, -1));
                } else if (this.isB) {
                    pieces[currentPieceIndex] = this.cube.getPiece(new XYZ(i - 1, j - 1, 1));
                }

                currentPieceIndex++;
            }
        }

        return pieces;
    }

    public void turn(TurnType turnType) {
        Turn.make(this, turnType);
    }

    public static Side random() {
        return new Side(new Random().nextInt(6));
    }

}
