package com.neighbor.ServiceAPI.api.carUseResve;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.RecptSttusService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 접수현황 조회 클래스.<p>
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
public class RecptSttusList extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(RecptSttusList.class);
	
	@Resource(name="recptSttusService")
	private RecptSttusService recptSttusService;
		
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "2200";
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
		page, CNTER_CD
	}
	
	enum OptionParam
	{
		CARALC_STTUS_CD, ST_DT, EN_DT
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
			
			mapParameter.put("USER_ID", httpSession.getAttribute("user_id"));

			if(mapParameter.containsKey("page")){
				int offset = (Integer.parseInt(mapParameter.get("page").toString())-1)*10;
				mapParameter.put("offset", offset);
			}
			List recptSttusList = recptSttusService.selectRecptList(mapParameter);
			List recptSttusListCount = recptSttusService.selectRecptListCount(mapParameter);
			resultData.put("recptSttusList", recptSttusList);
			resultData.put("recptSttusListCount", recptSttusListCount);
			
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