package utils;

public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K first, V second) {
        this.key = first;
        this.value = second;
    }
    
    public K getFirst() {
        return key;
    }

    public V getSecond() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        @SuppressWarnings("unchecked")
		Pair<K, V> pair = (Pair<K, V>) o;

        if (key != null ? !key.equals(pair.key) : pair.key != null) return false;
        if (value != null ? !value.equals(pair.value) : pair.value != null) return false;

        return true;
    }
}