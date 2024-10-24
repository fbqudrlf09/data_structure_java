package _00_Interface;

public interface StackInterface<E> {

    E push(E item);

    E pop();

    E peek();

    int search(E value);

    int size();

    void clear();

    boolean empty();
}
