package ws;

import java.net.URI;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import org.apache.commons.codec.binary.Base64;

/** This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded. */
public class connecter extends WebSocketClient {
	private String privateKeyString;
	
	private String sKey = null;
	private String ivParameter = null;
	private String msg = null;
	private String sendMsg = null;

	public connecter( URI serverUri , Draft draft ) {
		super( serverUri, draft );
	}

	public connecter( URI serverURI , String privateKey) {
		super( serverURI );
		privateKeyString = privateKey;
	}

	public connecter( URI serverUri, Map<String, String> httpHeaders ) {
		super(serverUri, httpHeaders);
	}

	@Override
	public void onOpen( ServerHandshake handshakedata ) {
		//send("Hello, it is me. Mario :)");
		System.out.println( "opened connection" );
		// if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
	}

	@Override
	public void onMessage( String message ) {
		System.out.println( "received: " + message );
		String msg_de = null;
		if(!isJson(message)) {
			try {
				msg_de = aesCBC.decrypt(message, sKey.getBytes(), ivParameter.getBytes());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(msg_de);
			msg = msg_de;
			close();
		}else {
			JSONObject jsonObject = new JSONObject(message);
            String en_aes_key = jsonObject.getString("en_aes_key");
            String en_aes_key_sha256 = jsonObject.getString("en_aes_key_sha256");
            //byte[] aes_key_rsa = Base64.getDecoder().decode(en_aes_key);
            
            //RSA解密
            byte[] aes_key_byte = null;
			try {
				aes_key_byte = rsa.decryptByPrivateKey(privateKeyString, en_aes_key);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String aes_key = convertHexToString(new String(aes_key_byte));
			sKey = aes_key;
			ivParameter = aes_key.substring(0, 16);
			String senString = Base64.encodeBase64String(sendMsg.getBytes());
			String sendString=null;
			try {
				sendString = aesCBC.encrypt(senString, sKey.getBytes(), ivParameter.getBytes());
				//s = AesCBC.encrypt("123", aes_key_byte, Arrays.copyOfRange(aes_key_byte, 0, 16));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			send(sendString);
        }
	}

	@Override
	public void onClose( int code, String reason, boolean remote ) {
		// The codes are documented in class org.java_websocket.framing.CloseFrame
		System.out.println( "Connection closed by " + ( remote ? "remote peer" : "us" ) + " Code: " + code + " Reason: " + reason );
	}

	@Override
	public void onError( Exception ex ) {
		ex.printStackTrace();
		// if the error is fatal then onClose will be called additionally
	}

//	public static void main( String[] args ) throws URISyntaxException {
//		String privString=
//				"MIIEowIBAAKCAQEAz/8xtlg5PQK0znBLUzdYPHkxk/ctevlpBF+8pFQeBxR2TbSW" + 
//				"C0SoefM1KYtlfN9mfTA0P5dI3w8Z+YD7q0HDE176Dcj/djBxMFxnO76uUGsjZjNQ" + 
//				"/NjMA1z4nfkI4fA28GkIkSH9wxGcLdLB0stqkf6bO8pBPCR06cxZrrM8HJ2qYveh" + 
//				"r6iKHyN1KS0Yz48pzco08of7bTqux6DM1LeVy/qB+ryfLbVqzWaqq7vl6LDy6gvV" + 
//				"tgaW2XHF0iwjZhaYA1FZUEFsbJQJ7d99FTOl8hhIZPv5N7frZQX1qz4ey05Qwkls" + 
//				"1kwxuh1nL0pEj5dK1q5jJVM7uE7WG7WD34dsZQIDAQABAoIBAAWSTOcZAuN+/I/h" + 
//				"Eyt1+o+YIXtYrieyoZ0D4X43YJyI8EhRk4lWu/qUSLLFsKDBLTIiRewEJegPs/7q" + 
//				"dRNXzh1pOGKBJVKppoaImx1PFMVDsW2N51f2iLR4W5KDtriyog4biia1FeKRSLEz" + 
//				"iYyXdVHW05qs5sVxJo6Ssj5LP8t2Y1Z3KZG4osYJi0HVLX5BQvQVPgJtYpJBENBL" + 
//				"sBlFX7sTMkE6FzKlA3BHEskgATegdzcFCPeY4RVvzazKc8A9Mh/Sqe3f5Lm1gKPK" + 
//				"KTURXHLuSXER6i8Pg1VRXG67CGXrvOHUtwGDaa7JFQVTimL85/d63i95C2RXRwCp" + 
//				"dGHmT4ECgYEA2wUh51AL37nGAJuX5RN78R3i/OpQKr7FlXN5GWqd44iBAVbXjQbQ" + 
//				"Xz6IPD805XwgU4Ue3jeAiS/uQsYcCfcJjA5h+k/j82KmAV64I4XED8L6IF2u6uxA" + 
//				"sfUprWHDmK454N7dnJJVsFx0r0HJB2Y+Bc6BSzmRLCgFSRE6wAUDjyUCgYEA8x2Z" + 
//				"DwsTOVq/CzXzNpaPb50ixqBngDzD1foIlQPzx+0nyoNTLnsm8m7uUTrCAwegLSP+" + 
//				"+nbj3e43Alc353XETHuQi+khq0LqQ5OhaGa9dL872dAgUp5XAxForXpV+bBF1A/P" + 
//				"uqZV2Q1Xqqu6TJ11IRojFIRn4uJdCq4aqIg9hEECgYAjkyjXnpvfIhi0PpGkhs6p" + 
//				"8nXNKO4Ok2Aaj2ALLdMpgyGZKxbl9EZiQhD04DtiTxAdINFLvO4m2l8z/2PMhpDJ" + 
//				"bAikFNfsz4gxTsZQFskq2juwiCcCR4+WmEDFxcx0f1AtY2XRqb87Xzkgwmm5xvSq" + 
//				"EsnTgy0+Y92/6YmGaQoT9QKBgEUBf7JfaGN9OIDMHW6/KZN6vKY4XWhu1v9jaxAx" + 
//				"AP8cd5Dp2bB10EY76nUwAmWtwm9NqlAZcvBJb1/AteOC7+bto8eyspXT9n6b5tKl" + 
//				"Zt0+cpSOmL4ap4P9awLimIi42fkpS8Mk93UWbm3JNKYOg3KT4qUuN/aUXehsJrDz" + 
//				"itKBAoGBAJre5KjOzBqOMkJ036H2QAoY6YjfuiR3lFDQsNloaq5FpeQ4VBr6cg6W" + 
//				"7YnJyvavKlEUMnlyNZHxxsbp+Y7VDpfP0hKdyuiRMFiAOM84Ejgg/Wb0YEHUq/hv" + 
//				"jBQhJV+yk5g+TC4+iRTEBUhR6iulaTcsw2PNjDrL3jiUANwWG10S";
//		connecter c = new connecter( new URI( "ws://localhost:4200" ), privString); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
//		c.run();
//		System.out.println(c.isClosed());
//	}
	
	public String getMsg() {
		return msg;
	}
	
	public void sendMsg(String s) {
		try {
			String sendString = null;
			sendString = aesCBC.encrypt(Base64.encodeBase64String(s.getBytes()), sKey.getBytes(), ivParameter.getBytes());
			System.out.println("发送的信息为："+sendString);
			System.out.println("aesKey:"+sKey);
			System.out.println("aesIv:"+ivParameter);
			send(sendString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("异常处理");
			e.printStackTrace();
		}
	}
	
	public void setSendMsg(String smsg) {
		this.sendMsg = smsg;
	}
	
	
	
	
	
	
	
	
	private boolean isJson(String string){
	    try {
			JSONObject jsonObject = new JSONObject(string);
	        return  true;
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        byte[] buffer = Base64.decodeBase64(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
	}
	

	public static String convertHexToString(String hex) {
 
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
 
		// 564e3a322d302e312e34 split into two characters 56, 4e, 3a...
		for (int i = 0; i < hex.length() - 1; i += 2) {
 
			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);
 
			temp.append(decimal);
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}
}