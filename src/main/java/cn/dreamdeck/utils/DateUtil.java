package cn.dreamdeck.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("dd");

	private final static SimpleDateFormat sdfYearMonth = new SimpleDateFormat("yyyy-MM");
	private final static SimpleDateFormat sdfYearMonthDay = new SimpleDateFormat("yyyy-MM-dd");

	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat sdfDayList = new SimpleDateFormat("yyyy/MM/dd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final static SimpleDateFormat sdfTimeZH = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private final static SimpleDateFormat sdfTime1 = new SimpleDateFormat("yyyyMMddHHmmss");

	private final static SimpleDateFormat stringSdfTime = new SimpleDateFormat("MM月dd日 HH时mm分");
	private final static SimpleDateFormat sdfYYYYMMDD = new SimpleDateFormat("yyyy年MM月dd日");
	private final static SimpleDateFormat sdfYYMMDD = new SimpleDateFormat("yy.MM.dd");
	private final static SimpleDateFormat sdfMMDD = new SimpleDateFormat("MM月dd日");
	private final static SimpleDateFormat sdfHHss = new SimpleDateFormat("HH:mm");
	private final static SimpleDateFormat sdfHHmmss = new SimpleDateFormat("HH:mm:ss");
	private final static SimpleDateFormat sdfHH = new SimpleDateFormat("HH");
	private final static SimpleDateFormat sdfTimeMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static final int NIGHT_TIME = 22;
	public static final int MORNING_TIME = 7;

	/**
	 * 是否免打扰时间
	 *
	 * @return
	 */
	public static Boolean isNoDisturbing() {

		return isNoDisturbing(new Date());
	}

	public static Boolean isNoDisturbing(String dateTime) {

		return isNoDisturbing(fomatDateTime(dateTime));
	}

	public static Boolean isNoDisturbing(Date date) {

		System.out.println(date);
		System.out.println(date.getHours());

		if (date.getHours() >= NIGHT_TIME || date.getHours() < MORNING_TIME) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	public static String getMonth() {
		return sdfMonth.format(new Date());
	}

	public static String getDay() {
		return sdfDay.format(new Date());
	}

	public static String getYearMonth(Date date) {
		return sdfYearMonth.format(date);
	}

	public static String getYearMonth() {
		return sdfYearMonth.format(new Date());
	}

	public static String getMinute(Date date) {
		return sdfTimeMinute.format(date);
	}

	public static String getMinute(String time) {
		return sdfTimeMinute.format(time);
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getYearMonthDay() {
		return sdfYearMonthDay.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getYearMonthDay(Date date) {
		return sdfYearMonthDay.format(date);
	}

	public static String getYearMonthDay(String date) {
		return sdfYearMonthDay.format(date);
	}

	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays() {
		return sdfDays.format(new Date());
	}

	public static String getDays(Date date) {
		return sdfDays.format(date);
	}

	/**
	 * 获取YYYY-MM-DD hh:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	public static String getTimeZH() {
		return sdfTimeZH.format(new Date());
	}

	public static String getNumber() {
		return sdfTime1.format(new Date());
	}

	public static String getTime(Date date) {
		return sdfTime.format(date);
	}

	public static String getTimeHH(Long time) {
		return sdfHH.format(time);
	}

	public static String getTime(String time) {
		return sdfTime.format(time);
	}

	public static String getTimeHHss(Long time) {
		return sdfHHss.format(time);
	}

	public static String getTimeHHss(String string) {
		return sdfHHss.format(string);
	}

	public static String getTimeHHmmss(Long time) {
		return sdfHHmmss.format(time);
	}

	public static String getTimeHHmmss(String string) {
		return sdfHHmmss.format(string);
	}

	public static String getYYYYMMDD(Date date) {
		return sdfYYYYMMDD.format(date);
	}

	public static String getYYMMDD(Date date) {
		return sdfYYMMDD.format(date);
	}

	public static String getTimeMMDD(String string) {
		return sdfMMDD.format(string);
	}

	public static String getTimeMMDD(Date date) {
		return sdfMMDD.format(date);
	}

	/**
	 * 获取 yyyy年MM月dd日 HH时mm分ss秒
	 *
	 * @return
	 */
	/*
	 * public static String getStringTime(Date date) { return
	 * stringSdfTime.format(date); }
	 */
	public static String getStringTime(String time) {
		return stringSdfTime.format(time);
	}

	public static String getStringTime(Date time) {
		return stringSdfTime.format(time);
	}

	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() >= fomatDate(e).getTime();
	}

	public static boolean compareDateTimeDa(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() > fomatDate(e).getTime();
	}

	public static boolean compareDateTimeXiao(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() < fomatDate(e).getTime();
	}

	public static boolean compareDateTimeDeng(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() == fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date fomatDateYYYYMMDD(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date fomatDateMMDD(String date) {
		DateFormat fmt = new SimpleDateFormat("MM月dd日");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDayList(Date time) {
		return sdfDayList.format(time);
	}

	public static String getDayCN(Date time) {
		return sdfYYYYMMDD.format(time);
	}

	public static Date fomatDateTime(String date) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean fomatTime(String date) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			fmt.parse(date);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {

		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDateTime(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long aa = 0;
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24))
					/ 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}
	
	public static long getHourSub(String beginDateStr, String endDateStr) {
		long hours = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		hours = (endDate.getTime() - beginDate.getTime()) / ( 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return hours;
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	public static String getAfterDayDay(int days) {
		// int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	public static String getAfterDayDayMMdd(int days) {
		// int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("MM.dd");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	public static String getBeforeDayDaydd(int days) {
		// int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("dd日");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	public static Date getDateTime(String time) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// String str="2011-5-31 14:40:50";
		Date d = null;
		try {
			d = sim.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return d;

	}

	public static Date getDate(String time) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		// String str="2011-5-31 14:40:50";
		Date d = null;
		try {
			d = sim.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return d;

	}

	public static int getHour(Date date) {
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar = Calendar.getInstance();
		canlendar.setTime(date);
		return canlendar.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMin(Date date) {
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar = Calendar.getInstance();
		canlendar.setTime(date);
		return canlendar.get(Calendar.MINUTE);
	}

	public static String getAfterTimeDate(String hour) {
		int daysInt = Integer.parseInt(hour);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.HOUR_OF_DAY, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	public static Date getAfterDayDate(Date date, int days) {

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.setTime(date);
		canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		Date date1 = canlendar.getTime();

		return date1;
	}

	/**
	 * 得到n天之后是周几
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	public static String getDayOfWeekForChinese(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	public static int getDayOfWeek(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);
		return day;
	}

	public static int getWeekOfYear(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int week = c.get(Calendar.WEEK_OF_YEAR);
		return week;
	}


	public static long getMS(String date) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c.getTimeInMillis();
	}

	public static long getTimeMS(String date) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c.getTimeInMillis();
	}

	public static String getDateTime(Long time) {
		Date dat = new Date(time);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dat);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateTime = format.format(gc.getTime());
		return dateTime;
	}

	public static String getTime(Long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = format.format(time);
		return dateTime;
	}
	/*
	 * public static String getStringTime(String time){ SimpleDateFormat format =
	 * new SimpleDateFormat("MM/dd HH/mm"); String dateTime=format.format(time);
	 * return dateTime; }
	 */

	public static String msFormatMin(long ms) {

		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = day < 10 ? "0" + day : "" + day; // 天
		String strHour = hour < 10 ? "0" + hour : "" + hour;// 小时
		String strMinute = minute < 10 ? "0" + minute : "" + minute;// 分钟
		String strSecond = second < 10 ? "0" + second : "" + second;// 秒
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;// 毫秒
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

		// return strMinute + " 分钟 " + strSecond + " 秒";
		return strMinute;
	}

	public static String getAfterHours(Date date, int hours) {

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.setTime(date);
		canlendar.add(Calendar.HOUR, hours); // 日期减 如果不够减会将月变动
		date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	public static String getMinHours(Date date, int minute) {

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.setTime(date);
		canlendar.add(Calendar.MINUTE, minute); // 日期减 如果不够减会将月变动
		date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	public static String calculateTime(String createTime) {
		String nowTime = DateUtil.getTime();
		long ms = DateUtil.getMS(nowTime) - DateUtil.getMS(createTime);
		String timeContent = "";

		Integer miaoNum = (int) (ms / 1000);
		if (miaoNum <= 59) {
			timeContent = miaoNum + "秒前";
		} else {
			Integer minNum = miaoNum / 60;
			if (minNum <= 59) {
				timeContent = minNum + "分钟前";
			} else {
				Integer xiaoshi = minNum / 60;
				if (xiaoshi <= 23) {
					timeContent = xiaoshi + "小时前";
				} else {
					Integer day = xiaoshi / 24;
					if (day <= 30) {
						timeContent = day + "天前";
					} else {
						timeContent = "一个月前";
					}
				}
			}
		}
		return timeContent;
	}

	/**
	 * 获取周几
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	public static String getMMDD(String date) {

		try {
			Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
			String str2 = sdf1.format(d1);
			String str3 = sdf2.format(d1);
			return str2 + "月" + str3 + "日";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

//	public static String TimeStamp2Date(String timestampString, String formats) {
//		if (TextUtils.isEmpty(formats))
//			formats = "yyyy-MM-dd HH:mm:ss";
//		Long timestamp = Long.parseLong(timestampString) * 1000;
//		String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
//		return date;
//	}

	public static long Date2TimeStamp(String dateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.parse(dateStr).getTime() / 1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static long Date2TimeStamp1(String dateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(dateStr).getTime() / 1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取两个日期之间的日期
	 * 
	 * @param start 开始日期
	 * @param end   结束日期
	 * @return 日期集合
	 * @throws ParseException
	 */
	public static List<Date> getBetweenDates(String start, String end) throws ParseException {

		Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(start);// 定义起始日期
		Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(end);// 定义结束日期

		List<Date> result = new ArrayList<Date>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(d1);
		while (d1.getTime() <= d2.getTime()) {
			result.add(tempStart.getTime());
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
			d1 = tempStart.getTime();
		}
		return result;
	}

	// 使用当前月份,得到上一个月的月份:月份的格式是:yyyy-MM
	public static String getLastDate(String currentDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(currentDate + "-" + "01");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);

		String lastDate = c.get(Calendar.YEAR) + "-" + "0" + (c.get(Calendar.MONTH) + 1);

		return lastDate;

	}

	// 获取上一年的年份
	public static String lastYear(String Date) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1);
		Date y = c.getTime();
		String year = sdfYear.format(y);
		return year;

	}

	public static String isWeekend(Date bDate) throws ParseException {
		/*
		 * DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); Date bdate =
		 * format1.parse(bDate);
		 */
		Calendar cal = Calendar.getInstance();
		cal.setTime(bDate);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return "OK";
		} else {
			return "NO";
		}

	}

	public static String getUpYearMonth() {
		Calendar now = Calendar.getInstance();
		int month = now.get(Calendar.MONTH);
		String m;
		if (month < 10) {
			m = "0" + month;
		} else {
			m = month + "";
		}
		return now.get(Calendar.YEAR) + "-" + m;
	}


	//获得两个日期之间的所有月份
	public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}


	public static String getLastMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // 设置为当前时间
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
		date = calendar.getTime();
		String accDate = format.format(date);
		return accDate;
	}

	public static String addHourDate(String date, double hour) {
		String dateStr = null;
		SimpleDateFormat formats = new SimpleDateFormat("HH:mm");
		int h = (new Double(hour)).intValue();
		double m = (hour-h)*60;
		int c = (new Double(m)).intValue();
		try {
			Date parse = formats.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parse);
			calendar.add(Calendar.HOUR_OF_DAY, h);
			calendar.add(Calendar.MINUTE,c);
			dateStr = formats.format(calendar.getTime());
		} catch (ParseException e) {
		}
		return dateStr;
	}

	public static boolean belongCalendar(String beginTime, String endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(getDateTime(getTime()));

		Calendar begin = Calendar.getInstance();
		begin.setTime(getDateTime(beginTime+" 00:00:00"));

		Calendar end = Calendar.getInstance();
		end.setTime(getDateTime(endTime+" 23:59:59"));

		return date.after(begin) && date.before(end);
	}


	public static boolean verifyTime(String time,String startTime,String endTime){

		Boolean ss = true;
		Calendar instance = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");// 24小时制
		//引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变
		//量day格式一致
		Calendar startDate = Calendar.getInstance();
		Calendar endDate= Calendar.getInstance();
		try {
			instance.setTime(format.parse(time));
			startDate.setTime(format.parse(startTime));
			endDate.setTime(format.parse(endTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (compTime(startTime,endTime)){
			Calendar ld= Calendar.getInstance();
			Calendar yd= Calendar.getInstance();
			try {
				ld.setTime(format.parse("23:59:59"));
				yd.setTime(format.parse("00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(instance.after(startDate) && instance.before(ld)){
				return ss;
			}
			if(instance.after(yd) && instance.before(endDate)){
				return ss;
			};
		}
		return instance.after(startDate) && instance.before(endDate);

	}


	public static boolean compTime(String s1,String s2){
		try {
			if (s1.indexOf(":")<0||s1.indexOf(":")<0) {
				System.out.println("格式不正确");
			}else{
				String[]array1 = s1.split(":");
				int total1 = Integer.valueOf(array1[0])*3600+Integer.valueOf(array1[1])*60+Integer.valueOf(array1[2]);
				String[]array2 = s2.split(":");
				int total2 = Integer.valueOf(array2[0])*3600+Integer.valueOf(array2[1])*60+Integer.valueOf(array2[2]);
				return total1-total2>0?true:false;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return true;
		}
		return false;

	}



//	public static String getDay(Integer year,Integer month, Integer day,Integer days) {
//		 /*
//	      * 1、先给定一个时间，例如2017年1月1日
//	      */
//	     Calendar cld = Calendar.getInstance();
//	     cld.set(Calendar.YEAR, year);
//	     cld.set(Calendar.MONDAY,month);
//	     cld.set(Calendar.DATE,day);
//	     
//	     //调用Calendar类中的add()，增加时间量
//	     cld.add(Calendar.DATE, days);
//	     
//	     String time = cld.get(Calendar.YEAR)+"年"+cld.get(Calendar.MONTH)+"月"+cld.get(Calendar.DATE)+"日";
//	     
//	     return time;
//	     //System.out.println("增加100天的日期为："+cld.get(Calendar.YEAR)+"年"+cld.get(Calendar.MONTH)+"月"+cld.get(Calendar.DATE)+"日");
//	}
//	
//

	/**
	 * 功能描述：时间相减得到时分秒
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	public static  String getTimeSub(String beginDateStr, String endDateStr){
		String timeSub = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = null;
		Date endDate = null;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			//毫秒ms
			long diff = endDate.getTime() - beginDate.getTime();
			if (diff==0){
				return  "0秒";
			}

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			//指定小数点后面的位数（2位）
			if (diffDays!=0){
				timeSub+=diffDays+"天";
			} if (diffHours!=0){
				timeSub+=diffHours+"时";
			} if (diffMinutes!=0){
				timeSub+=diffMinutes+"分";
			} if (diffSeconds!=0){
				timeSub+=diffSeconds+"秒";
			}
			/*timeSub+= diffDays+"天"+diffHours+"小时"+diffMinutes+"分钟"+diffSeconds+"秒";*/
			/*System.out.print(diffDays + " 天, ");
			System.out.print(diffHours + " 小时, ");
			System.out.print(diffMinutes + " 分钟, ");
			System.out.print(diffSeconds + " 秒");*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  timeSub ;
	}


	public static String TimeDifference(String start, String end){

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{

			Date d2 = df.parse(start.toString());
			Date d1 = df.parse(end.toString());
			long diff = d1.getTime() - d2.getTime();//这样得到的差值是毫秒级别
			long days = diff / (1000 * 60 * 60 * 24);

			long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
			long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
			return  days+"天"+hours+"小时"+minutes+"分";
		}catch (Exception e)
		{
			return  "计算失败";
		}
	}

}
