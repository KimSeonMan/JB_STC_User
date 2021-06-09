package com.neighbor.ServiceAPI.controller.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * @Class Name : MainServiceImpl.java
 * @Description : MainServiceImpl DAO Class
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

@Repository("mainMapper")
public class MainMapper extends EgovAbstractMapper {

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectNoticeList(Map mapParameter) throws Exception {
		return selectList("main.selectNoticeList", mapParameter);
	}
	
	public List selectReportList(Map mapParameter) throws Exception {
		return selectList("main.selectReportList", mapParameter);
	}
	
	public List selectQAList(Map mapParameter) throws Exception {
		return selectList("main.selectQAList", mapParameter);
	}
	
	/*public Map selectContentsInfo(String gubn, String CNTNTS_NO) throws Exception {
		return selectOne("main.selectContentsInfo", gubn, CNTNTS_NO);
	}*/
	
	public Map selectContentsInfo(Map mapParameter) throws Exception {
		return selectOne("main.selectContentsInfo",mapParameter);
	}
	
	public List selectQestnarList(Map mapParameter) throws Exception {
		return selectList("main.selectQestnarList", mapParameter);
	}

	public Map selectMemberShipInfo(Map mapParameter) throws Exception {
		return selectOne("main.selectMemberShipInfo", mapParameter);
	}

	public Integer insertKobusApiLog(Map mapParameter) throws Exception {
		return insert("main.insertKobusApiLog", mapParameter);
	}
}