package com.algorithm.classics.structs.Lists;

/**
 * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * 示例：
 * 输入：head = [1,2,3,4,5], k = 2
 * 输出：[2,1,4,3,5]
 */
public class ListReverse2 {
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        Node node = Solution.reverse1(node1, 3);
        System.out.println(node);

    }
    static class Node {
        int value;
        Node next;
        public Node(int val) {
            this.value = val;
        }
    }
    static class Solution {

        public static Node reverse1(Node n, int k) {
            Node totalHead = new Node(-1);
            Node totalTail = totalHead;

            Node segHead = null;
            Node segTail = null;

            Node cur = n;
            while (cur != null) {
                for (int i = 1; i <= k && cur != null; i ++) {
                    Node tmp = cur.next;
                    cur.next = segHead;
                    segHead = cur;
                    if (segTail == null) {
                        segTail = segHead;
                    }
                    cur = tmp;
                }
                totalTail.next = segHead;
                totalTail = segTail;
                segHead = null;
                segTail = null;
            }
            return totalHead.next;
        }

        public static Node reverse(Node n, int k) {
            Node totalHead = new Node(-1);
            Node totalTail = totalHead;
            int i = 1;
            Node cur = n;
            Node segHead = null;
            Node segTail = null;
            while (i > 0) {
                for (; i<=k && cur != null; i++) {
                    Node tmp = cur.next;
                    cur.next = segHead;
                    segHead = cur;
                    if (segTail == null) {
                        segTail = segHead;
                    }
                    cur = tmp;
                }
                if (cur == null) {
                    totalTail.next = segHead;
                    i = -1;
                } else {
                    totalTail.next = segHead;
                    totalTail = segTail;
                    segHead = null;
                    segTail = null;
                    i = 1;
                }
            }
            return totalHead.next;
        }

    }
}
