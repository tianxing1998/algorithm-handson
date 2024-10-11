package com.stats.machine.classics;

import java.util.Arrays;

public class ProblemStuctStats {
    public static void main(String[] args) {
        int[] a = {1,1,22,1};
        System.out.println(Solution.removeElement(a, 1));
        System.out.println(Arrays.toString(a));
    }
    static class Solution {
        /**
         *
         * @param nums
         * @param val
         * @return [0, j) is reserved
         */
        static public int removeElement(int[] nums, int val) {
            int j = 0; // LatestToInsert
            for  (int i = 0; i < nums.length; i++) {
                if (nums[i] == val) {
                    // deleted
                } else {
                    // moved
                    nums[j] = nums[i];
                    j++;
                }
            }
            return j;
        }
    }
}
