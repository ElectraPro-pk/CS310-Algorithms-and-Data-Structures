import java.util.Scanner;

/**
 * Created by Michael Hoang on 9/2/2014.
 */
public class TermReport {
    LineUsageData[] array = new LineUsageData[501];

    public void main(String[] args){
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()){
            String line = scan.nextLine();
            int split = line.indexOf(" ");
            int terminal = Integer.parseInt(line.substring(0,split));
            String name = line.substring(split+1);

            array[terminal].addObservation(name);
        }

        System.out.println("Line\tCommon User\tCount");
        for (int i=1; i<=500; i++){
            Usage max = array[i].findMaxUsage();
            System.out.println(i+"\t"+max.name+"\t"+max.count);
        }
    }
}
