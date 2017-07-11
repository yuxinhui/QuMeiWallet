package com.gdyd.qmwallet.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类,勿在多线程环境使用
 * 
 * @author hjh
 * 
 */
public class TimeUtils {

	/**
	 * 私有化构造对象,不能创建对象
	 */
	private TimeUtils() {
		super();
	}
    //时间格式1 年-月-日 时：分：秒
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	//时间格式1 年-月-日
	private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

	private static final SimpleDateFormat sysDateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	private static final SimpleDateFormat sysTimeFormat = new SimpleDateFormat(
			"HHmmss");

	private static final SimpleDateFormat sysDateFormat8 = new SimpleDateFormat(
			"yyyyMMdd");

	private static final SimpleDateFormat showDateFormatZHCN = new SimpleDateFormat(
			"yyyy年MM月dd日 HH时mm分ss秒");

	public static void main(String[] args) {
		System.out.println(TimeUtils.getShortDate());
		System.out.println(TimeUtils.getYear());
		System.out.println(TimeUtils.getMonth());
		System.out.println(TimeUtils.getWeek());
		System.out.println(TimeUtils.getDay());
		System.out.println(TimeUtils.getYesterdayStartTime()
				+ "this yesterday start");
		System.out.println(TimeUtils.getYesterdayLastTime()
				+ "this yesterday end");
		System.out.println(TimeUtils.getThisWeekOfStartTime()
				+ "this week start");
		System.out.println(TimeUtils.getThisMonthOfStartTime()
				+ "this month start");
		System.out.println(TimeUtils.getBeforeWeekOfEndTime()
				+ "before week end");
		System.out.println(TimeUtils.getBeforeWeekOfStartTime()
				+ "before week start");
		System.out.println(TimeUtils.getBeforeMonthOfEndTime()
				+ "before month end");
		System.out.println(TimeUtils.getBeforeMonthOfStartTime()
				+ "befor month start");
		System.out.println(TimeUtils.getWeek());
	}

	/**
	 * 获得时间（字符串）
	 * 
	 * @return 如:2016-03-05
	 */
	public static String getShortStandDate() {
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
		return sp.format(new Date());
	}

	/**
	 * 获得时间（字符串）
	 * 
	 * @return 如:20160305
	 */
	public static String getShortDate() {
		//获得字符串拼接对象
		StringBuilder sb = new StringBuilder();
		Calendar date = Calendar.getInstance();
		int year = date.get(Calendar.YEAR);
		sb.append(String.valueOf(year));
		int month = date.get(Calendar.MONTH) + 1;
		//如果小于十为 0+月数
		if (month < 10) {
			sb.append("0");
		}
		sb.append(String.valueOf(month));
		int day = date.get(Calendar.DAY_OF_MONTH);
		if (day < 10) {
			sb.append("0");
		}
		sb.append(String.valueOf(day));
		return sb.toString();
	}

	/**
	 * 获得时间（字符串）
	 * 
	 * @return 如:20160305120150241
	 */
	public static String getLongDate() {
		SimpleDateFormat sp = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sp.format(new Date());
	}

	/**
	 * 得到当前时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss 时间格式,如:2016-3-5 15:15:00
	 */
	public static String getTotalDate() {
		return sdf.format(new Date());
	}
	
	public static String getTotalStartDate(){
		return sdf2.format(new Date());
	}

