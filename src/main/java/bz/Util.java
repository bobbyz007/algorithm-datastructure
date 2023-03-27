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
}
