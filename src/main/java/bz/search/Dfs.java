package bz.search;

import bz.Util;

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
    private Map<String, String[]> numAlphaMap = new HashMap<>(){{
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
        String[] all = numAlphaMap.get(key);
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

    /**
     * 1239. 串联字符串的最大长度
     * 给定一个字符串数组 arr，字符串 s 是将 arr 某一子序列字符串连接所得的字符串，如果 s 中的每一个字符都只出现过一次，那么它就是一个可行解。
     * 请返回所有可行解 s 中最长长度。
     *
     * 输入：arr = ["un","iq","ue"]
     * 输出：4
     * 解释：所有可能的串联组合是 "","un","iq","ue","uniq" 和 "ique"，最大长度为 4。
     *
     * 思路：剪枝DFS
     */
    private int maxLen = Integer.MIN_VALUE;
    private int[] hash; // 字符串对应的hash值
    public int maxLength(List<String> ws) {
        HashSet<Integer> set = new HashSet<>();
        for (String s : ws) {
            // 由于只关心某个字符是否出现，因此考虑用一个整数（32 bits） 来表示 一个有效的不重复字符串（最多26位字母）
            int val = 0;
            for (char c : s.toCharArray()) {
                int t = c - 'a';
                // 说明字母c已经重复出现过，直接剔除。
                if (((val >> t) & 1) != 0) {
                    val = -1;
                    break;
                }
                // 对应的位置t 设置为1
                val |= (1 << t);
            }
            if (val != -1) {
                set.add(val);
            }
        }

        int n = set.size();
        if (n == 0) {
            return 0;
        }

        // 存放有效的字符串的hash值（也就是上面求解的整数表示）
        hash = new int[n];
        int idx = 0;
        // 可能拼接的最大值（对应字符串拼接的最大长度）
        int total = 0;
        for (Integer i : set) {
            hash[idx++] = i;
            total |= i;
        }
        dfs(0, 0, total, n);
        return maxLen;
    }

    /**
     * 剪枝DFS
     * @param u 当前决策到第几个字符串
     * @param cur 截止到当前已经串联后的字符串的 hash值
     * @param total 后续未经处理的字符串所剩余的“最大价值”是多少，从而实现剪枝
     * @param n 字符串个数，不变。
     */
    void dfs(int u, int cur, int total, int n) {
        // 剪枝： 串联当前价值和剩余的最大价值 都小于等于 最大长度，直接返回。
        if (getBitOneCount(cur | total) <= maxLen)  {
            return;
        }
        if (u == n) {
            // bit 1的个数就是字符串的字符个数
            maxLen = Math.max(maxLen, getBitOneCount(cur));
            return;
        }
        // 在原有基础上，选择该数字（不存在重复字符）
        if ((hash[u] & cur) == 0) {
            dfs(u + 1, hash[u] | cur, total - (total & hash[u]), n);
        }
        // 不选择该数字（不管是否存在重复字符，都可以不选）
        dfs(u + 1, cur, total, n);
    }
    static Map<Integer, Integer> bitOneMap = new HashMap<>();
    int getBitOneCount(int cur) {
        if (bitOneMap.containsKey(cur)) {
            return bitOneMap.get(cur);
        }
        int ans = 0;
        for (int i = cur; i > 0; i -= Util.lowestOneBit(i)) {
            ans++;
        }
        bitOneMap.put(cur, ans);
        return ans;
    }

    /**
     * 1723. 完成所有工作的最短时间
     * 给你一个整数数组 jobs ，其中 jobs[i] 是完成第 i 项工作要花费的时间。
     * 请你将这些工作分配给 k 位工人。所有工作都应该分配给工人，且每项工作只能分配给一位工人。
     * 工人的 工作时间 是完成分配给他们的所有工作花费时间的总和。
     * 请你设计一套最佳的工作分配方案，使工人的 最大工作时间 得以 最小化 。 返回分配方案中尽可能「最小」的 最大工作时间 。
     *
     * 也就是求解：n个数分成k份， 并且尽可能让k份平均。 这样最大的工作时间才是最小的。
     *
     * 思路： 朴素解法，可能超时
     * 搜索空间（3个job，2个worker）
     * {{0, 1, 2}, {}}
     * {{0, 1}, {2}}
     * {{0, 2}, {1}}
     * {{0}, {1, 2}}
     * {{1, 2}, {0}}  //重复
     * {{1}, {0, 2}}  //重复
     * {{2}, {0, 1}}    //重复
     * {{}, {0, 1, 2}}  //重复
     */
    private int[] jobs;
    private int jobCnt, workers;
    private int minTime = Integer.MAX_VALUE;
    public int minimumTimeRequired(int[] jobs, int k) {
        this.jobs = jobs;
        this.jobCnt = jobs.length;
        this.workers = k;
        int[] sum = new int[workers];
        dfs(0, sum, 0);
        return minTime;
    }
    /**
     * u: 当前处理到那个 job
     * sum : 工人的分配情况，例如：sum[0] = x 代表 0 号工人工作量为 x
     * max : 当前的「最大工作时间」
     */
    void dfs(int u, int[] sum, int max) {
        if (max >= minTime) {
            return;
        }
        if (u == jobCnt) {
            minTime = max;
            return;
        }
        for (int i = 0; i < workers; i++) {
            sum[i] += jobs[u];
            dfs(u + 1, sum, Math.max(sum[i], max));
            sum[i] -= jobs[u];
        }
    }

    /**
     * 优化dfs剪枝
     * 搜索空间（3个job，2个worker）
     * {{0, 2}, {1}}
     * {{0}, {1, 2}}
     * {{0, 1}, {2}}
     * {{0, 1, 2}, {}}
     */
    public int minimumTimeRequiredOptimized(int[] jobs, int k) {
        this.jobs = jobs;
        this.jobCnt = jobs.length;
        this.workers = k;
        int[] sum = new int[workers];
        dfs(0, 0, sum, 0);
        return minTime;
    }
    /**
     * u: 当前处理到那个 job
     * used : 当前分配给了多少个工人了
     * sum: 工人的分配情况，例如：sum[0] = x 代表 0 号工人工作量为 x
     * max: 当前的「最大工作时间」
     */
    void dfs(int u, int used, int[] sum, int max) {
        if (max >= minTime) {
             return;
        }
        if (u == jobCnt) {
            minTime = max;
            return;
        }
        // 优先分配给「空闲工人」
        if (used < workers) {
            sum[used] = jobs[u];
            dfs(u + 1, used + 1, sum, Math.max(sum[used], max));
            sum[used] = 0;
        }
        for (int i = 0; i < used; i++) {
            sum[i] += jobs[u];
            dfs(u + 1, used, sum, Math.max(sum[i], max));
            sum[i] -= jobs[u];
        }
    }

    /**
     * 1766. 互质数
     * 也就是对树的每个节点从下往上找，找到最近的「与其互质」的祖先节点。
     *
     * 给你一个 n 个节点的树（也就是一个无环连通无向图），节点编号从 0 到 n - 1 ，且恰好有 n -1 条边，每个节点有一个值。树的 根节点 为 0 号点。
     * 给你一个整数数组 nums 和一个二维数组 edges 来表示这棵树。nums[i] 表示第 i 个点的值，edges[j] = [uj, vj] 表示节点 uj 和节点 vj 在树中有一条边。
     * 一个节点 不是 它自己的祖先节点。
     *
     * 请你返回一个大小为 n 的数组 ans ，其中 ans[i]是离节点 i 最近的祖先节点且满足 nums[i] 和 nums[ans[i]] 是 互质的 ，如果不存在这样的祖先节点，ans[i] 为 -1 。
     *
     * 1 <= nums[i] <= 50
     * 1 <= nums.length <= 10^5
     */
    int[] closestPrimeAncestor;
    Map<Integer, List<Integer>> edgeMap = new HashMap<>(); // 边映射
    Map<Integer, List<Integer>> primeMap = new HashMap<>(); // 互质数字典
    int[] depth; // 节点高度，根节点最低
    int[] pos = new int[52]; // 节点值对应的编号
    public int[] getCoprimes(int[] nums, int[][] edges) {
        int n = nums.length;
        closestPrimeAncestor = new int[n];
        depth = new int[n];
        Arrays.fill(closestPrimeAncestor, - 1);
        Arrays.fill(pos, -1);
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1];
            List<Integer> alist = edgeMap.getOrDefault(a, new ArrayList<>());
            alist.add(b);
            edgeMap.put(a, alist);
            List<Integer> blist = edgeMap.getOrDefault(b, new ArrayList<>());
            blist.add(a);
            edgeMap.put(b, blist);
        }
        for (int i = 1; i <= 50; i++) {
            for (int j = 1; j <= 50; j++) {
                if (Util.gcd(i, j) == 1) {
                    List<Integer> list = primeMap.getOrDefault(i, new ArrayList<>());
                    list.add(j);
                    primeMap.put(i, list);
                }
            }
        }

        dfs(nums, 0, -1);

        return closestPrimeAncestor;
    }

    /**
     * @param nums nums[i]=j 表示i是节点编号， j是节点值
     * @param u 当前决策的节点编号
     * @param parent u的父亲节点编号
     */
    private void dfs(int[] nums, int u, int parent) {
        int uValue = nums[u];
        for (int v : primeMap.get(uValue)) {
            if (pos[v] == -1) {
                continue;
            }
            // 因为是从根节点往下搜索，且pos保存了 当前节点u的 所有祖先节点（当然包括根节点）的编号（值不是-1）
            // 如果depth更大，说明离u更近
            if (closestPrimeAncestor[u] == -1 || depth[closestPrimeAncestor[u]] < depth[pos[v]]) {
                closestPrimeAncestor[u] = pos[v];
            }
        }
        // 保留现场： 节点u的值对应的编号
        int p = pos[uValue];
        pos[uValue] = u;
        for (int child : edgeMap.get(u)) {
            if (child == parent) {
                continue;
            }
            depth[child] = depth[u] + 1;

            // 递归处理子节点
            dfs(nums, child, u);
        }

        // 处理完了所有子节点后，还原现场。 pos中的有效值（不是-1）始终保证是对应单条路径
        pos[uValue] = p;
    }
}
