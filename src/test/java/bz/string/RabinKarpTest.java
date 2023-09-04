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

    @Test
    void testMatchRabinKarpMultiple() {
        Assertions.assertArrayEquals(new int[]{5, 0}, inst.matchRabinKarp("afcefabccabcdefacee", "abcd", 3));
        Assertions.assertArrayEquals(new int[]{9, 0}, inst.matchRabinKarp("afcefabccabcdefacee", "abcd", 4));
        Assertions.assertArrayEquals(null, inst.matchRabinKarp("afcefabccabcdefacee", "abed", 4));


        Assertions.assertArrayEquals(new int[]{0, 0}, inst.matchRabinKarp("abcabcdabc", "abe", 2));
        Assertions.assertArrayEquals(new int[] {3, 0}, inst.matchRabinKarp("abcabcdabc", "abcd", 4));
        Assertions.assertArrayEquals(new int[]{4, 0}, inst.matchRabinKarp("acdddf", "df", 2));

        Assertions.assertArrayEquals(new int[]{2, 3}, inst.matchRabinKarp("ababcdf", "sdfabcd", 3));

    }
}
