package com.stats.index.data;

public class AZip {
    /**
     * https://biaodigit.github.io/LeetCode/0006/#%E9%A2%98%E7%9B%AE%EF%BC%9Az-%E5%AD%97%E5%BD%A2%E5%8F%98%E6%8D%A2
     */
    public static String zip(String s, int n) {
        /**
         * 递归分析 & 状态分析
         * F[n, s] => [2n - 2, s]
         * stats: (0, j) = (2n - 2) * j = N * j
         * stats: (i, j) = ( Nj + i ), ( (0, j + 1) - i  )
         */
        StringBuilder result = new StringBuilder();
        int N = (2 * n - 2);
        for (int i = 0; i < n; i ++) {
            if (i == 0 || i == (n - 1)) {
                int jStart = i;
                while(jStart < s.length()) {
                    result.append( s.charAt(jStart) );
                    jStart += N;
                }
            } else {
                int jStart = i;
                int col =0;
                while(jStart < s.length()) {
                    result.append( s.charAt(jStart) );
                    jStart += N;
                    col++;
                    if (col * N - i < s.length()) {
                        result.append( s.charAt(col * N - i) );
                    }
                }
            }
        }

        return result.toString();

    }

    public static void main(String[] args) {
        String result = zip("LEETCODEISHIRING", 3);
        System.out.println(result); // LCIRETOESIIGEDHN

        result = zip("LEETCODEISHIRING", 4);
        System.out.println(result); // LDREOEIIECIHNTSG
    }
}
