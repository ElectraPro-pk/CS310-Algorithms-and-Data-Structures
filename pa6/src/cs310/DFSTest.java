package cs310;

import java.util.Observable;
import java.util.Observer;

import org.jgrapht.Graph;
import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;

import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;

/**
 * Test DFS without using Swing visualization.
 *
 */
public class DFSTest {
	public static void test(String inFile, Graph<String, BasicEdge> g) {
		if (!LoadGraph.loadGraph(inFile, g, false))
			return;  // file not found
		DFS<String, BasicEdge> dfs = new DFS<String, BasicEdge>();
		DFSObserver observer = new DFSObserver();
		dfs.addObserver(observer);
		dfs.traverse(g); // notifies events, causing calls to update below
	}

	private static class DFSObserver implements Observer {
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
		System.out.println("Undirected graph:");
		UndirectedGraph<String, BasicEdge> g1 = new SimpleGraph<String, BasicEdge>(
				BasicEdge.class);
		test(inFile, g1);
		System.out.println("\nDirected graph:");
		DirectedGraph<String, BasicEdge> g2 = new SimpleDirectedGraph<String, BasicEdge>(
				BasicEdge.class);
		test(inFile, g2);
	}
}
