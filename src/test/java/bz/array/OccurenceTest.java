package bz.array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OccurenceTest {
    Occurence inst = new Occurence();
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
    void testRandomInRange() throws Exception {
        Method randomMethod = Occurence.class.getDeclaredMethod("randomInRange", new Class[]{int.class, int.class});
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
