package com.hym.datastructure.algorithm.dynamicplan;

public class MaxFormDynamic {

    public static int findMaxForm(String[] strs, int m, int n) {
        if (strs == null || strs.length == 0) {
            return 0;
        }
        int size = strs.length;
        int[] oneSums = new int[size];
        int[] zeroSums = new int[size];
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
        //记录状态值
        int[][][] flags = new int[size][m + 1][n + 1];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= n; k++) {
                    flags[i][j][k] = -1;
                }
            }
        }
        //第0阶段的状态
        flags[0][0][0] = 0;
        if (zeroSums[0] <= m && oneSums[0] <= n) {
            flags[0][zeroSums[0]][oneSums[0]] = 1;
        }
        /**
         * 状态转移公式：
         * flags[i][j][k]=max(flags[i - 1][j][k],flags[i][j][k]);
         * flags[i][j + zeroSums[i]][k + oneSums[i]]=max(flags[i][j + zeroSums[i]][k + oneSums[i]],flags[i - 1][j][k] + 1)
         */
        for (int i = 1; i < size; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= n; k++) {
                    if (flags[i - 1][j][k] >= 0) {
                        if (flags[i - 1][j][k] > flags[i][j][k]) {
                            flags[i][j][k] = flags[i - 1][j][k];
                        }
                        if (j + zeroSums[i] <= m && k + oneSums[i] <= n) {
                            if (flags[i - 1][j][k]  > flags[i][j + zeroSums[i]][k + oneSums[i]]) {
                                flags[i][j + zeroSums[i]][k + oneSums[i]] = flags[i - 1][j][k] + 1;
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
                if (flags[size - 1][j][k] > max) {
                    max = flags[size - 1][j][k];
                }
            }
        }

        return max;
    }
}
