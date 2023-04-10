package bz.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ConvexHullTest {
    ConvexHull inst = new ConvexHull();
    @Test
    void testOuterTrees() {
        int[][] result = inst.outerTrees(new int[][]{{1, 1}, {2, 2}, {2, 0}, {2, 4}, {3, 3}, {4, 2}});
        Arrays.sort(result, (a, b)-> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        Assertions.assertArrayEquals(new int[][]{{1,1}, {2,0}, {2,4}, {3,3}, {4, 2}}, result);

        result = inst.outerTrees(new int[][]{{1, 2}, {2, 2}, {4, 2}});
        Arrays.sort(result, (a, b)-> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        Assertions.assertArrayEquals(new int[][]{{1,2}, {2,2},{4, 2}}, result);

        result = inst.outerTrees(new int[][]{{1, 1}, {6, 6}, {1, 6}, {6,1}, {2,3},{4,4},{5,5},{5,4}, {4,2}, {4,5},{5,2}});
        Arrays.sort(result, (a, b)-> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        Assertions.assertArrayEquals(new int[][]{{1, 1}, {1, 6}, {6,1},{6, 6}}, result);
    }
}
