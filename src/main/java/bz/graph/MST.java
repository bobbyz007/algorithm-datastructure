package bz.graph;

import bz.unionfind.UnionFind;

import java.util.*;

/**
 * <p>MST: 最小生成树 Minimum Spanning Tree
 *
 * <pre>连通图相关概念： 连通图是在无向图的基础上对图中顶点之间的连通做了更高的要求，而强连通图是在有向图的基础上对图中顶点的连通做了更高的要求。
 * 1. 无向图中，如果任意两个顶点之间都能够连通，则称此无向图为连通图。
 * 若无向图不是连通图，但图中存储某个子图（这里的子图指的是图中"最大"的连通子图，也称"极大连通子图"）符合连通图的性质，则称该子图为连通分量。
 *
 * 一个无向图 G=(V,E) 是连通的，那么边的数目大于等于顶点的数目减一：|E|>=|V|-1，而反之不成立。
 *
 * 2. 有向图中，若任意两个顶点 Vi 和 Vj，满足从 Vi 到 Vj 以及从 Vj 到 Vi 都连通，也就是都含有至少一条通路，则称此有向图为强连通图。
 * 与此同时，若有向图本身不是强连通图，但其包含的最大连通子图具有强连通图的性质，则称该子图为强连通分量。
 *
 * 如果 G=(V,E) 是有向图，那么它是强连通图的必要条件是边的数目大于等于顶点的数目：|E|>=|V|，而反之不成立。
 *
 * <pre>生成树相关概念：
 * 1. 对连通图进行遍历，过程中所经过的边和顶点的组合可看做是一棵普通树，通常称为生成树。
 * 连通图中，由于任意两顶点之间可能含有多条通路，遍历连通图的方式有多种，往往一张连通图可能有多种不同的生成树与之对应。
 *
 * 2. 连通图中的生成树必须满足以下 2 个条件：
 *   a. 包含连通图中所有的顶点；
 *   b. 任意两顶点之间有且仅有一条通路；
 * 因此，连通图的生成树具有这样的特征，即生成树中边的数量 = 顶点数 - 1。
 *
 * <pre>最小生成树相关概念：
 * 所谓一个 带权图 的最小生成树，就是原图中边的权值最小的生成树 ，所谓最小是指边的权值之和小于或者等于其它生成树的边的权值之和。
 *
 * 最小生成树的问题，简单得理解就是给定一个带有权值的连通图（连通网），从众多的生成树中筛选出权值总和最小的生成树，即为该图的最小生成树。
 *
 */
public class MST {
    /**
     * 适合于稀疏图
     * 总的时间复杂度为O(ElogE) 或者O(Elogn)
     *
     * @param edges 边以及权重三元组： <u, v, w>
     * @param n 顶点个数
     */
    public int kruskal(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);

        // 元组<u,v,w>：按照权重w 非降序
        Arrays.sort(edges, Comparator.comparingInt(e -> e[2]));

        // 最小权重和
        int minWeightSum = 0;

        // 选取的边数
        int edgeCnt = 0;

        // 遍历排序好的边
        for (int[] edge : edges) {
            // 直到选取了n-1条边 已经构成了最小生成树
            if (edgeCnt == n - 1) {
                break;
            }

            int u = edge[0], v = edge[1], w = edge[2];
            // 加入u和v后，没有构成环； 如果构成环，直接舍弃
            if (uf.find(u) != uf.find(v)) {
                minWeightSum += w;  // 选取这条边
                uf.union(u, v); // 加入同一集合，用于判断是否连通
                edgeCnt++;
            }
        }

