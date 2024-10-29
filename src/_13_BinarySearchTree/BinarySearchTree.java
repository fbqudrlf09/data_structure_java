package _13_BinarySearchTree;

import java.sql.SQLOutput;
import java.util.Comparator;

public class BinarySearchTree<E> {

    private Node<E> root;
    private int size;

    private final Comparator<? super E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.root = null;
        this.size = 0;
    }

    public boolean add(E value) {
        if (comparator == null) {
            return addUsingComparable(value) == null;
        } else {
            return addUsingComparator(value, comparator) == null;
        }
    }

    public E remove(Object o) {
        if (root == null) {
            return null;
        }

        if (comparator == null) {
            return removeUsingComarable(o);
        } else {
            return removeUsingComparator(o, comparator);
        }
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Object o) {
        if (comparator == null) {
            return containsUsingComparable(o);
        } else {
            return containsUsingComparator(o, comparator);
        }
    }

    public void clear() {
        size = 0;
        root = null;
    }

    public void preorder() {
        preorder(this.root);
    }

    public void inorder() {
        inorder(this.root);
    }

    public void postorder() {
        postorder(this.root);
    }

    @SuppressWarnings("unchecked")
    private E addUsingComparable(E value) {
        Node<E> currentNode = root;

        // root가 null이면 root에 새로운 노드를 만들고 null반환
        if (currentNode == null) {
            root = new Node<E>(value);
            size++;
            return null;
        }

        Node<E> currentParent;

        Comparable<? super E> comp = (Comparable<? super E>) value;
        int compResult;

        do {
            currentParent = currentNode;
            compResult = comp.compareTo(currentNode.value);

            // 비교값을 통해 추가될 값이 비교할 값보다 크면 오른쪽 작으면 왼쪽으로 이동하여
            // compResult == 0이면 값을 추가하지 않고 반환하여 중복을 허용하지 않음
            if (compResult > 0) {
                currentNode = currentNode.right;
            } else if (compResult < 0) {
                currentNode = currentNode.left;
            } else {
                return value;
            }
        } while (currentNode != null);

        Node<E> newNode = new Node<>(value, currentParent);


        // 마지막 leaf node에 left or right에 노드 연결
        if (compResult < 0) {
            currentParent.left = newNode;
        } else {
            currentParent.right = newNode;
        }

        size++;
        return null;
    }

    private E addUsingComparator(E value, Comparator<? super E> comparator) {
        Node<E> currentNode = root;
        Node<E> newNode = new Node<>(value, null);

        // root가 null이면 root에 새로운 노드를 만들고 null반환
        if (currentNode == null) {
            root = newNode;
            size++;
            return null;
        }

        Node<E> parentNode;
        int compResult;

        do {
            parentNode = currentNode;
            compResult = comparator.compare(value, currentNode.value);

            // 비교값을 통해 추가될 값이 비교할 값보다 크면 오른쪽 작으면 왼쪽으로 이동하여
            // compResult == 0이면 값을 추가하지 않고 반환하여 중복을 허용하지 않음
            if (compResult > 0) {
                currentNode = currentNode.right;
            } else if (compResult < 0) {
                currentNode = currentNode.left;
            } else {
                return value;
            }

        } while (currentNode != null);

        // 마지막 leaf node에 left or right에 노드 연결
        if (compResult > 0) {
            parentNode.right = newNode;
        } else {
            parentNode.left = newNode;
        }

        size++;
        return null;
    }

    // node : 삭제되는 노드
    private Node<E> getSuccessorAndUnlink(Node<E> node) {
        Node<E> currentParent = node;
        Node<E> current = node.right;

        // 처음 탐색하게되는 오른쪽 자식 노드에서 current의 왼쪽 자식이 없다는 것을 의미 (currentNode가 삭제할 노드 다음으로 큰 값)
        if (current.left == null) {
            currentParent.right = current.right;
            if (currentParent.right != null) {
                currentParent.right.parent = currentParent;
            }

            current.right = null;
            return current;
        }

        // 가장 작은 노드를 찾을 때 까지 반복
        while (current.left != null) {
            currentParent = current;
            current = current.left;
        }

        // 제일 작은 노드를 트리와 연결된 부분을 끊어줌
        // 제일 작은 노드의 오른쪽 연결을 부모 노드의 왼쪽과 연결 1. 부모노드의 left 파라미터 변경 2. 자식노드의 parent 파라미터 변경
        currentParent.left = current.right;
        if (currentParent.left != null) {
            currentParent.left.parent = currentParent;
        }

        // 제일 작은 노드의 연결을
        current.right = null;
        current.parent = null;
        return current;
    }

    private Node<E> deleteNode(Node<E> node) {
        if (node != null) {
            // 삭제하는 노드가 자식노드를 가지고 있지 않을 경우
            // 해당 노드만 삭제
            // 삭제하는 노드가 root일 경우 root를 끊어버리고 종료
            if (node.left == null && node.right == null) {
                if (node == root) {
                    root = null;
                } else {
                    node = null;
                }
                return null;
            }

            // 삭제하는 노드가 왼쪽 오른쪽 자식 노드를 모두 가지고 있을 경우
            // 삭제할 노드의 후계자 노드를 찾아 교체
            if (node.left != null && node.right != null) {
                Node<E> replacement = getSuccessorAndUnlink(node);

                node.value = replacement.value;
            }
            // 삭제하는 노드가 왼쪽 자식만 가지고 있을 경우
            // 왼쪽 노드를 삭제할 노드를 가리키도록 변경
            else if (node.left != null) {
                if (node == root) {
                    node = node.left;
                    root = node;
                    root.parent = null;
                } else {
                    node = node.left;
                }
            }
            // 삭제하는 노드가 오른쪽 자식만 가지고 있을 경우
            // 오른쪽 노드를 삭제할 노드를 가리키도록 변경
            else {
                if (node == root) {
                    node = node.right;
                    root = node;
                    root.parent = null;
                } else {
                    node = node.right;
                }
            }
        }
        return node;
    }

    @SuppressWarnings("unchecked")
    private E removeUsingComarable(Object value) {
        E oldVal = (E) value;
        Node<E> parent = null, current = root;

        boolean hasLeft = false;

        if (root == null) {
            return null;
        }

        Comparable<? super E> compValue = (Comparable<? super E>) value;

        // 삭제할 노드 찾기
        do {
            int compResult = compValue.compareTo(current.value);
            if (compResult == 0) {
                break;
            }

            parent = current;
            if (compResult < 0) {
                hasLeft = true;
                current = current.left;
            } else {
                hasLeft = false;
                current = current.right;
            }
        } while (current != null);

        // 삭제할 노드가 없을 경우
        if (current == null) {
            return null;
        }

        // 노드가 root인 경우
        if (parent == null) {
            deleteNode(current);
            size--;
            return oldVal;
        }

        // 삭제할 노드의 왼쪽 오른쪽에 따라 노드를 삭제 후
        // 부모 노드와 대체된 노드를 연결
        if (hasLeft) {
            parent.left = deleteNode(current);
            if (parent.left != null) {
                parent.left.parent = parent;
            }
        } else {
            parent.right = deleteNode(current);
            if (parent.right != null) {
                parent.right.parent = parent;
            }
        }

        size--;
        return oldVal;
    }

    @SuppressWarnings("unchecked")
    private E removeUsingComparator(Object value, Comparator<? super E> comparator) {
        E oldVal = (E) value;
        Node<E> parent = null, current = root;
        boolean hasLeft = false;

        if (root == null) {
            return null;
        }

        // 삭제할 노드 찾기
        do {
            int compResult = comparator.compare(current.value, oldVal);

            if (compResult == 0) {
                break;
            }

            parent = current;

            if (compResult > 0) {
                current = current.left;
                hasLeft = true;

            } else {
                current = current.right;
                hasLeft = false;
            }
        } while (current != null);

        // 삭제할 노드가 없을 경우
        if (current == null) {
            return null;
        }

        // 노드가 root인 경우
        if (parent == null) {
            deleteNode(current);
            size--;
            return oldVal;
        }

        // 삭제할 노드의 왼쪽 오른쪽에 따라 노드를 삭제 후
        // 부모 노드와 대체된 노드를 연결
        if (hasLeft) {
            parent.left = deleteNode(current);
            if (parent.left != null) {
                parent.left.parent = parent;
            }
        } else {
            parent.right = deleteNode(current);
            if (parent.right != null) {
                parent.right.parent = parent;
            }
        }
        size--;
        return oldVal;
    }

    @SuppressWarnings("unchecked")
    private boolean containsUsingComparable(Object o) {
        Comparable<? super E> comp = (Comparable<? super E>) o;
        Node<E> currentNode = root;

        while (currentNode != null) {
            int compResult = comp.compareTo(currentNode.value);

            if (compResult == 0) {
                return true;
            } else if (compResult > 0) {
                currentNode = currentNode.right;
            } else {
                currentNode = currentNode.left;
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private boolean containsUsingComparator(Object o, Comparator<? super E> comparator) {
        Node<E> currentNode = root;

        while (currentNode != null) {
            int compResult = comparator.compare((E) o, currentNode.value);

            if (compResult == 0) {
                return true;
            } else if (compResult > 0) {
                currentNode = currentNode.right;
            } else {
                currentNode = currentNode.left;
            }
        }

        return false;
    }

    private void preorder(Node<E> node) {
        if (node != null) {
            System.out.println(node.value + " ");
            preorder(node.left);
            preorder(node.right);
        }
    }

    private void inorder(Node<E> node) {
        if (node != null) {
            inorder(node.left);
            System.out.println(node.value + " ");
            inorder(node.right);
        }
    }

    private void postorder(Node<E> node) {
        if (node != null) {
            postorder(node.left);
            postorder(node.right);
            System.out.println(node.value + " ");
        }
    }
}
