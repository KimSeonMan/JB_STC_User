package com.neighbor.ServiceAPI.controller.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.neighbor.ServiceAPI.controller.mapper.CntrlMapper;
import com.neighbor.ServiceAPI.controller.service.CntrlService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : CntrlServiceImpl.java
 * @Description : CntrlService Implement Class
 * @
 * @  ModifyDate      Modifier              Modify Content
 * @ ---------   		---------   -------------------------------
 * @ 2017.02.15          최재영 					
 *
 * @author 최재영
 * @since 2017. 02.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSsoft All right reserved.
 */

@Service("cntrlService")
public class CntrlServiceImpl extends EgovAbstractServiceImpl implements CntrlService {
	
	private static final Logger Logger = LoggerFactory.getLogger(CntrlServiceImpl.class);
	
	/**CntrlMapper**/
	@Resource(name="cntrlMapper")
	private CntrlMapper cntrlMapper;
	
	/**
	 * @name : cntrlSearchWordList
	 * @description : 차량 위치관제 검색 
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List cntrlSearchCarWordList(Map mapParameter){
		List list = cntrlMapper.cntrlSearchCarWordList(mapParameter);
		return list;
	}
	
	
	/**
	 * @name : cntrlSearchCarMarkerList
	 * @description : 차량 위치관제 검색 (마커 전체)
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List cntrlSearchCarMarkerList(Map mapParameter){
		List list = cntrlMapper.cntrlSearchCarMarkerList(mapParameter);
		return list;
	}
	
	/**
	 * @name : cntrlSearchWordSupportList
	 * @description : 이동보조원 위치관제 검색
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List cntrlSearchWordSupportList(Map mapParameter){
		List list = cntrlMapper.cntrlSearchWordSupportList(mapParameter);
		return list;
	}
	
	/**
	 * @name : cntrlSearchWordUserList
	 * @description : 이동약자 위치관제 검색 
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List cntrlSearchWordUserList(Map mapParameter){
		List list = cntrlMapper.cntrlSearchWordUserList(mapParameter);
		return list;
	}

	/**
	 * @name : cntrlSearchCarWordCount
	 * @description : 차량 위치관제 검색 카운트
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public int cntrlSearchCarWordCount(Map mapParameter){
		return cntrlMapper.cntrlSearchCarWordCount(mapParameter);
	}

	/**
	 * @name : cntrlSearchSupportWordCount
	 * @description : 이동보조원 위치관제 검색 카운트
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public int cntrlSearchSupportWordCount(Map mapParameter){
		return cntrlMapper.cntrlSearchSupportWordCount(mapParameter);
	}
	

	/**
	 * @name : cntrlSearchUserWordCount
	 * @description : 이동약자 위치관제 검색 카운트
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public int cntrlSearchUserWordCount(Map mapParameter){
		return cntrlMapper.cntrlSearchUserWordCount(mapParameter);
	}
	
	/**
	 * @name : coord2addr
	 * @description : 좌표값 주소 변경
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public Map coord2addr(Map mapParameter){
		
		//http://apis.daum.net/local/geo/coord2addr?apikey=64798646a8a2e629fb05bc5f56c1bf9e&longitude=127.1224273159&latitude=37.4950952443&inputCoordSystem=WGS84&output=json
		String latitude = mapParameter.get("X").toString();
		String longitude = mapParameter.get("Y").toString();
		
		String url = "http://dapi.kakao.com/v2/local/geo/coord2Address?apikey="+daumKey+"&latitude="+latitude+"&longitude="+longitude+"&inputCoordSystem=WGS84&output=json";
		StringBuffer strBuffer = sendGetHttpRequest(url);
		Map map = new HashMap();
		return map;
	}
	
	
	/**
	 * @name : searchAddrList
	 * @description : 주소검색
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param String addr,String x_coor,String y_coor,String page,String count
	 */
	public Map searchAddrList(String addr,String page,String count){ //키워드 검색
		Map map = new HashMap();
		String url = null;
		try { // kakao api 수정 
			url = "http://dapi.kakao.com/v2/local/search/keyword.json?query="+URLEncoder.encode(addr,"UTF-8")+"&page="+page+"&size="+count;
			
			StringBuffer strBuffer = sendGetHttpRequest(url);
			if(strBuffer != null){
				map.put("status", "success");
				JSONObject jobj = strBuffer2Json(strBuffer); // 데이터 로그
				Logger.info("##################"+jobj);
				JSONObject meta = (JSONObject) jobj.get("meta");
				//JSONObject infoSame = (JSONObject) meta.get("same_name");
				JSONArray itemList = (JSONArray) jobj.get("documents");
				if(itemList.size()==0) {
					map = searchAddrList_2(addr, page, count);
				}else {
					//map.put("keyword",infoSame.get("keyword").toString());
					//map.put("selectRegion", infoSame.get("selected_region"));\
					map.put("type",1);
					map.put("count", meta.get("pageable_count").toString());
					map.put("totalCount", meta.get("total_count").toString());
					map.put("list", itemList);
				}
			}else{
				map.put("status", "fail");
			}
			
		} catch (UnsupportedEncodingException e) {
			map.put("status", "fail");
			Logger.error("UnsupportedEncodingException ",e);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			Logger.error("MalformedURLException ",e);
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			Logger.error("IOException ",e);
//			e.printStackTrace();
		}
		
		return map;
	}

