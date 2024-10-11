package com.algorithm.classics.structs.Trees;

import java.util.Stack;

public class BinTrees {
    public static void main(String[] args) {

        Node n1 = new Node(1);
            Node n2 = new Node(2);
                Node n4 = new Node(4);// left
                //
            Node n3 = new Node(3);
                //
                Node n5 = new Node(5); //right

        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n3.right = n5;

        // 4 2 1 3 5
        // IterateByMiddel(n1);
        // 4 2 1 3 5
        // midOrder(n1);
        // 1 2 4 3 5
        // preOrder(n1);
        // 4 2 5 3 1
        postOrder(n1);
        //
    }

    public static class Node {
        int value;
        Node left;
        Node right;
        public Node(int v) {
            this.value = v;
        }
    }

    static class InnerNode {
        int type = 0; // 0 : node, 1 tree
        Node node;
        static InnerNode typeNode(int type, Node node) {
            InnerNode n = new InnerNode();
            n.type = type;
            n.node = node;
            return n;
        }
    }

    public static void midOrder1(Node root) {
        Stack<Node> stats = new Stack<>();
        if (root == null) {
            return;
        }
        stats.push(root);
        while(!stats.empty()) {
            Node cur = stats.peek();
            while(cur.left != null) {
                stats.push(cur.left);
                cur = cur.left;
            }
            System.out.println(cur.value); // visit
            stats.pop(); // 弹出最左节点
            if (cur.right != null) {
                stats.push(cur.right);
            } else {
                while(!stats.empty()) {
                    Node parent = stats.pop();
                    System.out.println(parent.value); // visit
                    if (parent.right != null) {
                        stats.push(parent.right);
                        break;
                    }
                }
            }
        }
    }

    public static void midOrder(Node root) {
        Stack<InnerNode> stats = new Stack<>();
        if (root != null) {
            stats.push(InnerNode.typeNode(1, root));
            while (!stats.empty()) {
                InnerNode node = stats.pop();
                if (node.type == 0) {
                    System.out.println(node.node.value);
                } else {
                    if (node.node.right != null) {
                        stats.push(InnerNode.typeNode(1, node.node.right));
                    }
                    stats.push(InnerNode.typeNode(0, node.node));
                    if (node.node.left != null) {
                        stats.push(InnerNode.typeNode(1, node.node.left));
                    }
                }
            }
        }
    }

    public static void preOrder(Node root) {
        Stack<InnerNode> stats = new Stack<>();
        if (root != null) {
            stats.push(InnerNode.typeNode(1, root));
            while (!stats.empty()) {
                InnerNode node = stats.pop();
                if (node.type == 0) {
                    System.out.println(node.node.value);
                } else {
                    if (node.node.right != null) {
                        stats.push(InnerNode.typeNode(1, node.node.right));
                    }
                    if (node.node.left != null) {
                        stats.push(InnerNode.typeNode(1, node.node.left));
                    }
                    stats.push(InnerNode.typeNode(0, node.node));
                }
            }
        }
    }

    public static void postOrder(Node root) {
        Stack<InnerNode> stats = new Stack<>();
        if (root != null) {
            stats.push(InnerNode.typeNode(1, root));
            while (!stats.empty()) {
                InnerNode node = stats.pop();
                if (node.type == 0) {
                    System.out.println(node.node.value);
                } else {
                    stats.push(InnerNode.typeNode(0, node.node));
                    if (node.node.right != null) {
                        stats.push(InnerNode.typeNode(1, node.node.right));
                    }
                    if (node.node.left != null) {
                        stats.push(InnerNode.typeNode(1, node.node.left));
                    }
                }
            }
        }
    }

}
