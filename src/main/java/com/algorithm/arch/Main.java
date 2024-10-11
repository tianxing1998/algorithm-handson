package com.algorithm.arch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Twitter twitter = new Twitter();
        int userid1 = 1;
        int userid2 = 2;
        int userid3 = 3;
        twitter.follow(userid1, userid2);
        twitter.follow(userid1, userid3);
        twitter.postTweet(userid2, 1);
        twitter.postTweet(userid2, 3);
        twitter.postTweet(userid3, 2);

        List<Integer> twlist = twitter.getNewsFeed(userid1);
        System.out.println(twlist.toString());
    }
}

class Twitter {
    public Twitter() {
    }

    private static class Txt {
        int tid;

        public static Txt valueOf(int twid) {
            Txt txt = new Txt();
            txt.tid = twid;
            return txt;
        }
    }

    private static class QEle {
        int lid;
        int eidx;
        List<List<Txt>> twlists; // 由于内部元素(List)使用的是链表，这里使用eidx访问性能差，直接使用迭代器来访问链表元素
        // TODD Iterator<Txt> iterator; // 下一个元素的指针
        // TODO Txt txt; // 多次读取因此使用临时存储空间

//        boolean hasNext2() {
//            return this.iterator.hasNext() && eidx < 9;
//        }

        boolean hasNext() {
            List<Txt> cur = this.twlists.get(lid);
            return eidx < cur.size() - 1 && eidx < 9;
        }
    }

    // 没有考虑并发问题，并发问题下需要做存储的读写控制。一般使用MVCC做版本管理和数据视图一致性控制
    // 对于当前问题，定制(非通用)性能更高的实现是用户的推文队列直接使用循环队列，自动做top10的裁剪，
        // 存储是连续存储，访问更快且访问逻辑更简单(直接数组下标)
    private HashMap<Integer, LinkedList<Txt>> txts = new HashMap<Integer, LinkedList<Txt>>();

    private HashMap<Integer, Set<Integer>> followings = new HashMap<Integer, Set<Integer>>();


    public void postTweet(int userId, int tweetId) {
        LinkedList<Txt> tws = txts.getOrDefault(userId, new LinkedList<Txt>());
        tws.addFirst(Txt.valueOf(tweetId));
        txts.put(userId, tws); // TODO 考虑性能，只有新的userid才重新put
    }

    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> following = this.followings.getOrDefault(userId, new HashSet<Integer>());
        List<List<Txt>> twlists = new ArrayList<List<Txt>>(); // TODO 初始化空间长度
        for (Integer fUserid : following) {
            twlists.add(txts.getOrDefault(fUserid, new LinkedList<Txt>())); // TODO 默认空链表使用静态空对象即可
        }
        // 归并
        PriorityQueue<QEle> queue = new PriorityQueue<QEle>(new Comparator<QEle>() {
            public int compare(QEle o1, QEle o2) {
                int tw1 = o1.twlists.get(o1.lid).get(o1.eidx).tid;
                int tw2 = o2.twlists.get(o2.lid).get(o2.eidx).tid;
                return tw2 - tw1;
            }
        });
        int nonEmptyCnt = 0;
        for (int l = 0; l < twlists.size(); l++) {
            List<Txt> twlist = twlists.get(l);
            if (!twlist.isEmpty()) {
                nonEmptyCnt++;
                QEle qEle = new QEle();
                qEle.lid = l;
                qEle.eidx = 0;
                qEle.twlists = twlists;
                queue.add(qEle);
            }
        }

        List<Integer> result = new ArrayList<Integer>();
        while (nonEmptyCnt > 0) {
            QEle cur = queue.poll();
            result.add(twlists.get(cur.lid).get(cur.eidx).tid);
            if (cur.hasNext()) {
                cur.eidx++;
                queue.add(cur);
            } else {
                nonEmptyCnt--;
            }
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        Set<Integer> following = followings.getOrDefault(followerId, new HashSet<Integer>());
        if (!following.contains(followeeId)) {
            following.add(followeeId);
            followings.put(followerId, following); // TODO 考虑性能，对新userid特殊处理
        }
    }

    public void unfollow(int followerId, int followeeId) {
        Set<Integer> following = followings.get(followerId);
        if (following != null && following.contains(followeeId)) { // TODO 直接remove即可(本身有查找能力)
            following.remove(followeeId);
            // followings.put(followerId, following); // ?
        }
    }
}
