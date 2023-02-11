package avengers;
import java.util.HashMap;
import java.util.ArrayList;

public class Digraph {

    private HashMap<Integer, Integer> eventEU = new HashMap<>();
    private int[][] adjmat;
    private int events;
    private int EU;
    private int count = 1;
    private ArrayList<Integer> ret = new ArrayList<>();
    
    public Digraph (int EU, int events) {
        this.events = events;
        this.EU = EU;
        adjmat = new int[events][events];
    }

    public void adjmat (int i, int j, int input) {
        adjmat[i][j] = input;
    }

    public void evEU (int events, int euva) {
        eventEU.put(events, euva);
    }

    public int paths (int source) {
        for (int i = 0; i < events; i++) {
            if (adjmat[source][i] == 1) {
                count++;
                //System.out.println("adjmat[" + source + "][" + i + "] = " + adjmat[source][i]);
                paths(i);
            }
        }
        return count;
    }

    public void printAdjmat() {
        for (int i = 0; i < events; i++) {
            for (int j = 0; j < events; j++) {
                System.out.print(adjmat[i][j] + " " );
            }
            System.out.println();
        }

        System.out.println("events = " + events);
    }

    public ArrayList<Integer> pathEU (int source, int t1) {
        int temp = t1;
        if (source == 0) {
            ret.add(eventEU.get(source));
            temp = ret.get(source);
        }

        for (int i = 0; i < events; i++) {
            if (adjmat[source][i] == 1) {
                ret.add(temp + eventEU.get(i));
                t1 = ret.get(ret.size() - 1);
                pathEU(i, t1);
            }
        }

        return ret;
    }
}
