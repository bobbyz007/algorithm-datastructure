package bz.array;

import bz.Util;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class Occurence {
    Random random = new Random();

    /**
     *  一个整型数组里除了1个数字只出现一次之外，其他数字都出现了三次。 找出这个只出现一次的数字。
     *
     *  思路： 时间空间复杂度：o(n), o(1)
     *  基于XOR没办法解决出现三次。 还是沿用位运算思路。
     *
     *  出现三次的数字的二进制表示的每一位分别累加，其和能被3整除。 只出现一次的数字的二进制对应位就是 对3取余。
     */
    public int findNumberAppearOnlyOnce(int[] numbers) {
        // bitSum[0] -> 高位
        int[] bitSum = new int[32];
        for (int number : numbers) {
            int bitMask = 1;
            for (int j = 31; j >= 0; j--) {
                int bit = number & bitMask;
                if (bit != 0) {
                    bitSum[j]++;
                }
                bitMask <<= 1;
            }
        }

        int result = 0;
        for (int i = 0; i < 32; i++) {
            result <<= 1;
            result += (bitSum[i] % 3);
        }
        return result;
    }

    /**
     *  一个整型数组里除了2个数字之外，其他数字都出现了两次。 找出这2个只出现一次的数字。
     *
     *  思路： 时间空间复杂度：o(n), o(1)
     *  基于XOR异或预算，相同的数XOR结果为0，但要求有2个只出现一次的数字， 需考虑把数组分成两个子数组。
     *
     *  怎么分？： 首先所有数据XOR，得到结果中的某一bit肯定不为0，再以这个bit的值0或1分成两个组。 由于XOR结果为0，意味着两个bit一个为0，一个为1
     *  这样就保证两个子数组各自只包含1个出现一次的数字
     */
    public int[] findTwoNumbersAppearOnlyOnce(int[] numbers) {
        int allXor = 0;
        for (int number : numbers) {
            allXor ^= number;
        }

        int[] result = new int[2];
        int oneBit = Util.lowestOneBit(allXor);
        for (int number : numbers) {
            if ((number & oneBit) == 0) {
                result[0] ^= number;
            } else {
                result[1] ^= number;
            }
        }

        return result;
    }

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

    /**
     * 最小的k个数
     * 思路：基于快排算法， 缺点：需要修改numbers数组
     */
    public int[] getLeastSmallNumbers(int[] numbers, int k) {
        int start = 0;
        int end = numbers.length - 1;
        int index = partition(numbers, start, end);
        while (index != k - 1) {
            if (index > k - 1) {
                end = index - 1;
                index = partition(numbers, start, end);
            } else {
                start = index + 1;
                index = partition(numbers, start, end);
            }
        }

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = numbers[i];
        }
        return result;
    }

    /**
     * 最小的k个数
     * 思路：基于堆排序算法，java已有实现 PriorityQueue
     */
    public int[] getLeastSmallNumbers2(int[] numbers, int k) {
        // 默认是小堆，自定义创建大堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        // 建立大堆
        for (int i = 0; i < numbers.length; i++) {
            if(maxHeap.size() < k){
                // 优先级队列会自动调整为大堆
                maxHeap.offer(numbers[i]);
            } else {
                int top = maxHeap.peek();
                if (top > numbers[i]){
                    maxHeap.poll();
                    maxHeap.offer(numbers[i]);
                }
            }
        }

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll();
        }
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
