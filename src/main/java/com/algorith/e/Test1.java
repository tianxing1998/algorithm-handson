package com.algorith.e;

import java.util.Arrays;
import java.util.List;

/**
 * 三数之和
 * https://leetcode.cn/problems/1fGaJU/description/
 * <p>
 * Topk
 * https://leetcode.cn/problems/kth-largest-element-in-an-array/description/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
 */
public class Test1 {

    public static void main(String[] args) {
        int[] data = {0, 0, 0, 0};
        SolutionThreeSum.solve(data);
        System.out.println("end.");

//        int[] data = {3,2,3,1,2,4,5,5,6};
//        int topk = 4;
//        data = SolutionTopk.solve(data, topk);
//        System.out.println(data[topk - 1]);
    }

    static class SolutionTopk {
        static int[] solve(int[] data, int topk) {
            return solve(data, 0, data.length - 1, topk);
        }

        static int[] solve(int[] data, int begin, int end, int topk) {
            if (end - begin + 1 < topk) {
                return data;
            }
            if (topk < 1) {
                return data;
            }
            int pVal = data[begin];
            int group2Idx = begin;
            for (int i = begin + 1; i <= end; ) {
                if (data[i] <= pVal) {
                    i++;
                } else {
                    int tmp = data[i];
                    data[i] = data[group2Idx];
                    data[group2Idx] = tmp;
                    i++;
                    group2Idx++;
                }
            }
            if (group2Idx == begin) {
                if (topk == 1) {
                    return data;
                } else {
                    return solve(data, begin + 1, end, topk - 1);
                }
            } else {
                int group1 = group2Idx - begin;
                if (group1 >= topk) {
                    return solve(data, begin, group2Idx - 1, topk);
                } else {
                    return solve(data, group2Idx, end, topk - group1);
                }
            }
        }
    }

    static class SolutionThreeSum {
        static List<int[]> solve(int[] data) {
            Arrays.sort(data);
            int[] result = new int[3];
            for (int i = 0; i < data.length - 2; i++) {
                if (i > 0) {
                    if (data[i] == data[i -1]) {
                        continue;
                    }
                }
                int sec = i + 1;
                int last = data.length - 1;
                int target = -data[i];
                result[0] = data[i];
                while (sec < last) {
                    if (data[sec] + data[last] == target) {
                        result[1] = data[sec];
                        result[2] = data[last];
                        System.out.println("result:" + Arrays.toString(result));
                        int secData = data[sec];
                        sec++;
                        while (sec < last) {
                            if (secData == data[sec]) {
                                sec++;
                            } else {
                                break;
                            }
                        }
                        int lastData = data[last];
                        last--;
                        while (sec < last) {
                            if (lastData == data[last]) {
                                last--;
                            } else {
                                break;
                            }
                        }
                    } else if (data[sec] + data[last] > target) {
                        last--;
                    } else {
                        sec++;
                    }
                }
            }
            return null;
        }
    }
}
