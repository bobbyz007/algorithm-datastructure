package bz.math;

import java.util.Arrays;

/**
 * 计算几何的凸包问题
 *
 * 587. 安装栅栏
 * 在一个二维的花园中，有一些用 (x,y) 坐标表示的树。由于安装费用十分昂贵，你的任务是先用最短的绳子围起所有的树。只有当所有的树都被绳子包围时，花园才能围好栅栏。
 * 你需要找到正好位于栅栏边界上的树的坐标。
 *
 * 例如：
 * 输入: [[1,1],[2,2],[2,0],[2,4],[3,3],[4,2]]
 * 输出: [[1,1],[2,0],[4,2],[3,3],[2,4]]
 *
 * 思路： Andrew 算法正是用于求解凸包上的所有点（围成所有点的最小周长），其算法逻辑将凸包分为「上凸壳」和「下凸壳」
 *
 */
public class ConvexHull {
    public int[][] outerTrees(int[][] trees) {
        // 对所有点进行双关键字排序，先根据 x 坐标排升序，后根据 y 排升序；根据 x 排升序的目的，是为了我们能够往一个方向画出凸包边缘（从左往后画出一半凸壳，从右往左画出另外一半）。
        // 而将 y 升序目的是可以确保一旦我们现在从 a 到 b 进行连线，那么 a 到 b 之间的所有点能够确保被围住；
        Arrays.sort(trees, (a, b)-> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        int n = trees.length;

        // 使用栈来维护凸包上的边（栈中相邻元素代表凸包上的一条边）
        int tp = 0;
        int[] stack = new int[n + 10];

        // 为了处理凸包第二部分的时候，不要使用到凸包第一部分的点。
        boolean[] visit = new boolean[n + 10];
        stack[++tp] = 0; // 不标记起点

        // 第一部分处理
        for (int i = 1; i < n; i++) {
            int[] c = trees[i];

            // 栈中少于2个点，直接将元素添加到栈中。因为一条边至少需要2个点，
            while (tp >= 2) {
                int[] a = trees[stack[tp - 1]];
                int[] b = trees[stack[tp]];
                if (getArea(a, b, c) > 0) {
                    // ac在ab的逆时针方向，删除ab边
                    visit[stack[tp--]] = false;
                } else {
                    // ac在ab的顺时针方向，保留ab边
                    break;
                }
            }
            // 添加顶点c到栈中
            stack[++tp] = i;
            visit[i] = true;
        }

        final int size = tp;
        // 第二部分处理
        for (int i = n - 1; i >= 0; i--) {
            // 忽略第一部分中已处理过的顶点
            if (visit[i]) {
                continue;
            }
            int[] c = trees[i];
            // 栈中还保留第一部分处理后保存的节点，个数为size
            while (tp > size) {
                int[] a = trees[stack[tp - 1]];
                int[] b = trees[stack[tp]];
                if (getArea(a, b, c) > 0) {
                    // ac在ab的逆时针方向，删除ab边
                    tp--;
                    // vis[stk[tp--]] = false; // 非必须
                } else {
                    // ac在ab的顺时针方向，保留ab边
                    break;
                }
            }
            stack[++tp] = i;
            // vis[i] = true; // 非必须
        }

        int[][] ans = new int[tp - 1][2];
        for (int i = 1; i < tp; i++) {
            ans[i - 1] = trees[stack[i]];
        }
        return ans;
    }

    private int[] subtraction(int[] a, int[] b) { // 向量相减
        return new int[]{a[0] - b[0], a[1] - b[1]};
    }

    /**
     *  >0: 向量x 正旋转 到y的角度<180°
     * ==0: 向量x平行于向量y
     *  <0: 向量x 正旋转 到y的角度>180°
     *
     *  注意： 什么是正旋转？  2d中，坐标轴x轴朝y轴方向旋转90°和y轴重合的方向视为正旋转。
     *  例如计算机图形使用的坐标系一般都是y的正方向朝下，需要一个通用且没有歧义的正旋转定义。
     *
     *  对于标准的x轴朝右y轴朝上，正旋转就是逆时针方向。
     *  对于计算机图形学的x轴朝右y轴朝下，正旋转就是顺时针方向。
      */
    private double cross(int[] x, int[] y) { // 向量叉乘
        return x[0] * y[1] - x[1] * y[0];
    }
    private double getArea(int[] a, int[] b, int[] c) { // 向量 ab 转为 向量 ac 过程中扫过的面积
        return cross(subtraction(b, a), subtraction(c, a));
    }
}
