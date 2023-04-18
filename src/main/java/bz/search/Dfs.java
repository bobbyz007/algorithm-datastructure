package bz.search;

import java.util.*;

/**
 * 回溯算法应用
 * 如何快速判断问题是否应该使用 DFS + 回溯算法来爆搜。
 * 1. 求的是所有的方案，而不是方案数。 由于求的是所有方案，不可能有什么特别的优化，我们只能进行枚举。这时候可能的解法有动态规划、记忆化搜索、DFS + 回溯算法。
 * 2. 通常数据范围 不会太大，只有几十。 如果是动态规划或是记忆化搜索的题的话，由于它们的特点在于低重复/不重复枚举，所以一般数据范围可以出到 10 5 到 10 7 ，
 * 而 DFS + 回溯的话，通常会限制在 30 以内。
 */
public class Dfs {
    /**
     * 17. 电话号码的字母组组合
     *
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
     * 给出数字到字母的映射如下（与电话按键相同），注意 1 不对应任何字母。
     */
    private Map<String, String[]> map = new HashMap<>(){{
        put("2", new String[]{"a", "b", "c"});
        put("3", new String[]{"d", "e", "f"});
        put("4", new String[]{"g", "h", "i"});
        put("5", new String[]{"j", "k", "l"});
        put("6", new String[]{"m", "n", "o"});
        put("7", new String[]{"p", "q", "r", "s"});
        put("8", new String[]{"t", "u", "v"});
        put("9", new String[]{"w", "x", "y", "z"});
    }};
    public List<String> letterCombinations(String ds) {
        int n = ds.length();
        List<String> ans = new ArrayList<>();
        if (n == 0)  {
            return ans;
        }
        StringBuilder sb = new StringBuilder();
        dfs(ds, 0, n, sb, ans);
        return ans;
    }
    private void dfs(String ds, int i, int n, StringBuilder sb, List<String> ans) {
        if (i == n) {
            ans.add(sb.toString());
            return;
        }
        String key = ds.substring(i, i + 1);
        String[] all = map.get(key);
        for (String item : all) {
            sb.append(item);
            dfs(ds, i + 1, n, sb, ans);
            // i是需要回溯的位置：尝试映射的下一个字母
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /**
     * 37. 解数独
     * 通过填充空格来解决数独问题。
     * 数独的解法需 遵循如下规则：
     *   1. 数字 1-9 在每一行只能出现一次。
     *   2. 数字 1-9 在每一列只能出现一次。
     *   3. 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
     * 数独部分空格内已填入了数字，空白格用 ‘.’ 表示。
     * 保证有一个解。
     * 例如：
     *  5 3 . . 7 . . . .
     *  6 . . 1 9 5 . . .
     *  . 9 8 . . . . 6 .
     *  8 . . . 6 . . . 3
     *  4 . . 8 . 3 . . 1
     *  7 . . . 2 . . . 6
     *  . 6 . . . . 2 8 .
     *  . . . 4 1 9 . . 5
     *  . . . . 8 . . 7 9
     */
    // row[i][t]: 第i行的 数字t是否被占用（true/false）
    private boolean[][] row = new boolean[9][9];
    // row[j][t]: 第j列的 数字t是否被占用（true/false）
    private boolean[][] col = new boolean[9][9];
    // cell[k][l][t]: 第k行（总共3行：原来的3行成1行）， 第l列（总共3列）的 3*3宫格内， 数字t是否被占用(true/false)
    private boolean[][][] cell = new boolean[3][3][9];
    public void solveSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int t = board[i][j] - '1';
                    // 初始化数字t 在第i行被使用，在第j列被使用，在对应位置的3*3宫格被使用
                    row[i][t] = col[j][t] = cell[i / 3][j / 3][t] = true;
                }
            }
        }
        // 逐行逐列开始遍历每个位置
        dfs(board, 0, 0);
    }
    // 在第x行，第y列的位置 可以填入什么数字？
    private boolean dfs(char[][] board, int x, int y) {
        // 到了列尾，则从下一行开始
        if (y == 9) {
            return dfs(board, x + 1, 0);
        }
        // 所有行都遍历完了
        if (x == 9) {
            return true;
        }
        // 碰到已填入的数字，则从当前行的右边格子开始
        if (board[x][y] != '.') {
            return dfs(board, x, y + 1);
        }
        // 枚举可决策的选项： 填入数字1...9
        for (int i = 0; i < 9; i++) {
            // 当前第x行，第y列都没有占用数字i， 且在对应位置的3*3宫格也没有占用数字i
            if (!row[x][i] && !col[y][i] && !cell[x / 3][y / 3][i]) {
                // 没有占用，则填入尝试填入数字i
                board[x][y] = (char)(i + '1');
                row[x][i] = col[y][i] = cell[x / 3][y / 3][i] = true;
                if (dfs(board, x, y + 1)) {
                    // 如果后续的行列都填入成功了，则退出循环
                    break;
                } else {
                    // 如果后续的行列都填入失败，则需要回溯，尝试下一个数字i
                    board[x][y] = '.';
                    row[x][i] = col[y][i] = cell[x / 3][y / 3][i] = false;
                }
            }
        }
        // 在第x行，y列位置成功填入了数字
        return board[x][y] != '.';
    }

    /**
     * 39. 组合总和
     * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使
     * 数字和为 target 的组合。
     * candidates 中的数字可以无限制重复被选取。
     *
     * 所有数字（包括 target）都是正整数。
     */
    public List<List<Integer>> combinationSum(int[] cs, int t) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> cur = new ArrayList<>();
        dfs(cs, t, 0, ans, cur);
        return ans;
    }
    /**
     * cs: 原数组，从该数组进行选数
     * t: 还剩多少值需要凑成。起始值为 target ，代表还没选择任何数；当 t = 0，代表选择的数凑成了 target
     * u: 当前决策到 cs[] 中的第几位
     * ans: 最终结果集
     * cur: 当前结果集
     */
    private void dfs(int[] cs, int t, int u, List<List<Integer>> ans, List<Integer> cur) {
        if (t == 0) {
            ans.add(new ArrayList<>(cur));
            return;
        }
        if (u == cs.length || t < 0) {
            return;
        }

        // 枚举 cs[u] 的使用次数
        for (int i = 0; cs[u] * i <= t; i++) {
            dfs(cs, t - cs[u] * i, u + 1, ans, cur);
            cur.add(cs[u]);
        }
        // 进行回溯。上述循环枚举完成后，cur已经添加了多个cs[u],注意回溯总是将数组的最后一位弹出
        for (int i = 0; cs[u] * i <= t; i++) {
            cur.remove(cur.size() - 1);
        }
    }

    /**
     * 40. 组合总和II
     * 类似 39. 组合总和， 唯一的不同是这里要求每个数只能使用一次。
     */
    public List<List<Integer>> combinationSum2(int[] cs, int t) {
        // 排序，结果集利用set去重
        Arrays.sort(cs);
        Set<List<Integer>> ans = new HashSet<>();
        List<Integer> cur = new ArrayList<>();
        dfs(cs, t, 0, ans, cur);
        return new ArrayList<>(ans);
    }
    /**
     * cs: 原数组，从该数组进行选数
     * t: 还剩多少值需要凑成。起始值为 target ，代表还没选择任何数；当 t = 0，代表选择的数凑成了 target
     * u: 当前决策到 cs[] 中的第几位
     * ans: 最终结果集
     * cur: 当前结果集
     */
    private void dfs(int[] cs, int t, int u, Set<List<Integer>> ans, List<Integer> cur) {
        if (t == 0) {
            ans.add(new ArrayList<>(cur));
            return;
        }
        if (u == cs.length || t < 0) return;
        // 使用 cs[u]
        cur.add(cs[u]);
        dfs(cs, t - cs[u], u + 1, ans, cur);
        // 进行回溯
        cur.remove(cur.size() - 1);
        // 不使用 cs[u]
        dfs(cs, t, u + 1, ans, cur);
    }

    /**
     * 301. 删除无效的括号
     * 给你一个由若干括号和字母组成的字符串 s ，删除最小数量的无效括号，使得输入的字符串有效。返回所有可能的结果。
     *
     * 输入: "()())()"
     * 输出: ["()()()", "(())()"]
     */
    private int maxValidBracketLen;
    public List<String> removeInvalidParentheses(String s) {
        char[] cs = s.toCharArray();
        int l = 0, r = 0;
        for (char c : cs) {
            if (c == '(') {
                l++;
            } else if (c == ')') {
                r++;
            }
        }
        int max = Math.min(l, r);
        Set<String> all = new HashSet<>();
        /**
         * 所有的合法方案，必然有左括号的数量与右括号数量相等。
         * 首先我们令左括号的得分为 1；右括号的得分为 -1。那么对于合法的方案而言，必定满足最终得分为 0。
         * 同时可以预处理出「爆搜」过程的最大得分： max = min(左括号的数量, 右括号的数量)
         */
        dfs(cs, 0, 0, max, "", all);

        List<String> ans = new ArrayList<>();
        for (String str : all) {
            // 对应删除最小数量的无效括号
            if (str.length() == maxValidBracketLen) {
                ans.add(str);
            }
        }
        return ans;
    }
    /**
     * cs: 字符串 s 对应的字符数组
     * u: 当前决策到 cs 的哪一位
     * score: 当前决策方案的得分值（每往 cur 追加一个左括号进行 +1；每往 cur 追加一个右括号进行 -1）
     * max: 整个 dfs 过程的最大得分
     * cur: 当前决策方案
     * ans: 合法方案结果集
     */
    private void dfs(char[] cs, int u, int score, int max, String cur, Set<String> ans) {
        if (u == cs.length) {
            if (score == 0 && cur.length() >= maxValidBracketLen) {
                maxValidBracketLen = Math.max(maxValidBracketLen, cur.length());
                ans.add(cur);
            }
            return;
        }
        if (cs[u] == '(') {
            // 左括号：如果当前得分不超过 max - 1 时，我们可以选择添加该左括号，也能选择不添加
            if (score + 1 <= max) {
                dfs(cs, u + 1, score + 1, max, cur + "(", ans);
            }
            dfs(cs, u + 1, score, max, cur, ans);
        } else if (cs[u] == ')') {
            // 右括号：如果当前得分大于 0（说明有一个左括号可以与之匹配），我们可以选择添加该右括号，也能选择不添加
            if (score > 0) {
                dfs(cs, u + 1, score - 1, max, cur + ")", ans);
            }
            dfs(cs, u + 1, score, max, cur, ans);
        } else {
            // 普通字符：无须删除，直接添加
            dfs(cs, u + 1, score, max, cur + String.valueOf(cs[u]), ans);
        }
    }
}
