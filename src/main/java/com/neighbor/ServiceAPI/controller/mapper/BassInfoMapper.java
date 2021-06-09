package com.neighbor.ServiceAPI.controller.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * @Class Name : BassInfoServiceImpl.java
 * @Description : BassInfoServiceImpl DAO Class
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

@Repository("bassInfoMapper")
public class BassInfoMapper extends EgovAbstractMapper {

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectHadicapMngList(Map mapParameter) throws Exception {
		return selectList("hadicapMng.selectHadicapMngList", mapParameter);
	}
	public List selectHadicapMngListCount(Map mapParameter) throws Exception {
		return selectList("hadicapMng.selectHadicapMngListCount", mapParameter);
	}
			
	public int hadicapMngCount(Map mapParameter) throws Exception {
		return selectOne("hadicapMng.hadicapMngCount", mapParameter);
	}
	
	
	public List selectCarMngList(Map mapParameter) throws Exception {
		return selectList("carMng.selectCarMngList", mapParameter);
	}
	public List selectCarMngListCount(Map mapParameter) throws Exception {
		return selectList("carMng.selectCarMngListCount", mapParameter);
	}
	
	public void carMngDelete(Map data) throws Exception {
		delete("carMng.carMngDelete", data); //CAR TABLE
	}
	
	public void carMngInsert(Map mapParameter) throws Exception {
		insert("carMng.carMngInsert", mapParameter);
	}
	
	public Map carMngDetail(Map mapParameter) throws Exception {
		return selectOne("carMng.carMngDetail", mapParameter);
	}
	
	public void carMngUpdate(Map mapParameter) throws Exception {
		update("carMng.carMngUpdate", mapParameter);
	}
	public List selectSearchVhcleTypeList() {
		return selectList("carMng.selectSearchVhcleTypeList");
	}
	public Map selectCarNumber(Map mapParameter) throws Exception{
		return selectOne("carMng.carNumberCheck", mapParameter);
	}
	public Map selectCarNumberWithWatchTable(Map mapParameter) {
		return selectOne("carMng.selectCarNumberWithWatchTable", mapParameter);
	}

	/*이지페이 배치키등록 추가, Yudy */
	public Map searchEasypayBatchKey(Map mapParameter) throws Exception {
		return selectOne("userMng.searchEasypayBatchKey", mapParameter);
	}

	public void easypayBatchkeyDelete(Map mapParameter) throws Exception {
		update("userMng.easypayBatchkeyDelete", mapParameter);
	}

	public void easypayBatchKeyUpdate(Map mapParameter) throws Exception{
		update("userMng.easypayBatchkeyUpdate", mapParameter);
	}

}