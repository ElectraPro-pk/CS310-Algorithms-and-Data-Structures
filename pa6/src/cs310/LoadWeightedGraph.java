package cs310;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 * Creating and using weighted graphs defined in files.
 * 
 * It constructs two graphs with this information, one directed and one
 * undirected, and then performs a breadth first search on each, 
 * using the BFS iterator of JGraphT.
 * 
 * Note that weighted JGraphT graphs need to have an edge type that 
 * satisfies edgetype instanceof BasicWeightedEdge, e.g. 
 * BasicWeightedEdge (as used here). 
 * This is a different approach from unweighted JGraphT edges, 
 * that can be of any class (but do need the edge objects to be
 * unique for each edge, so that a Map can be made from edges to 
 * more info).
 * 
 */
public class LoadWeightedGraph {

	/**
	 * Load a graph (directed or undirected) from a file. 
	 * It expects lines of the form "A B w" which it interprets 
	 * as a request to construct an edge from A to B with weight w. 
	 * It uses Strings A and B as vertices and uses 
	 * BasicWeightedEdge type edges (see note above.)
	 * 
	 * @param filename
	 * @param g  a WeightedGraph, so we can use setEdgeWeight
	 */
	public static boolean loadGraph(String filename,
			WeightedGraph<String, BasicWeightedEdge> g) {

		Scanner in;
		try {
			in = new Scanner(new File(filename));
		} catch (FileNotFoundException e1) {
			System.err.println("File not found: " + filename);
			return false;
		}

		while (in.hasNextLine()) {
			String line = in.nextLine();
			System.out.println(line);
			String[] tokens = line.split("\\s+"); // split on whitespace
			if (tokens.length < 2) { // line too short
				if (tokens.length == 1) // allow empty lines
					System.out.println(" Skipping bad line: " + line);
				continue;
			}
			String fromKey = tokens[0];
			String toKey = tokens[1];
			double weight = Double.parseDouble(tokens[2]);
			g.addVertex(fromKey);
			g.addVertex(toKey);
			// undirected edge--goes both ways, directed edge--one way
			g.addEdge(fromKey, toKey);
			BasicWeightedEdge e1 = g.getEdge(fromKey, toKey);
			g.setEdgeWeight(e1, weight);
			// check from
			Set<BasicWeightedEdge> edges = g.getAllEdges(fromKey, toKey);
			for (BasicWeightedEdge e : edges) {
				// can't get source, target from e, but can via g--
				System.out.println(" edge: source = "
						+ g.getEdgeSource(e) + ", target = "
						+ g.getEdgeTarget(e));
			}
		}
		System.out.println("Graph: " + g);
		return true;
	}

	public static void shortestPath(Graph<String, BasicWeightedEdge> g) {
		System.out.println("JGraphT Dijkstra for graph of type " + g.getClass());
		Iterator<String> itr = g.vertexSet().iterator();
		String vertex0 = itr.next();
		while (itr.hasNext()) {
			String v = itr.next();
			List<BasicWeightedEdge> path = DijkstraShortestPath
					.findPathBetween(g, vertex0, v);
			System.out.println("Dijkstra path for " + vertex0 + " to " + v + ":");
			if (path != null) {
				for (BasicWeightedEdge e : path) {
					System.out.print(", edge " + g.getEdgeSource(e) + "-" + g.getEdgeTarget(e) + " weight " + g.getEdgeWeight(e));
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		String inFile = "test.dat"; // default name
		if (args.length == 1)
			inFile = args[0];
		// We need to use the WeightedGraph type to be able to load it with weights
		// (i.e., use WeightedGraph.setEdgeWeight)
		// but once loaded, can pass it around as just Graph, because the Graph API
		// has getEdgeWeight. 
		WeightedGraph<String, BasicWeightedEdge> g = new SimpleWeightedGraph<String, BasicWeightedEdge>(
				BasicWeightedEdge.class);
		if (loadGraph(inFile, g))
			shortestPath(g);

		WeightedGraph<String, BasicWeightedEdge> dg = new SimpleDirectedWeightedGraph<String, BasicWeightedEdge>(
				BasicWeightedEdge.class);
		if (loadGraph(inFile, dg))
			shortestPath(dg);
	}
}
