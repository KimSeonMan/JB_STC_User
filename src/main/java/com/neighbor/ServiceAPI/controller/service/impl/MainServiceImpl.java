package com.neighbor.ServiceAPI.controller.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.neighbor.ServiceAPI.controller.mapper.MainMapper;
import com.neighbor.ServiceAPI.controller.service.MainService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : MainServiceImpl.java
 * @Description : MainServiceImpl Implement Class
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

@Service("mainService")
public class MainServiceImpl extends EgovAbstractServiceImpl implements MainService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainServiceImpl.class);

	/** MainDAO */
	@Resource(name="mainMapper")
	private MainMapper mainMapper;

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectNoticeList(Map mapParameter) throws Exception {
		return mainMapper.selectNoticeList(mapParameter);
	}

	public Map selectMemberShipInfo(Map mapParameter) throws Exception {
		return mainMapper.selectMemberShipInfo(mapParameter);
	}

	public List selectReportList(Map mapParameter) throws Exception {
		return mainMapper.selectReportList(mapParameter);
	}
	
	public List selectQAList(Map mapParameter) throws Exception {
		return mainMapper.selectQAList(mapParameter);
	}
	
	/*public Map selectContentsInfo(String gubn, String CNTNTS_NO) throws Exception {
		return mainMapper.selectContentsInfo(gubn, CNTNTS_NO);
	}*/
	
	public Map selectContentsInfo(Map mapParameter) throws Exception{
		return mainMapper.selectContentsInfo(mapParameter);
	}
	
	
	public List selectQestnarList(Map mapParameter) throws Exception {
		return mainMapper.selectQestnarList(mapParameter);
	}

	public void insertKobusApiLog(Map mapParameter) throws Exception {
		mainMapper.insertKobusApiLog(mapParameter);
	}
	
}