package cs310;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.*;

public class TopSortTest {
    public static void test(String inFile, DirectedGraph<String, BasicEdge> g) {
        if (!LoadGraph.loadGraph(inFile, g, false))
            return;  // file not found

        TopSort<String, BasicEdge> topSort = new TopSort<String, BasicEdge>(g);
        List<String> list = new LinkedList<String>();

        TopObserver observer = new TopObserver();
        topSort.addObserver(observer);
        try {
            list = topSort.getTopOrder();
        }catch (HasCycleException e) {
            System.out.println("Has a cycle!");
        }
    }

    private static class TopObserver implements Observer {
        /**
         * Receive events from observed dfs: arg1 is vertex reached by dfs, or
         * null if traversal had to restart at another start-vertex
         */
        @Override
        public void update(Observable arg0, Object arg1) {
            if (arg1 == null) { // had to restart traverse at another node
                System.out.print("|");
            } else {
                System.out.print(" " + arg1 + " ");
            }
        }
    }

    public static void main(String[] args) {
        String inFile = "test.dat"; // default name
        if (args.length == 1)
            inFile = args[0];
        DirectedGraph<String, BasicEdge> g = new SimpleDirectedGraph<String, BasicEdge>(
                BasicEdge.class);
        test(inFile, g);
    }
}
