package com.algorithm.classics.sort;

import java.util.Arrays;

//    给定一个无序的整数数组，其中数据可能重复，要求将其中的奇数排序并放置到数组头部，偶数则维持当前顺序放置到数组后部，要求空间复杂度为O(1)
//    例：[1,2,3,1,6,3,4,9]
//    结果：[1,1,3,3,9,2,6,4]
public class FastSort1 {
    public static void main(String[] args) {
        int[] data = {1,2,3,1,6,3,4,9};

        sort1(data);

        System.out.println(Arrays.toString(data));

    }

    public static boolean Isjishu(int a) {
        return a % 2 != 0;
    }

    /**
     * 问题推理
     *  先分组解决偶数问题
     *  再对奇数做插入排序
     * @param data
     */
    public static void sort1(int[] data) {
        int p = -1;
        for (int i = data.length - 1; i >= 0; i --) {
            if (Isjishu(data[i])) {
                p = i;
                break;
            }
        }
        if (p == -1) {
            // all oushu
            // ranks
            return;
        }
        // 双状态机，一个主控状态
        // (cur, p]为奇数
        // (p, n)为偶数
        // (cur, p]
        //      => (cur-1, p]
        //      => (cur-1,]
        for (int cur = p - 1; cur >= 0; cur--) {
            if (Isjishu(data[cur])) {
                // insert to [cur, p]
            } else {
                int tmp = data[cur];
                data[cur] = data[p];
                data[p] = tmp;
                p--;
            }
        }
        insertSort(data, p);
    }

    public static void insertSort(int[] data, int p) {
        for (int i = 0 + 1; i <= p; i++) {
            for (int j = i; j >= 1; j--) {
                if (data[j] >= data[j - 1]) {
                    break;
                } else {
                    int tmp = data[j];
                    data[j] = data[j - 1];
                    data[j - 1] = tmp;
                }
            }
        }
    }

    public static void sort(int[] inputs) {
        int dIndex = -1;
        for (int i = 0; i < inputs.length; i++) {
            if (!Isjishu(inputs[i])) {
                dIndex = i;
                break;
            }
        }
        if (dIndex == -1) {
            return;
        }
        for (int i = dIndex + 1; i < inputs.length; i ++) {
            if (Isjishu(inputs[i])) {
                int tmp = inputs[dIndex];
                inputs[dIndex] = inputs[i];
                inputs[i] = tmp;
                dIndex++;
            }
        }
    }

    public static void test(int[] inputs) {
        int begin = 0;
        int end = inputs.length -1;
        int mid = 0;
        while(begin < end) {
            while(Isjishu(inputs[begin]) && begin < end) {
                begin++;
            }
            if (begin >= end) {
                if (Isjishu(inputs[end])) {
                    mid = begin;
                } else {
                    mid = begin - 1;
                }
                break;
            }
            while(!Isjishu(inputs[end]) && end > begin) {
                int tmp = inputs[end];
                for (int m = end; m < inputs.length - 1; m++) {
                    inputs[m] = inputs[m + 1];
                }
                inputs[inputs.length - 1] = tmp;
                end--;
            }
            if (end <= begin) {
                mid = begin;
                break;
            }
            int tmp = inputs[begin];
            inputs[begin] = inputs[end];
            inputs[end] = tmp;
            mid = begin;
        }
        //sort [0, mid]
        for (int i = 1; i <= mid; i++) {
            int j = i - 1;
            while(j >= 0 && inputs[j] > inputs[j + 1]) {
                int tmp = inputs[j];
                inputs[j] = inputs[j + 1];
                inputs[j + 1] = tmp;
                j--;
            }
        }
    }

    public static class FastSort {
        public static void main(String[] args) {
            int[] inputs = {3, 7, 9};
            fastSort(inputs);
            System.out.println(Arrays.toString(inputs));
        }
        public static void fastSort(int[] inputs) {
            // sort(0...n) = sort(0...m) + group(0....n) + sort(m+1...n), m >= 0 & (m + 1) <= n
            // sort(m, n)
            fastSort(inputs, 0, inputs.length - 1);
        }
        public static void fastSort(int[] inputs, int begin, int end) {
            if (begin >= end) {
                return;
            }
            int pivot = inputs[begin];
            int NextIdx = begin;
            for (int i = begin + 1; i <= end; i++) {
                if (inputs[i] >= pivot) {
                    continue;
                } else {
                    int tmp = inputs[i];
                    inputs[i] = inputs[NextIdx];
                    inputs[NextIdx] = tmp;
                    NextIdx++;
                }
            }
            if (begin == NextIdx) {
                // 需要保证 {begin} = pivot
                // 此时表明没有小于，因此不会发生交换
                fastSort(inputs, NextIdx + 1, end);
            } else {
                // 边界条件可能有问题
                fastSort(inputs, begin, NextIdx -1);
                fastSort(inputs, NextIdx, end);
            }
        }
    }

    public static class FastSort2 {
        public static void main(String[] args) {
            int[] arr = new int[] { 32, 54, 22, 12, 87, 31 };
            System.out.println(Arrays.toString(arr));
            quickSort(arr);
            System.out.println(Arrays.toString(arr));
        }

        static void quickSort(int[] arr) {   // 先左后右排序
            subQuickSortLow(arr, 0, arr.length - 1);
        }

        static void subQuickSortLow(int[] arr, int start, int end) {
            if (start < end) {
                int base = arr[start];
                int low = start;
                int high = end; // [low, high]是待验证的部分，low & high是未验证的状态...

                while (low < high) {
                    while (low < high && arr[low] <= base) {
                        low++;
                    }
                    while (low < high && arr[high] >= base) {
                        high--;
                    }
                    if (low < high) {
                        swap(arr, low, high);
                    }
                }
                // low出的data未被测试过
                if (arr[low] > base) {
                    for (int i = start; i < low - 1; i++) {
                        arr[i] = arr[i + 1];
                    }
                    arr[--low] = base;
                } else {
                    swap(arr, start, low);
                }
                subQuickSortLow(arr, start, low - 1);
                subQuickSortLow(arr, low + 1, end);
            }
        }

        static void swap(int[] arr, int addr1, int addr2) {
            if (addr1 == addr2) {
                return;
            }
            arr[addr1] = arr[addr1] ^ arr[addr2];
            arr[addr2] = arr[addr1] ^ arr[addr2];
            arr[addr1] = arr[addr1] ^ arr[addr2];
        }
    }
}
