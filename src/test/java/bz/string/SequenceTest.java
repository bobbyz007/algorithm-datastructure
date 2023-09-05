package bz.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SequenceTest {
    Sequence inst = new Sequence();
    @Test
    void testLevenshtein() {
        // 替换1次
        Assertions.assertEquals(1, inst.levenshtein("a", "b"));
        Assertions.assertEquals(1, inst.levenshtein("a", "ab"));

        Assertions.assertEquals(1, inst.levenshtein("a", "aa"));

        // 方案可能有多种
        // 1. lada->aada->auda->audi
        // 2. lada->ada->auda->audi
        Assertions.assertEquals(3, inst.levenshtein("lada", "audi"));
        Assertions.assertEquals(6, inst.levenshtein("linda", "justin"));
    }

    @Test
    void testLcs() {
        Assertions.assertEquals("", inst.lcs("a", "b"));
        String result = inst.lcs("abcdae", "aedbea");
        Assertions.assertTrue(result.equals("aba") || result.equals("ade"));

        Assertions.assertEquals("abcd", inst.lcs("jaubsctidnn", "abnnnnnnczzzzzd"));
    }

    @Test
    void testLis() {

        Assertions.assertEquals(3, inst.lis(new int[]{3, 5, 6, 1}));
        Assertions.assertEquals(3, inst.lis(new int[]{3, 5, 6, 1, 2}));
        Assertions.assertEquals(4, inst.lis(new int[]{3, 5, 6, 1, 2, 7}));
        Assertions.assertEquals(5, inst.lis(new int[]{3, 5, 6, 1, 2, 7, 8}));

        Assertions.assertEquals(6, inst.lis(new int[]{3, 5, 6, 2, 5, 4, 19, 5, 5, 6, 7, 12}));
        // 3 5 5 5 5 6 7 12
        Assertions.assertEquals(8, inst.lisNonDesc(new int[]{3, 5, 6, 2, 5, 4, 19, 5, 5, 6, 7, 12}));

        Assertions.assertEquals(3, inst.lis(new int[]{3, 5, 6}));
        Assertions.assertEquals(3, inst.lisNonDesc(new int[]{3, 5, 6}));

        Assertions.assertEquals(1, inst.lis(new int[]{3, 3, 3}));
        Assertions.assertEquals(3, inst.lisNonDesc(new int[]{3, 3, 3}));

        Assertions.assertEquals(2, inst.lis(new int[]{3, 8, 3, 3, 3}));
        Assertions.assertEquals(4, inst.lisNonDesc(new int[]{3, 8, 3, 3, 3}));
    }
}
