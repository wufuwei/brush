package brush.travel;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import brush.entity.ProxyInfo;
import brush.strategy.Read;
import brush.util.ServiceUtils;

public class ProxyCheck implements java.lang.Runnable {
	private static final Logger logger = Logger.getLogger(ProxyCheck.class);
	private static final long checkTime = 30;
	private static boolean firstTime=true;

	public List<ProxyInfo> checkProxyInfo(List<ProxyInfo> list) {
		List<ProxyInfo> temp = new ArrayList<ProxyInfo>();
		for (ProxyInfo proxyInfo : list) {
			if (ServiceUtils.checkServerAvaliable(proxyInfo.getIp(),
					proxyInfo.getPort())) {
				temp.add(proxyInfo);
				if (firstTime) {
					DataInfo.validProxyInfo = temp;
					firstTime=false;
				}
			}
		}
		return temp;
	}

	public void run() {
		for (;;) {
			List<ProxyInfo> list = DataInfo.proxyInfo;
			if (list != null && list.size() > 0) {
				List<ProxyInfo> valid = checkProxyInfo(list);
				if (valid != null && valid.size() > 0&&!firstTime) {
					DataInfo.validProxyInfo = valid;
				}
				logger.debug("DataInfo.validProxyInfo="+ DataInfo.validProxyInfo);
			}
			try {
				Thread.currentThread().sleep(checkTime * 60 * 1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
