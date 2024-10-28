package _00_Interface;

public interface Set<E> {

    boolean add(E e);

    boolean remove(Object e);

    boolean contains(Object e);

    boolean isEmpty();

    boolean equals(Object o);

    int size();

    void clear();
}
