package brush.entity;

public class ProxyInfo {
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	
	public boolean isCanUse() {
		return canUse;
	}
	public void setCanUse(boolean canUse) {
		this.canUse = canUse;
	}
	private String ip;
	private int port;
	private String scheme;
	private boolean canUse;

	
}
