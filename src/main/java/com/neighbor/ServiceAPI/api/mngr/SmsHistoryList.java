package com.neighbor.ServiceAPI.api.mngr;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.BassInfoService;
import com.neighbor.ServiceAPI.controller.service.MngrService;
import com.neighbor.ServiceAPI.exception.ApiException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 긴급호출이력관리 클래스.<p>
 * 2. 처리개요 : <p>
 * 3. 주의사항 : <p>
 *  <pre>
 *  <b>History:</b> 
 *     작성자 : 송용석, 1.0, 2018/01/012  초기 작성
 *  </pre>
 *  
 * @author 최종 수정자 : 송용석
 * @version 1.0
 * @see
 * <p/>
 */
public class SmsHistoryList extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(SmsHistoryList.class);
	
	@Resource(name="mngrService")
	private MngrService mngrService;
	
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1287";
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
		searchNM, searchSMS_TYPE, searchStartDate, searchEndDate
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
			
			List smsHistoryList = mngrService.smsHistoryList(mapParameter);
			List smsHistoryListCount = mngrService.smsHistoryListCount(mapParameter);
			
			List smsTypeList = mngrService.smsTypeList();
			
			resultData.put("smsHistoryList", smsHistoryList);
			resultData.put("smsHistoryListCount", smsHistoryListCount);
			resultData.put("smsTypeList", smsTypeList);
			
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