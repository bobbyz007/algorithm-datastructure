package bz.string;

import java.util.Arrays;

public class SubString {
    /**
     * 最长不含重复字符的的子字符串，注意：子串是要求连续的。
     * <p>
     * 思路： f(i) 表示以第i个字符结尾的不含重复字符的的子字符串的最长长度。
     * 1. 第i个字符没有出现过，则f(i)=f(i-1)+1
     * 2. 第i个字符之前出现过，距离为d。
     * 如果d<=f(i-1),则之前重复出现的字符在f(i-1)中，更新f(i)为 d
     * 如果d>f(i-1),则之前重复出现的字符在f(i-1)之前，更新f(i)为 f(i-1)+1
     */
    public int longestSubstringWithoutDuplication(String str) {
        int curLength = 0;
        int maxLength = 0;

        int[] position = new int[26];
        Arrays.fill(position, -1);

        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            int prevIndex = position[c - 'a'];
            // 之前重复出现的字符在f(i-1)之前
            if (prevIndex < 0 || i - prevIndex > curLength) {
                ++curLength;
            } else {
                // 更新最大长度
                if (curLength > maxLength) {
                    maxLength = curLength;
                }
                curLength = i - prevIndex;
            }
            position[c - 'a'] = i;
        }
        if (curLength > maxLength) {
            maxLength = curLength;
        }
        return maxLength;
    }
}
