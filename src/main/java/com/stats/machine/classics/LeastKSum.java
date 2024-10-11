package com.stats.machine.classics;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * https://leetcode.cn/problems/shortest-subarray-with-sum-at-least-k/
 * 结构化 才能 更好的表示和优化 => 统一表达式 => 符号数学 & 符号推理 => 函数化 => 一组数据的特征
 * sum[i, j] = Sum(j) - Sum(i) => 相同形式则可以复用子问题的结果。
 * 最短性质
 *      F(j) = f(i, j)
 *      F(j + 1) < F(j) + 1
 * *
 * 有用的函数性质
 *      f(n) 的单调性 => 排序属性 => 单调栈
 *      f(n) 的最值问题 => 边界 & 值域
 *      f(n) 随机映射 => 毫无信息量 & 特征
 *      f(n) bitmap函数 => 存在性问题 n => val(n) => bit(val(n))
 * 函数 & 递推 & 状态机
 *      递推是一种函数的同一性质
 *      状态机是递推的演变逻辑
 *      Stat(i) => Actions(Test & 一致性) => NewStatsBranches
 *
 */
public class LeastKSum {
    static class Solution {
        public int shortestSubarray(int[] nums, int k) {
            int n = nums.length;
            /**
             * 前缀和构造递推数组
             */
            long[] preSumArr = new long[n + 1];
            for (int i = 0; i < n; i++) {
                preSumArr[i + 1] = preSumArr[i] + nums[i];
            }
            int res = n + 1;
            /**
             * 单调队列
             */
            Deque<Integer> queue = new ArrayDeque<Integer>();
            for (int i = 0; i <= n; i++) {
                long curSum = preSumArr[i];
                // 在单调函数上查找MinLength(i) minPresum
                while (!queue.isEmpty() && curSum - preSumArr[queue.peekFirst()] >= k) {
                    res = Math.min(res, i - queue.pollFirst());
                }
                // f(n)的单调性维持 => 单调性与最值算法的优化有关
                while (!queue.isEmpty() && preSumArr[queue.peekLast()] >= curSum) {
                    queue.pollLast();
                }
                queue.offerLast(i);
            }
            return res < n + 1 ? res : -1;
        }
    }
}
