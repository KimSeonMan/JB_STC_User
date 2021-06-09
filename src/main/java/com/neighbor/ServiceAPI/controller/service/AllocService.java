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
public interface AllocService {

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	
	/** --------- 차량예약등록 --------- */
	public List selectCarAllocList(Map mapParameter) throws Exception;
	public List selectCarAllocListCount(Map mapParameter) throws Exception;
	
	public void carReceiptInsert(Map mapParameter) throws Exception;
	
	public List selectCarAllocPopList(Map mapParameter) throws Exception;
	
	public void carAllocUpdate(Map mapParameter) throws Exception;
	
	public void RceptCancel(Map mapParameter) throws Exception;
	//public void carRecptSttusDelete(String[] list) throws Exception;
	
	public void carRecptSttusModify(Map mapParameter) throws Exception ;
	/** --------- 차량예약등록 --------- */
	
	/** --------- 차량배차목록 --------- */
	public List selectStatSearch(Map mapParameter) throws Exception;
	
	public List selectCarAllocResultList(Map mapParameter) throws Exception;
	public List selectCarAllocListResultCount(Map mapParameter) throws Exception;
	
	public Map selectCarAllocDetail(Map mapParameter) throws Exception;
	/** --------- 차량배차목록 --------- */
	
	
}