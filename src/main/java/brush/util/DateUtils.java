package brush.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	/**
	 * 获取当天日期
	 * 
	 * @return yyyyMMdd
	 */
	public static String getTodayYMD() {
		Date today = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String todayymd = sf.format(today);
		return todayymd;
	}

	/**
	 * 根据输入的字符串生成相应时间精确到秒
	 * 
	 * @param date
	 *            （yyyymmdd HH:mi:ss）
	 * @return Date
	 */
	public static Date getTime(String time, String format) {

		SimpleDateFormat sf = new SimpleDateFormat(format);
		Date dateformat = null;
		try {
			dateformat = sf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateformat;
	}

	/**
	 * 根据输入的字符串生成相应时间精确到秒
	 * 
	 * @param date
	 *            （yyyymmdd HH:mi:ss）
	 * @return Date
	 */
	public static Date getTime(String time) {
		return getTime(time, "yyyy-MM-dd HH:mm:ss");
	}
}
