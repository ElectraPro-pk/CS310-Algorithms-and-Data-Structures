package cs310;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

// top-level JGraphT classes: basic types, plus some helpers
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.VertexFactory;

//  use of JGraphT implementation classes
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;

// use of cs310 wrapper of JGraph visualization support
import cs310.visualization.VGraphWindow;
import cs310.visualization.VisualGraph;

/**
 * Show how a DFS works, as it chooses vertices one by one.
 * Run this more than once, to see different random graphs.
 *
 */
public class DFSDemo {
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
	
	void runDemo(Graph<Integer, DefaultEdge> g) {
		// graph of 10 vertices with randomly generated edges
		buildGraph(g, 10);
		
		// set up a Swing window with a visualization of this graph
		VGraphWindow<Integer, DefaultEdge> vWindow = 
			new VGraphWindow<Integer, DefaultEdge>(g, "DFS Demo", 
						DEFAULT_BG_COLOR, DISPLAY_SIZE);
		// a "VisualGraph" has a JGraph object constructed for the 
		// JGraphT graph. The JGraphT package itself has no 
		// visualizaton support, but the separate JGraph 
		// package does, and there is support for
		// building one from the other.
		VisualGraph<Integer, DefaultEdge> visualGraph = vWindow.getVisualGraph();
			
		// set up the DFS and the observer we need for notifications
		// of vertex visits and restarts of the DFS
		DFS<Integer, DefaultEdge> dfs = new DFS<Integer, DefaultEdge>();
		// pass the visualGraph to the observer so it can re-color vertices
		DFSObserver observer = new DFSObserver(visualGraph);
		dfs.addObserver(observer);													
		delay(1);
		
		// traverse the JGraphT graph: causes notifications via Observer
		dfs.traverse(g); // notifications cause calls to update below
		delay(2);  // let user study result before exit

	}

	/**
	 * DFSObserver: accept notifications of DFS node visits for a DFS 
	 * of a certain graph wrapped up for display.
	 *
	 */
	private static class DFSObserver implements Observer {
		VisualGraph<Integer, DefaultEdge> visualGraph;
		int colorIndex = 0;

		DFSObserver(VisualGraph<Integer, DefaultEdge> visualGraph) {
			this.visualGraph = visualGraph;
		}
		
		/**
		 * Receive events from observed dfs
		 * arg1 is vertex reached by dfs, or null if traversal
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
		DFSDemo demo = new DFSDemo();
		// Create an undirected JGraphT graph
		UndirectedGraph<Integer, DefaultEdge> g1 = 
			new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
		// do a DFS on it
		demo.runDemo(g1);
		// Now create a directed JGraphT graph
		// Note: its window will be on top of the first window
		// You can drag it off if you want.
		DirectedGraph<Integer, DefaultEdge> g2 = 
			new SimpleDirectedGraph<Integer, DefaultEdge>(DefaultEdge.class);
		demo.runDemo(g2);
		//System.exit(0); // kill off Swing thread too
	}

}
