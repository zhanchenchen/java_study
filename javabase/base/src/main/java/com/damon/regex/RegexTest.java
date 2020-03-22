package com.damon.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配测试
 */
public class RegexTest {
    public static void main(String[] args) {
//        testPattern();
//        testGroup();
//        testGroupAdvance();
//        testGroupRegion();
//        testReset();
//        testReplace();
        testAppendReplacement();
//        testPatternMatches("1");
//        testPatternSplit();
//        testStringRegex();
    }

    /**
     * pattern matcher
     * matches、lookingAt、find
     */
    public static void testPattern(){
        /* 创建正则表达式
        * 1、?表示出现零或一次
        * 2、+表示出现一次或多次
        * 3、*表示出现或不出现
        * */
        Pattern pattern = Pattern.compile("java");
        System.out.println(pattern.pattern());
        String test1 = "java";
        String test2 = "java123";
        String test3 = "123java";
        /* matches 全字符匹配*/
        System.err.println("—————— matches ——————");
        Matcher matcher = pattern.matcher(test1);
        System.out.println(matcher.matches());  // true
        matcher = pattern.matcher(test2);
        System.out.println(matcher.matches());  // false

        /* lookingAt 从头开始查找*/
        System.err.println("—————— lookingAt ——————");
        matcher = pattern.matcher(test2);
        System.out.println(matcher.lookingAt());  // true
        matcher = pattern.matcher(test3);
        System.out.println(matcher.lookingAt());  // false

        /* find 任意位置查找或指定起始位置查找*/
        System.err.println("—————— find ——————");
        matcher = pattern.matcher(test2);
        System.out.println(matcher.find());  // true
        matcher = pattern.matcher(test3);
        System.out.println(matcher.find());  // true
        matcher = pattern.matcher(test2);
        System.out.println(matcher.find(4));  // false
        matcher = pattern.matcher(test3);
        System.out.println(matcher.find(2));  // true
    }

    /**
     * 这里介绍下组的概念：组是用括号划分的正则表达式，可以根据组的编号来引用这个组。组号为0表示整个表达式，组号为1表示被第一对括号括起的组，依次类推，
     * 例如A(B(C))D，组0是ABCD，组1是BC，组2是C。
     *
     * Matcher类提供了start()，end()，group()分别用于返回字符串的起始索引，结束索引，以及匹配到到的字符串。
     */
    public static void testGroup(){
        Pattern pattern = Pattern.compile("java");
        String test = "123java456";
        Matcher matcher = pattern.matcher(test);
        matcher.find(); // 查找任意位置
        System.out.println(matcher.start());    // 3
        System.out.println(matcher.end());      // 7
        System.out.println(matcher.group());    //java
    }

    /**
     * Matcher类提供了start(int gropu)，end(int group)，group(int i)，groupCount()用于分组操作
     */
    public static void testGroupAdvance(){
        Pattern pattern = Pattern.compile("(java)(python)");
        String test = "123javapython456";
        Matcher matcher = pattern.matcher(test);
        matcher.find(); // 查找任意位置
        System.out.println(matcher.groupCount());   // 2 返回两个分组

        System.out.println(matcher.group(1));   // java 返回第一组匹配到字符串
        System.out.println(matcher.start(1));   // 3 第一组起始索引
        System.out.println(matcher.end(1));   // 7 第一组结束索引

        System.out.println(matcher.group(2));   // python 返回第二组匹配到字符串
        System.out.println(matcher.start(2));   // 3 第二组起始索引
        System.out.println(matcher.end(2));   // 7 第二组结束索引
    }


    /**
     * 设定查找范围
     * Matcher类还提供region(int start, int end)(不包括end)方法用于设定查找范围，并提供regionStrat()和regionEnd()用于返回起始和结束查找的索引
     */
    public static void testGroupRegion(){
        Pattern pattern = Pattern.compile("java");
        String test = "123javajava";
        Matcher matcher = pattern.matcher(test);
        matcher.region(7,11);   // 设置查找范围
        System.out.println(matcher.regionStart());   // 7
        System.out.println(matcher.regionEnd());   // 11
        matcher.find(); // 查找任意位置
        System.out.println(matcher.group());   // java
    }

    /**
     * Matcher类提供了两种用于重置当前匹配器的方法:reset()和reset(CharSequence input)
     */
    public static void testReset(){
        Pattern pattern = Pattern.compile("java");
        String test = "java";
        Matcher matcher = pattern.matcher(test);
        matcher.find();
        System.out.println(matcher.group());    // java

        matcher.reset();    // 从起始位置匹配
        matcher.find();
        System.out.println(matcher.group());    // java

        matcher.reset("python");
        System.out.println(matcher.find()); // false
    }

    /**
     * 最后说一下Matcher类的匹配方法：replaceAll(String replacement) 和 replaceFirst(String replacement)，
     * 其中replaceAll是替换全部匹配到的字符串，而replaceFirst仅仅是替换第一个匹配到的字符串
     */
    public static void testReplace(){
        Pattern pattern = Pattern.compile("java");
        String test = "javajava";
        Matcher matcher = pattern.matcher(test);
        System.out.println(matcher.replaceFirst("python")); // pythonjava
        System.out.println(matcher.replaceAll("python"));   // pythonpython
    }

    /**
     * 还有两个方法appendReplacement(StringBuffer sb, String replacement) 和 appendTail(StringBuffer sb)也很重要，
     * appendReplacement允许直接将匹配的字符串保存在另一个StringBuffer中并且是渐进式匹配，
     * 并不是只匹配依次或匹配全部，而appendTail则是将未匹配到的余下的字符串添加到StringBuffer中。
     */
    public static void testAppendReplacement(){
        Pattern pattern = Pattern.compile("java");
        Matcher matcher = pattern.matcher("java123");
        System.out.println(matcher.find());
        StringBuffer sb = new StringBuffer();

        matcher.appendReplacement(sb,"python");
        System.out.println(sb); // python

        matcher.appendTail(sb);
        System.out.println(sb); //  python123
    }

    /**
     * Pattern类也自带一个静态匹配方法matches(String regex, CharSequence input)，但只能进行全字符串匹配并且只能返回是否匹配上的boolean值
     * @param data 匹配数据
     */
    public static void testPatternMatches(String data){
        System.out.println(Pattern.matches("[0-9]*",data));
    }

    /**
     * Matcher类提供了对正则表达式的分组支持,以及对正则表达式的多次匹配支持，要想得到更丰富的正则匹配操作,那就需要将Pattern与Matcher联合使用。
     * Matcher类提供了三个返回boolean值得匹配方法：matches()，lookingAt()，find()，find(int start)，
     * 其中matches()用于全字符串匹配，lookingAt从字符串最开头开始匹配满足的子串，find可以对任意位置字符串匹配,其中start为起始查找索引值。
     */
    public static void testPatternSplit(){
        String str = "123java321java213";
        Pattern pattern = Pattern.compile("java");
        String[] result = pattern.split(str);
        for (String one:result) {
            System.out.println(one);
        }
    }

    /**
     * 字符串直接匹配正则
     */
    public static void testStringRegex(){
        String num = "11";
        System.out.println(num.matches("[0-9]?"));
    }
}
