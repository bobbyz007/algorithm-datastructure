package bz.permutation;

import bz.permutation.Permutation;
import org.junit.jupiter.api.Test;
public class PermutationTest {
    Permutation p = new Permutation();

    @Test
    void testPermute() {
        p.permute("123");
    }
}
