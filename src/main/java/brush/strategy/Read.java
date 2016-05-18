package brush.strategy;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import brush.entity.ProxyInfo;
import brush.entity.Strategy;
import brush.entity.WtInfo;
import brush.travel.DataInfo;
import brush.travel.ProxyCheck;
import brush.travel.StrategyCheck;
import brush.util.DateUtils;
import brush.util.XMLParserXStreamImpl;

public class Read {
	private final static String strategtFile = "strategy.xml";
	private final static String proxyInfoFile = "proxyInfo.txt";
	private final static String wtInfoFile = "wtInfo.txt";
	private final static String recordTotalFile = "recordTotal.txt";

	private final static long triggerPeriod = 5;

	private static long recordTotal = 0;
	private static final String CHART_ENCODING = "UTF-8";
	private static final Logger logger = Logger.getLogger(Read.class);
	private static String tempDate = null;
	private static URI uri = null;

	public void readStrategy() throws Exception {
		uri = ClassLoader.getSystemResource(strategtFile).toURI();
		String xmlString = FileUtils.readFileToString(new File(uri),
				CHART_ENCODING);
		DataInfo.strategy = (Strategy) XMLParserXStreamImpl.formXML(xmlString,
				new Strategy());
	}

	public void readProxyInfo() throws Exception {
		List<String> list = FileUtils.readLines(new File(ClassLoader
				.getSystemResource(proxyInfoFile).toURI()), CHART_ENCODING);
		List<ProxyInfo> proxyInfoList = new ArrayList<ProxyInfo>();
		ProxyInfo proxyInfo = null;
		for (String strProxyInfo : list) {
			String[] proxyInfos = strProxyInfo.split(",");
			proxyInfo = new ProxyInfo();
			proxyInfo.setIp(proxyInfos[0]);
			proxyInfo.setPort(Integer.parseInt(proxyInfos[1]));
			proxyInfo.setScheme(proxyInfos[2]);
			proxyInfoList.add(proxyInfo);
		}
		DataInfo.proxyInfo = proxyInfoList;
	}

	public void readWtInfo() throws Exception {
		List<String> list = FileUtils.readLines(new File(ClassLoader
				.getSystemResource(wtInfoFile).toURI()), CHART_ENCODING);
		List<WtInfo> wtInfoList = new ArrayList<WtInfo>();
		WtInfo wtInfo = null;
		for (String strWtInfo : list) {

			String[] wtInfos = strWtInfo.split(",");
			wtInfo = new WtInfo();
			wtInfo.setEs(wtInfos[0]);
			wtInfo.setEvent(wtInfos[1]);
			wtInfoList.add(wtInfo);
		}
		DataInfo.wtInfo = wtInfoList;
	}

	@SuppressWarnings("deprecation")
	public void writeRecord() throws Exception {
		logger.debug("execute writeRecord()");
		File file = new File(uri);
		recordTotal++;
		if (tempDate == null) {
			tempDate = DateUtils.getTodayYMD();
		}
		String now = DateUtils.getTodayYMD();
		if (!now.equalsIgnoreCase(tempDate)) {
			recordTotal = 1;
			tempDate = now;
		}
		File recordFile = new File(new File(file.getParent()).getParent() + File.separator+"logs"+ File.separator
				+ recordTotalFile + "." + tempDate);
		logger.debug("recordFile="+recordFile.getAbsolutePath()+" recordTotal="+recordTotal);
		FileUtils.writeStringToFile(recordFile, String.valueOf(recordTotal));
	}

	public static void main(String[] args) throws Exception {

		System.out.println(ClassLoader.getSystemResource("strategy.xml"));

		BasicConfigurator.configure();
		URI uri = ClassLoader.getSystemResource(strategtFile).toURI();
		File file = new File(uri);
		System.out.println(new File(file.getParent()).getParent() + File.separator + recordTotalFile
				+ "." + tempDate);
		//FileUtils.writeStringToFile(new File("e:\\abc.txt"),String.valueOf("1111"));
		/*
		 * Read read = new Read(); read.readStrategy(); // 读取策略配置文件
		 * read.readProxyInfo(); // 读取代理配置文件 read.readWtInfo(); // 读取事件配置文件
		 * 
		 * StrategyCheck strategyCheck = new StrategyCheck();
		 * strategyCheck.setTotalPerDay(); strategyCheck.setSleepTime();
		 * DataInfo.logDataInfo();
		 * 
		 * new Thread(new ProxyCheck()).start();// 检测并更新代理。
		 * 
		 * for (;;) { DataInfo.logDataInfo(); Thread.currentThread().sleep(20 *
		 * 1000); }
		 */
	}
}
