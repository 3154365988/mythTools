package com.mythsun.leetcode;

/**
 * 题目链接：https://leetcode-cn.com/problems/palindrome-number/
 * 作者：mythSun
 * 时间：21:26
 * <p>
 * 判断回文的情况时，最好优先考虑怎么对“回文”下手，因为回文肯定是到一半就能知道结果，那么自然能把循环降到一半，就是最优秀
 */
public class March14 {
    public static void main(String[] args) {
        System.out.println(isPalindrome(2147483647));
//        System.out.println(isPalindrome(123));

//        System.out.println(isPalindrome(0));
//        System.out.println(isPalindrome(121));
//        System.out.println(isPalindrome(-121));
//        System.out.println(isPalindrome(10));
//        System.out.println(isPalindrome(-101));

    }

    public static boolean isPalindrome(int x) {
        if (x < 0) return false;
        if (x == 0) return true;
        if (x % 10 == 0) return false;

        int[] nums = new int[11];
        int tmp = x;
        int ret = 0;

        int count = 0;
        while (tmp != 0) {
            nums[count] = tmp % 10;
            tmp = tmp / 10;
            count++;
        }
        int sum;
        while (count != 0) {
            sum = (int) (Math.pow(10, count - 1) * nums[tmp]);
            if ((ret + sum - sum) == ret) {
                ret = ret + sum;
            } else {
                ret = 0;
                break;
            }
            tmp++;
            count--;
        }
        return (ret - x) == 0;
    }

    /**
     * 最快算法
     *
     * @param x
     * @return
     */
    public boolean isPalindrome1(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int y = 0;
        // 通过y不断增大 直到y==x或者y>x
        while (x > y) {
            // y的增大方式反序增大
            y = y * 10 + x % 10;
            // x同时正序变小
            x = x / 10;
            // 二者“双向奔赴”减少循环。
        }
        // 两种情况，一种完全相等，一种y恰好是x的10倍
        return x == y || x == y / 10;
    }
}
