package bz.graph;

import java.util.*;

/**
 * 基于最小堆优化prim算法
 * 邻接表存储
 */
public class MSTPrimWithHeapMap {
    // 定义邻接表
    // private List<int[]>[] adjacentMap = new List[N];
    private Map<Integer, List<int[]>> adjacentMap = new HashMap<>();
    // 基于邻接表的最小堆优化最短路径计算
    public int prim(int startVertex, int n, int[][] edges) {
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
