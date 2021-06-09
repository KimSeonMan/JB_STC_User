package com.neighbor.ServiceAPI.controller.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * @Class Name : StatsMapper.java
 * @Description : StatsMapper DAO Class
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

@Repository("statsMapper")
public class StatsMapper extends EgovAbstractMapper {

	/**
	 * @name : getStatsList
	 * @description : 운행(이용)현황 리스트
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List getStatsList(Map mapParameter){
		return selectList("stats.getSelectList",mapParameter);
	}
	
	
	/**
	 * @name : getToDayAllocDataList
	 * @description : 일일배차 조회
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List getToDayAllocDataList(Map mapParameter){
		return selectList("stats.getToDayAllocDataList",mapParameter);
	}
	
	/**
	 * @name : getToDayUseStatList
	 * @description : 일일이용자현황
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param Map mapParameter
	 */
	public List getToDayUseStatList(Map mapParameter){
		return selectList("stats.getToDayUseStatList",mapParameter);
	}
	
}
