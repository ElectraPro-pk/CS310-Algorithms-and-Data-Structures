package cs310;

import org.jgrapht.DirectedGraph;

import java.util.*;

/**
* Created by Michael Hoang on 12/4/2014.
*/
public class Dijkstra<V,E> extends Observable {
    DirectedGraph<V,E> g;
    Map<V,Map<V,Node>> dijkstraMap = new HashMap<V,Map<V,Node>>();
    public Dijkstra(DirectedGraph<V, E> g){
        this.g = g;
    }

    public void dijkstra(V start){
        if (dijkstraMap.containsKey(start))
            return;

        Map<V, Node> map = new HashMap<V, Node>();
        Queue<Node> queue = new LinkedList<Node>();
        Node startnode = new Node(start);
        queue.add(startnode);
        map.put(start, startnode);
        while (!queue.isEmpty()){
            Node node = queue.poll();
            for (E e : g.outgoingEdgesOf(node.v)){
                V vnext = g.getEdgeTarget(e);
                double totalWeight = node.weight + g.getEdgeWeight(e);
                Node nextnode = new Node(vnext);
                nextnode.prev = node;
                nextnode.weight = totalWeight;
                if (!map.containsKey(vnext) || map.get(vnext).weight > totalWeight)
                    map.put(vnext,nextnode);
                queue.add(nextnode);
            }
        }
        for (V v : g.vertexSet())
            if (!map.containsKey(v)) {
                Node outsidenode = new Node(v);
                outsidenode.weight = Double.POSITIVE_INFINITY;
                map.put(v, outsidenode);
            }

        dijkstraMap.put(start,map);
    }

    public List<V> pathTo(V start, V target){
        List<V> list = new LinkedList<V>();

        Node node = dijkstraMap.get(start).get(target);
        list.add(target);
        while (node.prev != null){
            node = node.prev;
            list.add(0,node.v);
        }
        return list;
    }

    public double pathToWeight(V start, V target){
        dijkstra(start);
        return dijkstraMap.get(start).get(target).weight;
    }

    private class Node{
        V v;
        Node prev = null;
        double weight = 0;
        private Node(V v){ this.v = v; }
    }
}
