package cs310.util;

import java.util.*;

/**
 * cs310.HashMap1 implementation.
 * Matches are based on equals; and hashCode must be consistently defined.
 */
@SuppressWarnings({"", "NullableProblems"})
public class HashMap1<K, V> extends AbstractMap<K, V>{
    /**
     * Construct an empty cs310.HashSet.
     */
    public HashMap1(){
        allocateArray( DEFAULT_TABLE_SIZE );
        clear( );
    }

    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size(){ return currentSize; }

    /**
     * Tests if some item is in this collection.
     * @param x any object.
     * @return true if this collection contains an item equal to x.
     */
    public boolean contains(Entry x){ return isActive(array, findPos(x)); }
    public boolean contains(Object x){
        HashEntry<Object,Object> entry = new HashEntry<Object,Object>(x,null);
        return isActive(array, findPos(entry));
    }

    /**
     * Tests if item in pos is active.
     * @param pos a position in the hash table.
     * @param arr the HashEntry array (can be oldArray during rehash).
     * @return true if this position is active.
     */
    private boolean isActive( HashEntry [ ] arr, int pos ){
        return arr[ pos ] != null && arr[ pos ].isActive;
    }
    
    /**
     * Adds an item to this collection.
     * @param x any object.
     * @return true if this item was added to the collection.
     */
    public V put( K key, V x ){
        HashEntry<Object,Object> entry = new HashEntry<Object,Object>(key,x);
        int currentPos = findPos( entry );

        if( array[ currentPos ] == null )
            occupied++;
        array[ currentPos ] = new HashEntry<Object,Object>( key, x, true );
        currentSize++;
        modCount++;

        if( occupied > array.length / 2 )
            rehash( );

        return x;
    }

    @SuppressWarnings("unchecked")
    public V get (Object key){
        HashEntry<Object,Object> temp = new HashEntry<Object,Object>(key, null);
        if(!contains(temp))
            return null;
        else
            return (V) array[findPos(temp)].getValue();
    }
    
    /**
     * Private routine to perform rehashing.
     * Can be called by both add and remove.
     */
    @SuppressWarnings("unchecked")
    private void rehash( ){
        HashEntry<Object,Object>[ ] oldArray = array;
        
            // Create a new, empty table
        allocateArray( nextPrime( 4 * size( ) ) );
        currentSize = 0;
        occupied = 0;
        
            // Copy table over
        for( int i = 0; i < oldArray.length; i++ )
            if( isActive( oldArray, i ) )
                put((K) oldArray[i].key,(V) oldArray[i].element);
    }
    
    /**
     * Removes an item from this collection.
     * @param x any object.
     * @return null, just to properly override AbstractMap's remove().
     */
    public V remove(Object x){
        HashEntry<Object,Object> temp = new HashEntry<Object,Object>(x, null);
        int currentPos = findPos( temp );
        if( !isActive( array, currentPos ) )
            return null;

        array[ currentPos ].isActive = false;
        currentSize--;
        modCount++;

        if( currentSize < array.length / 8 )
            rehash( );

        return null;
    }

    /**
     * Change the size of this collection to zero.
     */
    public void clear( ){
        currentSize = occupied = 0;
        modCount++;
        for( int i = 0; i < array.length; i++ )
            array[ i ] = null;
    }

    @SuppressWarnings({"NullableProblems", "unchecked"})
    public Set<Entry<K, V>> entrySet() {
        HashSet<Entry<K, V>> set = new HashSet<Entry<K, V>>();
        for (Entry entry: array) {
            if (entry != null)
                set.add(entry);
        }
        return set;
    }

    @SuppressWarnings({"NullableProblems", "unchecked"})
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<K>();
        for (Entry entry: array) {
            if (entry != null)
                set.add((K) entry.getKey());
        }
        return set;
    }

    @SuppressWarnings("unchecked")
    public Set<V> values() {
        HashSet<V> set = new HashSet<V>();
        for (Entry entry: array) {
            if (entry != null)
                set.add((V) entry.getValue());
        }
        return set;
    }

    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public Iterator<V> iterator( )
    {
        return new HashSetIterator( );
    }

    /**
     * This is the implementation of the HashSetIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the cs310.HashSet.
     */
    private class HashSetIterator implements Iterator<V>{
        private int expectedModCount = modCount;
        private int currentPos = -1;
        private int visited = 0;

        public boolean hasNext( )
        {
            if( expectedModCount != modCount )
                throw new ConcurrentModificationException( );
            
            return visited != size( );    
        }
        
        @SuppressWarnings("unchecked")
        public V next( )
        {
            if( !hasNext( ) )
                throw new NoSuchElementException( );
                          
            do
            {
                currentPos++;
            } while( currentPos < array.length && !isActive( array, currentPos ) );
                            
            visited++;
            return (V) array[ currentPos ].element;
        }
        
        public void remove( )
        {
            if( expectedModCount != modCount )
                throw new ConcurrentModificationException( );              
            if( currentPos == -1 || !isActive( array, currentPos ) )
                throw new IllegalStateException( );
    
            array[ currentPos ].isActive = false;
            currentSize--;
            visited--;
            modCount++;
            expectedModCount++;
        }
    }
    
    @SuppressWarnings("TypeParameterHidesVisibleType")
    public class HashEntry<K,T> implements Entry{
        public K key;
        public T element;   // the element
        public boolean isActive;  // false if marked deleted

        public HashEntry(K k, T e){
            key = k;
            element  = e;
            isActive = true;
        }
        public HashEntry(K k, T e, boolean i ){
            key = k;
            element  = e;
            isActive = i;
        }

        public Object getKey() { return key; }
        public Object getValue() { return element; }
        @SuppressWarnings("unchecked")
        public Object setValue(Object value) { return element = (T) value;}

        @SuppressWarnings({"unchecked", "RedundantIfStatement"})
        public boolean equals(Object o) {
            if (o.getClass() != this.getClass())
                return false;
            HashEntry<K,T> rhs = (HashEntry<K,T>) o;
            if (rhs.getKey() == this.getKey())
                return true;
            return false;
        }
        public int hashCode() {
            return key.hashCode();
        }
    }
    
    
    /**
     * Method that performs quadratic probing resolution.
     * Assumes table is at least half-empty.
     * @param x the item to search for.
     * @return the position where the search terminates.
     */
    private int findPos( Entry x ){
        int offset = 1;
        int currentPos = ( x == null ) ? 0 : Math.abs( x.getKey().hashCode() % array.length );

        while( array[ currentPos ] != null ){
            if( x == null ){
                if( array[ currentPos ].key == null )
                    break;
            }
            else if( x.getKey().equals(array[currentPos].getKey()) )
                break; 
            
            currentPos += offset;                  // Compute ith probe
            offset += 2;
            if( currentPos >= array.length )       // Implement the mod
                currentPos -= array.length;
        }
        return currentPos;
    }
    
    
    /**
     * Internal method to allocate array.
     * @param arraySize the size of the array.
     */
    @SuppressWarnings("unchecked")
    private void allocateArray( int arraySize ){
        array = new HashEntry[ nextPrime( arraySize ) ];
    }

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    private static int nextPrime( int n ){
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n ){
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }
    
    private static final int DEFAULT_TABLE_SIZE = 101;
    
    private int currentSize = 0;
    private int occupied = 0;
    private int modCount = 0;
    private HashEntry<Object,Object>[ ] array;
}
