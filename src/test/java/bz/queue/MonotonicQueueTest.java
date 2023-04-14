package bz.queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MonotonicQueueTest {
    MonotonicQueue inst = new MonotonicQueue();
    @Test
    void testLongestSubarray() {
        Assertions.assertEquals(2,  inst.longestSubarray(new int[]{8,2,4,7}, 4));
        Assertions.assertEquals(4,  inst.longestSubarray(new int[]{10,1,2,4,7,2}, 5));
        Assertions.assertEquals(3,  inst.longestSubarray(new int[]{4,2,2,2,4,4,2,2}, 0));
    }
}
