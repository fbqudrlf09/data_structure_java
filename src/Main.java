import _02_SinglyLinkedList.SLinkedList;
import _03_DoublyLinkedList.DLinkedList;
import _04_Stack.Stack;
import _05_ArrayQueue.ArrayQueue;
import _09_Heap.Heap;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        String str1 = "0-42L";
        String str2 = "0-43-";

        System.out.println((str1.hashCode() == str2.hashCode()));	// true가 나온다.
    }
}