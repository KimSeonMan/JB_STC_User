package com.neighbor.ServiceAPI.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.ksign.securedb.api.test.APITest;

import com.neighbor.ServiceAPI.exception.ApiException;

/**
 * 
 * @ClassName: RequestUtil
 * @Description： Copyright (c) 2014 by NeighborSystem
 * @author xuliguo
 * @date：2014年10月10日 下午4:04:21
 * @version V1.3
 * 
 *          Modification History: Date Author Version Discription
 *          ----------------
 *          -------------------------------------------------------------------
 *          2014年10月10日 xuliguo 1.0 getParaMap,Cookies 2014年10月17日 xuliguo 1.1
 *          transPagging 2014年10月17日 xuliguo 1.2
 *          transSearchDate,getHighChartData,transHighCharData 2014年10月27日
 *          xuliguo 1.3 getIpAddr
 */
public class RequestUtil {
	public static final Logger logger = Logger.getLogger(RequestUtil.class);
	private static final String SYNC = "SYNC";

	public static Map<Object, Object> getParaMap(HttpServletRequest request) {
		Map<Object, Object> paraMap = new HashMap<Object, Object>();
		Enumeration<String> paraNames = request.getParameterNames();
		for (Enumeration<String> e = paraNames; e.hasMoreElements();) {
			Object key = e.nextElement();
			Object value = request.getParameter((String) key);
			if (!value.equals(""))
				paraMap.put(key, value);
		}
		return paraMap;
	}

	public static void transPagging(Map paramMap) {
		try {
			if (paramMap.get("PAGEINDEX").equals(null)) {
				throw new NullPointerException("PAGE INDEX가 없습니다.");
			}
			int pageindex = Integer.parseInt(paramMap.get("PAGEINDEX").toString());

			paramMap.put("START_INDEX", (pageindex - 1) * 10 + 1);
			paramMap.put("END_INDEX", pageindex * 10);
		} catch (NullPointerException e) {
			throw new ApiException("PAGE INDEX가 없습니다.");
		} catch (Exception e) {
			throw new ApiException("PAGE INDEX 값을 확인해 주세요.");
		}

	}

	public static void transSearchDate(Map paramMap) {
		String TIMETYPE = paramMap.get("TIMETYPE").toString();
		String STARTDATE = paramMap.get("STARTDATE").toString();
		String ENDDATE = paramMap.get("ENDDATE").toString();
		ArrayList<String> X = new ArrayList<String>();
		String formart = "";
		if (TIMETYPE.equals(null) || STARTDATE.equals(null) || ENDDATE.equals(null)) {
			throw new ApiException("서버로 전달된 정보에 오류가 있습니다.");
		}
		switch (TIMETYPE) {
		case "HOURLY":
			formart = "yyyy-MM-dd HH";
			break;
		case "DAILY":
			formart = "yyyy-MM-dd";
			break;
		case "MONTHLY":
			formart = "yyyy-MM";
			break;
		default:
			break;
		}
		SimpleDateFormat df = new SimpleDateFormat(formart);
		Calendar cal = Calendar.getInstance();
		try {
			Date START = df.parse(STARTDATE);
			Date END = df.parse(ENDDATE);
			long temp = END.getTime() - START.getTime();
			if (temp > 0) {
				paramMap.remove("STARTDATE");
				paramMap.remove("ENDDATE");
				switch (TIMETYPE) {
				case "HOURLY":
					int hours = (int) (temp / 3600000);
					if (hours > 23)
						throw new ApiException("시간 24시간까지 조회 가능합니다. 다시 선택해 주세요.");
					else {
						cal.setTime(START);
						X.add(STARTDATE.substring(11, 13) + "시");
						for (int i = 0; i < hours; i++) {
							cal.add(Calendar.HOUR, 1);
							X.add(df.format(cal.getTime()).substring(11, 13) + "시");
						}
						cal.setTime(END);
						cal.add(Calendar.HOUR, 1);
						END = cal.getTime();
					}
					break;
				case "DAILY":
					long days = temp / 3600000 / 24;
					
					//lkh 수정 1일~31까지 검색되게 수정
					//if (days > 29)
					if (days > 30)
						throw new ApiException("일간 30일까지 조회가능합니다. 다시 선택해 주세요.");
					else {
						cal.setTime(START);
						X.add(STARTDATE.substring(8, 10) + "일");
						for (int i = 0; i < days; i++) {
							cal.add(Calendar.DATE, 1);
							X.add(df.format(cal.getTime()).substring(8, 10) + "일");
						}
						cal.setTime(END);
						// cal.add(Calendar.HOUR, 23);
						cal.add(Calendar.DATE, 1);
						END = cal.getTime();

					}
					break;
				case "MONTHLY":
					cal.setTime(START);
					cal.add(Calendar.MONTH, 23);
					Date endMax = cal.getTime();
					long months = endMax.getTime() - END.getTime();
					if (months < 0)
						throw new ApiException("월간 24개월 까지 조회 가능합니다. 다시 선택해 주세요.");
					else {
						cal.setTime(START);
						X.add(STARTDATE.substring(5, 7) + "월");
						while ((END.getTime() - cal.getTime().getTime()) != 0) {
							cal.add(Calendar.MONTH, 1);
							X.add(df.format(cal.getTime()).substring(5, 7) + "월");
						}
						cal.setTime(END);
						cal.add(Calendar.MONTH, 1);
						// cal.add(Calendar.DATE, -1);
						// cal.add(Calendar.HOUR, 23);
						END = cal.getTime();
					}
					break;
				default:
					throw new ApiException("선택된 날짜 타입이 없습니다. 시간, 일간 또는 월간을 다시 선택해 주세요.");
				}
			} else {
				throw new ApiException("잘못된 기간입니다. 날짜를 확인한 다음 다시 선택해 주세요.");
			}
			paramMap.put("STARTDATE", START);
			paramMap.put("ENDDATE", END);
		} catch (ApiException e) {
			throw (e);
		} catch (Exception e) {
			logger.error(e);
		}
		paramMap.put("X", X);

	}

