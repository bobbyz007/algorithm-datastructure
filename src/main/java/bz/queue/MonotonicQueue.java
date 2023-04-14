package bz.queue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 单调队列： 用于保存区间最值
 *
 * 1438. 绝对差不超过限制的最长连续子数子数组
 * 给你一个整数数组 nums ，和一个表示限制的整数 limit，请你返回最长连续子数组的长度，
 * 该子数组中的任意两个元素之间的绝对差必须小于或者等于 limit 。如果不存在满足条件的子数组，则返回 0 。
 *
 * 思路： 双指针 + 单调队列
 * 始终让右端点 r 右移，当不满足条件时让 l 进行右移。同时，还是使用「单调队列」保存我们的区间最值。
 */
public class MonotonicQueue {
    public int longestSubarray(int[] nums, int limit) {
        int n = nums.length;
        int ans = 0;
        Deque<Integer> max = new ArrayDeque<>(), min = new ArrayDeque<>();

        for (int r = 0, l = 0; r < n; r++) {
            // max队列 存储比当前元素大的，也就是队头就是区间的最大值
            while (!max.isEmpty() && nums[r] >= nums[max.peekLast()]){
                max.pollLast();
            }
            // min队列 存储比当前元素小的，也就是队头就是区间的最小值
            while (!min.isEmpty() && nums[r] <= nums[min.peekLast()]){
                min.pollLast();
            }
            max.addLast(r);
            min.addLast(r);

            // 如果区间绝对差超过限制
            while (Math.abs(nums[max.peekFirst()] - nums[min.peekFirst()]) > limit) {
                l++;
                // 左边收缩，max和min队列也要相应更新，以匹配区间范围。
                if (max.peekFirst() < l) {
                    max.pollFirst();
                }
                if (min.peekFirst() < l) {
                    min.pollFirst();
                }
            }
            ans = Math.max(ans, r - l + 1);
        }
        return ans;
    }
}
