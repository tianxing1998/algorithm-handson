package com.algorith.e;

public class Test8 {
    public static void main(String[] args) {
        Node node1 = Node.nodeOf(1, 1);
        Node node2 = Node.nodeOf(2, 2);
        Node node3 = Node.nodeOf(3, 2);
        Node node4 = Node.nodeOf(4, 3);
        Node node5 = Node.nodeOf(5, 3);
        Node node6 = Node.nodeOf(6, 3);
        Node node7 = Node.nodeOf(7, 4);

        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        // node3.right = node7;

        Solution solution = new Solution();
        solution.solve(node1);
        System.out.println("cnt:" + solution.cnt);
    }
    public static class Node {
        int id;
        int val;
        Node left;
        Node right;
        public static Node nodeOf(int id, int val) {
            Node node = new Node();
            node.val = val;
            node.id = id;
            return node;
        }
    }

    /**
     * 任务分解: 1. 判断是否是X点 2.遍历所有顶点
     */
    static class Solution {
        int cnt;
        public void solve(Node tree) {
            if (tree != null) {
                if (treeEquals(tree.left, tree.right)) {
                    System.out.println("tree:" + tree.id);
                    cnt++;
                }
                solve(tree.left);
                solve(tree.right);
            }
        }
        public boolean treeEquals(Node a, Node b) {
            if (a == null && b == null) {
                return true;
            }
            if (a == null || b == null) {
                return false;
            }
            if (a.val == b.val) {
                return treeEquals(a.left, b.left) && treeEquals(a.right, b.right);
            } else {
                return false;
            }
        }
    }
}
