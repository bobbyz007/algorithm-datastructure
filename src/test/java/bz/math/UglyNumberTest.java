package bz.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UglyNumberTest {
    UglyNumber inst = new UglyNumber();

    @Test
    void testNthUglyNumber() {
        Assertions.assertEquals(1, inst.nthUglyNumber(1));
        Assertions.assertEquals(2, inst.nthUglyNumber(2));
        Assertions.assertEquals(3, inst.nthUglyNumber(3));
        Assertions.assertEquals(4, inst.nthUglyNumber(4));
        Assertions.assertEquals(5, inst.nthUglyNumber(5));
        Assertions.assertEquals(6, inst.nthUglyNumber(6));
        Assertions.assertEquals(8, inst.nthUglyNumber(7));
        Assertions.assertEquals(9, inst.nthUglyNumber(8));
        Assertions.assertEquals(10, inst.nthUglyNumber(9));
        Assertions.assertEquals(12, inst.nthUglyNumber(10));

        Assertions.assertEquals(859963392, inst.nthUglyNumber(1500));
    }

    @Test
    void testNthUglyNumber2() {
        Assertions.assertEquals(10, inst.nthUglyNumber(5, 2, 11, 13));

        Assertions.assertEquals(1999999984, inst.nthUglyNumber(1000000000, 2, 217983653, 336916467));
    }

    @Test
    void testNthSuperUglyNumber() {
        Assertions.assertEquals(32, inst.nthSuperUglyNumber(12, new int[]{2, 7, 13, 19}));
        Assertions.assertEquals(2, inst.nthSuperUglyNumber(2, new int[]{2, 3, 5}));

        Assertions.assertEquals(2144153025, inst.nthSuperUglyNumber(5911, new int[]{2, 3, 5, 7}));

    }
}
