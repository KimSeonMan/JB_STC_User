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

public class AllocateCarInfoSave extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(AllocateCarInfoSave.class);
	
	@Resource(name="StndSettingMngService")
	private StndSettingMngService stndSettingMngService;
		
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1261";
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
		CNTER_CD, SAVE_OPTION
	}
	
	enum OptionParam
	{
		ROUNDTRIP_PERM_AT, ALOC_REQRE_TIME, VHCLE_SEARCH_RADIUS, BEFFAT_RESVE_TIME_INTRVL
		,VHCLE_MVMN_POSBL_AT ,POSBL_DSTNC, WDR_SCTN_OTHINST_MBER_USE_POSBL_AT, WHTHRC_OTHINST_MBER_USE_POSBL_AT
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
			// 받은 파라미터값 저장
			Map mapParameter = getParameterMap(req);

			_checkNullParameterValue(mapParameter);
			stndSettingMngService.saveAllocateCarInfo(mapParameter);
			
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
