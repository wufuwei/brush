package brush.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("sendStrategy")
public class SendStrategy {
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public float getPercent() {
		return percent;
	}
	public void setPercent(float percent) {
		this.percent = percent;
	}
	public long getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
	private String fromTime;
	private String toTime;
	private float percent;
	private long sleepTime;
}
