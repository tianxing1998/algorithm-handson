package com.stats.machine.hard;

public class Stock1 {
    public static void main(String[] args) {
        int[] prices = {7,1,5,3,6,4};
        System.out.println(solve2(prices));
    }
    public static int solve(int[] prices) {
        int result = 0;
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            result = Math.max(result, prices[i] - min);
            min = Math.min(prices[i], min);
        }
        return result;
    }
    public static int solve2(int[] prices) {
        int s1 = 0;
        int buy = -prices[0];
        int s2 = -prices[0];
        int sell = 0;
        int s3 = 0;
        for (int i = 1; i < prices.length; i++) {
            s3 = Math.max(s3, sell);
            sell = Math.max(buy + prices[i], s2 + prices[i]);
            s2 = Math.max(buy, s2);
            buy = -prices[i];
            s1 = 0;
        }
        return Math.max(sell, s3);
    }
}
