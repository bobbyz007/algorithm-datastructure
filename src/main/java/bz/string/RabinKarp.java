package bz.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rabin-Karp算法
 * 不再逐个比较窗口内的字符，而是比较窗口的hash值，从而得到更好的时间复杂度。
 *
 * 性能比KMP算法差，但它的优势在于可以在其他的变种问题中应用。
 */
public class RabinKarp {
    static final long PRIME = 72_057_594_037_927_931L; // 大素数  < 2^(64-8)
    static final int DOMAIN = 128;

    /**
     * 从字符串s中查找模式串t的索引位置
     */
    public int matchRabinKarp(String s, String t) {
        if (s.length() < t.length()) {
            return -1;
        }
        long hashS = 0;
        long hashT = 0;
        long lastPos = ((long) Math.pow(DOMAIN, t.length() - 1)) % PRIME;
        for (int i = 0; i < t.length(); i++) {
            hashS = (DOMAIN * hashS + s.charAt(i)) % PRIME;
            hashT = (DOMAIN * hashT + t.charAt(i)) % PRIME;
        }

        int lenDiff = s.length() - t.length();
        for (int i = 0; i < lenDiff + 1; i++) {
            // 出现hash碰撞，需要逐个字符比较
            if (hashS == hashT) {
                if (match(s, t, i, 0, t.length())) {
                    return i;
                }
            }
            if (i < lenDiff) {
                // 向右滑动窗口的hash值
                hashS = rollHash(hashS, s.charAt(i), s.charAt(i + t.length()), lastPos);
            }
        }
        return -1;
    }

    /**
     * 变种1： 匹配多个模式，其中各个模式串长度必须一致。 只需把模式串集合的hash存入一个集合中即可。
     * 变种2： 公共子串，给定字符串s，t和一个长度值k，寻找长度为k的公共子串。
     * 变种3： 最长公共子串，基于变种2，以二分查找的思路找到最长公共子串
     *
     * s和t中存在长度为k的公共子串，则返回子串对应索引位置。
     */
    public int[] matchRabinKarp(String s, String t, int k) {
        if (s.length() < k || t.length() < k) {
            return null;
        }

        // 存放t子串的hash值->位置
        Map<Long, List<Integer>> hashMap = new HashMap<>();
        long lastPos = ((long) Math.pow(DOMAIN, k - 1)) % PRIME;

        long hashT = 0;
        for (int j = 0; j < k; j++) {
            hashT = (DOMAIN * hashT + t.charAt(j)) % PRIME;
        }
        for (int j = 0; j < t.length() - k + 1; j++) {
            List<Integer> list = hashMap.computeIfAbsent(hashT, x -> new ArrayList<>());
            list.add(j);
            if (j < t.length() - k) {
                hashT = rollHash(hashT, t.charAt(j), t.charAt(j + k), lastPos);
            }
        }

        long hashS = 0;
        for (int i = 0; i < k; i++) {
            hashS = (DOMAIN * hashS + s.charAt(i)) % PRIME;
        }
        for (int i = 0; i < s.length() - k + 1; i++) {
            if (hashMap.containsKey(hashS)) {
                for (int j : hashMap.get(hashS)) {
                    if (match(s, t, i, j, k)) {
                        return new int[]{i, j};
                    }
                }
            }
            if (i < s.length() - k) {
                hashS = rollHash(hashS, s.charAt(i), s.charAt(i + k), lastPos);
            }
        }
        return null;
    }

    /**
     * 计算滑动窗口的hash值
     *
     * 比如abcd的滑动窗口从 abc -> bcd，去除了a，添加了d
     *     a*128^2+b*128+c
     *     b*128+c
     *     b*128^2+c*128
     *     b*128^2+c*128+d
     *
     * @param lastPos: 上次多项式的高次项值
     */
    private long rollHash(long oldVal, int outDigit, int inDigit, long lastPos) {
        long val = (oldVal - outDigit * lastPos + PRIME) % PRIME;
        val = (val * DOMAIN + PRIME) % PRIME;
        return (val + inDigit) % PRIME;
    }

    private boolean match(String s, String t, int i, int j, int k) {
        for (int l = 0; l < k; l++) {
            if (s.charAt(i + l) != t.charAt(j + l)) {
                return false;
            }
        }
        return true;
    }


}
