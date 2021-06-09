package com.neighbor.ServiceAPI.controller.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.neighbor.ServiceAPI.controller.mapper.MngrMapper;
import com.neighbor.ServiceAPI.controller.service.MngrService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : MngrServiceImpl.java
 * @Description : MngrServiceImpl Implement Class
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

@Service("mngrService")
public class MngrServiceImpl extends EgovAbstractServiceImpl implements MngrService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MngrServiceImpl.class);

	/** MngrDAO */
	@Resource(name="mngrMapper")
	private MngrMapper mngrMapper;

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectUserMngList(Map mapParameter) throws Exception {
		return mngrMapper.selectUserMngList(mapParameter);
	}
	public List selectUserMngListCount(Map mapParameter) throws Exception {
		return mngrMapper.selectUserMngListCount(mapParameter);
	}
	
	public List selectCenter() throws Exception {
		return mngrMapper.selectCenter();
	}
	
	public List selectEmailList() throws Exception {
		return mngrMapper.selectEmailList();
	}
	
	public List selectselectTroblCode() throws Exception{
		return mngrMapper.selectTroblCode();
	}
	
	public List selectWheelchair() throws Exception{
		return mngrMapper.selectWheelchair();
	}
	
	public List selectMemberCode() throws Exception {
		return mngrMapper.selectMemberCode();
	}
	
	public Map selectUserMngDetail(Map mapParameter) throws Exception {
		return mngrMapper.selectUserMngDetail(mapParameter);
	}
	
	public void userMngUpdate(Map mapParameter) throws Exception {
		mngrMapper.userMngUpdate(mapParameter);
	}
	
	
	public List selectMenuMngList(Map mapParameter) throws Exception {
		return mngrMapper.selectMenuMngList(mapParameter);
	}
	public List selectMenuMngListCount(Map mapParameter) throws Exception {
		return mngrMapper.selectMenuMngListCount(mapParameter);
	}
	
	public Map selectMenuMngDetail(Map mapParameter) throws Exception {
		return mngrMapper.selectMenuMngDetail(mapParameter);
	}
	
	public void menuMngInsert(Map mapParameter) throws Exception {
		mngrMapper.menuMngInsert(mapParameter);
	}
	
	public void menuMngUpdate(Map mapParameter) throws Exception {
		mngrMapper.menuMngUpdate(mapParameter);
	}
	
	public void menuMngDelete(String gubn, String[] list) throws Exception {
		mngrMapper.menuMngDelete(gubn, list);
	}
	
	public Map menuMngDoubleChk(Map mapParameter) throws Exception {
		return mngrMapper.menuMngDoubleChk(mapParameter);
	}
	
	public List selectMenuMngAccessList(Map mapParameter) throws Exception {
		return mngrMapper.selectMenuMngAccessList(mapParameter);
	}
	
	public void menuMngAccessAdd(String menuId, String[] list, String cnterCd) throws Exception {
		mngrMapper.menuMngAccessAdd(menuId, list, cnterCd);
	}
	
	public List menuMngSelectboxList(Map mapParameter) throws Exception {
		return mngrMapper.menuMngSelectboxList(mapParameter);
	}
	
	
	public List selectContentsMngList(Map mapParameter) throws Exception {
		return mngrMapper.selectContentsMngList(mapParameter);
	}
	public List selectContentsMngListCount(Map mapParameter) throws Exception {
		return mngrMapper.selectContentsMngListCount(mapParameter);
	}

	public void contentsMngInsert(Map mapParameter) throws Exception {
		mngrMapper.contentsMngInsert(mapParameter);
	}
	
	public void contentsMngUpdate(Map mapParameter) throws Exception {
		mngrMapper.contentsMngUpdate(mapParameter);
	}
	
	public void contentsMngDelete(String[] list) throws Exception {
		mngrMapper.contentsMngDelete(list);
	}
	
	public Map selectContentsMngDetail(Map mapParameter) throws Exception {
		return mngrMapper.selectContentsMngDetail(mapParameter);
	}
	
	
	public List selectCommonCDMngListA(Map mapParameter) throws Exception {
		return mngrMapper.selectCommonCDMngListA(mapParameter);
	}
	public List selectCommonCDMngListCountA(Map mapParameter) throws Exception {
		return mngrMapper.selectCommonCDMngListCountA(mapParameter);
	}
	
	public List selectCommonCDMngListB(Map mapParameter) throws Exception {
		return mngrMapper.selectCommonCDMngListB(mapParameter);
	}
	public List selectCommonCDMngListCountB(Map mapParameter) throws Exception {
		return mngrMapper.selectCommonCDMngListCountB(mapParameter);
	}
	
	public Map selectCommonCDMngDetail(Map mapParameter) throws Exception {
		return mngrMapper.selectCommonCDMngDetail(mapParameter);
	}
	
	public Map selectCommonCDMngIdCheck(Map mapParameter) throws Exception {
		return mngrMapper.selectCommonCDMngIdCheck(mapParameter);
	}
	
	public void commonCDMngInsert(Map mapParameter) throws Exception {
		mngrMapper.commonCDMngInsert(mapParameter);
	}
	
	public void commonCDMngUpdate(Map mapParameter) throws Exception {
		mngrMapper.commonCDMngUpdate(mapParameter);
	}
	
	
	public List selectSiteMngList(Map mapParameter) throws Exception {
		return mngrMapper.selectSiteMngList(mapParameter);
	}
	public List selectSiteMngListCount(Map mapParameter) throws Exception {
		return mngrMapper.selectSiteMngListCount(mapParameter);
	}
	
	public void siteMngInsert(Map mapParameter) throws Exception {
		mngrMapper.siteMngInsert(mapParameter);
	}
	
	public void siteMngDelete(String[] list) throws Exception {
		mngrMapper.siteMngDelete(list);
	}
	
	public Map selectSiteMngDetail(Map mapParameter) throws Exception {
		return mngrMapper.selectSiteMngDetail(mapParameter);
	}
	
	public void siteMngUpdate(Map mapParameter) throws Exception {
		mngrMapper.siteMngUpdate(mapParameter);
	}
	
	
	public List selectQestnarMngList(Map mapParameter) throws Exception {
		return mngrMapper.selectQestnarMngList(mapParameter);
	}
	public List selectQestnarMngListCount(Map mapParameter) throws Exception {
		return mngrMapper.selectQestnarMngListCount(mapParameter);
	}
	
	public void qestnarMngInsert(Map mapParameter, String gubn) throws Exception {
		mngrMapper.qestnarMngInsert(mapParameter, gubn);
	}
	
	public void qestnarMngDelete(String[] list) throws Exception {
		mngrMapper.qestnarMngDelete(list);
	}
	
	public List selectQestnarMngDetail(Map mapParameter) throws Exception {
		return mngrMapper.selectQestnarMngDetail(mapParameter);
	}
	
	public List selectQestnarMngResult(Map mapParameter) throws Exception {
		return mngrMapper.selectQestnarMngResult(mapParameter);
	}
	@Override
	public List selectEmgHistoryList(Map mapParameter) {
		if(mapParameter.get("CNTER_CD").toString().contains("WDR")){
			mapParameter.put("cnterState", "WDR");
		} else if(mapParameter.get("CNTER_CD").toString().contains("ARE")) {
			mapParameter.put("cnterState", "ARE");
		}
		
		return mngrMapper.selectEmgHistoryList(mapParameter);
	}
	@Override
	public List selectEmgHistoryListCount(Map mapParameter) {
		if(mapParameter.get("CNTER_CD").toString().contains("WDR")){
			mapParameter.put("cnterState", "WDR");
		} else if(mapParameter.get("CNTER_CD").toString().contains("ARE")) {
			mapParameter.put("cnterState", "ARE");
		}
		return mngrMapper.selectEmgHistoryListCount(mapParameter);
	}
	@Override
	public List selectAreaTelNo() throws Exception {
		// TODO Auto-generated method stub
		return mngrMapper.selectAreaTelNo();
	}
	@Override
	public List selectPhoneNo() throws Exception {
		// TODO Auto-generated method stub
		return mngrMapper.selectPhoneNo();
	}
	@Override
	public List selectStatHistoryList(Map mapParameter) {
	if(mapParameter.get("CNTER_CD").toString().contains("WDR")){
			mapParameter.put("cnterState", "WDR");
		} else if(mapParameter.get("CNTER_CD").toString().contains("ARE")) {
			mapParameter.put("cnterState", "ARE");
		}

		return mngrMapper.selectStatHistoryList(mapParameter);
	}
	@Override
	public List selectStatHistoryListCount(Map mapParameter) {
		if(mapParameter.get("CNTER_CD").toString().contains("WDR")){
			mapParameter.put("cnterState", "WDR");
		} else if(mapParameter.get("CNTER_CD").toString().contains("ARE")) {
			mapParameter.put("cnterState", "ARE");
		}
		
		return mngrMapper.selectStatHistoryListCount(mapParameter);
	}
	@Override
	public List smsHistoryList(Map mapParameter) {
		// TODO Auto-generated method stub
		return mngrMapper.smsHistoryList(mapParameter);
	}
	@Override
	public List smsHistoryListCount(Map mapParameter) {
		// TODO Auto-generated method stub
		return mngrMapper.smsHistoryListCount(mapParameter);
	}
	@Override
	public List smsTypeList() {
		// TODO Auto-generated method stub
		return mngrMapper.smsTypeList();
	}
	
}