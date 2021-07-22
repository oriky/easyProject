package com.zhousj.common.ext.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * string 工具类
 *
 * @author zhousj
 * @date 2020/8/5
 */
@SuppressWarnings("unused")
public class StringUtil {

    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);


    private StringUtil() {
    }


    /**
     * 判断字符串是否为空或空字符
     */
    public static boolean isEmpty(String source) {
        return source == null || source.isEmpty() || source.trim().isEmpty();
    }


    /**
     * 判断字符串不为空
     */
    public static boolean isNotEmpty(String source) {
        return !isEmpty(source);
    }


    /**
     * 判断字符串是否为空
     */
    public static boolean isNull(String source) {
        return source == null;
    }


    /**
     * 判断字符串不为空
     */
    public static boolean nonNull(String source) {
        return !isNull(source);
    }


    /**
     * 字符串为空则返回other字符串
     */
    public static String isNullOr(String source, String other) {
        return nonNull(source) ? source : other;
    }


    /**
     * 字符串为空则返回other字符串
     */
    public static String isEmptyOr(String source, String other) {
        return isNotEmpty(source) ? source : other;
    }


    /**
     * 如果为null爆出异常
     */
    public static void isNullThrow(String source) {
        isNullThrow(source, "");
    }


    /**
     * 如果为null爆出异常
     */
    public static void isNullThrow(String source, String msg) {
        if (isNull(source)) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 如果为null爆出异常
     */
    public static void isNullThrow(String source, Supplier<? extends RuntimeException> consumer) {
        isNullThrow(source, consumer.get());
    }

    /**
     * 如果为null爆出异常
     */
    public static void isNullThrow(String source, RuntimeException e) {
        if (isNull(source)) {
            throw e;
        }
    }


    /**
     * 如果empty爆出异常
     */
    public static void isEmptyThrow(String source) {
        isNullThrow(source, "");
    }


    /**
     * 如果empty爆出异常
     */
    public static void isEmptyThrow(String source, String msg) {
        if (isEmpty(source)) {
            throw new RuntimeException(msg);
        }
    }


    /**
     * 如果empty爆出异常
     */
    public static <E extends RuntimeException> void isEmptyThrow(String source, Supplier<E> consumer) {
        isNullThrow(source, consumer.get());
    }


    /**
     * 如果empty爆出异常
     */
    public static <E extends RuntimeException> void isEmptyThrow(String source, E e) {
        if (isEmpty(source)) {
            throw e;
        }
    }


    /**
     * 字符拼接
     */
    public static String join(String joinStr, CharSequence... charSequences) {
        return String.join(isNullOr(joinStr, ","), charSequences);
    }


    /**
     * 判断字符串是否相等
     */
    public static boolean equals(String a, String b) {
        if (isNull(a)) {
            return isNull(b);
        } else {
            return a.equals(b);
        }
    }

    /**
     * 判断字符串是否不区分大小写相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        if (isNull(a)) {
            return isNull(b);
        } else {
            return a.equalsIgnoreCase(b);
        }
    }

    /**
     * 截取字符串
     */
    public static String subString(String src, String start, String to) {
        int indexFrom = isNull(start) ? 0 : src.indexOf(start);
        int indexTo = isNull(to) ? src.length() : src.indexOf(to);
        if (indexFrom >= 0 && indexTo >= 0 && indexFrom <= indexTo) {
            if (nonNull(start)) {
                indexFrom += start.length();
            }
            return src.substring(indexFrom, indexTo);
        } else {
            return null;
        }
    }

    /**
     * 通过编辑距离比较两个字符串相似度，百分比保留两位小数
     *
     * @param s 字符串s
     * @param t 字符串t
     * @return 最多保留四位小数
     * @date 2020-8-5
     * @author zhousj
     */
    public static double similarity(String s, String t) {
        int[][] distance = editDistance(s, t);
        int minDis = distance[s.length()][t.length()];
        return BigDecimal.valueOf(1 - (1.0d * minDis / (Math.max(s.length(), t.length()))))
                .setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * 编辑距离问题，由字符串a变为字符串b，最少需要编辑几次
     *
     * @param a 字符串a
     * @param b 字符串b
     * @return int[][] 编辑距离的深度
     * @date 2020-8-5
     * @author zhousj
     */
    public static int[][] editDistance(String a, String b) {
        int aLen = a.length(), bLen = b.length();
        int[][] dp = new int[aLen + 1][bLen + 1];
        for (int i = 0; i <= aLen; i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i <= bLen; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i <= aLen; i++) {
            for (int j = 1; j <= bLen; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j])) + 1;
                }
            }
        }
