package com.algorithm.classics.structs.Lists;

/**
 * 给定 m 个数组，每个数组都已经按照升序排好序了。现在你需要从两个不同的数组中选择两个整数（每个数组选一个）并且计算它们的距离。两个整数 a 和 b 之间的距离定义为它们差的绝对值 |a-b| 。你的任务就是去找到最大距离
 *
 * 示例 1：
 *
 * 输入：
 * [[1,2,3],
 *  [4,5],
 *  [1,2,3]]
 * 输出： 4
 * 解释：
 * 一种得到答案 4 的方法是从第一个数组或者第三个数组中选择 1，同时从第二个数组中选择 5 。
 *
 *
 * 注意：
 *
 * 每个给定数组至少会有 1 个数字。列表中至少有两个非空数组。
 * 所有 m 个数组中的数字总数目在范围 [2, 10000] 内。
 * m 个数组中所有整数的范围在 [-10000, 10000] 内。
 */
public class Test6 {
    public static void main(String[] args) {
        int [][] arr = {
            {1,2,3},
            {4,5},
            {1,2,3}
        };
        System.out.println(MaxDistance(arr));
    }

    static int max(int amin, int amax, int bmin, int bmax) {
        int distance0 = Math.abs(amax - bmin);
        int distance1 = Math.abs(amax - bmax);
        int distance2 = Math.abs(amin - bmin);
        int distance3 = Math.abs(amin - bmax);
        return Math.max( Math.max(distance0, distance1),  Math.max(distance2, distance3));
    }

    public static int MaxDistance(int[][] data) {
        int[][] m = new int[data.length][2];
        for (int i = 0; i < data.length ; i++) {
            m[i][0] = data[i][0];
            m[i][1] = data[i][data[i].length - 1];
        }
        int Max = Integer.MIN_VALUE;
        int min = m[0][0];
        int max = m[0][1];
        for (int i = 1; i < m.length ; i++) {
            int max0 = max(min, max, m[i][0], m[i][1] );
            Max = Math.max(max0, Max);
            min = Math.min(min, m[i][0]);
            max = Math.max(max, m[i][1]);
        }
        return Max;
    }
}