	public static void getHighChartData(ArrayList X_, ArrayList Y_, List resultList, String TIMETYPE) {
		if (resultList.size() > 0) {	
			for (Object object : resultList) {	
				Map result = (Map) object;
				int callCount = Integer.parseInt(result.get("CALL_CNT").toString());
				if (TIMETYPE.equals("HOURLY")) {
					X_.add(result.get("CALL_HOURLY").toString().substring(8, 10) + "시");
				} else if (TIMETYPE.equals("DAILY")) {
					X_.add(result.get("CALL_HOURLY").toString().substring(6, 8) + "일");
				} else if (TIMETYPE.equals("MONTHLY")) {
					X_.add(result.get("CALL_HOURLY").toString().substring(4, 6) + "월");
				}
				Y_.add(callCount);
			}
		}
	}
	
	public static void getHighChartData1(ArrayList X_, ArrayList Y_, List resultList, String TIMETYPE) {
		if (resultList.size() > 0) {	
			for (Object object : resultList) {	
				Map result = (Map) object;
				int callCount = Integer.parseInt(result.get("CNT").toString());
				X_.add(result.get("S_CLASS_NM").toString());
				Y_.add(callCount);
			}
		}
	}

	public static void transHighCharData(ArrayList<String> X, ArrayList<String> X_, ArrayList<Integer> Y, ArrayList<Integer> Y_, String TIMETYPE) {
		Map chartData = new HashMap<String, String>();
		for (int i = 0; i < X_.size(); i++) {
			chartData.put(X_.get(i), Y_.get(i));
		}
		logger.info("charData " + chartData);
		logger.info("X_ " + X_);
		logger.info("Y_ " + Y_);
		for (int i = 0; i < X.size(); i++) {
			Boolean f = false;
			String x = X.get(i);
			/**/
			if (chartData.containsKey(x)) {
				int num = Integer.parseInt(chartData.get(x).toString());
				Y.add(i, num);
			} else {
				Y.add(i, 0);
			}

			/*
			 * for (int j = 0; j < X_.size(); j++) { if (X_.get(j).equals(x)) {
			 * f = true; Y.add(i, Y_.get(j)); continue; } } if (!f) { Y.add(i,
			 * 0); }
			 */
		}
		logger.info("finally Y " + Y);
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1"))
			ip = "localhost";
		return ip;
	}

	public static Boolean writeFile(FileItem fileItem, String path, String file_id, String file_extension) {
		if (path.equals(null)) {
			throw new ApiException("File Path 값이 없습니다.");
		}
		File f = new File(path);
		
		if (f.equals(null)) {
			throw new ApiException("File이 없습니다.");
		} else {
			if(path != null && !"".equals(path)) {
				path = Security.cleanXss(path); //외부 입력값 필터링 
			}
			if(file_id != null && !"".equals(file_id)) {
				file_id = Security.cleanXss(file_id); //외부 입력값 필터링 
			}
			if(file_extension != null && !"".equals(file_extension)) {
				file_extension = Security.cleanXss(file_extension); //외부 입력값 필터링 
			}
		}
		Boolean returnValue = true;
		try {
			if (!f.exists()) {
				//시큐어코딩(2016-12-05) 중요한 자원에 대한 잘못된 권한 설정(File)
				f.setExecutable(false, true);
				f.setReadable(true);
				f.setWritable(false, true);
				
				f.mkdirs();
			}
			if(file_extension != null && (file_extension.equals("zip") || file_extension.equals("hwp") || file_extension.equals("doc") || file_extension.equals("ppt") ||
					file_extension.equals("pptx") || file_extension.equals("xls") || file_extension.equals("xlsx") || file_extension.equals("txt") || file_extension.equals("bmp") ||
					file_extension.equals("jpg") || file_extension.equals("jpeg") || file_extension.equals("gif") || file_extension.equals("png"))) {
				fileItem.write(new File(path, file_id + "." + file_extension));
			} else {
				throw new ApiException("업로드할 수 없는 확장자 입니다.(첨부파일형식 : zip, hwp, doc, ppt, pptx, xls, xlsx, txt, bmp, jpg, jpeg, gif, png 가능함)");
			}
		} catch (ApiException e) {
			returnValue = false;
		} catch (FileNotFoundException e) {
			returnValue = false;
		} catch (Exception e) {
			returnValue = false;
		} finally {
			return returnValue;
		}
	}

