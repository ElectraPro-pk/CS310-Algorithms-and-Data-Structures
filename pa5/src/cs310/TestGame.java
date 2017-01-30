package cs310;

import edu.umb.cs.game.*;

import java.util.Iterator;

/**
 * Minimal Game app
 *
 */
public class TestGame {
	public static void main(String[] args) {
		Game game = new Easy(); // a concrete Game

		System.out.println("Using game " + game.getName());

		try {
			game.init();
			PlayerNumber pn = game.whoseTurn();
			System.out.println("at game start, pn = " + pn);
			System.out.println("at game start, game state: " + game);

			Iterator<Move> moves = game.getMoves();
			while (moves.hasNext()) {
				Move m = moves.next();
				System.out.println("move: " + m);
				Game g1 = game.copy();
				g1.make(m);
				System.out.println("to game state: " + g1);
			}
		} catch (GameException e) {
			System.out.println(e);
		}
	}
}
