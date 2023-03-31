package bz.array;

import java.util.HashMap;

/**
 * 子数组相关：比如连续子数组的最大和等等。
 */
public class SubArray {
    /** 连续子数组的最大和：两种思路代码基本一致。
     *  思路一：找规律。
     *  思路二：动态规划，以f(i)表示以第i个数字结尾的子数组的最大和，则
     * f(i) = max{
     *    dataArr[i] if i==0 or f(i-1)<=0,
     *    f(i-1)+dataArr[i] if i>0 and f(i-1)>0
     * }
     */
    public int findGreatestSumOfSuArray(int[] dataArr) {
        int curSum = 0;
        int greatestSum = Integer.MIN_VALUE;
        for (int i = 0; i < dataArr.length; i++) {
            // 找规律： 如果前面的和为负数，则直接丢弃，以当前数开始计算
            if (curSum <= 0) {
                curSum = dataArr[i];
            } else {
                curSum += dataArr[i];
            }

            if (curSum > greatestSum) {
                greatestSum = curSum;
            }
        }
        return greatestSum;
    }

    /**
     * 输入 递增排序 的数组和数字s，在数组中找出两个数，使得它们的和正好是s。 输出任意一对即可。
     * 类似问题：和为s的连续正数序列，比如和为15的序列：1+2+3+4+5 = 4+5+6 = 7+8
     *
     * 思路：双指针
     */
    public int[] findTwoNumbersWithSum(int[] sortedArr, int s) {
        int left = 0;
        int right = sortedArr.length - 1;

        while (right > left) {
            long curSum = sortedArr[left] + sortedArr[right];
            if (curSum == s) {
                return new int[]{sortedArr[left], sortedArr[right]};
            } else if (curSum > s) {
                right--;
            } else {
                left++;
            }
        }
        return new int[2];
    }

    /**
     * 统计数组中和为 k 的 连续子数组的个数
     *
     * 一般思路：考虑以i结尾和为k的连续子数组个数，我们需要统计符合条件的下标j的个数，其中0<=j<i且[j..i]子数组的和为k。
     * 优化思路： 采用前缀和 + 哈希 来优化一般思路中的遍历j的求和，以空间换时间。
     *
     * @param nums 整数数组
     * @param k 整数
     */
    public int findSubArrayCountWithSum(int[] nums, int k) {
        int count = 0, pre = 0;
        // 前缀和 -> 该前缀和出现的次数
        HashMap <Integer, Integer> mp = new HashMap<>();
        mp.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            // 下标0开始以i结尾的前缀和
            pre += nums[i];
            // 如果i的前面有多个j出现过前缀和为 pre-k，也就是 j1~i 和 j2~i的和都是k，都是满足条件的连续子数组
            // ...j1......i...
            // ......j2...i...
            if (mp.containsKey(pre - k)) {
                count += mp.get(pre - k);
            }
            mp.put(pre, mp.getOrDefault(pre, 0) + 1);
        }
        return count;
    }


}
