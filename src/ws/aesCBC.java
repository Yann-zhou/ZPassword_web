package ws;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

 
/**AES ��һ�ֿ�������㷨�����û���������Ϣ���ܴ���
* ��ԭʼ���ݽ���AES���ܺ��ڽ���Base64����ת����
*/
public class aesCBC {
/*
* �����õ�Key ������26����ĸ���������
* �˴�ʹ��AES-128-CBC����ģʽ��key��ҪΪ16λ��
*/
	private String sKey="sklhdflsjfsdgdeg";
	private String ivParameter="cfbsdfgsdfxccvd1";
	
	public aesCBC(String aesKey, String aesIv){
		this.sKey = aesKey;
		this.ivParameter = aesIv;
	}
	
	// ����
	public static String encrypt(String sSrc, byte[] sKey, byte[] ivParameter) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = sKey;
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivParameter);//ʹ��CBCģʽ����Ҫһ������iv�������Ӽ����㷨��ǿ��
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		return Base64.encodeBase64String(encrypted);//�˴�ʹ��BASE64��ת�롣
}
 
	// ����
	public static String decrypt(String sSrc, byte[] sKey, byte[] ivParameter) throws Exception {
		try {
			byte[] raw = sKey;
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Base64.decodeBase64(sSrc);//����base64����
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original,"utf-8");
			return originalString;
		} catch (Exception ex) {
			return null;
		}
}
}
 
//	public static void main(String[] args) throws Exception {
//		// ��Ҫ���ܵ��ִ�
//		String cSrc = "�������й�";
//		System.out.println(cSrc);
//		// ����
//		long lStart = System.currentTimeMillis();
//		String enString = AesCBC.getInstance().encrypt(cSrc,"utf-8",sKey,ivParameter);
//		System.out.println("���ܺ���ִ��ǣ�"+ enString);
// 
//		long lUseTime = System.currentTimeMillis() - lStart;
//		System.out.println("���ܺ�ʱ��" + lUseTime + "����");
//		// ����
//		lStart = System.currentTimeMillis();
//		String DeString = AesCBC.getInstance().decrypt(enString,"utf-8",sKey,ivParameter);
//		System.out.println("���ܺ���ִ��ǣ�" + DeString);
//		lUseTime = System.currentTimeMillis() - lStart;
//		System.out.println("���ܺ�ʱ��" + lUseTime + "����");
//	}
//}
