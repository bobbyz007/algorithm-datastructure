package bz.array;

import java.util.HashMap;

/**
 * 子数组相关：比如连续子数组的最大和等等。
 * 子数组：都是指 数组中一个连续 非空 的元素序列
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

    /**
     * 和为奇数的子数组数目
     *
     * 思路：首先尝试动态规划
     * f(i): 以i 为结尾的且和为奇数的子数组数目
     * 当i为奇数时，统计i-1结尾的和为偶数：f(i) = 1 + i - f(i-1)
     * 当i为偶数时，统计i-1结尾的和为奇数：f(i) = 0 + f(i-1)
     */
    public int findSubArrayCountWithSumOdd(int[] arr) {
        long fi = 0;
        long result = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & 1) == 1) {
                fi = 1 + i - fi;
            }
            result += fi;
        }
        // 答案可能会很大，将结果对 10^9 + 7 取余后返回。
        return (int) (result % ((long) Math.pow(10,9) + 7));
    }

    /**
     * 给两个整数数组 nums1 和 nums2 ，返回 两个数组中 公共的 、长度最长的子数组的长度 。
     *
     * 思路： 朴素的思想： 滑动窗口。
     * nums1 从0,1...开始的子数组 与 nums2比较
     * nums2 从0,1...开始的子数组 与 nums1比较
     *
     * 时间复杂度：O(N+M)*MIN(N,M)
     */
    public int findLongestCommonLength(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int ret = 0;
        for (int i = 0; i < n; i++) {
            int len = Math.min(m, n - i);
            int maxlen = maxLength(nums1, nums2, i, 0, len);
            ret = Math.max(ret, maxlen);
        }
        for (int i = 0; i < m; i++) {
            int len = Math.min(n, m - i);
            int maxlen = maxLength(nums1, nums2, 0, i, len);
            ret = Math.max(ret, maxlen);
        }
        return ret;
    }
    private int maxLength(int[] A, int[] B, int addA, int addB, int len) {
        int ret = 0, k = 0;
        for (int i = 0; i < len; i++) {
            if (A[addA + i] == B[addB + i]) {
                k++;
            } else {
                k = 0;
            }
            ret = Math.max(ret, k);
        }
        return ret;
    }

    /**
     * 给定一个含有n个正整数的数组和一个正整数 target 。
     * 找出该数组中满足其和 ≥ target 的长度最小的 连续子数组{nums[l], nums[l+1], ..., nums[r-1], nums[r]}，并返回其长度。
     *
     * 思路： 滑动窗口： 连续子数组可以联想到可以用滑动思想
     */
    public int findMinSubArrayLenWithTarget(int target, int[] nums) {
        int l = 0, r = 0;
        int sum = 0;
        int result = Integer.MAX_VALUE;
        while (l <= r && r < nums.length) {
            while (sum < target && r < nums.length) {
                sum += nums[r++];
            }
            while (sum >= target && l < nums.length) {
                result = Math.min(result, r - l);
                sum -= nums[l++];
            }
        }
        return result == Integer.MAX_VALUE ? 0 : result;
    }

}
