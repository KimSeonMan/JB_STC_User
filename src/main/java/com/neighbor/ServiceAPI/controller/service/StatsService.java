package com.neighbor.ServiceAPI.controller.service;

import java.util.List;

/**
 * @Class Name : StatsService.java
 * @Description : StatsServiceImpl Class
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

public interface StatsService {
	
	/**
	 * @name : getStatsList
	 * @description : 운행 이용현황 조회
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param String startDate, String endDate
	 */
	public List getStatsList(String startDate, String endDate);
	
	
	/**
	 * @name : getToDayAllocDataList
	 * @description : 일일배차 조회
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param String searchDate
	 */
	public List getToDayAllocDataList(String searchDate);
	
	/**
	 * @name : getToDayUseStatList
	 * @description : 일일이용자현황
	 * @date : 2017. 02. 15
	 * @author : 최재영
	 * @param String searchDate
	 */
	public List getToDayUseStatList(String searchDate);
	
}
