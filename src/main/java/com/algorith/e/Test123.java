package com.algorith.e;

public class Test123 {
    public static void main(String[] args) {
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        Node n4 = new Node();
        Node n5 = new Node();

        n1.value = 1;
        n1.next = n2;
        n2.value = 2;
        n2.next = n3;
        n3.value = 3;
        n3.next = n4;
        n4.value = 4;
        n4.next = n5;
        n5.value = 5;

        Node result = solve(n1, 2);
        System.out.println(result);
    }
    public static class Node {
        int value;
        Node next;
    }
    public static Node solve(Node root, int k) {
        Node newHead = new Node();
        Node newTail = newHead;

        Node cur = root;
        while (cur != null) {
            int d = 1;
            Node segHead = cur;
            while (cur != null && d <= k) {
                cur = cur.next;
                d++;
            }
            if (cur != null) {
                Node newSeg = null;
                while (segHead != cur) {
                    Node tmp = segHead.next;
                    segHead.next = newSeg;
                    newSeg =  segHead;
                    segHead = tmp;
                }
                newTail.next = newSeg;
                while (newTail.next != null) {
                    newTail = newTail.next;
                }
            } else {
                newTail.next = segHead;
            }
        }
        return newHead.next;
    }
}
