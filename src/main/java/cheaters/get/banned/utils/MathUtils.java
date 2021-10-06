package cheaters.get.banned.utils;

import java.util.Random;

public class MathUtils {

    private static final Random random = new Random();

    public static int random(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

}
