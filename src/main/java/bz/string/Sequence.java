package bz.string;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * 序列问题：跟子串不同，子序列不要求是连续。
 */
public class Sequence {
    /**
     * 计算两个字符串之间的编辑距离（列文斯登距离）
     * 增，删，改字符的最少次数，使得s变成t
     */
    public int levenshtein(String s, String t) {
        // dp[i][j]: s中长度为i的前缀 与 t中长度为j的前缀 的距离
        int[][] dp = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i <= s.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= t.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {
                dp[i][j] = IntStream.of(
                        // 在 字符串s的前i-1个字符 与 字符串t的前j个字符 完全相同的基础上, 进行一次删除操作
                        dp[i - 1][j] + 1,
                        // 在 字符串s的前i个字符 与 字符串t的前j-1个字符 完全相同的基础上, 进行一次插入操作
                        dp[i][j - 1] + 1,
                        // 在 字符串s的前i-1个字符 与 字符串t的前j-1个字符 完全相同的基础上, 进行一次替换操作
                        dp[i - 1][j - 1] + (s.charAt(i - 1) == t.charAt(j - 1) ? 0 : 1)
                ).min().getAsInt();
            }
        }
        return dp[s.length()][t.length()];
    }

    /**
     * 最长公共子序列 longest common subsequence
     */
    public String lcs(String s, String t) {
        int[][] dp = new int[s.length() + 1][t.length() + 1];
        for (int i = 1; i < s.length() + 1; i++) {
            for (int j = 1; j < t.length() + 1; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        int i = s.length();
        int j = t.length();
        StringBuilder sb = new StringBuilder();
        while (dp[i][j] > 0) {
            if (dp[i][j] == dp[i - 1][j]) {
                --i;
            } else if (dp[i][j] == dp[i][j - 1]) {
                --j;
            } else {
                --i;
                --j;
                sb.append(s.charAt(i));
            }
        }

        return sb.reverse().toString();
    }

    /**
     * 严格升序最长子序列 longest increasing subsequence
     */
    public int lis(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }

        // tail 数组的定义：长度为 i + 1 的上升子序列的末尾最小是几
        int[] tail = new int[nums.length];
        // 遍历第 1 个数，直接放在有序数组 tail 的开头
        tail[0] = nums[0];
        // end 表示有序数组 tail 的最后一个已经赋值元素的索引
        int end = 0;

        for (int i = 1; i < nums.length; i++) {
            // 【逻辑 1】比 tail 数组实际有效的末尾的那个元素还大
            if (nums[i] > tail[end]) {
                // 直接添加在那个元素的后面，所以 end 先加 1
                end++;
                tail[end] = nums[i];
            } else {
                int left = bisectLeft(tail, end, nums[i]);
                // 走到这里是left就是第 1 个大于等于 nums[i] 的元素，也就是 tail[left-1] < nums[i] <= tail[left]
                tail[left] = nums[i];
            }
        }
        // 此时 end 是有序数组 tail 最后一个元素的索引
        // 题目要求返回的是长度，因此 +1 后返回。 如果要求返回序列，打印tail数组即可。
        end++;
        return end;
    }

    /**
     * 非降序最长子序列 longest increasing subsequence
     */
    public int lisNonDesc(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }

        // tail 数组的定义：长度为 i + 1 的上升子序列的末尾最小是几
        int[] tail = new int[nums.length];
        // 遍历第 1 个数，直接放在有序数组 tail 的开头
        tail[0] = nums[0];
        // end 表示有序数组 tail 的最后一个已经赋值元素的索引
        int end = 0;

        for (int i = 1; i < nums.length; i++) {
            // 【逻辑 1】比 tail 数组实际有效的末尾的那个元素还大或相等
            if (nums[i] >= tail[end]) {
                // 直接添加在那个元素的后面，所以 end 先加 1
                end++;
                tail[end] = nums[i];
            } else {
                int left = bisectRight(tail, end, nums[i]);
                // 走到这里是left就是第 1 个大于等于 nums[i] 的元素，也就是 tail[left-1] < nums[i] <= tail[left]
                tail[left] = nums[i];
            }
        }
        // 此时 end 是有序数组 tail 最后一个元素的索引
        // 题目要求返回的是长度，因此 +1 后返回。 如果要求返回序列，打印tail数组即可。
        end++;
        return end;
    }

    /**
     * tail[left - 1] < x <= tail[left]
     */
    private int bisectLeft(int[] tail, int end, int x) {
        // 使用二分查找法，在有序数组 tail 中 找到第 1 个大于等于 nums[i] 的元素，尝试让tail中那个元素更小
        int left = 0;
        int right = end;
        while (left < right) {
            // 选左中位数不是偶然，而是有原因的
            // int mid = left + (right - left) / 2;
            int mid = left + ((right - left) >>> 1);
            if (tail[mid] < x) {
                // 中位数肯定不是要找的数，把它写在分支的前面
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    /**
     * tail[left - 1] <= x < tail[left]
     */
    private int bisectRight(int[] tail, int end, int x) {
        // 使用二分查找法，在有序数组 tail 中 找到第 1 个大于等于 nums[i] 的元素，尝试让tail中那个元素更小
        int left = 0;
        int right = end;
        while (left < right) {
            // 选左中位数不是偶然，而是有原因的
            // int mid = left + (right - left) / 2;
            int mid = left + ((right - left) >>> 1);
            if (x < tail[mid]) {
                // 中位数肯定不是要找的数，把它写在分支的前面
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

}
