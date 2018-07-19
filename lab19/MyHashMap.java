import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class MyHashMap<K extends Comparable<K>, V> implements Map61BL<K, V> {
    private LinkedList<K> keys;
    private LinkedList<Pair>[] table;
    private int height;
    private int size;
    private double loadFactor;

    private class Pair {
        private K key;
        private V value;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        K myKey() {
            return key;
        }

        V myValue() {
            return value;
        }

        void changeValue(V v) {
            value = v;
        }
    }

    /* Constructor */
    public MyHashMap() {
        keys = new LinkedList<>();
        table = new LinkedList[10];
        for (int i = 0; i < 10; i++) {
            table[i] = new LinkedList<>();
        }
        height = 10;
        size = 0;
        loadFactor = 3;
    }

    public MyHashMap(int initialSize) {
        keys = new LinkedList<>();
        table = new LinkedList[initialSize];
        for (int i = 0; i < initialSize; i++) {
            table[i] = new LinkedList<>();
        }
        height = initialSize;
        size = 0;
        loadFactor = 10;
    }

    public MyHashMap(int initialSize, double loadFactor) {
        keys = new LinkedList<>();
        table = new LinkedList[initialSize];
        for (int i = 0; i < initialSize; i++) {
            table[i] = new LinkedList<>();
        }
        height = initialSize;
        size = 0;
        this.loadFactor = loadFactor;
    }

    @Override
    public void clear() {
        keys = new LinkedList<>();
        table = new LinkedList[height];
        for (int i = 0; i < height; i++) {
            table[i] = new LinkedList<>();
        }
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int box = Math.floorMod(key.hashCode(), height);
        for (Pair p : table[box]) {
            if (p.myKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(K key) {
        int box = Math.floorMod(key.hashCode(), height);
        for (Pair p : table[box]) {
            if (p.myKey().equals(key)) {
                return p.myValue();
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        int box = Math.floorMod(key.hashCode(), height);
        for (Pair p : table[box]) {
            if (p.myKey().equals(key)) {
                p.changeValue(value);
                return;
            }
        }
        keys.add(key);
        table[box].add(new Pair(key, value));
        size++;
        if ((double) size / (double) height > loadFactor) {
            resize();
        }
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> back = new HashSet<>();
        for (K i : keys) {
            back.add(i);
        }
        return back;
    }

    public class HashIterator implements Iterator<K> {
        private int pos;

        @Override
        public boolean hasNext() {
            return pos < keys.size() - 1;
        }

        @Override
        public K next() {
            pos++;
            return keys.get(pos);
        }

        public HashIterator() {
            pos = 0;
        }
    }

    public Iterator<K> iterator() {
        return new HashIterator();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    private void resize() {
        MyHashMap<K, V> newMap = new MyHashMap<>(height * 2, loadFactor);
        for (K i : keys) {
            newMap.put(i, this.get(i));
        }
        this.table = newMap.table;
        height = height * 2;
    }
}
