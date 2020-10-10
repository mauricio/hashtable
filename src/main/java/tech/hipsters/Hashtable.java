package tech.hipsters;

import java.util.ArrayList;
import java.util.List;

public class Hashtable<K,V> {

    private List<List<Entry<K,V>>> buckets;

    public Hashtable() {
        this(16);
    }

    public Hashtable(int s) {
        this.buckets = new ArrayList<>(s);
        for (int x = 0; x < s; x++) {
            buckets.add(null);
        }
    }

    public V put(K key, V value) {
        throwIfNull(key);

        var bucket = this.findOrCreateBucket(key);

        for (var entry : bucket) {
            if (key.equals(entry.getKey())) {
                var oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }

        bucket.add(new Entry<>(key, value));

        return null;
    }

    public V get(K key) {
        throwIfNull(key);

        var entry = this.findEntry(key);

        if (entry != null){
            return entry.getValue();
        }

        return null;
    }

    public boolean containsKey(K key) {
        throwIfNull(key);

        return this.findEntry(key) != null;
    }

    private Entry<K,V> findEntry(K key) {
        var bucket = this.findBucket(key);
        if (bucket == null || bucket.isEmpty()) {
            return null;
        }

        for ( var entry : bucket) {
            if (key.equals(entry.getKey())) {
                return entry;
            }
        }

        return null;
    }

    private List<Entry<K,V>> findBucket(K key) {
        return this.buckets.get(this.bucketIndexFor(key));
    }

    private int bucketIndexFor(K key) {
        return key.hashCode() / this.buckets.size();
    }

    private List<Entry<K,V>> findOrCreateBucket(K key) {
        var bucketIndex = this.bucketIndexFor(key);
        var bucket = this.buckets.get(bucketIndex);
        if (bucket == null) {
            bucket = new ArrayList<>();
            this.buckets.add(bucketIndex, bucket);
        }

        return bucket;
    }

    static void throwIfNull(Object key) {
        if (key == null) {
            throw new NullPointerException("key must not be null");
        }
    }

}
