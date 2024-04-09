package co.istad.mbanking.util;

import java.util.Random;

public class RandomUtil {
    public static String generate9Digits(){
        Random random = new Random();
        return String.valueOf(random.nextInt(1000000000));
    }

}
