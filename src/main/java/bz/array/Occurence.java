package bz.array;

import bz.Util;

import java.util.Random;
import java.util.random.RandomGenerator;

public class Occurence {
    Random random = new Random();
    /**
     * 出现次数超过数组长度一半的数字：假设数组中肯定存在这种数字
     * 思路：基于快速排序，超过数组长度一半，那么这个数字肯定出现在中位数位置。
     */
    public int moreThanHalfNum(int[] numbers) {
        int middle = numbers.length >> 1;
        int start = 0;
        int end = numbers.length - 1;
        // 以index位置分成两半：小于index和大于等于index
        int index = partition(numbers, start, end);
        while (index != middle) {
            if (index > middle) {
                end = index - 1;
                index = partition(numbers, start, end);
            } else {
                start = index + 1;
                index = partition(numbers, start, end);
            }
        }
        return numbers[middle];
    }

    /**
     * solution 2: 出现次数超过数组长度的一半，说明它出现的次数比其他所有数字出现的次数之和还要多。
     * 思路：保存数字出现的次数
     */
    public int moreThanHalfNum2(int[] numbers) {
        int result = numbers[0];
        int times = 1;
        for (int i = 1; i < numbers.length; i++) {
            if (times == 0) {
                result = numbers[i];
                times = 1;
            } else if (numbers[i] == result) {
                times++;
            } else {
                times--;
            }
        }
        // 要找的数字肯定是最后一次把次数设置为1时对应的数字
        return result;
    }

    private int partition(int[] arr, int start, int end) {
        int index = randomInRange(start, end);
        // 选择的数放在数组末尾
        Util.swap(arr, index, end);

        // <=small位置：小于选择的数； >small位置: 大于选择的数
        int small = start - 1;
        for (int i = start; i < end; i++) {
            if (arr[i] < arr[end]) {
                ++small;
                // ++small后，如果与i不相等，意味着自增后的small位置大于选择的数，需要与i交换位置。
                // 交换位置后，small位置又是小于选择的数
                if (small != i) {
                    Util.swap(arr, i, small);
                }
            }
        }

        ++small;
        // 把放在end位置选择的数交换回来
        Util.swap(arr, small, end);

        return small;
    }

    private int randomInRange(int start, int end)
    {
        return start + random.nextInt(end - start + 1);
    }
}
