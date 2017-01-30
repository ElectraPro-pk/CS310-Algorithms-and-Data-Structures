package cs310;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.AbstractGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;

import java.util.*;

/**
 * Implement the JGraphT Graph interface for a simple undirected graph, 
 * like JGraphT SimpleGraph, but simpler.
 * TODO: pa5: implement removeVertex, removeEdge
 * 
 * @author Betty O'Neil
 * @version Spring 2009
 * 
 */

public class SimpleGraph1<V, E> extends AbstractGraph<V, E> {
	private Map<V, Set<E>> vertexList; // vertices and info about them
	private Map<E, InternalEdge> edgeList; // edges and info about them
	private EdgeFactory<V, E> edgeFactory;
	private static final String LOOPS_NOT_ALLOWED = "loops not allowed";

	/**
	 * Graph constructor.
	 */
	public SimpleGraph1(Class<? extends E> edgeClass) {
		edgeFactory = new ClassBasedEdgeFactory<V, E>(edgeClass);
		vertexList = new HashMap<V, Set<E>>();
		edgeList = new HashMap<E, InternalEdge>();
	}

	@Override
	public E addEdge(V sourceVertex, V targetVertex) {
		assertVertexExist(sourceVertex);
		assertVertexExist(targetVertex);

		if (containsEdge(sourceVertex, targetVertex)) {
			return null;
		}

		if (sourceVertex.equals(targetVertex)) {
			throw new IllegalArgumentException(LOOPS_NOT_ALLOWED);
		}
		E edge = edgeFactory.createEdge(sourceVertex, targetVertex);
		if (!addEdge(sourceVertex, targetVertex, edge))
			return null;
		return edge;
	}

	@Override
	public boolean addEdge(V sourceVertex, V targetVertex, E e) {
		if (e == null) {
			throw new NullPointerException();
		} else if (containsEdge(e)) {
			return false;
		}

		assertVertexExist(sourceVertex);
		assertVertexExist(targetVertex);

		if (containsEdge(sourceVertex, targetVertex)) {
			return false;
		}

		if (sourceVertex.equals(targetVertex)) {
			throw new IllegalArgumentException(LOOPS_NOT_ALLOWED);
		}

		Set<E> fromEdges = vertexList.get(sourceVertex);
		fromEdges.add(e);
		Set<E> toEdges = vertexList.get(targetVertex);
		toEdges.add(e);

		edgeList.put(e, new InternalEdge(sourceVertex, targetVertex));

		return true;
	}

	@Override
	public boolean addVertex(V v) {
		if (v == null) {
			throw new NullPointerException();
		} else if (containsVertex(v)) {
			return false;
		} else {
			vertexList.put(v, new HashSet<E>());
			return true;
		}
	}

	@Override
	public boolean containsEdge(E e) {
		return edgeList.containsKey(e);
	}

	@Override
	public boolean containsVertex(V v) {

		return vertexList.containsKey(v);
	}

	@Override
	public Set<E> edgeSet() {
		return Collections.unmodifiableSet(edgeList.keySet());
	}

	@Override
	public Set<E> edgesOf(V vertex) {
		return Collections.unmodifiableSet(vertexList.get(vertex));
	}

	@Override
	public Set<E> getAllEdges(V sourceVertex, V targetVertex) {
		Set<E> edges = null;

		if (containsVertex(sourceVertex) && containsVertex(targetVertex)) {
			edges = new HashSet<E>((vertexList.get(sourceVertex)));

			edges.retainAll(vertexList.get(targetVertex));
		}

		return edges;
	}

	// For an undirected graph, an edge can be represented
	// by A --> B or A <-- B in terms of sourceVertex and targetVertex
	// so return edges with targetVertex at either end
	@Override
	public E getEdge(V sourceVertex, V targetVertex) {
		if (containsVertex(sourceVertex) && containsVertex(targetVertex)
				&& !sourceVertex.equals(targetVertex)) // no loops in simple graph
			for (E edge : vertexList.get(sourceVertex))
				if (getEdgeTarget(edge).equals(targetVertex)
						|| getEdgeSource(edge).equals(targetVertex))
					return edge;
		return null;
	}
	
	@Override
	public EdgeFactory<V, E> getEdgeFactory() {
		return edgeFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V getEdgeSource(E e) {

		return (V) edgeList.get(e).from;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V getEdgeTarget(E e) {
		return (V) edgeList.get(e).to;
	}

	@Override
	public double getEdgeWeight(E e) {
		return 1;
	}

	@Override
	public E removeEdge(V sourceVertex, V targetVertex) {
        Set<E> outVertSet = vertexList.get(sourceVertex);

        E edge = null;
        for (E e:outVertSet){
            edge = e;
            if (edgeList.get(e).to.equals(targetVertex))
                break;
        }
        edgeList.remove(edge);
        outVertSet.remove(edge);
		return edge;
	}

	@SuppressWarnings("unchecked")
    @Override
	public boolean removeEdge(E e) {
		InternalEdge edge = edgeList.get(e);
        edgeList.remove(e);
        Set<E> set = vertexList.get((V) edge.from);
        set.remove(e);
		return true;
	}

	@Override
	public boolean removeVertex(V v) {
        Set<E> edgeset = vertexList.get(v);
        ArrayList<E> edgelist = new ArrayList<E>();
        for (E e : edgeset)
            edgelist.add(e);
        for (int i=0; i<edgelist.size(); i++)
            removeEdge(edgelist.get(i));

        vertexList.remove(v);
		return true;
	}

	@Override
	public Set<V> vertexSet() {
		return Collections.unmodifiableSet(vertexList.keySet());
	}

	/**
	 * The InternalEdge knows about the vertices it's connecting, unlike the E
	 * instances. Each E instance has an associated InternalEdge obtainable via
	 * edgeList.  This class corresponds to JGraphT IntrusiveEdge. Like IntrusiveEdge,
	 * it uses Object references rather than using generic <V,E>.
	 * 
	 */
	private static class InternalEdge {
		public Object from;
		public Object to;

		public InternalEdge(Object from, Object to) {
			this.from = from;
			this.to = to;
		}
	}
}
