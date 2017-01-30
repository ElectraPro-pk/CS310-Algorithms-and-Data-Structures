package cs310;

import edu.umb.cs.game.Easy;
import edu.umb.cs.game.Game;
import edu.umb.cs.game.GameException;
import edu.umb.cs.game.Move;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/** Write a recursive method “explore(Game game, int level)” that (unless the game is over) tries all
 moves from game position game (see the loop in TestGame.java) and calling explore on them with level
 + 1. For each game states found, print out the “toString” report on the game state and the level, one
 state per line. Include the first 12 lines in your homework paper, as well as DisplayTree1.java. Note
 that it is hard to see the game tree from this output. Many of the states involve early resignations by a
 player, because one of the allowed moves (the first on the move-list) is “quit”, so there are many more
 leaves than you showed in your drawing.
 */
public class DisplayTree2 {
    static Move zeromove;
    static Map map = new HashMap<Game,Integer>();
    static int i = 0;
	public static void main(String[] args) {
		Game game = new Easy(); // a concrete Game

		System.out.println("Using game " + game.getName());

	    game.init();
        Iterator<Move> moves = game.getMoves();
        zeromove = moves.next();
        explore(game, 0);
	}

    private static void explore(Game game, int level){
        try {
            Iterator<Move> moves = game.getMoves();
            while (moves.hasNext()) {
                Move m = moves.next();
                Game g1 = game.copy();
                g1.make(m);
                System.out.print(game + "  \t| level " + level + "\t| move " + m + "\t| "+g1);
                if (!m.equals(zeromove)) {
                    int state;
                    if (!map.containsKey(g1.hashCode())){
                        state = i++;
                        map.put(g1.hashCode(),state);
                    }else
                        state = (Integer) map.get(g1.hashCode());

                    System.out.println("\t< G" + state);
                    explore(g1, level + 1);
                }else
                    System.out.println();
            }
        } catch (GameException e) {
            System.out.println(e);
        }
    }
    /**
     First 12 lines:

     Using game Easy
     [] [](1 next)  	| level 0	| move 0	| [] [](done)
     [] [](1 next)  	| level 0	| move 1	| [1] [](2 next)	< G0
     [1] [](2 next)  	| level 1	| move 0	| [1] [](done)
     [1] [](2 next)  	| level 1	| move 2	| [1] [2](1 next)	< G1
     [1] [2](1 next)  	| level 2	| move 0	| [1] [2](done)
     [1] [2](1 next)  	| level 2	| move 3	| [1, 3] [2](done)	< G2
     [1, 3] [2](done)  	| level 3	| move 0	| [1, 3] [2](done)
     [1] [](2 next)  	| level 1	| move 3	| [1] [3](1 next)	< G3
     [1] [3](1 next)  	| level 2	| move 0	| [1] [3](done)
     [1] [3](1 next)  	| level 2	| move 2	| [1, 2] [3](done)	< G4
     [1, 2] [3](done)  	| level 3	| move 0	| [1, 2] [3](done)
     [] [](1 next)  	| level 0	| move 2	| [2] [](2 next)	< G5
     */
}