        return minWeightSum;
    }

    // 稠密图用邻接矩阵表示法
    private int[][] adjacentMatrix;

    /**
     * 适合于稠密图
     * 算法时间复杂度 O(n^2)
     * 对于给定的连通网，起始状态全部顶点都归为 B 类。在找最小生成树时，选定任意一个顶点作为起始点，并将之从 B 类移至 A 类；
     * 然后找出 B 类中到 A 类中的顶点之间权值最小的顶点，将之从 B 类移至 A 类，如此重复，直到 B 类中没有顶点为止。
     * 所走过的顶点和边就是该连通图的最小生成树。
     *
     * @param startVertex 从哪个顶点开始构建最小生成树
     * @param n 顶点个数
     * @param edges 边
     * @return
     */
    public int prim(int startVertex, int n, int[][] edges) {
        // 构建邻接矩阵， 默认的0表示无穷大不可达。此处有visit数组，不存在同一个顶点的距离判断。
        buildAdjacentMatrix(n, edges);

        final int INF = Integer.MAX_VALUE;
        // 未标记顶点到已生成树的最短距离
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) {
            // 以默认0表示不可达
            dist[i] = adjacentMatrix[startVertex][i] == 0 ? INF : adjacentMatrix[startVertex][i];
        }

        // 初始化标记数组
        boolean[] visit = new boolean[n];
        visit[startVertex] = true;
        // 已标记的顶点个数
        int vertexCnt = 1;
        // 最小权重和
        int minWeightSum = 0;

        // 遍历各个顶点
        while (vertexCnt < n) {
            int minDist = INF;
            int minVertex = 0;
            for (int i = 0; i < n; i++) {
                // 顶点i未标记，0表示不可达: 找到离已生成树的最小权重并且没有被标记的点
                if (!visit[i] && 0 < dist[i] && dist[i] < minDist) {
                    minDist = dist[i];
                    minVertex = i;
                }
            }
            visit[minVertex] = true; // 标记已找到的顶点
            vertexCnt++;
            minWeightSum += dist[minVertex];

            for (int i = 0; i < n; i++) {
                // 以找到的minVertex可达的顶点 更新 dist
                if (!visit[i] && 0 < adjacentMatrix[minVertex][i] && adjacentMatrix[minVertex][i] < dist[i]) {
                    dist[i] = adjacentMatrix[minVertex][i];
                }
            }
        }
        return minWeightSum;
    }

    private void buildAdjacentMatrix(int n, int[][] edges) {
        this.adjacentMatrix = new int[n][n];
        for (int[] info : edges) {
            int a = info[0], b = info[1], w = info[2];
            adjacentMatrix[a][b] = adjacentMatrix[b][a] = w;
        }
    }

    // 稀疏图用链式前向星存图，不然存储稠密图需要开更大倍的数组空间
    final int N = 210;
    int[] head = new int[N]; // 某个顶点开始的 边集合 的第一条边的索引（边集合以链形式存储）
    int[] end = new int[N * 2]; // 某条边指向的顶点
    int[] next = new int[N * 2]; // 某条边的下一条边（有相同起点的边）
    int[] weight = new int[N * 2]; // 某条边的权重
    // 基于最小堆优化最短路径计算
    public int primOptimized(int startVertex, int n, int[][] edges) {
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

    // 定义邻接表
    // private List<int[]>[] adjacentMap = new List[N];
    private Map<Integer, List<int[]>> adjacentMap = new HashMap<>();
    // 基于邻接表的最小堆优化最短路径计算
    public int primOptimizedWithMap(int startVertex, int n, int[][] edges) {
        buildAdjacentMap(edges);

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
            for (int[] info : adjacentMap.get(vertex)) {
                int v = info[0], w = info[1];
                if (!visit[v] && w < dist[v]) {
                    dist[v] = w;
                    minHeap.offer(new long[]{v, dist[v]});
                }

            }
        }
        return minWeightSum;
    }

    private void buildAdjacentMap(int[][] edges) {
        for (int[] info : edges) {
            int a = info[0], b = info[1], w = info[2];
            // 对于无向图，需要存储两条边
            List<int[]> end = adjacentMap.getOrDefault(a, new ArrayList<>());
            add(a, b, w);
            add(b, a, w);
        }
    }
    private void add(int a, int b, int w) {
        List<int[]> list = adjacentMap.get(a);
        if (list == null) {
            list = new ArrayList<>();
            adjacentMap.put(a, list);
        }
        list.add(new int[]{b, w});
    }

}

