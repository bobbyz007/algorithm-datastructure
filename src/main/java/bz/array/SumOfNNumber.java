package bz.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * N个数之和： 基本思路采用排序和双指针
 */
public class SumOfNNumber {
    /**
     * 15. 三数之和
     * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？
     * 请你找出所有和为 0 且不重复的三元组。
     *
     * 思路： 通过枚举 i 确定第一个数，另外两个指针 j ， k 分别从左边 i + 1 和右边n - 1 往中间移动，找到满足 nums[i] + nums[j] + nums[k] == 0 的所有组合
     * j 和 k 指针的移动逻辑，分情况讨论 sum = nums[i] + nums[j] + nums[k] ：
     *   ◦ sum > 0： k 左移，使 sum 变小
     *   ◦ sum < 0： j 右移，使 sum 变大
     *   ◦ sum = 0：找到符合要求的答案，存起来
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        // 枚举i确定第一个数
        for (int i = 0; i < n; i++) {
            // 剔除重复的数字
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int j = i + 1, k = n - 1;
            while (j < k) {
                // 剔除重复的数字
                while (j > i + 1 && j < n && nums[j] == nums[j - 1]) {
                    j++;
                }
                if (j >= k) {
                    break;
                }
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == 0) {
                    ans.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    j++;
                } else if (sum > 0) {
                    k--;
                } else if (sum < 0) {
                    j++;
                }
            }
        }
        return ans;
    }

    /**
     * 16. 最接近的三数之和
     * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。
     * 找出 nums 中的三个整数，使得它们的和与 target 最接近。
     * 返回这三个数的和。
     *
     * 思路：同上个问题是类似思想。
     */
    public int threeSumClosest(int[] nums, int t) {
        Arrays.sort(nums);
        int ans = nums[0] + nums[1] + nums[2];
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int j = i + 1, k = n - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (Math.abs(sum - t) < Math.abs(ans - t)) {
                    ans = sum;
                }
                if (ans == t) {
                    return t;
                } else if (sum > t) {
                    k--;
                } else if (sum < t) {
                    j++;
                }
            }
        }
        return ans;
    }

    /**
     * 18. 四数之和
     * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？
     * 找出所有满足条件且不重复的四元组。
     *
     * 思路： 4个指针
     */
    public List<List<Integer>> fourSum(int[] nums, int t) {
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) { // 确定第一个数
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // 对第一个数进行去重（相同的数只取第
            }
            for (int j = i + 1; j < n; j++) { // 确定第二个数
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue; // 对第二个数进行去重（相同
                }
                // 确定第三个数和第四个数
                int k = j + 1, p = n - 1;
                while (k < p) {
                    // 对第三个数进行去重（相同的数只取第一个）
                    while (k > j + 1 && k < n && nums[k] == nums[k - 1]) {
                        k++;
                    }
                    // 如果 k 跳过相同元素之后的位置超过了 p，本次循环结束
                    if (k >= p) {
                        break;
                    }
                    int sum = nums[i] + nums[j] + nums[k] + nums[p];
                    if (sum == t) {
                        ans.add(Arrays.asList(nums[i], nums[j], nums[k], nums[p]));
                        k++;
                    } else if (sum > t) {
                        p--;
                    } else if (sum < t) {
                        k++;
                    }
                }
            }
        }
        return ans;
    }
}
