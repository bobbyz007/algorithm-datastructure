package bz.string;

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

}
