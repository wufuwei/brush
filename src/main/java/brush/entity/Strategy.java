package brush.entity;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("strategy")
public class Strategy {
	public DateStrategy getDateStrategy() {
		return dateStrategy;
	}
	public void setDateStrategy(DateStrategy dateStrategy) {
		this.dateStrategy = dateStrategy;
	}
	public List<SendStrategy> getSendStrategys() {
		return sendStrategys;
	}
	public void setSendStrategys(List<SendStrategy> sendStrategys) {
		this.sendStrategys = sendStrategys;
	}
	private DateStrategy dateStrategy;
	@XStreamAlias("sendStrategys")
	private List<SendStrategy> sendStrategys;
	
	private int useProxy;

	public int getUseProxy() {
		return useProxy;
	}
	public void setUseProxy(int useProxy) {
		this.useProxy = useProxy;
	}
	
}
