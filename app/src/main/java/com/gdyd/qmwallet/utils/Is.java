package com.gdyd.qmwallet.utils;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 判断非空工具类
 * 
 * @author hjh
 * 
 * @version v1.3 2016-5-23
 * @since 2016-2-12
 */
public class Is {

	/**
	 * 私有化构造对象,不能创建对象
	 */
	private Is() {
		super();
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 判断是否大于0
	 * 
	 * @param num
	 *            整数
	 * @return true为大于0,false为小于0
	 */
	public static boolean isPlusNum(int num) {
		return num > 0 ? true : false;
	}

	/**
	 * 判断是否大于0
	 * 
	 * @param num
	 *            浮点数
	 * @return true为大于0,false为小于0
	 */
	public static boolean isPlusNum(float num) {
		return num > 0 ? true : false;
	}

	/**
	 * 判断是否大于0
	 * 
	 * @param num
	 *            双精度浮点数
	 * @return true为大于0,false为小于0
	 */
	public static boolean isPlusNum(double num) {
		return num > 0 ? true : false;
	}

	/**
	 * 判断是否大于0
	 * 
	 * @param num
	 *            长整数
	 * @return true为大于0,false为小于0
	 */
	public static boolean isPlusNum(long num) {
		return num > 0 ? true : false;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return 如果str不为空返回true
	 */
	public static boolean isNoEmpty(String str) {
		boolean flag = false;
		if (str != null && str.trim().length() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return 如果str不为空返回true
	 */
	public static boolean isNoEmpty(String... str) {
		boolean flag = true;
		if (str != null && str.length > 0) {
			for (String s : str) {
				if (null == s || s.trim().length() == 0) {
					flag = false;
					break;
				}
			}
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param array
	 *            数组
	 * @return 如果array不为空返回true
	 */
	public static boolean isNoEmpty(int[] array) {
		boolean flag = false;
		if (array != null && array.length > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param list
	 *            list集合
	 * @return 如果list不为空返回true
	 */
	public static <T> boolean isNoEmpty(List<T> list) {
		boolean flag = false;
		if (list != null && list.size() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param map
	 *            map集合
	 * @return 如果map不为空返回true
	 */
	public static boolean isNoEmpty(Map<Object, Object> map) {
		boolean flag = false;
		if (map != null && map.size() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param objs
	 *            数据集
	 * @return 如果都不为空且有值且不为负数返回true
	 */
	public static boolean isNoEmptyAll(Object... objs) {
		boolean flag = true;
		for (Object obj : objs) {
			// 任意一项都不能为空
			if (obj == null) {
				flag = false;
				break;
			}
			// 判断不同数据是否符合规则
			if (obj instanceof Number) {
				// 数字
				if (((Number) obj).doubleValue() <= 0) {
					flag = false;
					break;
				}
			} else if (obj instanceof String) {
				// 字符
				if (((String) obj).trim().length() <= 0) {
					flag = false;
					break;
				}
			} else if (obj instanceof Collection) {
				// 集合
				if (((Collection<?>) obj).size() <= 0) {
					flag = false;
					break;
				}
			} else if (obj.getClass().isArray()) {
				// 数组
				if (Array.getLength(obj) <= 0) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 为空或为零时返回默认值
	 * 
	 * @param t
	 *            参数
	 * @param def
	 *            默认值
	 * @return t为空时返回def
	 */
	public static <T> T ifNull(T t, T def) {
		if (null == t) {
			return def;
		} else if (t instanceof Number && ((Number) t).intValue() == 0) {
			return def;
		} else {
			return t;
		}
	}

	/**
	 * 判断是否为数字,应使用正则表达式判断比较好(RegexUtils.isNumber(String str))
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNumber(Object obj) {
		boolean flag = false;
		try {
			if (obj instanceof String) {
				Integer.parseInt((String) obj);
			} else {

			}
		} catch (Exception e) {
			// 不处理
		}
		return flag;
	}

	/**
	 * 判断日期格式是否符合格式标准
	 * 
	 * @param s
	 * @param pattarm
	 * @return
	 */
	public static boolean isValidDate(String s) {
		boolean flag = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setLenient(false);
			sdf.parse(s);
			flag = true;
		} catch (Exception e) {
			// 不处理
		}
		return flag;
	}

	/**
	 * 判断日期格式是否符合格式标准
	 * 
	 * @param s
	 * @param pattarm
	 * @return
	 */
	public static boolean isValidDate(String s, String pattarm) {
		boolean flag = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattarm);
			sdf.setLenient(false);
			sdf.parse(s);
			flag = true;
		} catch (Exception e) {
			// 不处理
		}
		return flag;
	}
	
	public static void main(String[] args) {
		System.out.println(Is.isNoEmptyAll("asdf","asd  s"));
	}
}
