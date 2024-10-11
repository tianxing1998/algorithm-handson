package com.algorithm.classics.search;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {
    public static void main(String[] args) {
        Node node1 = new Node(1);
            Node node12 = new Node(2);
                Node node121 = new Node(6);
            Node node13 = new Node(3);
                Node node131 = new Node(5);
                Node node132 = new Node(7);
        node1.left = node12;
        node1.right = node13;
        node12.right = node121;
        node13.left = node131;
        node13.right = node132;

        middleOrderIterate(node1);
    }

    static class Node {
        int value;
        Node left;
        Node right;
        public Node(int value) {
            this.value = value;
        }
    }

    public static void iterateByLevel(Node tree) {
        Queue<Node> stats = new LinkedList<>();
        stats.add(tree);
        while (!stats.isEmpty()) {
            Node current = stats.poll();
            System.out.println(current.value);
            if (current.left != null) {
                stats.add(current.left);
            }
            if (current.right != null) {
                stats.add(current.right);
            }
        }
    }

    public static void middleOrderIterate(Node root) {
        if (root == null) {
            return;
        }
        System.out.println(root.value);
        middleOrderIterate(root.left);
        middleOrderIterate(root.right);
    }
}
