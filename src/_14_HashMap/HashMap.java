package _14_HashMap;

import java.util.Map.Entry;

public class HashMap<K, V> {

    private final static int DEFAULT_CAPACITY = 16;
    private final static float LOAD_FACTOR = 0.75f;

    private Node<K, V>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public HashMap(){
        table = (Node<K, V>[]) new Node<?>[DEFAULT_CAPACITY];
        size = 0;
    }

    public V put(K key, V value) {
        int hash = hash(key);
        int idx = getIndex(hash);

        Node<K, V> node = table[idx];
        Node<K, V> prev = null;

        // 같은 키가 있는 지 확인
        while (node != null) {
            if (node.hash == hash && (node.key == key || node.key.equals(key))) {
                V oldVal = node.value;
                node.value = value;
                return oldVal;
            }
            prev = node;
            node = node.next;
        }

        Node<K, V> newNode = new Node<>(key, value, hash,null);

        // 해당 인덱스에 첫 노드로 추가
        if (prev == null) {
            table[idx] = newNode;
        } else {
            // 연결 리스트의 마지막 추가
            prev.next = newNode;
        }
        size++;

        if (size > LOAD_FACTOR * table.length) {
            resize();
        }

        return null;
    }

    public V get(Object key) {
        int hash = hash(key);
        int idx = getIndex(hash);

        Node<K, V> node = table[idx];

        while (node != null) {
            if (node.hash == hash && (node.key == key || node.key.equals(key))) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    public V remove(Object key) {
        int hash = hash(key);
        int idx = getIndex(hash);

        Node<K, V> node = table[idx];
        Node<K, V> prev = null;

        while (node != null) {
            if (node.hash == hash && (node.key == key || node.key.equals(key))) {
                V removeValue = node.value;

                if (prev == null) {
                    table[idx] = node.next;
                } else {
                    prev.next = node.next;
                }

                size--;

                node.value = null;
                node.key = null;
                node.next = null;

                return removeValue;
            }
            prev = node;
            node = node.next;
        }
        return null;
    }

    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = table.length * 2;
        Node<K, V>[] newTable = (Node<K, V>[]) new Node<?>[newCapacity];

        for (int i = 0; i < table.length; i++) {
            Node<K, V> node = table[i];

            if (node == null) {
                continue;
            }

            table[i] = null;
            Node<K, V> nextNode;

            while (node != null) {
                nextNode = node.next;
                int idx = node.hash % newCapacity;

                if (newTable[idx] != null) {
                    Node<K, V> tail = newTable[idx];

                    while (tail.next != null) {
                        tail = tail.next;
                    }
                    tail.next = node;
                    node.next = null;

                } else {
                    newTable[idx] = node;
                    node.next = null;
                }
                node = nextNode;
            }
        }
        table = newTable;
    }

    private int hash(Object key) {
        int hash;
        return (key == null) ? 0 : Math.abs(hash = key.hashCode()) ^ hash >>> 16;
    }

    private int getIndex(int hash) {
        return hash % table.length;
    }
}
