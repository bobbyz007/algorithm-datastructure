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

}
