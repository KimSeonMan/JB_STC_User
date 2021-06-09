package com.neighbor.ServiceAPI.controller.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.neighbor.ServiceAPI.controller.mapper.StatsMapper;
import com.neighbor.ServiceAPI.controller.service.StatsService;

/**
 * @Class Name : StatsServiceImpl.java
 * @Description : StatsService Implement Class
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

@Service("statsService")
public class StatsServiceImpl implements StatsService {

	@Resource(name="statsMapper")
	private StatsMapper statsMapper;
	
	/**
	 * @name : getStatsList
	 * @description : 운행 이용현황 조회
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param String startDate, String endDate
	 */
	public List getStatsList(String startDate, String endDate) {
		Map mapParameter = new HashMap();
		mapParameter.put("startDate", startDate);
		mapParameter.put("endDate", endDate);
		return statsMapper.getStatsList(mapParameter);
	}
	
	
	/**
	 * @name : getToDayAllocDataList
	 * @description : 일일배차 조회
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param String searchDate
	 */
	public List getToDayAllocDataList(String searchDate){
		Map map = new HashMap();
		map.put("searchDate", searchDate);
		
		return statsMapper.getToDayAllocDataList(map);
	}
	
	/**
	 * @name : getToDayUseStatList
	 * @description : 일일이용자현황
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param String searchDate
	 */
	public List getToDayUseStatList(String searchDate){
		Map map = new HashMap();
		map.put("searchDate", searchDate);
		
		return statsMapper.getToDayUseStatList(map);
	}

}
