package bz.graph;

import bz.unionfind.UnionFind;

import java.util.*;

/**
 * 图论： kruskal思想 + 并查集。 用二分优化
 *
 * <pre>778. 水位上升的泳池中游泳
 *
 * 在一个 N*N 的坐标方格 grid 中，每一个方格的值 grid[i][j] 表示在位置 (i,j) 的平台高度。
 * 现在开始下雨了，当时间为 t 时，此时雨水导致水池中任意位置的水位为 t。
 *
 * 你可以从一个平台游向四周相邻的任意一个平台，但是前提是此时水位必须同时淹没这两个平台。
 * 假定你可以瞬间移动无限距离，也就是默认在方格内部游动是不耗时的。
 *
 * 你从坐标方格的左上平台 (0,0) 出发， 最少耗时多久你才能到达坐标方格的右下平台 (N-1,N-1)？
 *
 * <pre> 思路： 同 1631. 最小体力消耗路径 类似的思路。
 * 由于在任意点可以往任意方向移动，所以相邻的点（四个方向）之间存在一条无向边。
 *
 * 边的权重 w 是指两点节点中的最大高度（注意：不是高度差，因为要求雨水淹没平台，所以需要淹没最大高度）。
 *
 * 我们需要找的是从左上角点到右下角点的最优路径，其中最优路径是指「途径的边的最大权重值 最小」。
 *
 * 对所有的边集合进行排序，按照权重 w 进行从小到大进行排序（Kruskal部分）。当我们有了所有排好序的候选边集合之后，我们可以对边从前往后处理，每次加入一条边之后，
 * 使用并查集来查询左上角的点和右下角的点是否连通。如果连通，那么该边的权重即是答案
 */
public class SwimInWater {
    public int swimInWater(int[][] grid) {
        int n = grid.length;

        // 初始化并查集: 每个单元格有个唯一序号
        UnionFind uf = new UnionFind(n * n);

        // 预处理出所有的边
        // edge 存的是 [a, b, w]：代表从 a 到 b 所需要的时间为 w
        // 虽然我们可以往四个方向移动，但是只要对于每个点都添加「向右」和「向下」两条边的话，其实就已经覆盖了所有边了
        List<int[]> edges =  new ArrayList<>();
        for (int i = 0; i < n ;i++) {
            for (int j = 0; j < n; j++) {
                // (i,j)坐标的序号
                int idx = getIndex(i, j, n);
                if (i + 1 < n) {
                    // 向下的边
                    int a = idx, b = getIndex(i + 1, j, n);
                    int w = Math.max(grid[i][j], grid[i + 1][j]);
                    edges.add(new int[]{a, b, w});
                }
                if (j + 1 < n) {
                    // 向右的边
                    int a = idx, b = getIndex(i, j + 1, n);
                    int w = Math.max(grid[i][j], grid[i][j + 1]);
                    edges.add(new int[]{a, b, w});
                }
            }
        }

        // 根据权值 w 升序（类似kruskal）
        Collections.sort(edges, Comparator.comparingInt(edge -> edge[2]));

        // 从「小边」开始添加，当某一条边别应用之后，恰好使用得「起点」和「结点」连通
        // 那么代表找到了「最短路径」中的「权重最大的边」（因为已经按照升序排序）
        int start = getIndex(0, 0, n), end = getIndex(n - 1, n - 1, n);
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1], w = edge[2];
            uf.union(a, b);
            if (uf.isConnected(start, end)) {
                return w;
            }
        }
        return 0;
    }

    private int getIndex(int i, int j, int n) {
        return i * n + j;
    }

    /**
     * 基于二分+BFS
     * 题目给定了 grid[i][j] 的范围是 0~n^2-1，所以答案必然落在此范围。
     *
     * 假设最优解为 min 的话（恰好能到达右下角的时间）。那么小于 min 的时间无法到达右下角，大于 min 的时间能到达右下角。
     * 因此在以最优解 min 为分割点的数轴上具有两段性，可以通过「二分」来找到分割点 min。
     * 注意：「二分」的本质是两段性，并非单调性。只要一段满足某个性质，另外一段不满足某个性质，就可以用「二分」。
     */
    int[][] dirs = new int[][]{{1,0}, {-1,0}, {0,1}, {0,-1}};
    public int swimInWaterOptimized(int[][] grid) {
        int n = grid.length;
        int l = 0, r = n * n;
        while (l < r) {
            int mid = l + r >> 1;
            if (check(grid, mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return r;
    }

    // bfs搜索水位是time时候，是否可以到达右下节点
    private boolean check(int[][] grid, int time) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n];
        Deque<int[]> queue = new ArrayDeque<>();

        visited[0][0] = true;
        queue.addLast(new int[]{0, 0});

        while (!queue.isEmpty()) {
            int[] pos = queue.pollFirst();
            int x = pos[0], y = pos[1];
            if (x == n - 1 && y == n - 1) {
                return true;
            }

            for (int[] dir : dirs) {
                int newX = x + dir[0], newY = y + dir[1];
                int[] to = new int[]{newX, newY};
                if (inArea(n, newX, newY) && !visited[newX][newY] && canMove(grid, pos, to, time)) {
                    visited[newX][newY] = true;
                    queue.addLast(to);
                }
            }
        }
        return false;
    }
    boolean inArea(int n, int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }
    boolean canMove(int[][] grid, int[] from, int[] to, int time) {
        return time >= Math.max(grid[from[0]][from[1]], grid[to[0]][to[1]]);
    }
}
