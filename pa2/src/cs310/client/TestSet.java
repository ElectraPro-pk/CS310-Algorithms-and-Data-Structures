package cs310.client;

import cs310.util.HashMap1;
import cs310.util.HashSet1;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TestSet
{
    private static HashSet1<String,String> hashset;
    public TestSet(HashSet1<String, String> newmap){
        hashset = newmap;
    }
    // Do some inserts and printing (done in printMap).
    public static void main(String args[])
    {
        TestSet set = new TestSet(new HashSet1<String,String>());
        set.testHashSet(hashset);
    }

    public static <AnyType> void printCollection( Collection<AnyType> c )
    {
        for( AnyType val : c )
            System.out.print( val + ", " );
        System.out.println();
    }

    @SuppressWarnings("WhileLoopReplaceableByForEach")
    public void printSet( String msg, HashSet1<String, String> m ){
        System.out.println( msg + ":" );
        Set<Map.Entry<String,String>> entries = m.entrySet( );

        Iterator itr = entries.iterator();
        while (itr.hasNext()){
            HashMap1.HashEntry thisPair = (HashMap1.HashEntry) itr.next();
            System.out.print(thisPair.getKey() + ": ");
            System.out.println(thisPair.getValue());
        }
    }

    public void testHashSet( HashSet1<String,String> phone1 )
    {
        phone1.add("John Doe");
        phone1.add("Jane Doe");
        phone1.add("Holly Doe");
        phone1.add("Susan Doe");
        phone1.add("Jane Doe");

        System.out.println( "phone1.get(\"Jane Doe\"): " + phone1.get( "Jane Doe" ) );
        System.out.println( );

        System.out.println( "The " + phone1.getClass( ).getName( ) + ": " );
        printSet("phone1", phone1);
        
        System.out.println( "\nThe values are: " );
        Collection<String> values = phone1.values( );
        printCollection( values );
        System.out.println( );

        values.remove( "John Doe" );
        System.out.println( "After John Doe is removed, the set is" );
        printSet("phone1", phone1);
        System.out.println( );
        
        System.out.println( phone1 );
    }
}
