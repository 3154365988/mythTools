package com.mythsun.leetcode;

import java.util.Arrays;

/**
 * 题目链接：https://leetcode-cn.com/problems/string-to-url-lcci/
 * 作者：mythSun
 * 时间：21:54
 * 考验的不是你的思维，而是考你自己封装一个api，而不是直接用已有类库
 * 有所得：String.valueOf的使用，起始还是new String()的实现，只不过有了一些重载方法，比如直接在valueOf时做字符串截取，就不必之后substring了
 */
public class March8No1 {
    public static void main(String[] args) {
//        System.out.println(replaceSpaces4("Mr John Smith    ", 13));
//        System.out.println(replaceSpaces4("               ", 5));
        String s = "abcd";
        char[] chars = s.toCharArray();
        System.out.println(String.valueOf(chars).substring(2));
    }

    /**
     * 约束：输出长度有限制；空格替换为%20
     *
     * @param s      待处理
     * @param length 输出的字符长度
     * @return
     */
    public static String replaceSpaces(String s, int length) {
//        if (s.length() < length) {
//            length = s.length();
//        }
//        s = s.substring(0, length);
//        s = s.replaceAll(" ", "%20");
//        return s;

        return s.substring(0, length).replace(" ", "%20");
    }

    public static String replaceSpaces2(String s, int length) {
        char[] chars = s.toCharArray();
        int realLength = 0;
        for (int i = 0; i < length; i++) {
            if (chars[i] == ' ') {
                realLength++;
            }
        }
        // 开新数组，加内存；不开用算法，加时间，纠结ing
        char[] result = new char[length + 2 * realLength];
        int innerLoop = 0;
        for (int i = 0; i < length; i++) {
            if (chars[i] == ' ') {
                result[innerLoop++] = '%';
                result[innerLoop++] = '2';
                result[innerLoop++] = '0';
            } else {
                result[innerLoop++] = chars[i];
            }
        }
        return new String(result);
    }

    /**
     * 内存最小
     *
     * @param S
     * @param length
     * @return
     */
    public static String replaceSpaces3(String S, int length) {
        if (S.length() == 0) {
            return "";
        }
        char[] cs = S.toCharArray();
        // 题目中知道“真实”长度的意思就是，对输入string转char数组，其长度就是最终的长度（但是有的用例故意加几个空格，啥意思，坑嘛）
        int k = cs.length - 1;
        // 题目中“尾部有足够的空间存放新增字符”就是提示倒着来；
        // i = length - 1 可以明确从输入string的最后一位开始
        for (int i = length - 1; i >= 0; i--) {
            if (cs[i] != ' ') {
                cs[k--] = cs[i];
            } else {
                cs[k--] = '0';
                cs[k--] = '2';
                cs[k--] = '%';
            }
        }
        // 最后一次执行会因为k--导致k变成-1
        // 涉及到k--的使用，这个在用到的地方，还是k，直到下一行才变成-1后的值
        // 这里之所以-1，是因为担心取了所有的值，也确实，用例中有在最后偷偷加了三个空格的情况，导致程序最终输出多了，所以需要截取
        // substring就是从k+1截取到末尾
        return String.valueOf(cs).substring(k + 1);
    }

    /**
     * 时间最少
     *
     * @param S
     * @param length
     * @return
     */
    public static String replaceSpaces4(String S, int length) {
        int i, j;
        char[] s = S.toCharArray();
        for (i = length - 1, j = s.length - 1; i >= 0; i--) {
            if (s[i] == ' ') {
                s[j--] = '0';
                s[j--] = '2';
                s[j--] = '%';
            } else {
                s[j--] = s[i];
            }
        }
        // 相当于从j + 1截取S.length() - j - 1个
        return String.valueOf(s, j + 1, S.length() - j - 1);
    }

}
