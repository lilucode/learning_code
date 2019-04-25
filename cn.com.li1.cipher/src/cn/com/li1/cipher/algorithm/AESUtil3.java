package cn.com.li1.cipher.algorithm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @time 2019年4月25日
 * @description AES加密和解密
 *
 */
public class AESUtil3 {

	private static final String KEY_AES = "AES";

	public static String encrypt(String src, String key) throws Exception {
		if (key == null || key.length() != 16) {
			throw new Exception("key不满足条件");
		}
		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
		Cipher cipher = Cipher.getInstance(KEY_AES);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(src.getBytes());
		return byte2hex(encrypted);
	}

	public static String decrypt(String src, String key) throws Exception {
		if (key == null || key.length() != 16) {
			throw new Exception("key不满足条件");
		}
		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
		Cipher cipher = Cipher.getInstance(KEY_AES);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] encrypted1 = hex2byte(src);
		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);
		return originalString;
	}

	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("(1)请输入要加密的内容,(按回车结束）");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String arg1 = br.readLine();
		System.out.println("(2)请输入密钥,(按回车结束）");
		String arg2 = br.readLine();
		String a = encrypt(arg1, arg2);
		System.out.println("加密后的内容：" + a);
		System.out.println("(3)请输入要解密的内容,(按回车结束）");
		String arg3 = br.readLine();
		System.out.println("(2)请输入密钥,(按回车结束）");
		String arg4 = br.readLine();
		String b = decrypt(arg3, arg4);
		System.out.println("解密后的内容：" + b);
	}

}
