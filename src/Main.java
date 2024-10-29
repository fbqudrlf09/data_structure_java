import _13_BinarySearchTree.BinarySearchTree;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        BinarySearchTree<Object> binarySearchTree = new BinarySearchTree<>();

        binarySearchTree.add(23);
        binarySearchTree.add(12);
        binarySearchTree.add(40);
        binarySearchTree.add(7);
        binarySearchTree.add(16);
        binarySearchTree.add(1);
        binarySearchTree.add(14);
        binarySearchTree.add(17);
        binarySearchTree.add(29);
        binarySearchTree.add(55);
        binarySearchTree.add(61);

        System.out.println("중위 순위 : ");
        binarySearchTree.inorder();
        System.out.println();

        System.out.println("전위 순위");
        binarySearchTree.preorder();
    }
}