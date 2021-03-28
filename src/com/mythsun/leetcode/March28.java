package com.mythsun.leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * 题目链接：https://leetcode-cn.com/problems/calculator-lcci/
 * 作者：mythSun
 * 时间：10:07
 * 要多注意写代码前估计代码量，能过让自己更加清晰将要怎么写；哪怕传入的参数在下面调用时引不起异常，也要做检验，因为以后拓展功能可能会调用引起异常；
 * 用当前指针小于长度，就能轻松作为数组自循环的条件，同时当前指针++或者--更可以轻松前后移动
 */
public class March28 {

    public static void main(String[] args) {
        calculate("2+   2 *  3*2");
        calculate("222");
    }

    /**
     * 一遍过，但是数据感人哈哈哈，去看看高手的
     *
     * @param s
     * @return
     */
    public static int calculate(String s) {
        // 字符串校验3行，省2行
        if (s == null || "".equals(s)) return 0;
        // 申请所需内存6行，多2行
        int sLen = s.length();
        int[] data = new int[sLen];
        String[] symbol = new String[sLen];
        String[] format = s.split("");
        String[] tmp = new String[sLen];
        int dataIndex = 0;
        int symbolIndex = 0;
        int tmpLen = 0;
        // 字符串转表达式 25行，多10行，少估计了一个变量
        for (String c : format) {
            switch (c) {
                case "+":
                case "-":
                case "*":
                case "/":
                    symbol[symbolIndex] = c;
                    symbolIndex++;
                    // 计算data
                    for (int i = tmpLen - 1, j = 0; i >= 0; i--, j++) {
                        data[dataIndex] = (int) (data[dataIndex] + Integer.parseInt(tmp[j]) * Math.pow(10, i));
                    }
                    dataIndex++;
                    tmpLen = 0;
                    break;
                case " ":
                    break;
                default:
                    tmp[tmpLen] = c;
                    tmpLen++;
                    break;
            }
        }
        // 最后一个数字
        for (int i = tmpLen - 1, j = 0; i >= 0; i--, j++) {
            data[dataIndex] = (int) (data[dataIndex] + Integer.parseInt(tmp[j]) * Math.pow(10, i));
        }
        // 运算7行，多22行没想到好的处理逻辑，用了最直接的
        String as = "";
        for (int i = 0; i < symbol.length; i++) {
            if (symbol[i] == null) {
                break;
            }
            if ("*".equals(symbol[i])) {
                data[i + 1] = data[i] * data[i + 1];
                data[i] = 0;
                symbol[i] = as;
            }
            if ("/".equals(symbol[i])) {
                data[i + 1] = data[i] / data[i + 1];
                data[i] = 0;
                symbol[i] = as;
            }
            as = symbol[i];
        }
        int ret = 0;
        for (int i = 0; i < symbol.length; i++) {
            if (symbol[i] == null) {
                ret = data[i];
                break;
            }
            if ("+".equals(symbol[i])) {
                data[i + 1] = data[i] + data[i + 1];
            }
            if ("-".equals(symbol[i])) {
                data[i + 1] = data[i] - data[i + 1];
            }
        }
        return ret;
    }

    /**
     * 最快，看起来层数太多
     *
     * @param s
     * @return
     */
    public static int calculate1(String s) {
        char[] ss = s.toCharArray();
        int res = 0, multires = 1, cur = 0, pre = 0, len = ss.length, lastnum = 0;
        boolean ismin = false, isSet = false;
        // 用当前指针小于长度，就能轻松作为数组自循环的条件，同时当前指针++或者--更可以轻松前后移动
        while (cur < len) {
            if (ss[cur] == '+' || ss[cur] == '-') {
                if (isSet == true) {
                    if (ismin == true) {
                        res -= multires;
                        ismin = false;
                    } else {
                        res += multires;
                    }
                    isSet = false;
                } else {
                    if (ismin == true) {
                        res -= lastnum;
                        ismin = false;
                    } else {
                        res += lastnum;
                    }
                }
                if (ss[cur] == '-') {
                    ismin = true;
                }
                lastnum = 0;
            } else if (ss[cur] == '*' || ss[cur] == '/') {
                if (isSet == false) {
                    multires = lastnum;
                    isSet = true;
                }
                if (ss[cur] == '*') {
                    int num = 0;
                    while (cur < len && (ss[cur] < '0' || ss[cur] > '9')) {
                        cur++;
                    }
                    while (cur < len && ss[cur] >= '0' && ss[cur] <= '9') {
                        num = num * 10 + (ss[cur] - '0');
                        cur++;
                    }
                    multires *= num;
                    cur--;
                } else {
                    int num = 0;
                    while (cur < len && (ss[cur] < '0' || ss[cur] > '9')) {
                        cur++;
                    }
                    while (cur < len && ss[cur] >= '0' && ss[cur] <= '9') {
                        num = num * 10 + (ss[cur] - '0');
                        cur++;
                    }
                    multires /= num;
                    cur--;
                }
                lastnum = 0;
            } else if (ss[cur] >= '0' && ss[cur] <= '9') {
                lastnum = lastnum * 10 + (ss[cur] - '0');
            }
            cur++;
        }
        if (isSet == true) {
            if (ismin == true) {
                res -= multires;
            } else {
                res += multires;
            }
        } else {
            if (ismin == true) {
                res -= lastnum;
            } else {
                res += lastnum;
            }
        }
        return res;
    }

    /**
     * 栈实现
     *
     * @param s
     * @return
     */
    public int calculate2(String s) {

        char[] chars = s.trim().replace(" ", "").toCharArray();
        Stack<Integer> stack = new Stack<>();

        int i = 0;
        char op = 'M';

        while (i < chars.length) {
            if (chars[i] == '-' || chars[i] == '+' || chars[i] == '*' || chars[i] == '/') {
                op = chars[i];
                i++;
            }
            int num = 0;
            while (i < chars.length && chars[i] >= '0' && chars[i] <= '9') {
                num = num * 10 + chars[i] - '0';
                i++;
            }
            switch (op) {
                case '-':
                    stack.push(-num);
                    break;
                case '*':
                    num = stack.pop() * num;
                    stack.push(num);
                    break;
                case '/':
                    num = stack.pop() / num;
                    stack.push(num);
                    break;
                case '+':
                    stack.push(num);
                    break;
                default:
                    stack.push(num);
                    break;
            }
        }
        int total = 0;
        while (!stack.isEmpty()) {
            total += stack.pop();
        }
        return total;
    }

    /**
     * 最小内存
     * @param s
     * @return
     */
    public int calculate3(String s) {
        if(s==null||s.length()==0)return 0;
        s=s.replace(" ","");
        Stack<Integer> num=new Stack<>();
        int n=0;
        char op='+';
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(Character.isDigit(c)){
                n=n*10+(c-'0');
            }
            if(!Character.isDigit(c)||i==s.length()-1){
                int pre;
                switch(op){
                    case '+': num.push(n);
                        break;
                    case '-': num.push(-n);
                        break;
                    case '*': pre=num.pop();
                        num.push(pre*n);
                        break;
                    case '/': pre=num.pop();
                        num.push(pre/n);
                        break;
                }
                op=c;
                n=0;
            }
        }
        int res=0;
        while(!num.isEmpty()){
            res+=num.pop();
        }
        return res;
    }
}
