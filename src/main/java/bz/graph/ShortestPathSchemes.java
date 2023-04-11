package bz.graph;

import bz.Util;

import java.util.*;

import static bz.Util.MOD;

/**
 * 涉及算法：dijkstra算法的朴素版本和 基于最小堆的优化版本。 拓扑排序算法。
 *
 * 1976. 到达目的地最短路径的方案数
 *
 * 在一个城市里，城市由 n 个路口组成，路口编号为 0 到 n-1，某些路口之间有 双向 道路。输入保证你可以从任意路口出发到达其他任意路口，且任意两个路口之间最多有一条路。
 * 给你一个整数 n 和二维整数数组 roads，其中  表示在路口 ui 和 vi 之间有一条需要花费 ti 时间才能通过的道路。你想知道花费 最少时间 从路口 0 出发到达路口 n-1 的方案数。
 *
 * 输入：n = 7, roads = [[0,6,7],[0,1,2],[1,2,3],[1,3,3],[6,3,3],[3,5,1],[6,5,1],[2,5,1],[0,4,5],[4,6,2]]
 * 输出：4
 * 从路口 0 出发到路口 6 花费的最少时间是 7 分钟。 有4种方案
 */
public class ShortestPathSchemes {
    // 不超过200个顶点
    private final int N = 210;

    // 0点到其他点的最短路径
    private long[] dist = new long[N];

    //  实际顶点个数
    private int n;

    // 稠密图用邻接矩阵表示法
    private int[][] adjacentMatrix = new int[N][N];

    // 稀疏图用链式前向星存图，不然存储稠密图需要开更大倍的数组空间
    int[] head = new int[N]; // 某个顶点开始的 边集合 的第一条边的索引（边集合以链形式存储）
    int[] end = new int[N * 2]; // 某条边指向的顶点
    int[] next = new int[N * 2]; // 某条边的下一条边（有相同起点的边）
    int[] weight = new int[N * 2]; // 某条边的权重

    // 重新建图的入度
    private int[] indegrees = new int[N];

    /**
     * 首先使用朴素 Dijkstra 求解从 0 号点到其他点的最短路，记为 dist 数组， dist[i]=x代表以 0 号点为起点到到 i 点的最短路径为 x。
     *
     * 用反证法容易证明的性质： 在任意的合法方案中，途径的该路径中的每个点时，都是以最短路径的方式到达的。
     *
     * 利用该性质，我们可以对图进行「重建」，对于原图中点 a 与点 b 权重为 w 的无向边，我们根据 dist[a]、dist[b] 和 w 三者关系建立有向边，并统计入度：
     *   若有 dist[b]=dist[a]+w，在新图上增加从 a 到 b 的权重为 w 的有向边，同时 b 入度加一;
     *   若有 dist[a]=dist[b]+w，在新图上增加从 b 到 a 的权重为 w 的有向边，同时 a 入度加一。
     *
     * 构建新图的目的是能够在跑「拓扑排序」过程中进行 DP，统计方案数。
     */
    public int countPaths(int n, int[][] roads) {
        this.n = n;
        for (int[] info : roads) {
            int a = info[0], b = info[1], w = info[2];
            adjacentMatrix[a][b] = adjacentMatrix[b][a] = w;
        }
        // 朴素 Dijkstra 求解从 0 点到其他点的最短路
        dijkstra();

        return rebuildGraphAndCount(roads);
    }

    public int countPathsOptimized(int n, int[][] roads) {
        this.n = n;

        // 初始化图
        Arrays.fill(head, -1);
        int idx = 0; // 边索引号
        for (int[] info : roads) {
            int a = info[0], b = info[1], w = info[2];

            // 对于无向图，需要存储两条边
            add(a, b, w, idx++);
            add(b, a, w, idx++);
        }
        // 最小堆优化的Dijkstra算法 求解从 0 点到其他点的最短路
        dijkstraOptimized();

        return rebuildGraphAndCount(roads);
    }

    private int rebuildGraphAndCount(int[][] roads) {
        // 利用最短路径重新建图，并统计入度
        for (int[] info : roads) {
            int a = info[0], b = info[1], w = info[2];
            adjacentMatrix[a][b] = adjacentMatrix[b][a] = 0;
            if (dist[a] + w == dist[b]) {
                adjacentMatrix[a][b] = w;
                indegrees[b]++;
            } else if (dist[b] + w == dist[a]) {
                adjacentMatrix[b][a] = w;
                indegrees[a]++;
            }
        }

        // 跑拓扑排序统计方案数
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indegrees[i] == 0) {
                deque.addLast(i);
            }
        }
        // dp[i]: 从 0 到达 i 点的方案数， dp[n-1]为答案，同时我们有显而易见的初始化条件 dp[0]=1
        int[] dp = new int[n];
        dp[0] = 1;
        // 遍历入度为0的起点
        while (!deque.isEmpty()) {
            int x = deque.pollFirst();

            // 遍历所有邻接的顶点
            for (int i = 0; i < n; i++) {
                if (adjacentMatrix[x][i] == 0) {
                    continue;
                }
                // 如果 点x在 0到i点的最短路径上，则累加x的方案数
                dp[i] += dp[x];
                dp[i] %= MOD;
                if (--indegrees[i] == 0) {
                    deque.addLast(i);
                }
            }
        }
        return dp[n - 1];
    }

    /**
     * 注意：Dijkstra不能适用于负权边
     */
    void dijkstra() {
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        boolean[] visit = new boolean[N];
        for (int i = 0; i < n; i++) {
            int t = -1;
            // 每次找到当前 距起点最短的路径的点t，然后用该点去更新其他点的最短路径
            for (int j = 0; j < n; j++) {
                if (!visit[j] && (t == -1 || dist[j] < dist[t])) {
                    t = j;
                }
            }
            visit[t] = true;
            for (int j = 0; j < n; j++) {
                if (adjacentMatrix[t][j] == 0) {
                    continue;
                }
                // 从当前距起点最短的路径的 t 点 来更新 起点到j点的 最短路径，也就是遍历点t所有可达的点
                dist[j] = Math.min(dist[j], dist[t] + adjacentMatrix[t][j]);
            }
        }
    }

    /**
     * 使用最小堆优化
     */
    void dijkstraOptimized() {
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;

        // 以最短距离排序，堆顶点是距离起点最短的距离
        PriorityQueue<long[]> minHeap = new PriorityQueue<>(Comparator.comparingLong(o -> o[1]));

        boolean[] visit = new boolean[N];
        // <顶点编号， 距离>
        minHeap.offer(new long[]{0, 0});
        while (!minHeap.isEmpty()) {
            long[] top = minHeap.poll();
            int vertex = (int) top[0];
            long distance = top[1];
            if (visit[vertex]) {
                continue;
            }
            visit[vertex] = true;
            //通过这个点更新 和它相连的点 距离起点的距离
            for (int i = head[vertex]; i != -1 ; i = next[i]) {
                int j = end[i]; // 相邻顶点编号
                if (distance + weight[i] < dist[j]) {
                    dist[j] = distance + weight[i];
                    minHeap.offer(new long[]{j, dist[j]});
                }
            }
        }
    }

    void add(int a, int b, int w, int idx) {
        end[idx] = b; // 索引idx的边指向顶点b
        next[idx] = head[a]; // 原来以顶点a开始的 第一条边索引是head[a]，更新next数组
        head[a] = idx; // 更新head数组，指向当前添加的边。

        weight[idx] = w;
    }
}
