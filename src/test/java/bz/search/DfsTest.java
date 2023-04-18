package bz.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class DfsTest {
    @Test
    void testLetterCombinations() {
        Assertions.assertEquals(
                Arrays.asList("adg", "adh", "adi", "aeg", "aeh", "aei", "afg", "afh", "afi", "bdg", "bdh", "bdi", "beg", "beh",
                        "bei", "bfg", "bfh", "bfi", "cdg", "cdh", "cdi", "ceg", "ceh", "cei", "cfg", "cfh", "cfi"),
        new Dfs().letterCombinations("234"));
    }

    @Test
    void testSolveSudoku() {
        char[][] board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        char[][] result = new char[][]{
                {'5', '3', '4', '6', '7', '8', '9', '1', '2'},
                {'6', '7', '2', '1', '9', '5', '3', '4', '8'},
                {'1', '9', '8', '3', '4', '2', '5', '6', '7'},
                {'8', '5', '9', '7', '6', '1', '4', '2', '3'},
                {'4', '2', '6', '8', '5', '3', '7', '9', '1'},
                {'7', '1', '3', '9', '2', '4', '8', '5', '6'},
                {'9', '6', '1', '5', '3', '7', '2', '8', '4'},
                {'2', '8', '7', '4', '1', '9', '6', '3', '5'},
                {'3', '4', '5', '2', '8', '6', '1', '7', '9'}};
        new Dfs().solveSudoku(board);
        Assertions.assertArrayEquals(result, board);
    }

    @Test
    void testCombinationSum() {
        Assertions.assertEquals(Arrays.asList(Arrays.asList(3,5), Arrays.asList(2,3,3),Arrays.asList(2,2,2,2)),
                new Dfs().combinationSum(new int[]{2,3,5}, 8));

        Assertions.assertEquals(Arrays.asList(Arrays.asList(7), Arrays.asList(2,2,3)),
                new Dfs().combinationSum(new int[]{2,3,6, 7}, 7));
    }

    @Test
    void testCombinationSum2() {
        Assertions.assertEquals(Arrays.asList(Arrays.asList(1, 2,5), Arrays.asList(1,1,6),Arrays.asList(2,6), Arrays.asList(1,7)),
                new Dfs().combinationSum2(new int[]{10,1,2,7,6,1,5}, 8));

        Assertions.assertEquals(Arrays.asList(Arrays.asList(1,2,2), Arrays.asList(5)),
                new Dfs().combinationSum2(new int[]{2,5,2,1,2}, 5));
    }

    @Test
    void testRemoveInvalidParentheses() {
        Assertions.assertEquals(Arrays.asList("()()()", "(())()"), new Dfs().removeInvalidParentheses("()())()"));
        Assertions.assertEquals(Arrays.asList("(a)()()", "(a())()"), new Dfs().removeInvalidParentheses("(a)())()"));
        Assertions.assertEquals(Arrays.asList(""), new Dfs().removeInvalidParentheses(")("));
    }
}
