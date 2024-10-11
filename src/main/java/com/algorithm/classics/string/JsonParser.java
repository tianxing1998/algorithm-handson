package com.algorithm.classics.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * {
 *     "":[a,b],
 *     "":{x},
 *     "":c
 * }
 */
public class JsonParser {

    public static void main(String[] args) {
        String json = "{\n" +
            "    \"f2\":3,\n" +
            "    \"f3\":{\n" +
            "        \"f3_1\": 6,\n" +
            "        \"f3_2\": \"test\"\n" +
            "    }\n" +
            "}";
        Value val = ParseJson0(json);
        System.out.println(val);
    }

    public static class Scalar {
        String value = "";
    }

    public enum ValueType {
        None,
        Scalar,
        Object,
        Array
    }

    public static class Value {
        ValueType type = ValueType.None;
        JsonObject object;
        Scalar scalar;
        JsonArray array;
    }

    public static class JsonObject {
        Map<String, Value> fields = new HashMap<>();
    }

    public static class JsonArray {
        List<Value> elements = new ArrayList<>();
    }

    static class Result {
        Value val = new Value();
        int endIdx;
    }

    /**
     * 文法状态机 & 递归
     * @param
     */
    public static Value ParseJson0(String json) {
        List<String> terms = getTerms(json);
        String firstTerm = terms.get(0);
        if (firstTerm.equals("[")) {
            return ParseJsonArray(terms, 0).val;
        } else {
            return ParseJsonObject(terms, 0).val;
        }
    }

    /**
     * 文法状态机 & 状态栈
     * @param
     */
    public static Value ParseJson1(String json) {
        List<String> terms = getTerms(json);
        String firstTerm = terms.get(0);
        if (firstTerm.equals("[")) {
            return ParseJsonArray(terms, 0).val;
        } else {
            return ParseJsonObject(terms, 0).val;
        }
    }

    public static Result ParseJsonObject(List<String> terms, int from) {
        JsonObject jsonObject = new JsonObject();
        int cur = from + 1;
        Stack<Object> elements = new Stack<>();
        while (cur < terms.size()) {
            String term = terms.get(cur);
            if (term.equals(":")) {
                cur++;
            } else if (term.equals(",")) {
                Object fVal0 = elements.pop();
                Value fVal = null;
                if (fVal0 instanceof String) {
                    fVal = new Value();
                    fVal.type = ValueType.Scalar;
                    fVal.scalar = new Scalar();
                    fVal.scalar.value = (String) fVal0;
                } else {
                    fVal = (Value) fVal0;
                }
                String fKey = (String) elements.pop();
                jsonObject.fields.put(fKey, fVal);
                cur++;
            } else if (term.equals("}")) {
                Object fVal0 = elements.pop();
                Value fVal = null;
                if (fVal0 instanceof String) {
                    fVal = new Value();
                    fVal.type = ValueType.Scalar;
                    fVal.scalar = new Scalar();
                    fVal.scalar.value = (String) fVal0;
                } else {
                    fVal = (Value) fVal0;
                }
                String fKey = (String) elements.pop();
                jsonObject.fields.put(fKey, fVal);
                break;
            } else if (term.equals("{")) {
                Result tmp = ParseJsonObject(terms, cur);
                elements.push(tmp.val);
                cur = tmp.endIdx + 1;
            } else if (term.equals("[")) {
                Result tmp = ParseJsonArray(terms, cur);
                elements.push(tmp.val);
                cur = tmp.endIdx + 1;
            } else {
                elements.push(term);
                cur++;
            }
        }
        Result result = new Result();
        result.val.type = ValueType.Object;
        result.val.object = jsonObject;
        result.endIdx = cur;
        return result;
    }

    public static Result ParseJsonArray(List<String> terms, int from) {
        JsonArray jsonArray = new JsonArray();
        int cur = from + 1;
        while (cur < terms.size()) {
            String term = terms.get(cur);
            if (term.equals(",")) {
                cur++;
            } else if (term.equals("]")) {
                break;
            } else if (term.equals("{")) {
                Result tmp = ParseJsonObject(terms, cur);
                jsonArray.elements.add(tmp.val);
                cur = tmp.endIdx + 1;
            } else if (term.equals("[")) {
                Result tmp = ParseJsonArray(terms, cur);
                jsonArray.elements.add(tmp.val);
                cur = tmp.endIdx + 1;
            } else {
                Scalar val = new Scalar();
                val.value = term;
                Value val0 = new Value();
                val0.scalar = val;
                val0.type = ValueType.Scalar;
                jsonArray.elements.add(val0);
                cur++;
            }
        }
        Result result = new Result();
        result.val.type = ValueType.Array;
        result.val.array = jsonArray;
        result.endIdx = cur;
        return result;
    }

    static class Pos {
        int curRow;
        int curCol; // to print
        int curLevel; // 缩进
    }

    public static String beautify(String json) {
        Value val = ParseJson0(json);
        Pos p = new Pos();
        StringBuilder sb = new StringBuilder();
        if (val.type == ValueType.Object) {

        } else if (val.type == ValueType.Array) {

        } else {
            System.out.println("err");
        }
        return "";
    }

    public static Pos beautifyObject(JsonObject jsonObject, Pos pos, StringBuilder sb) {

        return null;
    }

    public static Pos beautifyArray(JsonArray jsonArray, Pos pos, StringBuilder sb) {
        return null;
    }

    public static List<String> getTerms(String s) {
        List<String> terms = new ArrayList<>();
        int cur = 0;
        int seg_begin = 0; // no segs. 字符串，数字
        while (cur < s.length()) {
            char ch = s.charAt(cur);
            if (ch == ':' || ch == '[' || ch == ']' || ch == '{' || ch == '}' || ch == ',') {
                if (seg_begin < cur) {
                    terms.add(s.substring(seg_begin, cur));
                }
                terms.add(String.valueOf(ch));
                cur ++;
                seg_begin = cur;
            } else if (ch == ' ' || ch == '\n') {
                if (seg_begin < cur) {
                    terms.add(s.substring(seg_begin, cur));
                }
                cur ++;
                seg_begin = cur;
            } else {
                cur++;
            }
        }
        System.out.println("s:" + s);
        System.out.println("terms:" + terms.toString());
        return terms;
    }
}
