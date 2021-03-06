package com.hym.datastructure.algorithm.dynamicplan;

public class MaxFormDynamic2 {

    public static int findMaxForm(String[] strs, int m, int n) {
        if (strs == null || strs.length == 0) {
            return 0;
        }
        int size = strs.length;
        int[] oneSums = new int[size];
        int[] zeroSums = new int[size];
        countZeroAndOneNum(strs, size, oneSums, zeroSums);
        //记录状态值
        int[][] flags = new int[m + 1][n + 1];
        for (int j = 0; j <= m; j++) {
            for (int k = 0; k <= n; k++) {
                flags[j][k] = -1;
            }
        }
        //第一阶段
        flags[0][0] = 0;
        if (zeroSums[0] <= m && oneSums[0] <= n) {
            flags[zeroSums[0]][oneSums[0]] = 1;
        }
        /**
         * 状态转移公式：flags[j + zeroSums[i]][k + oneSums[i]] =max(flags[j][k] + 1, flags[j + zeroSums[i]][k + oneSums[i]])
         */
        for (int i = 1; i < size; i++) {
            for (int j = m; j >= 0; j--) {
                for (int k = n; k >= 0; k--) {
                    if (flags[j][k] >= 0) {
                        if (j + zeroSums[i] <= m && k + oneSums[i] <= n) {
                            if (flags[j][k] + 1 > flags[j + zeroSums[i]][k + oneSums[i]]) {
                                flags[j + zeroSums[i]][k + oneSums[i]] = flags[j][k] + 1;
                            }
                        }
                    }
                }
            }
        }
        //遍历最后一个阶段，找出最大值
        int max = -1;
        for (int j = 0; j <= m; j++) {
            for (int k = 0; k <= n; k++) {
                if (flags[j][k] > max) {
                    max = flags[j][k];
                }
            }
        }

        return max;
    }

    private static void countZeroAndOneNum(String[] strs, int size, int[] oneSums, int[] zeroSums) {
        for (int i = 0; i < size; i++) {
            String str = strs[i];
            if (str != null) {
                int oneSum = 0;
                int zeroSum = 0;
                for (int j = 0; j < str.length(); j++) {
                    if (str.charAt(j) == '1') {
                        oneSum++;
                    }
                    if (str.charAt(j) == '0') {
                        zeroSum++;
                    }
                }
                oneSums[i] = oneSum;
                zeroSums[i] = zeroSum;
            }
        }
    }
}
