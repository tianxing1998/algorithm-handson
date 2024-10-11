package com.algorithm.classics.string;

import java.util.Stack;

/**
 * https://zhuanlan.zhihu.com/p/438543253
 * https://leetcode.cn/problems/string-compression-ii/
 * https://leetcode.cn/problems/decode-string/
 *      状态机的本质就是问题推理(问题推理 + 动态规划 + 状态遍历 + 状态控制 + 数据存储)
 *      算法优化 与 问题推理 独自考虑，不要一起考虑
 */
public class Expression2 {
    public static void main(String[] args) {
        // Solution1.StatsMem mem = Solution1.solve1("abc3[cd]xyz", 0);
        String mem = Solution1.solve3("abc3[cd33]xyz", 0);
        System.out.println("mem:" + mem);
    }

    /**
     *
     */
    static class Solution1 {
        public static boolean isDigit(char  c) {
            return c >= '0' && c <= '9';
        }
        public static boolean isChar(char c) {
            return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
        }
        public static String stringToken(String s, int start) {
            String result = "";
            for (int i = start; i < s.length(); i++) {
                if (isChar(s.charAt(i))) {
                    result += s.charAt(i);
                } else {
                    break;
                }
            }
            return result;
        }
        public static String numberToken(String s, int start) {
            String result = "";
            for (int i = start; i < s.length(); i++) {
                if (isDigit(s.charAt(i))) {
                    result += s.charAt(i);
                } else {
                    break;
                }
            }
            return result;
        }
        static class StatsMem {
            String data;
            int endIdx;
        }
        /**
         * 递归 + 递归状态优化
         * 算法推理
         *  1.分词
         *  2.词分割 & 推理
         *  3.状态控制 & 数据存储
         * 编程逻辑
         *  1 + 2的状态控制合并，3独立开发
         *  合并后尽量按照推理逻辑做状态控制和数据逻辑.
         *      递归使用前一个乘法器(需要证明).
         * @param s
         */
        public static StatsMem solve1(String s, int from) {
            String data = "";
            int multifer = 0; // maybe [分词]
            for (int i = from; i < s.length() ; ) {
                char cur = s.charAt(i);
                if (isChar(cur)) {
                    if (multifer != 0) {
                        data += multifer;
                        multifer = 0;
                    }
                    data += s.charAt(i);
                    i++;
                } else if (isDigit(cur)) {
                    multifer = multifer * 10 + (cur - '0');
                    i++;
                } else if (cur == '[') {
                    StatsMem sub = solve1(s, i + 1);
                    // 倍数处理 => 业务梳理
                    for (int j = 0; j < multifer; j++) {
                        data += sub.data;
                    }
                    // 索引处理 => 状态控制
                    i = sub.endIdx + 1;
                    // cores.
                    multifer = 0;
                } else {
                    // "]"
                    StatsMem stats = new StatsMem();
                    stats.data = data;
                    stats.endIdx = i;
                    return stats;
                }
            }
            // End stats;
            StatsMem stats = new StatsMem();
            stats.data = data;
            stats.endIdx = s.length();
            return stats;
        }
        public static String combineTokens(Stack<String> tokens) {
            String result = "";
            while (!tokens.empty()) {
                String token = tokens.pop();
                result = token + result;
            }
            return result;
        }
        /**
         * 递归 + 递归状态优化
         * 问题推理
         *  1.分词
         *  2.词分割 & 推理
         *  3.状态控制 & 数据存储
         * 编程逻辑
         *  1 + 2的状态控制独立，3独立开发
         *  支持独立的分词逻辑
         * @param s
         */
        public static StatsMem solve2(String s, int from) {
            Stack<String> tokens = new Stack<>();
            for (int i = from; i < s.length() ; ) {
                char cur = s.charAt(i);
                if (isChar(cur)) {
                    String token = stringToken(s, i);
                    tokens.push(token);
                    i += token.length();
                } else if (isDigit(cur)) {
                    String token = numberToken(s, i);
                    tokens.push(token);
                    i += token.length();
                } else if (cur == '[') {
                    StatsMem sub = solve1(s, i + 1);
                    // 倍数处理 => 业务梳理
                    String multifer0 = tokens.pop();
                    int multifer1 = Integer.valueOf(multifer0);
                    for (int j = 0; j < multifer1; j++) {
                        tokens.push(sub.data);
                    }
                    // 索引处理 => 状态控制
                    i = sub.endIdx + 1;
                } else {
                    // "]"
                    StatsMem stats = new StatsMem();
                    stats.data = combineTokens(tokens);
                    stats.endIdx = i;
                    return stats;
                }
            }
            // End stats;
            StatsMem stats = new StatsMem();
            stats.data = combineTokens(tokens);
            stats.endIdx = s.length();
            return stats;
        }
        /**
         * 非递归 + 递归状态优化
         * 问题推理
         *  1.分词
         *  2.词分割 & 推理
         *  3.状态控制 & 数据存储
         *      (i, j) => stack(i) + j => 状态压缩
         *      状态优化: i无用，重要的是data & 乘法器
         *          (i, j)使用stack表示tokens.
         * 编程逻辑
         *  1 + 2的状态控制独立，3独立开发
         *  支持独立的分词逻辑
         * @param s
         */
        public static String solve3(String s, int from) {
            Stack<Stack<String>> stats = new Stack<>();
            Stack<String> curTokens = new Stack<>();
            for (int i = from; i < s.length(); ) {
                if (isChar(s.charAt(i))) {
                    String token = stringToken(s, i);
                    curTokens.push(token);
                    i += token.length();
                } else if (isDigit(s.charAt(i))) {
                    String token = numberToken(s, i);
                    curTokens.push(token);
                    i += token.length();
                } else if (s.charAt(i) == '[') {
                    stats.push(curTokens);
                    curTokens = new Stack<>();
                    i++;
                } else {
                    // ]
                    String combined = combineTokens(curTokens);
                    curTokens = stats.pop();
                    int mutifer = Integer.valueOf(curTokens.pop());
                    for (int j = 0; j < mutifer; j++) {
                        curTokens.push(combined);
                    }
                    i++;
                }
            }
            return combineTokens(curTokens);
        }
    }

