
package cs310;


import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

import java.util.Set;

/**
 * Testing simple undirected graphs (with String vertices, BasicEdge edges)
 *  
 */
public class SimpleGraphTest {

	public static void main(String[] args) {
		String inFile = "test.dat"; // default name
		if (args.length == 1)
			inFile = args[0];
		
		// JGraphT SimpleGraph--
		Graph<String, BasicEdge> g1 = new SimpleGraph<String, BasicEdge>(BasicEdge.class);
		LoadGraph.loadGraph(inFile, g1, true);
		// our SimpleGraph--
		Graph<String, BasicEdge> g2 = new SimpleGraph1<String, BasicEdge>(BasicEdge.class);
		LoadGraph.loadGraph(inFile, g2, true);
		System.out.println(g1.getClass() + ": " +g1);
		System.out.println(g2.getClass() + ": " +g2);

        System.out.println("== Removing Vertex B ==");
        g1.removeVertex("B");
        g2.removeVertex("B");
        System.out.println(g1.getClass() + ": " +g1);
        System.out.println(g2.getClass() + ": " +g2);

        System.out.println("== Removing Edge Y-Z ==");
        g1.removeEdge("Y","Z");
        g2.removeEdge("Y","Z");
        System.out.println(g1.getClass() + ": " +g1);
        System.out.println(g2.getClass() + ": " +g2);

		Set<String> v1 = g1.vertexSet();
		Set<String> v2 = g2.vertexSet();
		// Since String.equals tests content, we can check these Sets easily: 
		// Set equals means element matching by equals
		if (v1.equals(v2))
			System.out.println("vertex sets match");
		else System.out.println("vertex sets differ");
		
		// The following doesn't print, because both JGraphT and our SimpleGraph1 
		// fail to override equals, and the default Object equals returns false
		// unless the g1 and g2 references are equal.
		if (g1.equals(g2))
			System.out.println("whole graphs match by equals");
		
	}
	
}
