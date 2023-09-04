package bz.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManacherTest {
    Manacher inst = new Manacher();
    @Test
    void testManacher() {
        Assertions.assertArrayEquals(new int[]{1, 10}, inst.manacher("babcbabcbaccba"));
        Assertions.assertArrayEquals(new int[]{3, 8}, inst.manacher("tccabcbasz"));
        Assertions.assertArrayEquals(new int[]{0, 11}, inst.manacher("tccabcbacct"));


        Assertions.assertArrayEquals(new int[]{0, 1}, inst.manacher("ab"));
    }
}
