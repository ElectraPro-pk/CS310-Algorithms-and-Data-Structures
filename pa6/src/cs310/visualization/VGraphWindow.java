package cs310.visualization;

import java.awt.Color;
import java.awt.Dimension;

import org.jgraph.JGraph;
import org.jgrapht.Graph;

/**
 * Set up a Swing window and a VisualGraph for a given JGraphT
 * graph, and display it. The vertices will be displayed around
 * a circle, and the edges between. The client can go on to edit the 
 * VisualGraph via its API, if desired.
 * 
 * The end user can move the vertices around on the screen.
 * 
 * @author eoneil
 *
 * @param <V> vertex type
 * @param <E> edge type
 */
public class VGraphWindow<V, E> {
	
	private VisualGraph<V, E> visualGraph;
	private VFrame vFrame;

	public VGraphWindow(Graph<V, E> g, String title, Color bgColor, Dimension size) {

		// Set up the visual graph and show it
		// The VFrame has the Swing JFrame for the window
		// in which we can show visual graph(s) on the screen
		vFrame = new VFrame();
		// Create a visual graph for the JGraphT graph, in the window
		visualGraph = new VisualGraph<V, E>(g, bgColor, size);
		JGraph jgraph = visualGraph.getJGraph();
		// add the jgraph, which ISA Swing JComponent, to the 
		// Swing JPanel in vFrame, i.e. supply the graph pic
		// to the otherwise very plain Swing setup of vFrame
		vFrame.addComponent(jgraph);

		// position vertices in a circle
		visualGraph.positionGraphElements();
		// VisualGraph is set up, show it:
		vFrame.createWindow(title + " ("+ g.getClass()+")");
	}
	
	public VFrame getVFrame() {
		return vFrame;
	}

	public VisualGraph<V, E> getVisualGraph() {
		return visualGraph;
	}
}
