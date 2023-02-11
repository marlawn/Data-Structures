package avengers;
import java.util.ArrayList;

public class UndirectedGraph {
    private final int V;
    private ArrayList<Integer>[] adjacencyList;

    public UndirectedGraph(int V) {
        this.V = V;
        for (int i = 0; i < V; i++) {
            adjacencyList[i] = new ArrayList<Integer>();
        }
    }

    public void addEdge(int v, int w) {
        adjacencyList[v].add(w);
        adjacencyList[w].add(v);
    }

    public Iterable<Integer> adj(int v) {
        return adjacencyList[v];
    }

    public int V() {
        return V;
    }
}
