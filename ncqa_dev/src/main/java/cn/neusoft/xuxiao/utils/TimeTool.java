package cn.neusoft.xuxiao.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;

@Component
public class TimeTool {

	public static final String PATTERN_YM = "yyyy-MM";
	
	public static final String PATTERN_YMD1 = "yyyyMMdd";
	
	public static final String PATTERN_YMD2 = "yyyy-MM-dd";
	
	public static final String PATTERN_YMD3 = "yyyy/MM/dd";
	
	public static final String PATTERN_YMDH = "yyyyMMddHH";
	
	public static final String PATTERN_YMDHM = "yyyyMMddHHmm";
	
	public static final String PATTERN_YMDHMS1 = "yyyy-MM-dd HH:mm:ss";

	public static final String PATTERN_YMDHMS2 = "yyyyMMddHHmmss";

	public static final String PATTERN_YMDHMS3 = "yyyy-MM-dd HH:mm";

	public static final String PATTERN_HMS = "HH:mm:ss";

	/**
	 * 根据时间格式获取对应的时间格式化工具
	 * 
	 * @author LuoDongXu 
	 * @date 2018年6月26日 下午4:24:04
	 * @param pattern
	 * @return
	 */
    private static SimpleDateFormat getSimpleDateFormat(String pattern) {
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    	return sdf;
    }	
    
