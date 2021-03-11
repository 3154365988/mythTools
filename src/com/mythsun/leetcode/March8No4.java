package com.mythsun.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题目链接：https://leetcode-cn.com/problems/isomorphic-strings/
 * 作者：mythSun
 * 时间：21:54
 * 有所得：有时候可以通过将string转为char，用char的ASCII码作为数组的index，然后值按需自己计算，就能建立一个char和值的“假map”关系，关键是数组实现map关系，节约内存
 * 像这种要求一一对应的，可以考虑找到一多对应的，直接返回false，找不到自然就是同构（最快解法的思路）
 * Arrays.fill的用法（重载）：
 * fill(int[] a,int value)：把数组a的所有值，填充成value
 * fill(int[] a,int fromIndex,int toIndex,int value)：把数组a的从fromIndex到toIndex的值，填充成value
 */
public class March8No4 {
    public static void main(String[] args) {
        System.out.println(isIsomorphic("bbbaaaba", "aaabbbba"));
        System.out.println(isIsomorphic("badc", "baba"));
        System.out.println(isIsomorphic("egg", "add"));
        System.out.println(isIsomorphic("foo", "bar"));
        System.out.println(isIsomorphic("paper", "title"));
    }

    /**
     * 有个思路，用map，a组出现的put进去，value自增，存在key不修改。b组同理，然后最后一个循环变成数字之和看是否相等，复杂度n
     * 不加和也行，直接map把字母和位置做记录也一样
     */
    public static boolean isIsomorphic1(String s, String t) {
        Map<Character, Integer> mapS = new HashMap<>();
        Map<Character, Integer> mapT = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            mapS.putIfAbsent(s.charAt(i), i);
            mapT.putIfAbsent(t.charAt(i), i);
        }
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            ss.append(mapS.get(s.charAt(i)));
        }
        StringBuilder tt = new StringBuilder();
        for (int i = 0; i < t.length(); i++) {
            tt.append(mapT.get(t.charAt(i)));
        }
        return ss.toString().equals(tt.toString());
    }

    /**
     * 同构-->替换   ???
     */
    public static boolean isIsomorphic(String s, String t) {
        return false;
    }

    /**
     * 最快解法
     * 也是用char的int值做索引。通过int[]建立索引-值的虚假map
     * 核心：找到某个char对应两个char的时候
     */
    public static boolean isIsomorphic2(String s, String t) {
        int[] map = new int[256];
        char[] one = s.toCharArray();
        char[] two = t.toCharArray();
        //以s数组元素的ASCII码10进制值为index，t数组元素的ASCII码为内容
        Arrays.fill(map, -1);
        for (int i = 0; i < one.length; i++) {
            //当前元素存在映射关系
            if (map[one[i]] != -1) {
                //如果index位置处元素映射的值和当前映射的值不同
                if (map[one[i]] != two[i]) {
                    return false;
                }
            } else {
                // 核心 ：看看有没有其他值映射了two[i]
                for (int j = 0; j < 256; j++) {
                    if (map[j] == two[i]) {
                        return false;
                    }
                }
                // 当two[i]这个char首次出现时，建立以s的值为index（相当于key），t的值为值，这样一个虚假索引
                // 后面如果再出现一个“t的值”，发现之前有，代表有一个“a”和他建立过关系了，自然返回false，代表不能同构
                map[one[i]] = two[i];
            }
        }
        return true;
    }

    /**
     * 最少内存解法
     * 善于利用char的特性
     */
    public static boolean isIsomorphic3(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] preIndexOfS = new int[128];
        int[] preIndexOfT = new int[128];

        for (int i = 0; i < s.length(); i++) {
            char sc = s.charAt(i);
            char tc = t.charAt(i);
            // 利用了char对应单一的int值，并以此作为index，然后记录其值，建立了一种index和值的关联，就不需要map了
            if (preIndexOfS[sc] != preIndexOfT[tc]) {
                return false;
            } else {
                preIndexOfS[sc] = i + 1;
                preIndexOfT[tc] = i + 1;
            }
        }
        return true;
    }


}
