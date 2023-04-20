package bz;

import java.util.ArrayList;
import java.util.List;

/**
 * 位运算，数学运算等等
 */
public class Util {
    public static final int MOD = (int)1e9 + 7;

    public static <T> void swap(T[] arr, int start, int end) {
        if (start == end) {
            return;
        }
        T temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
    }

    public static void swap(int[] arr, int start, int end) {
        if (start == end) {
            return;
        }
        int temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
    }

    // 最小公倍数
    public static long lcm(long x, long y) {
        // 防止溢出
        return x / gcd(x, y) * y;
    }

    // 最大公约数
    public static long gcd(long x, long y) {
        long tmp = x % y;
        return tmp > 0 ? gcd(y, tmp) : y;
    }

    /**
     * 位运算： lowbit技术，用于树状数组
     * 只保留二进制中最低位的1， 不管是正数还是负数
     * 1101(13) -> 0001
     * 0011(3) -> 0001
     * 0110(6) -> 0010
      */
    public static int lowestOneBit(int x) {
        // java自带了实现
        // return Integer.lowestOneBit(x);
        return x & (-x);
    }

    /**
     * 一般用于求1的个数或是否为2的n次方:  最右边的1变为0
     */
    public static int rightestBitOneToZero(int x) {
        return x & (x - 1);
    }

    /**
     * 二进制表示中包含1的子集
     * 比如十进制数字11，二进制表示为1011，对应的包含1的子集为：{1011}, {1010}, {1001},{1000},{0011},{0010},{0001}，即包含3个1或2个1或1个1。
     * 主要用于状态处理，比如实际中二进制的1表示某个东西的选择状态，这样包含1的子集就是罗列所有可能的选择情况。
     */
    public static List<Integer> subSetWithBitOne(int x) {
        List<Integer> result = new ArrayList<>();
        // 每一个数和x相与：只能包含x中的1
        for (int i = x; i != 0; i = (i - 1) & x) {
            // i: 表示x的包含二进制位1的子集
            result.add(i);
        }
        return result;
    }

    // 使用快速幂计算 x^n % mod 的值: 取模考虑溢出，时间复杂度logn
    public static long qPow(long x, long n, long mod) {
        long ret = 1;
        while (n != 0) {
            if ((n & 1) != 0) {
                ret = ret * x % mod;
            }
            x = x * x % mod;
            n >>= 1;
        }
        return ret;
    }

    // 快速幂计算x^n: 不考虑溢出，时间复杂度logn
    public static long qPow(long x, long n) {
        long ret = 1;
        while (n != 0) {
            if ((n & 1) != 0) {
                ret = ret * x;
            }
            x = x * x;
            n >>= 1;
        }
        return ret;
    }

    // 快速乘法a*k：采用倍增的思想
    public static long qMul(long a, long k) {
        long ans = 0;
        while (k > 0) {
            if ((k & 1) == 1) {
                ans += a;
            }
            k >>= 1;
            a += a;
        }
        return ans;
    }
}
