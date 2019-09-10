package cn.com.zhsz.eco.util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 时间工具类
 * 
 * @author qss
 *
 */
public class DateUtils {

	public static SimpleDateFormat mm_dd_hh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat mmddhh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat mm_dd = new SimpleDateFormat("yyyy-MM-dd");

	public static SimpleDateFormat mmdd = new SimpleDateFormat("yyyyMMdd");

	public static SimpleDateFormat mm_ = new SimpleDateFormat("yyyy-MM");

	public static SimpleDateFormat mm = new SimpleDateFormat("yyyyMM");

	public static SimpleDateFormat job = new SimpleDateFormat("ss mm HH dd MM ? yyyy");

	/**
	 * 时间戳转化为时间
	 * @param time
	 * @return
	 */
	public static Date getJobDate(String time) {
		try {
			return job.parse(time);
		} catch (ParseException e) {
		}
		return new Date();
	}

	/**
	 * 时间戳转化为时间
	 * 
	 * @param time
	 * @return
	 */
	public static String timestampToTime(String time) {

		Long l = Long.parseLong(time) * 1000L;// 转化为long类型

		String date = mm_dd_hh.format(new Date(l));

		return date;
	}

	/**
	 * 显示日期格式
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String formsat(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 获取指定时间为星期几，如果指定时间不存在，使用当前时间。
	 *
	 * 根据中国的星期来排序：1-7 分别表示周一至周日
	 *
	 * @param datestr
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static Integer getDayOfWeek(String datestr) {
		Integer week = null;
		try {
			Date date = null;

			// 指定时间不存在，使用当前时间
			if (StringUtils.isNotEmpty(datestr)) {
				date = mm_dd_hh.parse(datestr);
			} else {
				date = new Date();
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			week = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (week == 0) {
				week = 7;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return week;
	}

	/**
	 * 获得固定格式的系统当前时间的日期
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String getSystemCurrData() {
		Date date = new Date();
		return mm_dd_hh.format(date);
	}

	/**
	 * 根据当前日期获得所在周的日期区间（周一和周日日期）
	 * @param
	 * @return
	 */
	public static Map<String,String> getTimeInterval() {  
	     Calendar cal = Calendar.getInstance();  
	     cal.setTime(new Date());  
	     // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
	     int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
	     if (1 == dayWeek) {  
	        cal.add(Calendar.DAY_OF_MONTH, -1);  
	     }  
	     // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期  
	     // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
	     cal.setFirstDayOfWeek(Calendar.MONDAY);  
	     // 获得当前日期是一个星期的第几天  
	     int day = cal.get(Calendar.DAY_OF_WEEK);  
	     // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
	     cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);  
	     String imptimeBegin = mm_dd.format(cal.getTime());
	     // System.out.println("所在周星期一的日期：" + imptimeBegin);  
	     cal.add(Calendar.DATE, 6);  
	     String imptimeEnd = mm_dd.format(cal.getTime());
	     // System.out.println("所在周星期日的日期：" + imptimeEnd);  
	     Map<String,String> map = new HashMap<String,String>();
	     map.put("imptimeBegin", imptimeBegin);
	     map.put("imptimeEnd", imptimeEnd);
	     return map;  
	}
	
	/**
	 * 获取当前时间所在月的第一天和最后一天
	 * @return
	 */
	public static Map<String,String> getTimeMonth(){
		Map<String,String> map = new HashMap<String,String>();
		Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String firstTime = mm_dd.format(c.getTime());
        map.put("firstTime", firstTime);
        
       
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String lastTime = mm_dd.format(ca.getTime());
        map.put("lastTime", lastTime);	
		return map;  
	}

	public static Date getDate(String startTaskTime) throws ParseException {
		return mm_dd_hh.parse(startTaskTime);
	}

	public static String getDate(Date startTaskTime){
		if(startTaskTime == null){
			return "";
		}
		return mm_dd_hh.format(startTaskTime);
	}

	/**
	 * 格式化流程处理时间为*天*小时
	 * @return
	 * @author 郑建超
	 * @Time 2017年3月21日
	 * @version 1.0v
	 */
	public static String getMinutes(Long minutes){
		//获取天数,一天等于86400000毫秒
		int t = (int) (minutes/86400000);
		minutes = minutes%86400000;
		//获取小时数,一小时等于3600000毫秒
		int s = (int) (minutes/3600000);
		return t+"天"+s+"小时";
	}
}
