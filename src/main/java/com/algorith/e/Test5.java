package com.algorith.e;

public class Test5 {
    public static void main(String[] args) {
//        Node node1 = Node.nodeOf(1);
//        Node node2 = Node.nodeOf(2);
//        Node node3 = Node.nodeOf(3);
//
//        node1.left = node2;
//        node1.right = node3;

        Node node_10 = Node.nodeOf(-10);
        Node node9 = Node.nodeOf(9);
        Node node20 = Node.nodeOf(20);
        Node node15 = Node.nodeOf(15);
        Node node7 = Node.nodeOf(7);

//        node_10.left = node9;
//        node_10.right = node20;
//        node20.left = node15;
//        node20.right = node7;

        System.out.println(maxPathSum(node_10));
    }
    static class Node {
        int val;
        Node left;
        Node right;
        public static Node nodeOf(int val) {
            Node n = new Node();
            n.val = val;
            return n;
        }
    }

    public static int maxPathSum(Node n) {
        int max1 = Short.MIN_VALUE;
        if (n.left != null) {
            max1 = Math.max(max1, maxPathSum(n.left));
        }
        if (n.right != null) {
            max1 = Math.max(max1, maxPathSum(n.right));
        }
        int pathS1 = maxPathSingle(n.left);
        int pathS2 = maxPathSingle(n.right);
        int max2 = n.val;
        max2 = Math.max(max2, pathS2 + n.val);
        max2 = Math.max(max2, pathS1 + n.val);
        max2 = Math.max(max2, pathS1 + n.val + pathS2);
        System.out.println("n.val:" + n.val + ", max1 = " + max1 + ", max2 = " + max2);
        return Math.max(max1, max2);
    }
    public static int maxPathSingle(Node n) {
        int result = Short.MIN_VALUE;
        if (n == null) {
            return result;
        }
        if (n.left != null) {
            result = Math.max(result, maxPathSingle(n.left));
        }
        if (n.right != null) {
            result = Math.max(result, maxPathSingle(n.right));
        }
        result = Math.max(result + n.val, n.val);
        if (result == Integer.MIN_VALUE) {
            return 0;
        } else {
            return result;
        }
    }
}
