package com.algorithm.classics.structs.Lists;

public class ListReverse1 {
    public static void main(String[] args) {

    }
    static class Node {
        int value;
        Node next;
    }
    static class Solution {
        /**
         * start = 1.
         * [n, m]反转
         * 状态机
         *      Start => 1 => ... => M => M + 1 => .. => N => N + 1 ...=> End
         *      Any => End
         * @param head
         * @param n
         * @param m
         * @return
         */
        public static Node reverse(Node head, int n, int m) {
            if (n >= m) {
                return head;
            }

            if (head == null) {
                return head;
            }

            Node head1 = head;
            Node tail1 = null;

            Node cur = head;
            int i = 1;

            // [1, m - 1]
            for (; i < m && cur != null; i++) {
                i++;
                tail1 = cur;
                cur = cur.next;
            }
            if (cur == null) {
                return head;
            } else {
                // i = m
                tail1.next = null;

                // [m, n]
                Node head2 = null;
                Node tail2 = null;
                for (; i < (n + 1) && cur != null; i ++) {
                    i++;
                    Node tmp = cur;
                    cur = cur.next;
                    tmp.next = head2;
                    head2 = tmp;
                    if (tail2 == null) {
                        // tail2 = First Head2
                        tail2 = head2;
                    }
                }
                if (cur == null) {
                    // 不存在 (n + 1)
                    tail1.next = head2;
                    return head1;
                } else {
                    // [n + 1, ?)
                    // cur = n + 1
                    tail2.next = cur;
                    tail1.next = head2;
                    return head1;
                }
            }
        }
    }
}
