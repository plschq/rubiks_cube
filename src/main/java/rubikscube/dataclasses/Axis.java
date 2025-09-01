package rubikscube.dataclasses;


import java.util.Random;


public final class Axis {

    public static final Axis X = new Axis(0);
    public static final Axis Y = new Axis(1);
    public static final Axis Z = new Axis(2);

    public final int id;
    public final char letter;

    public final boolean isX;
    public final boolean isY;
    public final boolean isZ;


    public Axis(int id) {
        assert 0 <= id && id < 3;

        this.id = id;
        switch (this.id) {
            case 1: this.letter = 'Y'; break;
            case 2: this.letter = 'Z'; break;
            default: this.letter = 'X';
        }

        this.isX = this.id == 0;
        this.isY = this.id == 1;
        this.isZ = this.id == 2;
    }

    public Axis(char letter) {
        assert letter == 'X' || letter == 'Y' || letter == 'Z';

        this.letter = letter;
        switch (this.letter) {
            case 'Y': this.id = 1; break;
            case 'Z': this.id = 2; break;
            default: this.id = 0; break;
        }

        this.isX = this.letter == 'X';
        this.isY = this.letter == 'Y';
        this.isZ = this.letter == 'Z';
    }


    public static Axis random() {
        return new Axis(new Random().nextInt(3));
    }

}
