package bz.array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class OccurrenceTest {
    Occurrence inst = new Occurrence();

    @Test
    void tesFindTwoNumbersAppearOnlyOnce() {
        int[] result = inst.findTwoNumbersAppearOnlyOnce(new int[]{1, 2, 3, 4, 5, 6, 2, 6, 5, 4});
        Arrays.sort(result);
        Assertions.assertArrayEquals(new int[]{1, 3}, result);

        result = inst.findTwoNumbersAppearOnlyOnce(new int[]{1,2,2,1,3,4,4,3,5,5,6,6,7,7,8,8,9,9,10,10,1000,2000});
        Arrays.sort(result);
        Assertions.assertArrayEquals(new int[]{1000, 2000}, result);

    }

    @Test
    void testFindNumberAppearOnlyOnce() {
        Assertions.assertEquals(3, inst.findNumberAppearOnlyOnce(new int[]{1,1,1,2,2,2,3}));
        Assertions.assertEquals(100, inst.findNumberAppearOnlyOnce(new int[]{1,1,1,100,2,2,2,3,3,3}));

        Assertions.assertEquals(9999, inst.findNumberAppearOnlyOnce(new int[]{1,1,2,2,3, 9999,3,4,4,4,1,2,3}));
    }

    @Test
    void testMoreThanHalfNum() {
        // odd
        Assertions.assertEquals(1, inst.moreThanHalfNum(new int[]{1, 2, 3, 1, 1}));

        // even 5 > 8 / 2
        Assertions.assertEquals(2, inst.moreThanHalfNum(new int[]{1, 2, 3, 2, 1, 2, 2, 2}));

        Assertions.assertEquals(6, inst.moreThanHalfNum(new int[]{6, 7, 6, 3, 6, 6, 2}));

        Assertions.assertEquals(2, inst.moreThanHalfNum(new int[]{2, 2, 2, 2, 2 , 2}));
    }

    @Test
    void testMoreThanHalfNum2() {
        Assertions.assertEquals(inst.moreThanHalfNum(new int[]{1, 2, 3, 1, 1}), inst.moreThanHalfNum2(new int[]{1, 2, 3, 1, 1}));

        Assertions.assertEquals(inst.moreThanHalfNum(new int[]{1, 2, 3, 2, 1, 2, 2, 2}), inst.moreThanHalfNum2(new int[]{1, 2, 3, 2, 1, 2, 2, 2}));

        Assertions.assertEquals(inst.moreThanHalfNum(new int[]{6, 7, 6, 3, 6, 6, 2}), inst.moreThanHalfNum2(new int[]{6, 7, 6, 3, 6, 6, 2}));

        Assertions.assertEquals(inst.moreThanHalfNum(new int[]{2, 2, 2, 2, 2 , 2}), inst.moreThanHalfNum2(new int[]{2, 2, 2, 2, 2 , 2}));
    }

    @Test
    void testGetLeastSmallNumbers() {
        int[] result = inst.getLeastSmallNumbers(new int[]{3, 5, 2, 1, 6, 8}, 2);
        Assertions.assertArrayEquals(new int[]{1, 2}, Arrays.stream(result).sorted().toArray());

        result = inst.getLeastSmallNumbers(new int[]{3, 5, 2, 1, 6, 8}, 4);
        Assertions.assertArrayEquals(new int[]{1, 2, 3, 5}, Arrays.stream(result).sorted().toArray());

        result = inst.getLeastSmallNumbers(new int[]{1, 1, 1, 1, 6, 8}, 3);
        Assertions.assertArrayEquals(new int[]{1, 1, 1}, Arrays.stream(result).sorted().toArray());
    }

    @Test
    void testGetLeastSmallNumbers2() {
        int[] result = inst.getLeastSmallNumbers2(new int[]{3, 5, 2, 1, 6, 8}, 2);
        Assertions.assertArrayEquals(new int[]{1, 2}, Arrays.stream(result).sorted().toArray());

        result = inst.getLeastSmallNumbers2(new int[]{3, 5, 2, 1, 6, 8}, 4);
        Assertions.assertArrayEquals(new int[]{1, 2, 3, 5}, Arrays.stream(result).sorted().toArray());

        result = inst.getLeastSmallNumbers2(new int[]{1, 1, 1, 1, 6, 8}, 3);
        Assertions.assertArrayEquals(new int[]{1, 1, 1}, Arrays.stream(result).sorted().toArray());
    }

    @Test
    void testRandomInRange() throws Exception {
        Method randomMethod = Occurrence.class.getDeclaredMethod("randomInRange", new Class[]{int.class, int.class});
        // 压制java语言的访问检查
        randomMethod.setAccessible(true);

        Assertions.assertEquals(1, randomMethod.invoke(inst, new Object[]{ 1, 1}));

        for (int i = 0; i < 10; i++) {
            int result = (int) randomMethod.invoke(inst, new Object[]{1, 4});
            Assertions.assertTrue(1 <= result && result <= 4);
        }

        for (int i = 0; i < 500; i++) {
            int result = (int) randomMethod.invoke(inst, new Object[]{-10, 999});
            Assertions.assertTrue(-10 <= result && result <= 999);
        }
    }
}
