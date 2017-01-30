package cs310;

import java.io.*;
import java.util.*;
/**
 * Created by Michael Hoang on 9/24/2014.
 */

//  cd cs310/pa1/classes
//  javac -d . ../src/cs310/*.java
//  java -cp . cs310.SpellChecker <testinput

public class SpellChecker {
    public static void main(String[] args) throws FileNotFoundException {
        HashSet<String> dictionary = new HashSet<String>();
        Scanner scan = new Scanner(new File("customwords"));
        while (scan.hasNextLine())
            dictionary.add(scan.nextLine());
        scan = new Scanner(new File("words"));
        while (scan.hasNextLine())
            dictionary.add(scan.nextLine());

        HashMap<String, ArrayList<Integer>> wrongMap = new HashMap<String, ArrayList<Integer>>();
        Scanner input = new Scanner(System.in);
        int lineNum = 1;
        while (input.hasNextLine()) { //continue to end of file
            String line = input.nextLine();
            Scanner scanLine = new Scanner(line);
            while (scanLine.hasNext()) { //continue to end of line
                String word = scanLine.next().toLowerCase();

                if (!dictionary.contains(word)) {
                    if (!wrongMap.containsKey(word))
                        wrongMap.put(word, new ArrayList<Integer>());
                    wrongMap.get(word).add(lineNum);
                }
            }
            lineNum++;
        }

        for (Map.Entry<String, ArrayList<Integer>> entry : wrongMap.entrySet()) {
            StringBuilder output = new StringBuilder();
            output.append(entry.getKey() + "\t is incorrect on lines: ");
            for (int i = 0; i < entry.getValue().size(); i++)
                output.append(entry.getValue().get(i) + ","); //add line number


            /*  a. Add one character
                b. Remove one character
                c. Exchange adjacent characters.
            */
            output.append("\t and the suggested correction is: ");
            StringBuilder word = new StringBuilder(entry.getKey());
            int i = 0;
            boolean keepGoing = true;
            while (i < word.length()-1 && keepGoing){ //swap characters
                char temp = word.charAt(i);
                word.setCharAt(i,word.charAt(i+1));
                word.setCharAt(i+1,temp);
                if (dictionary.contains(word.toString()))
                    keepGoing = false;
                else
                    word.replace(0,word.length(),entry.getKey()); //if not a match, undo the change
                i++;
            }
            i = 0;
            while (i < word.length() && keepGoing){ //remove one character
                word.deleteCharAt(i);
                if (dictionary.contains(word.toString()))
                    keepGoing = false;
                else
                    word.replace(0,word.length(),entry.getKey()); //if not a match, undo the change
                i++;
            }
            i = 0;
            while (i <= word.length() && keepGoing){ //add one character
                char newChar = 'a';
                while (newChar <= 'z' && keepGoing) {
                    word.insert(i,newChar);
                    if (dictionary.contains(word.toString()))
                        keepGoing = false;
                    else
                        word.replace(0, word.length(), entry.getKey()); //if not a match, undo the change
                    newChar++;
                }
                i++;
            }
            if (keepGoing)
                word.replace(0,word.length(),"<NONE>");
            output.append(word.toString());
            System.out.println(output.toString());
        }
    }
}
