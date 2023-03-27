package bz.math;

public class NumberCount {
    /**
     * 1到n整数中1的个数：用暴力法复杂度太高。
     * 需要找到数学规律
     */
    public int numberOfOneBetweenOneAndN(int n) {
        return numberOfOneBetweenOneAndN(String.valueOf(n));
    }

    public int numberOfOneBetweenOneAndN(String nStr) {
        if (nStr == null || nStr.length() < 1) {
            return 0;
        }
        int first = nStr.charAt(0) - '0';

        if (nStr.length() == 1 && first == 0) {
            return 0;
        }
        if (nStr.length() == 1 && first > 0) {
            return 1;
        }

        int length = nStr.length();
        String strOtherThanHighest = nStr.substring(1);
        /** 在最高位 1出现的次数
         * 比如 32839，最高位3大于1，则最高位1出现的次数就是 10^4，也就是从10000~19999
         * 比如 12839，最高位已经固定为1了， 则决定于后面的数字个数
         */
        int numberOfOneInHighest = first > 1 ? (int)Math.pow(10, length - 1) : Integer.valueOf(strOtherThanHighest) + 1;

        /**
         * 除了最高位外，其余位置的1的个数
         * 同样考察 32839，剩下的4位可以1~2839 和 2840~32839 两段来考虑。1~2839是子问题可以递归求解。
         *
         * 2840~32839：剩下啊的4位 每个位置都可以是1，每个位置固定1，其他3个位置可以从0到9，则排列组合为 3 * 4*10^3
         * 前面的3表示是3段：2840~12839, 12840~22839, 22840~32839
         *
         * 为易于理解，可以参考 24~123， 1出现的次数是2*10^1 (2个位置，固定一个位置为1， 另外一个位置可从0到9)
          */
        int numberOfOneInOther = first * (length - 1) * (int) Math.pow(10, length - 2);

        // 低位，例如 1~2839
        int numberOfOneInLower = numberOfOneBetweenOneAndN(strOtherThanHighest);

        return numberOfOneInHighest + numberOfOneInOther + numberOfOneInLower;
    }
}
