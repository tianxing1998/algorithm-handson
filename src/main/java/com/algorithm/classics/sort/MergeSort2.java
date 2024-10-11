package com.algorithm.classics.sort;

import java.util.List;

public class MergeSort2 {
    static class Node {
        int value;
        Node next;
    }
    static class Iterator {
        Node cur;
        void next() {
            if (cur != null) {
                cur = cur.next;
            }
        }
        boolean hasNext() {
            return this.cur != null;
        }
        static Iterator instance(Node list) {
            Iterator iterator = new Iterator();
            iterator.cur = list;
            return iterator;
        }
    }

    static class Queue {
        void push(Iterator iterator){

        }

        Iterator pop() {
            return null;
        }

        boolean isEmpty() {
            return false;
        }

    }
    public static Node solve(Node[] lists) {
        Queue queue = new Queue();
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                queue.push(Iterator.instance(lists[i]));
            }
        }
        Node head = new Node();
        Node tail = head;
        while (!queue.isEmpty()) {
            Iterator cur = queue.pop();
            tail.next = cur.cur;
            tail = tail.next;
            tail.next = null;
            cur.next();
            if (cur.hasNext()) {
                queue.push(cur);
            }
        }
        return head.next;
    }
}
