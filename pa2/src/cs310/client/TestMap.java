package cs310.client;

import cs310.util.HashMap1;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TestMap
{
    private static HashMap1<String,String> hashmap;
    public TestMap(HashMap1<String,String> newmap){
        hashmap = newmap;
    }
    // Do some inserts and printing (done in printMap).
    public static void main(String args[])
    {
        TestMap map = new TestMap(new cs310.util.HashMap1<String,String>());
        map.test(hashmap);
    }

    public static <AnyType> void printCollection( Collection<AnyType> c )
    {
        for( AnyType val : c )
            System.out.print( val + ", " );
        System.out.println();
    }

    @SuppressWarnings("WhileLoopReplaceableByForEach")
    public <KeyType,ValueType> void printMap( String msg, Map<KeyType,ValueType> m ){
        System.out.println( msg + ":" );
        Set<Map.Entry<KeyType,ValueType>> entries = m.entrySet( );

        Iterator itr = entries.iterator();
        while (itr.hasNext()){
            HashMap1.HashEntry thisPair = (HashMap1.HashEntry) itr.next();
            System.out.print(thisPair.getKey() + ": ");
            System.out.println(thisPair.getValue());
        }
    }

    public void test( HashMap1<String,String> phone1 )
    {
        phone1.put("John Doe", "212-555-1212");
        phone1.put("Jane Doe", "312-555-1212");
        phone1.put("Holly Doe", "213-555-1212");
        phone1.put("Susan Doe", "617-555-1212");
        phone1.put("Jane Doe", "unlisted");

        System.out.println( "phone1.get(\"Jane Doe\"): " + phone1.get( "Jane Doe" ) );
        System.out.println( );

        System.out.println( "The " + phone1.getClass( ).getName( ) + ": " );
        printMap( "phone1", phone1 );
        
        System.out.println( "\nThe keys are: " );
        Set<String> keys = phone1.keySet( );
        printCollection( keys );
        
        System.out.println( "\nThe values are: " );
        Collection<String> values = phone1.values( );
        printCollection( values );
        System.out.println( );

        keys.remove( "John Doe" );
        values.remove( "unlisted" );
        System.out.println( "After John Doe and 1 unlisted are removed, the map is" );
        printMap( "phone1", phone1 );
        System.out.println( );
        
        System.out.println( phone1 );
    }
}
