package com.algorithm.classics.kernel;

public class SparseMatrixMultiply {
    public static class SparseMatrix {
        int[] value; // [V, 2(value, column)]
        int[] indices; // [R, 2(row_id, row_size)]
        int storage; // 1 for row major, 2 for column major
        int row;
        int column;
    }

    /**
     * [m, k] * [k, n] = sum(m, n)( [1, k] * [k, 1] )
     * @param a [m, k] by row
     * @param b [k, n] by column
     * @return [m, n]
     */
    public static int[][] mul(SparseMatrix a, SparseMatrix b) {
        int[][] result = new int[a.row][b.column];
        int rStart = 0;
        for (int rIndex = 0; rIndex < a.indices.length; rIndex += 2) {
            int cStart = 0;
            for (int cIndex = 0; cIndex < b.indices.length; cIndex += 2) {
                // [1 , k] * [k, 1]
                mulVec(result,a, a.indices[rIndex], rStart, a.indices[rIndex + 1], b, b.indices[cIndex], cStart, b.indices[cIndex + 1]);
                cStart += b.indices[cIndex + 1] * 2;
            }
            rStart += a.indices[rStart + 1] * 2;
        }
        return result;
    }

    public static int Value(SparseMatrix a, int start, int pos) {
        return a.value[start + pos * 2 ];
    }

    public static int Minor(SparseMatrix a, int start, int pos) {
        return a.value[start + pos * 2  + 1];
    }

    public static  void mulVec(int[][] result,
                               SparseMatrix a,  int r, int rStart , int rLength,
                               SparseMatrix b, int c, int cStart , int cLength) {
        int i = 0;
        int j = 0;
        while (i < rLength && j < cLength) {
            int indexI = Minor(a, rStart, i);
            int indexJ = Minor(b, cStart, j);
            if (indexI == indexJ) {
                int valueI = Value(a, rStart, i);
                int valueJ = Value(b, cStart, j);
                result[r][c] += valueI * valueJ;
            } else if (indexI > indexJ) {
                j++;
            } else {
                i++;
            }
        }
    }
}
