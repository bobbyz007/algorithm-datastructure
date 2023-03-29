package bz;

import java.util.ArrayList;
import java.util.List;

public class Util {
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
     * 一般用于求1的个数或是否为2的n次方
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
        for (int i = x; i != 0; i = (i - 1) & x) {
            // i: 表示x的包含二进制位1的子集
            result.add(i);
        }
        return result;
    }

}
