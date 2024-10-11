package com.algorithm.classics.structs.Graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * 大部分图论算法都是图的变形(基于规则构建新图，比如差分图等) 和 图的遍历.
 * 图的二分遍历
 *  单侧自成新图.
 */
public class Graphs {
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
     * 二分图. g1 + g2 = g
     *      层次遍历的递归写法 = 图级状态机
     *      节点状态机：S => queued => visit(Actions & 调度Next) => S.
     * @param g
     */
    public static void bfs(List<Node> g) {
        HashSet<Node> queued = new HashSet<>(); // queued = visited + visiting
        Queue<Node> queue = new ArrayDeque<>(); // visiting(二分图links)
        for (int i = 0; i < g.size(); i++) {
            if (!queued.contains(g.get(i))) {
                queue.add(g.get(i));
                queued.add(g.get(i));
                while (!queue.isEmpty()) {
                    Node cur = queue.poll();
                    System.out.println("visit:" + cur.value);
                    for (Node next : cur.nexts) {
                        if (!queued.contains(next)) {
                            queued.add(next);
                            queue.add(next);
                        }
                    }
                }
            }
        }
    }
    public static void dfs1(List<Node> g) {
        HashSet<Node> queued = new HashSet<>(); // queued = visited + to_be_visit
        for (int i = 0; i < g.size(); i++) {
            if (!queued.contains(g.get(i))) {
                dfs1(g.get(i), queued);
            }
        }
    }
    public static void dfs1(Node n, HashSet<Node> queued) {
        System.out.println("visit:" + n.value);
        for (int i = 0; i < n.nexts.size(); i++) {
            if (!queued.contains(n.nexts.get(i))) {
                dfs1(n.nexts.get(i), queued);
            }
        }
    }

    /**
     * 递归写法更简单.
     * @param g
     */
    public static void bfs1(List<Node> g) {
        // 广度优先不太适合写递归算法..
    }

    public static class GraphScheduler {
        public static void main(String[] args) {
            Node node0 = new Node(0);
            Node node1 = new Node(1);
            Node node2 = new Node(2);
            Node node3 = new Node(3);
            Node node4 = new Node(4);
            Node node5 = new Node(5);
            Node node6 = new Node(6);
            Node node7 = new Node(7);
            Node node8 = new Node(8);

            node0.successors.add(node2);
            node1.successors.add(node2);
            node1.successors.add(node3);
            node2.successors.add(node4);
            node3.successors.add(node5);
            node4.successors.add(node5);
            node4.successors.add(node6);
            node5.successors.add(node8);
            node6.successors.add(node7);

            Graph g = new Graph();
            g.nodes.add(node0);
            g.nodes.add(node1);
            g.nodes.add(node2);
            g.nodes.add(node3);
            g.nodes.add(node4);
            g.nodes.add(node5);
            g.nodes.add(node6);
            g.nodes.add(node7);
            g.nodes.add(node8);

            executeGraph(g);
        }

        public static class Node {
            int node_id;
            int value;
            public Node(Integer id) {
                this.node_id = id;
                this.value = id;
                this.successors = new ArrayList<>();
            }
            Function<Integer, Integer> functor = new Function<Integer, Integer>() {
                @Override
                public Integer apply(Integer val) {
                    System.out.println("value:" + val + ", thread:" + Thread.currentThread().getName());
                    return 0;
                }
            };
            List<Node> successors;
        }

        public static class Graph {
            List<Node> nodes = new ArrayList<>();
        }

        public static class ExecutorStats {
            ArrayList<AtomicInteger> predecessorRefs = new ArrayList<>();
            CountDownLatch latch;
        }

        public static class RunningNode implements Runnable {
            Node node;
            Executor executor;
            ExecutorStats stats;

            public RunningNode(Executor e, ExecutorStats stats, Node node) {
                this.executor = e;
                this.node = node;
                this.stats = stats;
            }


            @Override
            public void run() {
                this.node.functor.apply(this.node.value);
                this.stats.latch.countDown();
                System.out.println("latch: " + stats.latch.getCount() + ", val : " + this.node.value);
                if (!this.node.successors.isEmpty()) {
                    Node cur = null;
                    for (Node successor : this.node.successors) {
                        int pre = stats.predecessorRefs.get(successor.node_id).decrementAndGet();
                        if (pre == 0 && cur == null) {
                            cur = successor;
                        } else if (pre == 0) {
                            this.executor.execute(new RunningNode(this.executor, stats, successor));
                        }
                    }
                    if (cur != null) {
                        while (cur != null) {
                            cur.functor.apply(cur.value);
                            this.stats.latch.countDown();
                            System.out.println("latch: " + stats.latch.getCount() + ", val : " + cur.value);
                            List<Node> successors = cur.successors;
                            cur = null;
                            if (!successors.isEmpty()) {
                                for (Node successor : successors) {
                                    int pre = stats.predecessorRefs.get(successor.node_id).decrementAndGet();
                                    if (pre == 0) {
                                        if (cur == null) {
                                            cur = successor;
                                        } else {
                                            this.executor.execute(new RunningNode(this.executor, stats, successor));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                System.out.println("death thread:" + Thread.currentThread().getName());

            }
        }

        public static void executeGraph(Graph g) {
            ExecutorStats stats = new ExecutorStats();
            stats.predecessorRefs = new ArrayList<>(g.nodes.size());
            for (int i = 0; i < g.nodes.size(); i++) {
                stats.predecessorRefs.add(new AtomicInteger());
            }
            stats.latch = new CountDownLatch(g.nodes.size());
            for (Node n : g.nodes) {
                for (Node successor : n.successors) {
                    stats.predecessorRefs.get(successor.node_id).incrementAndGet();
                }
            }
            AtomicInteger tID = new AtomicInteger(0);
            ExecutorService e = Executors.newFixedThreadPool(10, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r, "tid:" + tID.getAndIncrement());
                    return t;
                }
            });

            RunningNode mine = null;
            for (Node n : g.nodes) {
                if (stats.predecessorRefs.get(n.node_id).get() == 0) {
                    if (mine == null) {
                        mine = new RunningNode(e, stats, n);
                    } else {
                        e.execute(new RunningNode(e, stats, n));
                    }
                }
            }
            mine.run();

            try {
                System.out.println("test");
                stats.latch.await( 3, TimeUnit.SECONDS);
                System.out.println("over latch:" + stats.latch.getCount());

                e.shutdownNow();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }
}
