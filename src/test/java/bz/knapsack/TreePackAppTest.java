package bz.knapsack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TreePackAppTest {
    // 每次都要重新创建实例，因为TreePack类字段需要初始化
    @Test
    void testMaxValue() {
        /**
         *      1    2      3
         *    4  5     6  7
         *
         * 主件： 1，2，3
         * 附件：4，5，6，7
         * 编号0 预留虚拟根节点
         *
         * 索引：0 1 2 3 4 5 6 7
         * 价格：0 3 1 4 2 1 6 2
         * 重要：0 1 5 3 2 4 2 4
         */
        // 不超过6元，选择节点3，7： 20=4*3+2*4
        Assertions.assertEquals(20, new TreePackApp().maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 6));
        // 不超过7,8或9元，选择节点2, 3，7： 25=1*5+4*3+2*4
        Assertions.assertEquals(25, new TreePackApp().maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 7));
        Assertions.assertEquals(25, new TreePackApp().maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 8));
        Assertions.assertEquals(25, new TreePackApp().maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 9));

        // 不超过10元，，选择节点1，2, 3，7： 28=3*1+1*5+4*3+2*4
        Assertions.assertEquals(28, new TreePackApp().maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 10));

        // 不超过11或12元，，选择节点1，2, 3，5, 7： 32=3*1+1*5+4*3+1*4+2*4
        Assertions.assertEquals(32, new TreePackApp().maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 11));
        Assertions.assertEquals(32, new TreePackApp().maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 12));

        // 不超过13元，，选择节点2, 3，6, 7： 37=1*5+4*3+6*2+2*4
        Assertions.assertEquals(37, new TreePackApp().maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 13));
        Assertions.assertEquals(37, new TreePackApp().maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 14));
        Assertions.assertEquals(37, new TreePackApp().maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 15));

        // 不超过16元，，选择节点1, 2, 3，6, 7： 40=3*1+1*5+4*3+6*2+2*4
        Assertions.assertEquals(40, new TreePackApp().maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 16));

        // 不超过17元，，选择节点1, 2, 3，5, 6, 7： 44=3*1+1*5+4*3+1*4+6*2+2*4
        Assertions.assertEquals(44, new TreePackApp().maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 17));
    }

    /**
     * 验证优化后的分组背包算法 与  原来树形背包算法 结果一致。
     */
    @Test
    void maxValueOptimizedWithGroupPack() {
        /**
         *      1    2      3
         *    4  5     6  7
         *
         * 主件： 1，2，3
         * 附件：4，5，6，7
         * 编号0 预留虚拟根节点
         *
         * 索引：0 1 2 3 4 5 6 7
         * 价格：0 3 1 4 2 1 6 2
         * 重要：0 1 5 3 2 4 2 4
         */
        // 不超过6元，选择节点3，7： 20=4*3+2*4
        TreePackApp inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 6),
                inst.maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 6));

        // 不超过7,8或9元，选择节点2, 3，7： 25=1*5+4*3+2*4
        inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 7),
                inst.maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 7));
        inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 8),
                inst.maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 8));
        inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 9),
                inst.maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 9));

        // 不超过10元，，选择节点1，2, 3，7： 28=3*1+1*5+4*3+2*4
        inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 10),
                inst.maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 10));

        // 不超过11或12元，，选择节点1，2, 3，5, 7： 32=3*1+1*5+4*3+1*4+2*4
        inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 11),
                inst.maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 11));
        inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 12),
                inst.maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 12));

        // 不超过13元，，选择节点2, 3，6, 7： 37=1*5+4*3+6*2+2*4
        inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 13),
                inst.maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 13));
        inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 14),
                inst.maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 14));
        inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 15),
                inst.maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 15));

        // 不超过16元，，选择节点1, 2, 3，6, 7： 40=3*1+1*5+4*3+6*2+2*4
        inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 16),
                inst.maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 16));

        // 不超过17元，，选择节点1, 2, 3，5, 6, 7： 44=3*1+1*5+4*3+1*4+6*2+2*4
        inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 17),
                inst.maxValue(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 17));
    }

    /**
     * 验证优化后的分组背包算法 与  二进制枚举优化的 结果一致。
     */
    @Test
    void maxValueOptimizedWithGroupPackBinary() {
        /**
         *      1    2      3
         *    4  5     6  7
         *
         * 主件： 1，2，3
         * 附件：4，5，6，7
         * 编号0 预留虚拟根节点
         *
         * 索引：0 1 2 3 4 5 6 7
         * 价格：0 3 1 4 2 1 6 2
         * 重要：0 1 5 3 2 4 2 4
         */
        // 不超过6元，选择节点3，7： 20=4*3+2*4
        TreePackApp inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 6),
                inst.maxValueOptimizedWithGroupPackBinary(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 6));

        // 不超过7,8或9元，选择节点2, 3，7： 25=1*5+4*3+2*4
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 7),
                inst.maxValueOptimizedWithGroupPackBinary(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 7));
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 8),
                inst.maxValueOptimizedWithGroupPackBinary(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 8));
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 9),
                inst.maxValueOptimizedWithGroupPackBinary(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 9));

        // 不超过10元，，选择节点1，2, 3，7： 28=3*1+1*5+4*3+2*4
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 10),
                inst.maxValueOptimizedWithGroupPackBinary(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 10));

        // 不超过11或12元，，选择节点1，2, 3，5, 7： 32=3*1+1*5+4*3+1*4+2*4
        inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 11),
                inst.maxValueOptimizedWithGroupPackBinary(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 11));
        inst = new TreePackApp();
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 12),
                inst.maxValueOptimizedWithGroupPackBinary(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 12));

        // 不超过13元，，选择节点2, 3，6, 7： 37=1*5+4*3+6*2+2*4
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 13),
                inst.maxValueOptimizedWithGroupPackBinary(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 13));
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 14),
                inst.maxValueOptimizedWithGroupPackBinary(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 14));
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 15),
                inst.maxValueOptimizedWithGroupPackBinary(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 15));

        // 不超过16元，，选择节点1, 2, 3，6, 7： 40=3*1+1*5+4*3+6*2+2*4
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 16),
                inst.maxValueOptimizedWithGroupPackBinary(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 16));

        // 不超过17元，，选择节点1, 2, 3，5, 6, 7： 44=3*1+1*5+4*3+1*4+6*2+2*4
        Assertions.assertEquals(inst.maxValueOptimizedWithGroupPack(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 17),
                inst.maxValueOptimizedWithGroupPackBinary(new int[]{-1, 0, 0, 0, 1, 1, 2, 3}, new int[]{0, 3, 1, 4, 2, 1, 6, 2}, new int[]{0, 1, 5, 3, 2, 4, 2, 4}, 17));
    }



}
