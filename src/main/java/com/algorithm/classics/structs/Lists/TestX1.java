package com.algorithm.classics.structs.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestX1 {
    public static void main(String[] args) {
        List<Integer> a = Arrays.asList(1,2,3,5);
        List<Integer> b = Arrays.asList(2,5);
        System.out.println(Arrays.toString(Solution.merge(a, b).toArray()));
    }

    static class Solution {
        public static int findElement(List<Integer> a, int start, int target) {
            return findElement(a, start, a.size() - 1, target);
        }

        /**
         * [begin, end] => [begin, mid - 1] | [mid + 1, end] && begin <= end
         * 未找到时, -1 * next
         *      [begin] => [begin, begin - 1], begin || [begin + 1, begin], begin
         *      [begin, begin + 1] => [begin, begin - 1] || [begin + 1]
         * @param a
         * @param start
         * @param end
         * @param target
         * @return
         */
        public static int findElement(List<Integer> a, int start, int end, int target) {
            if (start > end) {
                return -1 * start; // TODO
            } else {
                int mid = (start + end) / 2;
                if (a.get(mid) == target) {
                    return mid;
                } else if (a.get(mid) < target) {
                    return findElement(a, mid + 1, end, target);
                } else {
                    return findElement(a, start, mid - 1, target);
                }
            }
        }

        /**
         * a = [a, a_start] && b = [b, b_start]
         * [a, a_start] && b = [b, b_start] => [b, b_found + 1] && [a, a_start + 1]
         * @param a
         * @param b
         * @return
         */
        public static List<Integer> merge(List<Integer> a, List<Integer> b) {
            if (a.isEmpty() || b.isEmpty()) {
                return new ArrayList<>();
            }
            ArrayList<List<Integer>> storage = new ArrayList<>();
            storage.add(a);
            storage.add(b);
            ArrayList<Integer> stats = new ArrayList<>();
            stats.add(0);
            stats.add(0);
            int cur = 0;
            int target = 1;
            List<Integer> mergeResults = new ArrayList<>();
            while (stats.get(cur) < storage.get(cur).size()) {
                List<Integer> nowList = storage.get(cur);
                int nowStart = stats.get(cur);
                int nowValue = nowList.get(nowStart);
                List<Integer> targetList = storage.get( target );
                int targetStart = stats.get( target);
                if (targetStart >= targetList.size()) {
                    break;
                } else {
                    int found = findElement(targetList, targetStart, nowValue);
                    if (found < 0) {
                        stats.set(target, found * -1);
                        stats.set(cur, nowStart + 1);
                    } else {
                        mergeResults.add(targetList.get(found));
                        stats.set(target, found + 1);
                        stats.set(cur, nowStart + 1);
                    }
                }
                cur = target;
                target = (cur + 1) % 2;
            }
            return mergeResults;
        }
    }
}
