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

    @Test
    void testStringPower() {
        Assertions.assertEquals(1, inst.stringPower("abcd"));
        Assertions.assertEquals(1, inst.stringPower("abcdefgg"));

        Assertions.assertEquals(4, inst.stringPower("aaaa"));
        Assertions.assertEquals(3, inst.stringPower("ababab"));
        Assertions.assertEquals(4, inst.stringPower("abaabaabaaba"));
    }
}
