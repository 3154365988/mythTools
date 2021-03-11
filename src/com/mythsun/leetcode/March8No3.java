package com.mythsun.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 题目链接：https://leetcode-cn.com/problems/minimum-size-subarray-sum/
 * 作者：mythSun
 * 时间：21:54
 * 有所得：滑动窗口思维：把数组的的一部分，用一个窗口框起来。变幻窗口的起始位置，就会滑动起来；期间可以修改窗口的长度，就是这个题的解法了
 * 滑动窗口-->双指针
 */
public class March8No3 {
    public static void main(String[] args) {
        System.out.println(minSubArrayLen(6, new int[]{10, 2, 3}));
        System.out.println(minSubArrayLen(15, new int[]{1, 2, 3, 4, 5}));
        System.out.println(minSubArrayLen(11, new int[]{1, 2, 3, 4, 5}));
        System.out.println(minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}));
        System.out.println(minSubArrayLen(4, new int[]{1, 4, 4}));
        System.out.println(minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}));
    }

    /**
     * 复杂度 平方阶
     */
    public static int minSubArrayLen1(int target, int[] nums) {
        int subResult;
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            subResult = target - nums[i];
            if (subResult <= 0) {
                return 1;
            }

            int sumCount = 0;
            for (int j = 1; j < nums.length; j++) {
                if (i + j < nums.length) {
                    if (subResult - nums[i + j] <= 0) {
                        sumCount = j + 1;
                        break;
                    }
                    subResult = subResult - nums[i + j];
                } else {
                    break;
                }
            }
            if (sumCount != 0) {
                if (result > sumCount || result == 0) {
                    result = sumCount;
                }
            }
        }
        return result;
    }

    /**
     * 优秀写法
     */
    public static int minSubArrayLen(int target, int[] nums) {
        // result用于防止后面出现比前面更下的组合,之所以10000是因为题目给出了限制，不然就用 Integer.MAX_VALUE
        int result = 100000;
        int sum = 0; // 滑动窗口数值之和
        int i = 0; // 滑动窗口起始位置
        int subLength = 0; // 滑动窗口的长度
        for (int j = 0; j < nums.length; j++) {
            sum += nums[j];
            // 注意这里使用while，每次更新 i（起始位置），并不断比较子序列是否符合条件
            // 只有sum大于等于目标值才会进入内循环
            while (sum >= target) {
                subLength = (j - i + 1); // 取子序列的长度
                result = result < subLength ? result : subLength;
                // 每次变更滑动窗口的起始位置，能让每一个数组元素都被计算，因为外循环会从j=0开始计算，所以下面自然自减的时候，也要从0的索引开始减
                sum -= nums[i++]; // 这里体现出滑动窗口的精髓之处，不断变更i（子序列的起始位置）
            }
        }
        // 如果result没有被赋值的话，就返回0，说明没有符合条件的子序列
        return result == 100000 ? 0 : result;
    }

}
