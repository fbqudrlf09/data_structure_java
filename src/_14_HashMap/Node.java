package _14_HashMap;

public class Node<K, V> {

    K key;
    V value;
    int hash;
    Node<K, V> next;

    public Node(K key, V value, int hash, Node<K, V> next) {
        this.key = key;
        this.value = value;
        this.hash = hash;
        this.next = next;
    }
}
