package com.zhirui.lmwy.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * 数学支持类
 * @author longxn
 *
 */
public class MathUtils {
	public final static int DEF_SCALE = 8;

	/**
     * 提供数据类型转换为BigDecimal
     *
     * @param object BigDecimal和基本数据类型
     *            原始数据
     * @return BigDecimal
     */
    public static BigDecimal toBigDecimal(Object object, int... mc) {
        if (object == null) {
            throw new NullPointerException();
        }
        BigDecimal result;
        try {
            result = new BigDecimal(String.valueOf(object).replaceAll(",", ""));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please give me a numeral.Not " + object);
        }
        return result;
    }

	public static void main(String[] args) {
//		BigDecimal powerConsumption = BigDecimal.valueOf(2,2);
//		System.out.println(powerConsumption);//获取百分数20->0.2, 2->0.02
//		String format = format(323333355.3345,"#,###.000");//323,333,355.334
//		String format = format(323553345,"");//323,553,345.000
		System.out.println(formatAmountString("88888"));//￥88,888.00
	}

	/**
	 * BigDecimal的四舍五入
	 *
	 * @param decimal   需要转换的数据
	 * @param holdCount 需要保留的位数
	 */
	public static BigDecimal BigDeimalSetScale(BigDecimal decimal, int holdCount) {
		BigDecimal setScale = decimal.setScale(holdCount, BigDecimal.ROUND_HALF_UP);
		return setScale;
	}

	/**
	 * double类型转BigDecimal
	 *
	 * @param d
	 *            需要转换的double类型数字
	 * @param holdCount
	 *            保留的小数位数
	 * @return
	 */
	public static BigDecimal Double2BigDecimal(double d, int holdCount) {
		NumberFormat ddf1 = NumberFormat.getNumberInstance();
		ddf1.setMaximumFractionDigits(holdCount);
		String s = ddf1.format(d);
		s = s.replace(",", "");// s有时候会为111,2423.00这种格式, 需要替换掉逗号
		return BigDecimal.valueOf(Double.valueOf(s));

	}

	/**
	 * BigDecimal的四舍五入
	 *
	 *            需要转换的数据
	 * @param holdCount
	 *            需要保留的位数
	 */
	public static BigDecimal rounding(BigDecimal decimal, int holdCount) {
		BigDecimal setScale = decimal.setScale(holdCount, BigDecimal.ROUND_HALF_UP);
		return setScale;
	}


	public static BigDecimal rounding(Double decimal, int holdCount) {
		BigDecimal setScale = BigDecimal.valueOf(decimal).setScale(holdCount, BigDecimal.ROUND_HALF_UP);
		return setScale;
	}

	 /**
     * 获取start到end区间的随机数,包含start不包含end
     *
     * @param start
     * @param end
     * @return
     */
	public static long random(int start, int end) {
		Random r = new Random();
		return r.nextInt(end - start) + start;
	}

	/**
     * 格式化
     * String format = format(323333355.3345,"#,###.000");//323,333,355.334
	   String format = format(323553345,"");//323,553,345.000
     * @param obj
     * @param pattern
     * @return
     */
    public static String format(double obj, String pattern) {
        if (pattern == null || "".equals(pattern)) {
            pattern = "#,###.000";  //323,333,355.334
        }
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(toBigDecimal(obj));
    }

    /**
     * 格式化金额
     * 如 8888888.88--------->￥8,888,888.88
     * @return
     */
    public static String formatAmountString(String amount) {
        if (amount != null) {
            Double strDouble = Double.parseDouble(amount.replaceAll(",", "").trim());
            NumberFormat nFormat = NumberFormat.getCurrencyInstance();
            amount = nFormat.format(strDouble);
        }
        return amount;
    }

    /**
     * 格式化汇率
     * @param exrate
     * 如：'0.2----->0.200000'
     * @return
     */
    public static String formatExRate(String exrate) {
        if (exrate != null) {
            Double strDouble = Double.parseDouble(exrate.replaceAll(",", "").trim());
            DecimalFormat dFormat = new DecimalFormat("#,##0.000000");
            exrate = dFormat.format(strDouble);
        }
        return exrate;
    }

