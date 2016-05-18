package brush.util;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.log4j.Logger;

public class ServiceUtils {
	private static final Logger logger = Logger.getLogger(ServiceUtils.class);

	public static boolean checkServiceIsOK(String IP, int port) {
		logger.debug("execute checkServiceIsOK(String IP=" + IP + ",int port"
				+ port + ")");
		Socket s = null;
		boolean flag = false;
		try {
			s = new Socket(IP, port);
			flag = s.isConnected();
			logger.warn("checkServiceIsOK(String IP=" + IP + ",int port" + port
					+ ") is success");
			return true;
		} catch (Exception e) {
			logger.warn("checkServiceIsOK(String IP=" + IP + ",int port" + port
					+ ") is fail");
			return false;
		} finally {
			if (null != s) {
				try {
					s.close();
				} catch (Exception e2) {
				}
			}
		}
	}

	
	
	public static boolean checkServerAvaliable(String IP, int port) {
		logger.debug("execute checkServiceIsOK(String IP=" + IP + ",int port"
				+ port + ")");
		boolean isConnect = false;
		TelnetClient telnet = new TelnetClient();
		try {
			telnet.setConnectTimeout(10*1000);
			telnet.connect(IP, port);
			if (telnet != null) {
				isConnect = telnet.isConnected();
			}
		} catch (SocketException ioe) {
			logger.info("checkServiceIsOK(String IP=" + IP + ",int port" + port
					+ ") is fail");
		} catch (IOException se) {
			logger.info("checkServiceIsOK(String IP=" + IP + ",int port" + port
					+ ") is fail");
		} finally {
			disconnect(telnet);
		}
		if (isConnect) {
			logger.info("checkServiceIsOK(String IP=" + IP + ",int port" + port
					+ ") is success");
		}else{
			logger.info("checkServiceIsOK(String IP=" + IP + ",int port" + port
					+ ") is fail");
		}
		return isConnect;
	}

	public static void disconnect(TelnetClient telnet) {
		try {			
			telnet.disconnect();
		} catch (IOException e) {
		
		}
	}

	public static void main(String[] args) {
		String ip = "221.176.66.85";
		int port = 80;
		System.out.println(ServiceUtils.checkServiceIsOK(ip, port));
	}
}
