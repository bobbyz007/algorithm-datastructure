package bz.knapsack;

import java.util.*;

/**
 * 树形背包的应用：求解预算方案
 *
 * 想买的物品分为两类：主件与附件，附件是从属于某个主件的，下表就是一些主件与附件的例子：
 *   电脑 -》 打印机，扫描仪
 *   书柜 -》 图书
 *   书桌 -》 台灯，文具
 *   工作椅 -》 无
 * 如果要买归类为附件的物品，必须先买该附件所属的主件。 每个主件可以有 0 个、1 个或 2 个附件。附件不再有从属于自己的附件。
 *
 * 可买的物品总共有 M 件物品。
 * 每件物品规定了一个重要度，分为 5 等：用整数1 ~ 5表示，第 5 等最重要。
 * 希望总额在不超过 N 元的前提下，使每件物品的价格与重要度的乘积的总和最大。
 *   设第 j 件物品的价格为 cost[j]，重要度为 priority[j]，共选中了 k 件物品， 则乘积总和为：
 *   cost[j1]*priority[j1] + cost[j2]*priority[j2] + ... + cost[jk]*priority[jk]
 *
 * 请帮助设计一个满足要求的购物单。
 *
 * 思路： 典型的树形背包问题，不过树形节点没有同一个根节点（多个主件），可以虚拟出一个共同的根节点。
 * 时间复杂度： O(M*N^2)，适合N较小的场景，即容量维度较小。
 *
 */
public class TreePackApp {
    // 树节点个数： 可买的物品总数不超过60
    final int M = 65;
    // 容量：花费金额不超过 32000 元
    final int N = 40000;

    // 用于链式前向星存图： 每条边有个唯一的索引
    int[] head = new int[M]; // 某个顶点开始的 边集合 的第一条边的索引（边集合以链形式存储）
    int[] end = new int[M]; // 某条边指向的顶点
    int[] next = new int[M]; // 某条边的下一条边（有相同起点的边）

    // 背包条件定义
    int[] cost;
    int[] priority;
    int volume;

    // 定义 dp[u][j] 为考虑以 u 为根的子树，背包容量不超过 j 的最大价值
    int[][] dp = new int[M][N];

    // 链式向前星存图
    void add(int a, int b, int idx) {
        end[idx] = b; // 索引idx的边指向顶点b
        next[idx] = head[a]; // 原来以顶点a开始的 第一条边索引是head[a]，更新next数组
        head[a] = idx; // 更新head数组，指向当前添加的边。
    }

    /**
     * 花费不超过N元的前提下，求解物品价格与重要度的乘积的最大总和
     * @param parent 每件物品有个依赖关系，主件的parent为0，正好对应虚拟的根节点
     * @param cost 物品的价格
     * @param priority 物品的重要度
     * @param N 购买物品的总额不能超过N元
     */
    public int maxValue(int[] parent, int[] cost, int[] priority, int N) {
        this.cost = cost;
        this.priority = priority;
        this.volume = N;
        //  每件物品的父亲都需要指定，主件的parent的为0
        assert parent.length == cost.length;

        // 初始化树
        Arrays.fill(head, -1);
        final int root = 0; // 因为有多个根节点（主件），虚拟出一个统一的根节点0
        int idx = 0; // 边索引号
        for (int i = 1; i < cost.length; i++) {
            add(parent[i], i, idx++); // 顶点编号从1开始，预留虚拟根节点编号0
        }

        dfs(root);
        return dp[root][volume];
    }

    void dfs(int u) {
        // 要选u的任一子节点，必须先选 u，同时也限制了至少需要 cost[u]的 容量，即限制了只有金额大于主件价格才能购买
        for (int j = cost[u]; j <= volume ; j++) {
            dp[u][j] += cost[u] * priority[u];
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

    /**
     * 基于树形背包算法的时间复杂度是 O(cost.length * volume^2），对于此问题因为volume维度很大，造成时间复杂度很高
     *
     * 另一方面，物品维度的数量很小，不超过60， 且一个主件最多只能挂两个附件，可以枚举每个主件的选择方案：只选主件、选主件和一个附件、选主件和两个附件。
     * 如此一来，我们将问题彻底转化为「分组背包」问题：
     *   组内最多选择一件物品；
     *   当前组不选任何物品，对应原问题中不选该主件及其附件的情况；
     *   当前组选任意一件物品，对应原问题中选择主件以及选择/不选某些附件的情况。
     *
     * 而分组背包算法复杂度为 O(cost.length*volume)，可大幅提高算法性能。
     */
    public int maxValueOptimizedWithGroupPack(int[] parent, int[] cost, int[] priority, int N) {
        int volume = N;

        // 预处理出主件和附件的关系，例如 { 主件1 : [附件1, 附件2], 主件2 : [], 主件3 : [附件1] ... }
        Map<Integer, List<Integer>> masterMap = new HashMap<>();
        // 输入数据预留了虚拟根节点0
        for (int i = 1; i < cost.length; i++) {
            // 如果是主件
            if (parent[i] == 0) {
                if (!masterMap.containsKey(i)) {
                    masterMap.put(i, new ArrayList<>());
                }
            } else {
                List<Integer> list = masterMap.getOrDefault(parent[i], new ArrayList<>());
                list.add(i);
                masterMap.put(parent[i], list);
            }
        }

        // 构建物品组：list 装的每个物品组，例如 [ [只选主件1, 选主件1和一个附件，选主件1和两个附件], [[主件2]], [[主件3, 主件3和附件1]] ... ]
        List<List<int[]>> groups = new ArrayList<>();
        for (int root : masterMap.keySet()) {
            List<int[]> group = new ArrayList<>();
            int rootWorth = cost[root] * priority[root];
            // 不选任何附件：对应成本和价值
            group.add(new int[]{cost[root], rootWorth});

            // 最多2个附件
            List<Integer> attachments = masterMap.get(root);
            for (int i = 0; i < attachments.size(); i++) {
                int attachment = attachments.get(i);
                int attachWorth = cost[attachment] * priority[attachment];
                // 选择一件附件
                group.add(new int[]{cost[root] + cost[attachment], rootWorth + attachWorth});
                for (int j = i + 1; j < attachments.size(); j++) {
                    int attachment2 = attachments.get(i);
                    // 选择两件附件
                    group.add(new int[]{cost[root] + cost[attachment] + cost[attachment2],
                            rootWorth + attachWorth + cost[attachment2] * priority[attachment2]});
                }
            }
            groups.add(group);
        }

        // 分组背包一维空间优化：定义 dp[i][j] 为考虑前 i 件物品组，使用容量不超过 j 的最大价值
        int[] dp = new int[volume + 1];

        // 枚举物品组
        for (int i = 0; i < groups.size(); i++) {
            List<int[]> group = groups.get(i);
            // 枚举背包容量
            for (int j = volume; j >= 0; j--) {
                // 枚举决策（选哪个组内物品）
                for (int k = 0; k < group.size(); k++) {
                    int[] info = group.get(k);
                    int costi = info[0];
                    int worth = info[1];
                    if (j >= costi) {
                        dp[j] = Math.max(dp[j], dp[j - costi] + worth);
                    }
                }
            }
        }
        return dp[volume];
    }
}
