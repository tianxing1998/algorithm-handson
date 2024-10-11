package com.stats.index.data;

import java.util.Arrays;

public class Matrix1 {
    /**
     * 1 5 9    2
     * 12 13 14 6
     * 8 16 15 10
     * 4 11 7   3
     * @param args
     */
    public static void main(String[] args) {
        int[][] data = {
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0}
        };
        FillData(data);
        for(int[] d : data) {
            System.out.println(Arrays.toString(d));
        }
    }

    static class Point {
        int x;
        int y;
        static Point nextA(Point a, int i ) {
            Point p =  new Point();
            p.x = a.x;
            p.y = a.y + i;
            return p;
        }
        static Point nextB(Point b, int i ) {
            Point p =  new Point();
            p.x = b.x + i;
            p.y = b.y;
            return p;
        }
        static Point nextC(Point c, int i) {
            Point p =  new Point();
            p.x = c.x;
            p.y = c.y - i;
            return p;
        }
        static Point nextD(Point d, int i) {
            Point p =  new Point();
            p.x = d.x - i;
            p.y = d.y;
            return p;
        }
    }
    public static void FillData(int[][] data) {
        int n = data.length;
        int next = 0;
        for (int circle = 0; circle < n / 2; circle ++) {
            Point a = new Point();
            a.x = circle;
            a.y = circle;
            Point b = new Point();
            b.x = circle;
            b.y = n - 1 - circle;
            Point c = new Point();
            c.x = n - 1 - circle;
            c.y = n - 1 - circle;
            Point d = new Point();
            d.x = n - 1 - circle;
            d.y = circle;
            for (int i = 0; i < b.y - a.y; i++) {
                Point nextPoint = Point.nextA(a, i );
                next++;
                data[nextPoint.x][nextPoint.y] = next;
                nextPoint = Point.nextB(b, i);
                next++;
                data[nextPoint.x][nextPoint.y] = next;
                nextPoint = Point.nextC(c, i);
                next++;
                data[nextPoint.x][nextPoint.y] = next;
                nextPoint = Point.nextD(d, i );
                next++;
                data[nextPoint.x][nextPoint.y] = next;
            }
            if (b.y == a.y) {
                Point nextPoint = Point.nextA(a, 0);
                next++;
                data[nextPoint.x][nextPoint.y] = next;
            }
        }
    }
}
