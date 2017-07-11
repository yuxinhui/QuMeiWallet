package com.gdyd.qmwallet.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * 转换工具类
 *
 * @version v1.1
 */
public class ReplaceTools {

    /**
     * 私有化构造对象,不能创建对象
     */
    private ReplaceTools() {
        super();
    }

    /**
     * 字符串转义
     *
     * @param value 输入字符串
     * @return 完成转义后的字符串
     */
    public static String changeString(String value) {
        if (value == null || "".equals(value.trim())) {
            return value;
        }
        value = value.replaceAll("&", "&amp");
        value = value.replaceAll(" ", "&nbsp");
        value = value.replaceAll("<", "&lt");
        value = value.replaceAll(">", "&gt");
        value = value.replaceAll("\t", "    ");
        value = value.replaceAll("\r\n", "\n");
        value = value.replaceAll("\n", "<br/>");
        value = value.replaceAll("'", "&#39");
        value = value.replaceAll("\\\\", "&#92");
        value = value.replaceAll("\"", "&quot");
        return value;
    }

    /**
     * 转换数字格式,变成三个数字后加逗号
     *
     * @param number 输入一个数
     * @return String 根据英文读法分排序 例：123,456,789
     */
    public static String changeNumber(Number number) {
        DecimalFormat f = new DecimalFormat(",###");
        return f.format(number);
    }

    /**
     * 转换数字格式,变成带小数的元
     *
     * @param number 输入一个数
     * @return 0.00 或  1111.00
     */
    public static String changeMoney(Number number) {
        DecimalFormat f = new DecimalFormat("#.##");
        return f.format(number);
    }

    /**
     * 转换数字格式,变成带小数的元
     *
     * @param number 输入一个数
     * @return 0.00 或  1,111.00
     */
    public static String formatMoneyThousand(Number number) {
        DecimalFormat f = new DecimalFormat("#,##0.00");
        f.setRoundingMode(RoundingMode.FLOOR);
        return f.format(number);
    }

    /**
     * 转换数字格式,变成带小数的元
     *
     * @param money 输入一个数
     * @return 0.00 或  1111.00
     */
    public static double parseMoneyThousand(String money) throws ParseException {
        if (money.substring(money.length()-1,money.length()).equals(".")){
            money=money.substring(0,money.length()-1);
        }
        return new DecimalFormat().parse(money).doubleValue();
    }

    /**
     * 将金额离转元
     *
     * @param str
     * @return
     */
    public static String transliToYuan(String str) {
        if (str == null || "".equals(str.trim()))
            return "";
        String result = str.trim();
        try {
            BigDecimal bigDecimal = new BigDecimal(str.trim());
            result = bigDecimal.movePointLeft(3).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将金额元转分
     *
     * @param str
     * @return
     */
    public static String transYuanToFen(String str) {
        if (str == null || "".equals(str.trim()))
            return "";
        BigDecimal bigDecimal = new BigDecimal(str.trim());
        return bigDecimal.movePointRight(2).toString();
    }

    /**
     * 将金额分转元
     *
     * @param str
     * @return
     */
    public static String transFenToYuan(String str) {
        if (str == null || "".equals(str.trim()))
            return "";
        String result = str.trim();
        try {
            BigDecimal bigDecimal = new BigDecimal(str.trim());
            result = bigDecimal.movePointLeft(2).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String transMoney(double n) {
        try {
            String[] fraction = {"角", "分"};
            String[] digit = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
            String[][] unit = {{"元", "万", "亿"}, {"", "拾", "佰", "仟"}};

            String head = n < 0 ? "负" : "";
            n = Math.abs(n);

            String s = "";

            for (int i = 0; i < fraction.length; i++) {
                s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i])
                        .replaceAll("(零.)+", "");
            }
            if (s.length() < 1) {
                s = "整";
            }
            int integerPart = (int) Math.floor(n);

            for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
                String p = "";
                for (int j = 0; j < unit[1].length && n > 0; j++) {
                    p = digit[integerPart % 10] + unit[1][j] + p;
                    integerPart = integerPart / 10;
                }
                s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零")
                        + unit[0][i] + s;
            }
            return head + s.replaceAll("(零.)*零元", "元").replaceAll("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转换数字格式,上万以后简写,也可用于转换负数
     *
     * @param number 输入一个数
     * @return String 大于1万,输出(例：1.1万),否则不变
     */
    public static String changeNumber2(Number number) {
        String oldst = number.toString();
        // 判断是否有小数点
        if (oldst.contains(".")) {
            oldst = oldst.substring(0, oldst.indexOf("."));
        }
        int cd = oldst.length();
        if (cd < 5) {
            return number.toString();// 小于1万直接返回
        } else if (oldst.contains("-") && cd < 6) {
            return number.toString();// 小于负1万直接返回
        } else {
            StringBuilder sbd = new StringBuilder("");
            // 拼接字符串
            return sbd.append(oldst.substring(0, cd - 4)).append(".")
                    .append(oldst.substring(cd - 4, cd - 3)).append("万")
                    .toString();
        }
    }

    /**
     * 更新HQL语句的模版方法
     *
     * @param TbName 要更新的表名,如果要更新的表名为空,则直接返回hql
     * @param hql    hql语句,为空时自动创建
     * @param Addhql 输入要更新的字段及对应的值,可以使用"l"代替要更新的表名,如果要添加的条件为空,则直接返回hql
     * @return hql
     */
    public static String updateHql(String TbName, String hql, String Addhql) {
        // 如果hql为空,初始化hql
        if (hql == null) {
            hql = "";
        }
        // 如果要更新的表名为空,则直接返回hql
        if (TbName == null || TbName.isEmpty()) {
            return hql;
        }
        // 如果要添加的条件为空,则直接返回hql
        if (Addhql.isEmpty() || Addhql == null) {
            return hql;
        } else {// 添加条件
            // 如果还未写入hql,则写入hql的更新语句再添加条件
            if (hql.isEmpty()) {
                hql = "update " + TbName + " l set ";
                hql += Addhql;
            } else {// 如果已经存在更新语句,则先添加“,”再添加条件
                hql += "," + Addhql;
            }
        }
        return hql;
    }

    public static void main(String[] args) {
        System.out.println(ReplaceTools.formatMoneyThousand(11651631.132));
        System.out.println(ReplaceTools.changeNumber(123456789));
        System.out.println(ReplaceTools.changeNumber2(-12345));
        // System.out.println(Integer.MAX_VALUE);
        System.out.println(System.currentTimeMillis());
        System.out.println(new Date());
        System.out.println(new Long(1000 * 60 * 60 * 24 * 365));
        long day = 1000 * 60 * 60 * 24 * (long) 365;
        System.out.println(day);
    }
}
