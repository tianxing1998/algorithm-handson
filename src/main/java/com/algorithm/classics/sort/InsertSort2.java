package com.algorithm.classics.sort;

public class InsertSort2 {
    public static void main(String[] args) {
        Node n1 = Node.valIf(-1);
        Node n2 = Node.valIf(5);
        Node n3 = Node.valIf(3);
        Node n4 = Node.valIf(4);
        Node n5 = Node.valIf(0);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = null;

        Node tmp = Solution.insertSort(n1);
        System.out.println("tmp:" + tmp.val);
    }
    static class Node {
        int val;
        Node next;
        static Node valIf(int val) {
            Node n = new Node();
            n.val = val;
            return n;
        }
    }
    static class Solution {
        static public Node insertSort(Node data) {
            if (data == null) {
                return null;
            }
            Node ret = data;
            Node cur = data.next;
            ret.next = null;
            while (cur != null) {
                Node pre = null;
                Node pCur = ret;
                while (pCur != null) {
                    if (pCur.val > cur.val) {
                        if (pre==null) {
                            Node tmp = cur.next;
                            cur.next = pCur;
                            ret = cur;
                            cur = tmp;
                            break;
                        } else {
                            Node tmp = cur.next;
                            cur.next = pCur;
                            pre.next = cur;
                            cur = tmp;
                            break;
                        }
                    } else {
                        pre = pCur;
                        pCur = pCur.next;
                    }
                }
                if (pCur == null) {
                    Node tmp = cur.next;
                    cur.next = null;
                    pre.next = cur;
                    cur = tmp;
                }
            }
            return ret;
        }
    }
}
