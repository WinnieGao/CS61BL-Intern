public class UnionFind {

    private int[] id;

    /* Creates a UnionFind data structure holding N vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int N) {
        id = new int[N];
        for (int i = 0; i < N; i += 1) {
            id[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        int result = id[v];
        while (result > 0) {
            result = id[result];
        }
        return -result;
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return id[v];
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    public void validate(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid vertices are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        validate(v);
        int root = v;
        while (id[root] > 0) {
            root = id[root];
        }
        while (v != root) {
            int newv = id[v];
            id[v] = root;
            v = newv;
        }
        return root;
    }

    /* Connects two elements V1 and V2 together. V1 and V2 can be any element,
       and a union-by-size heuristic is used. If the sizes of the sets are
       equal, tie break by connecting V1's root to V2's root. Union-ing a vertex
       with itself or vertices that are already connected should not change the
       structure. */
    public void union(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);
        if (sizeOf(root1) < sizeOf(root2)) {
            id[root1] = root2;
            id[root2] += id[root1];
        } else {
            id[root2] = root1;
            id[root1] += id[root2];
        }
    }
}
