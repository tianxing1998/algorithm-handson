package com.algorithm.classics.search;

public class BinarySearch {
    public static void main(String[] args) {
        int[] array = {3,5,8};
        System.out.println(binarySearch2(array, 4));
    }

    public static int binarySearch(int[] input, int target) {
        int begin = 0;
        int end = input.length -1;
        while (begin <= end) {
            int mid = (begin + end) / 2;
            if (input[mid] == target) {
                return mid;
            } else if (input[mid] < target) {
                begin = mid + 1;
            } else {
                end = mid -1;
            }
        }
        return -1 * begin;
    }

    public static int binarySearch2(int[] input, int target) {
        int begin = 0;
        int end = input.length - 1;
        while (begin <= end) {
            int mid = (begin + end) / 2;
            if (input[mid] == target) {
                return mid;
            } else if (input[mid] > target) {
                end = mid - 1;
            } else {
                begin = mid + 1;
            }
        }
        return begin * -1;
    }
}
