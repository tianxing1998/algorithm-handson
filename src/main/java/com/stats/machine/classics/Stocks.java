package com.stats.machine.classics;

public class Stocks {
    public static void main(String[] args) {
        int[] a = {7,1,5,3,6,4};
        System.out.println(maxPro(a));
    }
    public static int maxPro(int[] prices) {
        int maxPro = Integer.MIN_VALUE;
        int minx = prices[0];
        for (int i = 1;  i < prices.length; i ++) {
            minx = Math.min(minx, prices[i - 1]);
            int pro = prices[i] - minx;
            maxPro = Math.max(pro, maxPro);
        }
        return maxPro;
    }

    /**
     * pro[n][m] = pro[n][i] + pro[i][m]
     * min(i,n) = min[min(i,n - 1), a(n)]
     * pro(i,n) = max( pro(i, n - 1), a(n) - min(i, n - 1))
     * pro[0][n] = Max(i)(pro(0,i) + max( pro(i, n - 1),  a(n) - min(i, n - 1) )  )
     * => 注意无无后效性(DAG图，不能形成问题环)
     * @param prices
     * @return
     */
    /**
     * pro(n) = max(buy(n), none(n), sell(n))
     * buy(n) = pro(n - 1) - price(n)
     * none(n) = pro(n - 1)
     * sell(n) = buy(i) + price(n)
     * @param prices
     * @return
     */
    public static int maxPro2(int[] prices) {
//        int[][] stats = new int[prices.length][prices.length];
//        for (int i = 0; i < prices.length - 1; i++) {
//            stats[i][i + 1] = prices[i + 1] - prices[i];
//        }
        return 0;
    }
}
