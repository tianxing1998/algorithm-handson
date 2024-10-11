package com.algorithm.arch;

public class IntensitySegments {
    public static void main(String[] args) {
        IntensitySegments segments = new IntensitySegments();
        System.out.println(segments.toString());
        segments.add(10, 30, 1);
        System.out.println(segments.toString());
        segments.add(20, 40, 1);
        System.out.println(segments.toString());
        segments.add(10, 40, -2);
        System.out.println(segments.toString());

        IntensitySegments segments2 = new IntensitySegments();
        System.out.println(segments2.toString());
        segments2.add(10, 30, 1);
        System.out.println(segments2.toString());
        segments2.add(20, 40, 1);
        System.out.println(segments2.toString());
        segments2.add(10, 40, -1);
        System.out.println(segments2.toString());
        segments2.add(10, 40, -1);
        System.out.println(segments2.toString());

    }

    static class LinkedSegment {
        int point = 0;
        int intensity;
        LinkedSegment next;
        static LinkedSegment of(int point, int intensity) {
            LinkedSegment segment = new LinkedSegment();
            segment.intensity = intensity;
            segment.point = point;
            return segment;
        }
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            builder.append(this.point);
            builder.append(",");
            builder.append(this.intensity);
            builder.append("]");
            return builder.toString();
        }
    }

    private LinkedSegment head = new LinkedSegment();

    /**
     * 插入节点
     * 若节点存在返回前一个节点
     * 若节点不存在插入当前节点返回前一个节点
     * @param point
     * @return prev
     */
    private LinkedSegment insertIfNeed(int point) {
        LinkedSegment prev = head;
        LinkedSegment cur = head.next;
        if (cur == null) {
            LinkedSegment fromSeg = LinkedSegment.of(point, 0);
            head.next = fromSeg;
            return prev;
        } else {
            while (cur != null && cur.point < point) {
                prev = cur;
                cur = cur.next;
            }
            if (cur == null || cur.point > point) {
                LinkedSegment insertSeg = LinkedSegment.of(point, prev.intensity);
                insertSeg.next = cur;
                prev.next = insertSeg;
            }
            return prev;
        }
    }

    public void add(int from, int to, int amount) {
        LinkedSegment fromPrev = insertIfNeed(from);
        LinkedSegment toPrev = insertIfNeed(to);

        LinkedSegment prev = fromPrev;
        LinkedSegment cur = prev.next;
        // [insertFrom, insertTo)的所有节点发生修改，并且与前一个节点进行比较
        // 若intensity相同，则融合(即删除当前节点)
        while (cur != toPrev.next) {
            cur.intensity += amount;
            if (cur.intensity != prev.intensity) {
                prev = cur;
                cur = cur.next;
            } else {
                prev.next = cur.next;
                cur = cur.next;
            }
        }
        {
            // insertTo节点由于前一个节点发生变化，需要测试是否融合insertTo到前一个节点
            if (cur.intensity == prev.intensity) {
                prev.next = cur.next;
            }
        }
    }

    public void set(int from, int to, int amount) {
        LinkedSegment fromPrev = insertIfNeed(from);
        LinkedSegment toPrev = insertIfNeed(to);

        LinkedSegment prev = fromPrev;
        LinkedSegment cur = prev.next;
        // [insertFrom, insertTo)的所有节点发生修改，并且与前一个节点进行比较
        // 若intensity相同，则融合(即删除当前节点)
        while (cur != toPrev.next) {
            cur.intensity = amount;
            if (cur.intensity != prev.intensity) {
                prev = cur;
                cur = cur.next;
            } else {
                prev.next = cur.next;
                cur = cur.next;
            }
        }
        {
            // insertTo节点由于前一个节点发生变化，需要测试是否融合insertTo到前一个节点
            if (cur.intensity == prev.intensity) {
                prev.next = cur.next;
            }
        }
    }

    public String toString() {
        LinkedSegment cur = head.next;
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        boolean isFirst = true;
        while (cur != null) {
            if (isFirst) {
                builder.append(cur.toString());
                isFirst = false;
            } else {
                builder.append(",");
                builder.append(cur.toString());
            }
            cur = cur.next;
        }
        builder.append("]");
        return builder.toString();
    }

    public static class UnitTest {
        public static void main(String[] args) {
            Test1.run();
            Test2.run();
            Test3.run();
            Test4.run();
            Test5.run();
            Test8.run();
            Test6.run();
            Test7.run();
            Test9.run();
            Test10.run();
        }
        /**
         *
         */
        static class Test1 {
            static void run() {
                IntensitySegments segments = new IntensitySegments();
                System.out.println(segments.toString());
                segments.add(10, 30, 1);
                System.out.println(segments.toString());
                segments.add(20, 40, 1);
                System.out.println(segments.toString());
                segments.add(10, 40, -2);
                System.out.println(segments.toString());
            }
        }

        /**
         *
         */
        static class Test2 {
            static void run() {
                IntensitySegments segments2 = new IntensitySegments();
                System.out.println(segments2.toString());
                segments2.add(10, 30, 1);
                System.out.println(segments2.toString());
                segments2.add(20, 40, 1);
                System.out.println(segments2.toString());
                segments2.add(10, 40, -1);
                System.out.println(segments2.toString());
                segments2.add(10, 40, -1);
                System.out.println(segments2.toString());
            }
        }

        // 空
        // []
        static class Test3 {
            static void run() {
                IntensitySegments segments2 = new IntensitySegments();
                System.out.println(segments2.toString());
            }
        }
        // 插入一个数据
        // [[10,1][30,0]]
        static class Test4 {
            static void run() {
                IntensitySegments segments2 = new IntensitySegments();
                segments2.add(10, 30, 1);
                System.out.println(segments2.toString());
            }
        }

        // add一个数据在一个segment中
        // [[10,2][20,1][30,0]]
        static class Test5 {
            static void run() {
                IntensitySegments segments2 = new IntensitySegments();
                segments2.add(10, 30, 1);
                segments2.add(10, 20, 1);
                System.out.println(segments2.toString());
            }
        }
        // add一个数据在两个segment中
        // [[10,1][20,2][30,1][40,0]]
        static class Test8 {
            static void run() {
                IntensitySegments segments2 = new IntensitySegments();
                segments2.add(10, 30, 1);
                segments2.add(20, 40, 1);
                System.out.println(segments2.toString());
            }
        }
        // 头节点插入
        // [[5,-1][10,0][30,-1][40,0]]
        static class Test9 {
            static void run() {
                IntensitySegments segments2 = new IntensitySegments();
                segments2.add(10, 30, 1);
                segments2.add(5, 40, -1);
                System.out.println(segments2.toString());
            }
        }
        // 头节点删除
        // [[30,-1][40,0]]
        static class Test10 {
            static void run() {
                IntensitySegments segments2 = new IntensitySegments();
                segments2.add(10, 30, 1);
                segments2.add(10, 40, -1);
                System.out.println(segments2.toString());
            }
        }

        // update一个数据在一个segment中
        // [[10,1][15,4][20,1][30,0]]
        static class Test6 {
            static void run() {
                IntensitySegments segments2 = new IntensitySegments();
                segments2.set(10, 30, 1);
                segments2.set(15, 20, 4);
                System.out.println(segments2.toString());
            }
        }
        // update一个数据在两个segment中
        // [[10,1][15,4][40,0]]
        static class Test7 {
            static void run() {
                IntensitySegments segments2 = new IntensitySegments();
                segments2.set(10, 30, 1);
                segments2.set(15, 40, 4);
                System.out.println(segments2.toString());
            }
        }
    }
}
