package com.algorithm.classics.structs.Lists;

import java.util.ArrayList;
import java.util.List;

public class MergeHeapSort {
    public static class ListStat {
        boolean empty;
        int cur;
        int length;
        public void next() {
            if (cur < length) {
                cur++;
            }
            if (cur >= length) {
                empty = true;
            }
        }
    }

    public static class QElement {
        int value;
        int listIdx;
    }

    public static class MyHeap {
        void push(QElement element) {

        }
        QElement pop() {
            return null;
        }
    }

    public static ArrayList<Integer> sort(List<ArrayList<Integer>> arrays) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<ListStat> listStats = new ArrayList<>();
        MyHeap heap = new MyHeap();
        int nonEmptyList = 0;
        for (ArrayList<Integer> a : arrays) {
            if (a != null && !a.isEmpty()) {
                ListStat stat = new ListStat();
                stat.empty = false;
                stat.cur = 0;
                stat.length = a.size();
                listStats.add(stat);
                nonEmptyList++;
            } else {
                ListStat stat = new ListStat();
                stat.empty = true;
                stat.cur = 0;
                stat.length = 0;
                listStats.add(stat);
            }
        }

        if (nonEmptyList != 0) {
            for (int i = 0;  i < listStats.size(); i++) {
                ListStat stat = listStats.get(i);
                if (!stat.empty) {
                    QElement e = new QElement();
                    e.value = arrays.get(i).get(stat.cur);
                    e.listIdx = i;
                    heap.push(e);
                }
            }
            // 状态引擎与数据索引的一致性... <=> 状态机的等价索引...
            //      原始状态机: (list_i, start_i, empty)
            //      数据索引: heap <=> list_i_start
            //      状态索引：nonEmptyList <= 原始状态机
            // nonEmptyList <=> listStats <=> heap
            // 状态机引擎：cur = undef...
            // (list_id, start)=>(list_id, stat + 1)=>...=>(list_id, empty)=(list_id2, start2)=>...=>all_empty(nonEmptyList = 0)
            while (nonEmptyList != 0) {
                QElement e = heap.pop();
                result.add(e.value);
                ListStat stat = listStats.get(e.listIdx);
                stat.next();
                if (!stat.empty) {
                    QElement x = new QElement();
                    x.value = arrays.get(e.listIdx).get(stat.cur);
                    x.listIdx = e.listIdx;
                    heap.push(x);
                } else {
                    nonEmptyList--;
                }
            }
        }
        return result;
    }
}