    public static class Test2 {
        public static void main(String[] args) {
    //        输入：s = "3[a]2[bc]"
    //        输出："aaabcbc"
    //        示例 2：
    //
    //        输入：s = "3[a2[c]]"
    //        输出："accaccacc"
    //        示例 3：
    //
    //        输入：s = "2[abc]3[cd]ef"
    //        输出："abcabccdcdcdef"
    //        示例 4：
    //
    //        输入：s = "abc3[cd]xyz"
    //        输出："abccdcdcdxyz"

            String a = "abc3[cd]xyz";
            System.out.println(decode(a));

        }

        static class AppendableString {
            public AppendableString(String input) {
                this.content = input;
            }

            void repeat(int begin, int end, int cnt, String entity) {
                String before = this.content.substring(0, begin);
                String next = this.content.substring(end + 1);
                StringBuilder repeat = new StringBuilder();
                for(int i = 0; i < cnt; i ++) {
                    repeat.append(entity);
                }
                this.content = before + repeat.toString() + next;
            }
            private String content;
        }

        static class CodeEntity {
            int index = 0;
            int cnt = 0;
        }
        public static String decode(String input) {
            AppendableString a = new AppendableString(input);
            Stack<CodeEntity> stack = new Stack<>();
            for(int i = 0; i < a.content.length(); ) {
                if (a.content.charAt(i) == '[')  {
                    CodeEntity codeEntity = new CodeEntity();
                    codeEntity.cnt = Integer.valueOf(String.valueOf(a.content.charAt(i -1)));
                    codeEntity.index = i;
                    stack.push(codeEntity);
                    i ++;
                } else if (a.content.charAt(i) == ']') {
                    CodeEntity codeEntity = stack.pop();
                    a.repeat(codeEntity.index -1, i, codeEntity.cnt, a.content.substring(codeEntity.index + 1, i));
                    i = codeEntity.index - 1;
                } else {
                    i++;
                }
            }
            return a.content;
        }
    }
}
