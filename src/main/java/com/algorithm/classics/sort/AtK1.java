package com.algorithm.classics.sort;

/**
 * [3,2,1,5,6,4] && k = 2
 * [3,2,3,1,2,4,5,5,6] && k = 4
 *
 */
public class AtK1 {
    public static void main(String[] args) {
        // Scanner input=new Scanner(System.in);
        // String str=input.next();
        int[] a = {3,2,1,5,6,4};
        System.out.println(topk2(a, 5));
        System.out.println( a[5 - 1] );
    }

    /**
     * k >= 1.
     * @param arr
     * @param k
     * @return
     */
    public static int topk(int[] arr, int k) {
        return topk(arr, 0, arr.length - 1, k);
    }

    /**
     * k >= 1.
     * @param arr
     * @param k
     * @return
     */
    public static int topk2(int[] arr, int k) {
        return topk2(arr, 0, arr.length - 1, k);
    }

    public static int topk2(int[] a, int begin, int end, int topk) {
        int next = begin;
        int pivot = a[begin];
        int i = begin + 1;
        while (i <= end) {
            if (a[i] >= pivot) {
                i++;
            } else {
                int tmp = a[i];
                a[i] = a[next];
                a[next] = tmp;
                next++;
            }
        }
        if (begin == next) {
            if (topk == 1) {
                return a[begin];
            } else {
                return topk2(a, begin + 1, end, topk - 1);
            }
        } else {
            int length1 = next - begin;
            if (length1 >= topk) {
                return topk2(a, begin, next - 1, topk);
            } else {
                return topk2(a, next, end, topk - length1);
            }
        }
    }

    public static int topk(int[] arr, int begin, int end, int topk) {
        /**
         * (end - begin) >= topk >= 1.
         */
        if (begin > end || topk <= 0) {
            return 0;
        }

        int p = arr[begin];
        int cur = begin + 1;
        int Next = begin;
        while(cur <= end) {
            if (arr[cur] >= p) {
                cur++;
            } else {
                int tmp = arr[Next];
                arr[Next] = arr[cur];
                arr[cur] = tmp;
                cur++;
                Next++;
            }
        }
        if (Next == begin) {
            return topk(arr, begin + 1, end, topk - 1);
        } else {
            if (Next - begin >= topk) {
                return topk(arr, begin, Next - 1, topk);
            } else {
                return topk(arr, Next, end, topk - (Next - begin));
            }
        }
    }
}