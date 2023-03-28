package bz;

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

}
