package bz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilTest {
    @Test
    void testLowbit() {
        Assertions.assertEquals(1, Util.lowbit(1));
        Assertions.assertEquals(2, Util.lowbit(2));
        Assertions.assertEquals(1, Util.lowbit(3));
        Assertions.assertEquals(4, Util.lowbit(4));
        Assertions.assertEquals(1, Util.lowbit(5));
        Assertions.assertEquals(2, Util.lowbit(6));
        Assertions.assertEquals(1, Util.lowbit(7));
        Assertions.assertEquals(8, Util.lowbit(8));
        Assertions.assertEquals(1, Util.lowbit(9));
        Assertions.assertEquals(2, Util.lowbit(10));
        Assertions.assertEquals(1, Util.lowbit(11));
        Assertions.assertEquals(4, Util.lowbit(12));
    }
}
