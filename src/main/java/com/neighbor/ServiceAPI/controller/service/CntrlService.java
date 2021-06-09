package com.neighbor.ServiceAPI.controller.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;


/**
 * @Class Name : CntrlService.java
 * @Description : CntrlServiceImpl Class
 * @
 * @  ModifyDate      Modifier              Modify Content
 * @ ---------   		---------   -------------------------------
 * @ 2017.02.15          최재영 					
 *
 * @author 최재영
 * @since 2017.02.15
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSsoft All right reserved.
 */
public interface CntrlService {

//	public static final String daumKey = "dd4800ee3b6b6d2739e6e689a5f47370";
	public static final String daumKey = "KakaoAK 851bc24da96e4876c5c7e1c0389d29de";
	/**
	 * @name : cntrlSearchCarWordList
	 * @description : 차량 위치관제 검색 
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List cntrlSearchCarWordList(Map mapParameter);
	
	/**
	 * @name : cntrlSearchCarMarkerList
	 * @description : 차량 위치관제 검색 (마커 전체)
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List cntrlSearchCarMarkerList(Map mapParameter);
	
	/**
	 * @name : cntrlSearchWordSupportList
	 * @description : 이동보조원 위치관제 검색
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List cntrlSearchWordSupportList(Map mapParameter);
	
	/**
	 * @name : cntrlSearchWordUserList
	 * @description : 이동약자 위치관제 검색 
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List cntrlSearchWordUserList(Map mapParameter);
	
	
	/**
	 * @name : cntrlSearchCarWordCount
	 * @description : 차량 위치관제 검색 카운트
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public int cntrlSearchCarWordCount(Map mapParameter);
	
	/**
	 * @name : cntrlSearchSupportWordCount
	 * @description : 이동보조원 위치관제 검색 카운트
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public int cntrlSearchSupportWordCount(Map mapParameter);
	
	/**
	 * @name : cntrlSearchUserWordCount
	 * @description : 이동약자 위치관제 검색 카운트
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public int cntrlSearchUserWordCount(Map mapParameter);
	
	
	/**
	 * @name : coord2addr
	 * @description : 좌표값 주소 변경
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public Map coord2addr(Map mapParameter);
	
	
	/**
	 * @name : searchAddrList
	 * @description : 주소검색
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param String addr,String x_coor,String y_coor,String page,String count
	 */
	public Map searchAddrList(String addr,String page,String count);
	
	
	/**
	 * @name : sendGetHttpRequest
	 * @description : HTTP통신
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param String url
	 */
	public StringBuffer sendGetHttpRequest(String url);
	
	
	/**
	 * @name : strBuffer2Json
	 * @description : 스트링버퍼 JSON Object 반환
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param StringBuffer strBuffer
	 */
	public JSONObject strBuffer2Json(StringBuffer str);


	public Map searchAddrListByNaver(String searchWord, String pageNo);
	public Map convertWGS(String x, String y);
	
}
