package com.neighbor.ServiceAPI.controller.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.neighbor.ServiceAPI.controller.mapper.BassInfoMapper;
import com.neighbor.ServiceAPI.controller.mapper.StcMngMapper;
import com.neighbor.ServiceAPI.controller.service.BassInfoService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : BassInfoServiceImpl.java
 * @Description : BassInfoServiceImpl Implement Class
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

@Service("bassInfoService")
public class BassInfoServiceImpl extends EgovAbstractServiceImpl implements BassInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BassInfoServiceImpl.class);

	/** BassInfoDAO */
	@Resource(name="bassInfoMapper")
	private BassInfoMapper bassInfoMapper;

	@Resource(name="StcMngMapper")
	private StcMngMapper stcMngMapper;

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	public List selectHadicapMngList(Map mapParameter) throws Exception {
		return bassInfoMapper.selectHadicapMngList(mapParameter);
	}
	public List selectHadicapMngListCount(Map mapParameter) throws Exception {
		return bassInfoMapper.selectHadicapMngListCount(mapParameter);
	}
	
	public int hadicapMngCount(Map mapParameter) throws Exception {
		return bassInfoMapper.hadicapMngCount(mapParameter);
	}
	
	public Map carNumberCheck(Map mapParameter) throws Exception {
		if(mapParameter.get("WATCH_TABLE") != null ){
			return bassInfoMapper.selectCarNumberWithWatchTable(mapParameter);
		}	
		return bassInfoMapper.selectCarNumber(mapParameter);
	}
	
	
	public List selectCarMngList(Map mapParameter) throws Exception {
		if(mapParameter.get("CNTER_CD").toString().contains("WDR")){
			mapParameter.put("cnterState", "WDR");
		} else if(mapParameter.get("CNTER_CD").toString().contains("ARE")) {
			mapParameter.put("cnterState", "ARE");
		}
		
		return bassInfoMapper.selectCarMngList(mapParameter);
	}
	public List selectCarMngListCount(Map mapParameter) throws Exception {
		return bassInfoMapper.selectCarMngListCount(mapParameter);
	}
	
	public void carMngDelete(Map mapParameter) throws Exception {
		String[] vhcleNoArray = mapParameter.get("VHCLE_NO").toString().split(",");
		String[] cnterCdArray = mapParameter.get("CNTER_CD").toString().split(",");
		for(int i = 0; i < vhcleNoArray.length; i++){
			Map data = new HashMap<String,String>();
			data.put("CNTER_CD", cnterCdArray[i]);
			data.put("VHCLE_NO", vhcleNoArray[i]);
			
			bassInfoMapper.carMngDelete(data);
		}
	}
	
	public void carMngInsert(Map mapParameter) throws Exception {
		bassInfoMapper.carMngInsert(mapParameter);
	}
	
	public Map carMngDetail(Map mapParameter) throws Exception {
		return bassInfoMapper.carMngDetail(mapParameter);
	}
	
	public void carMngUpdate(Map mapParameter) throws Exception {
		bassInfoMapper.carMngUpdate(mapParameter);
	}
	@Override
	public List selectSearchCnterList(Map mapParameter) {
		mapParameter = stcMngMapper.getSctMngDetailInfo(mapParameter);
		return stcMngMapper.getLevelTwoUpper(mapParameter);
	}
	@Override
	public List selectSearchVhcleTypeList() {
		return bassInfoMapper.selectSearchVhcleTypeList();
	}

	//이지페이 배치키등록 추가, Yudy
	@Override
	public Map searchEasypayBatchKey(Map mapParameter) throws Exception {
		return bassInfoMapper.searchEasypayBatchKey(mapParameter);
	}

	@Override
	public void easypayBatchKeyDelete(Map mapParameter) throws Exception {
		bassInfoMapper.easypayBatchkeyDelete(mapParameter);
	}

	@Override
	public void easypayBatchKeyUpdate(Map mapParameter) throws Exception {
		bassInfoMapper.easypayBatchKeyUpdate(mapParameter);
	}
}