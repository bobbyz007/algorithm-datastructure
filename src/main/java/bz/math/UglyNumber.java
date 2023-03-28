package bz.math;

import bz.Util;

import java.util.ArrayList;
import java.util.List;

public class UglyNumber {
    /**
     * 从已有丑数中生成： 2, 3, 5的因子
     */
    public int nthUglyNumber(int n) {
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        int i2 = 0;
        int i3 = 0;
        int i5 = 0;
        // 1 2 3 5 6 10 ...
        while (nums.size() < n) {
            int next2 = nums.get(i2) * 2;
            int next3 = nums.get(i3) * 3;
            int next5 = nums.get(i5) * 5;
            int min = Math.min(next2, Math.min(next3, next5));
            // 如果是乘以因子2得到最小值，则下一个更大的丑数必须是nums数组中后面的丑数乘以因子2
            if (min == next2) {
                i2++;
            }
            if (min == next3) {
                i3++;
            }
            if (min == next5) {
                i5++;
            }
            nums.add(min);
        }
        return nums.get(n - 1);
    }


    /**
     * 给你四个整数：n 、a 、b 、c ，请你设计一个算法来找出第 n 个丑数。
     * 丑数是可以被 a 或 b 或 c 整除的 正整数 。
     *
     * 思路：不同于2，3，5的特例可以用三指针求解， 比如2 13 21，使用指针方式会导致遗漏丑数。
     *
     * 首先第n个丑数的肯定是小于等于 n * min{a,b,c}的。 然后在范围内基于容斥原理使用二分查找来找到第n个丑数。
     */
    int nthUglyNumber(int n, int a, int b, int c) {
        long ab = Util.lcm(a, b);
        long ac = Util.lcm(a, c);
        long bc = Util.lcm(b, c);
        long abc = Util.lcm(ab, c);
        long left = 0, right = n * Math.min(Math.min(a, b), c);

        while (left <= right) {
            long mid = left + ((right - left) >> 1);
            // 小于等于mid范围内 丑数的数量:基于容斥原理
            long num = mid / a + mid / b + mid / c - mid / ab - mid / ac - mid / bc + mid / abc;
            // 正好有n个丑数
            if (num == n) {
                // 还需要进一步判断mid是否为丑数，小于等于mid范围内有n个丑数，不代表mid就是第n个。
                if (mid % a == 0 || mid % b == 0 || mid % c == 0) {
                    return (int)mid;
                } else {
                    right = mid - 1;
                }
            } else if (num > n) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return (int)left;
    }

    /**
     * 超级丑数 是一个正整数，并满足其所有质因数都出现在质数数组 primes 中。
     * 给你一个整数 n 和一个整数数组 primes ，返回第 n 个 超级丑数 。
     *
     * 思路： 注意条件 “所有质因数都出现在质数数组 primes”， 类似于三指针演变为多指针。
     */
    public int nthSuperUglyNumber(int n, int[] primes) {
        int pLen = primes.length;
        int[] indexes = new int[pLen];

        long[] dp = new long[n];
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            // 因为选最小值，先假设一个最大值
            dp[i] = Integer.MAX_VALUE;
            for (int j = 0; j < pLen; j++) {
                dp[i] = Math.min(dp[i], dp[indexes[j]] * primes[j]);
            }

            // dp[i] 是之前的哪个丑数乘以对应的 primes[j] 选出来的，给它加 1
            for (int j = 0; j < pLen; j++) {
                if (dp[i] == dp[indexes[j]] * primes[j]) {
                    // 注意：这里不止执行一次，例如选出 14 的时候，2 和 7 对应的最小丑数下标都要加 1
                    indexes[j]++;
                }
            }
        }
        return (int) dp[n - 1];
    }
}
