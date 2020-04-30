package ws;

import java.net.URI;
import java.net.URISyntaxException;
 
import ws.MsgWebSocketClient;
//import ggmes.websocket.server.MsgWebSocketServer;
//import utils.ObjectUtils;
 
public enum WebClientEnum {
 
	CLIENT;
	
	private static MsgWebSocketClient socketClient = null;
	
	public static void initClient(MsgWebSocketClient client) {
		socketClient = client;
//		if(ObjectUtils.isNotNull(socketClient)) {
//			socketClient.connect();
//			socketClient.send("≤‚ ‘websocket°£°£°£");
//		}
		boolean flag = true;
		int i=1000;
		while(flag) {
			socketClient.send("≤‚ ‘websocket°£°£°£"+(i--));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(i == 0) {
				flag = false;
			}
		}
	}
	
}