
package cs310.visualization;

import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Wrapper for a Swing JFrame/JPanel sufficient to show JGraphT 
 * graphs using the jgraph package. See VisualGraph.java for
 * top-level information.
 * 
 * This little Swing setup could be used to display any Swing
 * graphics delivered in a JComponent, as JGraph is.  Supplying
 * the graphics as a JComponent has the advantage that then
 * the graphics can be used not only in a JFrame as done here, 
 * but also in a JApplet.  JGraph is a "software component"
 * rather than a complete app in itself.
 * 
 * @author eoneil
 *
 */
public class VFrame {

	private JPanel panel; // Swing JPanel that holds visual graphs
	private JFrame frame;  // JPanel's frame
	
	public VFrame() {
		panel = new JPanel();
	}
	
	// This is how we add the JGraph, which ISA JComponent
	public void addComponent(JComponent component) {
		panel.add(component);
	}
	
	public void createWindow(String title) {
		final String windowTitle = title;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			
				frame = new JFrame();
				frame.getContentPane().add(panel);
				frame.setTitle(windowTitle);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	public void redoWindow(String title) {
		final String windowTitle = title;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				frame.setTitle(windowTitle);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	public void runTask(Runnable task) {
		EventQueue.invokeLater(task);
	}
}

