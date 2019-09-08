package com.iee.sort;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: dateTest
 * @Description: 日期排序, Date的before和after效率一样, 且都比转换成getTime后效率高50%左右
 * @author 龙小南
 * @email longxiaonan@163.com
 * @date 2017年10月10日 下午1:22:41
 * @version 1.0
 */
public class DateCompare {

	public static void main(String[] args) throws Exception {
		List dateList1 = new ArrayList();
		System.err.println("----before sort:");
		for (int i = 0; i < 90000; i++) {// 随机生成五个日期
			Date d = new Date(System.currentTimeMillis() + (int) (Math.random() * 1000000000));
			dateList1.add(d);
		}
		System.out.println(dateList1.size());
		List dateList2 = new ArrayList<> (dateList1);
		System.out.println(dateList2.size());

		System.err.println("----ComparatorDateByAfter sort:");
		ComparatorDateByBefore1 c1 = new ComparatorDateByBefore1();
		Collections.sort(dateList1, c1);
		dateList1.stream().map(d -> formatDate(d)).forEach(System.out::println);

		//通过lambda获取最大日期
		long startTime1 = System.currentTimeMillis();
		Date aa = (Date) dateList1.stream().max(Comparator.comparing(Date::getTime)).get();
		System.out.println(formatDate(aa) + "++++");
		long endTime1 = System.currentTimeMillis();
		System.out.println("ComparatorDateByAfter 花费的时间为:" + (endTime1 - startTime1));

		long startTime2 = System.currentTimeMillis();
		System.err.println("----ComparatorDateByGettime sort:");
		ComparatorDateByGettime c2 = new ComparatorDateByGettime();
		Collections.sort(dateList2, c2);
		long endTime2 = System.currentTimeMillis();
		System.out.println("ComparatorDateByGettime 花费的时间为:" + (endTime2 - startTime2));
//		dateList2.stream().map(d -> formatDate(d)).forEach(System.out::println);
		/*for (Object date : dateList2) {
			System.out.println(formatDate(date));// 格式化了日期
			// System.out.println(date);//未格式化日期
		}*/
	}

	/**
	 * 格式化日期，返回字符串形式，形如"yyyy-MM-dd"
	 *
	 * @param date
	 * @return
	 */
	private static String formatDate(Object date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		return dateFormat.format(date);
	}
}

class ComparatorDateByAfter implements Comparator {
	public int compare(Object obj1, Object obj2) {
		Date begin = (Date) obj1;
		Date end = (Date) obj2;
		if (begin.after(end)) {
			return 1;
		} else {
			return -1;
		}
	}
}

class ComparatorDateByBefore1 implements Comparator {
	public int compare(Object obj1, Object obj2) {
		Date begin = (Date) obj1;
		Date end = (Date) obj2;
		if (begin.before(end)) {
			return 1;
		} else {
			return -1;
		}
	}
}

class ComparatorDateByBefore2 implements Comparator {
	public int compare(Object obj1, Object obj2) {
		Date begin = (Date) obj1;
		Date end = (Date) obj2;
		if (begin.before(end)) {
			return 1;
		} else {
			return -1;
		}
	}
}

class ComparatorDateByGettime implements Comparator {
	public int compare(Object obj1, Object obj2) {
		Date begin = (Date) obj1;
		Date end = (Date) obj2;
		if (begin.getTime() > end.getTime()) {
			return 1;
		} else {
			return -1;
		}
	}
}
