package com.zhousj.common.ext.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhousj
 * @date 2021/7/21
 */
@SuppressWarnings("unused")
public class DateUtil {

    private static final String MONTH_FORMAT = "yyyy-MM";

    private static final String MONTH_FORMAT_WITHIN = "yyyyMM";

    private static final String COMMON_DATE_FORMAT = "yyyy-MM-dd";

    private static final String COMMON_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String COMMON_DATE_FORMAT_WITHIN = "yyyyMMdd";

    private static final String COMMON_TIME_FORMAT_WITHIN = "yyyyMMddHHmmss";

    public static String dateToMonth(Date date) {
        return dateFormat(date, MONTH_FORMAT);
    }

    public static String dateToMonthWithin(Date date) {
        return dateFormat(date, MONTH_FORMAT_WITHIN);
    }

    public static String dateFormat(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }







}
