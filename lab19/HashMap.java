import java.util.Iterator;
import java.util.LinkedList;

public class HashMap<K, V> implements Map61BL<K, V> {

    /* Instance variables here? */
    private LinkedList<K> keys;
    LinkedList<Entry>[] hm;
    int capacity;
    int size;
    double loadFactor;

    public HashMap() {
        capacity = 16;
        keys = new LinkedList<>();
        hm = new LinkedList[capacity];
        for (int i = 0; i < capacity; i += 1) {
            hm[i] = new LinkedList<>();
        }
        size = 0;
        loadFactor = 0.75;
    }

    public HashMap(int initialCapacity) {
        capacity = initialCapacity;
        loadFactor = 0.75;
        keys = new LinkedList<>();
        hm = new LinkedList[initialCapacity];
        for (int i = 0; i < capacity; i += 1) {
            hm[i] = new LinkedList<>();
        }
        size = 0;
        loadFactor = 0.75;
    }

    public HashMap(int initialCapacity, double loadFactor) {
        capacity = initialCapacity;
        this.loadFactor = loadFactor;
        keys = new LinkedList<>();
        hm = new LinkedList[capacity];
        for (int i = 0; i < capacity; i += 1) {
            hm[i] = new LinkedList<>();
        }
        size = 0;
    }

    /* Removes all of the mappings from this map. */
    public void clear() {
        keys = new LinkedList<>();
        hm = new LinkedList[capacity];
        for (int i = 0; i < capacity; i += 1) {
            hm[i] = new LinkedList<>();
        }
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key KEY. */
    @Override
    public boolean containsKey(K key) {
        int bucket = Math.floorMod(key.hashCode(), capacity);
        for (Entry e : hm[bucket]) {
            if (e.myKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /* Returns the value to which the specified key KEY is mapped, or null i
       this map contains no mapping for KEY. */
    @Override
    public V get(K key) {
        int bucket = Math.floorMod(key.hashCode(), capacity);
        for (Entry e : hm[bucket]) {
            if (e.myKey().equals(key)) {
                return e.myValue();
            }
        }
        return null;
    }

    /* Puts the specified key-value pair (KEY, VALUE) in this map. */
    @Override
    public void put(K key, V value) {
        int bucket = Math.floorMod(key.hashCode(), capacity);
        for (Entry e : hm[bucket]) {
            if (e.myKey().equals(key)) {
                e.newValue(value);
                return;
            }
        }
        hm[bucket].add(new Entry(key, value));
        keys.add(key);
        size += 1;
        if ((double) size / (double) capacity > loadFactor) {
            resize();
        }
    }

    /* Removes and returns a key KEY and its associated value. */
    @Override
    public V remove(K key) {
        int bucket = Math.floorMod(key.hashCode(), capacity);
        V result = null;
        for (Entry e : hm[bucket]) {
            if (e.myKey().equals(key)) {
                result = e.myValue();
                hm[bucket].remove(e);
                keys.remove(key);
                size -= 1;
            }
        }
        return result;
    }

    /* Removes a particular key-value pair (KEY, VALUE) and returns true if
       successful. */
    @Override
    public boolean remove(K key, V value) {
        int bucket = Math.floorMod(key.hashCode(), capacity);
        Boolean suc = false;
        for (Entry e : hm[bucket]) {
            if (e.myKey().equals(key) && e.myValue().equals(value)) {
                suc = true;
                hm[bucket].remove(e);
                keys.remove(key);
                size -= 1;
            }
        }
        return suc;
    }

    /* Returns the number of key-value pairs in this map. */
    @Override
    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public class HashIterator implements Iterator<K> {
        private int pos;

        @Override
        public boolean hasNext() {
            return pos < size() - 1;
        }

        @Override
        public K next() {
            pos += 1;
            return keys.get(pos);
        }

        public HashIterator() {
            pos = -1;
        }
    }

    /* Returns an Iterator over the keys in this map. */
    @Override
    public Iterator<K> iterator() {
        return new HashIterator();
    }

    public void resize() {
        HashMap<K, V> newMap = new HashMap<>(capacity * 2, loadFactor);
        for (K i : keys) {
            newMap.put(i, this.get(i));
        }
        this.hm = newMap.hm;
        capacity = capacity * 2;
    }

    private class Entry {
        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        K myKey() {
            return key;
        }

        V myValue() {
            return value;
        }

        void newValue(V val) {
            this.value = val;
            this.value = val;
        }
    }
}
