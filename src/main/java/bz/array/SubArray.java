package bz.array;

/**
 * 子数组相关：比如连续子数组的最大和等等。
 */
public class SubArray {
    /** 连续子数组的最大和：两种思路代码基本一致。
     *  思路一：找规律。
     *  思路二：动态规划，以f(i)表示以第i个数字结尾的子数组的最大和，则
     * f(i) = max{
     *    dataArr[i] if i==0 or f(i-1)<=0,
     *    f(i-1)+dataArr[i] if i>0 and f(i-1)>0
     * }
     */
    public int findGreatestSumOfSuArray(int[] dataArr) {
        int curSum = 0;
        int greatestSum = Integer.MIN_VALUE;
        for (int i = 0; i < dataArr.length; i++) {
            // 找规律： 如果前面的和为负数，则直接丢弃，以当前数开始计算
            if (curSum <= 0) {
                curSum = dataArr[i];
            } else {
                curSum += dataArr[i];
            }

            if (curSum > greatestSum) {
                greatestSum = curSum;
            }
        }
        return greatestSum;
    }

}
