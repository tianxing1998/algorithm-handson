package com.algorith.e;

/**
 * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
 * <p>
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 * <p>
 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
 */
public class TestNNN {

    public static void main(String[] args) {
        int[] a = {1, 3, 7, 0, 0, 0, 0};
        int[] b = {2, 4, 6, 8};
        sort(a, 3, b);
        System.out.println("x");
    }

    static int idxOf(int t, int m) {
        if (t < m) {
            return t;
        } else {
            return t - m;
        }
    }

    /**
     * 137
     * 2468
     * 4678123
     * <p>
     * 1238467
     * 1234867
     * 1234687
     * 1234678
     *
     * @param a
     * @param a0
     * @param b
     */
    static public void sort(int[] a, int a0, int[] b) {
        int i = 0;
        int j = 0;
        int t = a0;
        while (i < a0 && j < b.length) {
            if (a[i] <= b[j]) {
                a[idxOf(t, a.length)] = a[i];
                i++;
            } else {
                a[idxOf(t, a.length)] = b[j];
                j++;
            }
            t++;
        }
        while (i < a0) {
            a[idxOf(t, a.length)] = a[i];
            i++;
            t++;
        }
        while (j < b.length) {
            a[idxOf(t, a.length)] = b[j];
            j++;
            t++;
        }


        int seg1 = 0;
        int x = a0;
        int y = b.length;
        while (x > 0 && y > 0) {
            int min = Math.min(x, y);
            int seg2 = seg1 + x;
            for (int s = 0; s < min; s++) {
                int tmp = a[seg1 + s];
                a[seg1 + s] = a[seg2 + s];
                a[seg2 + s] = tmp;
            }
            seg1 = seg1 + min;
            if (x == y) {
                break;
            } else {
                if (x > min) {
                    x = x - min;
                }
                if (y > min) {
                    y = y - min;
                }
            }
        }
    }
}
