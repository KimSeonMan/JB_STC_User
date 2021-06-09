package com.neighbor.ServiceAPI.controller.mapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * @Class Name : MainServiceImpl.java
 * @Description : MainServiceImpl DAO Class
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

@Repository("mngrMapper")
public class MngrMapper extends EgovAbstractMapper {

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	
	public List selectUserMngList(Map mapParameter) throws Exception {
		return selectList("userMng.selectUserMngList", mapParameter);
	}
	public List selectUserMngListCount(Map mapParameter) throws Exception {
		return selectList("userMng.selectUserMngListCount", mapParameter);
	}
	
	public List selectCenter() throws Exception {
		return selectList("userMng.selectCenter");
	}
	
	public List selectEmailList() throws Exception{
		return selectList("userMng.selectEmailList");
	}
	
	public List selectTroblCode() throws Exception{
		return selectList("userMng.selectTroblCode");
	}
	
	public List selectWheelchair() throws Exception{
		return selectList("userMng.selectWheelchair");
	}
	
	public List selectMemberCode() throws Exception {
		return selectList("userMng.selectMemberCode");
	}
	
	public List selectAreaTelNo() throws Exception {
		return selectList("userMng.selectAreaTelNo");
	}
	
	public List selectPhoneNo() throws Exception {
		return selectList("userMng.selectPhoneNo");
	}
	
	public Map selectUserMngDetail(Map mapParameter) throws Exception {
		return selectOne("userMng.selectUserMngDetail", mapParameter);
	}
	
	public void userMngUpdate(Map mapParameter) throws Exception {
		update("userMng.userMngUpdate", mapParameter);
	}
	
	
	public List selectMenuMngList(Map mapParameter) throws Exception {
		return selectList("menuMng.selectMenuMngList", mapParameter);
	}
	public List selectMenuMngListCount(Map mapParameter) throws Exception {
		return selectList("menuMng.selectMenuMngListCount", mapParameter);
	}
	
	public Map selectMenuMngDetail(Map mapParameter) throws Exception {
		return selectOne("menuMng.selectMenuMngDetail", mapParameter);
	}
	
	public void menuMngInsert(Map mapParameter) throws Exception {
		update("menuMng.menuMngInsert", mapParameter);
	}
	
	public void menuMngUpdate(Map mapParameter) throws Exception {
		update("menuMng.menuMngUpdate", mapParameter);
	}
	
	@Transactional
	public void menuMngDelete(String gubn, String[] list) throws Exception {
		if(gubn.equals("U")) {
			update("menuMng.menuMngDelete", list);
		} else {
			delete("menuMng.menuMngAccessDelete", list); //MENU ACCESS
			delete("menuMng.menuMngDelete2", list); //MENU TABLE
		}
	}
	
	public Map menuMngDoubleChk(Map mapParameter) throws Exception {
		return selectOne("menuMng.menuMngDoubleChk", mapParameter);
	}
	
	public List selectMenuMngAccessList(Map mapParameter) throws Exception {
		return selectList("menuMng.selectMenuMngAccessList", mapParameter);
	}
	
	@Transactional
	public void menuMngAccessAdd(String menuId, String[] list, String cnterCd) throws Exception {
		Map mapParameter = new HashMap();
		delete("menuMng.menuMngAccessDelete2", menuId); //MENU ACCESS
		for(int i=0; i<list.length; i++) {
			String tempUserGubunCd = "";
			String tempAccessCd = "";
			tempUserGubunCd = list[i].split(" ")[0];
			tempAccessCd = list[i].split(" ")[1];
			mapParameter.put("MENU_ID", menuId);
			mapParameter.put("USER_GUBUN_CD", tempUserGubunCd);
			mapParameter.put("ACCESS_CD", tempAccessCd);
			mapParameter.put("CNTER_CD", cnterCd);
			
			
			insert("menuMng.menuMngAccessInsert", mapParameter); //MENU ACCESS
		}
	}
	
	public List menuMngSelectboxList(Map mapParameter) throws Exception {
		return selectList("menuMng.menuMngSelectboxList", mapParameter);
	}
	
	
	public List selectContentsMngList(Map mapParameter) throws Exception {
		return selectList("contentsMng.selectContentsMngList", mapParameter);
	}
	public List selectContentsMngListCount(Map mapParameter) throws Exception {
		return selectList("contentsMng.selectContentsMngListCount", mapParameter);
	}
	
