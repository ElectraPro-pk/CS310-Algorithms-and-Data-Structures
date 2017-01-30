/**
 * Created by Michael Hoang on 9/2/2014.
 * michael.hoang001@umb.edu
 * 781-397-9640
 */

public class LineUsageData{
    SinglyLinkedList<Usage> list = new SinglyLinkedList<Usage>();

    public void addObservation(String user){
        int n = 0;
        while(n < list.size()){
            if (list.get(n).name == user)
                break;
            n++;
        }

        if (list.get(n).name == user)
            list.get(n).count++;
        else {
            Usage newUse = new Usage(user, 1);
            list.add(newUse);
        }
    }

    public Usage findMaxUsage(){
        int maxCount = 0;
        int maxN = 0;
        if (list.size() == 0)
            return new Usage("<NONE>",0);

        int n = 0;
        while(n < list.size()){
            if( list.get(n).count > maxCount){
                maxCount = list.get(n).count;
                maxN = n;
            }
        }
        return list.get(n);
    }

}