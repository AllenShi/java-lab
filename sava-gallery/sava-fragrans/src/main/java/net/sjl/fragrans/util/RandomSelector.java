package net.sjl.fragrans.util;

import java.util.Arrays;
import java.util.Random;

public class RandomSelector {

    private static int[] TIME_OPTIONS = new int[] {0, 100, 11000};
    private final Random random = new Random();

    public int getSelectedTime(int[] distributions, int[] options) {
        assert distributions != null && options != null && distributions.length == options.length;
        // Arrays.sort(distributions);

        long negCount = Arrays.stream(distributions).filter((a) -> a <= 0).count();
        if(negCount > 0) return options[0];

        Arrays.stream(distributions).reduce(0, (sum, a) -> sum + a);


        Integer sum = Arrays.stream(distributions).filter((a) -> a > 0).reduce(0, (a, b) -> a + b);
        if(sum == 0) return options[0];


        int index = random.nextInt(sum);

        return index;

    }



}
