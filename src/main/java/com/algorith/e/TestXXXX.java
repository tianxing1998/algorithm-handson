package com.algorith.e;

public class TestXXXX {
    public static void main(String[] args) {

    }
    static class Solution {
        static class TagInt {
            int value;
            int tag = 0; // 1 for in, 2 for out.
            static TagInt inValue(int value) {
                TagInt tagInt = new TagInt();
                tagInt.tag = 1;
                tagInt.value = value;
                return tagInt;
            }
            static TagInt outValue(int value) {
                TagInt tagInt = new TagInt();
                tagInt.tag = 2;
                tagInt.value = value;
                return tagInt;
            }
        }
        static int solve(int[][] timeRanges) {
            TagInt[] sorted = new TagInt[timeRanges.length * 2];
            sorted[0] = TagInt.inValue(timeRanges[0][0]);
            sorted[1] = TagInt.outValue(timeRanges[0][1]);
            int j = 1;
            for (int i = 1; i < timeRanges.length; i++) {
                j++;
                sorted[j].tag = 1;
                sorted[j].value = timeRanges[i][0];
                for (int k = j - 1; k >= 0; k--) {
                    if (sorted[k].value <= sorted[k+1].value) {
                        break;
                    } else {
                        int tmpVal = sorted[k].value;
                        int tmpTag = sorted[k].tag;
                        sorted[k].value = sorted[k + 1].value;
                        sorted[k].tag = sorted[k + 1].tag;
                        sorted[k + 1].value = tmpVal;
                        sorted[k + 1].tag = tmpTag;
                    }
                }
                j++;
                sorted[j].tag = 2;
                sorted[j].value = timeRanges[i][1];
                for (int k = j - 1; k >= 0; k--) {
                    if (sorted[k].value <= sorted[k+1].value) {
                        break;
                    } else {
                        int tmpVal = sorted[k].value;
                        int tmpTag = sorted[k].tag;
                        sorted[k].value = sorted[k + 1].value;
                        sorted[k].tag = sorted[k + 1].tag;
                        sorted[k + 1].value = tmpVal;
                        sorted[k + 1].tag = tmpTag;
                    }
                }
            }

            int max = 0;
            int cnt = 0;
            for (int i = 0; i < sorted.length; i++) {
                if (sorted[0].tag == 1) {
                    cnt++;
                    max = Math.max(max, cnt);
                } else {
                    cnt--;
                }
            }
            return max;
        }

    }

}
