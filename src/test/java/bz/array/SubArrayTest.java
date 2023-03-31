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

    @Test
    void testFindTwoNumbersWithSum() {
        Assertions.assertArrayEquals(new int[]{1,2}, inst.findTwoNumbersWithSum(new int[]{1,2,3,4}, 3));
        Assertions.assertArrayEquals(new int[]{4,11}, inst.findTwoNumbersWithSum(new int[]{1,2,4,7,11,15}, 15));
    }

    @Test
    void testFindSubArrayCountWithSum() {
        Assertions.assertEquals(2, inst.findSubArrayCountWithSum(new int[]{1,1,1}, 2));
        Assertions.assertEquals(2, inst.findSubArrayCountWithSum(new int[]{1,2,3}, 3));
        Assertions.assertEquals(4, inst.findSubArrayCountWithSum(new int[]{1,-1,10,-1, 3, 5,0,4}, 9));
    }
}
