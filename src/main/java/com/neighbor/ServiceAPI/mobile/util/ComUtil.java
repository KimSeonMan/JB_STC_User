package com.neighbor.ServiceAPI.mobile.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComUtil {

	private static final Logger logger = LoggerFactory.getLogger(ComUtil.class);
	public static final String KEY = "handicapped";
	public static final String KEY_KOBUS = "1234567890123456";
	
	public ComUtil() {
	}

	public static boolean isNull(String str) {

		return "".equals(str) || str == null ? true : false;
		// return "".equals(str) || str.length() == 0 || str == null
		// ? true
		// : false;
	}
	public static boolean isNull(Object obj) {
		return obj == null ? true : false;
	}
	
	public static String getTime(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSSS");
		
		Calendar calendar = Calendar.getInstance();
		
		return dateFormat.format(calendar.getTime()).toString();
	}
	
	public static String getTrid(String id){
		String trid = "7Q=n_"+id+getTime();
		return trid;
	}
	
	/**
	 * 공통 코드 변환.
	 * 
	 * @param list
	 * @return
	 */
	public static LinkedHashMap<String, String> convertToCodeMap(final List<HashMap<String, Object>> list) {
		return convertToCodeMap(list, "CODE", "CODE_NM");
	}
	
	/**
	 * 공통 코드 변환.
	 * @param list
	 * @param code
	 * @param codeNm
	 * @return
	 */
	public static LinkedHashMap<String, String> convertToCodeMap(final List<HashMap<String, Object>> list, final String code, final String codeNm) {
		LinkedHashMap<String, String> map = null;
		
		try {
			map = new LinkedHashMap<String, String>();
			for (HashMap<String, Object> hashMap : list) {
				map.put(hashMap.get(code).toString(), hashMap.get(codeNm).toString());
			}
		} catch (Exception e) {
			logger.error(ComUtil.getStacktraceString(e));
			map = new LinkedHashMap<String, String>();
		}
		
		return map;
	}
	/**
     * Exception stacktrace 문자열.
     * @param e exception
     * @return stacktrace 문자열. 
     */
    public static String getStacktraceString(final Exception e) {
        String stacktrace = e.getMessage() + "\r\n";
        StackTraceElement[] stack = e.getStackTrace();
        if (e.getStackTrace() != null) {
            for (int i = 0; i < stack.length && i < 10; i++) {
                stacktrace += stack[i].toString() + "\r\n";
            }
        }
        return stacktrace; 
    }

}
