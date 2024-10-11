package com.algorithm.classics.search;

public class BiSearchTree {
    static class Node {
        int val;
        Node left;
        Node right;
        public static Node valueOf(int val) {
            Node n = new Node();
            n.val = val;
            return n;
        }
    }
    private Node tree = null;
    public void insert(int val) {
        if (this.tree == null) {
            this.tree = Node.valueOf(val);
        } else {
            Node cur = this.tree;
            Node parent = null;
            boolean leftChild = true;
            while (cur != null) {
                if (cur.val == val) {
                    return;
                } else if (cur.val < val) {
                    parent = cur;
                    cur = cur.right;
                    leftChild = false;
                } else {
                    parent = cur;
                    cur = cur.left;
                    leftChild = true;
                }
            }
            if (leftChild) {
                parent.left = Node.valueOf(val);
            } else {
                parent.right = Node.valueOf(val);
            }
        }
    }
    public void delete(int val) {
        Node cur = this.tree;
        Node parent = null;
        boolean leftChild = true;
        while (cur != null) {
            if (cur.val == val) {
                break;
            } else if (cur.val < val) {
                parent = cur;
                cur = cur.right;
                leftChild = false;
            } else {
                parent = cur;
                cur = cur.left;
                leftChild = true;
            }
        }
        if (cur == null) {
            return;
        } else {
            if (cur.left == null && cur.right == null) {
                if (leftChild) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
            Node p = parent;
            Node t = cur;
            if (cur.left != null) {
                parent = cur;
                cur = cur.left;
                while (cur.right != null) {
                    parent = cur;
                    cur = cur.right;
                }
                if (parent != p) {
                    parent.right = cur.left;
                } else {
                    parent.left = cur.left;
                }
                t.val = cur.val;
                return;
            }
            if (cur.right != null) {
                // do the sames.
                parent = cur;
                cur = cur.right;
                while (cur.left != null) {
                    parent = cur;
                    cur = cur.left;
                }
                if (parent != p) {
                    parent.left = cur.right;
                } else {
                    parent.right = cur.right;
                }
                t.val = cur.val;
                return;
            }
        }
    }
    public boolean find(int val) {
        return this.find(this.tree, val);
    }
    private boolean find(Node n, int val) {
        return find0(n, val) != null;
    }
    private Node find0(Node n, int val) {
        if (n == null) {
            return null;
        }
        if (n.val == val) {
            return n;
        } else if (n.val < val) {
            return this.find0(n.right, val);
        } else {
            return this.find0(n.left, val);
        }
    }
    public void println() {
        this.print(this.tree);
    }
    private void print(Node t) {
        // 中序遍历
        if (t == null) {
            return;
        }
        if (t.left != null) {
            print(t);
        }
        System.out.println(" ," + t.val);
        if (t.right != null) {
            print(t.right);
        }
    }
}