	/**
	 * delete one file
	 * 
	 * @param sPath
	 *            file path
	 * @return success return true, fail return false
	 */
	public static boolean deleteFile(String sPath) {
		Boolean flag = false;
		File file = null;
		synchronized (SYNC) {
			try {
				if(sPath != null && !"".equals(sPath)) {
					sPath = Security.cleanXss(sPath); //외부 입력값 필터링 
				}
				file = new File(sPath);
				if (file.isFile() && file.exists()) {
					file.delete();
					flag = true;
				}
			} catch (NullPointerException e) {
				logger.error(e);
				flag = false;
			} catch (Exception e) {
				logger.error(e);
				flag = false;
			} finally {
				return flag;
			}
		}
	}

	/**
	 * delete directory and files in the directory
	 * 
	 * @param sPath
	 *            directory path
	 * @return delete success return true,
	 */
	public static boolean deleteDirectory(String sPath) {
		synchronized (SYNC) {
			Boolean flag = false;
			try {
				if(sPath != null && !"".equals(sPath)) {
					sPath = Security.cleanXss(sPath); //외부 입력값 필터링 
				}
				if (!sPath.endsWith(File.separator)) {
					sPath = sPath + File.separator;
				}
				File dirFile = new File(sPath);
				if (!dirFile.exists() || !dirFile.isDirectory()) {
					return false;
				}
				File[] files = dirFile.listFiles();
				if (files != null && files.length > 0) {
					for (int i = 0; i < files.length; i++) {
						if (files[i].isFile()) {
							flag = deleteFile(files[i].getAbsolutePath());
							if (!flag)
								break;
						} 
//						else {
//							flag = deleteDirectory(files[i].getAbsolutePath());
//							if (!flag)
//								break;
//						}
					}
				}
				if (!flag)
					return false;
				flag = dirFile.delete();
			} catch (NullPointerException e) {
				logger.error(e);
				flag = false;
			} finally {
				return flag;
			}
		}
	}

	@SuppressWarnings("finally")
	public static boolean downloadLocal(HttpServletResponse response, String FILE_PATH, String FILE_NM, String FILE_EXTENSION,
			String FILE_CONTENT_TYPE) throws IOException, FileNotFoundException {
		Boolean f = false;
		InputStream inStream = null;
		try {
			if(FILE_PATH != null && !"".equals(FILE_PATH)) {
				FILE_PATH = Security.cleanXss(FILE_PATH); //외부 입력값 필터링 
			}
			if(FILE_NM != null && !"".equals(FILE_NM)) {
				FILE_NM = Security.cleanXss(FILE_NM); //외부 입력값 필터링 
			}
			if(FILE_EXTENSION != null && !"".equals(FILE_EXTENSION)) {
				FILE_EXTENSION = Security.cleanXss(FILE_EXTENSION); //외부 입력값 필터링 
			}
			inStream = new FileInputStream(new File(FILE_PATH, FILE_NM + "." + FILE_EXTENSION));
			response.reset();
			response.setContentType(FILE_CONTENT_TYPE);
			response.addHeader("Content-Disposition", "attachment; filename=\"" + FILE_NM + "." + FILE_EXTENSION + "\"");
			byte[] b = new byte[100];
			int len;
			try {
				while ((len = inStream.read(b)) > 0)
					response.getOutputStream().write(b, 0, len);
				f = true;
			} catch (IOException e) {
				throw (e);
			} finally {
				inStream.close();
			}
		} catch (FileNotFoundException e1) {
			throw new Exception(e1);
		} finally {
			if (inStream != null) {
				inStream.close();
			}
			return f;
		}

	}

	private static String html = "";

	public static String getHtmlName(String filePath) {
		File root = new File(filePath);
		File[] files = root.listFiles();
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				if (!files[i].isDirectory()) {
//					getHtmlName(files[i].getAbsolutePath());
//				} else {
					String fileName = files[i].getName();
					String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
					if (extension.equals("html"))
						html = files[i].getAbsolutePath();
				}
			}
		}
		return html;
	}
}
