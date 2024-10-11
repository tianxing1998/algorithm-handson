package com.algorithm.classics.sort;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MergeSort {
    public static void main(String[] args) {

    }

    static class Element {
        int li;
        int i;
        int value;
    }

    public void sort(int[][] lists, int[] result) {
        Queue<Element> queue = new PriorityQueue<>(new Comparator<Element>() {
            @Override
            public int compare(Element o1, Element o2) {
                return o1.value < o2.value ? -1 : (o1.value == o2.value? 0 : 1);
            }
        });
        for (int i = 0; i < lists.length; i++) {
            if (lists[i].length > 0) {
                Element e = new Element();
                e.li = i;
                e.i = 0;
                e.value = lists[i][0];
                queue.add(e);
            }
        }
        int i = 0;
        while (!queue.isEmpty()) {
            Element mx = queue.poll();
            result[i++] = mx.value;
            if (mx.i < lists[mx.i].length - 1) {
                mx.i++;
                queue.add(mx);
            }
        }
    }

}
