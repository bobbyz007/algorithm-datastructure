package bz.array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SumOfNNumberTest {
    SumOfNNumber inst = new SumOfNNumber();
    @Test
    void testThreeSum() {
        Assertions.assertEquals(Arrays.asList(Arrays.asList(-1,-1,2), Arrays.asList(-1,0,1)), inst.threeSum(new int[]{-1,0,1,2,-1,-4}));
    }

    @Test
    void testThreeSumClosest() {
        Assertions.assertEquals(2, inst.threeSumClosest(new int[]{-1,2,1,-4}, 1));
    }

    @Test
    void testFourSum() {
        Assertions.assertEquals(Arrays.asList(Arrays.asList(-2,-1,1,2), Arrays.asList(-2,0,0,2), Arrays.asList(-1,0,0,1)),
                inst.fourSum(new int[]{1,0,-1,0,-2,2}, 0));

        Assertions.assertEquals(Arrays.asList(Arrays.asList(-2, 0, 1, 2), Arrays.asList(-1, 0, 0, 2)),
                inst.fourSum(new int[]{1,0,-1,0,-2,2}, 1));
    }
}
