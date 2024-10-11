package com.algorith.e;

/**
 * https://leetcode.cn/problems/compress-string-lcci/
 * 遍历一次，同时做好状态控制.
 */
public class Test2 {
    public static int cntj(int j0, int j1) {
        return 0;
    }

    public static int nextj(int j) {
        return 0;
    }

    /**
     *
     * @param s abbccc
     * @param abbr ab12c3
     * @return
     */
    public static boolean match(String s, String abbr) {
        int i = 0; // [i, )

        int j_0 = 0; // [j_0, j_n) = (c, cnt)
        int cnt = 0;
        char c =  abbr.charAt(0);

        int j_n = 1;
        while (j_n < abbr.length()) {
            if (abbr.charAt(j_n) != 'a') {
                j_n++;
            } else {
                break;
            }
        }
        cnt = cntj(j_0, j_n);
        while (i < s.length() && (j_0 < j_n)) {
            if (s.charAt(i) == c) {
                cnt--;
                i++;
                if (cnt == 0) {
                    j_0 = j_n;
                    j_n = nextj(j_n);
                    cnt = cntj(j_0, j_n);
                    c =  abbr.charAt(j_0);
                }
            } else {
                return false;
            }
        }
        if (i < s.length() || j_0 < j_n) {
            return false;
        } else {
            return true;
        }
    }
}
