package com.algorith.e;

import java.util.ArrayList;
import java.util.Stack;

/**
 * https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/
 * 问题推理 & 状态控制
 *   遍历找祖先节点。使用后续遍历，左右都能找到相同祖先。
 * 状态存储优化
 *   见二叉树遍历问题
 */
public class Test7 {
    public static void main(String[] args) {
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);

        n3.left = n5;
        n3.right = n1;
        n5.left = n6;
        n5.right = n2;
        n2.left = n7;
        n2.right = n4;
        n1.left = n0;
        n1.right = n8;
        Solution.solve(n3, n3, n1);
    }
    static class Node {
        int value;
        Node left;
        Node right;
        public Node(int val) {
            this.value = val;
        }
        static Node newInstance(int val) {
            Node n = new Node(val);
            n.value = val;
            return n;
        }
        public String toString() {
            return "val:" + this.value;
        }
    }
    static class NodeVisit {
        Node node;
        int type = 0; // 0 tree, 1 node.
        public static NodeVisit newInstance(Node n, int type) {
            NodeVisit visit = new NodeVisit();
            visit.node = n;
            visit.type = type;
            return visit;
        }

        public String toString() {
            return "val:" + this.node.value + ", type:" + this.type;
        }

    }
    static class Solution {
        static void collectParrents(Stack<NodeVisit> parrents, ArrayList<Node> collector) {
            Stack<NodeVisit> helper = new Stack<>();
            while (!parrents.empty()) {
                NodeVisit visit = parrents.pop();
                helper.push(visit);
                if (visit.type == 1) {
                    collector.add(visit.node);
                }
            }
            while (!helper.empty()) {
                NodeVisit visit = helper.pop();
                parrents.push(visit);
            }
        }
        /**
         * 后续遍历找到所有的祖先节点，然后匹配节点.
         * @param tree
         * @param p
         * @param q
         */
        static void solve(Node tree, Node p, Node q) {
            Stack<NodeVisit> stats = new Stack<>();
            stats.push(NodeVisit.newInstance(tree, 0));
            int toMatchCnt = 2;
            ArrayList<Node> parentsOfP = new ArrayList<>();
            ArrayList<Node> parentsOfQ = new ArrayList<>();
            while (toMatchCnt != 0 && !stats.empty()) {
                NodeVisit visit = stats.pop();
                if (visit.type == 1) {
                    if (visit.node == p) {
                        parentsOfP.add(p);
                        collectParrents(stats, parentsOfP);
                        toMatchCnt--;
                    }
                    if (visit.node == q) {
                        parentsOfP.add(q);
                        collectParrents(stats, parentsOfQ);
                        toMatchCnt--;
                    }
                } else {
                    stats.push(NodeVisit.newInstance(visit.node, 1));
                    if (visit.node.right != null) {
                        stats.push(NodeVisit.newInstance(visit.node.right, 0));
                    }
                    if (visit.node.left != null) {
                        stats.push(NodeVisit.newInstance(visit.node.left, 0));
                    }
                }
            }

            // find common
            int maxSize = Math.min(parentsOfP.size(), parentsOfQ.size());
            int i = 1;
            Node node = null;
            while (i <= maxSize) {
                Node p0 = parentsOfP.get(parentsOfP.size() - i);
                Node q0 = parentsOfQ.get(parentsOfQ.size() - i);
                if (p0 != q0) {
                    break;
                } else {
                    node = p0;
                }
                i++;
            }

            System.out.println(node.value);

        }
    }
}
