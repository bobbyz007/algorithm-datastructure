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

    @Test
    void testFindSubArrayCountWithSumOdd() {
        Assertions.assertEquals(16, inst.findSubArrayCountWithSumOdd(new int[]{1, 2, 3, 4, 5, 6, 7}));
        Assertions.assertEquals(4, inst.findSubArrayCountWithSumOdd(new int[]{100,100,99,99}));
        Assertions.assertEquals(1, inst.findSubArrayCountWithSumOdd(new int[]{7}));
        Assertions.assertEquals(0, inst.findSubArrayCountWithSumOdd(new int[]{2,4,6}));
        Assertions.assertEquals(4, inst.findSubArrayCountWithSumOdd(new int[]{1,3,5}));
        Assertions.assertEquals(35, inst.findSubArrayCountWithSumOdd(new int[]{64,69,7,78,31,83,47,84,47,6,67}));
    }

    @Test
    void testFindLongestCommonLength() {
        Assertions.assertEquals(3, inst.findLongestCommonLength(new int[]{1,2,3,2,1}, new int[]{3,2,1,4,7}));
        Assertions.assertEquals(5, inst.findLongestCommonLength(new int[]{0,0,0,0,0}, new int[]{0,0,0,0,0}));
    }

    @Test
    void testFindMinSubArrayLenWithTarget() {
        Assertions.assertEquals(2, inst.findMinSubArrayLenWithTarget(7, new int[]{2,3,1,2,4,3}));
        Assertions.assertEquals(1, inst.findMinSubArrayLenWithTarget(4, new int[]{1,4,4}));
        Assertions.assertEquals(0, inst.findMinSubArrayLenWithTarget(11, new int[]{1,1,1,1,1,1,1,1}));




    }
}
