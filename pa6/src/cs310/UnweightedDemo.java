package cs310;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.SimpleDirectedGraph;
import cs310.LoadGraph;
import cs310.visualization.VGraphWindow;
import cs310.visualization.VisualGraph;
import cs310.UnweightedObservable.DistInfo;

/**
 * Show progress of Unweighted by coloring vertices by discovered
 * distance from the startVertex: hotter colors for closer vertices
 *
 */
public class UnweightedDemo implements Observer {
	private static final Color DEFAULT_BG_COLOR = Color.WHITE;
	private static final Dimension DISPLAY_SIZE = new Dimension(500, 500);
	VisualGraph<String, BasicEdge> visualGraph = null;

	public void runDemo(String inFile) {
		DirectedGraph<String, BasicEdge> dg = new SimpleDirectedGraph<String, BasicEdge>(
				BasicEdge.class);
		if (LoadGraph.loadGraph(inFile, dg, false)) {

			System.out.println("===========================");
			System.out.println("g = " + dg);
			System.out.println("===========================");
			// set up a Swing window with a visualization of this graph
			VGraphWindow<String, BasicEdge> vWindow = 
				new VGraphWindow<String, BasicEdge>(dg, "Unweighted Demo", 
							DEFAULT_BG_COLOR, DISPLAY_SIZE);
			// a "VisualGraph" has a JGraph object constructed for the 
			// JGraphT graph. The JGraphT package itself has no 
			// visualizaton support, but the separate JGraph 
			// package does, and there is support for
			// building one from the other
			visualGraph = vWindow.getVisualGraph();

			String start = dg.vertexSet().iterator().next();
			UnweightedObservable alg = new UnweightedObservable();
			alg.addObserver(this);
			Map<String, DistInfo> m = alg.unweighted(dg, start);
			System.out.println("unweighted distances for nodes:");
			for (String v : m.keySet()) {
				DistInfo d = m.get(v);
				System.out.println(v + " " + d);
			}
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		DistInfo info = (DistInfo) arg1;
		System.out.println("Saw DistInfo: v = " + info.getCurrent()
				+ " prev = " + info.getPrev() + " dist = " + info.getDist());
		Color colors[] = new Color[] { Color.RED, Color.ORANGE, Color.YELLOW,
				Color.GREEN, Color.BLUE, Color.MAGENTA, Color.BLACK };
		// recolor the newly-visited vertex
		// hotter colors for closer vertices
		visualGraph.setVertexColor((String) info.getCurrent(), colors[info
				.getDist()]);
		delay(1);

	}

	private static void delay(int secs) {
		try {
			Thread.sleep(1000 * secs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		String inFile = "test.dat"; // default name
		if (args.length == 1)
			inFile = args[0];

		UnweightedDemo demo = new UnweightedDemo();
		demo.runDemo(inFile);
	}


}
