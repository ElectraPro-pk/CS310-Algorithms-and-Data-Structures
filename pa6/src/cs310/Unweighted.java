package cs310;

import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.SimpleDirectedGraph;
// import org.jgrapht.graph.SimpleGraph;

/**
 * Find unweighted shortest paths in a graph, single-source. Graph can be a
 * directed or undirected JGraphT graph. Method unweighted is like Weiss, 
 * pg. 546(490), but using JGraphT. This method returns a Map with the distance
 * information for each node, rather than (in Weiss's version) leaving it in
 * special fields within the graph class.
 * Bug fixed Apr. '10, so it works for undirected as well as directed graphs
 */
public class Unweighted {
	/**
	 * Single-source unweighted shortest-path algorithm Single-source given by
	 * startVertex, within Graph g
	 * 
	 * @param <V>
	 * @param <E>
	 * @param g
	 * @param startVertex
	 * @return Map of V to Unweighted.DistInfo objects
	 */
	public static <V, E> Map<V, DistInfo> unweighted(Graph<V, E> g,
			V startVertex) {
		if (!g.containsVertex(startVertex))
			throw new NoSuchElementException();

		Map<V, DistInfo> distMap = new HashMap<V, DistInfo>();
		// set all vertices with dist = INFINITY, prev = null
		for (V v : g.vertexSet())
			distMap.put(v, new DistInfo());

		Queue<V> q = new LinkedList<V>();
		q.add(startVertex);

		DistInfo ds = distMap.get(startVertex);
		ds.dist = 0; // start Vertex has distance 0 from itself

		while (!q.isEmpty()) {
			V v = q.remove(); // dequeue v
			DistInfo dv = distMap.get(v);
			for (E e : g.edgesOf(v)) {
				System.out.println("v " + v + " e:" + e + "...");
				if (g instanceof UndirectedGraph
						|| g.getEdgeSource(e).equals(v)) {
					// V w = g.getEdgeTarget(e); was BUG: this doesn't work for UndirectedGraph
					V w = Graphs.getOppositeVertex(g, e, v);  // fixed
					DistInfo dw = distMap.get(w);
					System.out.print("w " + w + " D " + dw.dist);
					if (dw.dist == DistInfo.INFINITY) {
						System.out.print(" put d");
						dw.dist = dv.dist + 1;
						dw.prev = v;
						q.add(w); // enqueue w
					}
				}
				System.out.println();
			}
		}
		return distMap;
	}

	// Simple class for dist and prev for each Vertex
	// This is a public class used to help pass information
	// back to the client, as well as support the computation.
	// Note that only the outer class can access the private
	// fields directly.
	static public class DistInfo {
		public final static int INFINITY = Integer.MAX_VALUE;

		private int dist = INFINITY;
		private Object prev = null;

		public int getDist() {
			return dist;
		}

		public Object getPrev() {
			return prev;
		}

		public String toString() {
			return "" + (dist == INFINITY ? "INF" : "" + dist) + " (prev = "
					+ (prev == null ? "null" : prev) + ")";
		}
	}

	// added after jgrapht.zip provided, S10
	public static void main(String[] args) {
		String inFile = "test.dat"; // default name
		if (args.length == 1)
			inFile = args[0];

		Graph<String, BasicEdge> dg = 
			new SimpleDirectedGraph<String, BasicEdge>(BasicEdge.class); // or use SimpleGraph here
		if (LoadGraph.loadGraph(inFile, dg, false)) {
			String start = dg.vertexSet().iterator().next();
			Map<String, DistInfo> m = Unweighted.unweighted(dg, start);
			System.out.println("unweighted distances for nodes:");
			for (String v : m.keySet()) {
				DistInfo d = m.get(v);
				System.out.println(v + " " + d);
			}
		}
	}

}
