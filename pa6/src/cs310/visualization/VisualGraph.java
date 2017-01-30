
package cs310.visualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.Set;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphModelAdapter;

/**
 * An API for accessing the jgraph visual graph. For simple use,
 * use VGraphWindow to create a VisualGraph and position vertices
 * in a circle, then call here for positionVertex, get/set vertexColor
 * to change it to your specs.
 * 
 * A layer over JGraphModelAdapter that makes it easier to use
 * (i.e. a "facade") and allows us to use it without "unchecked"
 * warnings.
 *
 * @author Betty O'Neil
 * @since Jan. 2, 2009
 */
public class VisualGraph<V,E>
{
    private JGraphModelAdapter<V,E> jgAdapter;

	private JGraph jgraph;
    private Graph<V,E> graph;  // the graph we're displaying
    private Dimension size;

    public VisualGraph(Graph<V,E> g, Color bgColor, Dimension size)
    {     
    	graph = g;  // the basic JGraphT graph we start from
    	this.size = size;
        // create a visualization using JGraph, via an adapter
        jgAdapter = new JGraphModelAdapter<V, E>(g);

        jgraph = new JGraph(jgAdapter);
       
        jgraph.setPreferredSize(size);
        jgraph.setBackground(bgColor);
    }
    
    // Package visibility to allow the needed call from VGraphWindow
    // to add this JGraph, which ISA JComponent, to the JPanel
    // VGraphWindow is setting up for the client, but seal off direct
    // calls from the clients of VisualGraph, which should be using
    // the API of VisualGraph, or for advanced use, the jgAdapter,
    // but not the underlying jgraph.
    JGraph getJGraph() {   
        return jgraph;
    }
    
    public Graph<V,E> getGraph() {   
        return graph;
    }
    
    // In case you want other facilities of the jgraph adaptor.
    // Probably not.
    public JGraphModelAdapter<V, E> getJgAdapter() {
		return jgAdapter;
	}
    
	/**
	 * @return  Dimension of graph pic, i.e., width and height of
	 * graph area on screen
	 */
	public Dimension getSize() {return size;}

    /**
     * Set the vertex's position in the window
     * 
     * @param vertex
     * @param x
     * @param y
     */
    @SuppressWarnings("unchecked") 
    public void positionVertexAt(V vertex, int x, int y)
    {
        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D bounds = GraphConstants.getBounds(attr);

        Rectangle2D newBounds =
            new Rectangle2D.Double(
                x,
                y,
                bounds.getWidth(),
                bounds.getHeight());

        GraphConstants.setBounds(attr, newBounds);
        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        jgAdapter.edit(cellAttr, null, null, null);
    }
    
    /**
     * Get vertex's x position in the window
     * 
     * @param vertex
     * @return x position
     */
    public int getVertexXPosition(V vertex)
    {
        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D bounds = GraphConstants.getBounds(attr);

       return (int)bounds.getX();
    }
    /**
     * Get vertex's y position in the window
     * 
     * @param vertex
     * @return y position (down from top)
     */
    public int getVertexYPosition(V vertex)
    {
        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D bounds = GraphConstants.getBounds(attr);

       return (int)bounds.getY();
    }
    /**
     * Get vertex color
     * 
     * @param vertex
     * @return color
     */
    public Color getVertexColor(V vertex)
    {
        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        
        Color bgColor = GraphConstants.getBackground(attr);
        return bgColor;
     }
    
    /**
     * Set vertex color
     * 
     * @param vertex
     * @param color
     */
    @SuppressWarnings("unchecked")
    public void setVertexColor(V vertex, Color color)
    {
        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();

        GraphConstants.setBackground(attr, color);
        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        jgAdapter.edit(cellAttr, null, null, null);
     }

	/**
	 * By default, all the vertices are piled up in the same place
	 * Given a visualGraph, arrange its vertices evenly around a circle
	 * of radius = .40* window height, centered at (radius, radius).
	 * Caller can always rearrange them later.
	 * 
	 * Also, an end user can rearrange them on the display.
	 * 
	 */
	public void positionGraphElements() {
		Set<V> vSet = graph.vertexSet();
		int n = graph.vertexSet().size();
		double radius = 0.40*(double)size.getHeight();

		int[][] coords = new int[n][2];
		for (int i = 0; i < n; i++) {
			// angle for each = 1/n'th of full circle
			double theta = i * (2 * Math.PI / n);
			coords[i][0] = (int) (radius + radius * Math.sin(theta));
			coords[i][1] = (int) (radius + radius * Math.cos(theta));
		}
		int i = 0;
		for (V v : vSet) {
			positionVertexAt(v, coords[i][0], coords[i][1]);
			int x = getVertexXPosition(v);
			if (x == coords[i][0])
				System.out.println("OK");
			else System.out.println("XXX");
			i++;
			setVertexColor(v, Color.GRAY);
		}
	}
}

