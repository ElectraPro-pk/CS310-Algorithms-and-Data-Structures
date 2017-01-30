CS310 Graph Package Implementation Notes
Betty O'Neil, May, 2012

Only the files under src/cs310 differ from the original sources. Some
original source directories have been dropped, however, to save disk
space.  As of Spring, 2012, we are still using the v 0.8.0 JGraphT sources
even though more recent ones (0.8.3)are available. We have never had a bug
reported due to these JGraphT sources or the visual library jgraph.jar,
so there is no strong incentive to upgrade.  

The files under src/cs310 have been changed during Spring '12 to use
BasicEdge for edges rather than the JGraphT DefaultEdge, because the
use of DefaultEdge caused confusion when its toString displayed the
edge's source and target vertices even though the API expresses the
model that an edge does not know its source and target vertices.
For cs310 students just learning about the power of APIs to express
models, we should ensure that the important object toStrings don't leak 
protected information.  Also, this edge toString stopped working for their 
own Graph implementation (SimpleGraph1) because the edges no longer had 
access to this protected information.

Instead of editing JGraphT's DefaultEdge to change toString, I set up
a trivial class cs310.BasicEdge to use instead.  This way it is completely
obvious that an edge doesn't know its vertices, and shows that the client
can easily define the edge type.

BasicWeightedEdge isn't quite as simple since code in JGraphT's AbstractBaseGraph
checks the edge type, so BasicWeightedEdge extends DefaultWeightedEdge.

I've also cleaned up the code in cs310.visualization