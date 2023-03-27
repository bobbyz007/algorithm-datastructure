package bz.permutation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Permutation {
    /**
     * 排列
     */
    public List<String> permute(String str) {
        List<String> result = new ArrayList<>();
        permute(new StringBuilder(str), 0, result);
        return result;
    }

    private void permute(StringBuilder str, int begin, List<String> result) {
        if (begin == str.length()) {
            result.add(str.toString());
            return;
        }

        for (int pos = begin; pos < str.length(); pos++) {
            swap(str, begin, pos);

            permute(str, begin + 1, result);

            swap(str, begin, pos);
        }
    }

    /**
     * 从n个数字中 选择选取长度为m的组合
     * 比如 n=3，m=2，则组合为{1, 2}， {1, 3}， {2, 3}
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();

        if(n <= 0 || n < k) {
            return result;
        }

        ArrayList<Integer> item = new ArrayList<Integer>();
        dfsCombine(n, k, 1, item, result);

        return result;
    }

    private void dfsCombine(int n, int k, int start, ArrayList<Integer> item, List<List<Integer>> res) {
        if(item.size() == k) {
            res.add(new ArrayList<>(item));
            return;
        }

        for(int i = start; i <= n; i++) {
            item.add(i);
            dfsCombine(n, k, i + 1, item, res);
            item.remove(item.size() - 1);
        }
    }

    /**
     * 另外一种分而治之的算法
     */
    public List<List<Integer>> combine2(int n, int k) {
        return combineDivide(n, k, 1);
    }

    private List<List<Integer>> combineDivide(int n, int k, int start) {
        List<List<Integer>> result = null;
        if (n -start + 1 == k) {
            result = new ArrayList<>();
            List<Integer> item = new LinkedList<>();
            result.add(item);
            for (int i = start; i <= n; i++) {
                item.add(i);
            }
            return result;
        }
        if (k == 1) {
            result = new ArrayList<>();
            for (int i = start; i <= n; i++) {
                List<Integer> item = new LinkedList<>();
                item.add(i);
                result.add(item);
            }
            return result;
        }

        // 组合包含start字符，则从start+1开始选取 k-1个字符
        result = combineDivide(n, k - 1, start + 1);
        for (List<Integer> item : result) {
            item.add(0, start);
        }

        // 组合不包含start字符，则从start+1开始选择k个字符
        result.addAll(combineDivide(n, k, start + 1));

        return result;
    }

    private void swap(StringBuilder str, int pos1, int pos2) {
        if (pos1 == pos2) {
            return;
        }
        char temp = str.charAt(pos1);
        str.setCharAt(pos1, str.charAt(pos2));
        str.setCharAt(pos2, temp);
    }


}
