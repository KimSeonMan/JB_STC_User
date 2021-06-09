package com.neighbor.ServiceAPI.controller.service;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : MainService.java
 * @Description : MainService Class
 * @
 * @  ModifyDate      Modifier              Modify Content
 * @ ---------   		---------   -------------------------------
 * @ 2016.03.09          ksh 					
 *
 * @author ksh
 * @since 2016. 09.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSsoft All right reserved.
 */
public interface MainService {

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectNoticeList(Map mapParameter) throws Exception;
	
	public List selectReportList(Map mapParameter) throws Exception;
	
	public List selectQAList(Map mapParameter) throws Exception;
	
	/*public Map selectContentsInfo(String gubn, String CNTNTS_NO) throws Exception;*/
	
	public List selectQestnarList(Map mapParameter) throws Exception;

	public Map selectContentsInfo(Map params) throws Exception;

	public Map selectMemberShipInfo(Map mapParameter) throws Exception;

	public void insertKobusApiLog(Map mapParameter) throws Exception;
}