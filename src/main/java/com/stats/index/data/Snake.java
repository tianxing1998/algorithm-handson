package com.stats.index.data;

/**
 * https://blog.csdn.net/u013647382/article/details/51684815
 * https://juejin.cn/post/7155089704223211551
 */
public class Snake {
    public static void PrintSnake(int m , int n) {
        // 面向问题分析
        // 状态遍历无后效性设计 => 状态图的有序顺序性 or 状态环(可枚举性)
        // 状态stat分析: (x, y) => f(4 * direction, x, y) = select[ down(x + 1, y), left(x, y - 1), up(x - 1, y ), right(x , y + 1) ]
    }

    /**
     *
     * @param n
     */
    public static void PrintS(int n) {

    }

    /**
     *
     * @param n
     */
    public static void PrintS3(int n) {

    }
}
