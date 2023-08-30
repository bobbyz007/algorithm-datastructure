package bz.string;

/**
 * Rabin-Karp算法
 * 不再逐个比较窗口内的字符，而是比较窗口的hash值，从而得到更好的时间复杂度。
 *
 * 性能比KMP算法差，但它的优势在于可以在其他的变种问题中应用。
 */
public class RabinKarp {
    static final long PRIME = 72_057_594_037_927_931L;
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
