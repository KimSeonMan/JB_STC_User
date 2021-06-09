package com.neighbor.ServiceAPI.controller.service;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : BassInfoService.java
 * @Description : BassInfoService Class
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
public interface BassInfoService {

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectHadicapMngList(Map mapParameter) throws Exception;
	public List selectHadicapMngListCount(Map mapParameter) throws Exception;
	
	public int hadicapMngCount(Map mapParameter) throws Exception;
	
	
	public List selectCarMngList(Map mapParameter) throws Exception;
	public List selectCarMngListCount(Map mapParameter) throws Exception;
	
	public void carMngDelete(Map mapParamete) throws Exception;
	
	public void carMngInsert(Map mapParameter) throws Exception;
	
	public Map carMngDetail(Map mapParameter) throws Exception;
	
	public Map carNumberCheck(Map mapParameter) throws Exception;
	
	public void carMngUpdate(Map mapParameter) throws Exception;
	public List selectSearchCnterList(Map mapParameter);
	public List selectSearchVhcleTypeList();

	//이지페이 배치키등록 추가, Yudy
	public Map searchEasypayBatchKey(Map mapParameter) throws Exception;
	public void easypayBatchKeyDelete(Map mapParameter) throws Exception;
	public void easypayBatchKeyUpdate(Map mapParameter) throws Exception;
	
}