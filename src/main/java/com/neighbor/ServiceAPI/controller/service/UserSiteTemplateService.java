package com.neighbor.ServiceAPI.controller.service;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : UserSiteTemplateService.java
 * @Description : UserSiteTemplateService Class
 * @
 * @  ModifyDate      Modifier              Modify Content
 * @ ---------   		---------   -------------------------------
 * @ 2017.02.15          khc 					
 *
 * @author khc
 * @since 2017.02.15
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSsoft All right reserved.
 */
public interface UserSiteTemplateService {

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectNoticeList(Map mapParameter) throws Exception;
	public List selectNoticeListCount(Map mapParameter) throws Exception;
	
	public List noticeAddConfirm(Map mapParameter) throws Exception;
	
	public void fileInfoInsert(Map mapParameter) throws Exception;
	
	public void noticeInsert(Map mapParameter) throws Exception;
	
	public void noticeDelete(String[] list) throws Exception;
	
	public Map noticeDetail(Map mapParameter) throws Exception;
	
	public List fileInfoList(Map mapParameter) throws Exception;
	
	public void noticeUpdate(Map mapParameter) throws Exception;
	
	public int selectNoticeMaxCount(Map mapParameter) throws Exception;
	
	public void noticeUpdateHitCnt(Map mapParameter) throws Exception;
	
	public void fileInfoDelete(Map mapParameter) throws Exception;
	
	public List replyList(Map mapParameter) throws Exception;
	
	public void replyInsert(Map mapParameter) throws Exception;
	public Map attachInfoDetail(Map data);
	public void fileInfoInsertNew(Map mapParameter);
	
}