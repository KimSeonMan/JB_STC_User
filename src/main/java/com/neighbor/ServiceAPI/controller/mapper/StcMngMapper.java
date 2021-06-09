package com.neighbor.ServiceAPI.controller.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("StcMngMapper")
public class StcMngMapper extends EgovAbstractMapper{

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List getSctMngList(Map mapParameter) {
		return selectList("stcMng.getSctMngList", mapParameter);
	}

	public List getStcMngListTotalCount(Map mapParameter) {
		return selectList("stcMng.getStcMngListTotalCount", mapParameter);
	}

	public Map getSctMngDetailInfo(Map mapParameter) {
		return selectOne("stcMng.getSctMngDetailInfo", mapParameter);
	}

	public List getLevelTwoUpper(Map mapParameter) {
		return selectList("stcMng.getLevelTwoUpper", mapParameter);
	}

	public Object getCnterNo(String cnterType) {
		return selectOne("stcMng.getCnterNo", cnterType);
	}

	public void stcMngAdd(Map mapParameter) {
		insert("stcMng.stcMngAdd", mapParameter);
	}

	public void stcMngModify(Map mapParameter) {
		update("stcMng.stcMngModify", mapParameter);		
	}

	public void stcMngDelete(String cnterCd) {
		delete("stcMng.stcMngDelete", cnterCd);
	}

	public List getUpperCnterList(Map stcMngDetail) {
		return selectList("stcMng.getUpperCnterList", stcMngDetail);
	}

	public List findCnterCd(Map searchData) {
		return selectList("stcMng.findCnterCd", searchData);
	}

	public List findManageAreaCnterCd(Map searchData) {
		return selectList("stcMng.findManageAreaCnterCd", searchData);
	}

}
