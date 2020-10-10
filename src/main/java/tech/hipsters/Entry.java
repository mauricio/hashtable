package tech.hipsters;

import java.util.Map;
import java.util.Objects;

public class Entry<K, V> implements Map.Entry<K, V> {

    private final K key;
    private V value;

    public Entry(K key, V value) {
        this(key);
        this.value = value;
    }

    public Entry(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key can't be null");
        }
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;
        Entry<?, ?> entry = (Entry<?, ?>) o;
        return key.equals(entry.key) &&
                Objects.equals(value, entry.value);
    }

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        var oldValue = this.value;
        this.value = value;
        return oldValue;
    }

}
