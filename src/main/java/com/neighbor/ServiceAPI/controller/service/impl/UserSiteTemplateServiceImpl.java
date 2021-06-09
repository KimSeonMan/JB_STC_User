package com.neighbor.ServiceAPI.controller.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.neighbor.ServiceAPI.controller.mapper.UserSiteTemplateMapper;
import com.neighbor.ServiceAPI.controller.service.UserSiteTemplateService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : UserSiteTemplateServiceImpl.java
 * @Description : UserSiteTemplateServiceImpl Implement Class
 * @
 * @  ModifyDate      Modifier              Modify Content
 * @ ---------   		---------   -------------------------------
 * @ 2017.02.15          khc 					
 *
 * @author khc
 * @since 2017. 02.15
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSsoft All right reserved.
 */

@Service("userSiteTemplateService")
public class UserSiteTemplateServiceImpl extends EgovAbstractServiceImpl implements UserSiteTemplateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserSiteTemplateServiceImpl.class);

	/** UserSiteTemplateDAO */
	@Resource(name="userSiteTemplateMapper")
	private UserSiteTemplateMapper userSiteTemplateMapper;

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectNoticeList(Map mapParameter) throws Exception {
		return userSiteTemplateMapper.selectNoticeList(mapParameter);
	}
	public List selectNoticeListCount(Map mapParameter) throws Exception {
		return userSiteTemplateMapper.selectNoticeListCount(mapParameter);
	}
	
	public List noticeAddConfirm(Map mapParameter) throws Exception {
		return userSiteTemplateMapper.noticeAddConfirm(mapParameter);
	}
	
	public void fileInfoInsert(Map mapParameter) throws Exception {
		userSiteTemplateMapper.fileInfoInsert(mapParameter);
	}
	
	public void noticeInsert(Map mapParameter) throws Exception {
		userSiteTemplateMapper.noticeInsert(mapParameter);
	}
	
	public void noticeDelete(String[] list) throws Exception {
		userSiteTemplateMapper.noticeDelete(list);
	}
	
	public Map noticeDetail(Map mapParameter) throws Exception {
		return userSiteTemplateMapper.noticeDetail(mapParameter);
	}
	
	public List fileInfoList(Map mapParameter) throws Exception {
		return userSiteTemplateMapper.fileInfoList(mapParameter);
	}
	
	public void noticeUpdate(Map mapParameter) throws Exception {
		userSiteTemplateMapper.noticeUpdate(mapParameter);
	}
	
	public int selectNoticeMaxCount(Map mapParameter) throws Exception {
		return userSiteTemplateMapper.selectNoticeMaxCount(mapParameter);
	}
	
	public void noticeUpdateHitCnt(Map mapParameter) throws Exception {
		userSiteTemplateMapper.noticeUpdateHitCnt(mapParameter);
	}
	
	public void fileInfoDelete(Map mapParameter) throws Exception {
		userSiteTemplateMapper.fileInfoDelete(mapParameter);
	}
	
	public List replyList(Map mapParameter) throws Exception {
		return userSiteTemplateMapper.replyList(mapParameter);
	}
	
	public void replyInsert(Map mapParameter) throws Exception {
		userSiteTemplateMapper.replyInsert(mapParameter);
	}
	@Override
	public Map attachInfoDetail(Map data) {
		return userSiteTemplateMapper.attachInfoDetail(data);
	}
	@Override
	public void fileInfoInsertNew(Map mapParameter) {
		userSiteTemplateMapper.fileInfoInsertNew(mapParameter);		
	}
	
}