	public Map searchAddrListByNaver(String searchWord, String pageNo) {
		Map result = new HashMap();
		String clientId = "Rz8JwEm9A6g242J4edIm";//애플리케이션 클라이언트 아이디값";
		String clientSecret = "JnhyZW0tqG";//애플리케이션 클라이언트 시크릿값";
		try {
			String url = "https://openapi.naver.com/v1/search/local.json?display=30&query="+URLEncoder.encode(searchWord, "UTF-8");

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header 헤더를 만들어주는것.
			con.setRequestProperty("User-Agent", "Chrome/version");
			con.setRequestProperty("Accept-Charset", "UTF-8");
			con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responseCode = con.getResponseCode();

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			String responseBody = "";

			responseBody = response.toString();

			if(responseBody != "" || !responseBody.equals("")){

				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject)jsonParser.parse(responseBody);
				JSONArray documents = (JSONArray)jsonObject.get("items");
				String end = jsonObject.get("display").toString();
				result.put("type",1);
				result.put("list", documents);
				result.put("totalCount",end);
			}


		} catch (Exception e) {
			Logger.error("Exception ",e);
		}
		return result;
	}

	public Map convertWGS(String x, String y){ //카카오

		Map result = new HashMap();
		String kakaoAppKey ="KakaoAK 46450fb278229d9bd30928ff88a4ff80";
		String strUrl = "https://dapi.kakao.com/v2/local/geo/transcoord.json?";
		String param = "";
		param += "x="+x;
		param += "&y="+y;
		param += "&input_coord=KTM";
		param += "&output_coord=WGS84";
		URL url;
		try {
			url = new URL(strUrl+param);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", kakaoAppKey);
			conn.connect();

			InputStream inputStream = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String line="";
			String responseBody = "";
			while((line = br.readLine()) != null){
				responseBody += line;
			}

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(responseBody);
			JSONArray documents = (JSONArray)jsonObject.get("documents");
			JSONObject document =(JSONObject)documents.get(0);

			result.put("x",document.get("x"));
			result.put("y",document.get("y"));

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Logger.error("MalformedURLException ",e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.error("IOException ",e);
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Logger.error("ParseException ",e);
			e.printStackTrace();
		}

		return result;
	}



	
	/**
	 * @name : searchAddrList
	 * @description : 주소검색
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param String addr,String page,String count
	 */
	public Map searchAddrList_2(String addr,String page,String count){ //주소검색
		Map map = new HashMap();
		String url = null;
		try { // kakao api 수정 
			url = "https://dapi.kakao.com/v2/local/search/address.json?query="+URLEncoder.encode(addr,"UTF-8")+"&page="+page+"&size="+count;
			
			StringBuffer strBuffer = sendGetHttpRequest(url);
			if(strBuffer != null){
				map.put("status", "success");
				JSONObject jobj = strBuffer2Json(strBuffer); // 데이터 로그
				Logger.info("##################"+jobj);
				JSONObject meta = (JSONObject) jobj.get("meta");
				JSONArray itemList = (JSONArray) jobj.get("documents");
				map.put("type",2);
				map.put("count", meta.get("pageable_count").toString());
				map.put("totalCount", meta.get("total_count").toString());
				map.put("list", itemList);
			}else{
				map.put("status", "fail");
			}
			
		} catch (UnsupportedEncodingException e) {
			map.put("status", "fail");
			Logger.error("UnsupportedEncodingException ",e);
		}
		
		return map;
	}
	
	/**
	 * @name : sendGetHttpRequest
	 * @description : HTTP통신
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param String url
	 */
	public StringBuffer sendGetHttpRequest(String strUrl){
		StringBuffer strBuffer = new StringBuffer();
		
		
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestProperty("Authorization", daumKey);
			conn.setReadTimeout(20000000);
			conn.setRequestMethod("GET");
			conn.connect();
			int responseCode = conn.getResponseCode();
			
			if(responseCode == HttpURLConnection.HTTP_OK){
				InputStream inputStream = conn.getInputStream();
				
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
				
				String str = null;
				
				str = br.readLine();
				
				while(str != null){
					strBuffer.append(str).append('\n');
					str = br.readLine();
				}
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Logger.error("MalformedURLException ",e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.error("IOException ",e);
			e.printStackTrace();
		}
		
		return strBuffer;
	}
	
	/**
	 * @name : strBuffer2Json
	 * @description : 스트링버퍼 JSON Object 반환
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param StringBuffer strBuffer
	 */
	public JSONObject strBuffer2Json(StringBuffer str){
		JSONParser json = new JSONParser();
		JSONObject obj=null;
		try {
			obj = (JSONObject)json.parse(str.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Logger.error("ParseException ",e);
			e.printStackTrace();
		}
		return obj;
		
	}
}
