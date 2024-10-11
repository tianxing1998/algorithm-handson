package com.algorith.e;

/**
 * https://vxg1hes64k.feishu.cn/docx/WZBVdGduaoBd1qx2X2scKTimnGh?from=from_copylink
 */
public class Book1 {
    public static void main(String[] args) {

        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println("-----------------");
        System.out.println(System.getProperty("java.ext.dirs"));

        char[][] grid = {
            {'#','E','#'},
            {'.','.','.'},
            {'S','#','#'},
        };
        System.out.println(new Solution().solve(grid));
    }
    static class Position {
        int i;
        int j;
    }
    static class Solution {
        public int solve(char[][] grid) {
            Position start = new Position();
            Position end = new Position();
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 'S') {
                        start.i = i;
                        start.j = j;
                    }
                    if (grid[i][j] == 'E') {
                        end.i = i;
                        end.j = j;
                    }
                }
            }
            // List<Position> positions = new ArrayList<>();
            Position now = start;
            return solve(grid, end, now);
        }

        boolean pathForwardWard(char[][] grid, Position pos) {
            return grid[pos.i][pos.j] != '#';
        }

        public int solve(char[][] grid, Position end, Position now) {
            if (now.i == end.i && now.j == end.j) {
                return 0;
            } else {
                if (!pathForwardWard(grid, now)) {
                    return -1;
                }
                grid[now.i][now.j] = '#';
                int step = -1;
                // 上
                if (now.i > 0) {
                    now.i--;
                    if (pathForwardWard(grid, now)) {
                        int s = solve(grid, end, now);
                        if (step >= 0 && s >= 0) {
                            step = Math.min(step, s);
                        } else {
                            step = Math.max(step, s);
                        }
                    }
                    now.i++;
                }
                // 下
                if (now.i < grid.length - 1) {
                    now.i++;
                    if (pathForwardWard(grid, now)) {
                        int s = solve(grid, end, now);
                        if (step >= 0 && s >= 0) {
                            step = Math.min(step, s);
                        } else {
                            step = Math.max(step, s);
                        }
                    }
                    now.i--;
                }
                // 左
                if (now.j > 0) {
                    now.j--;
                    if (pathForwardWard(grid, now)) {
                        int s = solve(grid, end, now);
                        if (step >= 0 && s >= 0) {
                            step = Math.min(step, s);
                        } else {
                            step = Math.max(step, s);
                        }
                    }
                    now.j++;
                }
                // 右
                if (now.j < grid[0].length - 1) {
                    now.j++;
                    if (pathForwardWard(grid, now)) {
                        int s = solve(grid, end, now);
                        if (step >= 0 && s >= 0) {
                            step = Math.min(step, s);
                        } else {
                            step = Math.max(step, s);
                        }
                    }
                    now.j--;
                }
                grid[now.i][now.j] = '.';
                if (step == -1) {
                    return -1;
                } else {
                    return step + 1;
                }
            }
        }
    }
}
