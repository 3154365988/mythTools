package com.mythsun.leetcode;

import java.util.Arrays;

/**
 * 题目链接：https://leetcode-cn.com/problems/add-two-numbers/
 * 作者：mythSun
 * 时间：21:26
 * <p>
 * 链表的最后一位有个特点，指向null，通过该null用于判断，可以知道是否为最后一个，做相应的逻辑
 */
public class March20 {
    public static void main(String[] args) {
        ListNode listNode = addTwoNumbers(new ListNode(1, new ListNode(8, null)),
                new ListNode(0, null));
        pri(listNode);
        listNode = addTwoNumbers(new ListNode(9, new ListNode(9, new ListNode(1, null))),
                new ListNode(1, null));
        pri(listNode);
        listNode = addTwoNumbers(new ListNode(2, new ListNode(4, new ListNode(3, null))),
                new ListNode(5, new ListNode(6, new ListNode(4, null))));
        pri(listNode);
        listNode = addTwoNumbers(new ListNode(0, null),
                new ListNode(0, null));
        pri(listNode);
        listNode = addTwoNumbers(new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, null))))))),
                new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, null)))));
        pri(listNode);
    }

    public static void pri(ListNode l1) {
        while (l1 != null) {
            System.out.print(l1.val);
            System.out.print(" , ");
            l1 = l1.next;
        }
        System.out.println("-----------------------------------------");
    }

    /**
     * 最省内存
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null, tail = null;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int val1 = l1 != null ? l1.val : 0; //对null的做0值，但是不存在的l1怎么点val呢
            int val2 = l2 != null ? l2.val : 0;
            int sumVal = (val1 + val2 + carry) % 10;
            carry = (val1 + val2 + carry) / 10;
//            System.out.println(val1 + " " + val2 + " " + carry);

            if (head == null) {
                head = tail = new ListNode(sumVal);
            } else {
                // 通过构造，直接赋值了，下面用next就行
                tail.next = new ListNode(sumVal);
                tail = tail.next;
            }

            if (l1 != null) l1 = l1.next;   //防止出现空指针，不存在自然就一直用最后一个，最后一个的next就是一个null，而最开始，判null直接变成0，不会调用val
            if (l2 != null) l2 = l2.next;


        }
        return head;
    }

    /**
     * 同样2ms，但是简洁
     * 一个思路 只是更简洁
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        int carry = (l1.val + l2.val) / 10;
        head.val = (l1.val + l2.val) % 10;// 初值

        ListNode p = head;//指针
        while (l1.next != null && l2.next != null) {
            l1 = l1.next;
            l2 = l2.next;
            ListNode newNode = new ListNode((l1.val + l2.val + carry) % 10);
            carry = (l1.val + l2.val + carry) / 10;

            p.next = newNode;//next指向
            p = p.next;//next转移
        }
        while (l1.next != null) {
            l1 = l1.next;
            ListNode newNode = new ListNode((l1.val + carry) % 10);
            carry = (l1.val + carry) / 10;

            p.next = newNode;
            p = p.next;
        }
        while (l2.next != null) {
            l2 = l2.next;
            ListNode newNode = new ListNode((l2.val + carry) % 10);
            carry = (l2.val + carry) / 10;

            p.next = newNode;
            p = p.next;
        }
        if (carry == 1) {
            ListNode newNode = new ListNode(1);

            p.next = newNode;
        }
        return head;
    }

    public static ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode head = null;
        int sum;
        boolean isBitwise = false;

        while (l1 != null && l2 != null) {
            ListNode tmp = new ListNode();
            if (isBitwise) {
                l1.val++;
                isBitwise = false;
            }
            sum = l1.val + l2.val;
            if (sum > 9) {
                sum = sum % 10;
                isBitwise = true;
            }
            l1 = l1.next;
            l2 = l2.next;
            tmp.val = sum;
            head = add(head, tmp);
        }

        while (l1 != null) {
            ListNode tmp = new ListNode();
            if (isBitwise) {
                if (l1.val == 9) {
                    tmp.val = 0;
                    isBitwise = true;
                    l1 = l1.next;
                    head = add(head, tmp);
                    continue;
                }
                tmp.val = l1.val + 1;
                l1 = l1.next;
                head = add(head, tmp);
                isBitwise = false;
            } else {
                tmp.val = l1.val;
                l1 = l1.next;
                head = add(head, tmp);
            }
        }

        while (l2 != null) {
            ListNode tmp = new ListNode();
            if (isBitwise) {
                if (l2.val == 9) {
                    tmp.val = 0;
                    isBitwise = true;
                    l2 = l2.next;
                    head = add(head, tmp);
                    continue;
                }
                tmp.val = l2.val + 1;
                l2 = l2.next;
                head = add(head, tmp);
                isBitwise = false;
            } else {
                tmp.val = l2.val;
                l2 = l2.next;
                head = add(head, tmp);
            }
        }

        if (isBitwise) {
            ListNode tmp = new ListNode();
            tmp.val = 1;
            head = add(head, tmp);
        }
        return head;
    }

    private static ListNode add(ListNode head, ListNode tmp) {
        if (head == null) {
            head = tmp;
            return head;
        }
        ListNode cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = tmp;
        return head;
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
