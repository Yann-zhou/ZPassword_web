package ws;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

 
/**AES 是一种可逆加密算法，对用户的敏感信息加密处理
* 对原始数据进行AES加密后，在进行Base64编码转化；
*/
public class aesCBC {
/*
* 加密用的Key 可以用26个字母和数字组成
* 此处使用AES-128-CBC加密模式，key需要为16位。
*/
	private String sKey="sklhdflsjfsdgdeg";
	private String ivParameter="cfbsdfgsdfxccvd1";
	
	public aesCBC(String aesKey, String aesIv){
		this.sKey = aesKey;
		this.ivParameter = aesIv;
	}
	
	// 加密
	public static String encrypt(String sSrc, byte[] sKey, byte[] ivParameter) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = sKey;
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivParameter);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		return Base64.encodeBase64String(encrypted);//此处使用BASE64做转码。
}
 
	// 解密
	public static String decrypt(String sSrc, byte[] sKey, byte[] ivParameter) throws Exception {
		try {
			byte[] raw = sKey;
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Base64.decodeBase64(sSrc);//先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original,"utf-8");
			return originalString;
		} catch (Exception ex) {
			return null;
		}
}
}
 
//	public static void main(String[] args) throws Exception {
//		// 需要加密的字串
//		String cSrc = "我来自中国";
//		System.out.println(cSrc);
//		// 加密
//		long lStart = System.currentTimeMillis();
//		String enString = AesCBC.getInstance().encrypt(cSrc,"utf-8",sKey,ivParameter);
//		System.out.println("加密后的字串是："+ enString);
// 
//		long lUseTime = System.currentTimeMillis() - lStart;
//		System.out.println("加密耗时：" + lUseTime + "毫秒");
//		// 解密
//		lStart = System.currentTimeMillis();
//		String DeString = AesCBC.getInstance().decrypt(enString,"utf-8",sKey,ivParameter);
//		System.out.println("解密后的字串是：" + DeString);
//		lUseTime = System.currentTimeMillis() - lStart;
//		System.out.println("解密耗时：" + lUseTime + "毫秒");
//	}
//}
