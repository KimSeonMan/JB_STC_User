package com.neighbor.ServiceAPI.api.system;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.StcMngService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 공통코드목록조회 클래스.<p>
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
public class StcMngList extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(StcMngList.class);
	
	@Resource(name="StcMngService")
	StcMngService stcMngService;
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1280";
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
		page
	}
	
	enum OptionParam
	{
		CNTER_CD, LEVEL, UPPER_CNTER_CD, CNTER_NM, DOMN, TELNO, MRDN_AT
		, LC_CRDNT_X, LC_CRDNT_Y, ADRES, ADRES_DETAIL, searchMou
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
			
			List stcMngList = stcMngService.getSctMngList(mapParameter);
			List stcMngListTotalCount = stcMngService.getStcMngListTotalCount(mapParameter);
			
			Map cnterNo = stcMngService.getCnterCdNo();
			
			
			resultData.put("stcMngList", stcMngList);
			resultData.put("stcMngListTotalCount", stcMngListTotalCount);
			resultData.put("cnterNo", cnterNo);
			
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