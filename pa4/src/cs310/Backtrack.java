package cs310;

// Backtrack.java
//
// Stub version--you fill in real implementation
// Ethan Bolker, February 2003

import edu.umb.cs.game.*;

import java.util.*;


public class Backtrack extends ComputerPlayer {
    PlayerNumber mynum;
    Node head;
	public Backtrack() {
		super("Backtrack Algorithm");
	}

	public Move findbest(Game g) throws GameException {
        head = new Node(g, null, null);
        mynum = g.whoseTurn();
        createTreeRecur(head);

        weighTreeRecur(head);

        Iterator<Node> itr = head.children.iterator();
        while (itr.hasNext()) {
            Node node1 = itr.next();
            if ((mynum == Game.FIRST_PLAYER && node1.weight == 1) || (mynum == Game.SECOND_PLAYER && node1.weight == -1))
                return node1.move;
        }
        System.out.println("Just guessing!");
        Iterator<Move> itr2 = g.getMoves();
        itr2.next();
        return itr2.next();
    }

    private void createTreeRecur(Node node) throws GameException{
        if (node.game.isGameOver())
            return;
        Iterator<Move> itr = node.game.getMoves();
        while (itr.hasNext()){
            Move m1 = itr.next();
            Game g1 = node.game.copy();
            g1.make(m1);
            Node node1 = new Node(g1,m1,node);
            node.children.add(node1);
            createTreeRecur(node1);
        }
    }

    private int weighTreeRecur(Node node){
        if (node.children.size() == 0){
            Game g = node.game;
            if (g.isGameOver()) {
                if (g.winner() == Game.FIRST_PLAYER) {
                    node.weight = 1;
                    return 1;
                } else if (g.winner() == Game.SECOND_PLAYER) {
                    node.weight = -1;
                    return -1;
                } else {
                    node.weight = 0;
                    return 0;
                }
            }
        }else {
            Iterator<Node> itr = node.children.iterator();
            int totalweight = 0;
            while (itr.hasNext()) {
                Node node1 = itr.next();
                totalweight += weighTreeRecur(node1);
            }
            if (totalweight > 0)
                totalweight = 1;
            else if (totalweight < 0)
                totalweight = -1;
            node.weight = totalweight;
            return totalweight;
        }
        return 0;
    }

    private class Node{
        Game game;
        Move move;
        List<Node> children = new LinkedList<Node>();
        Node parent;
        int weight;

        private Node(Game g, Move m, Node p){ game = g; move = m; parent = p; }
    }
}
