package org.example;
//So it the main problem i face building this parser, so the comment will be longer than others.
//DSU disjoint set union, thanks stackoverflow for this
//So it manages grouping or clustering, u can choose the word u want
//There r some key responsibilities: 1. quick check if 2 lines are in the same group 2. Merges 2 groups, idk but all ppl call it union, i also ask ai so it true to call it onion)
//3. keeps track group sizes for optimization
public class UnionFind {
    private final int[] parent;
    private final int[] size;
    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }
    public void union(int x, int y) {
        int px = find(x);
        int py = find(y);
        if (px == py) return;
        if (size[px] < size[py]) {
            parent[px] = py;
            size[py] += size[px];
        } else {
            parent[py] = px;
            size[px] += size[py];
        }
    }
    public int getGroupSize(int x) {
        return size[find(x)];
    }
}

