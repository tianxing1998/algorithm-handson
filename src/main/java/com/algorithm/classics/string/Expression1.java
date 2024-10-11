package com.algorithm.classics.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

/**
 * Question:
 * <p>
 * Given a an expression string, calculate the result of the expression.
 * Consider addition and subtraction with brackets
 * <p>
 * for example:
 * Input: "1 + 2 - 3 + 5", output: 5
 * Input: "1 + 2 * 3 * 5", output: 5
 * Input: "-2+(5 - 3)", output: 0
 **/
public class Expression1 {
    public static void main(String[] args) {
        System.out.println("result: " + Solution.solve("-2*(5 - 3)"));
    }

    public static class Solution {
        /* + - * / ( )  */
        static HashSet<String> insts = new HashSet<String>(){
            {
                add(String.valueOf('*'));
                add(String.valueOf('/'));
                add(String.valueOf('+'));
                add(String.valueOf('-'));
                add(String.valueOf('('));
                add(String.valueOf(')')); // 语法层面(不考虑语法树).
            }
        };

        static boolean isFirstHighLevel( String a, String b) {
            HashMap<String, Integer> orders = new HashMap<String, Integer>() {
                {
                    put("*", 1);
                    put("-", 1);
                    put("+", 0);
                    put("-", 0);
                }
            };
            // 本质是语法层面的东西
            if (a.equals("(") || b.equals("(")) {
                return false;
            }
            if (a.equals(")") || b.equals(")")) {
                return true;
            }

            return orders.get(a) >= orders.get(b);
        }

        static boolean isDigit(String s, int pos) {
            char c = s.charAt(pos);
            return c >= '0' && c <= '9';
        }

        static boolean isEmpty(String s, int pos) {
            return s.charAt(pos) == ' ';
        }
        /**
         *
         * @param s
         * @param pos
         * @param instructs
         * @return
         */
        public static String numToken(String s, int pos, Stack<String> instructs) {
            char c = s.charAt(pos);
            String result = "";
            if (c == '-' && (instructs.empty() || instructs.peek() == "(")) {
                result += "-";
                pos ++;
            }
            if ("" != instToken(s, pos)) {
                return null; //不是数字
            }
            while (pos < s.length() && isDigit(s, pos)) {
                result += s.charAt(pos);
                pos++;
            }
            if (result.length() == 0) {
                return null;
            } else {
                return result;
            }
        }
        public static String instToken(String s, int pos) {
            char c = s.charAt(pos);
            if (insts.contains(String.valueOf(c))) {
                return String.valueOf(c);
            }
            return "";
        }
        static String exec(String inst, String first, String second) {
            if (inst.equals("*")) {
                Integer result = Integer.valueOf(first) * Integer.valueOf(second);
                return result.toString();
            }
            if (inst.equals("/")) {
                Integer result = Integer.valueOf(first) / Integer.valueOf(second);
                return result.toString();
            }
            if (inst.equals("+")) {
                Integer result = Integer.valueOf(first) + Integer.valueOf(second);
                return result.toString();
            }
            if (inst.equals("-")) {
                Integer result = Integer.valueOf(first) - Integer.valueOf(second);
                return result.toString();
            }
            return  "";
        }
        static String execInstr(Stack<String> data, Stack<String> instsCtx) {
            String inst = instsCtx.pop();
            if (inst.equals("*") || inst.equals("/") || inst.equals("-") || inst.equals("+")) {
                String sec = data.pop();
                String first = data.pop();
                return exec(inst, first, sec);
            }
            if (inst.equals(")")) {
                // 语法树
                instsCtx.pop();
                return data.pop();
            }
            return "";
        }
        /**
         * 指令优先级
         * 单目指令 & 双目指令
         * 问题推理
         *  1.分词问题，数字 & 负数 & 减号.
         *  1.语法问题，合并处理.
         *  2.问题结构化 & 状态控制
         *      0 => i => n
         *      lastInstruct
         *      lastData
         *  3.数据存储 & 状态优化
         * @param expression
         * @return
         */
        public static String solve(String expression) {
            Stack<String> instructs = new Stack<>();
            Stack<String> datas = new Stack<>();
            for (int i = 0; i < expression.length(); ) {
                if (isEmpty(expression, i)) {
                    i++;
                    continue;
                }
                String numToken = numToken(expression, i, instructs);
                if (numToken != null) {
                    datas.push(numToken);
                    i += numToken.length();
                } else {
                    String instToken = instToken(expression, i);
                    while (!instructs.empty()) {
                        String preInst = instructs.peek();
                        if (isFirstHighLevel(preInst, instToken)) {
                            String result = execInstr(datas, instructs);
                            datas.push(result);
                        } else {
                            break;
                        }
                    }
                    instructs.push(instToken);
                    i += instToken.length();
                }
            }
            // execLefts
            while (!instructs.empty()) {
                String result = execInstr(datas, instructs);
                datas.push(result);
            }
            return datas.pop();
        }
    }

    /**
     * Question:
     * <p>
     * Given a an expression string, calculate the result of the expression.
     * Consider addition and subtraction with brackets
     * <p>
     * for example:
     * Input: "1 + 2 - 3 + 5", output: 5
     * Input: "-2+(5 - 3)", output: 0
     **/
    public static class Expression {

