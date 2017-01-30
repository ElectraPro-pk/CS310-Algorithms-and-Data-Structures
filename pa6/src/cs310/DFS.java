package cs310;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;


/**
 * Carry out a depth first search on Graph g, directed or undirected,
 * reporting on visitations and restarts at new nodes via Observable
 * notifications.  Observer must call addObservable to register,
 * and will get notifications by calls to its update method.
 *
 * @param <V>
 * @param <E>
 */
public class DFS<V, E> extends Observable {

	public void traverse(Graph<V, E> g) {
		// start off with all vertices unvisited
		Set<V> unvisited = new HashSet<V>(g.vertexSet());

		while (!unvisited.isEmpty()) {
			setChanged();
			notifyObservers(null);
			traverse(g, unvisited.iterator().next(), unvisited);
		}
	}

	private void traverse(Graph<V, E> g, V n, Set<V> unvisited) {
		unvisited.remove(n);
		setChanged();
		notifyObservers(n);
		for (E e : g.edgesOf(n)) {
			// restrict attention to out-bound edges if a directed graph
			if (g instanceof UndirectedGraph || g.getEdgeSource(e).equals(n)) {
				V v = Graphs.getOppositeVertex(g, e, n);
				if (unvisited.contains(v)) {
					traverse(g, v, unvisited);
				}
			}
		}
	}
}