	@Transactional
	public void contentsMngInsert(Map mapParameter) throws Exception {
		mapParameter.put("CTNT_URL", "/view/userSite.do?gubn="+mapParameter.get("MENU_ID"));
		insert("contentsMng.contentsMngInsert", mapParameter); //CNTNTS_INFO Table
		update("contentsMng.contentsMngMenuUpdate", mapParameter); //MENU Table
	}
	
	@Transactional
	public void contentsMngUpdate(Map mapParameter) throws Exception {
		mapParameter.put("CTNT_URL", "/view/userSite.do?gubn="+mapParameter.get("MENU_ID"));
		update("contentsMng.contentsMngUpdate", mapParameter); //CNTNTS_INFO Table
		update("contentsMng.contentsMngMenuUpdate", mapParameter); //MENU Table
	}
	
	public void contentsMngDelete(String[] list) throws Exception {
		update("contentsMng.contentsMngDelete", list); //CNTNTS_INFO Table
	}
	
	public Map selectContentsMngDetail(Map mapParameter) throws Exception {
		return selectOne("contentsMng.selectContentsMngDetail", mapParameter);
	}
	
	
	public List selectCommonCDMngListA(Map mapParameter) throws Exception {
		return selectList("commonCDMng.selectCommonCDMngListA", mapParameter);
	}
	public List selectCommonCDMngListCountA(Map mapParameter) throws Exception {
		return selectList("commonCDMng.selectCommonCDMngListCountA", mapParameter);
	}
	
	public List selectCommonCDMngListB(Map mapParameter) throws Exception {
		return selectList("commonCDMng.selectCommonCDMngListB", mapParameter);
	}
	public List selectCommonCDMngListCountB(Map mapParameter) throws Exception {
		return selectList("commonCDMng.selectCommonCDMngListCountB", mapParameter);
	}
	
	public Map selectCommonCDMngDetail(Map mapParameter) throws Exception {
		if(mapParameter.get("CD_GUBN") != null && mapParameter.get("CD_GUBN").equals("A")) {
			return selectOne("commonCDMng.selectCommonCDMngDetailA", mapParameter);
		} else {
			return selectOne("commonCDMng.selectCommonCDMngDetailB", mapParameter);
		}
	}
	
	public Map selectCommonCDMngIdCheck(Map mapParameter) throws Exception {
		return selectOne("commonCDMng.selectCommonCDMngIdCheck", mapParameter);
	}
	
	public void commonCDMngInsert(Map mapParameter) throws Exception {
		if(mapParameter.get("CD_GUBN") != null && mapParameter.get("CD_GUBN").equals("A")) {
			insert("commonCDMng.commonCDMngInsertA", mapParameter); //CM_CD TABLE
		} else {
			insert("commonCDMng.commonCDMngInsertB", mapParameter); //CM_CD_VAL TABLE
		}
	}
	
	public void commonCDMngUpdate(Map mapParameter) throws Exception {
		if(mapParameter.get("CD_GUBN") != null && mapParameter.get("CD_GUBN").equals("A")) {
			insert("commonCDMng.commonCDMngUpdateA", mapParameter); //CM_CD TABLE
		} else {
			insert("commonCDMng.commonCDMngUpdateB", mapParameter); //CM_CD_VAL TABLE
		}
	}
	
	
	public List selectSiteMngList(Map mapParameter) throws Exception {
		return selectList("siteMng.selectSiteMngList", mapParameter);
	}
	public List selectSiteMngListCount(Map mapParameter) throws Exception {
		return selectList("siteMng.selectSiteMngListCount", mapParameter);
	}
	
	public void siteMngInsert(Map mapParameter) throws Exception {
		insert("siteMng.siteMngInsert", mapParameter); //CM_CD TABLE
	}
	
	public void siteMngDelete(String[] list) throws Exception {
		delete("siteMng.siteMngDelete", list); //CNTNTS_INFO Table
	}
	
	public Map selectSiteMngDetail(Map mapParameter) throws Exception {
		return selectOne("siteMng.selectSiteMngDetail", mapParameter);
	}
	
	public void siteMngUpdate(Map mapParameter) throws Exception {
		insert("siteMng.siteMngUpdate", mapParameter); //CM_CD TABLE
	}
	
	
	public List selectQestnarMngList(Map mapParameter) throws Exception {
		return selectList("qestnarMng.selectQestnarMngList", mapParameter);
	}
	public List selectQestnarMngListCount(Map mapParameter) throws Exception {
		return selectList("qestnarMng.selectQestnarMngListCount", mapParameter);
	}
	
