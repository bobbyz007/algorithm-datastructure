package bz.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubStringTest {
    SubString inst = new SubString();
    @Test
    void testLongestSubstringWithoutDuplication() {
        Assertions.assertEquals(4, inst.longestSubstringWithoutDuplication("arabcacfr"));
        Assertions.assertEquals(5, inst.longestSubstringWithoutDuplication("aabcaadefga"));
        Assertions.assertEquals(1, inst.longestSubstringWithoutDuplication("aaaaaaaaa"));
        Assertions.assertEquals(2, inst.longestSubstringWithoutDuplication("aaaaabaaa"));
        Assertions.assertEquals(2, inst.longestSubstringWithoutDuplication("aaaaaaaaab"));
    }
}
