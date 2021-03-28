package com.mythsun.leetcode;

import java.util.Arrays;
/**
 * 题目链接：https://leetcode-cn.com/problems/daily-temperatures/
 * 作者：mythSun
 * 时间：21:49
 * 看不懂的最快实现。单调栈，以前整理过，整理个屁呀，用到了就不会
 */
public class March29 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(dailyTemperatures1(new int[]{73, 74, 100, 71, 69, 72, 76, 73, 74})));
    }

    public static int[] dailyTemperatures(int[] T) {
        if (T == null || T.length == 0) return new int[0];
        int ret[] = new int[T.length];
        int count = 0;
        int index = 0;
        int tmp = 0;
        while (index < T.length - 1) {
            if (T[index] < T[index + 1]) {
                ret[index] = 1;
            } else {
                tmp = index;
                while (index < T.length - 1) {
                    if (T[tmp] < T[index + 1]) {
                        index = tmp;
                        break;
                    } else {
                        count++;
                    }
                    index++;
                }
                if (index == T.length - 1) {
                    index = tmp;
                    ret[tmp] = 0;
                } else {
                    ret[tmp] = count + 1;
                }
                count = 0;
            }
            index++;
        }
        return ret;
    }

    /**
     * 最快的 看不懂
     *
     * @param T
     * @return
     */
    public static int[] dailyTemperatures1(int[] T) {
        if (T == null && T.length == 0) return new int[0];
        int[] res = new int[T.length];
        int[] monoIncArr = new int[T.length];
        int idx = 0;
        for (int i = 1; i < T.length; i++) {
            while (idx >= 0 && T[monoIncArr[idx]] < T[i]) {
                res[monoIncArr[idx]] = i - monoIncArr[idx];
                idx--;
            }
            monoIncArr[++idx] = i;
        }
        return res;
    }
}
