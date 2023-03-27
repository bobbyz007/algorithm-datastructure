package bz.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumberCountTest {
    NumberCount inst = new NumberCount();

    @Test
    void testNumberOfOneBetweenOneAndN() {
        Assertions.assertEquals(1, inst.numberOfOneBetweenOneAndN(9));
        Assertions.assertEquals(2, inst.numberOfOneBetweenOneAndN(10));
        Assertions.assertEquals(4, inst.numberOfOneBetweenOneAndN(11));
        Assertions.assertEquals(6, inst.numberOfOneBetweenOneAndN(13));
        Assertions.assertEquals(13, inst.numberOfOneBetweenOneAndN(23));


        Assertions.assertEquals(24 + 20 + 13, inst.numberOfOneBetweenOneAndN(123));

        Assertions.assertEquals(23874, inst.numberOfOneBetweenOneAndN(32839));

    }

    @Test
    void testDigitAtIndex() {
        Assertions.assertEquals(5, inst.digitAtIndex(5));
        Assertions.assertEquals(1, inst.digitAtIndex(13));
        Assertions.assertEquals(4, inst.digitAtIndex(19));
        Assertions.assertEquals(0, inst.digitAtIndex(0));

        Assertions.assertEquals(7, inst.digitAtIndex(1001));

    }
}
