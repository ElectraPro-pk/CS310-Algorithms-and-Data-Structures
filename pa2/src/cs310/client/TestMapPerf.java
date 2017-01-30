package cs310.client;

//  cd cs310/pa2/classes
//  javac -d . ../src/cs310/util/*.java
//  javac -d . ../src/cs310/client/*.java
//  java -cp . cs310.client.TestMapPerf 10000 words

import cs310.util.HashMap1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TestMapPerf {
    Map<Integer, String> map;
    static int nAmount = 10000;

    public static void main(String[] args) throws FileNotFoundException{
        nAmount = Integer.parseInt(args[0]);
        List<String> wordList = new LinkedList<String>();
        Scanner scan = new Scanner(new File(args[1]));
        while (scan.hasNextLine())
            wordList.add(scan.nextLine());

        TestMapPerf test;

        test = new TestMapPerf( new HashMap<Integer,String>()); // java.util.HashMap
        test.timeTest(wordList);

        test = new TestMapPerf( new HashMap1<Integer,String>()); //cs310.util.HashMap1
        test.timeTest(wordList);

        test = new TestMapPerf( new TreeMap<Integer,String>()); //java.util.TreeMap
        test.timeTest(wordList);
    }

    public TestMapPerf(Map<Integer, String> map){ this.map = map; }

    @SuppressWarnings({"UnusedAssignment", "UnusedDeclaration"})
    private void timeTest(List<String> wordList){
        String useless;
        Date dateBefore;
        Date dateAfter;

        long timeElapsed;
        double msPerGet;

        System.out.println("Testing "+map.getClass()+" for all three cases.");

        for(int caseItr=0;caseItr<=2;caseItr++) {
            map.clear();
            List n = randomizeN(caseItr);
            for (int i = 0; i < n.size(); i++)
                map.put( i, wordList.get((Integer) n.get(i)) );

            dateBefore = new Date();
            for (int i = 0; i < n.size(); i++)
                useless = map.get(i);
            dateAfter = new Date();

            timeElapsed = dateAfter.getTime() - dateBefore.getTime();
            msPerGet = ((double) timeElapsed / n.size()) * 1000;
            System.out.println("- Case " + caseItr + " took " + timeElapsed +
                    " milliseconds to do " + n.size() + " amount of gets, which is " +
                    msPerGet + " microseconds per get.");
        }
    }

    @SuppressWarnings("unchecked")
    private List randomizeN(int caseNum){
        List n = new LinkedList<Integer>();
        Random rand = new Random();
        switch (caseNum) {
            case 0:{
                for (int i=0; i<(nAmount/4); i++)
                    n.add(rand.nextInt(nAmount));
                break;
            }
            case 1:{
                for (int i=0; i<(nAmount/2); i++)
                    n.add(rand.nextInt(nAmount));
                break;
            }
            case 2:{
                for (int i=0; i<nAmount; i++)
                    n.add(rand.nextInt(nAmount));
                break;
            }
        }
        return n;
    }

}
