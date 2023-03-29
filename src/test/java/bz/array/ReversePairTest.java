package bz.array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReversePairTest {
    ReversePair inst = new ReversePair();
    @Test
    void testReversePairs() {
        Assertions.assertEquals(5, inst.reversePairs(new int[] {7, 5, 6, 4}));

        Assertions.assertEquals(0, inst.reversePairs(new int[] {4, 5, 6, 7}));
        Assertions.assertEquals(6, inst.reversePairs(new int[] {4, 5, 6, 7, 8, 9, 0}));
        Assertions.assertEquals(18, inst.reversePairs(new int[] {4, 5, 6, 7, 8, 9, 0, 1, 2}));
    }

    @Test
    void testReversePairsWithBIT() {
        Assertions.assertEquals(5, inst.reversePairsWithBIT(new int[] {7, 5, 6, 4}));

        Assertions.assertEquals(0, inst.reversePairsWithBIT(new int[] {4, 5, 6, 7}));
        Assertions.assertEquals(6, inst.reversePairsWithBIT(new int[] {4, 5, 6, 7, 8, 9, 0}));

        Assertions.assertEquals(18, inst.reversePairsWithBIT(new int[] {4, 5, 6, 7, 8, 9, 0, 1, 2}));
    }
}
