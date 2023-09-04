package bz.string;

import java.util.Arrays;

/**
 * 寻找最长回文子串
 *
 * Manacher算法，时间复杂度O(n)
 * 思路： 初始化p[i]:是否存在某个最长半径r，使得i-r到 i+r 位置的子串是一个回文子串。
 * Manacher算法就是要优化p[i]，核心思想是从一个已知的回文子串，以这个已知的回文子串的中心得到i的对称位置j，这样就可以从p[j]来优化p[i]
 *
 * ------------        -------------
 *      j                    i
 *   -------------------------------
 *                 c               d
 * 求p[i]: 已知以c为中心，半径d-c的回文子串；j是以c为对称的点，此时以j为中心，p[j]为半径的回文子串，与以i为中心的字符等同，至少在半径d-i内如此。
 */
public class Manacher {
    /**
     * 返回开始和结束位置：左闭右开区间
     * 确保字符串s中不包含特殊边界字符：$ ^ #
     */
    public int[] manacher(String s) {
        // 添加分隔符方便字符串边界字符的处理
        String t = join(s);
        int c = 0;
        int d = 0;
        int[] p = new int[t.length()];

        // 忽略开始字符^
        for (int i = 1; i < t.length() - 1; i++) {
            // 对称下标j
            int j = c - (i - c);
            // 从p[j]来优化 p[i]
            p[i] = Math.max(0, Math.min(d - i, j >= 0 ? p[j] : Integer.MAX_VALUE));
            // 扩展回文子串半径
            while (t.charAt(i + p[i] + 1) == t.charAt(i - p[i] - 1)) {
                p[i]++;
            }

            // 调整中心点
            if (i + p[i] > d) {
                c = i;
                d = i + p[i];
            }
        }

        // 找出最大的回文子串半径
        int[] result = new int[2];
        for (int i = 1; i < t.length() - 1; i++) {
            if (p[i] > result[1]) {
                result[0] = i;
                result[1] = p[i];
            }
        }

        return new int[]{(result[0] - result[1]) / 2, (result[0] + result[1]) / 2};
    }

    /**
     * abc -> ^#a#b#c#$
     */
    private String join(String s) {
        StringBuilder sb = new StringBuilder("^#");
        for (int i = 0; i < s.length() - 1; i++) {
            sb.append(s.charAt(i)).append('#');
        }
        sb.append(s.charAt(s.length() - 1));
        sb.append("#$");
        return sb.toString();
    }
}
