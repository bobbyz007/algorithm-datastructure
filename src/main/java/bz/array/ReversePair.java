package bz.array;

/**
 * 逆序对
 * 思路：基于归并排序  或  树状数组
 */
public class ReversePair {
    /**
     * 基于归并排序算法求逆序对数量
     */
    public int reversePairs(int[] nums) {
        int[] temp = new int[nums.length];
        // 返回后，nums已排好序
        return reversePairs(nums, 0, nums.length - 1, temp);
    }

    /**
     * 基于树状数组求解逆序对
     */
    public int reversePairsWithBIT(int[] arr) {
        BITArray bitArray = new BITArray(arr.length);

        int[] discritizedArr = bitArray.discretize(arr);
        int result = 0;
        for (int i = 1; i <= arr.length; i++) {
            // discritizedArr[i]：新数组的索引位置
            bitArray.update(discritizedArr[i], 1);

            // bitArray.getsum(discritizedArr[i]): 排在i或i的前面，小于等于位置i的数 的个数。
            result += (i - bitArray.getsum(discritizedArr[i]));
        }
        return result;
    }

    private int reversePairs(int[] nums, int left, int right, int[] temp) {
        if (left == right) {
            return 0;
        }

        int mid = left + (right - left) / 2;
        int leftPairs = reversePairs(nums, left, mid, temp);
        int rightPairs = reversePairs(nums, mid + 1, right, temp);

        if (nums[mid] <= nums[mid + 1]) {
            return leftPairs + rightPairs;
        }

        int crossPairs = mergeAndCount(nums, left, mid, right, temp);
        return leftPairs + rightPairs + crossPairs;
    }

    private int mergeAndCount(int[] nums, int left, int mid, int right, int[] temp) {
        // nums中的left~mid，mid~right都分别已经排好序，待合并
        for (int i = left; i <= right; i++) {
            temp[i] = nums[i];
        }

        int i = left;
        int j = mid + 1;

        int count = 0;
        for (int k = left; k <= right; k++) {
            if (i == mid + 1) {
                nums[k] = temp[j];
                j++;
            } else if (j == right + 1) {
                nums[k] = temp[i];
                i++;
            } else if (temp[i] <= temp[j]) {
                nums[k] = temp[i];
                i++;
            } else {
                nums[k] = temp[j];
                j++;
                // temp[i]>temp[j]，则位于i后面的数也大于temp[j]，构成逆序对
                count += (mid - i + 1);
            }
        }

        // 至此nums中 left~right 已排好序
        return count;
    }
}
