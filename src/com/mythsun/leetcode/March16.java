package com.mythsun.leetcode;

import java.util.Arrays;

/**
 * 题目链接：https://leetcode-cn.com/problems/palindrome-number/
 * 作者：mythSun
 * 时间：21:26
 * <p>
 * 判断回文的情况时，最好优先考虑怎么对“回文”下手，因为回文肯定是到一半就能知道结果，那么自然能把循环降到一半，就是最优秀
 */
public class March16 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(addTwoNumbers(new int[]{2,4,3},new int[]{5,6,4})));   //[7,0,8]
        System.out.println(Arrays.toString(addTwoNumbers(new int[]{0},new int[]{0})));   //[0]
        System.out.println(Arrays.toString(addTwoNumbers(new int[]{9,9,9,9,9,9,9},new int[]{9,9,9,9})));   //[8,9,9,9,0,0,0,1]
    }

    public static int[] addTwoNumbers(int[] a, int[] b) {
        int max = a.length > b.length ? a.length + 1 : b.length + 1;
        // 数组迁移
        int[] newA = new int[max];
        int[] newB = new int[max];
        Arrays.fill(newA, -20);
        Arrays.fill(newB, -20);
        for (int i = 0; i < a.length; i++) {
            newA[i] = a[i];
        }
        for (int i = 0; i < b.length; i++) {
            newB[i] = b[i];
        }
        // 返回数组
        int[] ret = new int[max];
        boolean isNext = false;
        int cur = 0;
        for (int i = 0; i < max; i++) {
            // 进位
            if (isNext) {
                newA[i]++;
                isNext = false;
            }
            // 决定下一位是否进位
            cur = newA[i] + newB[i];
            if (cur > 9) {
                cur = cur % 10;
                isNext = true;
            } else if (cur == -39) {
                // 代表有一个值为空
                cur = 1;
            } else if (cur < 0 && cur > -39) {
                cur = newA[i] < 0 ? 1 : newA[i];
                if (cur > 9) {
                    cur = cur % 10;
                    isNext = true;
                }
            }
            ret[i] = cur;
        }
        // 去掉可能多出来的-40
        if (ret[ret.length - 1] == -40) {
            ret = Arrays.copyOf(ret, ret.length - 1);
        }
        return ret;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        return null;
    }

}
