package com.algorithm.arch;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;

interface Iterator {
    boolean hasNext();
    int next();
}
class Node {
    int value;
    List<Node> input;
    List<Node> output;
}
class Graph {
    List<Node> nodes;
}

public class GraphIterator implements Iterator{
    Graph graph;
    HashSet<Node> queued = new HashSet<>(); // scanned && scanning
    Queue<Node> visiting = new ArrayDeque<>(); // inputing = 0, scanning
    public GraphIterator(Graph graph) {
        this.graph = graph;
        for (Node node : this.graph.nodes) {
            if (node.input.isEmpty()) {
                visiting.add(node);
                queued.add(node);
            }
        }
    }
    @Override
    public boolean hasNext() {
        return !this.visiting.isEmpty();
    }

    @Override
    public int next() {
        Node cur = this.visiting.poll();
        for (Node child : cur.output) {
            if (!this.queued.contains(child)) {
                this.queued.add(child);
                this.visiting.add(child);
            }
        }
        return cur.value;
    }
}
