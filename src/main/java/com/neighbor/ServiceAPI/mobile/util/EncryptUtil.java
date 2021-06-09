package com.neighbor.ServiceAPI.mobile.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EncryptUtil {
	private static final String TAG = "EncryptUtil";
	
	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
	public static final String IV = "1234567890123456";
	
	public static String encryptData(String keyvalue, String text) {
		 
	    try {
	        final String key = keyvalue;
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        byte[] keyBytes = new byte[16];
	        byte[] b = key.getBytes("UTF-8");
	        int len = b.length;
	        if (len > keyBytes.length) len = keyBytes.length;
	        System.arraycopy(b, 0, keyBytes, 0, len);
	        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
	            IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
	            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
	 
	            byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
	        return new String(Base64.encodeBase64(results));
	    } catch (Exception e) {
//	        LogUtil.e(TAG, "데이터 암호화 실패.");
	    	logger.info("데이터 암호화 실페 : {}", e.getMessage());
	        return null;
	    }
	}

	public static String encryptData256(String keyvalue, String text) {

		try {
			final String key = keyvalue;
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] keyBytes = new byte[16];
			byte[] keyBytes32 = new byte[32];
			byte[] b = key.getBytes("UTF-8");
			int len = b.length;
			if (len > keyBytes32.length) len = keyBytes32.length;
			System.arraycopy(b, 0, keyBytes32, 0, len);
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes32, "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

			byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(results));
		} catch (Exception e) {
//	        LogUtil.e(TAG, "데이터 암호화 실패.");
			logger.info("데이터 암호화 실패 : {}", e.getMessage());
			logger.error("데이터 암호화 실패 : {}", e.getMessage());
			return null;
		}
	}

	public static String decryptData(String keyvalue, String text) {
	        try {
//	            final String key = keyvalue.substring(0,16);
	        	final String key = keyvalue;
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	            byte[] keyBytes = new byte[16];
	            byte[] b = key.getBytes("UTF-8");
	            int len = b.length;
	            if (len > keyBytes.length) len = keyBytes.length;
	            System.arraycopy(b, 0, keyBytes, 0, len);
	            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
	            IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
	            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
	            byte[] results = cipher.doFinal(Base64.decodeBase64(text.getBytes()));
	            return new String(results, "UTF-8");
	        } catch (Exception e) {
//	            LogUtil.e(TAG, "데이터 복호화 실패");
	        	logger.info("데이터 복호화 실페 : {}", e.getMessage());
	            return null;
	        }
	    }

	public static String decryptDataForKobus(String keyvalue, String text) {
		try {
//            final String key = keyvalue.substring(0,16);
			final String key = keyvalue;
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] keyBytes = new byte[16];
//			byte[] keyBytes32 = new byte[32];
			byte[] b = key.getBytes("UTF-8");
			int len = b.length;
			if (len > keyBytes.length) len = keyBytes.length;
			System.arraycopy(b, 0, keyBytes, 0, len);
			SecretKeySpec keySpec = new SecretKeySpec(b, "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
			byte[] results = cipher.doFinal(Base64.decodeBase64(text.getBytes()));
			return new String(results, "UTF-8");
		} catch (Exception e) {
//            LogUtil.e(TAG, "데이터 복호화 실패");
			logger.info("데이터 복호화 실패 : {}", e.getMessage());
			logger.error("데이터 복호화 실패 : {}", e.getMessage());
			return null;
		}
	}
}
