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
            if (!uf.isConnected(u, v)) {
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
}

