package bz.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BinarySearchTest {
    BinarySearch inst = new BinarySearch();

    @Test
    void testBinarySearch() {
        inst.check = mid -> true;
        Assertions.assertEquals(1, inst.binarySearch(0, 1));
        Assertions.assertEquals(0, inst.binarySearch2(0, 1));
        inst.check = mid -> false;
        Assertions.assertEquals(0, inst.binarySearch(0, 1));
        Assertions.assertEquals(1, inst.binarySearch2(0, 1));

        inst.check = mid -> true;
        Assertions.assertEquals(2, inst.binarySearch(0, 2));
        Assertions.assertEquals(0, inst.binarySearch2(0, 2));
        inst.check = mid -> false;
        Assertions.assertEquals(0, inst.binarySearch(0, 2));
        Assertions.assertEquals(2, inst.binarySearch2(0, 2));

        inst.check = mid -> true;
        Assertions.assertEquals(99, inst.binarySearch(1, 99));
        Assertions.assertEquals(1, inst.binarySearch2(1, 99));
        inst.check = mid -> false;
        Assertions.assertEquals(1, inst.binarySearch(1, 99));
        Assertions.assertEquals(99, inst.binarySearch2(1, 99));
    }

    @Test
    void testDivide() {
        Assertions.assertEquals(10 / 3, inst.divide(10, 3));
        Assertions.assertEquals(7 / -3, inst.divide(7, -3));

        Assertions.assertEquals(238234 / 538, inst.divide(238234, 538));
        Assertions.assertEquals(99923423 / 23, inst.divide(99923423, 23));
    }

    @Test
    void testSearchRotate() {
        Assertions.assertEquals(4, inst.searchRotate(new int[]{4,5,6,7,0,1,2}, 0));
        Assertions.assertEquals(-1, inst.searchRotate(new int[]{4,5,6,7,0,1,2}, 3));
    }

    @Test
    void testSearchRange() {
        Assertions.assertArrayEquals(new int[]{3,4}, inst.searchRange(new int[]{5,7,7,8,8,10}, 8));
        Assertions.assertArrayEquals(new int[]{-1,-1}, inst.searchRange(new int[]{5,7,7,8,8,10}, 6));
    }
}
