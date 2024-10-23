package _02_SinglyLinkedList;

import _00_Interface.List;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class SLinkedList<E> implements List<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public SLinkedList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addFirst(E value) {
        Node<E> newNode = new Node<E>(value);
        newNode.next = head;
        head = newNode;

        size++;

        /**
         * 다음에 가리킬 노드가 없는 경우 (빈 List에 새 노드를 추가할 경우)
         * 데이터가 한개이므로 새 노드가 tail과 head 모두를 수행
         */
        if (head.next == null) {
            tail = head;
        }

    }

    public void addLast(E value) {
        if (size == 0) {
            addFirst(value);
            return;
        }

        Node<E> newNode = new Node<E>(value);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    @Override
    public void add(int index, E value) {
        // 검색할때는 index >= size 이어야 예외 처리가 되지만
        // 생성할떄는 index > size 임
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        if (index == 0) {
            addFirst(value);
            return;
        }

        if (index == size) {
            addLast(value);
            return;
        }

        //추가하려는 노드인덱스의 앞 노드를 찾아서 그 노드의 next를 추가하는 Node의 Next로 변경 후 prevNode의 Next를 newNode로 변경
        Node<E> prevNode = search(index - 1);
        Node<E> newNode = new Node<E>(value);
        newNode.next = prevNode.next;
        prevNode.next = newNode;
        size++;
    }

    public E remove(){

        Node<E> removeNode = head;

        if (removeNode == null) {
            throw new NoSuchElementException();
        }

        E element = removeNode.data;

        Node<E> nextnode = head.next;

        head.data = null;
        head.next = null;

        head = nextnode;
        size--;

        if (size == 0) {
            tail = null;
        }
        return element;
    }

    @Override
    public E remove(int index) {
        if (index == 0) {
            return remove(index);
        }

        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> preNode = search(index - 1);
        Node<E> removeNode = preNode.next;
        Node<E> nextNode = removeNode.next;

        preNode.next = nextNode;

        if (preNode.next == null) {
            tail = preNode;
        }

        E element = removeNode.data;
        removeNode.data = null;
        removeNode.next = null;
        size--;

        return element;
    }

    @Override
    public boolean remove(E value) {

        Node<E> prevNode = head;
        Node<E> x = head;

        for (; x != null; x = x.next) {
            if (x.data.equals(value)) {
                break;
            }
            prevNode = x;
        }

        if (x == null) {
            return false;
        }

        if (x.equals(head)) {
            remove();
            return true;
        }

        prevNode.next = x.next;
        if (prevNode.next == null) {
            tail = prevNode;
        }

        x.data = null;
        x.next = null;
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
        E element = replaceNode.data;
        replaceNode.data = value;
        element = null;

    }

    @Override
    public boolean contains(E value) {

        return indexOf(value) >= 0;
    }

    @Override
    public int indexOf(E value) {
        int index = 0;

        for (Node<E> x = head; x != null; x = x.next) {
            if (x.data.equals(value)) {
                return index;
            }
            index++;
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
            x.data = null;
            x.next = null;

            x= nextNode;
        }

        head = tail = null;
        size = 0;
    }

    private Node<E> search(int index) {

        // index 가 범위 밖으로 나가면 예외 던지기
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> x = head;

        for (int i = 0; i < index; i++) {
            x = x.next;
        }

        return x;
    }

    @Override
    public String toString() {
        return "SLinkedList{" +
                "head=" + head +
                ", tail=" + tail +
                ", size=" + size +
                '}';
    }
}