	/**
     * 返回科学计算后的加法结果
     * @param val1
     * @param val2
     * @param mc    精度
     * @param more  更多
     * @return
     */
	public static double add(double val1, double val2, int mc, double... more) {
		BigDecimal bg1 = BigDecimal.valueOf(val1);
		BigDecimal bg2 = BigDecimal.valueOf(val2);
		BigDecimal result = BigDecimal.ZERO;
		result = bg1.add(bg2);
		if (more.length > 0) {
			for (int i = 0, len = more.length; i < len; i++) {
				result = result.add(BigDecimal.valueOf(more[i]));
			}
		}
		return result.setScale(mc, RoundingMode.HALF_UP).doubleValue();
	}

    /**
     * 返回科学计算后的减法结果
     * @param val1
     * @param val2
     * @param mc    精度
     * @param more  更多
     * @return
     */
	public static double subtract(double val1, double val2, int mc, double... more) {
		if (val2 == 0)
			return val1;
		BigDecimal bg1 = BigDecimal.valueOf(val1);
		BigDecimal bg2 = BigDecimal.valueOf(val2);
		BigDecimal result = BigDecimal.ZERO;
		result = bg1.subtract(bg2);
		//错误代码, MathContext, 如果mc=2,减得值为0.0111,得到的结果为0.011,而不是0.01
//		result = bg1.subtract(bg2, new MathContext(mc,RoundingMode.HALF_UP));
		if (more.length > 0) {
			for (int i = 0, len = more.length; i < len; i++) {
				result = result.subtract(BigDecimal.valueOf(more[i]));
			}
		}
		return result.setScale(mc, RoundingMode.HALF_UP).doubleValue();
	}

	/**
     * 返回科学计算后的乘法结果
     * @param val1
     * @param val2
     * @param mc    精度
     * @param more  更多
     * @return
     */
	public static double multiply(double val1, double val2, int mc, double... more) {
		if (val1 == 0 || val2 == 0)
			return 0;
		BigDecimal bg1 = BigDecimal.valueOf(val1);
		BigDecimal bg2 = BigDecimal.valueOf(val2);
		BigDecimal result =  BigDecimal.ZERO;
		result = bg1.multiply(bg2);
		if (more.length > 0) {
			for (int i = 0, len = more.length; i < len; i++) {
				result = result.multiply(BigDecimal.valueOf(more[i]));
			}
		}
		return result.setScale(mc, RoundingMode.HALF_UP).doubleValue();
	}

	/**
     * 返回科学计算后的除法结果
     * @param mc    精度
     * @param more  更多
     * @return
     */
	public static double divide(double val1, double val2, int mc, double... more) {
		if (val1 == 0 || val2 == 0)
			return 0;
		BigDecimal bg1 = BigDecimal.valueOf(val1);
		BigDecimal bg2 = BigDecimal.valueOf(val2);
		BigDecimal result =  BigDecimal.ZERO;
		if (more.length > 0) {
			for (int i = 0, len = more.length; i < len; i++) {
				result = result.divide(BigDecimal.valueOf(more[i]));
			}
		}
		result = bg1.divide(bg2,mc,BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}

	  /**
     * 功能：将字符串抓换为double，如果失败返回默认值。
     *
     * @param str
     *            字符串
     * @param defaultValue
     *            失败时返回的默认值
     * @return double
     */
    public static double toDouble(String str, double defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 功能：将字符串抓换为float，如果失败返回默认值。
     *
     * @param str
     *            字符串
     * @param defaultValue
     *            失败时返回的默认值
     * @return float
     */
    public static float toFloat(String str, float defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 功能：将字符串抓换为long，如果失败返回默认值。
     *
     * @param str
     *            字符串
     * @param defaultValue
     *            失败时返回的默认值
     * @return long
     */
    public static long toLong(String str, long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 功能：将字符串抓换为int，如果失败返回默认值。
     *
     * @param str
     *            字符串
     * @param defaultValue
     *            失败时返回的默认值
     * @return int
     */
    public static int toInt(String str, int defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}
}