	/**
	 * 周末判断
	 * @param date
	 * @return
	 * @date 2018年1月31日 下午5:43:41
	 * @author yangqiang
	 */
	public static boolean isWeekEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		if (weekday == 1 || weekday == 7) {
			return true;
		}
		return false;
	}

	/**
	 * 获取小时
	 * @param date
	 * @return
	 * @date 2018年1月31日 下午5:43:30
	 * @author yangqiang
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取分钟
	 * @param date
	 * @return
	 * @date 2018年1月31日 下午5:43:15
	 * @author yangqiang
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 获取年份
	 * @param d
	 * @return
	 * @date 2018年1月31日 下午5:42:50
	 * @author yangqiang
	 */
	public static int getDateInt(Date d) {
		String dstr = getSimpleDateFormat(PATTERN_YMD1).format(d);
		return Integer.parseInt(dstr);
	}

	/**
	 * 获取时间毫秒
	 * @param d
	 * @return
	 * @date 2018年1月31日 下午5:42:12
	 * @author yangqiang
	 */
	public static long getDateMill(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return cal.getTimeInMillis();
	}

	/**
	 * 标准时间格式转换
	 * @param millSec
	 * @return
	 * @date 2018年1月31日 下午5:41:54
	 * @author yangqiang
	 */
	public static String transferLongToDate(Long millSec) {
		Date date = new Date(millSec);
		return getSimpleDateFormat(PATTERN_YMDHMS1).format(date);
	}

	/**
	 * 获取当前时间
	 * @return
	 * @date 2018年1月31日 下午5:41:38
	 * @author yangqiang
	 */
	public static String getTodayTime() {
		Date d = new Date();
		return getSimpleDateFormat(PATTERN_YMD1).format(d);
	}

	/**
	 * 时间转字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param d
	 * @return
	 * @date 2018年1月31日 下午5:40:54
	 * @author yangqiang
	 */
	public static String DateToString(Date d) {
		if (d != null) {
			return getSimpleDateFormat(PATTERN_YMDHMS1).format(d);
		} else {
			return "";
		}
	}

	/**
	 * 时间转字符串 yyyy-MM-dd HH:mm
	 *
	 * @param d
	 * @return
	 * @date 2018年1月31日 下午5:40:54
	 * @author yang.yi
	 */
	public static String DateToShortString2(Date d) {
		if (d != null) {
			return getSimpleDateFormat(PATTERN_YMDHMS3).format(d);
		} else {
			return "";
		}
	}

	/**
	 * 时间转字符串HH:mm:ss
	 * 
	 * @param d
	 * @return
	 * @date 2018年1月31日 下午5:40:24
	 * @author yangqiang
	 */
	public static String TimeToString(Date d) {
		if (d != null) {
			return getSimpleDateFormat(PATTERN_HMS).format(d);
		} else {
			return "";
		}
	}

	/**
	 * 时间转字符串yyyy-MM-dd
	 * 
	 * @param f
	 * @return
	 * @date 2018年1月31日 下午5:39:43
	 * @author yangqiang
	 */
	public static String DateToShortString(Date f) {
		if (f != null) {
			return getSimpleDateFormat(PATTERN_YMD2).format(f);
		} else {
			return "";
		}
	}

	/**
	 * 时间转字符串 yyyy-MM
	 */
	public static String DateToStringMini(Date f){
		if(f != null){
			return getSimpleDateFormat(PATTERN_YM).format(f);
		}else{
			return "";
		}
	}

	/**
	 * 计算今天是多少周
	 * @return
	 */
	public static int getWeekOfYear(){
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);//将周一设为一周的开始，符合中国的习惯
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 字符串转时间
	 * 
	 * @param str
	 * @return
	 * @date 2018年1月31日 下午5:38:19
	 * @author yangqiang
	 */
	public static Date StrToDate(String str) {
		Date date = null;
		int len = str.length();
		if (str.indexOf('-') > 0 && len == 19) {// "yyyy-MM-dd HH:mm:ss"
			try {
				date = getSimpleDateFormat(PATTERN_YMDHMS1).parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
        } else if (str.indexOf('-') > 0 && len == 7) {
            try {
                date = getSimpleDateFormat(PATTERN_YM).parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (str.indexOf('-') > 0 && (8 <= len && len <= 10)) {// "yyyy-MM-dd"
			try {
				date = getSimpleDateFormat(PATTERN_YMD2).parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (str.indexOf('/') > 0 && (8 <= len && len <= 10)) {// "yyyy/MM/dd"
			try {
				date = getSimpleDateFormat(PATTERN_YMD3).parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (len == 14) {// "yyyyMMddHHmmss"
			try {
				date = getSimpleDateFormat(PATTERN_YMDHMS2).parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (len == 8) {// yyyymmdd
			try {
				date = getSimpleDateFormat(PATTERN_YMD1).parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (len == 12) {// yyyyMMddHHmm
			try {
				date = getSimpleDateFormat(PATTERN_YMDHM).parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 根据当前日期按月推算时间
	 * 
	 * @param days
	 * @return
	 * @date 2017年9月21日 下午5:58:52
	 * @author yang
	 */
	public static String addMonthTime(int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, months);
		return getSimpleDateFormat(PATTERN_YMDHMS1).format(calendar.getTime());
	}

	/**
	 * 根据指定日期按月推算时间
	 * 
	 * @param date
	 * @param months
	 * @return
	 * @date 2017年9月21日 下午5:58:52
	 * @author yang
	 */
	public static Date addMonthTime(Date date, int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}

	/**
	 * 根据当前日期按日推算时间
	 * 
	 * @param days
	 * @return
	 * @date 2017年9月21日 下午5:58:52
	 * @author yang
	 */
	public static String addDateTime(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, days);
		return getSimpleDateFormat(PATTERN_YMDHMS1).format(calendar.getTime());
	}
	
	/**
	 * 
	 * addDateTime: date基础上增加天数days，返回时间字符串
	 *
	 * @param date
	 * @param days
	 * @return
	 * @date 2018年3月28日 下午3:49:41
	 * @author yang.yi
	 */
	public static String addDateTime(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return getSimpleDateFormat(PATTERN_YMDHMS1).format(calendar.getTime());
	}
	
	/**
	 * date基础上增加天数days，返回指定格式的字符串
	 * 
	 * @author LuoDongXu 
	 * @date 2018年6月21日 下午3:51:22
	 * @param date
	 * @param days
	 * @param
	 * @return
	 */
	public static String addDateTime(Date date, int days, String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return getSimpleDateFormat(pattern).format(calendar.getTime());
	}
	
	/**
	 * date基础上增加天数days，返回指定格式的字符串
	 * 
	 * @author LuoDongXu 
	 * @date 2018年6月21日 下午4:13:59
	 * @param date
	 * @param days
	 * @param format
	 * @return
	 */
	public static String addDateTime(String date, int days, String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(StrToDate(date));
		calendar.add(Calendar.DATE, days);
		return getSimpleDateFormat(pattern).format(calendar.getTime());
	}
	
	/**
	 * date基础上增加天数days，返回指定格式的字符串
	 * 
	 * @author LuoDongXu 
	 * @date 2018年6月21日 下午4:13:59
	 * @param date
	 * @param days
	 * @return
	 */
	public static String addDateTime(String date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(StrToDate(date));
		calendar.add(Calendar.DATE, days);
		return getSimpleDateFormat(PATTERN_YMD2).format(calendar.getTime());
	}
	
	/**
	 * 将毫秒转换为字符串时间
	 *
	 * @param millis
	 * @return
	 * @date 2018年3月21日 上午11:26:22
	 * @author yang
	 */
	public static String millisToDateStr(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		Date date = calendar.getTime();
		return DateToString(date);
	}
	
	/**
	 * 将日期转换成指定格式
	 * @author LuoDongXu
	 * @date 2018年6月6日 下午5:21:29
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
	    return getSimpleDateFormat(pattern).format(date);
	}


	/**
	 * validateDate:该方法旨在判断指定的字符串是否符合特定的时间格式，通过异常捕获的方式进行判断
	 *
	 * @param date 时间字符串
	 * @param pattern 格式
	 * @return boolean 判断结果
	 * @author xuweicheng
	 * @date  2018/8/28
	*/
	public static boolean validateDate(String date ,String pattern){
		boolean result = true;
		//应用指定格式
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			//设置时间日期转化的窄限制
			format.setLenient(false);
			format.parse(date);
		} catch (ParseException e) {
			result = false;
		}
		return result;
	}
}