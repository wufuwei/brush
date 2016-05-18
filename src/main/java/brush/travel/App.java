package brush.travel;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import brush.entity.ProxyInfo;
import brush.entity.WtInfo;
import brush.http.HttpProxy;
import brush.strategy.Read;
import brush.util.ImeiUtils;
import brush.util.WebtrendsEncodeUtils;

/**
 * Hello world!
 *
 */
public class App implements java.lang.Runnable {
	HttpProxy proxy = HttpProxy.getInstance();
	private static final Logger logger = Logger.getLogger(App.class);

	public String postUrl() {
		return "http://183.224.41.138/v1/dcs47gx2e000008qd6ht30qnf_6j5x/events.svc/dcs47gx2e000008qd6ht30qnf_6j5x/events.svc";
	}

	public String para() {
		Date date = new Date();
		String deviceId = ImeiUtils.getUniqueId(date);
		deviceId = WebtrendsEncodeUtils.toBase64HASH(deviceId);
		WtInfo wtInfo = getRandWtInfo();

		StringBuffer bf = new StringBuffer();
		bf.append("WT.a_cat=Travel").append("&");
		bf.append("WT.a_nm=%E5%92%8C%E5%BF%83%E6%97%85%E8%A1%8C").append("&");
		bf.append("WT.a_pub=zhangjialun").append("&");
		bf.append("WT.av=1.0").append("&");
		bf.append("WT.co=yes").append("&");
		bf.append("WT.co_f=" + deviceId).append("&");
		bf.append("WT.ct=WIFI").append("&");
		bf.append("WT.dc=%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8").append("&");
		bf.append("WT.dl=0").append("&");
		bf.append("WT.dm=R7Plust").append("&");
		bf.append("WT.es=" + wtInfo.getEs()).append("&");
		bf.append("WT.ets=" + date.getTime()).append("&");
		bf.append("WT.event=" + wtInfo.getEvent()).append("&");
		bf.append("WT.fr=unknown").append("&");
		bf.append("WT.g_co=cn").append("&");
		bf.append("WT.os=5.0").append("&");
		bf.append("WT.pi=click").append("&");
		bf.append("WT.sr=1080x1800").append("&");
		bf.append("WT.sys=custom").append("&");
		bf.append("WT.ti=click").append("&");
		bf.append("WT.tz=8").append("&");
		bf.append("WT.uc=%E4%B8%AD%E5%9B%BD").append("&");
		bf.append("WT.ul=%E4%B8%AD%E6%96%87").append("&");
		bf.append("WT.vt_f=1").append("&");
		bf.append("WT.vt_f_d=1").append("&");
		bf.append("WT.vt_f_s=1").append("&");
		bf.append("WT.vt_sid=" + deviceId + "." + date.getTime()).append("&");
		bf.append("WT.vtid=" + deviceId).append("&");
		bf.append("WT.vtvs=" + date.getTime()).append("&");
		bf.append("dcsuri=" + wtInfo.getEvent());
		return bf.toString();
	}

	public static Header[] getHeaders() {
		Header header0 = new BasicHeader("Accept", "*/*");
		Header header1 = new BasicHeader("Content-Type",
				"application/x-www-form-urlencoded");
		Header header2 = new BasicHeader("User-Agent",
				"WebtrendsClientLibrary/v1.3.0.52+(App_Android)");
		// Header header3=new BasicHeader("Content-Length","583");
		Header header4 = new BasicHeader("Connection", "close");
		Header header5 = new BasicHeader("Host", "183.224.41.138");
		Header header6 = new BasicHeader("Accept-Encoding", "gzip");
		return new Header[] { header0, header1, header2, header4, header5,
				header6 };
	}

	public ProxyInfo getRandProxyInfo() {
		List<ProxyInfo> valid = DataInfo.validProxyInfo;
		int index = RandomUtils.nextInt(0, valid.size());
		return valid.get(index);
	}

	public WtInfo getRandWtInfo() {
		List<WtInfo> valid = DataInfo.wtInfo;
		int index = RandomUtils.nextInt(0, valid.size());
		return valid.get(index);
	}

	public void run() {
		try {
			proxy.post(postUrl() + "?" + para(), "", getHeaders(), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 主程序。
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		Read read = new Read();
		read.readStrategy(); // 读取策略配置文件
		read.readProxyInfo(); // 读取代理配置文件
		read.readWtInfo(); // 读取事件配置文件

		StrategyCheck strategyCheck = new StrategyCheck();
		strategyCheck.setTotalPerDay(); // 更新策略。
		strategyCheck.setSleepTime(); // 更新策略。

		int noProxyTryTimes = 20;
		int tempTryTimes = 0;

		if (1 == DataInfo.strategy.getUseProxy()) {
			new Thread(new ProxyCheck()).start();// 检测并更新代理。
		}
		App app = new App();

		HttpProxy proxy = HttpProxy.getInstance();
		HttpHost ipProxy = null;
		ProxyInfo proxyInfo = null;
		int status = 200;
		for (;;) {
			if (strategyCheck.isSendData()) {
				while (DataInfo.validProxyInfo == null
						|| DataInfo.validProxyInfo.size() == 0) {

					tempTryTimes++;
					if (tempTryTimes >= noProxyTryTimes) {
						logger.warn("exit for there is no proxy aviable to use!!!");
						System.exit(0);
					}
					Thread.sleep(10 * 1000);
				}

				// 重新归0；
				tempTryTimes = 0;
				if (1 == DataInfo.strategy.getUseProxy()) {
					proxyInfo = app.getRandProxyInfo();
					ipProxy = new HttpHost(proxyInfo.getIp(),
							proxyInfo.getPort(), proxyInfo.getScheme()
									.toLowerCase());
				}else{
					ipProxy=null;
				}
				try {
					status = proxy.post(app.postUrl() + "?" + app.para(), "",
							App.getHeaders(), ipProxy);

					if (200 == status) {
						read.writeRecord();
						Thread.sleep(strategyCheck.getCurrSleepTime());
					}
				} catch (Exception e) {
					
				}
			} else {
				logger.warn("exit for now after the end date !!!");
				System.exit(0);
			}
		}
	}
}
