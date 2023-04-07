package bz.knapsack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CompletePackAppTest {
    CompletePackApp inst = new CompletePackApp();
    @Test
    void testLargestNumber() {
        Assertions.assertEquals("7772", inst.largestNumber(new int[]{4,3,2,5,6,7,2,5,5}, 9));
        Assertions.assertEquals("85", inst.largestNumber(new int[]{7,6,5,5,5,6,8,7,8}, 12));
        Assertions.assertEquals("0", inst.largestNumber(new int[]{2,4,6,2,4,6,4,4,4}, 5));
        Assertions.assertEquals("32211", inst.largestNumber(new int[]{6,10,15,40,40,40,40,40,40}, 47));
    }

    @Test
    void testCoinChange() {
        Assertions.assertEquals(3, inst.coinChange(new int[]{1,2,5}, 11));
        Assertions.assertEquals(0, inst.coinChange(new int[]{1}, 0));

        // 100*1+5*17+2*2: 1+17+2=20
        Assertions.assertEquals(20, inst.coinChange(new int[]{1,2,5,100}, 189));
    }

    @Test
    void testCoinChangeII() {
        Assertions.assertEquals(4, inst.coinChangeII(new int[]{1,2,5}, 5));
        Assertions.assertEquals(1, inst.coinChangeII(new int[]{1,2,5}, 0));
        /**
         * 1*9
         * 1*7+2*1
         * 1*5+2*2
         * 1*4+5*1
         * 1*3+2*3
         * 1*2+2*1+5*1
         * 1*1+2*4
         * 2*2+5*1
         */
        Assertions.assertEquals(8, inst.coinChangeII(new int[]{1,2,5}, 9));
    }

    @Test
    void testNumSquares() {
        Assertions.assertEquals(3, inst.numSquares(12));
        Assertions.assertEquals(2, inst.numSquares(13));
    }

    @Test
    void testCombinationSum4() {
        Assertions.assertEquals(7, inst.combinationSum4(new int[]{1, 2, 3}, 4));
        Assertions.assertEquals(0, inst.combinationSum4(new int[]{9}, 4));
    }
}
