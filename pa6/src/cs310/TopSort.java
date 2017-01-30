package cs310;

import org.jgrapht.DirectedGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Michael Hoang on 12/2/2014.
 */
public class TopSort<V,E> extends Observable{
    DirectedGraph<V,E> graph;
    public TopSort(DirectedGraph<V, E> graph){this.graph = graph;}

    public List<V> getTopOrder() throws HasCycleException{
        List<V> sortedlist = new LinkedList<V>();
        List<V> noincoming = new LinkedList<V>();

        for(V node: graph.vertexSet()){
            if (graph.inDegreeOf(node) == 0)
                noincoming.add(node);
        }

        while (!noincoming.isEmpty()){
            V node = noincoming.remove(0);
            sortedlist.add(node);
            setChanged();
            notifyObservers(node);

            ArrayList<E> edgeList = new ArrayList<E>();
            for (E e:graph.outgoingEdgesOf(node))
                edgeList.add(e);

            for(E edge: edgeList){
                V nextnode = graph.getEdgeTarget(edge);
                graph.removeEdge(edge);
                if (graph.inDegreeOf(nextnode) == 0)
                    noincoming.add(nextnode);
            }
        }


        if (graph.edgeSet().size() > 0)
            throw new HasCycleException();
        else
            return sortedlist;
    }
}

