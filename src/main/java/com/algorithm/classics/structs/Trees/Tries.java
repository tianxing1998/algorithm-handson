package com.algorithm.classics.structs.Trees;

public class Tries {
    public static void main(String[] args) {

    }

    static class Node {
        char c;
        // Node childs;
        // Node brothers; // 优化
        Node[] brothers = new Node[26];
    }

    static class Solution {
        public static boolean match(String query, Node terms) {
            Node cur = terms;
            int i = 0;
            while (i != query.length() && cur != null) {
                if (cur.c == query.charAt(i)) {
                    cur = cur.brothers[cur.c - 'a'];
                    i ++;
                } else {
                    break;
                }
            }
            if (i == query.length()) {
                return true;
            } else {
                return false;
            }
        }
    }

}
