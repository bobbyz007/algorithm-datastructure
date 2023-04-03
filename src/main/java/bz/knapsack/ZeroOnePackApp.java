package bz.knapsack;

/**
 * 01背包算法的应用
 */
public class ZeroOnePackApp {
    /**
     * 416. 分割等和子集
     * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     *
     * 思路： 转化为0/1背包问题，从nums中找出数字 使得总和不超过 halfSum 的最大和
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
     */
    public int findMaxForm(String[] strs, int m, int n) {
        return 0;
    }



}
