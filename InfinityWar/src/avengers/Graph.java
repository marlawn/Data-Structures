package avengers;
import java.util.ArrayList;

public class Graph {

    private int[][] adjmat;
    private boolean[] visited;
    private int poop;

    public Graph(int n) {
        adjmat = new int[n][n];
        this.poop = n;
        visited = new boolean[n];
    }

    public void add(int i, int j, int value) {
        adjmat[i][j] = value;
    }

    public void remove(int i) {
        for (int j = 0; j < adjmat.length; j++) {
            adjmat[i][j] = 0;
            adjmat[j][i] = 0;
        }
    }

    public void print() {
        for (int i = 0; i < adjmat.length; i++) {
            for (int j = 0; j < adjmat[0].length; j++) {
                System.out.print(adjmat[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void dfs(int source, int n) {
        visited[source] = true;
        ArrayList<Integer> AV = new ArrayList<>();

        for (int i = 0; i < adjmat.length; i++) {
            if (adjmat[source][i] == 1) {
                AV.add(i);
            }
        }

        for (int i = 0; i < AV.size(); i++) {
            if (visited[AV.get(i)] == false) {
                dfs(AV.get(i), poop);
            }
        }

    }

    public void printDFS() {
        for (int i = 0; i < visited.length; i++) {
            System.out.println(visited[i] + " ");
        }
    }

    public boolean[] getVisited() {
        return visited;
    }

}