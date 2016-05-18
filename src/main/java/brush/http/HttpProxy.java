package brush.http;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class HttpProxy {
	private static final Logger logger = Logger.getLogger(HttpProxy.class);
	private static final Logger boss = Logger.getLogger("inf2log");
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";
	private static CloseableHttpClient client = null;
	private static RequestConfig config=null;
	private static HttpProxy instance = null;
	private static String charSet = "UTF-8";

	public static synchronized HttpProxy getInstance() {
		if (instance == null) {
			instance = new HttpProxy();
		}
		return instance;
	}

	private HttpProxy() {

		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(100);
		config = RequestConfig.custom()
                .setSocketTimeout(10000)
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(5000)
                .build();

		client = HttpClients.custom().setConnectionManager(cm).build();
	}

	/**
	 * 设置代理
	 * 
	 * @param ip
	 * @param port
	 * @param scheme
	 * @return
	 */
	public RequestConfig setProxy(String ip, int port, String scheme) {
		HttpHost proxy = new HttpHost(ip, port, scheme);
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		return config;
	}

	/**
	 * 发送Body流。
	 * 
	 * @param url
	 * @param StringEntity
	 * @param handler
	 * @return
	 */

	public int post(String url, String stringEntity, Header[] headers,
			HttpHost proxy) throws Exception {
		logger.debug("execute post2String(String url=" + url
				+ ", String stringEntity=" + stringEntity + ") ");
		HttpPost httppost = null;
	
		CloseableHttpResponse response = null;
		try {

			httppost = new HttpPost(url);
			if (proxy != null) {
				RequestConfig requestConfig = RequestConfig.copy(config)
	                    .setProxy(proxy)
	                    .build();
				httppost.setConfig(requestConfig);
			}
			if (headers != null && headers.length > 0) {
				httppost.setHeaders(headers);
			}
			if (StringUtils.isNotEmpty(stringEntity)) {
				stringEntity = stringEntity.trim();
				StringEntity reqEntity = new StringEntity(stringEntity, charSet);
				httppost.setEntity(reqEntity);

			}

			String sequence = String.valueOf(System.currentTimeMillis());
			log(sequence, "request", "post", url, stringEntity, 0);
			response = client.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream instream = entity.getContent();
			System.out.println("content:   " + IOUtils.toString(instream));

			int statusCode = response.getStatusLine().getStatusCode();
			log(sequence, "response", "post", url, stringEntity, statusCode);
			logger.debug(" response.getStatusLine().getStatusCode()="
					+ statusCode);
			return statusCode;

		} catch (Exception e) {
			logger.error("post exception", e);
			throw e;
		} finally {
			response.close();
			if (httppost != null) {
				httppost.abort();
			}
		}

	}

	/**
	 * @param url
	 * @param StringEntity
	 * @param handler
	 * @return
	 */
	public int get(String url, Header[] headers) throws Exception {
		logger.debug("execute get2String(String url=" + url);
		HttpGet httpget = null;
		CloseableHttpResponse response = null;
		try {

			httpget = new HttpGet(url);
			if (headers != null && headers.length > 0) {
				httpget.setHeaders(headers);
			}
			String sequence = String.valueOf(System.currentTimeMillis());
			log(sequence, "request", "get", url, "", 0);
			response = client.execute(httpget);
			int statusCode = response.getStatusLine().getStatusCode();
			log(sequence, "response", "get", url, "", statusCode);
			logger.debug(" response.getStatusLine().getStatusCode()="
					+ statusCode);
			return statusCode;
		} catch (Exception e) {
			logger.error("get exception", e);
			throw e;
		} finally {
			response.close();
			if (httpget != null) {
				httpget.abort();
			}
		}

	}

	/**
	 * 日志
	 * 
	 * @param actionType
	 * @param method
	 * @param requestURI
	 * @param body
	 */
	private void log(String sequence, String actionType, String method,
			String requestURI, String body, int status) {
		StringBuffer bf = new StringBuffer();
		bf.append("sequence:").append(sequence);
		bf.append("|actionType:").append(actionType);
		bf.append("|actionMethod:").append(method);
		bf.append("|requestURI:").append(requestURI);
		bf.append("|body:").append(body);
		bf.append("|status:").append(status);
		boss.warn(bf.toString());

	}

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		HttpProxy proxy = HttpProxy.getInstance();
		String url = "http://221.176.1.140/osp/interface/search_sync_status.php";
		String para = "WT.a_cat=Travel&WT.a_nm=%E5%92%8C%E5%BF%83%E6%97%85%E8%A1%8C&WT.a_pub=zhangjialun&WT.av=1.0&WT.co=yes&WT.co_f=xUvTsZP7d38&WT.ct=WIFI&WT.dc=%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8&WT.dl=0&WT.dm=R7Plust&WT.es=%E6%9C%AC%E5%9C%B0%E4%BA%BA&WT.ets=1462853979912&WT.event=%E6%9C%AC%E5%9C%B0%E4%BA%BA&WT.fr=unknown&WT.g_co=cn&WT.os=5.0&WT.pi=click&WT.sr=1080x1800&WT.sys=custom&WT.ti=click&WT.tz=8&WT.uc=%E4%B8%AD%E5%9B%BD&WT.ul=%E4%B8%AD%E6%96%87&WT.vt_f=1&WT.vt_f_d=1&WT.vt_f_s=1&WT.vt_sid=xUvTsZP7d38.1462853979912&WT.vtid=xUvTsZP7d38&WT.vtvs=1462853979912&dcsuri=%E6%9C%AC%E5%9C%B0%E4%BA%BA";
		url = "http://183.224.41.138/v1/dcs47gx2e000008qd6ht30qnf_6j5x/events.svc/dcs47gx2e000008qd6ht30qnf_6j5x/events.svc";
		int statusCode = proxy.post(url + "?" + para, "", null,null);
		System.out.println("statusCode=" + statusCode);
	}

}
