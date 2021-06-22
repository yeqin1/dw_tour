/*********************************************************************
 *
 * CHINA TELECOM CORPORATION CONFIDENTIAL
 * ______________________________________________________________
 *
 *  [2015] - [2020] China Telecom Corporation Limited, 
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of China Telecom Corporation and its suppliers,
 * if any. The intellectual and technical concepts contained 
 * herein are proprietary to China Telecom Corporation and its 
 * suppliers and may be covered by China and Foreign Patents,
 * patents in process, and are protected by trade secret  or 
 * copyright law. Dissemination of this information or 
 * reproduction of this material is strictly forbidden unless prior 
 * written permission is obtained from China Telecom Corporation.
 **********************************************************************/
package com.shujia.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

	private DateUtils() {
		super();
	}

	/**
	 * 获取给定日期的前一天
	 */
	public static String getYestoday(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date parse = sdf.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse);
		cal.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		Date time = cal.getTime();
		String format = sdf.format(time);
		return format;
	}
	/**
	 * 获取给定日期的前n天
	 */
	public static String getYestoday(String date,int day) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date parse = sdf.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse);
		cal.add(Calendar.DAY_OF_MONTH, day); // 设置为前一天
		Date time = cal.getTime();
		String format = sdf.format(time);
		return format;
	}
	/**
	 * 获取给定日期的前一天
	 */
	public static String getYestodayMin(String dateMin) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date parse = null;
		try {
			parse = sdf.parse(dateMin);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse);
		cal.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		Date time = cal.getTime();
		String format = sdf.format(time);
		return format;
	}

	/**
	 * 获取给定日期的后一天
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getTomorrow(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date parse = sdf.parse(date);
		if(System.currentTimeMillis()< parse.getTime()){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse);
		cal.add(Calendar.DAY_OF_MONTH, 1); // 设置为后一天
		Date time = cal.getTime();
		String format = sdf.format(time);
		return format;
	}

	/**
	 * 获取给定月份的上个月份
	 */
	public static String getLastMonth(String month) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date parse = sdf.parse(month);
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse);
		cal.add(Calendar.MONTH, -1); // 设置为前一天
		Date time = cal.getTime();
		String format = sdf.format(time);
		return format;
	}

	/**
	 * 获取给定月份的下个月份
	 */
	public static String getNextMonth(String month) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date parse = sdf.parse(month);
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse);
		cal.add(Calendar.MONTH, 1); // 设置为前一天
		Date time = cal.getTime();
		String format = sdf.format(time);
		return format;
	}

	/**
	 * 查询日期之间间隔的天数
	 */
	public static int getBetweenDays(String min, String max)
			throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		Date parse1 = f.parse(min);
		Date parse2 = f.parse(max);
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse1);
		long t1 = cal.getTimeInMillis();
		cal.setTime(parse2);
		long t2 = cal.getTimeInMillis();
		int betweenDays = (int) ((t2 - t1) / (1000 * 3600 * 24));
		return betweenDays;
	}
	/**
	 * 查询日期之间间隔的小时
	 */
	public static int getBetweenHours(String min, String max){
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		int betweenHours = 0;
		try{
			Date parse1 = f.parse(min);
			Date parse2 = f.parse(max);
			Calendar cal = Calendar.getInstance();
			cal.setTime(parse1);
			long t1 = cal.getTimeInMillis();
			cal.setTime(parse2);
			long t2 = cal.getTimeInMillis();
			betweenHours = (int) ((Math.abs(t2 - t1)) / (1000 * 3600));
		}catch(Exception e){
			e.printStackTrace();
		}
		return betweenHours;
	}
	/**
	 * 查询日期之间间隔的分钟
	 */
	public static int getBetweenMinute(String min, String max){
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		int betweenHours = 0;
		try{
			Date parse1 = f.parse(min);
			Date parse2 = f.parse(max);
			Calendar cal = Calendar.getInstance();
			cal.setTime(parse1);
			long t1 = cal.getTimeInMillis();
			cal.setTime(parse2);
			long t2 = cal.getTimeInMillis();
			betweenHours = (int) ((Math.abs(t2 - t1)) / (1000 * 60));
		}catch(Exception e){
			e.printStackTrace();
		}
		return betweenHours;
	}
	/**
	 * 查询日期之间间隔的日期
	 */
	public static String getBetweenDay(String group)
			throws ParseException {
		String[] split = group.split("-");
		int betweenDays = getBetweenDays(split[0],split[1]);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date parse = sdf.parse(split[0]);
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse);
		StringBuffer sb=new StringBuffer();
		sb.append("'"+split[0]+"',");
		for (int i = 0; i < betweenDays; i++) {
			cal.add(Calendar.DAY_OF_MONTH, 1); // 设置为前一天
			Date time = cal.getTime();
			String format = sdf.format(time);
			sb.append("'"+format+"',");
		}
		return sb.substring(0, sb.length()-1);
	}

	/**
	 * 查询日期之间间隔的日期
	 */
	public static String getMonthDays(String month)
			throws ParseException {
		String group = getFristAndLastDayOfMonth(month).replace(",","-");
		return  getBetweenDay(group);
	}

	/**
	 * 获取给定月份的前一天和后一天的组合
	 */
	public static String getFristAndLastDayOfMonth(String date) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
			Date parse = df.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(parse);
			int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
			String fristDay = date + "01";
			String lastDay = date + dateOfMonth;
			return fristDay + "," + lastDay;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	// 获取当前日期
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String currentTime = sdf.format(new Date());
		return currentTime;
	}
	// 获取某天time
	public static long getSomeTimeLong(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d.getTime();
	}
	// 获取某天time
	public static long getSomeTimeLong2(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d =null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d.getTime();
	}
	public static String getCurrentTime2(String patt) {
		SimpleDateFormat sdf = new SimpleDateFormat(patt);
		String currentTime = sdf.format(new Date());
		return currentTime;
	}

	// 时间戳转日期
	public static String timeStamp2Date(String time, String patt) {
		SimpleDateFormat sdf = new SimpleDateFormat(patt);
		String date = sdf.format(new Date(Long.valueOf(time)));
		return date;
	}

	/**
	 * 判断是否是一个月的最后一天
	 */
	public static boolean isLastDayOfMonth(String day) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			Date parse = df.parse(day);
			Calendar b = Calendar.getInstance();
			b.setTime(parse);
			int lastDay = b.getActualMaximum(Calendar.DAY_OF_MONTH);
			int now = b.get(Calendar.DAY_OF_MONTH);
			return now == lastDay;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	//判断是否是日期格式
	public static boolean isValidDate(String str) {
		boolean convertSuccess=true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess=false;
		}
		return convertSuccess;
	}

	/**
	 * 根据日期模型获取当前时间
	 */
	public static String getDateByMode(String mode) {
		String date = "";
		switch (mode) {
			case "hour":
				date = getCurrentTime2("yyyy-MM-dd:HH");
				break;
			case "month":
				date = getCurrentTime2("yyyy-MM");
				break;
			case "day":
				date = getCurrentTime2("yyyy-MM-dd");
				break;
			default:
				date = "";
				break;
		}
		return date;
	}
	public static List<String> getLastsMonth(String cycle){
		List<String> list=new ArrayList<>();
		for (int i = 1; i <=Integer.parseInt(cycle) ; i++) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MONTH, -i); // 设置为前一天
			Date time = cal.getTime();
			String format = sdf.format(time);
			list.add(format);
		}
		return list;
	}
	public static List<String> getLastsDay(String cycle){
		List<String> list=new ArrayList<>();
		for (int i = 1; i <=Integer.parseInt(cycle) ; i++) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -i); // 设置为前一天
			Date time = cal.getTime();
			String format = sdf.format(time);
			list.add(format);
		}
		return list;
	}

	/**
	 * 获取给定日期星期几
	 */
	public static int getDayWeek(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date parse = null;
		try {
			parse = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return w;
	}

	/**
	 * 获取给定日期星期几
	 */
	public static int getDayHour(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date parse = null;
		try {
			parse = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse);
		int w = cal.get(Calendar.HOUR_OF_DAY) ;
		return w;
	}

	/**
	 * 获取一个月的每天
	 *
	 * @return
	 */
	public static List<String> getDaysOfMonth(String date) {
		try {
			SimpleDateFormat df=new SimpleDateFormat("yyyyMM");
			Date parse = df.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(parse);
			int dateOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			List<String> list=new ArrayList<String>();
			for(int i=1;i<=dateOfMonth;i++){
				if(i<10){
					list.add(date+"0"+i);
				}else{
					list.add(date+i);
				}
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}



	public static void main(String[] args) throws ParseException {

		System.out.println(getMonthDays("201804"));
	}

}
