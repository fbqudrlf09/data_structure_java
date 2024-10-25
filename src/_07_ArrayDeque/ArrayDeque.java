package _07_ArrayDeque;

import _00_Interface.Queue;
import _05_ArrayQueue.ArrayQueue;

import java.util.NoSuchElementException;

public class ArrayDeque<E> implements Queue<E> {

    private static final int DEFAULT_CAPACITY = 64;

    private Object[] array;
    private int size;

    private int front;
    private int rear;

    public ArrayDeque() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    public ArrayDeque(int capacity) {
        this.array = new Object[capacity];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    @Override
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    public E peekFirst() {
        if (size == 0) {
            return null;
        }

        @SuppressWarnings("unchecked")
        E item = (E) array[(front + 1) % array.length];

        return item;
    }

    public E peekLast() {
        if (size == 0) {
            return null;
        }

        return (E) array[rear];
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

    public E pollFirst() {
        if (size == 0) {
            return null;
        }

        front = (front + 1) % array.length;

        E item = (E)array[front];

        array[front] = null;
        size--;

        return item;
    }

    public E remove() {
        return removeFirst();
    }

    public E removeFirst() {
        E item = pollFirst();

        if (item == null) {
            throw new NoSuchElementException();
        }

        return item;
    }

    public E pollLast() {
        if (size == 0) {
            return null;
        }

        @SuppressWarnings("unchecked")
        E item = (E) array[rear];
        array[rear] = null;

        rear = (rear -1 + array.length) % array.length;
        size--;

        return item;
    }

    public E removeLast() {
        E item = pollLast();

        if (item == null) {
            throw new NoSuchElementException();
        }

        return item;
    }

    /**
     * 양 뱡향이니까 front앞에 붙히는 방식이기 때문에 front -1이고 혹시 front -1이 음수일수도 있기에 array.length를 더하여 양수로 만들고 나머지 연산을 시행
     * @param item
     * @return
     */
    public boolean offerFirst(E item) {
        int frontIdx  = (front - 1 + array.length) % array.length;

        if (frontIdx == rear) {
            resize(array.length * 2);
        }

        array[front] = item;
        front = frontIdx;
        size++;

        return true;
    }

    public boolean offerLast(E item) {
        int rearIdx = (rear + 1) % array.length;

        if (rearIdx == front) {
            resize(array.length * 2);
        }

        rear = rearIdx;
        array[rear] = item;
        size++;

        return true;
    }

    public int size() {
        return size;
    }

    public boolean isEmtpy() {
        return size == 0;
    }

    public boolean contains(Object value) {
        int start = (front + 1) % array.length;

        for (int i = 0, idx = start; i < size; i++, idx = (idx + 1) % array.length) {
            if (array[idx].equals(value)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }

        rear = front = size = 0;
    }

    private void resize(int newCapacity) {
        int arrayCapacity = array.length;

        Object[] newArray = new Object[newCapacity];

        for (int i = 1, j = front + 1; i <= size; i++, j++) {
            newArray[i] = array[j % arrayCapacity];
        }

        this.array = null;
        this.array = newArray;

        front = 0;
        rear = size;
    }
}