	@Transactional
	public void qestnarMngInsert(Map mapParameter, String gubn) throws Exception {
		String [] SURVEY_TYPE_CD;
		String [] SURVEY_CHOICE_CD;
		String [] SURVEY_GBN_CD;
		String [] SURVEY_CTNT;
		int CNT = 0;
		
		if(gubn != null && gubn.equals("I")) {
			Calendar date = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssS");
			String idDate = df.format(date.getTimeInMillis());
			
			mapParameter.put("SURVEY_ID", idDate);
			insert("qestnarMng.surveyInsert", mapParameter); //SURVEY TABLE
		} else {
			String [] list = new String[] {mapParameter.get("SURVEY_ID").toString()};
			
			update("qestnarMng.surveyUpdate", mapParameter); //SURVEY TABLE
			delete("qestnarMng.surveyInfoDelete", list); //SURVEY_INFO TABLE
		}
		
		if(mapParameter.get("SURVEY_TYPE_CD") != null && !mapParameter.get("SURVEY_TYPE_CD").equals("")) {
			SURVEY_TYPE_CD = mapParameter.get("SURVEY_TYPE_CD").toString().split(",");
			SURVEY_CHOICE_CD = mapParameter.get("SURVEY_CHOICE_CD").toString().split(",");
			SURVEY_GBN_CD = mapParameter.get("SURVEY_GBN_CD").toString().split(",");
			SURVEY_CTNT = mapParameter.get("SURVEY_CTNT").toString().split(",");
			
			for(int i=0; i<SURVEY_TYPE_CD.length; i++) {
				mapParameter.put("survey_type_cd", SURVEY_TYPE_CD[i]);
				if(SURVEY_TYPE_CD[i].equals("01")) {
					mapParameter.put("survey_choice_cd", SURVEY_CHOICE_CD[i]);
				} else {
					mapParameter.put("survey_choice_cd", " ");
				}
				
				int cnt2 = 0;
				for(int j=CNT+0; j< CNT+Integer.parseInt(SURVEY_GBN_CD[i]); j++) {
					if(cnt2 == 0) {
						mapParameter.put("survey_gbn_cd", "1");
					} else {
						mapParameter.put("survey_gbn_cd", "2");
					}
					
					if(SURVEY_CTNT[j] != null && !SURVEY_CTNT[j].equals("")) {
						mapParameter.put("survey_ctnt", SURVEY_CTNT[j]);
					} else {
						mapParameter.put("survey_ctnt", " ");
					}
					
					mapParameter.put("SURVEY_SEQ", j+1);
					
					insert("qestnarMng.surveyInfoInsert", mapParameter); //SURVEY_INFO TABLE
					
					cnt2++;
				}
				CNT = CNT + Integer.parseInt(SURVEY_GBN_CD[i]);
			}
		}
	}
	
	@Transactional
	public void qestnarMngDelete(String[] list) throws Exception {
		delete("qestnarMng.surveyDelete", list); //SURVEY TABLE
		delete("qestnarMng.surveyInfoDelete", list); //SURVEY_INFO TABLE
	}
	
	public List selectQestnarMngDetail(Map mapParameter) throws Exception {
		return selectList("qestnarMng.selectQestnarMngDetail", mapParameter);
	}
	
	public List selectQestnarMngResult(Map mapParameter) throws Exception {
		return selectList("qestnarMng.selectQestnarMngResult", mapParameter);
	}
	public List selectEmgHistoryList(Map mapParameter) {
		return selectList("emgHistroy.selectEmgHistoryList", mapParameter);
	}
	public List selectEmgHistoryListCount(Map mapParameter) {
		return selectList("emgHistroy.selectEmgHistoryListCount", mapParameter);
	}
	public List selectStatHistoryList(Map mapParameter) {
		return selectList("statHistroy.selectStatHistoryList", mapParameter);
	}
	public List selectStatHistoryListCount(Map mapParameter) {
		return selectList("statHistroy.selectStatHistoryListCount", mapParameter);
	}
	public List smsHistoryList(Map mapParameter) {
		return selectList("smsHistroy.smsHistoryList", mapParameter);
	}
	public List smsHistoryListCount(Map mapParameter) {
		return selectList("smsHistroy.smsHistoryListCount", mapParameter);
	}
	public List smsTypeList() {
		return selectList("smsHistroy.smsTypeList");
	}
	
}