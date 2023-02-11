package avengers;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0),
 * modify the edge weights using the functionality values of the vertices that
 * each edge
 * connects, and then determine the minimum cost to reach Titan (vertex n-1)
 * from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 * 1. g (int): number of generators (vertices in the graph)
 * 2. g lines, each with 2 values, (int) generator number, (double) funcionality
 * value
 * 3. g lines, each with g (int) edge values, referring to the energy cost to
 * travel from
 * one generator to another
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel
 * from one
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by
 * DIVIDING it
 * by the functionality of BOTH vertices (generators) that the edge points to.
 * Then,
 * typecast this number to an integer (this is done to avoid precision errors).
 * The result
 * is an adjacency matrix representing the TOTAL COSTS to travel from one
 * generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and
 * Titan.
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 * To read from a file use StdIn:
 * StdIn.setFile(inputfilename);
 * StdIn.readInt();
 * StdIn.readDouble();
 * 
 * To write to a file use StdOut (here, minCost represents the minimum cost to
 * travel from Earth to Titan):
 * StdOut.setFile(outputfilename);
 * StdOut.print(minCost);
 * 
 * Compiling and executing:
 * 1. Make sure you are in the ../InfinityWar directory
 * 2. javac -d bin src/avengers/*.java
 * 3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {

    public static void main(String[] args) {

        if (args.length < 2) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

        String locateTitanInputFile = args[0];
        String locateTitanOutputFile = args[1];
        StdIn.setFile(locateTitanInputFile);
        StdOut.setFile(locateTitanOutputFile);

        int n = StdIn.readInt(); // number of generators (nodes)
                                 // (n-1)th generator is TITAN

        // stores generator number and functionality value to a hashmap
        HashMap<Integer, Double> genFunc = new HashMap<>();
        for (int i = 0; i < n; i++) {
            genFunc.put(StdIn.readInt(), StdIn.readDouble());
        }

        // stores adjacency matrix values
        int[][] adjmat = new int[n][n]; // TOTAL COST WITH FUNCTIONALITY INCLUDED
        for (int i = 0; i < n; i++) {
            double t1 = genFunc.get(i); // gets the functionality of i (row)
            for (int j = 0; j < n; j++) {
                double t2 = genFunc.get(j); // gets the functionality of j (col)
                int t3 = StdIn.readInt(); // gets the adjacency matrix value at [i,j]
                int val = (int) (t3 / (t1 * t2));
                adjmat[i][j] = val;
            }
        }

        // MAKE A GRAPH FIRST

        // use dijkstra algorithm to find minimum cost
        int[] minCost = new int[n];
        boolean[] dijkstraSet = new boolean[n];

        for (int i = 0; i < minCost.length; i++) {
            if (i == 0)
                minCost[i] = 0;
            else
                minCost[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < n - 1; i++) {
            int min = Integer.MAX_VALUE, min_index = -1;
            for (int v = 0; v < n; v++) {
                if (dijkstraSet[v] == false && minCost[v] <= min) {
                    min = minCost[v];
                    min_index = v;
                }
            }
            int currentSource = min_index;
            dijkstraSet[currentSource] = true;
            for (int j = 0; j < n; j++) {
                if (adjmat[currentSource][j] > 0 && minCost[currentSource] != Integer.MAX_VALUE
                        && dijkstraSet[j] == false
                        && (minCost[j] > (minCost[currentSource] + adjmat[currentSource][j]))) {
                    minCost[j] = minCost[currentSource] + adjmat[currentSource][j];
                }
            }

        }

        StdOut.print(minCost[n-1]);
    }
}
