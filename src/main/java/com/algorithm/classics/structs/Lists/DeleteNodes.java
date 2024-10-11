package com.algorithm.classics.structs.Lists;

public class DeleteNodes {
    public static class Node {
        int value;
        Node next;
        public Node(int v) {
            this.value = v;
        }
    }

    public static void main(String[] args) {

        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(2);
        Node n4 = new Node(3);
        Node n5 = new Node(3);
        Node n6 = new Node(4);
        Node n7 = new Node(5);
        Node n8 = new Node(5);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        n6.next = n7;
        n7.next = n8;

        // deleteNodes(n1);
        deleteNodes1(n1);

        System.out.println(n1);
    }

    /**
     * S=>(pre=-1, cur=0)=>(0, 1) =>(1/0, 2)=>...=>(x, n-1)=>E
     * @param list
     */
    public static void deleteNodes0(Node list) {
        Node result = list;
        if (list == null || list.next == null) {
            return;
        }
        Node pre = list;
        Node cur = list.next;
        while(cur != null) {
            if (cur.value != pre.value) {
                cur = cur.next;
                pre = pre.next;
            } else {
                pre.next = cur.next;
                cur = cur.next;
            }
        }
        return;
    }

    public static void deleteNodes1(Node list) {
        if (list == null) {
            return;
        }
        Node newHead = list;
        Node newTail = list;
        Node cur = list.next;
        newHead.next = null;
        while (cur != null) {
            if (cur.value != newTail.value) {
                Node tmp = cur.next;
                newTail.next = cur;
                cur.next = null;
                newTail = cur;
                cur = tmp;
            } else {
                cur = cur.next;
            }
        }

        return;
    }

    public static void deleteNodes(Node list) {
        Node head = new Node(-1);
        Node tail = head;
        Node begin = list;
        Node end = list;
        Node cur = list.next;
        while (cur != null) {
            if (cur.value == end.value) {
                end = cur;
            } else {
                if ( true /*begin == end*/ ) {
                    tail.next = begin;
                    tail = tail.next;
                    tail.next = null;
                }
                begin = cur;
                end = cur;
            }
            cur = cur.next;
        }

        // [begin, end]
        if (begin == end) {
            tail.next = begin;
            tail = tail.next;
            tail.next = null;
        }
    }

}
