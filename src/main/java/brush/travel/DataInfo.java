package brush.travel;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import brush.entity.ProxyInfo;
import brush.entity.Strategy;
import brush.entity.WtInfo;
import brush.util.XMLParserXStreamImpl;

public class DataInfo {
	private static final Logger logger = Logger.getLogger(DataInfo.class);
	public static Strategy strategy;
	public static List<ProxyInfo> proxyInfo;
	public static List<ProxyInfo> validProxyInfo;
	public static List<WtInfo> wtInfo;

	public static void logDataInfo() {
		logger.debug("proxyInfo=" + proxyInfo);
		logger.debug("validProxyInfo=" + validProxyInfo);
		logger.debug("wtInfo=" + wtInfo);
		try {
			logger.debug("strategy="
					+ XMLParserXStreamImpl.toXMLStringAppendXmlHead(strategy));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
