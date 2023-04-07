package bz.knapsack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TreePackTest {
    // 每次都要重新创建实例，因为TreePack类字段需要初始化
    @Test
    void testMaxValue() {
        /**
         *         0
         *      1    2
         *    3  4     5
         *
         * 顶点：0  1  2  3  4  5
         * 成本：3, 1, 4, 2, 1, 6
         * 价值：1, 5, 3, 2, 4, 2
         */
        // 选择节点0，1，4： 10=1+5+4
        Assertions.assertEquals(10, new TreePack().maxValue(new int[]{-1, 0, 0, 1, 1, 2}, new int[]{3, 1, 4, 2, 1, 6}, new int[]{1, 5, 3, 2, 4, 2}, 6));

        // 选择节点0，1，3，4： 12=1+5+2+4
        Assertions.assertEquals(12, new TreePack().maxValue(new int[]{-1, 0, 0, 1, 1, 2}, new int[]{3, 1, 4, 2, 1, 6}, new int[]{1, 5, 3, 2, 4, 2}, 7));
        Assertions.assertEquals(12, new TreePack().maxValue(new int[]{-1, 0, 0, 1, 1, 2}, new int[]{3, 1, 4, 2, 1, 6}, new int[]{1, 5, 3, 2, 4, 2}, 8));

        // 选择节点0，1，2，4： 13=1+5+3+4
        Assertions.assertEquals(13, new TreePack().maxValue(new int[]{-1, 0, 0, 1, 1, 2}, new int[]{3, 1, 4, 2, 1, 6}, new int[]{1, 5, 3, 2, 4, 2}, 9));
        Assertions.assertEquals(13, new TreePack().maxValue(new int[]{-1, 0, 0, 1, 1, 2}, new int[]{3, 1, 4, 2, 1, 6}, new int[]{1, 5, 3, 2, 4, 2}, 10));

        // 选择节点0，1，2，3，4
        Assertions.assertEquals(15, new TreePack().maxValue(new int[]{-1, 0, 0, 1, 1, 2}, new int[]{3, 1, 4, 2, 1, 6}, new int[]{1, 5, 3, 2, 4, 2}, 11));
        Assertions.assertEquals(15, new TreePack().maxValue(new int[]{-1, 0, 0, 1, 1, 2}, new int[]{3, 1, 4, 2, 1, 6}, new int[]{1, 5, 3, 2, 4, 2}, 12));
        Assertions.assertEquals(15, new TreePack().maxValue(new int[]{-1, 0, 0, 1, 1, 2}, new int[]{3, 1, 4, 2, 1, 6}, new int[]{1, 5, 3, 2, 4, 2}, 13));
        Assertions.assertEquals(15, new TreePack().maxValue(new int[]{-1, 0, 0, 1, 1, 2}, new int[]{3, 1, 4, 2, 1, 6}, new int[]{1, 5, 3, 2, 4, 2}, 14));
        Assertions.assertEquals(15, new TreePack().maxValue(new int[]{-1, 0, 0, 1, 1, 2}, new int[]{3, 1, 4, 2, 1, 6}, new int[]{1, 5, 3, 2, 4, 2}, 15));
        Assertions.assertEquals(15, new TreePack().maxValue(new int[]{-1, 0, 0, 1, 1, 2}, new int[]{3, 1, 4, 2, 1, 6}, new int[]{1, 5, 3, 2, 4, 2}, 16));

        // 选择节点0，1，2，3，4，5
        Assertions.assertEquals(17, new TreePack().maxValue(new int[]{-1, 0, 0, 1, 1, 2}, new int[]{3, 1, 4, 2, 1, 6}, new int[]{1, 5, 3, 2, 4, 2}, 17));
    }
}
