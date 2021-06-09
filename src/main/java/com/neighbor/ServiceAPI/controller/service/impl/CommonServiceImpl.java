package com.neighbor.ServiceAPI.controller.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.neighbor.ServiceAPI.controller.mapper.CommonMapper;
import com.neighbor.ServiceAPI.controller.service.CommonService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : CommonServiceImpl.java
 * @Description : CommonServiceImpl Implement Class
 * @
 * @  ModifyDate      Modifier              Modify Content
 * @ ---------   		---------   -------------------------------
 * @ 2017.02.09          khc 					
 *
 * @author khc
 * @since 2017. 02.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSsoft All right reserved.
 */

@Service("commonService")
public class CommonServiceImpl extends EgovAbstractServiceImpl implements CommonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonServiceImpl.class);

	/** CommonDAO */
	@Resource(name="commonMapper")
	private CommonMapper commonMapper;

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectMenuList(Map mapParameter) throws Exception {
		return commonMapper.selectMenuList(mapParameter);
	}
	
	public List selectCodeList(Map mapParameter) throws Exception {
		return commonMapper.selectCodeList(mapParameter);
	}
	
	public List selectCodeDetailList(Map mapParameter) throws Exception {
		return commonMapper.selectCodeDetailList(mapParameter);
	}
	
	public List userSearchList(Map mapParameter) throws Exception {
		return commonMapper.userSearchList(mapParameter);
	}
	public List userSearchListCount(Map mapParameter) throws Exception {
		return commonMapper.userSearchListCount(mapParameter);
	}
	
	public List selectLinkSiteList(Map mapParameter) throws Exception {
		return commonMapper.selectLinkSiteList(mapParameter);
	}
	
	public List selectCenter(Map mapParameter) throws Exception {
		return commonMapper.selectCenter(mapParameter);
	}
	
	
	
}