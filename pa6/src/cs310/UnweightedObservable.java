package cs310;

import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;

/**
 * Slight modification of Unweighted, to add notifications
 * of vertices added to the queue, for demo.
 *
 */
public class UnweightedObservable extends Observable {
	/**
	 * Single-source unweighted shortest-path algorithm
	 * Single-source given by startVertex, within Graph g
	 * objects
	 * @param <V>
	 * @param <E>
	 * @param g
	 * @param startVertex
	 * @return Map of V to UnweightedObservable.DistInfo 
	 * Bug fixed Apr. '10, so it works for undirected as well as directed graphs
	 */
	public <V,E> Map<V, DistInfo> unweighted(Graph<V, E> g,
			V startVertex) {
		if (!g.containsVertex(startVertex))
			throw new NoSuchElementException();

		Map<V, DistInfo> distMap = new HashMap<V, DistInfo>();
		// set all vertices with dist = INFINITY, prev = null
		for (V v : g.vertexSet())
			distMap.put(v, new DistInfo(v));

		Queue<V> q = new LinkedList<V>();
		q.add(startVertex);

		DistInfo ds = distMap.get(startVertex);
		ds.dist = 0; // start Vertex has distance 0 from itself
		setChanged();
		notifyObservers(ds); // send off ds, for start vertex
		
		while (!q.isEmpty()) {
			System.out.println(q);
			V v = q.remove(); // dequeue v
			DistInfo dv = distMap.get(v);
			for (E e : g.edgesOf(v)) {
				if (g instanceof UndirectedGraph || g.getEdgeSource(e).equals(v)) {
					// V w = g.getEdgeTarget(e); was BUG: this doesn't work for UndirectedGraph
					V w = Graphs.getOppositeVertex(g, e, v);  // fixed
					DistInfo dw = distMap.get(w);
					if (dw.dist == DistInfo.INFINITY) {
						dw.dist = dv.dist + 1;
						dw.prev = v;
						setChanged();
						notifyObservers(dw); // send off dw
						q.add(w); // enqueue w
					}
				}
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
		private Object current = null;  // for Observer info
		
		public DistInfo(Object cur) {
			current = cur;
		}

		public int getDist() {
			return dist;
		}

		public Object getPrev() {
			return prev;
		}
		public Object getCurrent() {
			return current;
		}

		public String toString() {
			return "" + (dist == INFINITY ? "INF" : "" + dist) + " (prev = "
					+ (prev == null ? "null" : prev) + ")";
		}
	}
}
