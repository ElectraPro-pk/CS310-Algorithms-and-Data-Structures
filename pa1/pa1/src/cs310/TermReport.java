package cs310;
import java.util.Scanner;

public class TermReport {
	public static final int NLINES = 500;

	public static void main(String[] args) {
		// Create an array of 500 SinglyLinkedLists
		// since there are NLINES = 500 individual lines,
		// numbered 1 to 500, so use array size NLINES+1

		LineUsageData[] lines = new LineUsageData[NLINES + 1];
		for (int i = 1; i <= NLINES; i++)
			lines[i] = new LineUsageData();

		// Get all information through System.in
		// Could use BufferedReader, like Weiss, pg. 54, but starting with
		// Java 5 we can use more convenient Scanner class:
		Scanner in = new Scanner(System.in);

		String line, user;
		int lineNumber;
		// For reading a text data file, with one record on each line,
		// it's best to read the lines separately and then analyze them:
		while (in.hasNextLine()) {
			line = in.nextLine();
			// Easier than StringTokenizer: String.split(regex)
			// We want whitespace delimited tokens, so we use the
			// regex (regular expression) for a whitespace character, \s
			// but we have to escape the \ in the string, so "\\s".
			// To allow possibly multiple whitespace chars, we add +
			// ending up with "\\s+"
			// Alternatively, we could use another Scanner, new Scanner(line),
			// which has aq built-in default whitespace delimiter
			String[] tokens = line.split("\\s+"); // whitespace-delimited
			if (tokens.length < 2) {
				System.err.println("Error in data format, line = " + line
						+ " , continuing");
			} else {
				System.out.println("tokens: " + tokens[0] + "  " + tokens[1]);
				lineNumber = Integer.parseInt(tokens[0]);
				user = tokens[1];
				// Place data in proper LineUsageData element of array.
				lines[lineNumber].addObservation(user);
			}
		}

		// All done reading data, now print out records.
		System.out.println("Line\tMost Common User\tCount");

		// Loop through array and print out the most common user
		// and number of logins.
		for (int i = 1; i <= NLINES; i++) {
			Usage record = lines[i].findMaxUsage();
			System.out.println(i + "\t" + record.getUser() + "\t"
					+ record.getCount());
		}
	}
}
