package com.algorithm.classics.structs.Trees;

public class RangeList {
    public static void main(String[] args) {
        RangeList rangeList = new RangeList();
        System.out.println(rangeList.toString());
        // rangeList.add(Range.of(1,5));
        rangeList.add(Range.of(5,10));
        System.out.println(rangeList.toString());
        System.out.println(rangeList.toString());
        rangeList.add(Range.of(3,20));
        System.out.println(rangeList.toString());
        rangeList.add(Range.of(20,21));
        System.out.println(rangeList.toString());
        rangeList.add(Range.of(2, 4));
        System.out.println(rangeList.toString());
        rangeList.add(Range.of(3, 8));
        System.out.println(rangeList.toString());
        rangeList.remove(Range.of(10, 10));
        System.out.println(rangeList.toString());
        rangeList.remove(Range.of(10, 11));
        System.out.println(rangeList.toString());
        rangeList.remove(Range.of(15, 17));
        System.out.println(rangeList.toString());
        rangeList.remove(Range.of(10, 11));
        System.out.println(rangeList.toString());
    }
    public static class Range {
        int from;
        int to;
        public boolean contains(int val) {
            if (val >= this.from && val < this.to) {
                return true;
            }
            return false;
        }

        public boolean isEmpty() {
            return (this.to - this.from) < 1;
        }

        public static Range of(int from, int to) {
            Range r = new Range();
            r.from = from;
            r.to = to;
            return r;
        }
        public String toString() {
            String result = "[" + this.from + ", " + this.to + ")";
            return result;
        }
    }
    private static class Node {
        Range val;
        Node left;
        Node right;
        public static Node of(Range r) {
            Node n = new Node();
            n.val = r;
            return n;
        }
        public String toString() {
            return this.val.toString() + " ";
        }
    }
    private Node tree;
    enum DeleteOps {
        NodeOnly,
        NodeAndLeftTree,
        NodeAndRightTree,
        NodeFullTree
    }

    private void deleteNodeNormal(Node node, Node parent, boolean leftBranch) {
        if (node == null) {
            return;
        }
        Node newNode = null;
        if (node.left == null) {
            newNode = node.right;
        }
        if (node.right == null) {
            newNode = node.left;
        }
        if (node.left != null && node.right != null) {
            Node p = null;
            Node cur = node.left;
            while (cur.right != null) {
                p = cur;
                cur = cur.right;
            }
            if (p != null) {
                p.right = cur.left;
                node.val = cur.val;
            } else {
                node.left = cur.left;
                node.val = cur.val;
            }
            newNode = node;
        }
        if (parent != null) {
            if (leftBranch) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        } else {
            if (leftBranch) {
                // this.tree.left = newNode;
            } else {
                // this.tree.right = newNode;
            }
            this.tree = newNode;
        }
    }

    /**
     *
     * @param
     * @param parent null for root.
     * @param leftBranch
     * @param ops
     */
    private void deleteNode(Node node, Node parent, boolean leftBranch, DeleteOps ops) {
        if (node == null) {
            return;
        }
        if (ops == DeleteOps.NodeAndLeftTree) {
            node.left = null;
            deleteNode(node, parent, leftBranch, DeleteOps.NodeOnly);
        } else if (ops == DeleteOps.NodeAndRightTree) {
            node.right = null;
            deleteNode(node, parent, leftBranch, DeleteOps.NodeOnly);
        } else if (ops == DeleteOps.NodeFullTree) {
            if (parent != null) {
                if (leftBranch) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            } else {
                if (leftBranch) {
                    // this.tree.left = null;
                } else {
                    // this.tree.right = null;
                }
                this.tree = null;
            }
        } else if (ops == DeleteOps.NodeOnly) {
            deleteNodeNormal(node, parent, leftBranch);
        } else {
            // never
        }
    }

