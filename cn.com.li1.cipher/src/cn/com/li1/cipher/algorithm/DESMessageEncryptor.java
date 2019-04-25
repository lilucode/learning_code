package cn.com.li1.cipher.algorithm;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESMessageEncryptor {

	public String decryptMessage(String encryptedText) {
	String passwordString = encryptedText.substring(0, 12);
	String cipherString = encryptedText.substring(12, encryptedText
	.length());
	BASE64Decoder decoder = new BASE64Decoder();
	try {
	byte[] password = decoder.decodeBuffer(passwordString);
	System.out.println(new BASE64Encoder().encode(password));
	byte[] cipherText = decoder.decodeBuffer(cipherString);
	System.out.println(new BASE64Encoder().encode(cipherText));
//	Security.addProvider(new com.sun.crypto.provider.SunJCE());
	DESKeySpec keySpec = new DESKeySpec(password);
	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	SecretKey key = keyFactory.generateSecret(keySpec);
	byte[] iv = new sun.misc.BASE64Decoder().decodeBuffer("t4JPbY+rXgk=");

	javax.crypto.spec.IvParameterSpec ips = new javax.crypto.spec.IvParameterSpec(
	iv);
	Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	cipher.init(Cipher.DECRYPT_MODE, key,ips);
	System.out.println("IV1:");
	System.out.println(new BASE64Encoder().encode(cipher.getIV()));
	return new String(cipher.doFinal(cipherText));
	} catch (Exception e) {
	e.printStackTrace();
	return null;
	}
	}

	public String encryptMessage(String plainText) {
	// TODO Auto-generated method stub
	try {
	byte[] password = new byte[8];
	SecureRandom random = new SecureRandom();
	random.nextBytes(password);
	System.out.println(new BASE64Encoder().encode(password));
//	Security.addProvider(new com.sun.crypto.provider.SunJCE());
	DESKeySpec keySpec = new DESKeySpec(password);
	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	SecretKey key = keyFactory.generateSecret(keySpec);
	Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	byte[] iv = new sun.misc.BASE64Decoder().decodeBuffer("t4JPbY+rXgk=");

	javax.crypto.spec.IvParameterSpec ips = new javax.crypto.spec.IvParameterSpec(
	iv);
	cipher.init(Cipher.ENCRYPT_MODE, key,ips);
	System.out.println("IV0:");
	System.out.println(new BASE64Encoder().encode(cipher.getIV()));
	byte[] cipherText = cipher.doFinal(plainText.getBytes());
	BASE64Encoder encoder = new BASE64Encoder();
	return encoder.encode(password).concat(encoder.encode(cipherText));
	} catch (Exception e) {
	e.printStackTrace();
	return null;
	}
	}

	public static void main(String[] args) {
	DESMessageEncryptor test1 = new DESMessageEncryptor();
	String cipher = test1.encryptMessage("programmer");
	System.out.println(cipher);
	System.out.println(test1.decryptMessage(cipher));
	}
	
}
