// Grid.java, for pa3
// originally by Scott Madin
package cs310;

import java.util.*;

/*  You will have to write only the groupSize method and a helper functions.

    Here’s one idea: set up a local Set of Spots to hold spots you’ve found so far in the cluster.
    From the current spot-position, greedily add as many direct neighbor spots as possible.
    For each neighbor spot that is actually newly-found, call recursively from that position.

    See attached MakeChange.java for an example of a recursion helper method – you need a recursion helper here to fill the set.

    Once the set is filled with spots in the group, the top-level method just returns its size.

    Usage: “java cs310.Grid 3 7” for example, to search from (3,7), the top occupied square of the L-shaped
    group. This case should print out “4”
*/

/**
 * Class to demonstrate greedy algorithms
 */
public class Grid {
    private boolean[][] grid = null;

    /**
     * Very simple constructor
     *
     * @param ingrid
     *            a two-dimensional array of boolean to be used as the grid to
     *            search
     */
    public Grid(boolean[][] ingrid) {
        grid = ingrid;
    }

    /**
     * Main method, creates a Grid, then asks it for the size of the group
     * containing the given point.
     */
    public static void main(String[] args) {
        int i = 0;
        int j = 0;

        // Make sure we've got the right number of arguments
        if (args.length != 2) {
            System.err.println("Incorrect arguments.");
            printUsage();
            return;
        } else {
            i = Integer.parseInt(args[0]);
            j = Integer.parseInt(args[1]);
        }

        // Usage: java Grid 3 7 to search from (3, 7), top occupied square
        // of L-shaped group of Figure 7.30, pg. 281

        boolean[][] gridData = {
                { false, false, false, false, false, false, false, false, false, true  },
                { false, false, false, true,  true,  false, false, false, false, true  },
                { false, false, false, false, false, false, false, false, false, false },
                { false, false, false, false, true,  false, false, true,  false, false },
                { false, false, false, true,  false, false, false, true,  false, false },
                { false, false, false, false, false, false, false, true,  true,  false },
                { false, false, false, false, true,  true,  false, false, false, false },
                { false, false, false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false, false, false } };

        Grid mygrid = new Grid(gridData);

        int size = mygrid.groupSize(i, j);
        System.out.println("Group size: " + size);
    }

    /**
     * Prints out a usage message
     */
    private static void printUsage() {
        System.out.println("Usage: java Grid <i> <j>");
        System.out
                .println("Find the size of the cluster of spots including position i,j");
    }

    /**
     * This calls the recursive method to find the group size
     *
     * @param i
     *            the first index into grid (i.e. the row number)
     * @param j
     *            the second index into grid (i.e. the col number)
     * @return the size of the group
     */
    HashSet<Spot> set;
    public int groupSize(int i, int j) {
        // Implement this function which, among other calls a helper recursive function
        // to find the group size.
        set = new HashSet<Spot>();
        groupRecur(i,j);
        return set.size();
    }

    public void groupRecur(int i, int j) {
        attemptRecur(i-1,j);
        attemptRecur(i+1,j);
        attemptRecur(i,j-1);
        attemptRecur(i,j+1);
    }

    public void attemptRecur(int i, int j){
        if (i>0 && i<10 && j>0 && j<10 && grid[i][j] == true){
            Spot newspot = new Spot(i,j);
            if (!set.contains(newspot)){
                set.add(newspot);
                groupRecur(i,j);
            }
        }
    }


    /**
     * Nested class to represent a filled spot in the grid
     */
    private static class Spot {
        int i;
        int j;

        /**
         * Constructor for a Spot
         *
         * @param i
         *            the i-coordinate of this Spot
         * @param j
         *            the j-coordinate of this Spot
         */
        public Spot(int i, int j) {
            this.i = i;
            this.j = j;
        }

        /**
         * Tests whether this Spot is equal (i.e. has the same coordinates) to
         * another
         *
         * @param o
         *            an Object
         * @return true if o is a Spot with the same coordinates
         */
        public boolean equals(Object o) {
            if (o == null)
                return false;
            if (o.getClass() != getClass())
                return false;
            Spot other = (Spot) o;
            return (other.i == i) && (other.j == j);

        }

        /**
         * Returns an int based on Spot's contents
         * another way: (new Integer(i)).hashCode()^(new Integer(j)).hashCode();
         */
        public int hashCode() {
            return i << 16 + j; // combine i and j two halves of int
        }

        /**
         * Returns a String representing this Spot
         */
        public String toString() {
            return "(" + i + " , " + j + ")";
        }
    }
}