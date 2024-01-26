package git.tmou.t_common.utils;


import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

import static java.text.NumberFormat.getPercentInstance;

public class NumUtils {

    public static Double formatPercent(Double dou, int digitsAfterDecimalPoint) {
        double roundNum = Math.pow(10, digitsAfterDecimalPoint);
        return dou != null ? ((1d * Math.round(dou * 100 * roundNum))) / roundNum : null;
    }

    public static Double formatPercent(Double dou) {
        return dou != null ? ((1d * Math.round(dou * 10000))) / 100 : null;
    }

    public static Double formatPercent(String str) throws NullPointerException, NumberFormatException {
        try {
            if (str == null) {
                throw new NullPointerException("cannot formatPercent from null");
            }
            Double dou = Double.valueOf(str);
            return formatPercent(dou);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NumberFormatException("cannot formatPercent from " + str);
        }
    }

    public static Double round(Double dou, int digitsAfterDecimalPoint) {
        double roundNum = Math.pow(10, digitsAfterDecimalPoint);
        return dou != null ? ((1d * Math.round(dou * roundNum))) / roundNum : null;
    }

    public static Double round(String str, int digitsAfterDecimalPoint) throws NullPointerException, NumberFormatException {
        try {
            if (str == null) {
                throw new NullPointerException("cannot formatPercent from null");
            }
            Double dou = Double.valueOf(str);
            return round(dou, digitsAfterDecimalPoint);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NumberFormatException("cannot formatPercent from " + str);
        }
    }

    /**
     * 当Integer对象为null值时设置为0
     *
     * @param number Integer对象
     * @return
     */
    public static int convertInteger(Integer number) {
        return number == null ? 0 : number;
    }

    public static Long convertLong(Long number) {
        return Objects.isNull(number) ? 0L : number;
    }

    /**
     * double转百分数
     *
     * @param number Double对象
     * @param digits 小数点后保留位数
     * @return
     */
    public static String double2Percent(Double number, int digits) {
        NumberFormat nf = getPercentInstance();
        nf.setMinimumFractionDigits(digits);
        if (number == null) {
            return nf.format(0);
        }
        return nf.format(NumUtils.round(number, digits + 2));
    }

    /**
     * 计算百分比 Long
     *
     * @param a 分子
     * @param b 分母
     * @return java.lang.String
     * @title calPercent
     * @author JiayiYang
     * @updateTime 2022/12/6 21:16
     */
    public static String calPercent(Long a, Long b) {
        if (a == null || b == null || a == 0 || b == 0) {
            return null;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String str = df.format(((a.doubleValue() / b.doubleValue()) * 100));
        return "" + str + "%";
    }

    public static String calPercent(Double a, Double b) {
        if (a == null || b == null || b == 0) {
            return null;
        }
        double ret = a * 100 / b;
        DecimalFormat df = new DecimalFormat("0.00");
        String str = df.format(ret);
        return "" + str + "%";
    }

    /**
     * @param num1 分子
     * @param num2 分母
     * @return java.lang.String
     * @title calNum
     * @description Integer 计算相除 保留小数点后两位
     * @author y
     * @updateTime 2021/3/17 12:23
     */
    public static String calNum(Integer num1, Integer num2) {
        if (num1 == null || num2 == null || num1 == 0L || num2 == 0L) {
            return null;
        }
        double ret = (double) num1 / num2;
        return String.format("%.2f", ret);
    }

    /**
     * @param num1 分子
     * @param num2 分母
     * @return java.lang.String
     * @title calNum
     * @description Long 计算相除 保留小数点后两位
     * @author y
     * @updateTime 2021/3/17 12:24
     */
    public static String calNum(Long num1, Long num2) {
        if (num1 == null || num2 == null || num1 == 0L || num2 == 0L) {
            return null;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format((num1.doubleValue() / num2.doubleValue()));
    }

    public static String scientificNotationRound(Double dou, int digitsAfterDecimalPoint) {
        BigDecimal bigDecimal = BigDecimal.valueOf(dou);
        return bigDecimal.setScale(digitsAfterDecimalPoint, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    public static String double2Pct(Double number, int digits) {
        NumberFormat numberFormat = getPercentInstance();
        numberFormat.setMinimumFractionDigits(digits);
        if (number == null) {
            return null;
        }
        return numberFormat.format(NumUtils.round(number, digits + 2));
    }

    public static Double calDivide(Double num1, Long num2, int digitsAfterDecimalPoint) {
        if (num1 == null || num2 == null || num2 == 0L) {
            return null;
        }
        double ret = num1 / num2.doubleValue();
        double roundNum = Math.pow(10, digitsAfterDecimalPoint);
        return 1d * Math.round(ret * roundNum) / roundNum;
    }

    public static Double calDivide(Double num1, Double num2, int digitsAfterDecimalPoint) {
        if (num1 == null || num2 == null || num2 == 0) {
            return null;
        }
        double ret = num1 / num2.doubleValue();
        double roundNum = Math.pow(10, digitsAfterDecimalPoint);
        return 1d * Math.round(ret * roundNum) / roundNum;
    }

    public static String scientificNotation2String(Double num, int digitsAfterDecimalPoint) {
        if (Objects.isNull(num)) {
            return null;
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setRoundingMode(RoundingMode.HALF_UP);
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(digitsAfterDecimalPoint);
        return nf.format(num);
    }

    public static Long scientificNotationRoundLong(Double dou, int digitsAfterDecimalPoint) {
        if (Objects.isNull(dou)) {
            return null;
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(dou);
        return bigDecimal.setScale(digitsAfterDecimalPoint, RoundingMode.HALF_UP).longValue();
    }

    public static Double calDivide(Long num1, Long num2, int digitsAfterDecimalPoint) {
        if (num1 == null || num2 == null || num2 == 0L) {
            return null;
        }
        return NumberUtil.div(num1, num2, digitsAfterDecimalPoint).doubleValue();
    }

    public static Double calDivide(Long num1, Double num2, int digitsAfterDecimalPoint) {
        if (num1 == null || num2 == null || num2 == 0L) {
            return null;
        }
        return NumberUtil.div(num1, num2, digitsAfterDecimalPoint).doubleValue();
    }

    public static String price2Str(Long price) {
        Double ret = NumUtils.calDivide(price, 100L, 2);
        if (ret == null) {
            return null;
        }
        return String.valueOf(Math.round(ret));
    }

    public static void main(String[] args) {
        System.out.println(double2Percent(3.32631, 2));
    }
}
