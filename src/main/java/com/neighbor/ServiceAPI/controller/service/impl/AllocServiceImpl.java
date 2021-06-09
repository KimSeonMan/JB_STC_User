package com.neighbor.ServiceAPI.controller.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.neighbor.ServiceAPI.controller.mapper.AllocMapper;
import com.neighbor.ServiceAPI.controller.mapper.RecptSttusMapper;
import com.neighbor.ServiceAPI.controller.service.AllocService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : AllocServiceImpl.java
 * @Description : AllocServiceImpl Implement Class
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

@Service("allocService")
public class AllocServiceImpl extends EgovAbstractServiceImpl implements AllocService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AllocServiceImpl.class);

	/** AllocDAO */
	@Resource(name="allocMapper")
	private AllocMapper allocMapper;

	@Resource(name="recptSttusMapper")
	private RecptSttusMapper recptSttusMapper;
	
	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectCarAllocList(Map mapParameter) throws Exception {
		return allocMapper.selectCarAllocList(mapParameter);
	}
	public List selectCarAllocListCount(Map mapParameter) throws Exception {
		return allocMapper.selectCarAllocListCount(mapParameter);
	}
	
	public void carReceiptInsert(Map mapParameter) throws Exception {
		allocMapper.carReceiptInsert(mapParameter);
	}
	
	public List selectCarAllocPopList(Map mapParameter) throws Exception {
		return allocMapper.selectCarAllocPopList(mapParameter);
	}
	
	public void carAllocUpdate(Map mapParameter) throws Exception {
		allocMapper.carAllocUpdate(mapParameter);
	}
	
	/*
	 * public void carRecptSttusDelete(String[] list) throws Exception {
	 * allocMapper.carRecptSttusDelete(list); }
	 */
	
	//예약취소
	public void RceptCancel(Map mapParameter) throws Exception {
		// 배차이력 추가
		allocMapper.CaralcCancelInsert(mapParameter);
		// 접수이력 업데이트
		allocMapper.RceptCaralcSttusUpdate(mapParameter);
	}
	
	public void carRecptSttusModify(Map mapParameter) throws Exception {
		Map getChrg_cnter_cd = allocMapper.getChrgCnterCd(mapParameter); //배차 담당센터
		allocMapper.deleteCaralcHist(mapParameter);//배차이력 삭제
		allocMapper.deleteRceptHist(mapParameter); //접수이력 삭제
		
		Integer sn = allocMapper.selectlastCancelSN(mapParameter);
		if (sn == null) {
			sn = 1;
		}else {
			sn = sn + 1;
		}
		mapParameter.put("sn", sn);
		mapParameter.put("chrg_cnter_cd", getChrg_cnter_cd.get("CHRG_CNTER_CD"));
		allocMapper.insertRceptCancelHist(mapParameter);
		cancelKobusRceptHist(mapParameter);
	}
	/**KOBUS 예약 이력조회 및 예약 취소*/
	public void cancelKobusRceptHist(Map mapParameter) throws Exception {
		Map<String, Object> rceptHist = recptSttusMapper.getKobusRceptHist(mapParameter);
		if(rceptHist != null) {
			recptSttusMapper.cancelKobusRceptHist(mapParameter);
		}
	}
	
	public List selectStatSearch(Map mapParameter) throws Exception {
		return allocMapper.selectStatSearch(mapParameter);
	}
	
	public List selectCarAllocResultList(Map mapParameter) throws Exception {
		return allocMapper.selectCarAllocResultList(mapParameter);
	}
	public List selectCarAllocListResultCount(Map mapParameter) throws Exception {
		return allocMapper.selectCarAllocListResultCount(mapParameter);
	}
	
	public Map selectCarAllocDetail(Map mapParameter) throws Exception {
		return allocMapper.selectCarAllocDetail(mapParameter);
	}
	
}