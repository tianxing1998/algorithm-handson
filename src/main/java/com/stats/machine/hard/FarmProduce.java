package com.stats.machine.hard;

import java.util.Arrays;

/**
 * 农场生产问题
 */
public class FarmProduce {
    public static void main(String[] args) {
        int[] transortCost = {0, 100,100};
        int[] prodCost = {1,2,3};
        MaxCost(prodCost, transortCost);
    }

    /**
     * 问题规约有问题： F(n + 1 ) != g( F(n) ), A(n+1)与A(n)不独立.
     * @param prodCost
     * @param transpotCost
     * @return
     */
    public static int MaxCost(int[] prodCost, int[] transpotCost) {
        // int[] leftMax = new int[prodCost.length]; // pos + 1;
        // transport to x must to x - 1
        int[][] transprotCostSum0 = new int[prodCost.length][]; // (x = position, y = cost <= position + 1)
        for (int pos = 0; pos < prodCost.length; pos++) {
            transprotCostSum0[pos] = new int[prodCost.length + 1];
        }
        transprotCostSum0[0][0] = prodCost[0]; // pos=0 must product things.
        transprotCostSum0[0][1] = prodCost[1];
        int[][] transprotCostSum1 = new int[prodCost.length][]; // (x = position, y = cost <= position + 1), with car
        for (int pos = 0; pos < prodCost.length; pos++) {
            transprotCostSum1[pos] = new int[prodCost.length + 1];
        }
        transprotCostSum1[0][0] = prodCost[0]; // pos=0 must product things.
        transprotCostSum1[0][1] = prodCost[1];

        for (int pos = 1; pos < prodCost.length; pos++) {
            for (int left = 0; left <= pos + 1; left++) {
                int Max = Integer.MAX_VALUE;
                if (left == 0) {
                    Max = transprotCostSum0[pos - 1][0] + prodCost[pos];
                        // + transpotCost[pos]; // pos做生产
                } else if (left == pos + 1) {
                    Max = transprotCostSum0[pos - 1][pos] + prodCost[pos]; // pos做生产
                } else {
                    int Max0 = transprotCostSum0[pos - 1][left] + prodCost[pos]; // pos做生产
                    int Max1 = transprotCostSum0[pos - 1][left - 1] + prodCost[pos]; // pos做生产
                    int Max2 = transprotCostSum0[pos - 1][left + 1] + transpotCost[pos]; // pos by transport
                    Max = Math.min(Max,Max0);
                    Max = Math.min(Max,Max1);
                    Max = Math.min(Max,Max2);
                }
                transprotCostSum0[pos][left] = Max;
            }
        }

//        for (int pos = prodCost.length - 1; pos < prodCost.length; pos++) {
//            for (int left = 0; left <= pos + 1; left++) {
//                int Max = 0;
//                if (left == 0) {
//                    Max = transprotCostSum0[pos - 1][0] + prodCost[pos]; // pos做生产
//                } else if (left == pos + 1) {
//                    Max = transprotCostSum0[pos - 1][pos] + prodCost[pos]; // pos做生产
//                } else {
//                    Max = transprotCostSum0[pos - 1][left] + prodCost[pos]; // pos做生产
//                    Max = Math.max(Max,
//                        transprotCostSum0[pos - 1][left + 1] + transpotCost[pos]); // pos by transport
//                }
//                transprotCostSum0[pos][left] = Max;
//            }
//        }
        System.out.println(Arrays.toString(transprotCostSum0[prodCost.length - 1]));
        return 0;
    }
}