	/**
	 * 获取当前日
	 * 
	 * @return
	 */
	public static int getDay() {
		Calendar date = Calendar.getInstance();
		return date.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 获取当周
	 *
	 * @return
	 */
	public static String getWeek() {
		Calendar date = Calendar.getInstance();
		return new SimpleDateFormat("EEEE").format(date.getTime());
	}

	/**
	 * 获取当前月
	 * 
	 * @return
	 */
	public static int getMonth() {
		Calendar date = Calendar.getInstance();
		return date.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当前年
	 * 
	 * @return
	 */
	public static int getYear() {
		Calendar date = Calendar.getInstance();
		return date.get(Calendar.YEAR);
	}

	/**
	 * 获取当前时间的毫秒值
	 * 
	 * @return
	 */
	public static long getTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取昨天的结束时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss 时间格式,上周一的0点
	 */
	public static String getYesterdayStartTime() {
		// 获取前一天的日期
		Calendar calendar = Calendar.getInstance();
		// 秒-1 即前一天的23:59:59
		calendar.set(Calendar.HOUR_OF_DAY, -24);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime();
		return sdf.format(date);
	}

	/**
	 * 获取昨天的结束时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss 时间格式,昨天的23:59:59
	 */
	public static String getYesterdayLastTime() {
		return sdf.format(TimeUtils.getYesterdayLastTimeOfDate());
	}

	/**
	 * 获取昨天的结束时间,用于判断是否是今天
	 * 
	 * @return 昨天的23:59:59
	 */
	public static Date getYesterdayLastTimeOfDate() {
		// 获取前一天的日期
		Calendar calendar = Calendar.getInstance();
		// 秒-1 即前一天的23:59:59
		calendar.set(Calendar.SECOND, -1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		Date date = calendar.getTime();
		return date;
	}

	/**
	 * 获得本周开始时间
	 * 
	 * @return
	 */
	public static String getThisWeekOfStartTime() {
		Calendar date = Calendar.getInstance();
		date.setFirstDayOfWeek(Calendar.MONDAY);
		date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		return sdf.format(date.getTime());
	}

	/**
	 * 获得上周开始时间
	 * 
	 * @return
	 */
	public static String getBeforeWeekOfStartTime() {
		Calendar date = Calendar.getInstance();
		date.setFirstDayOfWeek(Calendar.MONDAY);
		date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.add(Calendar.WEEK_OF_MONTH, -1);
		return sdf.format(date.getTime());
	}

	/**
	 * 获得上周结束时间
	 * 
	 * @return
	 */
	public static String getBeforeWeekOfEndTime() {
		Calendar date = Calendar.getInstance();
		date.setFirstDayOfWeek(Calendar.MONDAY);
		date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, -1);
		return sdf.format(date.getTime());
	}

	/**
	 * 获得本月开始时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss 时间格式,如:2016-03-01 00:00:00
	 */
	public static String getThisMonthOfStartTime() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, 1);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		return sdf.format(date.getTime());
	}
	/**
	 * 获得本月开始时间
	 *
	 * @return yyyy-MM-dd HH:mm:ss 时间格式,如:2016-03-01 00:00:00
	 */
	public static String getThisMonthOfStartTime2() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, 1);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		return sdf.format(date.getTime());
	}
	/**
	 * 获得上月开始时间
	 * 
	 * @return
	 */
	public static String getBeforeMonthOfStartTime() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, -1);
		date.set(Calendar.DAY_OF_MONTH, 1);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		return sdf.format(date.getTime());
	}

	/**
	 * 获得上月结束时间
	 * 
	 * @return
	 */
	public static String getBeforeMonthOfEndTime() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, 1);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, -1);
		return sdf.format(date.getTime());
	}

	/**
	 * 获得最近7天时间
	 * @return 2016-12-09
     */
	public static String getLatelySevenDay(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, - 7);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		return preMonday;
	}

	/**
	 * 获得最近一月时间
	 * @return 2016-12-09
	 */
	public static String getLatelyOneMonth(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, - 1);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		return preMonday;
	}

	/**
	 * 比较时间
	 * 
	 * @param date
	 *            输入需要判断的时间
	 * @return true 今天以前,false 今天或今天以后
	 */
	public static boolean equalsTime(Date date) {
		if (date == null) {
			return false;
		}
		// 获取前一天的日期
		return TimeUtils.getYesterdayLastTimeOfDate().after(date);
	}

	/**
	 * 转换时间格式
	 * 
	 * @param date
	 *            输入时间
	 * @return yyyy-MM-dd HH:mm:ss 时间格式,如:2016-3-5 15:15:00
	 */
	public static String changeTime(Date date) {
		return sdf.format(date);
	}

	/**
	 * 转换时间格式,今天前的n.m年(相隔多少年)
	 * 
	 * @param date
	 *            输入时间
	 * @return 如果大于0年输出如“1.5年”,否则返回0
	 */
	public static String changeTimeOfApartYear(Date date) {
		float year = (float) ((System.currentTimeMillis() - date.getTime()) / (float) (1000 * 60 * 60 * 24 * (long) 365));
		if (year > 0) {
			String newyear = String.format("%,.1f", year);
			// 判断转换成字符后是否大于0
			if (!newyear.equals("0.0")) {
				return newyear;
			} else {
				return "0";
			}
		} else {
			return "0";
		}
	}
	
	/**
	 * 检查时间非空及有效性,有待优化
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public static String[] checkTime(String startDate,String endDate){
		String[] strs = new String[2];
		boolean isSt = Is.isNoEmpty(startDate);
		boolean isEt = Is.isNoEmpty(endDate);
		if(!isSt && !isEt){
			//都为空
			strs[0] = TimeUtils.getTotalStartDate();
			strs[1] = TimeUtils.getTotalDate();
		}else{
			if(isSt && isEt){
				//有开始时间也有结束时间
				strs[0] = startDate;
				strs[1] = endDate;
			}else if(isSt){
				//只有开始时间
				strs[0] = startDate;
				strs[1] = startDate;
			}else{
				strs[0] = endDate;
				strs[1] = endDate;
			}
		}
		return strs;
	}
}
