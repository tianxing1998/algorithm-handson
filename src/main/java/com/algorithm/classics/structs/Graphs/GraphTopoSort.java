package com.algorithm.classics.structs.Graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GraphTopoSort {
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);

        node1.nexts.add(node2);
        node1.nexts.add(node4);
        node2.nexts.add(node3);
        node4.nexts.add(node3);
        node4.nexts.add(node6);
        node3.nexts.add(node5);

        Sort(node1);

        System.out.println("");

    }
    static class Node {
        int value;
        int refCnt; // <=> Map<Node, RefCount>
        List<Node> nexts = new ArrayList<>();
        public Node(int value) {
            this.refCnt = 0;
            this.value = value;
        }
        public String toString() {
            return "value=" + value + ", refCnt" + this.refCnt;
        }
    }

    /**
     * S => g1 => g2 => E(g3).
     * 层次遍历.
     * @param root
     */
    public static void Sort(Node root) {
        // 构建RefCnt
        Queue<Node> nodes = new LinkedList<>(); // to_be_visit
        nodes.add(root);
        HashSet<Node> queued = new HashSet<>(); // visited & to_be_visit
        queued.add(root);
        /**
         * 节点状态机：Start => Found(Queued) => Visit => End.
         */
        while (!nodes.isEmpty()) {
            Node cur = nodes.poll();
            System.out.println("visit:" + cur.value);
            for (Node next : cur.nexts) {
                next.refCnt++;
                if (!queued.contains(next)) {
                    nodes.add(next);
                    queued.add(next);
                    System.out.println("queuing:" + next.value);
                }
            }
        }

        // Top遍历
        nodes.clear();
        nodes.add(root);
        queued.clear();
        while (!nodes.isEmpty()) {
            Node cur = nodes.poll();
            System.out.println(cur.value);
            for (Node next : cur.nexts) {
                next.refCnt--;
                if (next.refCnt == 0) {
                    nodes.add(next);
                }
            }
        }
    }
}
