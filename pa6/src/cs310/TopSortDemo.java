package cs310;

import cs310.visualization.VGraphWindow;
import cs310.visualization.VisualGraph;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

// top-level JGraphT classes: basic types, plus some helpers
//  use of JGraphT implementation classes
// use of cs310 wrapper of JGraph visualization support

/**
 * Show how a TopSort works, as it chooses vertices one by one.
 * Run this more than once, to see different random graphs.
 *
 */
public class TopSortDemo{
    private static final Color DEFAULT_BG_COLOR = Color.WHITE;
    private static final Dimension DISPLAY_SIZE = new Dimension(500, 500);

    /**
     * Create a JGraphT graph using a JGraphT graph generator,
     * @param graph empty JGraphT
     * @param n  number of vertices
     */
    public void buildGraph(Graph<Integer,DefaultEdge> graph, int n) {
        CompleteGraphGenerator<Integer, DefaultEdge> completeGenerator =
                new CompleteGraphGenerator<Integer, DefaultEdge>(n);
        // create a vertex generator for the graph generator: local class
        VertexFactory<Integer> vertexFactory = new VertexFactory<Integer>() {
            private int i = 0;
            public Integer createVertex() {
                return new Integer(++i);
            }
        };
        completeGenerator.generateGraph(graph, vertexFactory, null);
        Random r = new Random();
        // remove some edges to make a random graph
        int numEdges = graph.edgeSet().size();
        System.out.println("numEdges = "+ numEdges);
        for (int i = 0; i < 2*numEdges; i++) {  // change 2 to 3 or ... for other cases
            Integer from = r.nextInt(n) + 1;
            Integer to = r.nextInt(n) + 1;
            // System.out.println("removing edge from " + from + " to " + to);
            graph.removeEdge(from, to);
        }
    }

    void runDemo(DirectedGraph<Integer, DefaultEdge> g) {
        // graph of 10 vertices with randomly generated edges
        buildGraph(g, 5);

        // set up a Swing window with a visualization of this graph
        VGraphWindow<Integer, DefaultEdge> vWindow =
                new VGraphWindow<Integer, DefaultEdge>(g, "TopSort Demo",
                        DEFAULT_BG_COLOR, DISPLAY_SIZE);
        // a "VisualGraph" has a JGraph object constructed for the
        // JGraphT graph. The JGraphT package itself has no
        // visualizaton support, but the separate JGraph
        // package does, and there is support for
        // building one from the other.
        VisualGraph<Integer, DefaultEdge> visualGraph = vWindow.getVisualGraph();

        // set up the TopSort and the observer we need for notifications
        // of vertex visits and restarts of the TopSort
        TopSort<Integer, DefaultEdge> top = new TopSort<Integer, DefaultEdge>(g);
        // pass the visualGraph to the observer so it can re-color vertices
        TopObserver observer = new TopObserver(visualGraph);
        top.addObserver(observer);
        delay(1);

        // traverse the JGraphT graph: causes notifications via Observer
        try {
            top.getTopOrder(); // notifications cause calls to update below
        }catch (HasCycleException e) {
            System.out.println("Has a cycle!");
        }
        delay(2);  // let user study result before exit

    }

    /**
     * TopObserver: accept notifications of TopSort node visits for a TopSort
     * of a certain graph wrapped up for display.
     *
     */
    private static class TopObserver implements Observer {
        VisualGraph<Integer, DefaultEdge> visualGraph;
        int colorIndex = 0;

        TopObserver(VisualGraph<Integer, DefaultEdge> visualGraph) {
            this.visualGraph = visualGraph;
        }

        /**
         * Receive events from observed TopSort
         * arg1 is vertex reached by TopSort, or null if traversal
         * had to restart at another start-vertex
         */
        @Override
        public void update(Observable arg0, Object arg1) {
            Color colors[] = new Color[] { Color.BLACK, Color.BLUE, Color.CYAN,
                    Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED,
                    Color.MAGENTA };
            if (arg1 == null) { // had to restart traverse at another node
                // switch color
                colorIndex++;
                if (colorIndex >= colors.length) {
                    System.out.println(" Ran out of vertex colors, recycling them.");
                    colorIndex = 0;
                }
            } else {  // recolor the newly-visited vertex
                Integer vertex = (Integer) arg1;
                visualGraph.setVertexColor(vertex, colors[colorIndex]);
                delay(1);
            }
        }
    }

    private static void delay(int secs) {
        try {
            Thread.sleep(1000 * secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // our sample app using visual graphs, undirected and directed
        TopSortDemo demo = new TopSortDemo();

        // Now create a directed JGraphT graph
        // Note: its window will be on top of the first window
        // You can drag it off if you want.
        DirectedGraph<Integer, DefaultEdge> g2 =
                new SimpleDirectedGraph<Integer, DefaultEdge>(DefaultEdge.class);
        demo.runDemo(g2);
        //System.exit(0); // kill off Swing thread too
    }

}
