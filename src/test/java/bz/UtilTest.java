package bz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class UtilTest {
    @Test
    void testLowbit() {
        Assertions.assertEquals(1, Util.lowestOneBit(1));
        Assertions.assertEquals(2, Util.lowestOneBit(2));
        Assertions.assertEquals(1, Util.lowestOneBit(3));
        Assertions.assertEquals(4, Util.lowestOneBit(4));
        Assertions.assertEquals(1, Util.lowestOneBit(5));
        Assertions.assertEquals(2, Util.lowestOneBit(6));
        Assertions.assertEquals(1, Util.lowestOneBit(7));
        Assertions.assertEquals(8, Util.lowestOneBit(8));
        Assertions.assertEquals(1, Util.lowestOneBit(9));
        Assertions.assertEquals(2, Util.lowestOneBit(10));
        Assertions.assertEquals(1, Util.lowestOneBit(11));
        Assertions.assertEquals(4, Util.lowestOneBit(12));

        // -3: 11111111111111111111111111111101
        Assertions.assertEquals(1, Util.lowestOneBit(-3));
        // -150: 11111111111111111111111101101010
        Assertions.assertEquals(2, Util.lowestOneBit(-150));
        // -32308: 11111111111111111000000111001100
        Assertions.assertEquals(4, Util.lowestOneBit(-32308));
        // 0B11111111111111111000000111100000
        Assertions.assertEquals(32, Util.lowestOneBit(-32288));
    }

    @Test
    void testRightestBitOneToZero() {
        Assertions.assertEquals(2, Util.rightestBitOneToZero(3));
        Assertions.assertEquals(0, Util.rightestBitOneToZero(64));
    }

    @Test
    void testSubSetWithBitOne() {
        Assertions.assertEquals(Arrays.asList(11, 10, 9, 8, 3, 2, 1), Util.subSetWithBitOne(11));
        Assertions.assertEquals(Arrays.asList(3, 2, 1), Util.subSetWithBitOne(3));
        Assertions.assertEquals(Arrays.asList(4), Util.subSetWithBitOne(4));
        Assertions.assertEquals(Arrays.asList(6, 4, 2), Util.subSetWithBitOne(6));
    }
}
