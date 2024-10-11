package com.algorith.e;

public class Test9 {
    public static void main(String[] args) {
        Node n1 = Node.valOf(1);
        Node n2 = Node.valOf(2);
        Node n3 = Node.valOf(3);
        Node n4 = Node.valOf(4);
        Node n5 = Node.valOf(5);
        Node n6 = Node.valOf(6);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        System.out.println(Solution.solve(n1, 7));
    }

    static class Node {
        int val;
        Node next;
        static Node valOf(int val) {
            Node n = new Node();
            n.val = val;
            return n;
        }
    }
    static class Solution {
        static int solve(Node head, int k) {
            int cnt = 0;
            Node cur = head;
            while (cur != null) {
                cnt++;
                cur = cur.next;
            }
            if (cnt < k) {
                return 0;
            }
            if (cnt == k) {
                return head.val;
            }
            cur = head;
            int i = 1;
            for (; i < k; i++) {
                cur = cur.next;
            }
            Node p1 = cur;
            Node p2 = head;
            while (p1.next != null) {
                p1 = p1.next;
                p2 = p2.next;
            }
            return p2.val;
        }
    }
}
