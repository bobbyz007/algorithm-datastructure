package bz.string;

/**
 * KMP算法
 */
public class Kmp {
    /**
     * 截止到位置i的字符串，其前缀（不包括自身）和后缀的最大长度。
     * 比如截止到位置4，其前缀和后缀相同的最大长度是2， 即ab
     *
     * a b c a b d
     * 0 0 0 1 2 0
     *
     * a b a b a b a c d
     * 0 0 1 2 3 4 5 0 0
     */
    public int[] next(char[] arr) {
        int k = 0; // 最大前后缀长度
        int[] next = new int[arr.length];

        next[0] = 0; //模版字符串的第一个字符的最大前后缀长度为0
        for (int q = 1; q < arr.length; ++q) { //for循环，从第二个字符开始，依次计算每一个字符对应的next值
            while(k > 0 && arr[q] != arr[k]) { //递归的求出arr[0]···arr[q]的最大的相同的前后缀长度k
                // 比如 abcab 在索引4的位置，最大的相同前缀和后缀是ab
                k = next[k - 1]; // 因为next定义隐含是前缀和后缀相等，所以k实际是arr的索引位置，继续循环比较
            }
            if (arr[q] == arr[k]) { //如果相等，那么最大相同前后缀长度加1
                k++;
            }
            next[q] = k;
        }
        return next;
    }

    /**
     * 思想：回退移动模式串的位置
     * @param s 目标串
     * @param p 模式串
     * @return s中的匹配位置，-1表示未找到
     */
    public int kmp(String s, String p) {
        int[] next = next(p.toCharArray());
        int k = 0;
        for (int i = 0; i < s.length(); ++i) {
            while (k > 0 && s.charAt(i) != p.charAt(k)) {
                k = next[k - 1];
            }
            if (s.charAt(i) == p.charAt(k)) { // 有可能k=0还不相等，则k不变，比较s的下一个位置
                k++;
            }
            if (k == p.length()) { // 模式串已匹配
                return i - p.length() + 1; // 返回匹配位置
            }
        }
        return -1;
    }

}
