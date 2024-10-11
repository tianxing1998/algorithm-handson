package com.algorithm.classics.kernel;

public class SparseMatMul {
    /**
     * https://www.cnblogs.com/lightwindy/p/9692836.html
     * @param a
     * @param b
     * @return
     */
    public static int[][] matMul0(int[][] a, int[][] b) {
        return null;
    }

    /**
     * 稀疏表示，data & col & row
     */
    static class SparseMatrix {
        int[] data; // [V, 1]
        int[] colIdx; // [V, 1]
        int[] rowScan; // [row + 1, 1], row = [row, row + 1)
        int row;
        int col;
    }

    /**
     * [M, N] = [M, K] * [K, N] = Sparse[M, K] * Sparse[N, K]
     * @param a
     * @param b
     * @return
     */
    public int[][] matMul1(SparseMatrix a, SparseMatrix b) {
        if (a.col != b.col) {
            return null;
        }
        int[][] result = new int[a.row][b.row];
        for (int m = 0; m < a.row; m++) {
            for (int n = 0; n < b.row; n++) {
                int mRowStart = a.rowScan[m];
                int mRowEnd = a.rowScan[m + 1]; // [mRowStart, mRowEnd)
                int nRowStart = b.rowScan[n];
                int nRowEnd = b.rowScan[n + 1]; // [mRowStart, mRowEnd)
                while (mRowStart < mRowEnd && nRowStart < nRowEnd) {
                    int mIdx = a.colIdx[mRowStart];
                    int nIdx = b.colIdx[nRowStart];
                    if (nIdx == mIdx) {
                        result[m][n] += a.data[mRowStart] * b.data[nRowStart];
                        mRowStart++;
                        nRowStart++;
                    } else if (nIdx > mIdx) {
                        mRowStart++;
                    } else {
                        nRowStart++;
                    }
                }
            }
        }
        return result;
    }
}
