package tech.hipsters;

import java.util.ArrayList;
import java.util.List;

public class Hashtable<K, V> {

    private static final float loadFactor = 0.75F;

    private List<List<Entry<K, V>>> buckets;
    private int count;

    public Hashtable() {
        this(16);
    }

    public Hashtable(int s) {
        this.buckets = new ArrayList<>(s);
        fill(this.buckets, s);
    }

    public V put(K key, V value) {
        throwIfNull(key);

        var entry = this.findOrCreateEntry(key);
        var oldValue = entry.getValue();
        entry.setValue(value);

        this.rehashIfNeeded();

        return oldValue;
    }

    public V get(K key) {
        throwIfNull(key);

        var entry = this.findEntry(key);

        if (entry != null) {
            return entry.getValue();
        }

        return null;
    }

    public boolean containsKey(K key) {
        throwIfNull(key);

        return this.findEntry(key) != null;
    }

    public V remove(K key) {
        throwIfNull(key);

        var bucket = this.findBucket(key);
        if (bucket == null || bucket.isEmpty()) {
            return null;
        }

        for (var it = bucket.iterator(); it.hasNext();) {
            var next = it.next();
            if (key.equals(next.getKey())) {
                it.remove();
                this.count--;

                this.rehashIfNeeded();

                return next.getValue();
            }
        }

        return null;
    }

    private Entry<K, V> findEntry(K key) {
        var bucket = this.findBucket(key);
        if (bucket == null || bucket.isEmpty()) {
            return null;
        }

        for (var entry : bucket) {
            if (key.equals(entry.getKey())) {
                return entry;
            }
        }

        return null;
    }

    private List<Entry<K, V>> findBucket(K key) {
        return this.buckets.get(this.bucketIndexFor(key));
    }

    private int bucketIndexFor(K key) {
        return key.hashCode() % this.buckets.size();
    }

    private Entry<K, V> findOrCreateEntry(K key) {
        var bucketIndex = this.bucketIndexFor(key);
        var bucket = this.buckets.get(bucketIndex);
        if (bucket == null) {
            bucket = new ArrayList<>();
            this.buckets.set(bucketIndex, bucket);
        }

        for (var entry : bucket) {
            if (key.equals(entry.getKey())) {
                return entry;
            }
        }

        var entry = new Entry<K,V>(key);
        bucket.add(entry);
        this.count++;

        return entry;
    }

    private void rehashIfNeeded() {
        if (this.count > 0 && ((this.buckets.size() / (float) this.count) < loadFactor)) {
            var oldBuckets = this.buckets;
            this.count = 0;
            var capacity = oldBuckets.size() * 2;
            this.buckets = new ArrayList<>(capacity);
            fill(this.buckets, capacity);

            for (var bucket : oldBuckets) {
                if (bucket != null) {
                    for (var entry : bucket) {
                        this.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
    }

    static void throwIfNull(Object key) {
        if (key == null) {
            throw new NullPointerException("key must not be null");
        }
    }

    static <E> void fill(List<E> items, int count) {
        for (int x = 0; x < count; x++) {
            items.add(null);
        }
    }

}
