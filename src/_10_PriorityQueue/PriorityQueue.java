package _10_PriorityQueue;

import _00_Interface.Queue;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class PriorityQueue<E> implements Queue<E> {

    private final Comparator<? super E> comparator;
    private static final int DEFAULT_CAPACITY = 10;

    private int size;
    private Object[] array;

    public PriorityQueue() {
        this(null);
    }

    public PriorityQueue(Comparator<? super E> comparator) {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.comparator = comparator;
    }

    public PriorityQueue(int capacity) {
        this(capacity, null);
    }

    public PriorityQueue(int capacity, Comparator<? super E> comparator) {
        this.array = new Object[capacity];
        this.size = 0;
        this.comparator = comparator;
    }

    // 부모 인덱스 찾기
    private int getParent(int index) {
        return index/2;
    }

    // 왼쪽 자식 인덱스 찾기
    private int getLeftChild(int index) {
        return index * 2;
    }

    // 오른쪽 자식 인덱스 찾기
    private int getRightChild(int index) {
        return index * 2 + 1;
    }

    @Override

    public boolean offer(E e) {
        if (size + 1 == array.length) {
            resize(array.length * 2);

        }

        siftUp(size + 1, e);
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (array[1] == null) {
            return null;
        }

        return remove();
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        if (size == 0) {
            return null;
        }

        return (E) array[1];
    }

    public boolean contains(Object value) {
        for (int i = 1; i <= size; i++) {
            if (value.equals(array[i])) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (int i = 1; i <= size; i++) {
            array[i] = null;
        }

        size = 0;
    }

    @SuppressWarnings("unchecked")
    private void siftUpComarator(int idx, E target, Comparator<? super E> comparator) {

        while (idx > 1) {

            int parent = getParent(idx);
            Object parentVal = array[parent];

            if (comparator.compare(target, (E) array[parent]) >= 0) {
                break;
            }

            array[idx] = parentVal;
            idx = parent;
        }

        array[idx] = target;
    }

    public void siftUp(int idx, E target) {
        if (comparator != null) {
            siftUpComarator(idx, target, comparator);
        } else {
            siftUpComparable(idx, target);
        }
    }

    @SuppressWarnings("unchecked")
    private void siftUpComparable(int idx, E target) {
        Comparable<? super E> comp = (Comparable<? super E>) target;

        while (idx > 1) {
            int parent = getParent(idx);
            Object parentVal = array[parent];

            if (comp.compareTo((E)parentVal) >= 0) {
                break;
            }

            array[idx] = parentVal;
            idx = parent;
        }

        array[idx] = target;
    }

    @SuppressWarnings("unchecked")
    public E remove() {
        if (array[1] == null) {
            throw new NoSuchElementException();
        }

        E result = (E) array[1];
        E target;
        if (size == 1) {
            target = null;
        } else {
            target = (E)array[size];
        }
        array[size] = null;

        siftDown(1, target);

        return result;
    }

    @SuppressWarnings("unchecked")
    public E removeRYU() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        E target = (E) array[1];
        array[1] = null;
        siftDown(1, (E) target);

        return target;
    }

    private void siftDown(int idx, E target) {
        if (comparator != null) {
            siftDownComparator(idx, target, comparator);
        } else {
            siftDownComparable(idx, target);
        }
    }

    @SuppressWarnings("unchecked")
    private void siftDownComparator(int idx, E target, Comparator<? super E> comparator) {

        array[idx] = null;

        while (idx <= size) {
            int child = getLeftChild(idx);
            int right = getRightChild(idx);

            if (right <= size && comparator.compare((E) array[child], (E) array[right]) < 0) {
                child = right;
            }

            if (comparator.compare((E) target, (E) array[child]) <= 0) {
                break;
            }

            array[idx] = array[child];
            idx = child;
        }

        array[idx] = target;
    }

    @SuppressWarnings("unchecked")
    private void siftDownComparable(int idx, E target) {

        array[idx] = null;
        Comparable<? super E> comparable = (Comparable<? super E>) target;

        while (idx <= size) {
            int child = getLeftChild(idx);
            int right = getRightChild(idx);

            if (right <= size && ((Comparable<? super E>)array[child]).compareTo((E)array[right]) < 0) {
                child = right;
            }

            if (comparator.compare((E) target, (E) array[child]) <= 0) {
                break;
            }

            array[idx] = array[child];
            idx = child;
        }

        array[idx] = target;
    }

    private void resize(int newCapacity) {
        Object[] newArray = new Object[newCapacity];

        for (int i = 1; i <= size; i++) {
            newArray[i] = array[i];
        }

        this.array = null;
        this.array = newArray;
    }

    public int size() {
        return this.size;
    }
}
