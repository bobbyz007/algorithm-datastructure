package bz.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RabinKarpTest {
    RabinKarp inst = new RabinKarp();
    @Test
    void testMatchRabinKarp() {
        Assertions.assertEquals(9, inst.matchRabinKarp("afcefabccabcdefacee", "abcd"));
        Assertions.assertEquals(-1, inst.matchRabinKarp("abcabcdabc", "abe"));
        Assertions.assertEquals(3, inst.matchRabinKarp("abcabcdabc", "abcd"));
        Assertions.assertEquals(4, inst.matchRabinKarp("acdddf", "df"));
    }
}