//      最少编辑次数：dp[aLen][bLen]
        return dp;
    }

    /**
     * 通过编辑距离深度，比较两个字符串，将差异部分标记
     *
     * @param s 字符串s
     * @param t 字符串t
     * @return String[] string[0]为s字符串标记后,string[1]为t字符串标记后
     * @date 2020-8-5
     * @author zhousj
     */
    public static String[] compareWithStr(String s, String t) {
        int[][] distance = editDistance(s, t);
        int i = distance.length - 1, j = distance[0].length - 1;
        int minDistance = distance[i][j];
        char[] sChar = new char[s.length()];
        char[] tChar = new char[t.length()];
        while (i > 0 && j > 0) {
            if (distance[i][j - 1] + 1 == minDistance) {
                //此时为s字符串i位置增加字符，t字符串j位置删除字符
                tChar[j - 1] = t.charAt(j - 1);
                minDistance = distance[i][j - 1];
                j--;
            } else if (distance[i - 1][j] + 1 == minDistance) {
                //此时为t字符串i位置增加字符，s字符串j位置删除字符
                sChar[i - 1] = s.charAt(i - 1);
                minDistance = distance[i - 1][j];
                i--;
            } else if (distance[i - 1][j - 1] + 1 == minDistance) {
                //此时为s字符串和t字符串在i，j位置替换字符
                sChar[i - 1] = s.charAt(i - 1);
                tChar[j - 1] = t.charAt(j - 1);
                minDistance = distance[i - 1][j - 1];
                i--;
                j--;
            } else {
                //此时s字符串t字符串在i，j位置字符相等
                i--;
                j--;
            }
        }
        while (i > 0) {
            sChar[i - 1] = s.charAt(i - 1);
            i--;
        }
        while (j > 0) {
            tChar[j - 1] = t.charAt(j - 1);
            j--;
        }
        return new String[]{conventStr(sChar, s, "[", "]"), conventStr(tChar, t, "{", "}")};
    }


    /**
     * 将字符数组中出现的字符在字符串s中标记
     *
     * @param chars 字符数组，包含字符串s中的某些字符
     * @param s     字符串s
     * @return 标记后的字符
     * @date 2020-8-5
     * @author zhousj
     */
    private static String conventStr(char[] chars, String s, String head, String tail) {
        StringBuilder sb = new StringBuilder((s.length() >> 1) + s.length());
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != 0) {
                if (i > 0 && chars[i - 1] != 0) {
                    logger.trace("[]");
                } else {
                    sb.append(head);
                }
                sb.append(chars[i]);
                if (i < s.length() - 1 && chars[i + 1] == 0) {
                    sb.append(tail);
                }
            } else {
                sb.append(s.charAt(i));
            }
            if (i == s.length() - 1 && chars[i] != 0) {
                sb.append(tail);
            }
        }
        return sb.toString();
    }


    /**
     * 字符串分割转换stream
     *
     * @param source   源字符串
     * @param splitStr 分割字符
     * @return 字符stream
     * @author zhousj
     * @date 2020-8-25
     */
    public static Stream<String> splitToStream(String source, String splitStr) {
        String[] split = source.split(splitStr);
        return Arrays.stream(split);
    }


    /**
     * 字符串分割转换stream
     *
     * @param source   源字符串
     * @param splitStr 分割字符
     * @param trim     是否trim
     * @return 字符stream
     * @author zhousj
     * @date 2020-8-25
     */
    public static Stream<String> splitToStream(String source, String splitStr, boolean trim) {
        Stream<String> stream = splitToStream(source, splitStr);
        if (!trim) {
            return stream;
        }
        return stream.filter(str -> !str.isEmpty() && !str.trim().isEmpty());
    }


    /**
     * 字符串分割转换list
     *
     * @param source   源字符串
     * @param splitStr 分割字符
     * @return string集合
     * @author zhousj
     * @date 2020-8-25
     */
    public static List<String> splitToList(String source, String splitStr) {
        return splitToList(source, splitStr, false);
    }


    /**
     * 字符串分割转换list
     *
     * @param source   源字符串
     * @param splitStr 分割字符
     * @param trim     是否trim
     * @return string集合
     * @author zhousj
     * @date 2020-8-25
     */
    public static List<String> splitToList(String source, String splitStr, boolean trim) {
        return splitToStream(source, splitStr, trim).collect(Collectors.toList());
    }


    /**
     * 字符串是否包含某个字符
     *
     * @param source 源字符串
     * @param search 查找字符串
     * @return true或false
     * @author zhousj
     * @date 2020-8-25
     */
    public static boolean contains(String source, String search) {
        return nonNull(source) && source.contains(search);
    }


    /**
     * 字符串反转
     *
     * @param source 原字符串
     * @return 反转后字符串
     * @author zhousj
     * @date 2020-8-25
     */
    public static String reserve(String source) {
        return new StringBuilder(source).reverse().toString();
    }


    /**
     * 分片表获取数据分区
     * @param settleCycleId 账期，六位，格式yyyyMM
     * @return 01-32分区
     */
    public static String findPartition(String settleCycleId) {
        if (settleCycleId != null && !"".equals(settleCycleId)) {
            if (settleCycleId.length() > 6) {
                settleCycleId = settleCycleId.substring(0, 6);
            }
            try {
                int partition = (Integer.parseInt(settleCycleId) % 32) + 1;
                if (partition < 10) {
                    return "0" + partition;
                } else {
                    return String.valueOf(partition);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return "01";
    }

}
