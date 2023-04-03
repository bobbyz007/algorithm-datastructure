package bz.knapsack;

import java.util.Arrays;

/**
 * 背包问题：01背包，完全背包，多重背包等等
 */
public class Knapsack {
    /**
     * 有 N 件物品和一个容量为 V 的背包。放入第 i 件物品耗费的费用是 C[i]，得到的价值是 W[i]。求解将哪些物品装入背包可使价值总和最大。
     * 即每种物品只有一件，可以选择放 或 不放。
     *
     * 思路：dp[i][v] :前i件物品放入一个容量为v的背包可以获得的最大价值。
     * dp[i][v] = max{dp[i-1][v], dp[i-1][v-C[i]] + W[i]} 第i件物品可以选择放或不放。
     *
     * @param cost 所占容量或费用数组
     * @param worth 价值数组
     * @param volume 容量V
     */
    public int zeroOnePack(int[] cost, int[] worth, int volume) {
        int[] dp = new int[volume + 1];

        int n = cost.length;
        for (int i = 0; i < n; i++) {
            for (int v = volume; v >= cost[i]; v--) {
                dp[v] = Math.max(dp[v], dp[v - cost[i]] + worth[i]);
            }
        }
        return dp[volume] < 0 ? 0 :dp[volume];
    }
    // 要求容量正好等于volume
    public int zeroOnePackEqual(int[] cost, int[] worth, int volume) {
        int[] dp = new int[volume + 1];
        // 如果要求恰好装满背包，即要求把背包装满。 则初始化时dp[1~volumn]设置位负无穷大-INFINITY，
        // 否则初始化为默认0值
        // 解释：实际和转移方程有关，即没有恰好装满时，dp[v - cost[i]]为负无穷大，Math.max返回的还是原值dp[v]
        Arrays.fill(dp, 1, volume + 1, Integer.MIN_VALUE);

        int n = cost.length;
        for (int i = 0; i < n; i++) {
            for (int v = volume; v >= cost[i]; v--) {
                dp[v] = Math.max(dp[v], dp[v - cost[i]] + worth[i]);
            }
        }
        return dp[volume] < 0 ? 0 :dp[volume];
    }

    /**
     * 有 N 种物品和一个容量为 V 的背包，每种物品都有无限件可用。放入第 i 种物品的费用是 C[i]，价值是 W[i]。
     * 求解：将哪些物品装入背包，可使这些物品的耗费的费用总和不超过背包容量，且价值总和最大。
     *
     * 思路：类似01背包问题，迭代顺序不同，也就是递增迭代，后面v可以复用前面的值，即每种物品都有无限件可用
     */
    public int completePack(int[] cost, int[] worth, int volume) {
        int[] dp = new int[volume + 1];

        int n = cost.length;
        for (int i = 0; i < n; i++) {
            for (int v = cost[i]; v <= volume; v++) {
                dp[v] = Math.max(dp[v], dp[v - cost[i]] + worth[i]);
            }
        }
        return dp[volume] < 0 ? 0 :dp[volume];
    }

    // 要求容量正好等于volume
    public int completePackEqual(int[] cost, int[] worth, int volume) {
        int[] dp = new int[volume + 1];
        // 如果要求恰好装满背包，即要求把背包装满。 则初始化时dp[1~volumn]设置位负无穷大-INFINITY，
        // 否则初始化为默认0值
        // 解释：实际和转移方程有关，即没有恰好装满时，dp[v - cost[i]]为负无穷大，Math.max返回的还是原值dp[v]
        Arrays.fill(dp, 1, volume + 1, Integer.MIN_VALUE);

        int n = cost.length;
        for (int i = 0; i < n; i++) {
            for (int v = cost[i]; v <= volume; v++) {
                dp[v] = Math.max(dp[v], dp[v - cost[i]] + worth[i]);
            }
        }
        return dp[volume] < 0 ? 0 :dp[volume];
    }

