package bz.queen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QueenMethodTest {
    QueenMethod q = new QueenMethod();
    @Test
    void testArrangeMethod() {
        Assertions.assertEquals(92, q.arrangeMethod(8).size());
    }
}
