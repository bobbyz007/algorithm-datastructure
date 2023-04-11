package bz.unionfind;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnionFindAppTest {
    UnionFindApp inst = new UnionFindApp();
    @Test
    void testMinSwapsCouples() {
        Assertions.assertEquals(1, inst.minSwapsCouples(new int[]{0, 2, 1, 3}));
        Assertions.assertEquals(0, inst.minSwapsCouples(new int[]{3, 2, 0, 1}));

        /**
         * 5对情侣：   0, 9, 2, 6, 5, 8, 3, 7, 4, 1
         * 第一次交换： 0, 1, 2, 6, 5, 8, 3, 7, 4, 9
         * 第二次交换： 0, 1, 2, 3, 5, 8, 6, 7, 4, 9
         * 第三次交换： 0, 1, 2, 3, 5, 4, 6, 7, 8, 9
         */
        Assertions.assertEquals(3, inst.minSwapsCouples(new int[]{0, 9, 2, 6, 5, 8, 3, 7, 4, 1}));
    }
}
