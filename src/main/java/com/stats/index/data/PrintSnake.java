package com.stats.index.data;

public class PrintSnake {
    public static void main(String[] args) {

    }

    static class Point {
        int i = 0;
        int j = 0;
    }

    public static void PrintSnake(int n, int m) {
        // line [i, j], i = (0, n - 1), j = (0, m - 1)
        int[][] a = {
            {},
            {},
            {},
            {},
        };
        a[0][0] = 1;
        Point cur = new Point();
        cur.i = 0;
        cur.j = 1;
        Point pre = new Point();
        pre.i = 0;
        pre.j = 0;
        while (cur.i + cur.j < (n + m - 1)) {
            a[cur.i][cur.j] = a[pre.i][pre.j] + 1;
            pre = cur;
            cur = NextPoint(cur, n, n);
        }
    }

    static Point AdjPoint(Point next, Point cur, int n, int m) {
        if (next.i < 0) {
            next.i = 0;
            next.j = cur.i + cur.j + 1;
        }
        if (next.j > m - 1) {
            next.j = m - 1;
            next.i = cur.i + cur.j + 1 - next.j;
        }
        if (next.j < 0) {
            next.j = 0;
            next.i = cur.i + cur.j + 1;
        }
        if (next.i > m - 1) {
            next.i = n - 1;
            next.j = cur.i + cur.j + 1 - next.i;
        }
        return next;
    }

    static Point NextPoint(Point cur, int n, int m) {
        if ((cur.i + cur.j) % 2 == 0) {
            Point next = new Point();
            next.i = cur.i - 1;
            next.j = cur.j + 1;
            AdjPoint(next, cur, n, m);
            return next;
        } else {
            Point next = new Point();
            next.i = cur.i + 1;
            next.j = cur.j - 1;
            AdjPoint(next, cur, n, m);
            return next;
        }
    }

}
