package bz.search;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

/**
 * 搜索应用： DFS记忆化搜索，线性DP, BFS
 *
 * <pre> 403. 青蛙过河
 * 一只青蛙想要过河。 假定河流被等分为若干个单元格，并且在每一个单元格内都有可能放有一块石子（也有可能没有）。 青蛙可以跳上石子，但是不可以跳入水中。
 *
 * 给你石子的位置列表 stones（用单元格序号 升序 表示），请判定青蛙能否成功过河（即能否在最后一步跳至最后一块石子上）。开始时，青蛙默认已站在第一块石子上，
 * 并可以假定它第一步只能跳跃 1 个单位（即只能从单元格 1 跳至单元格 2 ）。
 *
 * 如果青蛙上一步跳跃了k个单位，那么它接下来的跳跃距离只能选择为k - 1、k或k + 1 个单位。另请注意，青蛙只能向前方（终点的方向）跳跃。
 */
public class FrogCrossRiver {
    // <石头位置， 石头索引号>
    Map<Integer, Integer> stoneMap = new HashMap<>();

    // 记忆化搜索结果存储
    // int[][] cache = new int[2009][2009];
    Map<String, Boolean> cache = new HashMap<>();

    /**
     * 通常设计 DFS 函数时，我们只需要不失一般性的考虑完成第 i 块石子的跳跃需要些什么信息即可：
     *   • 需要知道当前所在位置在哪，也就是需要知道当前石子所在列表中的下标 u 。
     *   • 需要知道当前所在位置是经过多少步而来的，也就是需要知道上一步的跳跃步长 k。
     *
     * 在考虑加入记忆化时，只需要将DFS方法中的可变参数作为维度，dfs方法中的返回值作为缓存值即可。
     */
    public boolean canCross(int[] stones) {
        int n = stones.length;
        for (int i = 0; i < n; i++) {
            stoneMap.put(stones[i], i);
        }
        // check first step，第一步只能跳跃一个单位
        if (!stoneMap.containsKey(1)) {
            return false;
        }
        return dfs(stones, stones.length, 1, 1);
    }

    /**
     * @param stones 石子列表【不变】
     * @param n 石子列表长度【不变】
     * @param u 当前所在的石子的下标
     * @param k 上一次是经过多少步跳到当前位置的
     */
    boolean dfs(int[] stones, int n, int u, int k) {
        String key = u + "_" + k;
        // if (cache[u][k] != 0) return cache[u][k] == 1;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        if (u == n - 1) {
            return true;
        }
        for (int i = -1; i <= 1; i++) {
            if (k + i == 0) {
                continue;
            }
            int next = stones[u] + k + i;
            if (stoneMap.containsKey(next)) {
                boolean cur = dfs(stones, n, stoneMap.get(next), k + i);
                // cache[u][k] = cur ? 1 : -1;
                cache.put(key, cur);
                if (cur) {
                    return true;
                }
            }
        }
        // cache[u][k] = -1;
        cache.put(key, false);
        return false;
    }

    /**
     * 从记忆化搜索dfs函数的可变参数 转变为dp的维度
     * dp[i][j]: 当前在第i个位置，并且是以步长k跳到位置i时，是否到达最后一块石子。
     */
    public boolean canCrossWithDp(int[] stones) {
        int n = stones.length;
        // check first step
        if (stones[1] != 1) {
            return false;
        }
        boolean[][] dp = new boolean[n + 1][n + 1];
        // 初始条件
        dp[1][1] = true;
        for (int i = 2; i < n; i++) {
            for (int j = 1; j < i; j++) {
                int k = stones[i] - stones[j];
                // 我们知道从位置 j 到位置 i 是需要步长为 k 的跳跃
                // 而从位置 j 发起的跳跃最多不超过 j + 1
                // 因为每次跳跃，下标至少增加 1，而步长最多增加 1
                if (k <= j + 1) {
                    dp[i][k] = dp[j][k - 1] || dp[j][k] || dp[j][k + 1];
                }
            }
        }
        for (int i = 1; i < n; i++) {
            if (dp[n - 1][i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * 类似记忆化搜索，此处是带标记的BFS,使用visit数组比较重复计算的结果。
     */
    public boolean canCrossWithBfs(int[] stones) {
        int n = stones.length;
        for (int i = 0; i < n; i++) {
            stoneMap.put(stones[i], i);
        }
        // check first step
        if (!stoneMap.containsKey(1)) {
            return false;
        }

        // 类似于记忆化搜索，存储已经计算过的值
        boolean[][] visit = new boolean[n][n];
        Deque<int[]> deque = new ArrayDeque<>();

        visit[1][1] = true;
        deque.addLast(new int[]{1, 1});

        while (!deque.isEmpty()) {
            int[] poll = deque.pollFirst();
            int idx = poll[0], k = poll[1];
            if (idx == n - 1) {
                return true;
            }
            for (int i = -1; i <= 1; i++) {
                if (k + i == 0) {
                    continue;
                }
                int next = stones[idx] + k + i;
                if (stoneMap.containsKey(next)) {
                    int nIdx = stoneMap.get(next), nK = k + i;
                    if (nIdx == n - 1) {
                        return true;
                    }
                    if (!visit[nIdx][nK]) {
                        visit[nIdx][nK] = true;
                        deque.addLast(new int[]{nIdx, nK});
                    }
                }
            }
        }
        return false;
    }
}
