package bz.search;

import bz.Util;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 二分查找
 */
public class BinarySearch {
    /**
     * 二分模板一： 如果check函数更新的是l，则计算mid：要加1
     * 比如l=0, r=1, 不加1则mid=0，此时l=mid=0， 会陷入死循环。
     */
    Function<Long, Boolean> check = mid -> true;
    public long binarySearch(long l, long r) {
        // long l = 0, r = 1000009;
        while (l < r) {
            long mid = l + r + 1 >> 1;
            if (check.apply(mid)) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }
    /**
     * 二分模板二： 如果check函数更新的是r，则计算mid：不要加1
     * 比如l=0, r=1, 加1则mid=1，此时r=mid=1， 会陷入死循环。
     */
    public long binarySearch2(long l, long r) {
        // long l = 0, r = 1000009;
        while (l < r) {
            long mid = l + r >> 1;
            if (check.apply(mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    /**
     * 29. 两数相除
     * 给定两个整数，被除数 dividend 和除数 divisor。 将两数相除，要求不使用乘法、除法和 mod 运算符。
     * 返回被除数 dividend 除以除数 divisor 得到的商。
     *
     * 思路： 可以先实现一个「倍增乘法」，然后利用对于 x 除以 y ，结果 x / y 必然落在范围 [0, x] 的规律进行二分。
     */
    public int divide(int a, int b) {
        long x = a, y = b;
        boolean isNeg = false;
        if ((x > 0 && y < 0) || (x < 0 && y > 0)) {
            isNeg = true;
        }
        if (x < 0) {
            x = -x;
        }
        if (y < 0) {
            y = -y;
        }
        long l = 0, r = x;
        while (l < r) {
            long mid = l + r + 1 >> 1;
            // 倍增乘法
            if (Util.qMul(mid, y) <= x) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        long ans = isNeg ? -l : l;
        if (ans > Integer.MAX_VALUE || ans < Integer.MIN_VALUE) return Integer.MAX_VALUE;
        return (int)ans;
    }

    /**
     * 33. 搜索旋转排序数组
     * 整数数组 nums 按升序排列，数组中的值 互不相同（注意这个条件）。
     * 在传递给函数之前， nums 在预先未知的某个下标 k （0 <= k < nums.length） 上进行了旋转。
     * 例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
     * 给你旋后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
     *
     * 思路：但凡是从有序序列中找某个数，我们第一反应应该是「二分」。
     */
    public int searchRotate(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        }
        if (n == 1) {
            return nums[0] == target ? 0 : -1;
        }
        // 第一次「二分」：从中间开始找，找到满足 >=nums[0] 的分割点（旋转点）
        // 如果数组中的值 可能相同， 则需要做一些预处理，使得二段性恢复，即将数组尾部与 nums[0]相同的元素忽略掉。
        int l = 0, r = n - 1;
        while (l < r) {
            int mid = l + r + 1 >> 1;
            if (nums[mid] >= nums[0]) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        // 第二次「二分」：通过和 nums[0] 进行比较，得知 target 是在旋转点的左边还是右边
        if (target >= nums[0]) {
            l = 0;
        } else {
            l = l + 1;
            r = n - 1;
        }
        while (l < r) {
            int mid = l + r >> 1;
            if (nums[mid] >= target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return nums[r] == target ? r : -1;
    }

    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。
     * 找出给定目标值在数组中的开始位置和结束位置。
     * 如果数组中不存在目标值 target，返回 [-1, -1]。
     */
    public int[] searchRange(int[] nums, int target) {
        int[] ans = new int[]{-1, -1};
        int n = nums.length;
        if (n == 0) {
            return ans;
        }

        // 开始位置：注意判断条件
        int l = 0, r = n - 1;
        while (l < r) {
            int mid = l + r >> 1;
            if (nums[mid] >= target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        if (nums[l] != target) {
            return ans;
        } else {
            ans[0] = l;
            // 结束位置：注意判断条件
            l = 0; r = n - 1;
            while (l < r) {
                int mid = l + r + 1 >> 1;
                if (nums[mid] <= target) {
                    l = mid;
                } else {
                    r = mid - 1;
                }
            }
            ans[1] = l;
            return ans;
        }
    }
}
