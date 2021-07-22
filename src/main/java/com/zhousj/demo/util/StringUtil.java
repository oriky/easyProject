package com.zhousj.demo.util;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
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

    /**
     * 默认偏移量
     */
    private final static String COMMON_IV = "0A0B0C0D0E0F0G0H";

    /**
     * 默认秘钥
     */
    private final static String COMMON_KEY = "SEKIRO_ORI_NIOH_";


    /**
     * 用于补全不足16位Key
     */
    private final static String COMPLETION_KEY = "0000000000000000";

    /**
     * 用于补全不足16位IV
     */
    private final static String COMPLETION_IV = "1111111111111111";


    /**
     * 加密模式
     */
    private final static String MODE = "RSA";

    /**
     * 秘钥和秘钥偏移量长度
     */
    private final static int COMMON_LENGTH = 16;


    /**
     * 公钥key
     */
    private static final String PUBLIC_KEY = "public";


    /**
     * 私钥key
     */
    private static final String PRIVATE_KEY = "private";

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


    public static void isNullThrow(String source) {
        isNullThrow(source,"");
    }


    public static void isNullThrow(String source,String msg) {
        if (isNull(source)) {
            throw new RuntimeException(msg);
        }
    }

    public static void isNullThrow(String source, Supplier<? extends RuntimeException> consumer) {
        isNullThrow(source,consumer.get());
    }

    public static void isNullThrow(String source, RuntimeException e) {
        if (isNull(source)) {
            throw e;
        }
    }


    public static void isEmptyThrow(String source) {
        isNullThrow(source,"");
    }


    public static void isEmptyThrow(String source,String msg) {
        if (isEmpty(source)) {
            throw new RuntimeException(msg);
        }
    }

    public static void isEmptyThrow(String source, Supplier<RuntimeException> consumer) {
        isNullThrow(source,consumer.get());
    }

    public static void isEmptyThrow(String source, RuntimeException e) {
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
     * 通过编辑距离比较两个字符串相似度
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
    private static int[][] editDistance(String a, String b) {
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
     * 字符串AES.CBC模式加密
     *
     * @param sSrc 加密字符
     * @return 加密后的字符
     * @author zhousj
     * @date 2020-8-25
     */
    public static String encrypt(String sSrc) {
        return encrypt(sSrc, COMMON_KEY, COMMON_IV);
    }


    /**
     * 字符串AES.CBC模式加密
     *
     * @param sSrc        加密字符
     * @param key         秘钥
     * @param ivParameter 秘钥偏移量
     * @return 加密后的字符
     * @author zhousj
     * @date 2020-8-25
     */
    public static String encrypt(String sSrc, String key, String ivParameter) {
        return encrypt(sSrc, "utf-8", key, ivParameter);
    }


    /**
     * 字符串AES.CBC模式加密
     *
     * @param sSrc           加密字符
     * @param encodingFormat 编码格式
     * @param key            秘钥
     * @param ivParameter    秘钥偏移量
     * @return 加密后的字符
     * @author zhousj
     * @date 2020-8-25
     */
    public static String encrypt(String sSrc, String encodingFormat, String key, String ivParameter) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            key = completionKey(key);
            ivParameter = completionIv(ivParameter);
            byte[] raw = key.getBytes(encodingFormat);
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));
            return parseByte2HexStr(encrypted);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 字符串AES.CBC模式解密
     *
     * @param sSrc 加密字符
     * @return 解密后的源字符串
     * @author zhousj
     * @date 2020-8-25
     */
    public static String decrypt(String sSrc) {
        return decrypt(sSrc, COMMON_KEY, COMMON_IV);
    }


    /**
     * 字符串AES.CBC模式解密
     *
     * @param sSrc        加密字符
     * @param key         秘钥
     * @param ivParameter 秘钥偏移量
     * @return 解密后的源字符串
     * @author zhousj
     * @date 2020-8-25
     */
    public static String decrypt(String sSrc, String key, String ivParameter) {
        return decrypt(sSrc, "utf-8", key, ivParameter);
    }


    /**
     * 字符串AES.CBC模式解密
     *
     * @param sSrc           加密字符
     * @param encodingFormat 编码格式
     * @param key            秘钥
     * @param ivParameter    秘钥偏移量
     * @return 解密后的源字符串
     * @author zhousj
     * @date 2020-8-25
     */
    public static String decrypt(String sSrc, String encodingFormat, String key, String ivParameter) {
        try {
            key = completionKey(key);
            byte[] raw = key.getBytes(encodingFormat);
            ivParameter = completionIv(ivParameter);
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            byte[] encrypted1 = parseHexStr2Byte(sSrc);
            if (encrypted1 != null) {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original, encodingFormat);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }


    /**
     * 密钥长度不足16位用0补充至16位，超过16位截取16位
     *
     * @param key 秘钥
     * @return 处理后的秘钥
     * @author zhousj
     * @date 2020-8-25
     */
    private static String completionKey(String key) {
        key = isEmptyOr(key, COMMON_KEY);
        if (key.length() < COMMON_LENGTH) {
            int i = COMMON_LENGTH - key.length();
            key = key + COMPLETION_KEY.substring(0, i);
        } else {
            key = key.substring(0, COMMON_LENGTH);
        }
        return key;
    }


    /**
     * 密钥偏移量长度不足16位用1补充至16位，超过16位截取16位
     *
     * @param iV 秘钥偏移量
     * @return 处理后的秘钥偏移量
     * @author zhousj
     * @date 2020-8-25
     */
    private static String completionIv(String iV) {
        iV = isEmptyOr(iV, COMMON_IV);
        if (iV.length() < COMMON_LENGTH) {
            int i = COMMON_LENGTH - iV.length();
            iV = iV + COMPLETION_IV.substring(0, i);
        } else {
            iV = iV.substring(0, COMMON_LENGTH);
        }
        return iV;
    }


    /**
     * 将byte数组转换字符串
     *
     * @param buf byte数组
     * @return 转换后的字符串
     * @author zhousj
     * @date 2020-8-25
     */
    private static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }


    /**
     * 将字符串转换为byte数组
     *
     * @param hexStr byte数组
     * @return 转换后的数组
     * @author zhousj
     * @date 2020-8-25
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() >> 1];
            for (int i = 0; i < (hexStr.length() >> 1); i++) {
                int high = Integer.parseInt(hexStr.substring(i << 1, (i << 1) + 1), COMMON_LENGTH);
                int low = Integer.parseInt(hexStr.substring((i << 1) + 1, (i << 1) + 2), COMMON_LENGTH);
                result[i] = (byte) (high * COMMON_LENGTH + low);
            }
            return result;
        }
    }


    /**
     * 字符串base64编码
     *
     * @param source 源字符串
     * @return 编码后字符串
     * @author zhousj
     * @date 2020-8-25
     */
    public static String enCodeBase64(String source) {
        return enCodeBase64(source, StandardCharsets.UTF_8);
    }


    public static String enCodeBase64(byte[] source) {
        byte[] bytes = Base64.getEncoder().encode(source);
        return new String(bytes);
    }


    /**
     * 字符串base64编码
     *
     * @param source  源字符串
     * @param charset 编码格式
     * @return 编码后字符串
     * @author zhousj
     * @date 2020-8-25
     */
    public static String enCodeBase64(String source, Charset charset) {
        byte[] bytes = Base64.getEncoder().encode(source.getBytes(charset));
        return new String(bytes, charset);
    }


    /**
     * 字符串base64解码
     *
     * @param target 编码后字符串
     * @return 解码后字符串
     * @author zhousj
     * @date 2020-8-25
     */
    public static String deCodeBase64(String target) {
        return deCodeBase64(target, StandardCharsets.UTF_8);
    }


    public static byte[] deCodeBase64(byte[] target) {
        return Base64.getDecoder().decode(target);
    }


    /**
     * 字符串base64解码
     *
     * @param target  编码后字符串
     * @param charset 编码格式
     * @return 解码后字符串
     * @author zhousj
     * @date 2020-8-25
     */
    public static String deCodeBase64(String target, Charset charset) {
        byte[] bytes = Base64.getDecoder().decode(target.getBytes(charset));
        return new String(bytes, charset);
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
     * 生成Rsa加密公钥和私钥
     *
     * @return map, private:私钥，public:公钥
     * @author zhousj
     * @date 2020-8-27
     */
    public static Map<String, String> generateKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(MODE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            HashMap<String, String> map = Maps.newHashMap();
            map.put(PUBLIC_KEY, enCodeBase64(publicKey.getEncoded()));
            map.put(PRIVATE_KEY, enCodeBase64(privateKey.getEncoded()));
            return map;
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * Rsa私钥加密
     *
     * @param source     加密原文
     * @param privateKey 私钥
     * @return 加密后的密文
     * @author zhousj
     * @date 2020-8-27
     */
    public static String encodeRsa(String source, String privateKey) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(deCodeBase64(privateKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance(MODE);
            PrivateKey privateK = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(MODE);
            cipher.init(Cipher.ENCRYPT_MODE, privateK);
            byte[] doFinal = cipher.doFinal(source.getBytes());
            return enCodeBase64(doFinal);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * Rsa公钥解密
     *
     * @param source    密文
     * @param publicKey 公钥
     * @return 解密后的原文
     * @author zhousj
     * @date 2020-8-27
     */
    public static String decodeRsa(String source, String publicKey) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(deCodeBase64(publicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance(MODE);
            PublicKey publicK = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(MODE);
            cipher.init(Cipher.DECRYPT_MODE, publicK);
            byte[] result = cipher.doFinal(deCodeBase64(source.getBytes()));
            return new String(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
