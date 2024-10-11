package com.algorithm.classics.sort;

import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
        int[] data = {2,5,3,8,1};
        sort(data);
        System.out.println(Arrays.toString(data));
    }

    public static void sort(int[] inputs) {
        // sort(n) = sort(n - 1) + max(0...n)
        for (int i = inputs.length - 1; i >= 0; i--) {
            sort(inputs, i);
        }
    }
    public static void sort(int[] inputs, int end) {
        // 递归性质
        for (int i = 1 ; i <= end; i++) {
            if (inputs[i-1] > inputs[i]) {
                int tmp = inputs[i - 1];
                inputs[i - 1] = inputs[i];
                inputs[i] = tmp;
            }
        }
    }
}
