package com.stats.machine.hard;

/**
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/description/
 */
public class Stock2 {
    public static void main(String[] args) {
        int[] prices = {7,6,4,3,1};
        // int[] prices = {7, 1,5,3,6,4};
        System.out.println(Solution.solve(prices));
    }

    static class Solution {
        public static int solve(int[] prices) {
            int[] none = new int[prices.length];
            int[] one = new int[prices.length];
            none[0] = 0;
            one[0] = -prices[0];
            for (int i = 1; i < prices.length; i++) {
                none[i] = Math.max(none[i - 1], one[i - 1] + prices[i]);
                one[i] = Math.max(one[i - 1], none[i - 1] - prices[i]);
            }
            return none[prices.length - 1];
        }
    }
}
