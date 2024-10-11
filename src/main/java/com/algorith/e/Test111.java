package com.algorith.e;

import java.util.HashMap;
import java.util.HashSet;

public class Test111 {
    public static void main(String[] args) {
        char[] data = {'a'};
        System.out.println(Solution.solve(data));
    }
    static class Solution {
        static int solve(char[] data) {
            if (data == null || data.length == 0) {
                return 0;
            }
            HashMap<Character, Integer> positions = new HashMap<>();
            int i = 0;
            int j = 0;
            int max = 1;
            positions.put(data[0], 0);
            int begin;
            int end;
            for (j = 1; j < data.length; j++) {
                if (positions.containsKey(data[j])) {
                    int pos = positions.get(data[j]);
                    for (; i <= pos; i++) {
                        positions.remove(data[i]);
                    }
                    positions.put(data[j], j);
                } else {
                    positions.put(data[j], j);
                    max = Math.max(max, j - i + 1);
                }
            }
            return max;
        }
    }
}
