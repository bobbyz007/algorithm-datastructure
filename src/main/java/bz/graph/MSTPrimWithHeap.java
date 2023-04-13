package bz.graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 基于最小堆优化prim算法
 * 稀疏图用链式前向星存图
 */
public class MSTPrimWithHeap {
    // 稀疏图用链式前向星存图，不然存储稠密图需要开更大倍的数组空间
    final int N = 210;
    int[] head = new int[N]; // 某个顶点开始的 边集合 的第一条边的索引（边集合以链形式存储）
    int[] end = new int[N * 2]; // 某条边指向的顶点
    int[] next = new int[N * 2]; // 某条边的下一条边（有相同起点的边）
    int[] weight = new int[N * 2]; // 某条边的权重
    // 基于最小堆优化最短路径计算
    public int prim(int startVertex, int n, int[][] edges) {
        buildGraph(edges);

        // 未标记顶点到已生成树的最短距离
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;

        // 以距离排序，堆顶是未标记顶点距离已生成树的最短距离
        PriorityQueue<long[]> minHeap = new PriorityQueue<>(Comparator.comparingLong(o -> o[1]));

        // 最小权重和
        int minWeightSum = 0;

        boolean[] visit = new boolean[n];
        // <顶点编号， 距离>
        minHeap.offer(new long[]{0, 0});
        while (!minHeap.isEmpty()) {
            // 弹出 未标记顶点距离已生成树的最短距离
            long[] top = minHeap.poll();
            int vertex = (int) top[0];
            long distance = top[1];
            if (visit[vertex]) {
                continue;
            }
            visit[vertex] = true;
            minWeightSum += distance;

            // 遍历和vertex相邻的点， 更新未标记顶点距离已生成树的最短距离
            for (int i = head[vertex]; i != -1 ; i = next[i]) {
                int j = end[i]; // 相邻顶点编号
                if (!visit[j] && weight[i] < dist[j]) {
                    dist[j] = weight[i];
                    minHeap.offer(new long[]{j, dist[j]});
                }
            }
        }
        return minWeightSum;
    }

    private void buildGraph(int[][] edges) {
        // 初始化图
        Arrays.fill(head, -1);
        int idx = 0; // 边索引号
        for (int[] info : edges) {
            int a = info[0], b = info[1], w = info[2];
            // 对于无向图，需要存储两条边
            add(a, b, w, idx++);
            add(b, a, w, idx++);
        }
    }

    private void add(int a, int b, int w, int idx) {
        end[idx] = b; // 索引idx的边指向顶点b
        next[idx] = head[a]; // 原来以顶点a开始的 第一条边索引是head[a]，更新next数组
        head[a] = idx; // 更新head数组，指向当前添加的边。

        weight[idx] = w;
    }
}
