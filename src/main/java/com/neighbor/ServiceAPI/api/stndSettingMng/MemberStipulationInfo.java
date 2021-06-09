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

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.StndSettingMngService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighbor.ServiceAPI.mobile.domain.CnterInfo;
import com.neighbor.ServiceAPI.mobile.service.MobileApiService;
import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

public class MemberStipulationInfo extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(MemberStipulationInfo.class);
	
	@Resource(name="StndSettingMngService")
	private StndSettingMngService stndSettingMngService;
		
	@Resource(name="mobileApiService")
	private MobileApiService mobileApiService;
	
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1266";
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
		STPLAT_SE_CD, CN
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
			
			List memberStipulationInfo = stndSettingMngService.getMemberStipulationInfo(mapParameter);
			//CnterInfo cnterInfo = mobileApiService.getCnterInfo(mapParameter.get("CNTER_CD").toString());

			
			resultData.put("memberStipulationInfo", memberStipulationInfo);
			//resultData.put("cnterInfo", cnterInfo);
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
