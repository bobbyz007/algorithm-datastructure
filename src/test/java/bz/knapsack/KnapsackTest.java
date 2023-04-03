package bz.knapsack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KnapsackTest {
    Knapsack inst = new Knapsack();
    @Test
    void testZeroOnePack() {
        // 要求容量小于等于
        Assertions.assertEquals(8, inst.zeroOnePack(new int[]{2, 4, 5, 7, 8}, new int[]{1, 2, 3, 5, 7}, 11));
        Assertions.assertEquals(1, inst.zeroOnePack(new int[]{1, 5}, new int[]{1, 2}, 4));

        // 要求容量正好
        Assertions.assertEquals(7, inst.zeroOnePackEqual(new int[]{2, 4, 5, 7, 8}, new int[]{1, 2, 3, 5, 7}, 11));
        Assertions.assertEquals(0, inst.zeroOnePackEqual(new int[]{1, 5}, new int[]{1, 2}, 4));  // 没有符合要求的物品
    }

    @Test
    void testCompletePack() {
        // 要求容量小于等于
        Assertions.assertEquals(20, inst.completePack(new int[]{2, 4, 5, 7, 8}, new int[]{4, 2, 3, 5, 7}, 11));
        Assertions.assertEquals(4, inst.completePack(new int[]{2, 4}, new int[]{1, 2}, 9));

        // 要求容量正好: 15 = 3*4+1*3
        Assertions.assertEquals(15, inst.completePackEqual(new int[]{2, 4, 5, 7, 8}, new int[]{4, 2, 3, 5, 7}, 11));
        Assertions.assertEquals(0, inst.completePackEqual(new int[]{2, 4}, new int[]{1, 2}, 9));  // 没有符合要求的物品
    }

    @Test
    void testMultiplePack() {
        // 如果是完全背包 20=5*4，而限定数量 13=2*4+3
        Assertions.assertEquals(13, inst.multiplePack(new int[]{2, 4, 5, 7, 8}, new int[]{4, 2, 3, 5, 7}, new int[]{ 2 ,3, 1, 4, 2}, 11));

        // 退化为01背包
        Assertions.assertEquals(inst.zeroOnePack(new int[]{2, 4, 5, 7, 8}, new int[]{4, 2, 3, 5, 7}, 11),
                inst.multiplePack(new int[]{2, 4, 5, 7, 8}, new int[]{4, 2, 3, 5, 7}, new int[]{ 1 ,1, 1, 1, 1}, 11));

        // 退化为完全背包
        Assertions.assertEquals(inst.completePack(new int[]{2, 4, 5, 7, 8}, new int[]{4, 2, 3, 5, 7}, 11),
                inst.multiplePack(new int[]{2, 4, 5, 7, 8}, new int[]{4, 2, 3, 5, 7}, new int[]{ 1000 ,1000, 1000, 1000, 1000}, 11));
    }

    @Test
    void testMultiplePackFeasible() {
        // 正好填满： 11 = 2 + 4 + 5
        Assertions.assertEquals(true, inst.multiplePackFeasible(new int[]{2, 4, 5, 7, 8}, new int[]{1, 1, 1, 1, 1}, 11));
        Assertions.assertEquals(false, inst.multiplePackFeasible(new int[]{1, 2, 4, 6, 8}, new int[]{0, 1, 1, 1, 1}, 11));
        Assertions.assertEquals(true, inst.multiplePackFeasible(new int[]{1, 2, 4, 6, 8}, new int[]{2, 1, 1, 1, 1}, 11));
    }

    @Test
    void testZeroOnePackTwoDimension() {
        Assertions.assertEquals(7, inst.zeroOnePackTwoDimension(new int[][]{ {1, 5}, {2, 6}, {3, 7}, {4, 8} },
                new int[]{2, 3, 5, 7},
                new int[]{10, 11}));

        Assertions.assertEquals(12, inst.zeroOnePackTwoDimension(new int[][]{ {1, 5}, {2, 6}, {3, 7}, {4, 8} },
                new int[]{2, 3, 5, 7},
                new int[]{10, 18}));
    }
}
