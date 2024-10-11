package com.stats.index.data;

/**
 * https://leetcode.cn/problems/maximal-rectangle/
 */
public class MaxArea {
public static void main(String[] args) {

    // 算法题复杂了容易出bug => 合并规则有业务bug => (3 + 2 != all case)
        // 合并规则太复杂了... => 有corner case...
    // 50行左右已经比较多了...
    char[][] matrix = {{'1','0','1','0','0'},{'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}};
    System.out.println(Solution.maximalRectangle(matrix));
}

}

class Point {
    int x;
    int y;
    public String toString() {
        return "x:" + x + "; y:" + y;
    }
}

class PointStats {
    Point row;
    Point column;
    Point rectangle;
    public void newRowStats(PointStats stats, Point p) {
        if (row == null) {
            stats.row = p;
            return;
        } else {
            stats.row = row;
        }
    }
    public void newColStats(PointStats stats, Point p) {
        if (column == null) {
            stats.column = p;
            return;
        } else {
            stats.column = column;
        }
    }

    public String toString() {
        String s = "";
        s += "row{" + ((row != null) ? row.toString() : "null")  + "}";
        s += "column{" + ((column != null) ? column.toString() : "null")  + "}";
        s += "rectangle{" + ((rectangle != null) ? rectangle.toString() : "null")  + "}";
        return s;
    }

    static public void newRectangleStats(PointStats[][] stats, PointStats pointStat,  Point p) {
        int x = p.x;
        int y = p.y;
        x = Math.min(x, pointStat.column.x);
        y = Math.min(y, pointStat.row.y);
        Point base = new Point();
        base.x = x;
        base.y = y;

        PointStats pStatRec = stats[p.x - 1][p.y - 1];

        Point rectangle = new Point();
        rectangle.x = base.x;
        rectangle.y = base.y;
        if (pStatRec.rectangle != null) {
            rectangle.x= Math.max(rectangle.x, pStatRec.rectangle.x);
            rectangle.y = Math.max(rectangle.y, pStatRec.rectangle.y);
        } else {
            rectangle.x = p.x;
            rectangle.y = p.y;
        }

        Point rowtangle = new Point();
        rowtangle.x = base.x;
        rowtangle.y = base.y;
        if (pStatRec.row != null) {
            rowtangle.x = Math.max(rowtangle.x, pStatRec.row.x);
            rowtangle.y = Math.max(rowtangle.y, pStatRec.row.y);
        } else {
            rowtangle.x = p.x;
            rowtangle.y = p.y;
        }

        Point coltangle = new Point();
        coltangle.x = base.x;
        coltangle.y = base.y;
        if (pStatRec.row != null) {
            coltangle.x = Math.max(coltangle.x, pStatRec.column.x);
            coltangle.y = Math.max(coltangle.y, pStatRec.column.y);
        } else {
            coltangle.x = p.x;
            coltangle.y = p.y;
        }

        Point[] points = new Point[3];
        points[0] = rectangle;
        points[1] = rowtangle;
        points[2] = coltangle;
        int[] area = new int[3];
        area[0] = Math.max(0, (p.y - rectangle.y + 1) * ((p.x - rectangle.x + 1)));
        area[1] = Math.max(0, (p.y - rowtangle.y + 1) * ((p.x - rowtangle.x + 1)));
        area[2] = Math.max(0, (p.y - coltangle.y + 1) * ((p.x - coltangle.x + 1)));

        int maxIndex = 0;
        if (area[maxIndex] < area[1]) {
            maxIndex = 1;
        }
        if (area[maxIndex] < area[2]) {
            maxIndex = 2;
        }
        pointStat.rectangle = points[maxIndex];
    }
    public int area(Point p) {
        int area = 0;
        if (row != null) {
            area = Math.max(area, p.y - row.y + 1);
        }
        if (column != null) {
            area = Math.max(area, p.x - column.x + 1);
        }
        if (this.rectangle != null) {
            area = Math.max(area, (p.y - this.rectangle.y + 1) * ((p.x - rectangle.x + 1)));
        }
        return area;
    }
}

class Solution {
    static public int maximalRectangle(char[][] matrix) {
        int maxArea = 0;
        PointStats[][] stats = new PointStats[matrix.length][matrix[0].length];
        PointStats stat0 = new PointStats();
        stats[0][0] = stat0;
        if (matrix[0][0] == '1') {
            Point p = new Point();
            p.x = 0;
            p.y = 0;
            stat0.column = p;
            stat0.row = p;
            stat0.rectangle = p;
            maxArea = Math.max(maxArea, stat0.area(p));
        }
        for (int col = 1; col < matrix[0].length; col ++) {
            Point p = new Point();
            p.x = 0;
            p.y = col;
            if (matrix[0][col] == '0') {
                stats[0][col] = new PointStats();
            } else {
                PointStats stats1 = new PointStats();
                stats[0][col - 1].newRowStats(stats1, p);
                stats1.column = p;
                stats1.rectangle = p;
                stats[0][col] = stats1;
                maxArea = Math.max(maxArea, stats1.area(p));
            }
        }

        for (int row = 1; row < matrix.length; row ++) {
            Point p = new Point();
            p.x = row;
            p.y = 0;
            if (matrix[row][0] == '0') {
                stats[row][0] = new PointStats();
            } else {
                PointStats stats1 = new PointStats();
                stats[row - 1][0].newColStats(stats1, p);
                stats1.row = p;
                stats1.rectangle = p;
                stats[row][0] = stats1;
                maxArea = Math.max(maxArea, stats1.area(p));
            }
        }

        for (int row = 1; row < matrix.length; row ++) {
            for (int col = 1; col < matrix[0].length; col ++) {
                Point p = new Point();
                p.x = row;
                p.y = col;
                if (matrix[row][col] == '0') {
                    stats[row][col] = new PointStats();
                } else {
                    PointStats stats1 = new PointStats();
                    stats[row][col - 1].newRowStats(stats1, p);
                    stats[row - 1][col].newColStats(stats1, p);
                    PointStats.newRectangleStats(stats, stats1, p);
                    stats[row][col] = stats1;
                    maxArea = Math.max(maxArea, stats1.area(p));
                    System.out.println("row:" + row + "; col:" + col + "; area:" + maxArea + "" + "");
                    System.out.println(stats1.toString());
                }
            }
        }

        return maxArea;
    }
}