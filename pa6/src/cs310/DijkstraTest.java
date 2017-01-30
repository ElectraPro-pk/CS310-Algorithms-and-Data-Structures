package cs310;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.LinkedList;
import java.util.List;

public class DijkstraTest {
    public static void test(String inFile, DirectedGraph<String, BasicEdge> g) {
        if (!LoadGraph.loadGraph(inFile, g, false))
            return;  // file not found

        Dijkstra<String, BasicEdge> dijkstra = new Dijkstra<String, BasicEdge>(g);
        List<String> list = new LinkedList<String>();

        String first = null;
        for (String v : g.vertexSet()){
            if (first == null)
                first = v;

            double weight = dijkstra.pathToWeight(first,v);
            List<String> path = dijkstra.pathTo(first,v);
            System.out.println(v+" "+weight+" "+path);
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