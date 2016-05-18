package brush.entity;

import java.util.Date;

import brush.util.XStreamDateTimeConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("dateStrategy")
public class DateStrategy {
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@XStreamConverter(value=XStreamDateTimeConverter.class)
	private Date startTime;
	@XStreamConverter(value=XStreamDateTimeConverter.class)
	private Date endTime;
	private long total;
	private long totalPerDay;
	public long getTotalPerDay() {
		return totalPerDay;
	}

	public void setTotalPerDay(long totalPerDay) {
		this.totalPerDay = totalPerDay;
	}
}
