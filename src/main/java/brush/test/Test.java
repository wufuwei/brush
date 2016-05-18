package brush.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import brush.entity.ProxyInfo;
import brush.travel.DataInfo;
import brush.travel.ProxyCheck;

public class Test {
	public static void main(String[] args) {
		Test test = new Test();
		test.init();
		System.out.println("after init() firstTime=" + firstTime);
		test.run();
		System.out.println("after run1()  firstTime=" + firstTime);
		System.out.println("DataInfo.proxyInfo=" + DataInfo.proxyInfo);
		System.out
				.println("DataInfo.validProxyInfo=" + DataInfo.validProxyInfo.size());
		test.run();
		System.out.println("after run2()  firstTime=" + firstTime);
		System.out.println("firstTime=" + firstTime);
		System.out.println("DataInfo.proxyInfo=" + DataInfo.proxyInfo);
		System.out
				.println("DataInfo.validProxyInfo=" + DataInfo.validProxyInfo.size());
	}

	private static final Logger logger = Logger.getLogger(ProxyCheck.class);
	private static final long checkTime = 30;
	private static boolean firstTime = true;

	public void init() {
		List<ProxyInfo> all = new ArrayList<ProxyInfo>();
		ProxyInfo p = null;
		for (int i = 0; i < 100; i++) {
			p = new ProxyInfo();
			p.setIp(String.valueOf(i));
			all.add(p);
		}
		DataInfo.proxyInfo = all;

		System.out.println("DataInfo.proxyInfo=" + all);
	}

	public List<ProxyInfo> checkProxyInfo(List<ProxyInfo> list) {
		List<ProxyInfo> temp = new ArrayList<ProxyInfo>();
		if (firstTime) {
			DataInfo.validProxyInfo = temp;
		}
		int ip = 0;
		boolean ipis=false;
		for (ProxyInfo proxyInfo : list) {
			ip = Integer.parseInt(proxyInfo.getIp());
			if (firstTime) {				
				ipis = ip > 50 && ip < 70;
			}else{
				ipis = ip > 10 && ip < 20;
			}
			if (ipis) {

				temp.add(proxyInfo);
				System.out.println("temp=" + temp);
				if (firstTime) {
					System.out.println("add temp");
					
				}
			}
		}
		System.out.println("-----------DataInfo.validProxyInfo=" + DataInfo.validProxyInfo);
		firstTime = false;
		return temp;
	}

	public void run() {

		List<ProxyInfo> list = DataInfo.proxyInfo;
		if (list != null && list.size() > 0) {
			List<ProxyInfo> valid = checkProxyInfo(list);
			
			if (valid != null && valid.size() > 0&&!firstTime) {
				DataInfo.validProxyInfo = valid;
			}

		}

	}
}
