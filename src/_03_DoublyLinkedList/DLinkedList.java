package _03_DoublyLinkedList;

import _00_Interface.List;

import java.util.NoSuchElementException;

public class DLinkedList<E> implements List<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addFirst(E value) {
        Node<E> newNode = new Node<>(value);

        newNode.next = head;

        // 헤드 노드의 전 노드 링크
        if (head != null) {
            head.prev = newNode;
        }

        head = newNode;
        size++;

        // 노드가 처음 삽입되었을시 tail 설정
        if (head.next == null) {
            tail = head;
        }
    }

    public void addLast(E value) {
        if (size == 0) {
            addFirst(value);
            return;
        }

        Node<E> newNode = new Node<>(value);
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;

        size++;
    }

    private Node<E> search(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index + 1 > size / 2) {
            Node<E> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        } else {
            Node<E> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        }
    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    @Override
    public void add(int index, E value) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            addFirst(value);
            return;
        }
        if (index == size) {
            addLast(value);
            return;
        }

        Node<E> prevNode = search(index - 1);
        Node<E> newNode = new Node<>(value);
        Node<E> nextNode = prevNode.next;

        prevNode.next = newNode;
        newNode.next = nextNode;

        nextNode.prev = newNode;
        newNode.prev = prevNode;

        size++;
    }

    public E remove(){
        Node<E> removeNode = head;

        if (removeNode == null) {
            throw new NoSuchElementException();
        }

        Node<E> nextNode = head.next;

        E element = removeNode.data;
        removeNode.data = null;
        removeNode.next = null;

        if (nextNode != null) {
            nextNode.prev = null;
        }
        head = nextNode;
        size--;

        if (size == 0) {
            tail = null;
        }

        return element;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            return remove();
        }

        Node<E> prevNode = search(index - 1);
        Node<E> removeNode = prevNode.next;
        Node<E> nextNode = removeNode.next;

        E element = removeNode.data;

        prevNode.next = null;
        removeNode.next = null;
        removeNode.prev = null;
        removeNode.data = null;

        if (nextNode != null) {
            nextNode.prev = prevNode;
            prevNode.next = nextNode;
        }else {
            tail = prevNode;
        }

        size--;

        return element;
    }

    @Override
    public boolean remove(E value) {
        Node<E> prevNode = head;
        Node<E> removeNode = head;

        for (; removeNode != null; removeNode = removeNode.next) {
            if (value.equals(removeNode.data)) {
                break;
            }
        }

        if (prevNode == null) {
            return false;
        }

        prevNode = removeNode.prev;

        if (removeNode.data.equals(head)) {
            remove();
            return true;
        }

        Node<E> nextNode = removeNode.next;

        prevNode.next = null;
        removeNode.next = null;
        removeNode.prev = null;
        removeNode.data = null;

        if (nextNode != null) {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        } else {
            tail = prevNode;
        }

        size--;
        return true;
    }

    @Override
    public E get(int index) {
        return search(index).data;
    }

    @Override
    public void set(int index, E value) {
        Node<E> replaceNode = search(index);
        replaceNode.data = value;
    }

    @Override
    public boolean contains(E value) {
        return indexOf(value) >= 0;
    }

    @Override
    public int indexOf(E value) {
        int index = 0;

        for (Node<E> x = head; x != null; x = x.next, index++) {
            if (value.equals(x.data)) {
                return index;
            }
        }

        return -1;
    }

    public int lastIndexOf(E value) {
        int index = size-1;

        for (Node<E> x = tail; x != null; x = x.prev, index--) {
            if (value.equals(x.data)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (Node<E> x = head; x != null; x = x.next) {
            Node<E> nextNode = x.next;

            x.prev = null;
            x.next = null;
            x.data = null;
            x = nextNode;
        }

        head = tail = null;
        size = 0;
    }

    @Override
    public String toString() {
        return "DLinkedList{" +
                "head=" + head +
                ", tail=" + tail +
                ", size=" + size +
                '}';
    }
}
