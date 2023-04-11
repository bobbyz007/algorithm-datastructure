package bz.unionfind;

/**
 * 并查集
 */
public class UnionFind {
    // 节点的父节点
    private int[] p;
    private int ringCnt;

    public UnionFind(int n) {
        ringCnt = n; // 默认每个节点自成一环
        p = new int[n];
        // 初始化指向自身
        for (int i = 0; i < n; i++) {
            p[i] = i;
        }
    }

    public void union(int a, int b) {
        // a的根节点 指向 b的根节点
        int roota = find(a);
        int rootb = find(b);
        if (roota != rootb) {
            p[roota] = p[rootb];
            ringCnt--;
        }
    }

    public int find(int x) {
        if (p[x] != x) {
            /**
             * 实现了路径压缩，例如：
             *        4
             *      3
             *    2
             *  1
             * 当find(1)的时候，路径压缩为
             *         4
             *     1   2   3
             */
            p[x] = find(p[x]);
        }
        return p[x];
    }

    // 元素p和元素q是否同属一个集合
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    public int ringCnt() {
        int cnt = 0;
        for (int i = 0; i < p.length; i++) {
            if (i == find(i)) {
                cnt++;
            }
        }
        return cnt;
    }

    public int ringCntOptimized() {
        return ringCnt;
    }
}
