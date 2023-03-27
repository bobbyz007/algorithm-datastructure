package bz.array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubArrayTest {
    SubArray inst = new SubArray();

    @Test
    void testFindGreatestSumOfSuArray() {
        Assertions.assertEquals(16, inst.findGreatestSumOfSuArray(new int[]{1, -2, 3, 10, -4, 7}));
        Assertions.assertEquals(2, inst.findGreatestSumOfSuArray(new int[]{1, -1, -1, 2, -1, -1}));
        Assertions.assertEquals(301, inst.findGreatestSumOfSuArray(new int[]{1, 2, 3, -100, -200, 301}));
    }
}
