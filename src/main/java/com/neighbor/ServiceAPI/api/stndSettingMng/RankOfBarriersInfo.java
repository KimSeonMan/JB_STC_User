package com.neighbor.ServiceAPI.api.stndSettingMng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.neighbor.ServiceAPI.api.userSiteTemplate.NoticeAdd;
import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.StndSettingMngService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

public class RankOfBarriersInfo extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(AllocateCarInfo.class);
	
	@Resource(name="StndSettingMngService")
	private StndSettingMngService stndSettingMngService;
		
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1262";
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
		TROBL_KND_CD, TROBL_GRAD, WHEELCHAIR_USE_YN, PRTCTOR_ACMPNY_AT
	}	
	
	@Transactional
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
			
			List rankOfBarriersInfo = stndSettingMngService.getRankOfBarriersInfo(mapParameter);
			List troblKndList = stndSettingMngService.getTroblKndList();
			
			resultData.put("rankOfBarriersInfo", rankOfBarriersInfo);
			resultData.put("troblKndList", troblKndList);
			
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
