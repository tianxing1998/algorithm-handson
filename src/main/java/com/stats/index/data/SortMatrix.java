package com.stats.index.data;

/**
 * https://leetcode.cn/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof/
 * 面向问题思考
 * *=>递归分析 & 状态分析
 * *=>递归优化=>更大力度的裁剪=>递归树=>(极端是线性结构 & 有向无环图)
 *   f(n)=g(n-1)
 *   < f(n) = g(n/2)
 *   < f(n) = g(n / 10)
 */
public class SortMatrix {
    public static boolean findElement(int[][] a, int target) {
        /**
         * stats = (x, y) to check.
         * f(m, n) = g(f(m - 1, n) , f(m, n - 1))
         */
        int m = a.length;
        int n = a[0].length;
        int x = 0;
        int y = n - 1;
        while(x < m && y >= 0) { // new stat to check.
            if (a[x][y] == target) {
                return true;
            } else if (a[x][y] > target) {
                // column 全部大于
                y--;
            } else {
                // row 全部小于
                x++;
            }
        }
        return false;
    }
}
