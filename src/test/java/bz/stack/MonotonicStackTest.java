package bz.stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MonotonicStackTest {
    MonotonicStack inst = new MonotonicStack();
    @Test
    void testSubArrayRangeSum() {
        Assertions.assertEquals(4, inst.subArrayRangeSum(new int[]{1,2,3}));
        Assertions.assertEquals(4, inst.subArrayRangeSum(new int[]{1,3,3}));

        Assertions.assertEquals(59, inst.subArrayRangeSum(new int[]{4,-2,-3,4,1}));
    }
}
