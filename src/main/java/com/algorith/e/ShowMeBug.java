package com.algorith.e;

import java.util.PriorityQueue;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class ShowMeBug {

    public static void main(String[] args) {
        // 请在此处书写代码
        // please write your code here
        Node n1 = Node.of(1);
        Node n2 = Node.of(2);
        Node n3 = Node.of(3);
        Node n4 = Node.of(4);
        Node n5 = Node.of(5);
        Node n6 = Node.of(6);
        Node n7 = Node.of(7);

        n1.next = n2;
        n2.next = n3;
        n3.next = null;
        n4.next = n5;
        n5.next = n6;
        n6.next = n7;

        Node[] array = new Node[2];
        array[0] = n1;
        array[1] = n4;

        Node result = Solution.sovle(array);
        result.print();
    }

    static class Node {
        int value;
        Node next;
        static Node of(int val) {
            Node n = new Node();
            n.value = val;
            return n;
        }
        void print() {
            System.out.println(value);
            if (next != null) {
                this.next.print();
            }
        }
    }

    static class Solution {
        static Node sovle(Node[] lists) {

            PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return o1.value - o2.value;
                }
            });
            for (int i = 0; i < lists.length ;i++) {
                if (lists[i] != null) {
                    queue.add(lists[i]);
                }
            }

            Node result = new Node();
            Node tail = result;
            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                tail.next = cur;
                cur = cur.next;
                tail = tail.next;
                tail.next = null;
                if (cur != null) {
                    queue.add(cur);
                }
            }

            return result.next;
        }
    }
}
