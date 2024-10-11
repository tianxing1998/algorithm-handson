package com.algorithm.classics.structs.Trees;

public class BTrees {
    public static class Node {
        int[] values; // N
        Node[] subNodes; // N + 1
    }
    private static class Arrays {
        /**
         * 找到返回index，找不到返回插入index * (-1)
         * [1,3,8,10], -0 for 0; 5 for -2
         * @param values
         * @param target
         * @return
         */
        static int find(int[] values, int target) {
            int low = 0;
            int high = values.length - 1;
            while (low <= high) {
                int mid = (low + high ) / 2;
                if (values[mid] == target) {
                    return mid;
                } else if (values[mid] > target) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            return low * -1;
        }
    }
}
