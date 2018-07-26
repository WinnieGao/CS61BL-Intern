import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Stack;
import java.util.HashSet;

public class Graph implements Iterable<Integer> {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    /* Adds a directed Edge (V1, V2) to the graph. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        Edge add = new Edge(v1, v2, weight);
        for (int i = 0; i < adjLists[v1].size(); i++) {
            Edge e1 = adjLists[v1].get(i);
            if (e1.getTo() == v2) {
                adjLists[v1].set(i, add);
          //      for (int j = 0; j < adjLists[v2].size(); j++) {
          //          Edge e2 = adjLists[v2].get(j);
          //          if (e2.getFrom() == v1) {
          //              adjLists[v2].set(j, add);
          //              return;
          //          }
          //      }
            }
        }
        adjLists[v1].add(add);
       // adjLists[v2].add(add);
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        addEdge(v1, v2, weight);
        addEdge(v2, v1, weight);
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        for (Edge e : adjLists[from]) {
            if (e.getTo() == to) {
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        LinkedList<Integer> ans = new LinkedList<>();
        for (Edge e : adjLists[v]) {
            if (e.getFrom() == v) {
                ans.add(e.getTo());
            }
        }
        return ans;
    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        int count = 0;
        for (Edge e : adjLists[v]) {
            if (e.getTo() == v) {
                count++;
            }
        }
        return count;
    }

    /* Returns an Iterator that outputs the vertices of the graph in topological
       sorted order. */
    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    /* A class that iterates through the vertices of this graph, starting with
       vertex START. If the iteration from START has no path from START to some
       vertex v, then the iteration will not include v. */
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        DFSIterator(int start) {
            fringe = new Stack<>();
            visited = new HashSet<>();
            fringe.push(start);
        }

        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        public Integer next() {
            int current = fringe.pop();
            visited.add(current);
            for (int i : neighbors(current)) {
                if (!visited.contains(i) && !fringe.contains(i)) {
                    fringe.push(i);
                }
            }
            return current;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /* Returns the collected result of performing a depth-first search on this
       graph's vertices starting from V. */
    public List<Integer> dfs(int v) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(v);

        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    /* Returns true iff there exists a path from START to STOP. Assumes both
       START and STOP are in this graph. If START == STOP, returns true. */
    public boolean pathExists(int start, int stop) {
        for (int i : dfs(start)) {
            if (i == stop) {
                return true;
            }
        }
        return false;
    }


    /* Returns the path from START to STOP. If no path exists, returns an empty
       List. If START == STOP, returns a List with START. */
    public List<Integer> path(int start, int stop) {
        LinkedList<Integer> ans = new LinkedList<>();
        List<Integer> dfsResult = dfs(start);
        int endIndex = -1;
        for (int i = 0; i < dfsResult.size(); i++) {
            if (dfsResult.get(i) == stop) {
                endIndex = i;
                break;
            }
        }
        if (endIndex == -1) {
            return ans;
        }
        for (int i = 0; i < endIndex + 1; i++) {
            ans.add(dfsResult.get(i));
        }
        return ans;
    }

    public List<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        int[] currentInDegree;

        TopologicalIterator() {
            fringe = new Stack<Integer>();
            currentInDegree = new int[vertexCount];
            for (int i = 0; i < vertexCount; i++) {
                currentInDegree[i] = inDegree(i);
                if (currentInDegree[i] == 0) {
                    fringe.push(i);
                }
            }
        }

        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        public Integer next() {
            int current = fringe.pop();
            for (int i : neighbors(current)) {
                currentInDegree[i]--;
                if (currentInDegree[i] == 0) {
                    fringe.push(i);
                }
            }
            return current;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private class Edge {

        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

    }

    private void generateG1() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG2() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void printDFS(int start) {
        System.out.println("DFS traversal starting at " + start);
        List<Integer> result = dfs(start);
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printPath(int start, int end) {
        System.out.println("Path from " + start + " to " + end);
        List<Integer> result = path(start, end);
        if (result.size() == 0) {
            System.out.println("No path from " + start + " to " + end);
            return;
        }
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printTopologicalSort() {
        System.out.println("Topological sort");
        List<Integer> result = topologicalSort();
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

    public static void main(String[] args) {
        Graph g1 = new Graph(5);
        g1.generateG1();
        g1.printDFS(0);
        g1.printDFS(2);
        g1.printDFS(3);
        g1.printDFS(4);

        g1.printPath(0, 3);
        g1.printPath(0, 4);
        g1.printPath(1, 3);
        g1.printPath(1, 4);
        g1.printPath(4, 0);

        Graph g2 = new Graph(5);
        g2.generateG2();
        g2.printTopologicalSort();
    }
}
