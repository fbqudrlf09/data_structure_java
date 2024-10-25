package _05_ArrayQueue;

import _00_Interface.Queue;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

public class ArrayQueue<E> implements Queue<E> {

    private static final int DEFAULT_CAPACITY = 64;

    private Object[] array;
    private int size;

    /**
     * 시작 인덱스를 카리키는 변수 (빈 공간임을 유의)
     * rear와 front만으로 array가 비어있는지 확인을 할 수 없기 때문에 빈 공간을 두어 front와 rear가 같은 인덱스를 가리키면 빈공간임을 알려줌
     */
    private int front; // 시작 인덱스를 가리키는 변수 (빈 공간임을 유의)
    private int rear;

    public ArrayQueue() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    public ArrayQueue(int capacity) {
        this.array = new Object[capacity];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    @Override

    public boolean offer(E e) {
        if ((rear + 1) % array.length == front) {
            resize(array.length * 2);
        }

        rear = (rear + 1) % array.length;
        array[rear] = e;
        size++;

        return true;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }

        front = (front + 1) % array.length;

        @SuppressWarnings("unchecked")
        E element = (E)array[front];
        array[front] = null;
        size--;

        return element;
    }

    @Override
    public E peek() {
        if (size == 0) {
            return null;
        }

        @SuppressWarnings("unchecked")
        E element = (E) array[(front + 1) % array.length];
        return element;
    }

    public E element(){

        E item = peek();

        if (item == null) {
            throw new NoSuchElementException();
        }
        return item;
    }

    public E remove(){
        E item = poll();

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
        int start = (front + 1) % array.length;

        for (int i = 0, idx = start; i < size; i++, idx++) {
            if (value.equals(array[idx % array.length])) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
        front = rear = size = 0;
    }

    private void resize(int newCapacity) {
        int arrayCapacity = array.length;

        Object[] newArray = new Object[newCapacity];

        /**
         * i : new array index
         * j = original array
         * index 오소 개수(size)만큼 새 배열에 값 복사
         * j % arrayCapacity를 하는 이유는 원형 구조를 생각해서 작성한 것 이기 때문
         */
        for (int i = 1, j = front + 1; i <= size; i++, j++) {
            newArray[i] = array[j % arrayCapacity];
        }

        this.array = null;
        this.array = newArray;

        front = 0;
        rear = size;
    }
}
