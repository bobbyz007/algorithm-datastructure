package bz.array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HeapMedianTest {
    HeapMedian inst = new HeapMedian();

    @Test
    void testMedian() {
        inst.insert(new int[]{2, 3, 4, 5, 9, 1});
        Assertions.assertEquals(3, inst.getMedian());

        inst.insert(8);
        Assertions.assertEquals(4, inst.getMedian());

        inst.insert(new int[]{6, 7});
        Assertions.assertEquals(5, inst.getMedian());
    }
}
