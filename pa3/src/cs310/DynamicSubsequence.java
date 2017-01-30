package cs310;

import java.util.*;

@SuppressWarnings("ALL")
public class DynamicSubsequence {
    int[] testdata = {0,8,4,12,2,10,6,14,1,9,5,13,3,11,7,15};
    ArrayList<LinkedList<Integer>> maximums = new ArrayList<LinkedList<Integer>>();
    LinkedList<Integer> nums = new LinkedList<Integer>();
    LinkedList<Integer> subsequence = new LinkedList<Integer>();

    /*  for each number in testdata, I keep a linked list of all the numbers that
        are both larger than it, and to the right of it. I've named this count to
        be a number's "maximum". The best subsequence will be created by:
            1. Find the number with the highest "maximum". The best subsequence starts here.
            2. Of the list of all its "maximums", find the number that has the highest maximum.
            3. Repeat until we have looped throughout the entirety of the test data.
     */
    public static void main(String args[]){
        DynamicSubsequence sub = new DynamicSubsequence();
        sub.start();
    }

    public void start(){
        initializeMaximums();
        //one-time setup to convert the testdata into a linked list
        for (int data : testdata)
            nums.add(data);

        boolean keepLooping = true;
        while (keepLooping){
            //System.out.println("nums is "+nums.toString());
            //we have a list, find the element with the most maximums
            int mostMaximums = sizeOfMaxs(0);
            for (int i=0; i<nums.size(); i++) {
                if (sizeOfMaxs(i) > sizeOfMaxs(mostMaximums))
                    mostMaximums = i;
            }

            //we have the element with the most maximums. this will be in the best subsequence.
            subsequence.add(nums.get(mostMaximums));

            //we have the element, now we get ready to loop again, but use its list
            //if the list is empty, that means we've reached the end
            if (sizeOfMaxs(mostMaximums) <= 0)
                keepLooping = false;
            else
                nums = maximums.get(nums.get(mostMaximums));
        }

        //we now have the completed subsequence! let's print it and finish this.
        for (int i=0; i<subsequence.size(); i++)
             System.out.print(subsequence.get(i) + ", ");
        System.out.println();
    }

    /*  the entire time, we must compare how many maximums a certain element has.
        we always have a list of elements, so we get the actual element,
        use that to look up its corresponding list in the 'maximums' ArrayList,
        then compare its size.
    */
    private int sizeOfMaxs(int i){ return maximums.get(nums.get(i)).size(); }

    private void initializeMaximums(){
        for (int i=0; i<testdata.length; i++) {
            maximums.add(i, new LinkedList<Integer>());
            for (int j=i; j<testdata.length; j++)
                if (testdata[i] < testdata[j])
                    maximums.get(i).add(testdata[j]);
        }
    }
}