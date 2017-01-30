package cs310.util;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashSet1<K,V> extends AbstractCollection<K>{
    HashMap1<K,V> map;
    public HashSet1(){ map = new HashMap1<K,V>(); }

    public int size(){ return map.size(); }

    public boolean contains(Object x){
        return map.contains(x);
    }

    public boolean add(K key){
        map.put(key,null);
        return true;
    }

    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
    public K get (Object key){ return (K) map.get(key); }

    public boolean remove(Object x){
        map.remove(x);
        return true;
    }

    public void clear( ){ map.clear(); }

    public Set<Map.Entry<K,V>> entrySet() { return map.entrySet(); }

    public Set<K> values() { return map.keySet(); }

    @SuppressWarnings({"unchecked", "NullableProblems"})
    public Iterator<K> iterator(){ return (Iterator<K>) map.iterator(); }

    public String toString(){ return map.toString(); }
}
