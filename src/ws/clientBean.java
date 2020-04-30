package ws;

import java.net.URI;
import java.net.URISyntaxException;

public class clientBean {
	private connecter c;
	private String ipString;
	private String portString;
	private String sendMsgString;
	private String rsaPrivateString;
	
	public connecter getC() {
		return c;
	}

	public String getIpString() {
		return ipString;
	}

	public void setIpString(String ipString) {
		this.ipString = ipString;
	}

	public String getPortString() {
		return portString;
	}

	public void setPortString(String portString) {
		this.portString = portString;
	}

	public String getRsaPrivateString() {
		return rsaPrivateString;
	}

	public void setRsaPrivateString(String rsaPrivateString) throws URISyntaxException {
		this.rsaPrivateString = rsaPrivateString;
		c = new connecter(new URI("ws://"+ipString+":"+portString), this.rsaPrivateString);
		c.setSendMsg(sendMsgString);
		c.run();
	}

	public String getSendMsgString() {
		return sendMsgString;
	}

	public void setSendMsgString(String sendMsgString) {
		this.sendMsgString = sendMsgString;
	}

	public clientBean() {
		
	}
}
