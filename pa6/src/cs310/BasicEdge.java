package cs310;


/**
 *  The simplest possible object for a JGrapht edge
 *  for cs310 use of JGrapht.
 *  Note that an edge doesn't know its connected vertices
 *  but the Graph does, so you can use g.getEdgeSource(e)
 *  for example to get the source vertex for the edge e.
 *  In general, JGrapht edges can be of any class, but do need 
 *  the edge objects to be unique for each edge, so that a Map 
 *  can be made from edges to more info.
 */
public class BasicEdge {
	public String toString() { return "BasicEdge"; }  // nothing more to report
}
