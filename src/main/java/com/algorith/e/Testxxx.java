package com.algorith.e;

public class Testxxx {
    public static void main(String[] args) {
        int[] a = {1,100,1,1,1,100,1,1,100,1};
        System.out.println(Testxxx.solve(a));
    }
    public static int solve(int[] a) {
        int[] stats = new int[a.length + 1];
        stats[0]= 0;
        stats[1] = 0;
        for (int i = 2; i <= a.length; i++) {
            stats[i] = Math.min(stats[i - 1] + a[i - 1], stats[i - 2] + a[i - 2]);
        }
        return stats[a.length];
    }
}
