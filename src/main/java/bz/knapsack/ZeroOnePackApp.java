package bz.knapsack;

/**
 * 01背包算法的应用
 */
public class ZeroOnePackApp {
    /**
     * 416. 分割等和子集
     * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     *
     * 思路： 转化为0/1背包问题，从nums中找出数字 使得总和不超过 halfSum 的最大和(数字本身既是成本也是价值)
     */
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int halfSum = sum / 2;
        // 转化为0/1背包问题，从nums中找出数字 使得总和不超过 halfSum 的最大和
        // dp[i]: 标识总和不超过i的 最大和
        // 比如[1,2,6,7,12]，dp[11]=10，即不超过11的最大总和为10（1+2+7）
        int[] dp = new int[halfSum + 1];
        for (int num : nums) {
            for (int j = halfSum; j >= num; j--) {
                dp[j] = Math.max(dp[j], dp[j - num] + num);
            }
        }

        // 如果可以分割，则总和不超过 halfSum 的最大和 应该是 halfSum
        return dp[halfSum] == halfSum;
    }

    /**
     * 474. 一和零
     * 给你一个二进制字符串数组 strs 和两个整数 m 和 n 。 请你找出并返回 strs 的最大子集的长度，该子集中 最多 有 m 个 0 和 n 个 1 。
     *
     * 如果 x 的所有元素也是 y 的元素，集合 x 是集合 y 的 子集 。
     * 举例：strs = ["10", "0001", "111001", "1", "0"], m = 5, n = 3 ，最大子集长度是4： {"10","0001","1","0"}
     *
     * 思路： 二维费用的背包问题， 求解子集，实际上就是从一堆物品（01字符串）中挑选，容量限制就是0和1的个数，是二维的。
     */
    public int findMaxForm(String[] strs, int m, int n) {
        // 设 dp[i][v][u] 表示前i件物品付出两种费用(对应分别是0和1的个数)，容量限制分别为 v 和 u 时可获得的最大价值（对应长度）
        int[][] dp = new int[m + 1][n + 1];

        int len = strs.length;
        int[] zeroOneMap = new int[2];
        for (int i = 0; i < len; i++) {
            zeroOneMap[0] = 0;
            for (char c : strs[i].toCharArray()) {
                zeroOneMap[0] += (c == '0' ? 1 : 0);
            }
            zeroOneMap[1] = strs[i].length() - zeroOneMap[0];
            for (int v = m; v >= zeroOneMap[0]; v--) {
                for (int u = n; u >= zeroOneMap[1]; u--) {
                    dp[v][u] = Math.max(dp[v][u], dp[v - zeroOneMap[0]][u - zeroOneMap[1]] + 1);
                }
            }
        }
        return dp[m][n];
    }

    /**
     * 494. 目标和
     * 给你一个整数数组 nums 和一个整数 target 。向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式。
     * 求解：返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
     *
     * 例如有5种方案：nums = [1,1,1,1,1], target = 3
     * -1 + 1 + 1 + 1 + 1 = 3
     * +1 - 1 + 1 + 1 + 1 = 3
     * +1 + 1 - 1 + 1 + 1 = 3
     * +1 + 1 + 1 - 1 + 1 = 3
     * +1 + 1 + 1 + 1 - 1 = 3
     *
     * 思路： 转化为01背包类问题（不是纯粹的01背包）？
     * P: 数组中+前面的整数和， N：数组中-前面的整数和, sum：整个数组和
     * P-N=target
     * sum-N-N=target
     * 推导出 N = (sum-target)/2, 问题转化成在数组 nums 中选取若干元素，使得这些元素之和等于 N，计算选取元素的方案数。
     * 注意：此处不是标准的01背包问题，需变通推导公式。
     */
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int diff = sum - target;
        if (diff < 0 || diff % 2 != 0) {
            return 0;
        }
        // N = (sum-target)/2
        int neg = diff / 2;
        int[] dp = new int[neg + 1];
        dp[0] = 1;
        for (int num : nums) {
            for (int j = neg; j >= num; j--) {
                /**
                 * 此处是求方案数，无法对应到标准01背包的最大价值。 但框架是类似的。
                 * 如果不选择num：元素和不变，对应方案数为从前面i-1个元素中选择使得元素和等于j的方案数
                 * 如果选择num，元素和变为j-num，对应方案数为从前面i-1个元素中选择使得元素和等于j-num的方案数
                 * 总的方案就是前面两种方案相加
                  */
                dp[j] += dp[j - num];
            }
        }
        return dp[neg];
    }

    /**
     * 879. 盈利计划
     * 求解有多少种至少盈利minProfit的计划： 第 i 种工作会产生 profit[i] 的利润，它要求 group[i] 名成员共同参与。如果成员参与了其中一项工作，就不能参与另一项工作。
     *
     * 思路：基于01背包思想：
     * dp[i][j][k] 表示在前 i 个工作中选择了 j 个员工，并且满足工作利润至少为 k 的情况下的盈利计划的总数目。
     *
     * 不选择第i种工作方案数： dp[i-1][j][k]
     * 选择第i种工作方案数： dp[i-1][j-group[i]][max(0,k-profit[i])]
     */
    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
        int[][] dp = new int[n + 1][minProfit + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }
        int len = group.length;
        int MOD = (int)1e9 + 7;
        for (int i = 1; i <= len; i++) {
            int members = group[i - 1];
            int earn = profit[i - 1];
            for (int j = n; j >= members; j--) {
                for (int k = minProfit; k >= 0; k--) {
                    dp[j][k] = (dp[j][k] + dp[j - members][Math.max(0, k - earn)]) % MOD;
                }
            }
        }
        return dp[n][minProfit];
    }

    /**
     * 1049. 最后一块石头的重量 II
     *
     * 有一堆石头，用整数数组stones 表示。其中stones[i] 表示第 i 块石头的重量。
     * 每一回合，从中选出任意两块石头，然后将它们一起粉碎。假设石头的重量分别为x 和y，且x <= y。那么粉碎的可能结果如下：
     *      如果x == y，那么两块石头都会被完全粉碎；
     *      如果x != y，那么重量为x的石头将会完全粉碎，而重量为y的石头新重量为y-x。
     * 最后，最多只会剩下一块 石头。返回此石头 最小的可能重量 。如果没有石头剩下，就返回 0。
     *
     * 思路：用归纳法可以证明，最后一块石头的重量可以表示为(很关键)：
     * n−1
     * ∑ ki * stones[i] 其中ki取值1或-1
     * i=0
     * 求得上述和式的最小非负值 就是要求的最小可能重量。 可以想象把ki等于1的石头分成一堆，等于-1的石头分成一堆。
     * 记石头的总重量为sum，ki等于-1的石头重量为neg，则上述和式可表示为：
     * (sum−neg)−neg = sum−2⋅neg
     *
     * 要使最后一块石头的重量尽可能地小，neg 需要在不超过 ⌊sum/2⌋ 的前提下尽可能地大。因此本问题可以看作是背包容量为 ⌊sum/2⌋  ，物品重量和价值均为stones[i] 的 0-1 背包问题。
     *
     */
    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for (int weight : stones) {
            sum += weight;
        }
        int halfSum = sum / 2;
        // dp[j]: 前i个石头能否凑出重量j
        boolean[] dp = new boolean[halfSum + 1];
        dp[0] = true;
        for (int weight : stones) {
            for (int j = halfSum; j >= weight; --j) {
                // 第i个石头存在选或不选两种情况,只要任一种情况可以凑出重量j,则返回true
                dp[j] = dp[j] || dp[j - weight];
            }
        }
        // j 需要在不超过 ⌊sum/2⌋ 的前提下尽可能地大,倒序遍历
        for (int j = halfSum;; --j) {
            if (dp[j]) {
                // 返回最小的diff值
                return sum - 2 * j;
            }
        }
    }

    /**
     * 1230. 抛掷硬币
     *
     * 有一些不规则的硬币。在这些硬币中，prob[i] 表示第 i 枚硬币正面朝上的概率。
     * 请对每一枚硬币抛掷 一次，然后返回正面朝上的硬币数等于 target 的概率。
     * 示例 1：
     * 输入：prob = [0.4], target = 1
     * 输出：0.40000
     *
     * 思路：dp(i,j) 表示前 i 个硬币，总计 j 个硬币正面朝上的概率
     * 则考虑第i个硬币正面朝上或反面朝上：
     *  dp(i,j) = prob[i-1]*dp(i-1,j-1) + (1-prob[i-1])*dp(i-1,j)
     */
    public double probabilityOfHeads(double[] prob, int m) {
        double[] dp = new double[m + 1];
        // 0个硬币朝上的概率
        dp[0] = 1 - prob[0];
        if (m > 0) {
            dp[1] = prob[0];
        }
        for (int i = 1; i < prob.length; i++) {
            for (int j = m; j >= 0; j--) {
                dp[j] = dp[j] * (1 - prob[i]);
                if (j > 0){
                    dp[j] += dp[j - 1] * prob[i];
                }
            }
        }
        return dp[m];
    }

}