        static boolean isOperator(Character ch) {
            return ch.charValue() == '+' || ch == '-' || ch == '(' || ch == ')';
        }

        static Integer opFunc1(Character op, Integer param) {
            return param;
        }

        static Integer opFunc2(Character op, Integer leftParam, Integer rightParam) {
            if (op == '+') {
                return leftParam + rightParam;
            }
            if (op == '-') {
                return leftParam - rightParam;
            }
            throw new RuntimeException("op:" + op);
        }

        static boolean isOpLevelHighOrEqual(Character op1, Character op2) {
            if (op1 == '(') {
                return false;
            }
            if (op1 == ')') {
                return true;
            }
            if (op2 == '(') {
                return false;
            }
            if (op2 == ')') {
                return true;
            }
            return true;
        }

        static boolean isNegOp(String s, int current) {
            Character ch = s.charAt(current);
            if (ch == '-') {
                if (current == 0 || s.charAt(current - 1) == '(') {
                    return true;
                }
            }
            return false;
        }

        static int calc(String s) {
            Stack<Integer> operaNums = new Stack<Integer>();
            Stack<Character> operators = new Stack<Character>();
            Integer operateeBegin = -1;
            for (int i = 0; i < s.length(); i++) {
                if (isNegOp(s, i) || !isOperator(s.charAt(i))) {
                    if (operateeBegin == -1) {
                        operateeBegin = i;
                    }
                    // operaNums.push(s.charAt(i) - '0');
                } else {

                    // handle multi-char numbers
                    if (operateeBegin != -1) {
                        operaNums.push(Integer.valueOf(s.substring(operateeBegin, i)));
                    }

                    if (operators.empty()) {
                        operators.push(s.charAt(i));
                    } else {
                        Character op = operators.peek();
                        if (isOpLevelHighOrEqual(op, s.charAt(i))) {
                            if (op == ')') {
                                operators.pop(); // '('
                            } else {
                                op = operators.pop();
                                Integer second = operaNums.pop();
                                Integer first = operaNums.pop();
                                Integer result = opFunc2(op, first, second);
                                operaNums.push(result);
                                operators.push(s.charAt(i));
                            }
                        } else {
                            operators.push(s.charAt(i));
                        }
                    }
                    operateeBegin = -1;
                }
            }
            if (operateeBegin != -1) {
                operaNums.push(Integer.valueOf(s.substring(operateeBegin)));
            }

            while (!operators.empty()) {
                Character op = operators.pop();
                if (op == ')') {
                    operators.pop(); // '('
                } else {
                    Integer second = operaNums.pop();
                    Integer first = operaNums.pop();
                    Integer result = opFunc2(op, first, second);
                    operaNums.push(result);
                }
            }

            return operaNums.pop();
        }

        public static void main(String[] args) {
            String expression = "-2+(5-3)";
            System.out.println(calc(expression));
        }

    }

    /**
     * 表达式解析
     * a + b * c
     * 分词：多char数字 & 多char符号
     * 正负数
     * 符号
     *   四则运算 & 括号
     *   单目 & 双目
     *   多char符号
     */
    public static class ExpressParse {
        public static boolean firstIsPriority(char first, char second) {
            HashSet<Character> a = new HashSet<Character>();
            a.add(Character.valueOf('+'));
            a.add(Character.valueOf('-'));
            HashSet<Character> b = new HashSet<Character>();
            b.add(Character.valueOf('*'));
            b.add(Character.valueOf('/'));
            if (b.contains(first) && a.contains(second)) {
                return true;
            }
            return false;
        }

        public static Integer runOp(char op, Integer first, Integer second) {
            if (op == '+') {
                return first + second;
            } else if (op == '-') {
                return first - second;
            } else if (op == '*') {
                return first * second;
            } else if (op == '/') {
                return first / second;
            }
            return 0;
        }

        public static void main(String[] args) {
            System.out.println("hello");
            Stack<Integer> operatation = new Stack<Integer>();
            Stack<Character> operators = new Stack<Character>();
            String express = "1+3*4-3";
            HashSet<Character> allOperators = new HashSet<Character>();
            allOperators.add('+');
            allOperators.add('-');
            allOperators.add('*');
            allOperators.add('/');
            for (int i = 0; i < express.length(); i++) {
                if (!allOperators.contains(express.charAt(i))) {
                    operatation.add(Integer.valueOf(String.valueOf(express.charAt(i))));
                } else {
                    if (operators.empty() || firstIsPriority(express.charAt(i), operators.peek())) {
                        operators.push(express.charAt(i));
                    } else {
                        char op_to_run = operators.pop();
                        Integer first_operation = operatation.pop();
                        Integer second_operaton = operatation.pop();
                        operatation.push(runOp(op_to_run, second_operaton, first_operation));
                        operators.push(express.charAt(i));
                    }
                }
            }

            while (!operators.empty()) {
                char op_to_run = operators.pop();
                Integer first_operation = operatation.pop();
                Integer second_operaton = operatation.pop();
                operatation.push(runOp(op_to_run, second_operaton, first_operation));
            }

            while (!operatation.empty()) {
                System.out.println("output:" + operatation.pop());
            }
        }
    }
}
