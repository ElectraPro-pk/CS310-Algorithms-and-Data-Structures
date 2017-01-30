package cs310;

// Backtrack.java
//
// Stub version--you fill in real implementation
// Ethan Bolker, February 2003

import edu.umb.cs.game.*;
import edu.umb.cs.io.Terminal;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Backtracking ComputerPlayer chooses the best move by recursively exploring
 * the entire Game tree below the current position.
 * 
 * That algorithm isn't implemented here yet - this version goes to the terminal
 * and asks the user to choose the computer's move.
 */

public class Backtrack extends ComputerPlayer {
	// Use interactive input to fake backtracking algorithm
	Terminal t = new Terminal();
    Queue<Game> q = new LinkedList<Game>();

	public Backtrack() {
		super("actually backtracking findbest");
	}

	/**
	 * This supposedly smart ComputerPlayer consulting his (interactive) oracle.
	 * 
	 * @param g
	 *            the current Game position.
	 * @return the best Move to make.
	 * @throws GameException.
	 */
	public Move findbest(Game g) throws GameException {
		q.offer(g);
        recur();
	}

    public void recur() throws GameException {
        GameStrings view = q.peek().getGameStrings();
        while (true) {
            try {
                String s = t.readLine("computer's move: ").trim();
                return view.parseMove(s);
            } catch (GameException e) {
                t.println(e.toString());
            }
        }
    }
}
