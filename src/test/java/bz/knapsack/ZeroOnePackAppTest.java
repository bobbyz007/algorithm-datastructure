package bz.knapsack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZeroOnePackAppTest {
    ZeroOnePackApp inst = new ZeroOnePackApp();

    @Test
    void testCanPartition() {
        Assertions.assertEquals(true, inst.canPartition(new int[]{1, 2, 3, 4, 5, 6, 7}));
        Assertions.assertEquals(false, inst.canPartition(new int[]{1, 2, 3, 4, 5, 6}));
        Assertions.assertEquals(false, inst.canPartition(new int[]{1, 1,1,2}));
        Assertions.assertEquals(true, inst.canPartition(new int[]{1, 1,2}));
    }

    @Test
    void testFindMaxForm() {
        Assertions.assertEquals(4, inst.findMaxForm(new String[]{"10", "0001", "111001", "1", "0"}, 5, 3));
        Assertions.assertEquals(2, inst.findMaxForm(new String[]{"10", "0", "1"}, 1, 1));
    }

    @Test
    void testfindTargetSumWays() {
        Assertions.assertEquals(5, inst.findTargetSumWays(new int[]{1,1,1,1,1}, 3));
        Assertions.assertEquals(1, inst.findTargetSumWays(new int[]{1}, 1));

        Assertions.assertEquals(11, inst.findTargetSumWays(new int[]{1,2,3,4,5,6,7,8}, 10));
    }

    @Test
    void testProfitableSchemes() {
        Assertions.assertEquals(2, inst.profitableSchemes(5, 3, new int[]{2, 2}, new int[]{2, 3}));
        Assertions.assertEquals(7, inst.profitableSchemes(10, 5, new int[]{2, 3, 5}, new int[]{6, 7, 8}));
    }

    @Test
    void testLastStoneWeightII() {
        Assertions.assertEquals(1, inst.lastStoneWeightII(new int[]{2,7,4,1,8,1}));
        Assertions.assertEquals(5, inst.lastStoneWeightII(new int[]{31,26,33,21,40}));
    }

    @Test
    void testProbabilityOfHeads() {
        Assertions.assertEquals(0.4, inst.probabilityOfHeads(new double[]{0.4}, 1));
        Assertions.assertEquals(0.03125, inst.probabilityOfHeads(new double[]{0.5,0.5,0.5,0.5,0.5}, 0));
    }
}
