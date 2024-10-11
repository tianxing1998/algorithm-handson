package com.algorithm.classics.sort;

import java.util.Arrays;

/**
 * TreeTable => 树形表 => 完全二叉树
 * 堆 & 锦标赛数表
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] array1 = {3, 4, 8, 2, 3, 7};
        heapSort(array1);
        System.out.println(Arrays.toString(array1));
    }

    /**
     * [0] 插入 (1) => [0, 1], 插入(2) => ... > [0, k -1](大顶堆)
     * [0, k- 1] + (k), 交换如果(0) > (k), 调整堆，最后得到最小的k个数据
     * @param arrays
     */
    static void heapSort(int[] arrays) {
        int father = arrays.length / 2 - 1;
        for (; father >= 0; father--) {
            int leftChild = 2 * ( father + 1 ) - 1;
            int rightChild = leftChild + 1;
            if (rightChild < arrays.length) {
                if (arrays[father] > arrays[rightChild]) {
                    int tmp = arrays[father];
                    arrays[father] = arrays[rightChild];
                    arrays[rightChild] = tmp;
                }
            }
            if (leftChild < arrays.length) {
                if (arrays[father] > arrays[leftChild]) {
                    int tmp = arrays[father];
                    arrays[father] = arrays[leftChild];
                    arrays[leftChild] = tmp;
                }
            }
        }
    }
 }
