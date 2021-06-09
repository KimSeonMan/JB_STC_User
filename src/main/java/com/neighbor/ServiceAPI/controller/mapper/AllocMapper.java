package com.neighbor.ServiceAPI.controller.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * @Class Name : AllocServiceImpl.java
 * @Description : AllocServiceImpl DAO Class
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

@Repository("allocMapper")
public class AllocMapper extends EgovAbstractMapper {

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectCarAllocList(Map mapParameter) throws Exception {
		return selectList("alloc.selectCarAllocList", mapParameter);
	}
	public List selectCarAllocListCount(Map mapParameter) throws Exception {
		return selectList("alloc.selectCarAllocListCount", mapParameter);
	}
	
	public void carReceiptInsert(Map mapParameter) throws Exception {
		insert("alloc.carReceiptInsert", mapParameter);
	}
	
	public List selectCarAllocPopList(Map mapParameter) throws Exception {
		return selectList("alloc.selectCarAllocPopList", mapParameter);
	}
	
	public void carAllocUpdate(Map mapParameter) throws Exception {
		update("alloc.carAllocUpdate", mapParameter);
	}

	/*
	 * public void carRecptSttusDelete(String[] list) throws Exception {
	 * update("alloc.carRecptSttusDelete", list); }
	 */
	
	//취소 배차이력 추가
	public void CaralcCancelInsert(Map mapParameter) throws Exception {
		insert("alloc.CaralcCancelInsert", mapParameter);
	}
	//취소 접수이력 업데이트
	public void  RceptCaralcSttusUpdate(Map mapParameter) throws Exception {
		insert("alloc.RceptCaralcSttusUpdate", mapParameter);
	}
	
	public Map getChrgCnterCd(Map mapParameter) throws Exception { //담당센터코드 가져오기
		return selectOne("alloc.getChrgCnterCd", mapParameter);
	}
	
	public void deleteCaralcHist(Map mapParameter) throws Exception { //배차이력삭제
		delete("alloc.deleteCaralcHist", mapParameter);
	}

	public Map selectCaralcHistCount(Map mapParameter) throws Exception { //배차이력 카운트
		return selectOne("alloc.selectCaralcHistCount", mapParameter);
	}
	public void deleteRceptHist(Map mapParameter) throws Exception { //접수이력삭제
		delete("alloc.deleteRceptHist", mapParameter);
	}
	
	public Integer selectlastCancelSN(Map mapParameter) throws Exception { //취소이력시퀀스조회
		return selectOne("alloc.selectRceptCancelHistSN", mapParameter);
	}
	
	public void insertRceptCancelHist(Map mapParameter) throws Exception { //취소이력입력
		insert("alloc.insertRceptCancelHist", mapParameter);
	}
		
	public List selectStatSearch(Map mapParameter) throws Exception {
		return selectList("alloc.selectStatSearch", mapParameter);
	}
	
	public List selectCarAllocResultList(Map mapParameter) throws Exception {
		return selectList("alloc.selectCarAllocResultList", mapParameter);
	}
	public List selectCarAllocListResultCount(Map mapParameter) throws Exception {
		return selectList("alloc.selectCarAllocListResultCount", mapParameter);
	}
	
	public Map selectCarAllocDetail(Map mapParameter) throws Exception {
		return selectOne("alloc.selectCarAllocDetail", mapParameter);
	}
	public void addCarAlocHist(Map alocMap) {
		insert("alloc.addCarAlocHist", alocMap);
	}
	
}