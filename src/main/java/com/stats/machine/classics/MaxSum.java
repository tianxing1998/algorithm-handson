package com.stats.machine.classics;

public class MaxSum {
//    Given an integer array nums,
//    find the contiguous subarray (containing at least one number ，
//        which has the largest sum and return its sum.
    public static void main(String[] args) {
        int[] array = {1,3,7,-8};
        findMaxSum(array);
    }

    /**
     * problem structs.
     *      结构上，存在多个range局部max，求max。局部max：以x节点结尾的最大sum。
     *      需要构造合适的子问题
     * 问题架构
     *      问题如何规约?
     *      问题有多少个层次?
     * 构造式递归
     *      Max(x) = x or Max(x - 1)
     *      优化: 优化递归树的深度和复杂度.
     * 多层递归树
     *      => 复杂的子问题结构
     * @param inputs
     */
    public static void findMaxSum(int[] inputs) {
        int[] sumStats = new int[inputs.length];
        sumStats[0] = inputs[0];
        for (int i = 1; i < inputs.length ; i++) {
            if (sumStats[i -1] < 0) {
                sumStats[i] = inputs[i];
            } else {
                sumStats[i] = sumStats[i -1] + inputs[i];
            }
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < sumStats.length; i++) {
            max = Math.max(max, sumStats[i]);
        }
        System.out.println(max);
    }

}
