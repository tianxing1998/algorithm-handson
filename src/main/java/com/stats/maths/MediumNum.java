package com.stats.maths;

import java.util.PriorityQueue;

/**
 * *
 *
 */
public class MediumNum {
    /** initialize your data structure here. */
    MediumNum() {
        this.leftMax = new PriorityQueue<>();
        this.rightMin = new PriorityQueue<>();
    }

    void addNum(int num) {
        if (leftMax.size() == rightMin.size()) {
            this.rightMin.add(num);
        } else {
            this.leftMax.add(this.rightMin.poll());
            this.rightMin.add(num);
        }
    }

    double findMedian() {
        if (this.rightMin.size() == this.leftMax.size()) {
            return ( this.leftMax.peek() + this.rightMin.peek() ) % 2;
        } else {
            return this.leftMax.peek();
        }
    }
    private PriorityQueue<Integer> leftMax;
    private PriorityQueue<Integer> rightMin;
}


