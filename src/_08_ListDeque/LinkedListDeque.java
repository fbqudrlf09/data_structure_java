package _08_ListDeque;

import _00_Interface.Queue;

import java.util.NoSuchElementException;

public class LinkedListDeque<E> implements Queue<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public LinkedListDeque() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean offerFirst(E value) {
        Node<E> newNode = new Node<>(value);
        newNode.next = head;

        if (head != null) {
            head.prev = newNode;
        }

        head = newNode;
        size++;

        if (head.next == null) {
            tail = head;
        }

        return true;
    }

    public boolean offer(E value) {
        return offerLast(value);
    }

    public boolean offerLast(E value) {

        if (size == 0) {
            return offerFirst(value);
        }

        Node<E> newNode = new Node<>(value);
        newNode.prev = tail;
        tail.next = newNode;

        tail = newNode;
        size++;

        return true;
    }

    @Override
    public E poll() {
        return null;
    }

    public E pollFirst() {
        if (size == 0) {
            return null;
        }

        E item = head.data;

        Node<E> removeNode = head;
        head = removeNode.next;

        removeNode.next = null;
        removeNode.prev = null;
        removeNode.data = null;
        size--;

        if (head != null) {
            head.prev = null;
        }

        if (size == 0) {
            tail = null;
        }

        return item;
    }

    public E pollLast() {
        if (size == 0) {
            return null;
        }

        E item = tail.data;

        Node<E> prevNode = tail.prev;

        tail.data = null;
        tail.prev = null;

        if (prevNode != null) {
            prevNode.next = null;
        }

        tail = prevNode;
        size--;

        if (size == 0) {
            head = null;
        }

        return item;
    }

    public E removeLast() {
        E item = pollLast();

        if (item == null) {
            throw new NoSuchElementException();
        }
        return item;
    }

    public E remove() {
        return removeFirst();
    }

    public E removeFirst() {
        E item = poll();

        if (item == null) {
            throw new NoSuchElementException();
        }

        return item;
    }

    @Override
    public E peek() {
        return null;
    }

    public E peekFirst() {
        if (size == 0) {
            return null;
        }

         return head.data;
    }

    public E peekLast(){
        if (size == 0) {
            return null;
        }
        return tail.data;
    }

    public E element() {
        return getFirst();
    }

    public E getFirst() {
        E item = peek();

        if (item == null) {
            throw new NoSuchElementException();
        }

        return item;
    }

    public E getLast() {
        E item = peekLast();
        if (item == null) {
            throw new NoSuchElementException();
        }
        return item;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object value) {
        for (Node<E> x = head; x != null; x = x.next) {
            if (value.equals(x.data)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (Node<E> x = head; x != null; ) {
            Node<E> nextNode= x.next;

            x.prev = null;
            x.next = null;
            x.data = null;

            x = nextNode;
        }
        size = 0;
        head = tail = null;
    }
}
