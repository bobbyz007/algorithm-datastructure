package bz.state;

import java.util.*;

/**
 * 90. 子集 II
 * 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集）。
 * 解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。
 * 输入：nums = [1,2,2]
 * 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
 *
 * 思路： 状态压缩： 使用一个 int 的后 10 位来代表每位数组成员是否被选择。
 * 同样，我们也需要先对原数组进行排序，再配合 Set 来进行去重。
 */
public class SubSet {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        Set<List<Integer>> ans = new HashSet<>();
        List<Integer> cur = new ArrayList<>();
        // 枚举 i 代表，枚举所有的选择方案状态[0, 2^n-1]
        // 例如 [1,2]，我们有 []、[1]、[2]、[1,2] 几种方案，分别对应了 00、10、01、11 几种状态
        for (int i = 0; i < (1 << n); i++) {
            cur.clear();
            // 对当前状态进行诸位检查，如果当前状态为 1 代表被选择，加入当前方案中
            for (int j = 0; j < n; j++) {
                int t = (i >> j) & 1;
                if (t == 1) {
                    cur.add(nums[j]);
                }
            }
            // 将当前方案中加入结果集，并去重
            ans.add(new ArrayList<>(cur));
        }
        return new ArrayList<>(ans);
    }
}
