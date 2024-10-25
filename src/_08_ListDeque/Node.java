package _08_ListDeque;

public class Node<E> {

    E data;
    Node<E> next;
    Node<E> prev;

    Node(E data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
