package bz.queue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 双端队列： 也就是区别于传统的队列的队尾进队头出（先进先出），双端队列可以队尾进，队尾出或队头出，兼具栈的特点。
 * Java自带了实现: ArrayDeque 或 LinkedList（同时实现了List和Deque接口）
 *
 * 1190. 反 转 每对括号间的子串
 * 给出一个字符串 s（仅含有小写英文字母和括号）。
 * 请你按照从括号内到外的顺序，逐层反转每对匹配括号中的字符串，并返回最终的结果。
 * 注意，您的结果中 不应 包含任何括号。
 *
 * (ed(et(oc))el) -> (ed(etco)el) -> (edocteel) -> leetcode
 * (u(love)i) -> (uevoli) -> iloveu
 *
 * 思路： 双端队列模拟
 */
public class DoubleEndQueue {
    public String reverseParentheses(String s) {
        int n = s.length();
        char[] cs = s.toCharArray();
        // 双端队列模拟
        Deque<Character> d = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            char c = cs[i];
            if (c == ')') {
                StringBuilder path = new StringBuilder();
                while (!d.isEmpty()) {
                    if (d.peekLast() != '(') {
                        path.append(d.pollLast());
                    } else {
                        d.pollLast();
                        for (int j = 0; j < path.length(); j++) {
                            d.addLast(path.charAt(j));
                        }
                        break;
                    }
                }
            } else {
                d.addLast(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!d.isEmpty()) sb.append(d.pollFirst());
        return sb.toString();
    }
}
