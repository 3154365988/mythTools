package com.mythsun.leetcode;

/**
 * 题目链接：https://leetcode-cn.com/problems/reverse-integer/
 * 作者：mythSun
 * 时间：22:08
 * 所得：对整数的处理，可以靠%10来获得末尾的数，然后再/10，整数就会抹掉最后一位。非常适合用来拿到自己需要的某一位
 * 整数的越界，也就是溢出，会导致最大变最小，或者反之，可以通过这个来确定是否越界
 * 除法，取的是最小，余的舍去，比如3/2 = 1 ； 5/10 = 0 ； 11/10 = 1
 */
public class March12 {
    public static void main(String[] args) {
        System.out.println(reverse(123));
        System.out.println(reverse(-123));
        System.out.println(reverse(120));
        System.out.println(reverse(0));
    }

    public void fun(int x, int y) {
        for (int i = 0; i < x; i++) {
            if (y == i) {
                return;
            }
        }
    }

    public static int reverse(int x) {
        int result = 0;
        int pop;
        while (x != 0) {
            // 弹出最后一位
            pop = x % 10;
            x = x / 10;
            // 防止越界
            if ((result * 10 + pop - pop) / 10 != result) {
                return 0;
            }
            // 推入刚刚弹出的
            result = result * 10 + pop;
        }
        return result;
    }

}
