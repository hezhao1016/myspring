package com.test.myspring.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * ���ڡ�ʱ�䴦������
 * 
 * �Ⱦ�������
 * 
 * commons-logging.jar
 * 
 * @author user
 * 
 */
public class DateUtil {

	private Calendar calendar = Calendar.getInstance();

	/**
	 * ����unixʱ��� (1970�����������)
	 * 
	 * @return
	 */
	public static long getUnixStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * �õ����������
	 * 
	 * @return
	 */
	public static String getYestoryDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yestoday = sdf.format(calendar.getTime());
		return yestoday;
	}

	/**
	 * �õ����������
	 * 
	 * @return
	 */
	public static String getTodayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		return date;
	}

	/**
	 * ʱ���ת��Ϊʱ���ʽ
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static String timeStampToStr(long timeStamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(timeStamp * 1000);
		return date;
	}

	/**
	 * �õ����� yyyy-MM-dd
	 * 
	 * @param timeStamp
	 *            ʱ���
	 * @return
	 */
	public static String formatDate(long timeStamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(timeStamp * 1000);
		return date;
	}

	/**
	 * �õ�ʱ�� HH:mm:ss
	 * 
	 * @param timeStamp
	 *            ʱ���
	 * @return
	 */
	public static String getTime(long timeStamp) {
		String time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(timeStamp * 1000);
		String[] split = date.split("\\s");
		if (split.length > 1) {
			time = split[1];
		}
		return time;
	}

	/**
	 * ��һ��ʱ���ת������ʾ��ʱ���ַ�������ոգ�1��ǰ
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static String convertTimeToFormat(long timeStamp) {
		long curTime = System.currentTimeMillis() / (long) 1000;
		long time = curTime - timeStamp;

		if (time < 60 && time >= 0) {
			return "�ո�";
		} else if (time >= 60 && time < 3600) {
			return time / 60 + "����ǰ";
		} else if (time >= 3600 && time < 3600 * 24) {
			return time / 3600 + "Сʱǰ";
		} else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
			return time / 3600 / 24 + "��ǰ";
		} else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
			return time / 3600 / 24 / 30 + "����ǰ";
		} else if (time >= 3600 * 24 * 30 * 12) {
			return time / 3600 / 24 / 30 / 12 + "��ǰ";
		} else {
			return "�ո�";
		}
	}

	/**
	 * ��һ��ʱ���ת������ʾ��ʱ���ַ�����(���ٷ���)
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static String timeStampToFormat(long timeStamp) {
		long curTime = System.currentTimeMillis() / (long) 1000;
		long time = curTime - timeStamp;
		return time / 60 + "";
	}

	// ---------------------------------------------------------------------------------------------------------

	/**
	 * �õ���ǰ��ʱ�䣬ʱ���ʽyyyy-MM-dd
	 * 
	 * @return
	 */
	public String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	/**
	 * �õ���ǰ��ʱ��,�Զ���ʱ���ʽ y �� M �� d �� H ʱ m �� s ��
	 * 
	 * @param dateFormat
	 *            �����ʾ��ʱ���ʽ
	 * @return
	 */
	public String getCurrentDate(String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(new Date());
	}

	/**
	 * ���ڸ�ʽ����Ĭ�����ڸ�ʽyyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public String getFormatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * ���ڸ�ʽ�����Զ���������ڸ�ʽ
	 * 
	 * @param date
	 * @return
	 */
	public String getFormatDate(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	/**
	 * ���ص�ǰ���ڵ�ǰһ��ʱ�����ڣ�amountΪ���� ��ǰʱ����ʱ�� Ϊ���� ��ǰʱ��ǰ��ʱ�� Ĭ�����ڸ�ʽyyyy-MM-dd
	 * 
	 * @param field
	 *            �����ֶ� y �� M �� d �� H ʱ m �� s ��
	 * @param amount
	 *            ����
	 * @return һ������
	 */
	public String getPreDate(String field, int amount) {
		calendar.setTime(new Date());
		if (field != null && !field.equals("")) {
			if (field.equals("y")) {
				calendar.add(calendar.YEAR, amount);
			} else if (field.equals("M")) {
				calendar.add(calendar.MONTH, amount);
			} else if (field.equals("d")) {
				calendar.add(calendar.DAY_OF_MONTH, amount);
			} else if (field.equals("H")) {
				calendar.add(calendar.HOUR, amount);
			}
		} else {
			return null;
		}
		return getFormatDate(calendar.getTime());
	}

	/**
	 * ĳһ�����ڵ�ǰһ������
	 * 
	 * @param d
	 *            ,ĳһ������
	 * @param field
	 *            �����ֶ� y �� M �� d �� H ʱ m �� s ��
	 * @param amount
	 *            ����
	 * @return һ������
	 */
	public String getPreDate(Date date, String field, int amount) {
		calendar.setTime(date);
		if (field != null && !field.equals("")) {
			if (field.equals("y")) {
				calendar.add(calendar.YEAR, amount);
			} else if (field.equals("M")) {
				calendar.add(calendar.MONTH, amount);
			} else if (field.equals("d")) {
				calendar.add(calendar.DAY_OF_MONTH, amount);
			} else if (field.equals("H")) {
				calendar.add(calendar.HOUR, amount);
			}
		} else {
			return null;
		}
		return getFormatDate(calendar.getTime());
	}

	/**
	 * ĳһ��ʱ���ǰһ��ʱ��
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public String getPreDate(String date) throws ParseException {
		Date d = new SimpleDateFormat().parse(date);
		String preD = getPreDate(d, "d", 1);
		Date preDate = new SimpleDateFormat().parse(preD);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(preDate);
	}

	// ------------------------------------------------------------------------------------------------

	protected static Logger log = Logger.getLogger(DateUtil.class);

	/**
	 * ȱʡ�����ڸ�ʽ
	 */
	private static final String DAFAULT_DATE_FORMAT = "yyyy-M-d";

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * Ĭ���������͸���.
	 * 
	 * @see DAFAULT_DATE_FORMAT
	 */
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			DAFAULT_DATE_FORMAT);

	/**
	 * ȱʡ������ʱ���ʽ
	 */
	private static final String DAFAULT_DATETIME_FORMAT = "yyyy-M-d HH:mm:ss";

	/**
	 * ʱ���ʽ
	 */
	private static String DATETIME_FORMAT = DAFAULT_DATETIME_FORMAT;

	private static SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			DATETIME_FORMAT);

	/**
	 * ȱʡ��ʱ���ʽ
	 */
	private static final String DAFAULT_TIME_FORMAT = "HH:mm:ss";

	/**
	 * ʱ���ʽ
	 */
	private static String TIME_FORMAT = DAFAULT_TIME_FORMAT;

	private static SimpleDateFormat timeFormat = new SimpleDateFormat(
			TIME_FORMAT);

	private DateUtil() {
		// ˽�ù�������.��Ϊ�����ǹ�����.
	}

	/**
	 * ��ȡ��ʽ��ʵ��.
	 * 
	 * @param pattern
	 *            ���Ϊ��ʹ��DAFAULT_DATE_FORMAT
	 * @return
	 */
	public static SimpleDateFormat getFormatInstance(String pattern) {
		if (pattern == null || pattern.length() == 0) {
			pattern = DAFAULT_DATE_FORMAT;
		}
		return new SimpleDateFormat(pattern);
	}

	/**
	 * ��ʽ��Calendar
	 * 
	 * @param calendar
	 * @return
	 */
	public static String formatCalendar(Calendar calendar) {
		if (calendar == null) {
			return "";
		}
		return dateFormat.format(calendar.getTime());
	}

	public static String formatDateTime(Date d) {
		if (d == null) {
			return "";
		}
		return datetimeFormat.format(d);
	}

	public static String formatDate(Date d) {
		if (d == null) {
			return "";
		}
		return dateFormat.format(d);
	}

	/**
	 * ��ʽ��ʱ��
	 * 
	 * @param calendar
	 * @return
	 */
	public static String formatTime(Date d) {
		if (d == null) {
			return "";
		}
		return timeFormat.format(d);
	}

	/**
	 * ��ʽ������������
	 * 
	 * @param intDate
	 * @return
	 */
	public static String formatIntDate(Integer intDate) {
		if (intDate == null) {
			return "";
		}
		Calendar c = newCalendar(intDate);
		return formatCalendar(c);
	}

	/**
	 * ����ָ����ʽ������ʽ����.
	 * 
	 * @param date
	 *            ����ʽ��������.
	 * @param pattern
	 *            ��ʽ����ʽ��ָ�,��yyMMddHHmmss
	 * @return �ַ���������.
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		if (StringUtil.isBlank(pattern)) {
			return formatDate(date);
		}
		SimpleDateFormat simpleDateFormat = null;
		try {
			simpleDateFormat = new SimpleDateFormat(pattern);
		} catch (Exception e) {
			e.printStackTrace();
			return formatDate(date);
		}
		return simpleDateFormat.format(date);
	}

	/**
	 * ȡ��Integer�͵ĵ�ǰ����
	 * 
	 * @return
	 */
	public static Integer getIntNow() {
		return getIntDate(getNow());
	}

	/**
	 * ȡ��Integer�͵ĵ�ǰ����
	 * 
	 * @return
	 */
	public static Integer getIntToday() {
		return getIntDate(getNow());
	}

	/**
	 * ȡ��Integer�͵ĵ�ǰ���
	 * 
	 * @return
	 */
	public static Integer getIntYearNow() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		return year;
	}

	/**
	 * ȡ��Integer�͵ĵ�ǰ�·�
	 * 
	 * @return
	 */
	public static Integer getIntMonthNow() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		return month;
	}

	public static String getStringToday() {
		return getIntDate(getNow()) + "";
	}

	/**
	 * ���������ջ�ȡ��������
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Integer getIntDate(int year, int month, int day) {
		return getIntDate(newCalendar(year, month, day));
	}

	/**
	 * ĳ���µĵ�һ��
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getFirstDayOfMonth(int year, int month) {
		return getIntDate(newCalendar(year, month, 1));
	}

	/**
	 * ĳ���µĵ�һ��
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getFirstDayOfThisMonth() {
		Integer year = DateUtil.getIntYearNow();
		Integer month = DateUtil.getIntMonthNow();
		return getIntDate(newCalendar(year, month, 1));
	}

	/**
	 * ĳ���µĵ�һ��
	 * 
	 * @param date
	 * @return
	 * @time:2008-7-4 ����09:58:55
	 */
	public static Integer getFistDayOfMonth(Date date) {
		Integer intDate = getIntDate(date);
		int year = intDate / 10000;
		int month = intDate % 10000 / 100;
		return getIntDate(newCalendar(year, month, 1));
	}

	/**
	 * ĳ���µ����һ��
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getLastDayOfMonth(int year, int month) {
		return intDateSub(getIntDate(newCalendar(year, month + 1, 1)), 1);
	}

	/**
	 * ����Calendar��ȡ�������
	 * 
	 * @param c
	 * @return
	 */
	public static Integer getIntYear(Calendar c) {
		int year = c.get(Calendar.YEAR);
		return year;
	}

	/**
	 * ����Calendar��ȡ��������
	 * 
	 * @param c
	 * @return
	 */
	public static Integer getIntDate(Calendar c) {
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		return year * 10000 + month * 100 + day;
	}

	/**
	 * ����Date��ȡ�������
	 * 
	 * @param d
	 * @return
	 */
	public static Integer getIntYear(Date d) {
		if (d == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return getIntYear(c);
	}

	/**
	 * ����Date��ȡ��������
	 * 
	 * @param d
	 * @return
	 */
	public static Integer getIntDate(Date d) {
		if (d == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return getIntDate(c);
	}

	/**
	 * ����Integer��ȡDate����
	 * 
	 * @param n
	 * @return
	 */
	public static Date getDate(Integer n) {
		if (n == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.set(n / 10000, n / 100 % 100 - 1, n % 100);
		return c.getTime();
	}

	public static Date getDate1(String date) {
		if (date == null || date.length() == 0) {
			return null;
		}

		try {
			if (date.contains("/")) {
				date = date.replaceAll("/", "-");
			}
			return getFormatInstance(DATE_FORMAT).parse(date);
		} catch (ParseException e) {
			log.error("����[" + date + "]����", e);
			return null;
		}
	}

	/**
	 * �������Integer��ȡDate����
	 * 
	 * @param year
	 * @return
	 */
	public static Date getFirstDayOfYear(Integer year) {
		if (year == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.set(year, 1, 1);
		return c.getTime();
	}

	/**
	 * ��������������Calendar
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Calendar newCalendar(int year, int month, int day) {
		Calendar ret = Calendar.getInstance();
		if (year < 100) {
			year = 2000 + year;
		}
		ret.set(year, month - 1, day);
		return ret;
	}

	/**
	 * ����������������Calendar
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar newCalendar(int date) {
		int year = date / 10000;
		int month = (date % 10000) / 100;
		int day = date % 100;

		Calendar ret = Calendar.getInstance();
		ret.set(year, month - 1, day);
		return ret;
	}

	/**
	 * ȡ��Date�͵ĵ�ǰ����
	 * 
	 * @return
	 */
	public static Date getNow() {
		return new Date();
	}

	/**
	 * ȡ��Date�͵ĵ�ǰ����
	 * 
	 * @return
	 */
	public static Date getToday() {
		return DateUtil.getDate(DateUtil.getIntToday());
	}

	/**
	 * ���������ڵļӷ�
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Integer intDateAdd(int date, int days) {
		int year = date / 10000;
		int month = (date % 10000) / 100;
		int day = date % 100;

		day += days;

		return getIntDate(year, month, day);
	}

	/**
	 * ���������ڵļ���
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Integer intDateSub(int date, int days) {
		return intDateAdd(date, -days);
	}

	/**
	 * ����������������֮�������
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer daysBetweenDate(Integer startDate, Integer endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}
		Calendar c1 = newCalendar(startDate);
		Calendar c2 = newCalendar(endDate);

		Long lg = (c2.getTimeInMillis() - c1.getTimeInMillis()) / 1000 / 60
				/ 60 / 24;
		return lg.intValue();
	}

	/**
	 * ����������������֮�������
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer daysBetweenDate(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}
		Long interval = endDate.getTime() - startDate.getTime();
		interval = interval / (24 * 60 * 60 * 1000);
		return interval.intValue();
	}

	/**
	 * ȡ�õ�ǰ����.
	 * 
	 * @return ��ǰ����,�ַ�������.
	 */
	public static String getStringDate() {
		return getStringDate(DateUtil.getNow());
	}

	/**
	 * ����calendar�����ַ���������
	 * 
	 * @param d
	 * @return eg:20080707
	 */
	public static String getStringDate(Date d) {
		if (d == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(d);
	}

	public static String getFormatStringDate(Date d) {
		if (d == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		return sdf.format(d);
	}
	// /**
	// * ���ʻ���
	// */
	// public static String formatI18nDate(Date date){
	// if(date == null){
	// return "";
	// }
	// ActionSupport actionSupport = new ActionSupport();
	// SimpleDateFormat sdf = new
	// SimpleDateFormat(actionSupport.getText("date.i18n.format"));
	// return sdf.format(date);
	// }

	
	//-------------------------------------------------------------------------------------------------------
	
	 /** 
     * ��ȡSimpleDateFormat 
     * @param parttern ���ڸ�ʽ 
     * @return SimpleDateFormat���� 
     * @throws RuntimeException �쳣���Ƿ����ڸ�ʽ 
     */  
    private static SimpleDateFormat getDateFormat(String parttern) throws RuntimeException {  
        return new SimpleDateFormat(parttern);  
    }  
  
    /** 
     * ��ȡ�����е�ĳ��ֵ�����ȡ�·� 
     * @param date ���� 
     * @param dateType ���ڸ�ʽ 
     * @return ��ֵ 
     */  
    private static int getInteger(Date date, int dateType) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        return calendar.get(dateType);  
    }  
      
    /** 
     * ����������ĳ���͵�ĳ��ֵ������������ 
     * @param date �����ַ��� 
     * @param dateType ���� 
     * @param amount ��ֵ 
     * @return ����������ַ��� 
     */  
    private static String addInteger(String date, int dateType, int amount) {  
        String dateString = null;  
        DateStyle dateStyle = getDateStyle(date);  
        if (dateStyle != null) {  
            Date myDate = StringToDate(date, dateStyle);  
            myDate = addInteger(myDate, dateType, amount);  
            dateString = DateToString(myDate, dateStyle);  
        }  
        return dateString;  
    }  
      
    /** 
     * ����������ĳ���͵�ĳ��ֵ������������ 
     * @param date ���� 
     * @param dateType ���� 
     * @param amount ��ֵ 
     * @return ��������� 
     */  
    private static Date addInteger(Date date, int dateType, int amount) {  
        Date myDate = null;  
        if (date != null) {  
            Calendar calendar = Calendar.getInstance();  
            calendar.setTime(date);  
            calendar.add(dateType, amount);  
            myDate = calendar.getTime();  
        }  
        return myDate;  
    }  
  
    /** 
     * ��ȡ��ȷ������ 
     * @param timestamps ʱ��long���� 
     * @return ���� 
     */  
    private static Date getAccurateDate(List<Long> timestamps) {  
        Date date = null;  
        long timestamp = 0;  
        Map<Long, long[]> map = new HashMap<Long, long[]>();  
        List<Long> absoluteValues = new ArrayList<Long>();  
  
        if (timestamps != null && timestamps.size() > 0) {  
            if (timestamps.size() > 1) {  
                for (int i = 0; i < timestamps.size(); i++) {  
                    for (int j = i + 1; j < timestamps.size(); j++) {  
                        long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));  
                        absoluteValues.add(absoluteValue);  
                        long[] timestampTmp = { timestamps.get(i), timestamps.get(j) };  
                        map.put(absoluteValue, timestampTmp);  
                    }  
                }  
  
                // �п�������ȵ��������2012-11��2012-11-01��ʱ�������ȵ�  
                long minAbsoluteValue = -1;  
                if (!absoluteValues.isEmpty()) {  
                    // ���timestamps��sizeΪ2�����ǲ�ֵֻ��һ�������Ҫ��Ĭ��ֵ  
                    minAbsoluteValue = absoluteValues.get(0);  
                }  
                for (int i = 0; i < absoluteValues.size(); i++) {  
                    for (int j = i + 1; j < absoluteValues.size(); j++) {  
                        if (absoluteValues.get(i) > absoluteValues.get(j)) {  
                            minAbsoluteValue = absoluteValues.get(j);  
                        } else {  
                            minAbsoluteValue = absoluteValues.get(i);  
                        }  
                    }  
                }  
  
                if (minAbsoluteValue != -1) {  
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);  
                    if (absoluteValues.size() > 1) {  
                        timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);  
                    } else if (absoluteValues.size() == 1) {  
                        // ��timestamps��sizeΪ2����Ҫ�뵱ǰʱ����Ϊ����  
                        long dateOne = timestampsLastTmp[0];  
                        long dateTwo = timestampsLastTmp[1];  
                        if ((Math.abs(dateOne - dateTwo)) < 100000000000L) {  
                            timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);  
                        } else {  
                            long now = new Date().getTime();  
                            if (Math.abs(dateOne - now) <= Math.abs(dateTwo - now)) {  
                                timestamp = dateOne;  
                            } else {  
                                timestamp = dateTwo;  
                            }  
                        }  
                    }  
                }  
            } else {  
                timestamp = timestamps.get(0);  
            }  
        }  
  
        if (timestamp != 0) {  
            date = new Date(timestamp);  
        }  
        return date;  
    }  
  
    /** 
     * �ж��ַ����Ƿ�Ϊ�����ַ��� 
     * @param date �����ַ��� 
     * @return true or false 
     */  
    public static boolean isDate(String date) {  
        boolean isDate = false;  
        if (date != null) {  
            if (StringToDate(date) != null) {  
                isDate = true;  
            }  
        }  
        return isDate;  
    }  
  
    /** 
     * ��ȡ�����ַ��������ڷ��ʧ������null�� 
     * @param date �����ַ��� 
     * @return ���ڷ�� 
     */  
    public static DateStyle getDateStyle(String date) {  
        DateStyle dateStyle = null;  
        Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();  
        List<Long> timestamps = new ArrayList<Long>();  
        for (DateStyle style : DateStyle.values()) {  
            Date dateTmp = StringToDate(date, style.getValue());  
            if (dateTmp != null) {  
                timestamps.add(dateTmp.getTime());  
                map.put(dateTmp.getTime(), style);  
            }  
        }  
        dateStyle = map.get(getAccurateDate(timestamps).getTime());  
        return dateStyle;  
    }  
  
    /** 
     * �������ַ���ת��Ϊ���ڡ�ʧ�ܷ���null�� 
     * @param date �����ַ��� 
     * @return ���� 
     */  
    public static Date StringToDate(String date) {  
        DateStyle dateStyle = null;  
        return StringToDate(date, dateStyle);  
    }  
  
    /** 
     * �������ַ���ת��Ϊ���ڡ�ʧ�ܷ���null�� 
     * @param date �����ַ��� 
     * @param parttern ���ڸ�ʽ 
     * @return ���� 
     */  
    public static Date StringToDate(String date, String parttern) {  
        Date myDate = null;  
        if (date != null) {  
            try {  
                myDate = getDateFormat(parttern).parse(date);  
            } catch (Exception e) {  
            }  
        }  
        return myDate;  
    }  
  
    /** 
     * �������ַ���ת��Ϊ���ڡ�ʧ�ܷ���null�� 
     * @param date �����ַ��� 
     * @param dateStyle ���ڷ�� 
     * @return ���� 
     */  
    public static Date StringToDate(String date, DateStyle dateStyle) {  
        Date myDate = null;  
        if (dateStyle == null) {  
            List<Long> timestamps = new ArrayList<Long>();  
            for (DateStyle style : DateStyle.values()) {  
                Date dateTmp = StringToDate(date, style.getValue());  
                if (dateTmp != null) {  
                    timestamps.add(dateTmp.getTime());  
                }  
            }  
            myDate = getAccurateDate(timestamps);  
        } else {  
            myDate = StringToDate(date, dateStyle.getValue());  
        }  
        return myDate;  
    }  
  
    /** 
     * ������ת��Ϊ�����ַ�����ʧ�ܷ���null�� 
     * @param date ���� 
     * @param parttern ���ڸ�ʽ 
     * @return �����ַ��� 
     */  
    public static String DateToString(Date date, String parttern) {  
        String dateString = null;  
        if (date != null) {  
            try {  
                dateString = getDateFormat(parttern).format(date);  
            } catch (Exception e) {  
            }  
        }  
        return dateString;  
    }  
  
    /** 
     * ������ת��Ϊ�����ַ�����ʧ�ܷ���null�� 
     * @param date ���� 
     * @param dateStyle ���ڷ�� 
     * @return �����ַ��� 
     */  
    public static String DateToString(Date date, DateStyle dateStyle) {  
        String dateString = null;  
        if (dateStyle != null) {  
            dateString = DateToString(date, dateStyle.getValue());  
        }  
        return dateString;  
    }  
  
    /** 
     * �������ַ���ת��Ϊ��һ�����ַ�����ʧ�ܷ���null�� 
     * @param date �������ַ��� 
     * @param parttern �����ڸ�ʽ 
     * @return �������ַ��� 
     */  
    public static String StringToString(String date, String parttern) {  
        return StringToString(date, null, parttern);  
    }  
  
    /** 
     * �������ַ���ת��Ϊ��һ�����ַ�����ʧ�ܷ���null�� 
     * @param date �������ַ��� 
     * @param dateStyle �����ڷ�� 
     * @return �������ַ��� 
     */  
    public static String StringToString(String date, DateStyle dateStyle) {  
        return StringToString(date, null, dateStyle);  
    }  
  
    /** 
     * �������ַ���ת��Ϊ��һ�����ַ�����ʧ�ܷ���null�� 
     * @param date �������ַ��� 
     * @param olddParttern �����ڸ�ʽ 
     * @param newParttern �����ڸ�ʽ 
     * @return �������ַ��� 
     */  
    public static String StringToString(String date, String olddParttern, String newParttern) {  
        String dateString = null;  
        if (olddParttern == null) {  
            DateStyle style = getDateStyle(date);  
            if (style != null) {  
                Date myDate = StringToDate(date, style.getValue());  
                dateString = DateToString(myDate, newParttern);  
            }  
        } else {  
            Date myDate = StringToDate(date, olddParttern);  
            dateString = DateToString(myDate, newParttern);  
        }  
        return dateString;  
    }  
  
    /** 
     * �������ַ���ת��Ϊ��һ�����ַ�����ʧ�ܷ���null�� 
     * @param date �������ַ��� 
     * @param olddDteStyle �����ڷ�� 
     * @param newDateStyle �����ڷ�� 
     * @return �������ַ��� 
     */  
    public static String StringToString(String date, DateStyle olddDteStyle, DateStyle newDateStyle) {  
        String dateString = null;  
        if (olddDteStyle == null) {  
            DateStyle style = getDateStyle(date);  
            dateString = StringToString(date, style.getValue(), newDateStyle.getValue());  
        } else {  
            dateString = StringToString(date, olddDteStyle.getValue(), newDateStyle.getValue());  
        }  
        return dateString;  
    }  
  
    /** 
     * �������ڵ���ݡ�ʧ�ܷ���null�� 
     * @param date ���� 
     * @param yearAmount ������������Ϊ���� 
     * @return ������ݺ�������ַ��� 
     */  
    public static String addYear(String date, int yearAmount) {  
        return addInteger(date, Calendar.YEAR, yearAmount);  
    }  
      
    /** 
     * �������ڵ���ݡ�ʧ�ܷ���null�� 
     * @param date ���� 
     * @param yearAmount ������������Ϊ���� 
     * @return ������ݺ������ 
     */  
    public static Date addYear(Date date, int yearAmount) {  
        return addInteger(date, Calendar.YEAR, yearAmount);  
    }  
      
    /** 
     * �������ڵ��·ݡ�ʧ�ܷ���null�� 
     * @param date ���� 
     * @param yearAmount ������������Ϊ���� 
     * @return �����·ݺ�������ַ��� 
     */  
    public static String addMonth(String date, int yearAmount) {  
        return addInteger(date, Calendar.MONTH, yearAmount);  
    }  
      
    /** 
     * �������ڵ��·ݡ�ʧ�ܷ���null�� 
     * @param date ���� 
     * @param yearAmount ������������Ϊ���� 
     * @return �����·ݺ������ 
     */  
    public static Date addMonth(Date date, int yearAmount) {  
        return addInteger(date, Calendar.MONTH, yearAmount);  
    }  
      
    /** 
     * �������ڵ�������ʧ�ܷ���null�� 
     * @param date �����ַ��� 
     * @param dayAmount ������������Ϊ���� 
     * @return ����������������ַ��� 
     */  
    public static String addDay(String date, int dayAmount) {  
        return addInteger(date, Calendar.DATE, dayAmount);  
    }  
  
    /** 
     * �������ڵ�������ʧ�ܷ���null�� 
     * @param date ���� 
     * @param dayAmount ������������Ϊ���� 
     * @return ��������������� 
     */  
    public static Date addDay(Date date, int dayAmount) {  
        return addInteger(date, Calendar.DATE, dayAmount);  
    }  
      
    /** 
     * �������ڵ�Сʱ��ʧ�ܷ���null�� 
     * @param date �����ַ��� 
     * @param dayAmount ������������Ϊ���� 
     * @return ����Сʱ��������ַ��� 
     */  
    public static String addHour(String date, int hourAmount) {  
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);  
    }  
  
    /** 
     * �������ڵ�Сʱ��ʧ�ܷ���null�� 
     * @param date ���� 
     * @param dayAmount ������������Ϊ���� 
     * @return ����Сʱ������� 
     */  
    public static Date addHour(Date date, int hourAmount) {  
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);  
    }  
      
    /** 
     * �������ڵķ��ӡ�ʧ�ܷ���null�� 
     * @param date �����ַ��� 
     * @param dayAmount ������������Ϊ���� 
     * @return ���ӷ��Ӻ�������ַ��� 
     */  
    public static String addMinute(String date, int hourAmount) {  
        return addInteger(date, Calendar.MINUTE, hourAmount);  
    }  
  
    /** 
     * �������ڵķ��ӡ�ʧ�ܷ���null�� 
     * @param date ���� 
     * @param dayAmount ������������Ϊ���� 
     * @return ���ӷ��Ӻ������ 
     */  
    public static Date addMinute(Date date, int hourAmount) {  
        return addInteger(date, Calendar.MINUTE, hourAmount);  
    }  
      
    /** 
     * �������ڵ����ӡ�ʧ�ܷ���null�� 
     * @param date �����ַ��� 
     * @param dayAmount ������������Ϊ���� 
     * @return �������Ӻ�������ַ��� 
     */  
    public static String addSecond(String date, int hourAmount) {  
        return addInteger(date, Calendar.SECOND, hourAmount);  
    }  
  
    /** 
     * �������ڵ����ӡ�ʧ�ܷ���null�� 
     * @param date ���� 
     * @param dayAmount ������������Ϊ���� 
     * @return �������Ӻ������ 
     */  
    public static Date addSecond(Date date, int hourAmount) {  
        return addInteger(date, Calendar.SECOND, hourAmount);  
    }  
  
    /** 
     * ��ȡ���ڵ���ݡ�ʧ�ܷ���0�� 
     * @param date �����ַ��� 
     * @return ��� 
     */  
    public static int getYear(String date) {  
        return getYear(StringToDate(date));  
    }  
  
    /** 
     * ��ȡ���ڵ���ݡ�ʧ�ܷ���0�� 
     * @param date ���� 
     * @return ��� 
     */  
    public static int getYear(Date date) {  
        return getInteger(date, Calendar.YEAR);  
    }  
  
    /** 
     * ��ȡ���ڵ��·ݡ�ʧ�ܷ���0�� 
     * @param date �����ַ��� 
     * @return �·� 
     */  
    public static int getMonth(String date) {  
        return getMonth(StringToDate(date));  
    }  
  
    /** 
     * ��ȡ���ڵ��·ݡ�ʧ�ܷ���0�� 
     * @param date ���� 
     * @return �·� 
     */  
    public static int getMonth(Date date) {  
        return getInteger(date, Calendar.MONTH);  
    }  
  
    /** 
     * ��ȡ���ڵ�������ʧ�ܷ���0�� 
     * @param date �����ַ��� 
     * @return �� 
     */  
    public static int getDay(String date) {  
        return getDay(StringToDate(date));  
    }  
  
    /** 
     * ��ȡ���ڵ�������ʧ�ܷ���0�� 
     * @param date ���� 
     * @return �� 
     */  
    public static int getDay(Date date) {  
        return getInteger(date, Calendar.DATE);  
    }  
      
    /** 
     * ��ȡ���ڵ�Сʱ��ʧ�ܷ���0�� 
     * @param date �����ַ��� 
     * @return Сʱ 
     */  
    public static int getHour(String date) {  
        return getHour(StringToDate(date));  
    }  
  
    /** 
     * ��ȡ���ڵ�Сʱ��ʧ�ܷ���0�� 
     * @param date ���� 
     * @return Сʱ 
     */  
    public static int getHour(Date date) {  
        return getInteger(date, Calendar.HOUR_OF_DAY);  
    }  
      
    /** 
     * ��ȡ���ڵķ��ӡ�ʧ�ܷ���0�� 
     * @param date �����ַ��� 
     * @return ���� 
     */  
    public static int getMinute(String date) {  
        return getMinute(StringToDate(date));  
    }  
  
    /** 
     * ��ȡ���ڵķ��ӡ�ʧ�ܷ���0�� 
     * @param date ���� 
     * @return ���� 
     */  
    public static int getMinute(Date date) {  
        return getInteger(date, Calendar.MINUTE);  
    }  
      
    /** 
     * ��ȡ���ڵ����ӡ�ʧ�ܷ���0�� 
     * @param date �����ַ��� 
     * @return ���� 
     */  
    public static int getSecond(String date) {  
        return getSecond(StringToDate(date));  
    }  
  
    /** 
     * ��ȡ���ڵ����ӡ�ʧ�ܷ���0�� 
     * @param date ���� 
     * @return ���� 
     */  
    public static int getSecond(Date date) {  
        return getInteger(date, Calendar.SECOND);  
    }  
  
    /** 
     * ��ȡ���� ��Ĭ��yyyy-MM-dd��ʽ��ʧ�ܷ���null�� 
     * @param date �����ַ��� 
     * @return ���� 
     */  
    public static String getDate(String date) {  
        return StringToString(date, DateStyle.YYYY_MM_DD);  
    }  
  
    /** 
     * ��ȡ���ڡ�Ĭ��yyyy-MM-dd��ʽ��ʧ�ܷ���null�� 
     * @param date ���� 
     * @return ���� 
     */  
    public static String getDate(Date date) {  
        return DateToString(date, DateStyle.YYYY_MM_DD);  
    }  
  
    /** 
     * ��ȡ���ڵ�ʱ�䡣Ĭ��HH:mm:ss��ʽ��ʧ�ܷ���null�� 
     * @param date �����ַ��� 
     * @return ʱ�� 
     */  
    public static String getTime(String date) {  
        return StringToString(date, DateStyle.HH_MM_SS);  
    }  
  
    /** 
     * ��ȡ���ڵ�ʱ�䡣Ĭ��HH:mm:ss��ʽ��ʧ�ܷ���null�� 
     * @param date ���� 
     * @return ʱ�� 
     */  
    public static String getTime(Date date) {  
        return DateToString(date, DateStyle.HH_MM_SS);  
    }  
  
    /** 
     * ��ȡ���ڵ����ڡ�ʧ�ܷ���null�� 
     * @param date �����ַ��� 
     * @return ���� 
     */  
    public static Week getWeek(String date) {  
        Week week = null;  
        DateStyle dateStyle = getDateStyle(date);  
        if (dateStyle != null) {  
            Date myDate = StringToDate(date, dateStyle);  
            week = getWeek(myDate);  
        }  
        return week;  
    }  
  
    /** 
     * ��ȡ���ڵ����ڡ�ʧ�ܷ���null�� 
     * @param date ���� 
     * @return ���� 
     */  
    public static Week getWeek(Date date) {  
        Week week = null;  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;  
        switch (weekNumber) {  
        case 0:  
            week = Week.SUNDAY;  
            break;  
        case 1:  
            week = Week.MONDAY;  
            break;  
        case 2:  
            week = Week.TUESDAY;  
            break;  
        case 3:  
            week = Week.WEDNESDAY;  
            break;  
        case 4:  
            week = Week.THURSDAY;  
            break;  
        case 5:  
            week = Week.FRIDAY;  
            break;  
        case 6:  
            week = Week.SATURDAY;  
            break;  
        }  
        return week;  
    }  
      
    /** 
     * ��ȡ���������������� 
     * @param date �����ַ��� 
     * @param otherDate ��һ�������ַ��� 
     * @return ������� 
     */  
    public static int getIntervalDays(String date, String otherDate) {  
        return getIntervalDays(StringToDate(date), StringToDate(otherDate));  
    }  
      
    /** 
     * @param date ���� 
     * @param otherDate ��һ������ 
     * @return ������� 
     */  
    public static int getIntervalDays(Date date, Date otherDate) {  
        date = DateUtil.StringToDate(DateUtil.getDate(date));  
        long time = Math.abs(date.getTime() - otherDate.getTime());  
        return (int)time/(24 * 60 * 60 * 1000);  
    }  
    
    /**
     * ��������ö��
     * @author user
     *
     */
    public enum DateStyle {  
        
        MM_DD("MM-dd"),  
        YYYY_MM("yyyy-MM"),  
        YYYY_MM_DD("yyyy-MM-dd"),  
        MM_DD_HH_MM("MM-dd HH:mm"),  
        MM_DD_HH_MM_SS("MM-dd HH:mm:ss"),  
        YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),  
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),  
          
        MM_DD_EN("MM/dd"),  
        YYYY_MM_EN("yyyy/MM"),  
        YYYY_MM_DD_EN("yyyy/MM/dd"),  
        MM_DD_HH_MM_EN("MM/dd HH:mm"),  
        MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss"),  
        YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm"),  
        YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss"),  
          
        MM_DD_CN("MM��dd��"),  
        YYYY_MM_CN("yyyy��MM��"),  
        YYYY_MM_DD_CN("yyyy��MM��dd��"),  
        MM_DD_HH_MM_CN("MM��dd�� HH:mm"),  
        MM_DD_HH_MM_SS_CN("MM��dd�� HH:mm:ss"),  
        YYYY_MM_DD_HH_MM_CN("yyyy��MM��dd�� HH:mm"),  
        YYYY_MM_DD_HH_MM_SS_CN("yyyy��MM��dd�� HH:mm:ss"),  
          
        HH_MM("HH:mm"),  
        HH_MM_SS("HH:mm:ss");  
          
          
        private String value;  
          
        DateStyle(String value) {  
            this.value = value;  
        }  
          
        public String getValue() {  
            return value;  
        }  
    }  

    /**
     * ���� ö��
     * @author user
     *
     */
    public enum Week {  
      
        MONDAY("����һ", "Monday", "Mon.", 1),  
        TUESDAY("���ڶ�", "Tuesday", "Tues.", 2),  
        WEDNESDAY("������", "Wednesday", "Wed.", 3),  
        THURSDAY("������", "Thursday", "Thur.", 4),  
        FRIDAY("������", "Friday", "Fri.", 5),  
        SATURDAY("������", "Saturday", "Sat.", 6),  
        SUNDAY("������", "Sunday", "Sun.", 7);  
          
        String name_cn;  
        String name_en;  
        String name_enShort;  
        int number;  
          
        Week(String name_cn, String name_en, String name_enShort, int number) {  
            this.name_cn = name_cn;  
            this.name_en = name_en;  
            this.name_enShort = name_enShort;  
            this.number = number;  
        }  
          
        public String getChineseName() {  
            return name_cn;  
        }  
      
        public String getName() {  
            return name_en;  
        }  
      
        public String getShortName() {  
            return name_enShort;  
        }  
      
        public int getNumber() {  
            return number;  
        } 
    }
}  
