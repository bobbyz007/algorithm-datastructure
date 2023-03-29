package bz.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KmpTest {
    Kmp inst = new Kmp();
    @Test
    void testKmp() {
        Assertions.assertArrayEquals(new int[]{0,0,0,1,2,0}, inst.next("abcabd".toCharArray()));

        Assertions.assertArrayEquals(new int[]{0,0,1,2,3,4,5,0,0}, inst.next("abababacd".toCharArray()));

        Assertions.assertEquals(-1, inst.kmp("abcabcdabc", "abe"));
        Assertions.assertEquals(3, inst.kmp("abcabcdabc", "abcd"));

    }
}
