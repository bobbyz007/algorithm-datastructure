package bz.queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DoubleEndQueueTest {
    DoubleEndQueue inst = new DoubleEndQueue();
    @Test
    void testReverseParentheses() {
        Assertions.assertEquals("iloveu", inst.reverseParentheses("(u(love)i)"));
        Assertions.assertEquals("leetcode", inst.reverseParentheses("(ed(et(oc))el)"));
        Assertions.assertEquals("apmnolkjihgfedcbq", inst.reverseParentheses("a(bcdefghijkl(mno)p)q"));
    }
}
