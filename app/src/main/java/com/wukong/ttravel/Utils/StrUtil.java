package com.wukong.ttravel.Utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shijian on 15/7/13.
 */
public class StrUtil {

    public static boolean isEmpty(String str) {
        if (str == null)
            return true;
        if (str.trim().equals(""))
            return true;
        return false;
    }


    private static DecimalFormat df = new DecimalFormat("#0.00");

    public static String doubleFormat(Double value) {
        return df.format(value);
    }


    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        str = str.trim();
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String toString(List<String> data) {
        StringBuilder builder = new StringBuilder();
        for (String str : data) {
            builder.append(str);
        }
        return builder.toString();
    }

    /**
     * Return a random string with x length. This string composed by alpha and
     * number.
     *
     * @param length
     * @return random string.
     */
    public static String getRandomString(int length) {
        String baseString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer randomStringBuff = new StringBuffer();
        for (int i = 0; i < length; i++) {
            randomStringBuff.append(baseString.charAt(random.nextInt(baseString
                    .length())));
        }
        return randomStringBuff.toString();
    }

    /**
     * 提取字符中的数字，并设置为指定的颜色和大小
     *
     * @param sourceText
     * @param spanFontColor 字体颜色
     * @param spanFontSize  字体尺寸px
     * @return
     */
    public static Spannable getDigitSpanText(String sourceText, int spanFontColor, int spanFontSize) {
        Spannable spanText = new SpannableString(sourceText);
        try {
            Pattern p = Pattern.compile("\\d+(\\.\\d+)?");
            Matcher matcher = p.matcher(sourceText);
            int start = 0;
            int end = 0;
            while (matcher.find()) {
                start = matcher.start();
                end = matcher.end();
                if (start < end) {
                    spanText.setSpan(new ForegroundColorSpan(spanFontColor), start, end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new AbsoluteSizeSpan(spanFontSize),
                            start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spanText;
    }

    /**
     * 提取字符中的数字，并设置为指定的颜色和大小
     *
     * @param sourceText
     * @param spanFontColor 字体颜色
     * @param spanFontSize  字体尺寸px
     * @return
     */
    public static Spannable getDigitSpanText(String sourceText, String destText, int spanFontColor, int spanFontSize) {
        Spannable spanText = new SpannableString(sourceText);
        try {
            int start = 0;
            int end = 0;
            start = sourceText.indexOf(destText);
            end = sourceText.indexOf(destText) + destText.length();
            if (start < end) {
                spanText.setSpan(new ForegroundColorSpan(spanFontColor), start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spanText.setSpan(new AbsoluteSizeSpan(spanFontSize),
                        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spanText;
    }

    /**
     * 单位是千米的float 转换为string
     *
     * @param distance
     * @return
     */
    public static String getDistanceString(Float distance) {
        if (distance == null || distance < 0.0f) {
            return "未知";
        }
        float distanceInMeter = distance * 1000;
        if (distanceInMeter < 100) {
            return "小于100米";
        } else if (distanceInMeter <= 1000) {
            return String.valueOf((int) distanceInMeter) + "米";
        } else {
            BigDecimal b = new BigDecimal(distance);
            double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            return String.valueOf(f1) + "公里";
        }
    }
}
