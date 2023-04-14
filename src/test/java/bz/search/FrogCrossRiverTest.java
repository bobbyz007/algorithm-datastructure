package bz.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FrogCrossRiverTest {
    @Test
    void testCanCross() {
        Assertions.assertEquals(true, new FrogCrossRiver().canCross(new int[]{0,1,3,5,6,8,12,17}));
        Assertions.assertEquals(true, new FrogCrossRiver().canCrossWithDp(new int[]{0,1,3,5,6,8,12,17}));
        Assertions.assertEquals(true, new FrogCrossRiver().canCrossWithBfs(new int[]{0,1,3,5,6,8,12,17}));

        Assertions.assertEquals(false, new FrogCrossRiver().canCross(new int[]{0,1,2,3,4,8,9,11}));
        Assertions.assertEquals(false, new FrogCrossRiver().canCrossWithDp(new int[]{0,1,2,3,4,8,9,11}));
        Assertions.assertEquals(false, new FrogCrossRiver().canCrossWithBfs(new int[]{0,1,2,3,4,8,9,11}));
    }
}
