package com.neighbor.ServiceAPI.common.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 1. 기능 : 해시함수 관리 클래스.
 * <p>
 * 2. 처리개요 :
 * <p>
 * 3. 주의사항 :
 * <p>
 * 
 * <pre>
 *  <b>History:</b> 
 *     작성자 : 김성현, 1.0, 2014/08/18  초기 작성
 * </pre>
 * 
 * @author 최종 수정자 : 김성현
 * @version 1.0
 * @see <p/>
 */
public class Security {

	public Security() {

	}

	public static String sqlInjectionCheck(String str) {
		// TODO Auto-generated method stub
		if (str != null) {
			// string 객체 생성
			String filerStr = new String(str);
			// injection array
			String[] arr = { "\'", ":", ";", "--", "exec", "drop", "update", "delete", "select", "insert", "create", "sp_", "xp_" };

			// string에 포함된 injection을 ""로 대체
			for (int i = 0; i < arr.length; i++) {
				filerStr = filerStr.replaceAll(arr[i], "");
			}

			return filerStr;
		} else
			return str;

	}

	public static String cleanXss(String value) {
		// TODO Auto-generated method stub
		if (value != null) {
			value = value.replaceAll("&", "&amp;");
			value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			value = value.replaceAll("#", "&#35");
			value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
			value = value.replaceAll("'", "&#39;");
			value = value.replaceAll("\"", "&quot;");

			value = value.replaceAll("eval\\((.*)\\)", "");
			value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
			value = value.replaceAll("script", "");
			return value;
		} else
			return value;

	}

}
