package bz.unionfind;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnionFindTest {
    @Test
    void testUnionFind() {
        UnionFind uf = new UnionFind(5);
        uf.union(0, 1);
        uf.union(1, 2);
        Assertions.assertEquals(2, uf.find(0));
        Assertions.assertEquals(2, uf.find(1));
        Assertions.assertEquals(2, uf.find(2));
        Assertions.assertEquals(3, uf.find(3));
        Assertions.assertEquals(4, uf.find(4));

        Assertions.assertTrue(uf.isConnected(0, 0));
        Assertions.assertTrue(uf.isConnected(0, 1));
        Assertions.assertTrue(uf.isConnected(0, 2));
        Assertions.assertTrue(uf.isConnected(1, 2));
        Assertions.assertFalse(uf.isConnected(2, 3));
        Assertions.assertFalse(uf.isConnected(1, 3));
        Assertions.assertFalse(uf.isConnected(1, 4));

        Assertions.assertEquals(3, uf.ringCnt());
        Assertions.assertEquals(uf.ringCntOptimized(), uf.ringCnt());


        uf.union(2, 4);
        Assertions.assertEquals(4, uf.find(0));
        Assertions.assertEquals(4, uf.find(1));
        Assertions.assertEquals(4, uf.find(2));
        Assertions.assertEquals(3, uf.find(3));
        Assertions.assertEquals(4, uf.find(4));

        Assertions.assertTrue(uf.isConnected(1, 4));

        Assertions.assertEquals(2, uf.ringCnt());
        Assertions.assertEquals(uf.ringCntOptimized(), uf.ringCnt());


        uf.union(1, 3);
        Assertions.assertEquals(3, uf.find(0));
        Assertions.assertEquals(3, uf.find(1));
        Assertions.assertEquals(3, uf.find(2));
        Assertions.assertEquals(3, uf.find(3));
        Assertions.assertEquals(3, uf.find(4));

        Assertions.assertTrue(uf.isConnected(2, 3));
        Assertions.assertTrue(uf.isConnected(1, 3));

        Assertions.assertEquals(1, uf.ringCnt());
        Assertions.assertEquals(uf.ringCntOptimized(), uf.ringCnt());
    }
}
