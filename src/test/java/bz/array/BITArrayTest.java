package bz.array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BITArrayTest {
    @Test
    void testSingleUpdateAndSingleQuery() {
        BITArray bitArray = new BITArray(50);
        bitArray.update(1, 3);
        bitArray.update(2, 4);
        bitArray.update(3, 5);

        Assertions.assertEquals(7, bitArray.getsum(2));
        Assertions.assertEquals(12, bitArray.singleQuery(3));
        Assertions.assertEquals(12, bitArray.singleQuery(50));

        bitArray.update(2, 1);
        bitArray.update(3, 1);
        Assertions.assertEquals(3, bitArray.getsum(1));

        Assertions.assertEquals(8, bitArray.getsum(2));
        Assertions.assertEquals(14, bitArray.singleQuery(3));
        Assertions.assertEquals(14, bitArray.singleQuery(50));
    }

    @Test
    void testRangeUpdateAndSingleQuery() {
        BITArray bitArray = new BITArray(50);
        // 修改单个值不能单独调用update函数
        bitArray.rangeAddForRS(1, 1, 3);
        bitArray.rangeAddForRS(2, 3, 5);
        Assertions.assertEquals(3, bitArray.singleQuery(1));
        Assertions.assertEquals(5, bitArray.singleQuery(2));
        Assertions.assertEquals(5, bitArray.singleQuery(3));

        bitArray.rangeAddForRS(3, 5, 10);
        Assertions.assertEquals(15, bitArray.singleQuery(3));
        Assertions.assertEquals(10, bitArray.singleQuery(4));
        Assertions.assertEquals(10, bitArray.singleQuery(5));

        bitArray.rangeAddForRS(1, 4, 100);
        Assertions.assertEquals(103, bitArray.singleQuery(1));
        Assertions.assertEquals(105, bitArray.singleQuery(2));
        Assertions.assertEquals(115, bitArray.singleQuery(3));
        Assertions.assertEquals(110, bitArray.singleQuery(4));
        Assertions.assertEquals(10, bitArray.singleQuery(5));
    }

    @Test
    void testRangeUpdateAndRangeQuery() {
        BITArray bitArray = new BITArray(50, true);
        bitArray.rangeAddForRR(1, 1, 3);
        bitArray.rangeAddForRR(2, 3, 5);

        // pos:  1 2 3
        // value 3 5 5
        Assertions.assertEquals(3, bitArray.rangeQuery(1, 1));
        Assertions.assertEquals(8, bitArray.rangeQuery(1, 2));
        Assertions.assertEquals(10, bitArray.rangeQuery(2, 3));
        Assertions.assertEquals(13, bitArray.rangeQuery(1, 3));

        bitArray.rangeAddForRR(3, 5, 10);
        // pos:  1 2 3   4  5
        // value 3 5 15  10 10
        Assertions.assertEquals(3, bitArray.rangeQuery(1, 1));
        Assertions.assertEquals(23, bitArray.rangeQuery(1, 3));
        Assertions.assertEquals(20, bitArray.rangeQuery(4, 5));
        Assertions.assertEquals(43, bitArray.rangeQuery(1, 5));

        bitArray.rangeAddForRR(1, 4, 100);
        // pos:  1   2   3    4   5
        // value 103 105 115  110 10
        Assertions.assertEquals(110, bitArray.rangeQuery(4, 4));
        Assertions.assertEquals(220, bitArray.rangeQuery(2, 3));
        Assertions.assertEquals(433, bitArray.rangeQuery(1, 4));
        Assertions.assertEquals(120, bitArray.rangeQuery(4, 5));
        Assertions.assertEquals(10, bitArray.rangeQuery(5, 5));
    }


}
