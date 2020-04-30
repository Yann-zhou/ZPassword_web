package ws;

import org.apache.commons.codec.binary.Base64;
//import org.apache.tomcat.util.codec.binary.Base64;

//import sun.tools.tree.ThisExpression;

import javax.crypto.Cipher;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;


import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;



/**
 * RSA加解密工具类，实现公钥加密私钥解密和私钥解密公钥解密
 */
public class rsa {
	static private String privateRsaKeyString = 
			"MIIEowIBAAKCAQEAz/8xtlg5PQK0znBLUzdYPHkxk/ctevlpBF+8pFQeBxR2TbSW" + 
			"C0SoefM1KYtlfN9mfTA0P5dI3w8Z+YD7q0HDE176Dcj/djBxMFxnO76uUGsjZjNQ" + 
			"/NjMA1z4nfkI4fA28GkIkSH9wxGcLdLB0stqkf6bO8pBPCR06cxZrrM8HJ2qYveh" + 
			"r6iKHyN1KS0Yz48pzco08of7bTqux6DM1LeVy/qB+ryfLbVqzWaqq7vl6LDy6gvV" + 
			"tgaW2XHF0iwjZhaYA1FZUEFsbJQJ7d99FTOl8hhIZPv5N7frZQX1qz4ey05Qwkls" + 
			"1kwxuh1nL0pEj5dK1q5jJVM7uE7WG7WD34dsZQIDAQABAoIBAAWSTOcZAuN+/I/h" + 
			"Eyt1+o+YIXtYrieyoZ0D4X43YJyI8EhRk4lWu/qUSLLFsKDBLTIiRewEJegPs/7q" + 
			"dRNXzh1pOGKBJVKppoaImx1PFMVDsW2N51f2iLR4W5KDtriyog4biia1FeKRSLEz" + 
			"iYyXdVHW05qs5sVxJo6Ssj5LP8t2Y1Z3KZG4osYJi0HVLX5BQvQVPgJtYpJBENBL" + 
			"sBlFX7sTMkE6FzKlA3BHEskgATegdzcFCPeY4RVvzazKc8A9Mh/Sqe3f5Lm1gKPK" + 
			"KTURXHLuSXER6i8Pg1VRXG67CGXrvOHUtwGDaa7JFQVTimL85/d63i95C2RXRwCp" + 
			"dGHmT4ECgYEA2wUh51AL37nGAJuX5RN78R3i/OpQKr7FlXN5GWqd44iBAVbXjQbQ" + 
			"Xz6IPD805XwgU4Ue3jeAiS/uQsYcCfcJjA5h+k/j82KmAV64I4XED8L6IF2u6uxA" + 
			"sfUprWHDmK454N7dnJJVsFx0r0HJB2Y+Bc6BSzmRLCgFSRE6wAUDjyUCgYEA8x2Z" + 
			"DwsTOVq/CzXzNpaPb50ixqBngDzD1foIlQPzx+0nyoNTLnsm8m7uUTrCAwegLSP+" + 
			"+nbj3e43Alc353XETHuQi+khq0LqQ5OhaGa9dL872dAgUp5XAxForXpV+bBF1A/P" + 
			"uqZV2Q1Xqqu6TJ11IRojFIRn4uJdCq4aqIg9hEECgYAjkyjXnpvfIhi0PpGkhs6p" + 
			"8nXNKO4Ok2Aaj2ALLdMpgyGZKxbl9EZiQhD04DtiTxAdINFLvO4m2l8z/2PMhpDJ" + 
			"bAikFNfsz4gxTsZQFskq2juwiCcCR4+WmEDFxcx0f1AtY2XRqb87Xzkgwmm5xvSq" + 
			"EsnTgy0+Y92/6YmGaQoT9QKBgEUBf7JfaGN9OIDMHW6/KZN6vKY4XWhu1v9jaxAx" + 
			"AP8cd5Dp2bB10EY76nUwAmWtwm9NqlAZcvBJb1/AteOC7+bto8eyspXT9n6b5tKl" + 
			"Zt0+cpSOmL4ap4P9awLimIi42fkpS8Mk93UWbm3JNKYOg3KT4qUuN/aUXehsJrDz" + 
			"itKBAoGBAJre5KjOzBqOMkJ036H2QAoY6YjfuiR3lFDQsNloaq5FpeQ4VBr6cg6W" + 
			"7YnJyvavKlEUMnlyNZHxxsbp+Y7VDpfP0hKdyuiRMFiAOM84Ejgg/Wb0YEHUq/hv" + 
			"jBQhJV+yk5g+TC4+iRTEBUhR6iulaTcsw2PNjDrL3jiUANwWG10S";
	
	static private String publicRsaKeyString = 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz/8xtlg5PQK0znBLUzdY" + 
			"PHkxk/ctevlpBF+8pFQeBxR2TbSWC0SoefM1KYtlfN9mfTA0P5dI3w8Z+YD7q0HD" + 
			"E176Dcj/djBxMFxnO76uUGsjZjNQ/NjMA1z4nfkI4fA28GkIkSH9wxGcLdLB0stq" + 
			"kf6bO8pBPCR06cxZrrM8HJ2qYvehr6iKHyN1KS0Yz48pzco08of7bTqux6DM1LeV" + 
			"y/qB+ryfLbVqzWaqq7vl6LDy6gvVtgaW2XHF0iwjZhaYA1FZUEFsbJQJ7d99FTOl" + 
			"8hhIZPv5N7frZQX1qz4ey05Qwkls1kwxuh1nL0pEj5dK1q5jJVM7uE7WG7WD34ds" + 
			"ZQIDAQAB";

    /**
     * 公钥解密
     *
     * @param publicKeyText
     * @param text
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String publicKeyText, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    /**
     * 私钥加密
     *
     * @param privateKeyText
     * @param text
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String privateKeyText, String text) throws Exception {
    	RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(Base64.decodeBase64(privateKeyText)));
    	RSAPrivateKeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent());
    	KeyFactory keyFactory= KeyFactory.getInstance("RSA");
    	PrivateKey priKey= keyFactory.generatePrivate(rsaPrivKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * 私钥解密
     *
     * @param privateKeyText
     * @param text
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(String privateKeyText, String text) throws Exception {
    	RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(Base64.decodeBase64(privateKeyText)));
    	RSAPrivateKeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent());
    	KeyFactory keyFactory= KeyFactory.getInstance("RSA");
    	PrivateKey priKey= keyFactory.generatePrivate(rsaPrivKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return result;
    }

    /**
     * 公钥加密
     *
     * @param publicKeyText
     * @param text
     * @return
     */
    public static String encryptByPublicKey(String publicKeyText, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * RSA密钥对对象
     */
    public static class RSAKeyPair {

        private String publicKey;
        private String privateKey;

        public RSAKeyPair(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

    }

}