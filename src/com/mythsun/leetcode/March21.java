package com.mythsun.leetcode;

import java.util.Arrays;

/**
 * 题目链接：https://leetcode-cn.com/problems/reverse-linked-list/submissions/
 * 作者：mythSun
 * 时间：21:26
 * <p>
 * 反转就是用ListNode temp = cur.next;提前储存原先的下一个，下一轮开始前赋值给下一路的当前指针即可
 */
public class March21 {
    public static void main(String[] args) {
        pri(reverseList(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null)))))));
        pri(reverseList(new ListNode(1, null)));
        pri(reverseList(new ListNode(0, null)));
        pri(reverseList(null));
    }

    public static ListNode reverseList1(ListNode head) {
        if (head == null) return null;
        ListNode ret = head;
        ListNode cur = ret;
        ListNode pre = null;
        while (cur != null) {
            ListNode tmp = cur.next;
            if (tmp == null) {
                ret = cur;
            }
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return ret;
    }

    /**
     * 内存消耗最少
     *
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode temp = cur.next;
            cur.next = prev;//指向之前
            prev = cur;
            cur = temp;
        }
        return prev;
    }


    public static void pri(ListNode l1) {
        while (l1 != null) {
            System.out.print(l1.val);
            System.out.print(" , ");
            l1 = l1.next;
        }
        System.out.println("-----------------------------------------");
    }

    public static class ListNode {
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
}
