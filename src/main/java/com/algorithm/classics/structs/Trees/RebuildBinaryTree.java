package com.algorithm.classics.structs.Trees;

public class RebuildBinaryTree {
    public static void main(String[] args) {
        int[] mid = {4, 7, 3, 8, 9, 10};
        int[] post = {7, 4, 8, 10, 9, 3};
        Node root = new Node();
        root.value = 0;
        rebuild(mid, post, 0, mid.length - 1, 0, mid.length - 1, root, true);
        System.out.println(root);
    }

    static class Node {
        int value;
        Node left;
        Node right;
        public String toString() {
            return "val:" + value + "\n" +
                "left:" + (left == null ? "null": left.toString()) + "\n" +
                "right:" + (right == null ? "null": right.toString()) + "\n";
        }
    }

    public static void rebuild(int[] midTraversal, int[] postTraversal,
                               int post_begin, int post_end,
                               int mid_begin, int mid_end,
                               Node parent, boolean isLeft) {
        if (post_begin > post_end || post_end > postTraversal.length - 1 || post_begin < 0) {
            return;
        }
        int root = postTraversal[post_end];
        int posRootMid = mid_begin;
        // 边界条件不充分... 逻辑等价性 <=> 调试太慢
        while (midTraversal[posRootMid] != root) {
            posRootMid++;
        }

        Node newNode = new Node();
        newNode.value = root;
        if (parent == null) {
            parent = newNode;
        } else {
            if (isLeft) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }

        rebuild(midTraversal, postTraversal,
            post_begin, post_begin + (posRootMid - 1 - mid_begin),
            mid_begin, posRootMid - 1, newNode, true);
        rebuild(midTraversal, postTraversal,
            (post_end - 1) - (mid_end - posRootMid - 1), post_end - 1,
            posRootMid + 1, mid_end, newNode, false);

    }

}
