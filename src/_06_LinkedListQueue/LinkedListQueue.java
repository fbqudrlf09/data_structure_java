package _06_LinkedListQueue;

import _00_Interface.Queue;

import java.util.NoSuchElementException;

public class LinkedListQueue<E> implements Queue<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public LinkedListQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }


    @Override
    public boolean offer(E e) {
        Node<E> newNode = new Node<>(e);

        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;

        return true;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }

        E element = head.data;

        Node<E> nextNode = head.next;

        head.data = null;
        head.next = null;

        head = nextNode;
        size--;

        // 마지막 노드였을 경우 tail도 null로 설정
        if (size == 0) {
            tail = null;
        }

        return element;
    }

    @Override
    public E peek() {

        if (size == 0) {
            return null;
        }

        return head.data;
    }

    public E remove() {
        E element = poll();

        if (element == null) {
            throw new NoSuchElementException();
        }

        return element;
    }

    public E element() {
        E element = peek();

        if (element == null) {
            throw new NoSuchElementException();
        }

        return element;
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
        for (Node<E> x = head; x != null;) {
            Node<E> nextNode = x.next;

            x.data = null;
            x.next = null;
            x = nextNode;
        }
        size = 0;
        head = tail = null;
    }
}
