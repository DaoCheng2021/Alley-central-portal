package com.tts.cp.lib.common;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Alley zhao created on 2021/8/30.
 */
public class AlleyUtils {
    private static final String UNDERSCORE_CHAR = "_";
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");

    private AlleyUtils() {
    }

    public static boolean isEmpty(String text) { // 判空，空返回true
        return text == null || text.trim().equals(""); //trim删除字符串头尾空白字符
    }

    public static boolean isEmpty(Object text) {
        return text == null || text.toString().trim().equals("");
    }

    public static String encryptString(String unencodeString) {  //MD5加密
        return encryptString(unencodeString, "MD5");
    }

    public static String encryptString(String unencodeString, String encryptAlgorithm) {
        byte[] unencodedStr = unencodeString.trim().getBytes();
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance(encryptAlgorithm);
        } catch (Exception var7) {
            return unencodeString;
        }

        md.reset();
        md.update(unencodedStr);
        byte[] encodedStr = md.digest();
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < encodedStr.length; ++i) {
            if ((encodedStr[i] & 255) < 16) {
                buf.append("0");
            }

            buf.append(Long.toString((long) (encodedStr[i] & 255), 16));
        }

        return buf.toString();
    }

    public static String list2Values(List<String> list) { //('aa','bb') list集合变成这种类型String数据
        if (list != null && !list.isEmpty()) {
            Iterator<String> iList = list.iterator();
            StringBuffer sb = (new StringBuffer("(")).append("'").append((String) iList.next()).append("'");
            // iterator 迭代器 .next()返回迭代器下一个元素 .hasNext()判断迭代器有值就是true
            while (iList.hasNext()) {
                sb.append(",'").append((String) iList.next()).append("'");
            }

            sb.append(")");
            return sb.toString();
        } else {
            return "";
        }
    }

    public static boolean isDigit(String s) {
        return DIGIT_PATTERN.matcher(s).matches();
    }

    public static String underscoreName(String name) { //itemId->item_id
        if (!hasLength(name)) {
            return "";
        } else {
            StringBuilder result = new StringBuilder();
            result.append(name.substring(0, 1).toLowerCase());

            for (int i = 1; i < name.length(); ++i) {
                String s = name.substring(i, i + 1);
                String slc = s.toLowerCase();
                if (!isDigit(slc) && !s.equals(slc)) {
                    result.append("_").append(slc);
                } else {
                    result.append(s);
                }
            }

            return result.toString();
        }
    }

    public static String camelCaseName(String name) { //item_id->itemId
        StringBuffer result = new StringBuffer();
        if (name != null && name.length() > 0) {
            result.append(name.substring(0, 1).toLowerCase());

            for (int i = 1; i < name.length(); ++i) {
                String s = name.substring(i, i + 1);
                if ("_".equals(s)) {
                    s = name.substring(i + 1, i + 2).toUpperCase();
                    ++i;
                }

                result.append(s);
            }
        }

        String camelCaseName = result.toString();
        return camelCaseName;
    }

    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        } else {
            int strLen = str.length();

            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

    public static String mergeTemplate(String tpl, Object data) throws Exception {
        if (data == null) {
            return tpl;
        } else {
            Velocity.init();
            VelocityContext context = new VelocityContext();
            context.put("data", data);
            StringWriter out = new StringWriter();
            Velocity.evaluate(context, out, "com.tts.lib.utils.TextUtil", tpl);
            String targetStr = out.getBuffer().toString();
            out.flush();
            out.close();
            return targetStr;
        }
    }

    /*
    *        <dependency>
            <groupId>org.apache.velocity</groupId>
            <artifactId>velocity-engine-core</artifactId>
            <version>2.0</version>
        </dependency>
        <!--这个包是反向驼峰映射的-->

    **/
}
