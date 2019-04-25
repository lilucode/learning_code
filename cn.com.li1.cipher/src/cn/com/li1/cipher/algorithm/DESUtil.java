package cn.com.li1.cipher.algorithm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESUtil {

	/**
	 * 
	 * 加密
	 * 
	 * @param src
	 *            数据源
	 * 
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * 
	 * @return 返回加密后的数据
	 * 
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] src, byte[] key) throws Exception
	{

		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("AES");

		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance("AES");

		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		// 现在，获取数据并加密
		// 正式执行加密操作
		return cipher.doFinal(src);

	}

	/**
	 * 
	 * 解密
	 * 
	 * @param src
	 *            数据源
	 * 
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * 
	 * @return 返回解密后的原始数据
	 * 
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] src, byte[] key) throws Exception
	{

		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("AES");

		SecretKey securekey = keyFactory.generateSecret(dks);

		
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("AES");

		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		// 现在，获取数据并解密
		// 正式执行解密操作
		return cipher.doFinal(src);
	}

	/**
	 * 
	 * 密码解密
	 * 
	 * @param data
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	private final static String decrypt(String data, String key)
			throws Exception
	{
		if (key.isEmpty())
		{
			key = "__agree_";
		}
		return new String(decrypt(hex2byte(data), key.getBytes()));

	}

	/**
	 * 
	 * 密码加密
	 * 
	 * @param password
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	private final static String encrypt(String password, String key)
			throws Exception
	{
		if (key.isEmpty())
		{
			key = "__agree_";
		}
		return byte2hex(encrypt(password.getBytes(), key.getBytes()));
	}

	/**
	 * 
	 * 二行制转字符串
	 * 
	 * @param b
	 * 
	 * @return
	 */
	private static String byte2hex(byte[] b)
	{

		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++)
		{
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();

	}

	private static byte[] hex2byte(String src)
	{
		if ((src.length() % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b = new byte[src.length() / 2];
		for (int n = 0; n < src.length(); n += 2)
		{
			String item = src.substring(n, n + 2);
			b[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b;
	}
	
	/**
	 * (1)请输入密码,(按回车结束）
		password
		(2)请再输入密码,(按回车结束）
		password
		加密后密文[39D748CC59CFD9467D76AD019B105EC8]
		解密后 密码[password]
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String arg1 = null;
        String arg2 = null;
        while (true)
        {
            System.out.println("(1)请输入密码,(按回车结束）");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    System.in));
            arg1 = br.readLine();
            System.out.println("(2)请再输入密码,(按回车结束）");
            br = new BufferedReader(new InputStreamReader(System.in));
            arg2 = br.readLine();
            if ((arg1 != null) && (arg1.equals(arg2)))
            {
                break;
            }
            System.out.println("两次密码不一至，请重新输入");
        }
        String code = encrypt(arg1,"__agree_");
        System.out.println("加密后密文[" + code + "]");
        String deccode=decrypt(code, "__agree_");
        System.out.println("解密后密码[" + deccode + "]");
	}
}
