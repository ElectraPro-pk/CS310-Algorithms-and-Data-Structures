package cs310;

import org.jgrapht.graph.DefaultWeightedEdge;


/**
 *  The simplest possible object for a JGrapht weighted edge
 *  for cs310 use of JGrapht.
 *  Note that an edge doesn't know its connected vertices
 *  and won't reveal its weight,
 *  but the Graph knows all, so you can use g.getEdgeSource(e)
 *  for example to get the source vertex for the edge e,
 *  and g.getEdgeWeight(e) to get its weight.
 */
public class BasicWeightedEdge extends DefaultWeightedEdge {
	private static final long serialVersionUID = 1L;

	public String toString() { return "BasicWeightedEdge"; }  // nothing more to report
}
