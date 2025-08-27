package rubikscube;


import java.util.Random;


public class RandomInteger {
    public static int get(int min, int max) {
        return min + new Random().nextInt(max - min);
    }
}
