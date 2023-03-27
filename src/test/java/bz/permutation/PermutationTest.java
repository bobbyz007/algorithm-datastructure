package bz.permutation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PermutationTest {
    Permutation p = new Permutation();

    @Test
    void testPermute() {
        List<String> result = p.permute("123");
        Assertions.assertEquals(6, result.size());

        Assertions.assertEquals(120, p.permute("12345").size());
    }

    @Test
    void testCombine() {
        List<List<Integer>> result = p.combine(3, 2);
        Assertions.assertEquals(3, result.size());

        Assertions.assertEquals(10, p.combine(10, 9).size());

        Assertions.assertEquals(10, p.combine2(10, 9).size());

        Assertions.assertEquals(p.combine(10, 7).size(), p.combine2(10, 7).size());

        Assertions.assertEquals(p.combine(15, 7), p.combine2(15, 7));
    }
}
