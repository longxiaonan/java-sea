package com.iee.date;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils{

	private static SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String dirpath = "D:/";

	private final static int MIN = 60 * 1000;
	/**
	 * HH:mm:ss
	 */
	public static final String HHmmss = "HH:mm:ss";
	/**
	 * yyyy-MM
	 */
	public static final String yyyy_MM = "yyyy-MM";
	/**
	 * yy-MM-dd
	 */
	public static final String yy_MM_dd = "yy-MM-dd";
	/**
	 * yyyy-MM-dd
	 */
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	/**
	 * yy-MM-dd HH:mm:ss
	 */
	public static final String yy_MM_ddHHmmss = "yy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String yyyy_MM_ddHHmmss = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd HH:mm:ss:SSS
	 */
	public  static final String yyyy_MM_ddHHmmssSSS = "yyyy-MM-dd HH:mm:ss:SSS";
	/**
	 * HHmmssSSS
	 */
	public static final String HHmmssSSS = "HHmmssSSS";
	/**
	 * yyyyMMdd
	 */
	public static final String yyMMdd = "yyyyMMdd";
	/**
	 * yyyyMMddHHmmssSSS
	 */
	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	/**
	 * yyyyMMddHHmmss
	 */
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	private DateUtils() {
	}

	/**
	 * 获取当前时间往前退或往后一段时间后的日期, 如,获取两天后的日期:getIntervalTime("yyyy-MM-dd", 2, IntervalModel.day);
	 * @param style  默认yyyy-MM-dd
	 * @param value 具体的数值
	 * @param diff  传入的值只能是正负24之内的，包括正负24
	 * @return
	 */
	public static String getDistanceTime(String style, int value, IntervalModel model) {
		SimpleDateFormat sdf = null;
		if (style == null || "".equals(style)) {
			style = "yyyy-MM-dd";
		}
		try {
			sdf = new SimpleDateFormat(style);
		} catch (Exception ex) {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		String time = sdf.format(new Date(System.currentTimeMillis() + value * getTimeInterval(model)));
		return time;
	}

	/**
	 * 返回日期字符串对应的日期, 调用方式:parseToDate("2017-9-24 00:00:24", DateUtils.yyyy_MM_ddHHmmss);
	 * @param dateString 日期类型字符串,格式要大于2017-9-24，不能是年月
	 * @param format 日期格式
	 * @return 日期字符串对应的日期
	 */
	public static Date parseToDate(String dateString, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(format);
		Date date = null;
		if (dateString == null || dateString.length() < 8)
			return null;
		try {
			date = simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 返回日期字符串对应format格式的日期字符串
	 * @param dateString 日期字符串类型
	 * @param format 日期格式
	 * @return
	 */
	public static String parseToString(String dateString, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(format);
		Date date = null;
		String str = null;
		if (dateString == null || dateString.length() < 8)
			return null;
		try {
			date = simpleDateFormat.parse(dateString);
			str = simpleDateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 返回输入格式格式format的Date类型
	 * @param date 日期类型
	 * @param format 日期格式
	 * @return
	 */
	public static String format(Date date, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(format);
		String str = null;
		if (date == null){
			return null;
		}
		str = simpleDateFormat.format(date);
		return str;
	}

	/**
	 * 以yyyy-MM-dd HH:mm:ss进行格式化
	 */
	public static String formatYYYYMMDDHHMMSS(Date date) {
		return YYYYMMDDHHMMSS.format(date);
	}
	public static String formatYYYYMMDDHHMMSS(long time) {
		return YYYYMMDDHHMMSS.format(new Date(time));
	}
	/**
	 * 以yyyy-MM-dd进行格式化
	 */
	public static String formatYYYYMMDD(Date date) {
		return YYYYMMDD.format(date);
	}

	/**
	 * 传入的date按照format格式化
	 * @param date  如：2017-11-24
	 * @param format  如：DateUtils.yyyy_MM_dd
	 * @return   返回Date
	 */
	public static Date parse(String date, String format){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("字符串转Date出错(StirngToDate())");
		}
	}
	public static Date parseYYYYMMDDHHMMSS(String s) {
		try {
			return YYYYMMDDHHMMSS.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public static Date parseYYYYMMDD(String s) {
		try {
			return YYYYMMDD.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * java.util.Date转成java.sql.Timestamp
	 * @param d
	 * @return
	 */
	public static java.sql.Timestamp parseToTimestamp(Date d) {
		if (d == null) {
			throw new IllegalArgumentException("传递的日期不能为空");
		}
		return new java.sql.Timestamp(d.getTime());
	}

	/**
	 * 按照传入的时间往前退多少毫秒
	 * @param timeformat  格式化字符串，如：DateUtils.yyyy_MM_ddHHmmss
	 * @param init    传入的字符串时间，如：2017-11-12 10:10:10
	 * @param modify   需要往前进多少毫秒，如：10000（10秒）
	 * @return
	 */
	public static String getTimeInterval(String timeformat, String init,long modify) {
		SimpleDateFormat dateformat = new SimpleDateFormat(timeformat);
		long dateinit = 0L;
		try {
			dateinit = dateformat.parse(init).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("timeInterval()出错");
		}
		String time2 = dateformat.format(new Date(dateinit - modify));
		// System.out.println("init-time:"+dateformat.format(new
		// Date(dateinit))+" modify:"+time2);

		return time2;
	}

	/**
	 * 获取传入参数距离当前时间的天数
	 * @param date
	 * @return
	 */
	public static long getPastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取传入参数距离当前的小时数
	 * @param date
	 * @return
	 */
	public static long getPastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}

	/**
	 * 获取传入参数距离当前时间的分钟数
	 * @param date
	 * @return
	 */
	public static long getPastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}

	static enum IntervalModel{
		min,hour,day
	}

	/**
	 * 获取时间 间隔
	 * @param level
	 *            时间粒度 0-分钟 1-5分钟 2-10分钟 3-15分钟 4-30分钟 5-小时 6-天 7-2分钟
	 * @return
	 */
	private static int getTimeInterval(IntervalModel model) {
		int time_interval = 0;

		switch (model) {
		case min:
			time_interval = 1 * MIN;
			break;
		case hour:
			time_interval = 60 * MIN;
			break;
		case day:
			time_interval = 24 * 60 * MIN;
			break;
		default:
			time_interval = 60 * MIN;
			break;
		}
		return time_interval;
	}

	public void AddLog(String sMsg) {
		String sFileName = "";
		String sDate = "";
		String sTime = "";
		String sMessage = "";

		if (sMsg.equals("")) {
			return;
		}

		// java.util.Date cur_date = new java.util.Date();
		Calendar cur_date = Calendar.getInstance();
		int iYear = cur_date.get(Calendar.YEAR);
		int iMonth = cur_date.get(Calendar.MONTH) + 1;
		int iDay = cur_date.get(Calendar.DATE);
		int iHour = cur_date.get(Calendar.HOUR_OF_DAY);
		int iMinute = cur_date.get(Calendar.MINUTE);
		int iSecond = cur_date.get(Calendar.SECOND);
		cur_date = null;

		String sYear = Integer.toString(iYear);
		String sMonth = (iMonth < 10) ? ("0" + Integer.toString(iMonth))
				: Integer.toString(iMonth);
		String sDay = (iDay < 10) ? ("0" + Integer.toString(iDay)) : Integer
				.toString(iDay);
		String sHour = (iHour < 10) ? ("0" + Integer.toString(iHour)) : Integer
				.toString(iHour);
		String sMinute = (iMinute < 10) ? ("0" + Integer.toString(iMinute))
				: Integer.toString(iMinute);
		String sSecond = (iSecond < 10) ? ("0" + Integer.toString(iSecond))
				: Integer.toString(iSecond);

		sDate = sYear + "/" + sMonth + "/" + sDay;
		sTime = sHour + ":" + sMinute + ":" + sSecond;
		sFileName = dirpath + "report_" + sYear + sMonth + sDay + "_Log.log";
		sMessage = "[" + sDate + " " + sTime + "] : " + sMsg;

		PrintWriter log;
		try {
			log = new PrintWriter(new FileWriter(sFileName, true), true);
			log.println(sMessage + "\r"); // auto enter; log.print can't auto
			// enter
			log.close();
		} catch (IOException e) {
			System.err.println("Can't not open file: " + sFileName);
		}
		return;
	}

	/**
	 * ErrLog function
	 *
	 * @param sMsg
	 */
	public void ErrLog(String sMsg) {
		String sFileName = "";
		String sDate = "";
		String sTime = "";
		String sMessage = "";

		if (sMsg.equals("")) {
			return;
		}

		// java.util.Date cur_date = new java.util.Date();
		Calendar cur_date = Calendar.getInstance();
		int iYear = cur_date.get(Calendar.YEAR);
		int iMonth = cur_date.get(Calendar.MONTH) + 1;
		int iDay = cur_date.get(Calendar.DATE);
		int iHour = cur_date.get(Calendar.HOUR_OF_DAY);
		int iMinute = cur_date.get(Calendar.MINUTE);
		int iSecond = cur_date.get(Calendar.SECOND);
		cur_date = null;

		String sYear = Integer.toString(iYear);
		String sMonth = (iMonth < 10) ? ("0" + Integer.toString(iMonth))
				: Integer.toString(iMonth);
		String sDay = (iDay < 10) ? ("0" + Integer.toString(iDay)) : Integer
				.toString(iDay);
		String sHour = (iHour < 10) ? ("0" + Integer.toString(iHour)) : Integer
				.toString(iHour);
		String sMinute = (iMinute < 10) ? ("0" + Integer.toString(iMinute))
				: Integer.toString(iMinute);
		String sSecond = (iSecond < 10) ? ("0" + Integer.toString(iSecond))
				: Integer.toString(iSecond);

		sDate = sYear + "/" + sMonth + "/" + sDay;
		sTime = sHour + ":" + sMinute + ":" + sSecond;
		sFileName = dirpath + "report_" + sYear + sMonth + sDay + "_Log.log";
		sMessage = "[" + sDate + " " + sTime + "] : " + sMsg;

		PrintWriter log;
		try {
			log = new PrintWriter(new FileWriter(sFileName, true), true);
			log.println(sMessage + "\r"); // auto enter; log.print can't auto
			// enter
			log.close();
		} catch (IOException e) {
			System.err.println("Can't not open file: " + sFileName);
		}
		return;
	}

	/**
	 * 获取当前时间并格式化为:yyyy-M-d HH:mm:ss
	 * @return
	 */
	public static String currentTimeStr() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
		String time = dateFormat.format(new Date(System.currentTimeMillis()));
		return time;
	}


	/**
	 * 获取当前时间并格式化为传入参数的格式
	 * @param style  如：DateUtils.yyyy_MM
	 * @return
	 */
	public static String currentTimeStr(String style) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(style);
		String time = dateFormat.format(new Date(System.currentTimeMillis()));
		return time;
	}

	/**
	 * 参数参数年月，获取该月份的第一天开始时间和上个月第一天的开始时间
	 * @param year  如：2018
	 * @param month  如:1
	 * @return  {start=2018-2-01 00:00:00, end=2018-1-01 00:00:00}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getMonthMap(String year, String month) {
		HashMap hashMap = new HashMap();

		int yearTemp = Integer.parseInt(year);
		int monthTemp = Integer.parseInt(month) + 1;

		if (monthTemp > 12) {
			monthTemp = monthTemp - 1;
			yearTemp = yearTemp + 1;
		}

		String monthS = String.valueOf(monthTemp);
		String yearS = String.valueOf(yearTemp);

		String start = yearS + "-" + monthS + "-01 00:00:00";
		String end = year + "-" + month + "-01 00:00:00";

		hashMap.put("start", start);
		hashMap.put("end", end);

		return hashMap;
	}

	/**
	 * 计算开始时间结束时间相差多少天的日期集合
	 * @param dateStart 开始时间，如：2017-11-11
	 * @param dateEnd   结束时间   如：2017-11-13
	 * @param style     将字符串格式化，DateUtils.yyyy_MM_dd
	 * @return   输出结果:[2017-11-11, 2017-11-12, 2017-11-13]
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getDayList(String dateStart, String dateEnd,
			String style) {
		ArrayList list = new ArrayList();
		SimpleDateFormat myFormatter = new SimpleDateFormat(style);
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = myFormatter.parse(dateStart);
			date2 = myFormatter.parse(dateEnd);
		} catch (Exception ex1) {
			System.out.println("日期转换格式应和传入的日期格式一致" + ex1.toString());
		}
		long day = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000)
				+ 1;
		long date3 = date1.getTime();

		for (int i = 0; i < day; i++) {
			String dateStr = myFormatter.format((new Date(date3)));
			list.add(dateStr);
			date3 = date3 + 24 * 60 * 60 * 1000;
		}
		return list;
	}

	/**
	 * 计算开始时间结束时间相差几个月的日期集合
	 * @param beginTime  开始时间  如：2017/10,开始时间和结束时间的格式只能是这样
	 * @param endTime	 结束时间  如：2017/12
	 * @return   输出结果：[2017/10, 2017/11, 2017/12]
	 */
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static ArrayList getMonthList(String beginTime, String endTime) {
		String[] time1 = beginTime.split("/");
		String[] time2 = endTime.split("/");
		int iyear1 = Integer.parseInt(time1[0]);
		int imonth1 = Integer.parseInt(time1[1]);
		int iyear2 = Integer.parseInt(time2[0]);
		int imonth2 = Integer.parseInt(time2[1]);
		int months = (iyear2 - iyear1) * 12 + (imonth2 - imonth1);

		ArrayList list = new ArrayList();
		boolean dif = false;

		for (int i = iyear1; i <= iyear2; i++) {
			for (int k = iyear1; k <= iyear2; k++) {
				if (dif)
					imonth1 = 1;
				if (i == iyear2) {
					for (int j = imonth1; j <= imonth2; j++) {
						String m = String.valueOf(j);
						m = (m.length() == 1) ? "/0" + m : "/" + m;
						String data = String.valueOf(i) + m;
						list.add(data);
					}
				} else if (iyear1 < iyear2) {
					for (int j = imonth1; j <= 12; j++) {
						String m = String.valueOf(j);
						m = (m.length() == 1) ? "/0" + m : "/" + m;
						String data = String.valueOf(iyear1) + m;
						list.add(data);
					}
				} else {
					String data = "2000/01";
					list.add(data);
				}
				i++;
				dif = true;
			}
		}
		return list;
	}

	/**
	 * 传入年-月，算出中间隔了几个月
	 * @param dateStart  开始时间   年-月
	 * @param dateEnd    结束时间   年-月
	 * @param style
	 * @return
	 * @throws ParseException
	 *
	 * 例如传入参数  "2017-11","2018-02","yyyy-MM",
	 * 输出的结果是2017-11,2017-12,2018-01,2018-02
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getYearAndMonthList(String dateStart, String dateEnd,
			String style) {

		List list=new ArrayList();

		SimpleDateFormat sdf=new SimpleDateFormat(style);

		try {

	        Calendar c1=Calendar.getInstance();
	        Calendar c2=Calendar.getInstance();

	        c1.setTime(sdf.parse(dateStart));
	        c2.setTime(sdf.parse(dateEnd));


	        int year =c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);

	        int monthDistance;
	        //开始日期若小月结束日期
	        if(year<0){
	            year=-year;
	            monthDistance = year*12+c1.get(Calendar.MONTH)-c2.get(Calendar.MONTH);
	        }

	        monthDistance = year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);

	        int cyear;
	        int cmonth;
	        for(int i=0;i<monthDistance+1;i++){
	        	//获取开始时间的年份
	        	cyear=c1.get(c1.YEAR);
	        	//这里的月份要加1才是开始时间的月份
	        	c1.add(c1.MONTH, 1);
	        	//这里第一次加完之后是开始时间的月份
	        	cmonth=c1.get(c1.MONTH);
	        	//0代表12月
	        	if(cmonth==0){
	        		cmonth=12;
	        	}

	        	String yearAndMonth;
	        	if(cmonth<10){
	        		yearAndMonth=cyear+""+"-"+"0"+cmonth+"";
	        	}else{
	        		yearAndMonth=cyear+""+"-"+cmonth+"";
	        	}

	        	list.add(yearAndMonth);

	        }
		} catch (Exception e) {
			e.printStackTrace();
		}

        return list;
	}

	public static String getCurrentTimeStr() {
		return getCurrentTimeStr("yyyy-M-d HH:mm:ss");
	}
	/**
	 * 获取当前时间并且格式化为yyyy-M-d HH:mm:ss
	 * @param timeformat 参数不知道有啥用
	 * @return
	 */
	public static String getCurrentTimeStr(String timeformat) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(timeformat);
		String time = dateFormat.format(new Date(System.currentTimeMillis()));
		return time;
	}

	/**
	 * 获取当前日期是当前年的第几天
	 * @param offset  往前进或往后退几天
	 * @return
	 */
	public static int getDayOfYear(int offset) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, offset);
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获取传入的Date是当前年的第几天
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获取传入的Date往前进或往后退几天算得该时间是在当前年的第几天
	 * @param date
	 * @param offset 往前进或往后退几天
	 * @return
	 */
	public static int getDayOfYear(Date date, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, offset);
		return cal.get(Calendar.DAY_OF_YEAR);
	}


	/**
	 * 获取传入的date字符串是在当前年的第几天
	 * @param format 格式化date的形式，如yyyy-MM-dd
	 * @param date   传入的时间字符串，如2017-11-23
	 * @return
	 * @throws ParseException
	 */
	public static int getDayOfYear(String format, String date)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(date));
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 判断两个日期相距的间隔(相隔多少天, 多少秒, 多少小时等)
	 *
	 * @param date1
	 *            参照的日期
	 * @param date2
	 *            目标日期
	 * @param level
	 *            间隔标准
	 * @return
	 */
	public static int getDistance(Date date1, Date date2, IntervalModel level) {
		return getDistance(date1.getTime(), date2.getTime(), level);
	}

	/**
	 * 判断两个日期相距的间隔(相隔多少天, 多少秒, 多少小时等)
	 *
	 * @param date1
	 *            参照的日期
	 * @param date2
	 *            目标日期
	 * @param level
	 *            间隔标准, 表示返回的是多少天, 多少秒, 多少小时等
	 * @return
	 */
	public static int getDistance(long date1, long date2, IntervalModel level) {
		long interval = date1 - date2;
		int time_interval = getTimeInterval(level);
		int dis = (int) (interval / time_interval);
		return dis;
	}

	/**
	 * 如果一个日期是空，则抛出RuntimeException(msg)异常
	 *
	 * @param date
	 * @param msg
	 */
	public static void checkIsNull(Date date, String msg) {
		if (date == null) {
			throw new RuntimeException(msg);
		}
	}

	/**
	 * 获取beginTime后跨了机构24点才到了endTime, 也就是跨的天数
	 *
	 * @param beginTime
	 * @param endTime
	 * @return 返回跨的天数
	 */
	public static int getSpanDayNum(Date beginTime, Date endTime) {
		return getSpanDayNum(beginTime.getTime(), endTime.getTime());
	}

	/**
	 * 获取beginTime后跨了机构24点才到了endTime, 也就是跨的天数
	 *
	 * @param beginTime
	 * @param endTime
	 * @return 返回跨的天数
	 */
	public static int getSpanDayNum(long beginTime, long endTime) {
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(beginTime);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(endTime);
		// 获取c1当天时间的24点， 也就是第二天的零点
		Calendar c3 = Calendar.getInstance();
		c3.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH) + 1, 0, 0, 0);
		long a = c3.getTimeInMillis();// c1第二天零点的毫秒数
		long b = 24 * 3600 * 1000;// 一天的毫秒数
		int count = 0;
		// 如果当天的24点还比c2的毫秒数小， 说明有跨天， 跨天数加一
		for (int i = 0; a + i * b < c2.getTimeInMillis(); i++) {
			count++;
		}
		return count;
	}

	/**
	 * 获取指定interval的那天的最后的时间 比如time为2017-12-21 22:00:01, interval为1,
	 * 表示获取到22日那天的23:59； interval为-1, 表示获取到20日那天的23:59如果； interval为0，
	 * 表示获取到当天的23:59。同理， interval还可以为其他的整数
	 */
	public static long getEndTime(long time, int interval) {
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(time);
		// 获取c1当天时间的24点， 也就是第二天的零点
		Calendar c3 = Calendar.getInstance();
		c3.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH) + interval, 23, 59, 59);
		return c3.getTimeInMillis();
	}
	public static long getEndTime(Date time, int interval) {
		return getEndTime(time.getTime(),interval);
	}

	/** 获取指定时间的最晚时间，如：date为2014-05-20 11:11:11，返回时间为：2014-05-20 23:59:59 */
	public static Date getEndTime(Date date) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String strTime = YYYYMMDD.format(cal.getTime()) + " 23:59:59";
			return YYYYMMDDHHMMSS.parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取当前时间的前一天的开始时间，如：今天为2014-05-20 11:11:11，返回时间为：2014-05-19 00:00:00
	 *
	 * @return
	 * @throws ParseException
	 */
	public static Date getStartTimeBeforeCurrent() {

		try {

			Calendar cal = Calendar.getInstance();

			SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");

			cal.add(Calendar.DAY_OF_MONTH, -1);

			SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String strTime = YYYYMMDD.format(cal.getTime()) + " 00:00:00";

			return YYYYMMDDHHMMSS.parse(strTime);

		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * 获取当前时间的前一天的最晚时间，如：今天为2014-05-20 11:11:11，返回时间为：2014-05-19 23:59:59
	 *
	 * @return
	 * @throws ParseException
	 */
	public static Date getEndTimeBeforeCurrent() {

		try {

			Calendar cal = Calendar.getInstance();

			SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");

			cal.add(Calendar.DAY_OF_MONTH, -1);

			SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String strTime = YYYYMMDD.format(cal.getTime()) + " 23:59:59";

			return YYYYMMDDHHMMSS.parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/** 获取当前时间的最早时间，如：date为2014-05-20 11:11:11，返回时间为：2014-05-20 00:00:00 */
	public static long getStartTime(long time, int interval) {
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(time);
		// 获取c1当天时间的24点， 也就是第二天的零点
		Calendar c3 = Calendar.getInstance();
		c3.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH) + interval, 00, 00, 00);
		return c3.getTimeInMillis();
	}
	/**
	 * 获取指定interval的那天的最后的时间 比如time为2017-12-21 22:00:01, interval为1,
	 * 表示获取到22日那天的00:00:00； interval为-1, 表示获取到20日那天的00:00:00如果； interval为0，
	 * 表示获取到当天的00:00:00。同理， interval还可以为其他的整数
	 */
	public static Date getStartTime(Date date, int interval) {
		long startTime = getStartTime(date.getTime(), interval);
		return new Date(startTime);
	}

}
