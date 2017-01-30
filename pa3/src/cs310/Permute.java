package cs310;

import java.util.*;

/**
 * Created by Michael Hoang on 10/22/2014.
 */
@SuppressWarnings("UnusedDeclaration")
public class Permute {
    static Set<String> set;

    public void main(String args[]){
        set = permute(args[0]);
        System.out.println(set.toString());
    }
    public static Set<String> permute(String str){
        set = new HashSet<String>();
        permuteLoop(str, "");
        return set;
    }

    public static void permuteLoop(String prefix, String str){
        if (str.length() == 0)
                set.add(prefix);
        else{
            StringBuffer buffer;
            for (int i = 0; i < prefix.length(); i++){
                buffer = new StringBuffer(prefix);
                String newPrefix = prefix+buffer.charAt(i);
                buffer.deleteCharAt(i);
                permuteLoop(newPrefix, buffer.toString());
            }
        }
    }
}
