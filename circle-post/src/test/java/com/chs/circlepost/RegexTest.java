package com.chs.circlepost;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: chs
 * Description:
 * CreateTime: 2025-07-05
 */
public class RegexTest {

    public static void main(String[] args) {
        String input = "#主题1#";
        List<String> inputs = Arrays.asList("主题","","主题,aa");
        String regex = "#(.+?)#";
        String regexTopic = "[\\u4e00-\\u9fa5\\w]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        for (String str : inputs) {
            System.out.println(Pattern.matches(regexTopic, str));
        }
        System.out.println();
    }
}
