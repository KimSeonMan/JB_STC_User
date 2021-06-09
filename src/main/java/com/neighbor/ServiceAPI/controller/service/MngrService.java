package com.neighbor.ServiceAPI.controller.service;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : MngrService.java
 * @Description : MngrService Class
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
public interface MngrService {

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectUserMngList(Map mapParameter) throws Exception;
	public List selectUserMngListCount(Map mapParameter) throws Exception;
	public List selectCenter() throws Exception;
	public List selectMemberCode() throws Exception;
	public List selectEmailList() throws Exception;
	public List selectselectTroblCode() throws Exception;
	public List selectWheelchair() throws Exception;
	public List selectAreaTelNo()  throws Exception;
	public List selectPhoneNo()  throws Exception;
	
	public Map selectUserMngDetail(Map mapParameter) throws Exception;
	
	public void userMngUpdate(Map mapParameter) throws Exception;
	
	
	public List selectMenuMngList(Map mapParameter) throws Exception;
	public List selectMenuMngListCount(Map mapParameter) throws Exception;
	
	public Map selectMenuMngDetail(Map mapParameter) throws Exception;
	
	public void menuMngInsert(Map mapParameter) throws Exception;
	
	public void menuMngUpdate(Map mapParameter) throws Exception;
	
	public void menuMngDelete(String gubn, String[] list) throws Exception;
	
	public Map menuMngDoubleChk(Map mapParameter) throws Exception;
	
	public List selectMenuMngAccessList(Map mapParameter) throws Exception;
	
	public void menuMngAccessAdd(String menuId, String[] list, String cnterCd) throws Exception;
	
	public List menuMngSelectboxList(Map mapParameter) throws Exception;
	
	
	public List selectContentsMngList(Map mapParameter) throws Exception;
	public List selectContentsMngListCount(Map mapParameter) throws Exception;
	
	public void contentsMngInsert(Map mapParameter) throws Exception;
	
	public void contentsMngUpdate(Map mapParameter) throws Exception;
	
	public void contentsMngDelete(String[] list) throws Exception;
	
	public Map selectContentsMngDetail(Map mapParameter) throws Exception;
	
	
	public List selectCommonCDMngListA(Map mapParameter) throws Exception;
	public List selectCommonCDMngListCountA(Map mapParameter) throws Exception;
	
	public List selectCommonCDMngListB(Map mapParameter) throws Exception;
	public List selectCommonCDMngListCountB(Map mapParameter) throws Exception;
	
	public Map selectCommonCDMngDetail(Map mapParameter) throws Exception;
	
	public Map selectCommonCDMngIdCheck(Map mapParameter) throws Exception;
	
	public void commonCDMngInsert(Map mapParameter) throws Exception;
	
	public void commonCDMngUpdate(Map mapParameter) throws Exception;
	
	
	public List selectSiteMngList(Map mapParameter) throws Exception;
	public List selectSiteMngListCount(Map mapParameter) throws Exception;
	
	public void siteMngInsert(Map mapParameter) throws Exception;
	
	public void siteMngDelete(String[] list) throws Exception;
	
	public Map selectSiteMngDetail(Map mapParameter) throws Exception;
	
	public void siteMngUpdate(Map mapParameter) throws Exception;
	
	
	public List selectQestnarMngList(Map mapParameter) throws Exception;
	public List selectQestnarMngListCount(Map mapParameter) throws Exception;
	
	public void qestnarMngInsert(Map mapParameter, String gubn) throws Exception;
	
	public void qestnarMngDelete(String[] list) throws Exception;
	
	public List selectQestnarMngDetail(Map mapParameter) throws Exception;
	
	public List selectQestnarMngResult(Map mapParameter) throws Exception;
	public List selectEmgHistoryList(Map mapParameter);
	public List selectEmgHistoryListCount(Map mapParameter);
	public List selectStatHistoryList(Map mapParameter);
	public List selectStatHistoryListCount(Map mapParameter);
	public List smsHistoryList(Map mapParameter);
	public List smsHistoryListCount(Map mapParameter);
	public List smsTypeList();
	
}