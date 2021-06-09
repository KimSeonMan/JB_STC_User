package com.neighbor.ServiceAPI.controller.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * @Class Name : CntrlMapper.java
 * @Description : CntrlMapper DAO Class
 * @
 * @  ModifyDate      Modifier              Modify Content
 * @ ---------   		---------   -------------------------------
 * @ 2017.02.15          최재영 					
 *
 * @author 최재영
 * @since 2017. 02.15
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSsoft All right reserved.
 */

@Repository("cntrlMapper")
public class CntrlMapper extends EgovAbstractMapper{

	
	/**
	 * @name : cntrlSearchCarWordList
	 * @description : 차량 위치관제 검색
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List cntrlSearchCarWordList(Map mapParameter) {
		return selectList("cntrl.cntrlSearchCarWordList",mapParameter);
	}

	/**
	 * @name : cntrlSearchCarMarkerList
	 * @description : 차량 위치관제 검색 (마커 전체)
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List cntrlSearchCarMarkerList(Map mapParameter){
		return selectList("cntrl.cntrlSearchCarMarkerList",mapParameter);
	}
	
	
	/**
	 * @name : cntrlSearchWordSupportList
	 * @description : 이동보조원 위치관제 검색
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List cntrlSearchWordSupportList(Map mapParameter) {
		return selectList("cntrl.cntrlSearchWordSupportList",mapParameter);
		
	}
	
	/**
	 * @name : cntrlSearchWordUserList
	 * @description : 이동약자 위치관제 검색
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List cntrlSearchWordUserList(Map mapParameter) {
		return selectList("cntrl.cntrlSearchWordUserList",mapParameter);
	}

	/**
	 * @name : cntrlSearchCarWordCount
	 * @description : 차량 위치관제 검색 카운트
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public int cntrlSearchCarWordCount(Map mapParameter){
		return selectOne("cntrl.cntrlSearchCarWordCount",mapParameter);
	}
	
	/**
	 * @name : cntrlSearchSupportWordCount
	 * @description : 이동보조원 위치관제 검색 카운트
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public int cntrlSearchSupportWordCount(Map mapParameter){
		return selectOne("cntrl.cntrlSearchSupportWordCount",mapParameter);
	}
	
	
	/**
	 * @name : cntrlSearchUserWordCount
	 * @description : 이동약자 위치관제 검색 카운트
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public int cntrlSearchUserWordCount(Map mapParameter){
		return selectOne("cntrl.cntrlSearchUserWordCount",mapParameter);
	}
	
}
