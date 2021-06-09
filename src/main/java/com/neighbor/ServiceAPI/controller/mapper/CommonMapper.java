package com.neighbor.ServiceAPI.controller.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * @Class Name : CommonServiceImpl.java
 * @Description : CommonServiceImpl DAO Class
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

@Repository("commonMapper")
public class CommonMapper extends EgovAbstractMapper {

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectMenuList(Map mapParameter) throws Exception {
		return selectList("common.selectMenuList", mapParameter);
	}
	
	public List userSearchList(Map mapParameter) throws Exception {
		return selectList("common.userSearchList", mapParameter);
	}
	
	public List selectCodeList(Map mapParameter) throws Exception {
		return selectList("common.selectCodeList", mapParameter);
	}
	
	public List selectCodeDetailList(Map mapParameter) throws Exception {
		return selectList("common.selectCodeDetailList", mapParameter);
	}
	public List userSearchListCount(Map mapParameter) throws Exception {
		return selectList("common.userSearchListCount", mapParameter);
	}
	
	public List selectLinkSiteList(Map mapParameter) throws Exception {
		return selectList("common.selectLinkSiteList", mapParameter);
	}
	
	public List selectCenter(Map mapParameter) throws Exception {
		return selectList("common.selectCenter", mapParameter);
	}
	
	
	
}