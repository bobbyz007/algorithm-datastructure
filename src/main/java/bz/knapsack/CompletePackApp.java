package bz.knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 完全背包算法的应用
 */
public class CompletePackApp {
    /**
     * 1449. 数位成本和为目标值的最大数字
     *
     * 给你一个整数数组cost和一个整数target。请你返回满足如下规则可以得到的最大整数：
     *   给当前结果添加一个数位（i + 1）的成本为cost[i]（cost数组下标从 0 开始）。
     *   总成本必须恰好等于target。
     *   添加的数位中没有数字 0 。
     * 如果按照上述要求无法得到任何整数，请你返回 "0" 。
     *
     * 思路： 完全背包的变种。 从数组中重复选择某些元素，满足一定的容量限制并求最大值。
     * 难点： 怎么对应完全背包的最大价值, 最大价值转化为先求最大位数（位数越多，对应整数值肯定越大），在根据转移方程求把过程值找出来。
     */
    public String largestNumber(int[] cost, int target) {
        // 使用前 i 个数位且花费总成本 恰好为 j 时的最大位数
        int[] dp = new int[target + 1];
        // 限制条件不是至少，是恰好
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[0] = 0;
        // 从第一个数位开始遍历
        for (int c : cost) {
            for (int j = c; j <= target; ++j) {
                /**
                 * 1. 若j<cost[i]，无法选择第i个数位，则从前面i-1个数位中选择： dp[i][j]
                 * 2. 若j>=cost[i], 存在两种选择策略，若不选第i个数位：dp[j]; 若选择（因为可以重复选择多次，选择了i依旧可以再次选择，即从前i而不是i-1个数位中选择）：
                 *    dp[i+1][j-cost[i]]+1
                 */
                dp[j] = Math.max(dp[j], dp[j - c] + 1);
            }
        }
        if (dp[target] < 0) {
            return "0";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 8, j = target; i >= 0; i--) {
            /**
             * 根据转移方程，若dp[j] == dp[j - c] + 1，则表示选择了第i个数位（可能多次选择），否则继续往前寻找选择的数位
             * j -= c: 当前数位可以选择多次
             * 比如：target=9
             * 数位：    1   2   3  4  5  6  7  8  9
             * 成本：    4,  3,  2, 5, 6, 7, 2, 5, 5
             * dp：  0 -inf 1   1  2  2  3  3  4  4
             *
             * 分析： 前9个数位：j=9,dp[9]=4, dp[9-5]=2, 考察第9个数位：没有选择，因为不是相差1
             * 前8个数位：j=9,dp[9]=4, dp[9-5]=2, 考察第8个数位：没有选择，因为不是相差1
             * 前7个数位：j=9,dp[9]=4, dp[9-2]=3, 考察第7个数位：选择，相差1；继续考察： dp[9-2-2]=2, dp[9-2-2-2]=1, dp[9-2-2-2-2]=-inf
             *          可以选择3次：也即是从前7个数位中 成本恰好等于9时，第7个数位可以选择3次。
             * 前6个数位：j=9-2-2-2=3
             * ...
             * 前2个数位： j=3, dp[3]=1, dp[3-3]=0,考察第2个数位，选择
             */
            for (int c = cost[i]; j >= c && dp[j] == dp[j - c] + 1; j -= c) {
                sb.append(i + 1);
            }
        }
        return sb.toString();
    }

    /**
     * 322. 零钱兑换
     *
     * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。你可以认为每种硬币的数量是无限的。
     * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回-1 。
     *
     * 思路： 典型完全背包问题，成本就是面额， 价值就是统计总的个数
     */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        int maxCount = amount + 1;
        // 恰好等于amount，不是至少
        Arrays.fill(dp, 1, dp.length, maxCount);

        for (int coin : coins) {
            for (int j = coin; j <= amount; ++j) {
                dp[j] = Math.min(dp[j], dp[j - coin] + 1);
            }
        }
        return dp[amount] == maxCount ? -1 : dp[amount];
    }

    /**
     * 518. 零钱兑换II
     *
     * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
     * 请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
     *
     * 假设每一种面额的硬币有无限个。 题目数据保证结果符合 32 位带符号整数。
     *
     * 思路： 完全背包类问题： 但是是求方案或组合数，需要变通推导公式
     */
    public int coinChangeII(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        dp[0] = 1;
        //因为是组合,先遍历物品再遍历背包
        for(int i = 0; i < coins.length; i++){
            for(int j = coins[i]; j <= amount; j++){
                // 选或不选两种组合
                dp[j] += dp[j - coins[i]];
            }
        }
        return dp[amount];
    }

    /**
     * 279. 完全平方数
     *
     * 给你一个整数 n ，返回 和为 n 的完全平方数的最少数量 。
     * 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
     *
     * 思路： 典型的完全背包问题
     */
    public int numSquares(int n) {
        // 前i个物品装满n时最小代价（个数）
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 1, dp.length, Integer.MAX_VALUE);
        for (int i = 1; i * i <= n; i++) {
            int square = i * i;
            for (int j = square; j <= n; ++j) {
                // 每个物品的价值为个数1
                dp[j] = Math.min(dp[j], dp[j - square] + 1);
            }
        }

        return dp[n];
    }

    /**
     * 377. 组合总和 Ⅳ
     *
     * 给你一个由 不同 整数组成的数组 nums ，和一个目标整数 target 。请你从 nums 中找出并返回总和为 target 的元素组合的个数。
     * 注意：顺序不同的序列被视作不同的组合。
     *
     * 思路： 不同于基于完全背包算法求方案数，此处需要考虑组合的顺序。 因此严格说不是完全背包类问题， 但可以借鉴类似思想。
     */
    public int combinationSum4(int[] nums, int target) {
        /**
         * dp[j]:和为 j 的排列总数（顺序不同是不同的组合）
         *         n−1
         * dp[j] = ∑ dp[j − nums[i]]  要求：target⩾nums[i]
         *         i=0
         *  为什么需要累加：因此要考虑组合的顺序，此处理解为每个nums[i]作为排列的结尾。
         *
         *  举例分析： nums={1,2,3}, target=4
         *  dp[4] = dp[4-1]+dp[4-2]+dp[4-3] = dp[3]+dp[2]+dp[1], 也就是考虑每个元素作为结尾时总和为j的排列数
         *  dp[3] = dp[3-1]+dp[3-2]+dp[3-3] = dp[2]+dp[1]+dp[0]
         *  dp[2] = dp[2-1]+dp[2-2] = dp[1]+dp[0] = 1 + 1 = 2
         *  dp[1] = dp[1-1] = dp[0] = 1
         *
         *  最终dp[4] = 7
          */
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i <= target; ++i) {
            // 考虑每个元素作为结尾时总和为j的排列数，隐含已经考虑了顺序，因此需要在内层遍历nums数组的每个元素。
            for (int num : nums) {
                if (i >= num) {
                    dp[i] += dp[i - num];
                }
            }
        }

        return dp[target];
    }

    public int numRollsToTarget(int n, int k, int target) {
        /**
         * dp[n][j]: 用n个一样的骰子（每个都有k个面），正面朝上的数字之和为j的方案数
         */
        int[][] dp = new int[n + 1][target + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= target; j++) {
                for (int l = 1; l <= k; l++) {
                    if (j > l) {
                        dp[i][j] += dp[i - 1][j - l];
                    }
                }
            }
        }
        return dp[n][target];
    }


}
