package _11_HashSet;

import _00_Interface.Set;

import java.util.NoSuchElementException;
import java.util.Objects;

public class HashSet<E> implements Set<E> {

    private final static int DEFAULT_CAPACITY = 1 << 4;
    private final static float LOAD_FACTOR = 0.75f;

    Node<E>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public HashSet() {
        table = (Node<E>[]) new Node<?>[DEFAULT_CAPACITY];
        size = 0;
    }


    @Override
    public boolean add(E e) {
        return add(hash(e), e) == null ;
    }

    @Override
    public boolean remove(Object e) {
        return remove(hash(e), e) != null;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public boolean contains(Object e) {
        int idx = hash(e) % table.length;

        Node<E> node = table[idx];

        while (node != null) {
            if (Objects.equals(e, node.key)) {
                return true;
            }
            node = node.next;
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {


        /** 모든 노드를 순회하여 null로 하지 않는 이유
         *  첫번째 Node를 null로 설정하면 해당 버킷에 연결된 모든 노드는 더 이상 사용되지 않게 되며 GC가 이들을 자동으로 메모리에서 제거 함
         */
        if (table != null && size > 0) {
            for (int i = 0; i < table.length; i++) {
                table[i] = null;
            }
            size = 0;
        }
    }

    private E add(int hash, E key) {
        int idx = hash % table.length;

        if (table[idx] == null) {
            table[idx] = new Node<>(hash, key, null);
        } else {
            Node<E> temp = table[idx];
            Node<E> prev = null;

            while (temp != null) {

                if ((temp.hash == hash) && temp.key == key || temp.key.equals(key)) {
                    return key;
                }

                prev = temp;
                temp = temp.next;
            }

            prev.next = new Node<>(hash, key, null);
        }

        size++;

        if (size >= LOAD_FACTOR * table.length) {
            resize();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {

        int newCapacity = table.length * 2;
        final Node<E>[] newTable = (Node<E>[]) new Node<?>[newCapacity];

        for (int i = 0; i < table.length; i++) {
            Node<E> value = table[i];

            if (value == null) {
                continue;
            }

            table[i] = null;

            Node<E> nextNode;

            while (value != null) {
                int idx = value.hash % newCapacity;

                nextNode = value.next;

                if (newTable[idx] != null) {
                    Node<E> tail = newTable[idx];

                    while (tail.next != null) {
                        tail = tail.next;
                    }

                    tail.next = value;
                    value.next = null;
                } else {
                    newTable[idx] = value;
                    value.next = null;
                }

                value = nextNode;
            }

        }
        table = newTable;
    }

    private Object remove(int hash, Object key) {
        int idx = hash % table.length;
        Node<E> node = table[idx];
        Node<E> removeNode = null;
        Node<E> prev = null;

        if (node == null) {
            return null;
        }

        while (node != null) {
            if (node.hash == hash && (node.key == key || node.key.equals(key))) {
                removeNode = node;

                if (prev == null) {
                    table[idx] = node.next;
                    node = null;
                } else {
                    prev.next = node.next;
                    node = null;
                }

                size--;
                break;
            }
            prev = node;
            node = node.next;

        }

        return removeNode;
    }

    private static int hash(Object key) {
        int hash;

        if (key == null) {
            return 0;
        } else {
            return Math.abs(hash = key.hashCode()) ^ (hash >>> 16);
        }
    }
}
