package com.algorith.e;

public class TestN {
    public static void main(String[] args) {
        Node n1 = Node.ValOf(1);
        Node n2 = Node.ValOf(2);
        Node n3 = Node.ValOf(3);
        Node n4 = Node.ValOf(4);
        Node n5 = Node.ValOf(5);
        Node n7 = Node.ValOf(7);

        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        n3.right = n7;

        Node t = Solution.solve(n1);
        System.out.println("");

    }

    static class Node {
        int val = 0;
        Node left;
        Node right;
        Node next;
        static Node ValOf(int val) {
            Node n = new Node();
            n.val = val;
            return n;
        }
    }
    static class Solution {
        static Node solve(Node t) {
            Node cur = t;
            Node nextLayerHead = Node.ValOf(-1);
            Node nextLayerTail = nextLayerHead;
            while (cur != null) { // ceng jian
                while (cur != null) { // ceng nei
                    if (cur.left != null) {
                        nextLayerTail.next = cur.left;
                        nextLayerTail = nextLayerTail.next;
                    }
                    if (cur.right != null) {
                        nextLayerTail.next = cur.right;
                        nextLayerTail = nextLayerTail.next;
                    }
                    cur = cur.next;
                }
                cur = nextLayerHead.next;
                nextLayerHead.next = null;
                nextLayerTail = nextLayerHead;
            }
            return t;
        }
    }
}