    /**
     * 包含或者可合并
     */
    interface Callback {
        void onHit(Node n, Node parent, boolean leftBranch);
    }
    public void add(Range input) {
        this.add(input, this.tree, null, true);
    }
    private void deleteByRightVal(int rightVal, Node node, Node parent, boolean leftBranch, Callback hitCallback) {
        if (node == null) {
            return;
        }
        if (node.val.contains(rightVal) || node.val.from == rightVal || node.val.to == rightVal) {
            hitCallback.onHit(node, parent, leftBranch);
            return;
        }
        if (rightVal < node.val.from) {
            this.deleteByRightVal(rightVal, node.left, node, leftBranch, hitCallback);
            return;
        } else if (rightVal > node.val.to) {
            Node newNode = node.right;
            deleteNode(node, parent, leftBranch, DeleteOps.NodeAndLeftTree);
            deleteByRightVal(rightVal, newNode, parent, leftBranch, hitCallback);
            return;
        } else {
            // already handled.
        }
    }
    private void deleteByLeftVal(int leftVal, Node node, Node parent, boolean leftBranch, Callback hitCallback) {
        if (node == null) {
            return;
        }
        if (node.val.contains(leftVal) || node.val.to == leftVal) {
            hitCallback.onHit(node, parent, leftBranch);
            return;
        }
        if (leftVal < node.val.from) {
            Node newNode = node.left;
            this.deleteNode(node, parent, leftBranch, DeleteOps.NodeAndRightTree);
            deleteByLeftVal(leftVal, newNode, parent, leftBranch, hitCallback);
            return;
        } else if (leftVal > node.val.to) {
            this.deleteByLeftVal(leftVal, node.right, node, false, hitCallback);
            return;
        } else {
            // todo
        }
    }
    private void add(Range input, Node target, Node parent, boolean leftBranch) {
        if (input.isEmpty()) {
            // 输入为空
            return;
        }
        if (parent == null && target == null) {
            // 二叉树为空直接插入
            this.tree = Node.of(input);
            return;
        }
        if (target == null) {
            // 没找到节点直接插入
            Node newNode = Node.of(input);
            if (leftBranch) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
            return;
        }
        Node curNode = target;
        if (curNode.val.contains(input.from) && curNode.val.contains(input.to - 1)) {
            // 节点包含输入
            return;
        }
        if (input.contains(curNode.val.from) && input.contains(curNode.val.to - 1)) {
            // 输入包含当前节点，删除当前节点再插入
            this.deleteNode(curNode, parent, leftBranch, DeleteOps.NodeOnly);
            this.add(input);
            return;
        }
        if (curNode.val.from > input.to) {
            // 输入在当前节点左边
            this.add(input, curNode.left, curNode, true);
            return;
        }
        if (input.from > curNode.val.to) {
            // 输入在当前节点右边
            this.add(input, curNode.right, curNode, false);
            return;
        }
        if (curNode.val.contains(input.from) || curNode.val.to == input.from) {
            curNode.val.to = input.to;
            // 当前节点命中输入的左边界
            this.deleteByRightVal(input.to, curNode.right, curNode, false, new Callback() {
                @Override
                public void onHit(Node node, Node parent, boolean leftBranch) {
                    // 命中节点融合, 并且删除节点和左子树
                    if (leftBranch) {
                        parent.left = node.right;
                        curNode.val.to = node.val.to;
                    } else {
                        parent.right = node.right;
                        curNode.val.to = node.val.to;
                    }
                }
            });
            return;
        }
        if (curNode.val.contains(input.to) || curNode.val.from == input.to || curNode.val.to == input.to) {
            curNode.val.from = input.from;
            // 当前节点命中输入的右边界
            this.deleteByLeftVal(input.from, curNode.left, curNode, true, new Callback() {
                @Override
                public void onHit(Node node, Node parent, boolean leftBranch) {
                    // 命中节点融合, 并且删除右子树
                    if (leftBranch) {
                        parent.left = node.left;
                        curNode.val.from = node.val.from;
                    } else {
                        parent.right = node.left;
                        curNode.val.from = node.val.from;
                    }
                }
            });
            return;
        }
    }

    public void remove(Range r) {
        this.remove(r, this.tree, null, true);
    }

    private void remove(Range input, Node target, Node parent, boolean leftBranch) {
        if (input.isEmpty()) {
            // 输入为空
            return;
        }
        if (parent == null && target == null) {
            // 二叉树为空
            return;
        }
        if (target == null) {
            // 没找到节点
            return;
        }
        Node curNode = target;
        if (input.contains(curNode.val.from) && input.contains(curNode.val.to - 1)) {
            // 输入包含当前节点
            this.deleteNode(curNode, parent, leftBranch, DeleteOps.NodeOnly);
            this.remove(input);
            return;
        }
        if (curNode.val.contains(input.from) && curNode.val.contains(input.to - 1)) {
            // 当前节点包含输入
            Range newRange1 = Range.of(curNode.val.from, input.from);
            Range newRange2 = Range.of(input.to, curNode.val.to);
            if (newRange1.isEmpty() && newRange2.isEmpty()) {
                this.deleteNode(curNode, parent, leftBranch, DeleteOps.NodeOnly);
                return;
            }
            if (newRange1.isEmpty()) {
                curNode.val = newRange2;
                return;
            }
            if (newRange2.isEmpty()) {
                curNode.val = newRange1;
                return;
            }
            curNode.val = newRange1;
            this.add(newRange2);
            return;
        }
        if (curNode.val.from >= input.to) {
            // 输入在节点左边
            this.remove(input, curNode.left, curNode, true);
            return;
        }
        if (input.from >= curNode.val.to) {
            // 输入在节点右边
            this.remove(input, curNode.right, curNode, false);
            return;
        }
        if (curNode.val.contains(input.from)) {
            curNode.val.to = input.from;
            this.deleteByRightVal(input.to, curNode.right, curNode, false, new Callback() {
                @Override
                public void onHit(Node node, Node parent, boolean leftBranch) {
                    if (node.val.from == input.to) {
                        node.left = null;
                        return;
                    }
                    if (node.val.to == input.to) {
                        if (leftBranch) {
                            parent.left = node.right;
                        } else {
                            parent.right = node.right;
                        }
                        return;
                    }
                    node.val.from = input.to;
                }
            });
            return;
        }
        if (curNode.val.contains(input.to)) {
            curNode.val.from = input.to;
            this.deleteByLeftVal(input.from, curNode.left, curNode, true, new Callback() {
                @Override
                public void onHit(Node node, Node parent, boolean leftBranch) {
                    // 命中节点融合, 并且删除右子树
                    if (node.val.from == input.from) {
                        if (leftBranch) {
                            parent.left = node.left;
                        } else {
                            parent.right = node.left;
                        }
                        return;
                    }
                    if (node.val.to == input.from) {
                        node.right = null;
                        return;
                    }
                    node.val.to = input.from;
                }
            });
            return;
        }
    }

    public String toString() {
        String result = print(this.tree);
        if (result.isEmpty()) {
            return result;
        } else {
            return result.substring(0, result.length() - 1);
        }
    }

    private static String print(Node tree) {
        if (tree == null) {
            return "";
        }
        String result = "";
        result += print(tree.left);
        result += tree.toString();
        result += print(tree.right);
        return result;
    }
}
