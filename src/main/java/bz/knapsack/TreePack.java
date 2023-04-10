package bz.knapsack;

import java.util.Arrays;

/**
 * 树形背包
 * 有 n 个物品和一个容量为 volume 的背包，物品编号为0,1,2 ... n-1 。
 * 物品之间具有依赖关系，且依赖关系组成一棵树的形状。 如果选择一个物品，则必须选择它的父节点。
 *
 * 第 i 件物品的成本为cost[i] ，价值为 worth[i]，其父节点物品编号为parent[i] ，其中根节点parent[i]=-1 。
 * 求解将哪些物品装入背包，可使这些物品的总成本不超过背包容量，且总价值最大。
 *
 * 树形结构：以 链式前向星存图 的方式保存，这种方式存储效率高，用数组方式模拟邻接表。缺点是不能直接用起点和终点确定是否有边
 *
 * 时间复杂度： cost.length * volume^2，主要适用于「容量」维度数据范围较小（即volume较小）的情况。
 */
public class TreePack {
    // 假设容量 和 树节点个数都不超过 100
    int n = 110;
    // 用于链式前向星存图： 每条边有个唯一的索引
    int[] head = new int[n]; // 某个顶点开始的 边集合 的第一条边的索引（边集合以链形式存储）
    int[] end = new int[n * 2]; // 某条边指向的顶点
    int[] next = new int[n * 2]; // 某条边的下一条边（有相同起点的边）

    // 背包条件定义
    int[] cost;
    int[] worth;
    int volume;

    // 定义 dp[u][j] 为考虑以 u 为根的子树，背包容量不超过 j 的最大价值
    int[][] dp = new int[n][n];

    // 链式向前星存图
    void add(int a, int b, int idx) {
        end[idx] = b; // 索引idx的边指向顶点b
        next[idx] = head[a]; // 原来以顶点a开始的 第一条边索引是head[a]，更新next数组
        head[a] = idx; // 更新head数组，指向当前添加的边。
    }

    public int maxValue(int[] parent, int[] cost, int[] worth, int volume) {
        this.cost = cost;
        this.worth = worth;
        this.volume = volume;

        // 初始化树
        Arrays.fill(head, -1);
        int root = -1;
        int idx = 0; // 边索引号
        for (int i = 0; i < cost.length; i++) {
            if (parent[i] == -1) {
                root = i;
            } else {
                add(parent[i], i, idx++);
            }
        }

        dfs(root);
        return dp[root][volume];
    }

    void dfs(int u) {
        // 要选u的任一子节点，必须先选 u，同时也限制了至少需要 cost[u]的 容量
        for (int j = cost[u]; j <= volume ; j++) {
            dp[u][j] += worth[u];
        }

        // 遍历节点 u 的所有直接子节点 x（分组背包遍历物品组）
        for (int i = head[u]; i != -1 ; i = next[i]) {
            // u指向的顶点
            int child = end[i];
            // 递归处理子节点，递归完成后刷新得到 dp[child][...]的值
            dfs(child);

            // 从大到小遍历背包容量（分组背包遍历容量）
            for (int j = volume; j >= 0; j--) {
                // 遍历给节点 child 分配多少背包容量（分组背包遍历决策）
                for (int l = 0; l <= j - cost[u]; l++) {
                    dp[u][j] = Math.max(dp[u][j], dp[u][j - l] + dp[child][l]);
                }
            }
        }
    }

}