    /**
     * 有 N 种物品和一个容量为 V 的背包。第 i 种物品最多有 available[i] 件可用，每件耗费的空间是 C[i]，价值是 W[i]。
     * 求解将哪些物品装入背包可使这些物品的耗费的空间总和不超过背包容量，且价值总和最大。
     *
     * 也属于求解混合三种背包问题： available可以设置1 或 特定数量 或 无穷大
     *
     * 思路：基于二进制位拆分物品
     *
     * @param available 每种物品最多有多少件可用
     */
    public int multiplePack(int[] cost, int[] worth, int[] available, int volume) {
        int[] dp = new int[volume + 1];
        int n = cost.length;
        for (int i = 0; i < n; i++) {
            // 转化为完全背包问题
            if (cost[i] * available[i] >= volume) {
                for (int v = cost[i]; v <= volume; v++) {
                    dp[v] = Math.max(dp[v], dp[v - cost[i]] + worth[i]);
                }
                continue;
            }

            int k = 1;
            int m = available[i];
            // 比如对于m为13，则拆分为：1,2^1,2^2,m-2^k+1,即：1+2+4+6
            // 相当于原来13件物品，现在拆分成了4件物品，对应的cost分别是： 1*cost[i], 2*cost[i], 4*cost[i], 6*cost[i]
            // 这样，相应的价值也要乘以对应的系数。
            // 基于上述分析，就转化为了0/1背包问题，在上面拆分的4件物品里面选 或 不选。
            while (k < m) {
                // 拆分的当前物品cost和worth分别为 k*cost[i], k*worth[i]
                for (int v = volume; v >= k * cost[i]; v--) {
                    dp[v] = Math.max(dp[v], dp[v - k * cost[i]] + k * worth[i]);
                }
                m -= k;
                k *= 2;
            }
            for (int v = volume; v >= m * cost[i]; v--) {
                dp[v] = Math.max(dp[v], dp[v - m * cost[i]] + m * worth[i]);
            }
        }

        return dp[volume];
    }

    /**
     * 求解问题：“每种有若干件的物品能否填满给定容量的背包”，只需考虑填满背包的可行性，不需考虑每件物品的价值时，多重背包问题同样有 O(V*N) 复杂度的算法。
     *
     * 思路： 求填满给定容量背包的可行性，不需考虑物品的价值
     * dp[i][j]: 用前i个物品填满容量为j的背包后，最多还剩下第i个物品的可用个数
     * dp[i][j]=-1: 表示不可行
     *
     * @return 基于每件物品的cost和数量available，填满容量 volume 是否可行
     */
    public boolean multiplePackFeasible(int[] cost, int[] available, int volume) {
        int n = cost.length;
        int[][] dp = new int[n + 1][volume + 1];

        dp[0][0] = 0;
        Arrays.fill(dp[0], 1, volume + 1, -1);

        for (int i = 1; i <= n; i++) {
            // 考虑第i种物品
            for (int j = 0; j <= volume; j++) {
                // 如果前面i-1个物品已经填满了容量j(>=0表示第i-1个物品都还有可用的)，则用前i个物品填充时，第i个物品最大可用数量为 available[i]
                if (dp[i - 1][j] >= 0) {
                    dp[i][j] = available[i - 1];
                } else {
                    // 默认不可行
                    dp[i][j] = -1;
                }
            }

            for (int j = cost[i - 1]; j <= volume; j++) {
                if (dp[i][j - cost[i - 1]] > 0) {
                    // 第i个物品的容量为cost[i-1]，那么填充容量j 比 填充j-cost[i-1] 需多用一个第i个物品，即剩余可用个数少了一个。
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - cost[i - 1]] - 1);
                }
            }
        }

        return dp[n][volume] >= 0;
    }

    /**
     * 二维费用的背包问题：给定两种费用，分别不超过对应容量时的背包的最大价值
     * 对于每件物品，具有两种不同的费用，选择这件物品必须同时付出这两种费用。对于每种费用都有一个可付出的最大值（背包容量）。
     * 问怎样选择物品可以得到最大的价值。
     *
     * 思路：算法类似一维的背包问题，以0/1背包的二维费用为例
     *
     * @param cost 两种费用：cost[i][0], cost[i][1]
     * @param worth 价值： worth[i]
     * @param volume 两种容量限制： volume[0], volume[1]
     * @return 最大价值
     */
    public int zeroOnePackTwoDimension(int[][] cost, int[] worth, int[] volume) {
        // 设 dp[i][v][u] 表示前i件物品付出两种费用，容量限制分别为 v 和 u 时可获得的最大价值
        int[][] dp = new int[volume[0] + 1][volume[1] + 1];

        int n = cost.length;
        for (int i = 0; i < n; i++) {
            for (int v = volume[0]; v >= cost[i][0]; v--) {
                for (int u = volume[1]; u >= cost[i][1]; u--) {
                    dp[v][u] = Math.max(dp[v][u], dp[v - cost[i][0]][u - cost[i][1]] + worth[i]);
                }
            }
        }
        return dp[volume[0]][volume[1]];
    }
}
