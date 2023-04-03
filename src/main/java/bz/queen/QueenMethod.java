package bz.queen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueenMethod {
    /**
     * 八皇后问题：同行同列同对角线 不能有两个皇后
     * @param queens
     * @return
     */
    public List<int[]> arrangeMethod(int queens) {
        // int[i]=j: queen位于第i行，第j列
        int[] queenArr = new int[queens];

        List<int[]> result = new ArrayList<>();

        put(0, queenArr, result);

        return result;
    }

    private void put(int queenIdx, int[] queenArr, List<int[]> result) {
        if (queenIdx == queenArr.length) {
            result.add(Arrays.copyOf(queenArr, queenArr.length));
            return;
        }

        for (int i = 0; i < queenArr.length; i++) {
            // 第 queenIdx 个皇后 放在位置 i
            queenArr[queenIdx] = i;
            if (check(queenArr, queenIdx)) {
                // 不冲突的话就继续放置第queenIdx+1个。
                put(queenIdx + 1, queenArr, result);
            }
            // 如果冲突，则试下一个列位置i+1
        }
    }

    //检查放到第queenIdx个皇后时，是否与之前的皇后冲突
    private boolean check(int[] queenArr, int queenIdx) {
        for (int i = 0; i < queenIdx; i++) {
            if (queenArr[i] == queenArr[queenIdx]
                    || Math.abs(i - queenIdx) == Math.abs(queenArr[i] - queenArr[queenIdx])) {
                return false;
            }
        }
        return true;
    }
}
