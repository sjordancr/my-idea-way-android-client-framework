package com.myideaway.android.util;

import java.util.regex.Pattern;

public class HtmlUtil {

    public static String html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;
        Pattern p_char;
        java.util.regex.Matcher m_char;


        if (null == inputString || "".equals(inputString)) {
            return "";
        }

        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{�?script[^>]*?>[\\s\\S]*?<\\/script>
        // }
        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{�?style[^>]*?>[\\s\\S]*?<\\/style>
        // }
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

        String regEx_char = "&.*;"; // 定义HTML特殊字符的正则表达式

        p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        p_char = Pattern.compile(regEx_char, Pattern.CASE_INSENSITIVE);
        m_char = p_char.matcher(htmlStr);
        htmlStr = m_char.replaceAll(""); // 过滤html特殊字符

        htmlStr = htmlStr.replaceAll("\r", "");
        htmlStr = htmlStr.replaceAll("\n", "");
        htmlStr = htmlStr.replaceAll("\t", "");
        htmlStr = htmlStr.replaceAll("　", "");

        System.out.println(htmlStr);

        textStr = htmlStr;

        return textStr;

    }
}
