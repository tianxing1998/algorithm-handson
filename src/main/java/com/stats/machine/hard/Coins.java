package com.stats.machine.hard;

/**
 * https://leetcode.cn/problems/coin-change/
 */
public class Coins {
    public static void main(String[] args) {
        int[] coins = {2};
        int amount = 3;
        System.out.println(Solution.solve(coins, amount));
    }

    static class Solution {
        public static int solve(int[] coins, int amount) {
            int[][] stats = new int[amount + 1][];
            for (int i = 0; i < amount + 1; i++) {
                stats[i] = new int[coins.length];
                if (i % coins[0] == 0) {
                    stats[i][0] = i / coins[0];
                }
            }
            // 0 => 不存在解法
            for (int a = 1; a <= amount; a++) {
                if (a == 11) {
                    System.out.println();
                }
                for (int c = 1; c < coins.length; c++) {
                    if (a - coins[c] > 0 && stats[a - coins[c]][c] != 0) {
                        if (stats[a][c - 1] > 0) {
                            stats[a][c] = Math.min(stats[a][c - 1], stats[a - coins[c]][c] + 1);
                        } else {
                            stats[a][c] = stats[a - coins[c]][c] + 1;
                        }
                    } else {
                        if (a == coins[c]) {
                            stats[a][c] = 1;
                        } else {
                            stats[a][c] = stats[a][c - 1];
                        }
                    }
                }
            }
            return stats[amount][coins.length - 1];
        }
    }
}
