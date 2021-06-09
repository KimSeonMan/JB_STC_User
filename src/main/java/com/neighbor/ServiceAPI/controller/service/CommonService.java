package com.neighbor.ServiceAPI.controller.service;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : CommonService.java
 * @Description : CommonService Class
 * @
 * @  ModifyDate      Modifier              Modify Content
 * @ ---------   		---------   -------------------------------
 * @ 2017.02.09          khc 					
 *
 * @author khc
 * @since 2017. 02.09
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSsoft All right reserved.
 */
public interface CommonService {

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectMenuList(Map mapParameter) throws Exception;
	
	public List selectCodeList(Map mapParameter) throws Exception;
	
	public List selectCodeDetailList(Map mapParameter) throws Exception;
	
	public List userSearchList(Map mapParameter) throws Exception;
	public List userSearchListCount(Map mapParameter) throws Exception;
	
	public List selectLinkSiteList(Map mapParameter) throws Exception;

	public List selectCenter(Map mapParameter) throws Exception;
	
}