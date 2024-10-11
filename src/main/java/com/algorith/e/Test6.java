package com.algorith.e;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 0,2,4,6,6,8
 *
 * 1.前三位和小于后三位和
 * 2.中间两位数不同
 * 不重不漏打印所有六位数组合
 * https://leetcode.cn/problems/permutations/
 *      问题推理 & 状态控制
 *          动态规划 & 状态遍历 = 回溯算法
 *      直接存储
 *          剩余的空间 & 剩余的数字即可(可以重复)(Yes)
 *              需要不断重建数组性能不太好
 *      存储优化
 *          1) 直接使用原来的存储(swap一下就可以)(Yes)
 *          2) CntHash存储剩余数字.
 *      重复数字优化
 *          数字排序，数字相同跳过.(但是swap会破坏排序)(No)
 *          数字做到去重即可.
 */
public class Test6 {
    public static void main(String[] args) {
        int[] arr = {0,2,4,6,6,8};
        // Solution.solve2(arr, 0);
        Solution.solve(arr);
    }

    static class Solution {

        public static void solve(int[] arr){
            int[] result = new int[arr.length];
            solve2(arr, 0);
        }

        public static boolean test(int[] numbers) {
            int sum0 = numbers[0] + numbers[1] + numbers[2];
            int sum1 = numbers[3] + numbers[4] + numbers[5];
            if (numbers[2] != numbers[3] && sum0 < sum1) {
                return true;
            }
            return false;
        }

        /**
         *
         * @param numbers 可能的numbers， 没有去重
         * @return 去重计数.
         */
        public static HashMap<Integer, Integer> toHash(int[] numbers) {
            HashMap<Integer, Integer> hash = new HashMap<>();
            for (int num : numbers) {
                Integer counter = hash.get(num);
                if (counter == null) {
                    counter = 1;
                } else {
                    counter++;
                }
                hash.put(num, counter);
            }
            return hash;
        }
        public static int[] toArrAndReduce(HashMap<Integer, Integer> numbers, int size, int num) {
            int[] result = new int[size - 1];
            int cur = 0;
            for (Map.Entry<Integer, Integer> entry : numbers.entrySet()) {
                if (entry.getValue() == 1 && entry.getKey() == num) {
                    continue;
                } else {
                    int discnt = (entry.getKey() == num)?1 : 0;
                    for (int i = 1; i <= entry.getValue() - discnt; i ++) {
                        result[cur] = entry.getKey();
                        cur++;
                    }
                }
            }
            return result;
        }

        public static void solve(int[] numbers, int[] result, int from) {
            if (from >= result.length) {
                boolean match = test(result);
                if (match) {
                    System.out.println("match:" + Arrays.toString(result));
                }
            }
            HashMap<Integer, Integer> uniq_numbers = toHash(numbers);
            for (Map.Entry<Integer, Integer> entry : uniq_numbers.entrySet()) {
                int[] next_numbers = toArrAndReduce(uniq_numbers, numbers.length, entry.getKey());
                result[from] = entry.getKey();
                solve(next_numbers, result, from + 1);
            }
        }

        /**
         * 存储优化
         * @param numbers
         * @param from
         */
        public static void solve2(int[] numbers, int from) {
            if (from >= numbers.length) {
                boolean match = test(numbers);
                if (match) {
                    System.out.println("match:" + Arrays.toString(numbers));
                }
            }
            HashSet<Integer> visited = new HashSet<>();
            /**
             * i for all options for position[from]
             */
            for(int i = from; i < numbers.length; i++) {
                if (visited.contains(numbers[i])) {
                    continue;
                } else {
                    visited.add(numbers[i]);
                    int tmp = numbers[i];
                    numbers[i] = numbers[from];
                    numbers[from] = tmp;
                    solve2(numbers, from + 1);
                    tmp = numbers[i];
                    numbers[i] = numbers[from];
                    numbers[from] = tmp;
                }
            }
        }

    }
}
