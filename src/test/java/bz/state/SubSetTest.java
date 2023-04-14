package bz.state;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SubSetTest {
    SubSet inst = new SubSet();

    @Test
    void testSubsetsWithDup() {
        List<List<Integer>> list = Arrays.asList(Arrays.asList(1), Arrays.asList(1,2,2), Arrays.asList(), Arrays.asList(2), Arrays.asList(2,2), Arrays.asList(1,2));
        Assertions.assertEquals(list, inst.subsetsWithDup(new int[]{1, 2, 2}));

        list = Arrays.asList(Arrays.asList(), Arrays.asList(0));
        Assertions.assertEquals(list, inst.subsetsWithDup(new int[]{0}));
    }
}
