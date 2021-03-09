package com.mythsun.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 题目链接：https://leetcode-cn.com/problems/shuffle-string/
 * 作者：mythSun
 * 时间：21:54
 * 有所得：能用charAt，不要转char数组，减少内存损耗？？？底层用的是byte[]
 */
public class March8No2 {
    public static void main(String[] args) {
        System.out.println(restoreString3("codeleet", new int[]{4, 5, 6, 7, 0, 2, 1, 3}));
    }

    public static String restoreString(String s, int[] indices) {
        if (s == null || s.length() == 0 || indices == null || indices.length == 0)
            return "";
        char[] chars = s.toCharArray();
        char[] result = new char[s.length()];
        for (int i = 0; i < indices.length; i++) {
            result[indices[i]] = chars[i];
        }
        return String.valueOf(result);
    }

    public static String restoreString2(String s, int[] indices) {
        if (s == null || s.length() == 0 || indices == null || indices.length == 0)
            return "";
        char[] chars = s.toCharArray();
        Map<Integer,Character> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(indices[i],chars[i]);
        }
        for (int i = 0; i < s.length(); i++) {
            chars[i] = map.get(i);
        }
        return String.valueOf(chars);
    }

    public static String restoreString3(String s, int[] indices) {
        char[] result = new char[indices.length];
        for (int i = 0; i < s.length(); i++) {
            result[indices[i]] = s.charAt(i);
        }
        return String.valueOf(result);
    }
}
