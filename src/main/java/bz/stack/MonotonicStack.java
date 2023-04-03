package bz.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 单调栈的应用
 */
public class MonotonicStack {
    /**
     * 给你一个整数数组 nums 。nums 中，子数组的 范围 是子数组中最大元素和最小元素的差值。
     * 返回 nums 中 所有 子数组范围的 和 。
     *
     * 子数组是数组中一个连续 非空 的元素序列。
     *
     * 思路： <p>根据范围和的定义，可以推出范围和 sum 等于所有子数组的最大值之和 sumMax 减去所有子数组的最小值之和 sumMin。
     * 比如： 如下以i为最小值的子数组 不会与 以j为最小值的子数组相同。 反证法：如果相同，则一个子数组中有两个 逻辑 最小值，产生了矛盾。
     * 0 1 2 3...i...j...k...
     *
     *
     * <p>假设 nums[i] 左侧最近的比它小的数为 nums[j]，右侧最近的比它小的数为 nums[k]，那么所有以
     * nums[i] 为最小值的子数组数目为 (k−i)×(i−j)。
     * 为了能获得 nums[i] 左侧和右侧最近的比它小的数的下标，我们可以使用单调递增栈分别预处理出数组 minLeft 和 minRight，
     * 其中 minLeft[i] 表示 nums[i] 左侧最近的比它小的数的下标，minRight[i] 表示 nums[i] 右侧最近的比它小的数的下标。
     *
     * <p>为了使子数组的最小值或最大值唯一，
     * 我们定义如果 nums[i]=nums[j]，那么 nums[i] 与 nums[j] 的逻辑大小由下标 i 与下标 j 的逻辑大小决定，
     * 即如果 i<j，那么 nums[i] 逻辑上小于 nums[j]。
     */
    public long subArrayRangeSum(int[] nums) {
        int n = nums.length;
        int[] minLeft = new int[n];
        int[] minRight = new int[n];
        int[] maxLeft = new int[n];
        int[] maxRight = new int[n];
        Deque<Integer> minStack = new ArrayDeque<>();
        Deque<Integer> maxStack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            // 判断条件不能加==：因为minStack.peek()<i，如果添加条件nums[minStack.peek()] == nums[i]，实际是逻辑小于，与实际判定条件大于矛盾
            while (!minStack.isEmpty() && nums[minStack.peek()] > nums[i]) {
                minStack.pop();
            }
            minLeft[i] = minStack.isEmpty() ? -1 : minStack.peek();
            minStack.push(i);

            // 如果 nums[maxStack.peek()] == nums[i], 那么根据定义，
            // nums[maxStack.peek()] 逻辑上小于 nums[i]，因为 maxStack.peek() < i，此时的==实际是逻辑小于
            while (!maxStack.isEmpty() && nums[maxStack.peek()] <= nums[i]) {
                maxStack.pop();
            }
            maxLeft[i] = maxStack.isEmpty() ? -1 : maxStack.peek();
            maxStack.push(i);
        }
        minStack.clear();
        maxStack.clear();
        for (int i = n - 1; i >= 0; i--) {
            // 如果 nums[minStack.peek()] == nums[i], 那么根据定义，
            // nums[minStack.peek()] 逻辑上大于 nums[i]，因为 minStack.peek() > i
            while (!minStack.isEmpty() && nums[minStack.peek()] >= nums[i]) {
                minStack.pop();
            }
            minRight[i] = minStack.isEmpty() ? n : minStack.peek();
            minStack.push(i);

            // 需要的判定条件是小于。如果加了等号，会导致逻辑大于的情况，与需要的判定条件矛盾。
            while (!maxStack.isEmpty() && nums[maxStack.peek()] < nums[i]) {
                maxStack.pop();
            }
            maxRight[i] = maxStack.isEmpty() ? n : maxStack.peek();
            maxStack.push(i);
        }

        long sumMax = 0, sumMin = 0;
        for (int i = 0; i < n; i++) {
            sumMax += (long) (maxRight[i] - i) * (i - maxLeft[i]) * nums[i];
            sumMin += (long) (minRight[i] - i) * (i - minLeft[i]) * nums[i];
        }
        return sumMax - sumMin;
    }
}
