package com.algorithm.classics.sort;

import java.util.Arrays;

public class InsertSort {
    public static void main(String[] args) {
        int[] array1 = {3,4,2,6};
        sort1(array1);
        System.out.println(Arrays.toString(array1));

        Node node1 = new Node(3);
        Node node2 = new Node(4);
        Node node3 = new Node(6);
        Node node4 = new Node(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        System.out.println(reverse(node1));

    }

    /**
     * 状态机 + 符号表达 => 算法逻辑本质 => 状态机模型(当前状态待check)
     * [0,x] + (x + 1) => [0, x + 1] => ... [0, end]
     *  => 1. [x] < [x + 1] check
     *  => 2. 交换[x] [x + 1], ([0, x - 1] & (x)  x - 1 >= 0)
     *  => 3. 分析转迭代
     * @param input
     */
    public static void sort1(int[] input) {
        for (int i = 1; i < input.length; i++) {
            int j = i - 1;
            while (j >= 0 && input[j] > input[j + 1]) { // 结束条件
                {   // 循环逻辑
                    int tmp = input[j];
                    input[j] = input[j + 1];
                    input[j + 1] = tmp;
                }
                j--; // 循环条件
            }
            // 状态控制
            // 0 => 1 => 2 => ... => Ends.
        }
    }

    static class Node {
        int value;
        Node next = null;
        public Node(int value) {
            this.value = value;
        }
        public String toString() {
            if (this.next != null) {
                return "" + value + "," + next.toString();
            } else {
                return "" + value;
            }
        }
    }

    public static Node reverse(Node node) {
        Node head = null;
        Node current = node;
        while (current != null) {
            Node n = current;
            current = current.next;
            n.next = head;
            head = n;
        }
        return head;
    }

}
