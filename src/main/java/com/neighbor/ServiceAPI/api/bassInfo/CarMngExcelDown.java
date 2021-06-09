package com.neighbor.ServiceAPI.api.bassInfo;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.BassInfoService;
import com.neighbor.ServiceAPI.exception.ApiException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 차량정보관리 목록 조회 클래스.<p>
 * 2. 처리개요 : <p>
 * 3. 주의사항 : <p>
 *  <pre>
 *  <b>History:</b> 
 *     작성자 : 김희철, 1.0, 2017/02/08  초기 작성
 *  </pre>
 *  
 * @author 최종 수정자 : 김희철
 * @version 1.0
 * @see
 * <p/>
 */
public class CarMngExcelDown extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(CarMngExcelDown.class);
	
	@Resource(name="bassInfoService")
	private BassInfoService bassInfoService;
		
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1316";
	}

	@Override
	public HttpMethod getHttpMethod() {
		// TODO Auto-generated method stub
		return HttpMethod.POST;
	}

	@Override
	public Class getMustParameter() throws AbsException {
		// TODO Auto-generated method stub
		return MustParam.class;
	}

	@Override
	public Class getOptionParameter() throws AbsException {
		// TODO Auto-generated method stub
		return OptionParam.class;
	}

	@Override
	protected String getQueryStr() {
		return null;
	}
	
	enum MustParam
	{
		CNTER_CD
	}
	
	enum OptionParam
	{
		VHCLE_NO, RESVE_VHCLE_AT, COPERTN_CARALC_AT, VHCLE_TY_CD, MODL_NM
		, MAKR, MXMM_BRDNG_NMPRM, YRIDNW, VIN, USE_AT, REGISTER_ID, REGIST_DT, RM
		, searchCNTER_CD, searchCOPERTN_CARALC_AT, searchRESVE_VHCLE_AT, searchVHCLE_TY_CD, searchVHCLE_NO
	}	
	
	@Override
	public Map executeAPI(HttpServletRequest req, HttpServletResponse res,
			String trId) throws AbsException {
		// TODO Auto-generated method stub
		httpSession = req.getSession();

		Map resultData = new HashMap();
		
		try {
			logger.info("START Query - ApiID[" + this.getApiId() + "] ");

			Map mapParameter = getParameterMap(req);

			_checkNullParameterValue(mapParameter);
			
			List carMngList = bassInfoService.selectCarMngList(mapParameter);
			List carMngListCount = bassInfoService.selectCarMngListCount(mapParameter);
			
			
			List searchCnter = bassInfoService.selectSearchCnterList(mapParameter);
			List searchVhcleType = bassInfoService.selectSearchVhcleTypeList();
			
			
			resultData.put("carMngList", carMngList);
			resultData.put("carMngListCount", carMngListCount);
			resultData.put("searchCnter", searchCnter);
			resultData.put("searchVhcleType", searchVhcleType);
			
			logger.info("END Query - TXID[" + getApiId() + "] ");
		}catch (AbsAPIException e) {
			logger.error(e);
			throw e;
		} catch (IllegalArgumentException e) {
			logger.error(e);
			throw new ApiException("Input Error");
		} catch (Exception e) {
			logger.error(e);
			throw new ApiException("Error");
		}
		return resultData;
	}
}