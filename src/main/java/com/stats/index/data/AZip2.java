package com.stats.index.data;

public class AZip2 {
    public static void main(String[] args) {
        String data = "LEETCODEISHIRING";
        String output = Solution.conv(data, 3);
        System.out.println(output);
    }
    /**
     * 输入: s = "LEETCODEISHIRING", numRows = 3
     * 输出: "LCIRETOESIIGEDHN"
     * 输入: s = "LEETCODEISHIRING", numRows = 4
     * 输出: "LDREOEIIECIHNTSG"<br>
     */
    static class Solution {
        static String conv(String s, int nRows) {
            /**
             * c = 2n - 2
             * row = 0, col = x * c ; col < len(s)
             * row = 1, col = 1 + x * c ; col = 1 + x * c - 2 ; col < len(s)
             * row = 2, col = 2 + x * c ; col = 2 + x * c - 4 ; col < len(s)
             * row = n - 1, col = n - 1 + x * c ; col = x * c - 2(n - 1)
             * col = x * c - row ; col = x * c + row
             */
            char[] data = new char[s.length()];
            int c = 2 * nRows - 2;
            int cur = 0;
            for (int row = 0; row < nRows; row++) {
                int x = 0;
                while (true) {
                    int col2 = x * c + row;
                    int col1 = (1 + x) * c - row;
                    x++;
                    if (col1 >= s.length() && col2 >= s.length()) {
                        break;
                    }
                    if (col2 < s.length()) {
                        data[cur] = s.charAt(col2);
                        cur++;
                        if (row == 0 || row == nRows - 1) {
                            continue;
                        }
                    }
                    if (col1 < s.length()) {
                        data[cur] = s.charAt(col1);
                        cur++;
                        if (row == 0 || row == nRows - 1) {
                            continue;
                        }
                    }
                }
            }
            return new String(data);
        }
    }
}
