package bz.array;

import bz.Util;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 暂不考虑二维数组。
 *
 * 树状数组是一个查询和修改复杂度都为log(n)的数据结构
 * binary indexed tree: BIT array
 * <p>
 * - C[1] = A[1];
 * - C[2] = A[1] + A[2];
 * - C[3] = A[3];
 * - C[4] = A[1] + A[2] + A[3] + A[4];
 * - C[5] = A[5];
 * - C[6] = A[5] + A[6];
 * - C[7] = A[7];
 * - C[8] = A[1] + A[2] + A[3] + A[4] + A[5] + A[6] + A[7] + A[8];
 *  C[i] = A[i - 2^k+1] + A[i - 2^k+2] + ... + A[i];  //k为i的二进制中从最低位到高位连续零的长度
 * 简单理解即是 A[i] 往前的连续 2^k 个数的和。</p>
 */
public class BITArray {
    // 树状数组, 为方便处理，索引位置从1开始
    private int[] c;
    private int len;

    // only for range update and range query
    private int[] c2;

    public BITArray(int len) {
        this.len = len;
        // 索引从1开始
        this.c = new int[len + 1];
    }

    public BITArray(int len, boolean enableRangeUpdateRangeQuery) {
        this(len);
        if (enableRangeUpdateRangeQuery) {
            this.c2 = new int[len + 1];
        }
    }

    /**
     * 单点更新，更新所有受影响的c[i]
     *
     * @param pos 单点增量更新数组 a 的位置，从1开始
     * @param delta 更新的增量值
     */
    public void update(int pos, int delta){
        for(int i = pos; i <= len; i += Util.lowbit(i)) {
            c[i] += delta;
        }
    }

    /**
     * 区间求和：求原始数组的 1~pos的值
     * @param pos
     */
    public int getsum(int pos) {
        int ans = 0;
        for (int i = pos; i > 0; i -= Util.lowbit(i)) {
            ans += c[i];
        }
        return ans;
    }

    /**
     * <p>RS: 区间修改，单点查询</p>
     *
     * 原数组a[i]，差分数组d[i] = a[i]-a[i-1]（数组下标从1开始，置a[0]=0），则
     * a[i] 等于 d[i]求和。
     *
     * 给区间[l, r]增加delta时，a[l]与前一个元素的差增加了delta，a[r+1]与前一个元素的差减少了delta。
     *
     * 注意：由于原数组是差分数组，在区间修改单点查询时不要单独调用update函数，因为修改的是差分数组，不是原数组，查询时结果难料。
     * 可通过指定 l==r 来达成单个修改的目的。
     *
     * @param l 范围开始位置 inclusive
     * @param r 范围结束位置 inclusive
     * @param delta
     */
    public void rangeAddForRS(int l, int r, int delta) {
        update(l, delta); // d[l]增加了delta
        update(r + 1, -delta); // d[r+1]减少了delta
    }

    /**
     * 对差分数组求和，即得到原数组的值
     */
    public int singleQuery(int pos) {
        return getsum(pos);
    }

    /**
     * <p>RR: 区间修改 区间查询</p>
     *
     * 维护两个差分数组的前缀和： d[i], i*d[i]
     */
    public void rangeAddForRR(int l, int r, int delta) {
        updateForRR(l, delta); // d[l]增加了delta
        updateForRR(r + 1, -delta); // d[r+1]减少了delta
    }
    private void updateForRR(int pos, int delta){
        for(int i = pos; i <= len; i += Util.lowbit(i)) {
            c[i] += delta;
            c2[i] += pos * delta;
        }
    }

    // [pos1, pos2]
    public int rangeQuery(int pos1, int pos2) {
        return queryForRR(pos2) - queryForRR(pos1 - 1);
    }

    // [1, pos]
    private int queryForRR(int pos) {
        int ans = 0;
        for (int i = pos; i > 0; i -= Util.lowbit(i)) {
            ans += (pos + 1) * c[i] - c2[i];
        }
        return ans;
    }

    /**
     * 离散化数组
     * 比如 {50, 4, 2, 800000, - 2, 3, - 2} 处理为 {5, 4, 2, 6, 1, 3, 1}
     * @param arr 可能包含负数，或任何值
     */
    public int[] discretize(int[] arr) {
        Item[] items = new Item[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            items[i] = new Item(arr[i], i + 1);
        }

        //  排序是为了处理重复的数据
        Arrays.sort(items, Comparator.comparingInt(item -> item.value));

        // 返回结果以下标1开始，方便树状数组处理
        int[] result = new int[arr.length + 1];
        int cnt = 1;
        for (int i = 0; i < arr.length; ++i) {
            // 相同的数据有相同的离散化后的值
            if (i != 0 && items[i].value != items[i - 1].value) {
                cnt++;
            }
            result[items[i].pos] = cnt;
        }
        return result;
    }

    static class Item{
        public int value;
        public int pos;
        Item(int value, int pos) {
            this.value = value;
            this.pos = pos;
        }
    }

}
