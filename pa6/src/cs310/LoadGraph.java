package cs310;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

/**
 * Creating and using graphs defined in files
 * 
 * It constructs two graphs with this information, one directed and one
 * undirected, and then performs a breadth first search on each, using the BFS
 * iterator of JGraphT
 * 
 * @author Ethan Bolker
 * @version Spring 2002 changed to use JGraphT, spring 09
 * Changed to use BasicEdge, spring 10, because String edge type, used before with
 * graph constructors of form constructor(String.class), leads to graphs that
 * cannot create multiple new edges needed for multiple addEdge(v,v) calls.
 * Note: be sure to use jgrapht.graph.BasicEdge, not jgraph.graph.BasicEdge
 */
public class LoadGraph {

	/**
	 * Load a graph (directed or undirected) from a file. It expects lines of
	 * the form "A B" which it interprets as a request to construct an edge from
	 * A to B. It uses Strings A and B as vertices, and a BasicEdge object for the edge
	 * <p>
	 * 
	 * @param filename
	 * @param g
	 */
	public static boolean loadGraph(String filename, Graph<String, BasicEdge> g,
			boolean verbose) {

		Scanner in;
		try {
			in = new Scanner(new File(filename));
		} catch (FileNotFoundException e1) {
			System.err.println("File not found: " + filename);
			return false;
		}

		while (in.hasNextLine()) {
			String line = in.nextLine();
			if (verbose)
				System.out.println(line);
			String[] tokens = line.split("\\s+"); // split on whitespace
			if (tokens.length < 2) { // line too short
				if (tokens.length == 1) // allow empty lines
					System.out.println(" Skipping bad line: " + line);
				continue;
			}
			String fromKey = tokens[0];
			String toKey = tokens[1];
			g.addVertex(fromKey);
			g.addVertex(toKey);
	
			// undirected edge--goes both ways, directed edge--one way
			g.addEdge(fromKey, toKey);
			// check from
			Set<BasicEdge> edges = g.getAllEdges(fromKey, toKey);
			if (verbose)
				for (BasicEdge e : edges) {
					// can't get source, target from e, but can via g--
					System.out.println(" edge:" 
							+ " source = " + g.getEdgeSource(e)
							+ ", target = " + g.getEdgeTarget(e));
				}
		}
		if (verbose)
			System.out.println("Graph: " + g);
		return true;
	}

	public static void traverse(Graph<String, BasicEdge> g) {
		System.out.println("\nBFS traversal on graph of type " + g.getClass());
		BreadthFirstIterator<String, BasicEdge> itr = new BreadthFirstIterator<String, BasicEdge>(
				g);
		while (itr.hasNext()) {
			String s = itr.next();
			System.out.println("BFS step: " + s);
		}
	}

	public static void main(String[] args) {
		String inFile = "test.dat"; // default name
		if (args.length == 1)
			inFile = args[0];

		UndirectedGraph<String, BasicEdge> g = 
			new SimpleGraph<String, BasicEdge>(BasicEdge.class);
		if (loadGraph(inFile, g, true)) {
			// show off the one method special to UndirectedGraph: degreeOf
			for (String v : g.vertexSet())
				System.out.println("vertex " + v + ": degree = "
						+ g.degreeOf(v));
			traverse(g);
		}

		DirectedGraph<String, BasicEdge> dg = new SimpleDirectedGraph<String, BasicEdge>(
				BasicEdge.class);
		if (loadGraph(inFile, dg, true)) {
			// show off one of the methods special to DirectedGraph:
			// inDegreeOf. DirectedGraph also has outDegreeOf, and
			// ways to get the Set<E>s in and out
			for (String v : dg.vertexSet())
				System.out.println("vertex " + v + ": inDegree = "
						+ dg.inDegreeOf(v));
			traverse(dg);
		}
	}
}
