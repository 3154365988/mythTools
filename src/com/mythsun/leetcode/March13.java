package com.mythsun.leetcode;

/**
 * 题目链接：https://leetcode-cn.com/problems/longest-common-prefix/
 * 作者：mythSun
 * 时间：19:00
 * substring 第二个参数是索引，但是是“不闭合”也就是输入substring(0, 2)，实际取得是0和1，因为不是闭合区间，2没有取到
 */
public class March13 {
    public static void main(String[] args) {
        System.out.println(longestCommonPrefix(new String[]{"a"}));
        System.out.println(longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
        System.out.println(longestCommonPrefix(new String[]{"dog", "racecar", "car"}));
        System.out.println(longestCommonPrefix(new String[]{""}));
        System.out.println(longestCommonPrefix(new String[3]));
    }

    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0 || strs[0] == null || strs[0].length() == 0) {
            return "";
        }
        int mixStrLength = 201;
        int tmp;
        for (int i = 0; i < strs.length; i++) {
            tmp = strs[i].length();
            if (mixStrLength > tmp) {
                mixStrLength = tmp;
            }
        }
        boolean isMatch = true;
        for (int i = 0; i < mixStrLength; i++) {
            for (int j = 1; j < strs.length; j++) {
                if (strs[0].charAt(i) != strs[j].charAt(i)) {
                    isMatch = false;
                    break;
                }
            }
            if (!isMatch) {
                return strs[0].substring(0, i);
            }
        }
        return strs[0].substring(0, mixStrLength);
    }

    /**
     * 最快解法
     * A.startsWith(B)  :  作用为A是否以B开头
     * 配合上面的方法,控制循环指针,一旦某个不可以,把B去掉最后一个字符,然后通过i--回滚一个循环,重新比较
     */
    public String longestCommonPrefix1(String[] strs) {
        if(strs.length == 0) {
            return "";
        }
        if(strs.length == 1) {
            return strs[0];
        }
        String result = strs[0];
        for(int i = 0; i < strs.length; i++) {
            if(!strs[i].startsWith(result)) {
                result = result.substring(0, result.length() - 1);
                i--;
            }
        }
        return result;
    }
}
