package com.neighbor.ServiceAPI.controller.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * @Class Name : UserSiteTemplateServiceImpl.java
 * @Description : UserSiteTemplateServiceImpl DAO Class
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

@Repository("userSiteTemplateMapper")
public class UserSiteTemplateMapper extends EgovAbstractMapper {

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectNoticeList(Map mapParameter) throws Exception {
		List result = null;
		if(mapParameter.get("GUBN") != null) {
			if(mapParameter.get("GUBN").equals("40")) { //Q&A
				result = selectList("notice.selectBoardList", mapParameter);
			} else if(mapParameter.get("GUBN").equals("50")) { //FAQ
				result = selectList("notice.selectFAQList", mapParameter);
			} else { //60 공지사항
				result = selectList("notice.selectNoticeList", mapParameter);
			}
		}
		return result;
	}
	public List selectNoticeListCount(Map mapParameter) throws Exception {
		List result = null;
		if(mapParameter.get("GUBN") != null) {
			if(mapParameter.get("GUBN").equals("40")) { //Q&A
				result = selectList("notice.selectBoardListCount", mapParameter);
			} else if(mapParameter.get("GUBN").equals("50")) { //FAQ
				result = selectList("notice.selectFAQListCount", mapParameter);
			} else { //60 공지사항
				result = selectList("notice.selectNoticeListCount", mapParameter);
			}
		}
		return result;
	}
	
	public List noticeAddConfirm(Map mapParameter) throws Exception {
		return selectList("notice.noticeAddConfirm", mapParameter);
	}
	
	public void fileInfoInsert(Map mapParameter) throws Exception {
		insert("notice.fileInfoInsert", mapParameter);
	}
	
	public void noticeInsert(Map mapParameter) throws Exception {
		if(mapParameter.get("CNTNTS_TYPE2_CD2") != null) {
			if(mapParameter.get("CNTNTS_TYPE2_CD2").equals("40")) { //Q&A
				insert("notice.QAInsert", mapParameter);
			} else if(mapParameter.get("CNTNTS_TYPE2_CD2").equals("50")) { //FAQ
				insert("notice.FAQInsert", mapParameter);
			} else { //60 공지사항
				insert("notice.noticeInsert", mapParameter);
			}
		}
	}
	
	@Transactional
	public void noticeDelete(String[] list) throws Exception {
		Map mapParameter = new HashMap();
		for(int i=0; i<list.length; i++) {
			String CNTNTS_TYPE2_CD = "";
			String CNTNTS_CLS_CD = "";
			String CNTNTS_NO = "";
			String NOTICE_NO = "";
			CNTNTS_TYPE2_CD = list[i].split(" ")[0];
			CNTNTS_CLS_CD = list[i].split(" ")[1];
			CNTNTS_NO = list[i].split(" ")[2];
			NOTICE_NO = list[i].split(" ")[3];
			
			mapParameter.put("CNTNTS_TYPE2_CD2", CNTNTS_TYPE2_CD);
			mapParameter.put("CNTNTS_NO", CNTNTS_NO);
			mapParameter.put("NOTICE_NO", NOTICE_NO);
			
			if(CNTNTS_TYPE2_CD.equals("40")) {
				delete("notice.QADelete", mapParameter); //BOARD TABLE
			} else if(CNTNTS_TYPE2_CD.equals("50")) {
				delete("notice.FAQDelete", mapParameter); //FAQ TABLE
			} else {
				delete("notice.noticeDelete", mapParameter); //NOTICE TABLE
			}

			delete("notice.fileInfoDelete", mapParameter); //FILE_INFO TABLE
		}
	}
	
	public Map noticeDetail(Map mapParameter) throws Exception {
		String CNTNTS_TYPE2_CD = mapParameter.get("CNTNTS_TYPE2_CD").toString();
		if(CNTNTS_TYPE2_CD.equals("40")) {
			return selectOne("notice.QADetail", mapParameter);
		} else if(CNTNTS_TYPE2_CD.equals("50")) {
			return selectOne("notice.FAQDetail", mapParameter);
		} else {
			return selectOne("notice.noticeDetail", mapParameter);
		}
	}
	
	public List fileInfoList(Map mapParameter) throws Exception {
		return selectList("notice.fileInfoList", mapParameter);
	}
	
	public void noticeUpdate(Map mapParameter) throws Exception {
		if(mapParameter.get("CNTNTS_TYPE2_CD2") != null) {
			if(mapParameter.get("CNTNTS_TYPE2_CD2").equals("40")) { //Q&A
				update("notice.QAUpdate", mapParameter);
			} else if(mapParameter.get("CNTNTS_TYPE2_CD2").equals("50")) { //FAQ
				update("notice.FAQUpdate", mapParameter);
			} else { //60 공지사항
				update("notice.noticeUpdate", mapParameter);
			}
		}
	}
	
	public int selectNoticeMaxCount(Map mapParameter) throws Exception {
		return selectOne("notice.selectNoticeMaxCount", mapParameter);
	}
	
	public void noticeUpdateHitCnt(Map mapParameter) throws Exception {
		update("notice.noticeUpdateHitCnt", mapParameter);
	}
	
	public void fileInfoDelete(Map mapParameter) throws Exception {
		delete("notice.fileInfoDelete", mapParameter);
	}
	
	public List replyList(Map mapParameter) throws Exception {
		return selectList("notice.replyList", mapParameter);
	}
	
	public void replyInsert(Map mapParameter) throws Exception {
		insert("notice.replyInsert", mapParameter);
	}
	public Map attachInfoDetail(Map data) {
		return selectOne("notice.attachInfoDetail", data);
	}
	public void fileInfoInsertNew(Map mapParameter) {
		insert("notice.fileInfoInsertNew", mapParameter);
	}
	
}