package _12_LinkedHashSet;

import _00_Interface.Set;

import java.util.Objects;

public class LinkedHashSet<E> implements Set<E> {

    private static final int DEFAULT_CAPACITY = 1 << 4;

    private static final float LOAD_CAPACITY = 0.75f;

    Node<E>[] table;
    private int size;

    private Node<E> head;
    private Node<E> tail;

    @SuppressWarnings("unchecked")
    public LinkedHashSet() {
        table = (Node<E>[]) new Node[DEFAULT_CAPACITY];
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    public boolean add(E e) {
        return add(hash(e), e) == null;
    }

    private E add(int hash, E key) {
        int idx = hash % table.length;

        Node<E> newNode = new Node<E>(hash, key, null);

        if (table[idx] == null) {
            table[idx] = newNode;
        } else {
            Node<E> prev = null;
            Node<E> node = table[idx];

            while (node != null) {
                if (hash == node.hash && (node.key == key || key.equals(node.key))) {
                    return key;
                }
                prev = node;
                node = node.next;
            }

            prev.next = newNode;
        }
        size++;

        linkLastNode(newNode);

        if (size >= LOAD_CAPACITY * table.length) {
            resize();
        }

        return null;
    }

    @Override
    public boolean remove(Object e) {
        return remove(hash(e), e) != null;
    }

    private Object remove(int hash, Object key) {
        int idx = hash % table.length;

        Node<E> node = table[idx];
        Node<E> removeNode = null;
        Node<E> prevNode = null;

        if (node == null) {
            return null;
        }

        while(node != null){

            if (hash == node.hash && (key == node || key.equals(node.key))) {
                removeNode = node;

                if (prevNode == null) {
                    table[idx] = node.next;
                } else {
                    prevNode.next = node.next;
                }

                unlinkNode(node);
                node = null;

                size--;
                break;
            }

            prevNode = node;
            node = node.next;
        }

        return removeNode;
    }

    @Override
    public boolean contains(Object e) {

        int hash = hash(e);
        int idx = hash % table.length;

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

        if(table!= null && size > 0) {
            for (int i = 0; i < table.length; i++) {
                table[i] = null;
            }
            size = 0;
        }
        tail = head = null;
    }

    private int hash(Object key) {
        int hash;
        if (key == null) {
            return 0;
        } else {
            return Math.abs(hash = key.hashCode()) ^ (hash >>> 16);
        }
    }

    private void linkLastNode(Node<E> eNode) {
        Node<E> last = tail;

        tail = eNode;

        if (last == null) {
            head = eNode;
        } else {
            eNode.prevLink = last;
            last.nextLink = eNode;
        }
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = table.length * 2;

        final Node<E>[] newTable = (Node<E>[]) new Node[newCapacity];

        for (Node<E> eNode : table) {

            Node<E> value = eNode;
            Node<E> temp = null;

            if (value == null) {
                continue;
            }

            Node<E> nextNode;

            while (value != null) {

                int idx = value.hash % newCapacity;

                if (newTable[idx] != null) {
                    Node<E> tail = newTable[idx];

                    while (tail.next != null) {
                        tail = tail.next;
                    }

                    nextNode = value.next;
                    value.next = null;
                    tail.next = value;
                } else {
                    nextNode = value.next;
                    value.next = null;
                    newTable[idx] = value;
                }

                value = nextNode;
            }
        }

        table = newTable;
    }

    private void unlinkNode(Node<E> eNode) {

        Node<E> prevNode = eNode.prevLink;
        Node<E> nextNode = eNode.nextLink;

        if (prevNode == null) {
            head = nextNode;
        }else{
            prevNode.nextLink = nextNode;
            eNode.prevLink = null;
        }


        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prevLink = prevNode;
            eNode.nextLink = null;
        }
    }
}
