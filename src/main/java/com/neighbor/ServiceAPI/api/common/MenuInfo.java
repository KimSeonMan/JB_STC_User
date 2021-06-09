package com.neighbor.ServiceAPI.api.common;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.CommonService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 메뉴조회 클래스.<p>
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
public class MenuInfo extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(MenuInfo.class);
	
	@Resource(name="commonService")
	private CommonService commonService;
	
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1105";
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
		parent_menu_id
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

			String user_gbn_cd = (String) httpSession.getAttribute("user_gbn_cd"); 
			if(user_gbn_cd != null && !user_gbn_cd.equals("")) {
				mapParameter.put("USER_GBN_CD", user_gbn_cd);
//				mapParameter.put("MBER_SE_CD", user_gbn_cd);
			} else {
				mapParameter.put("USER_GBN_CD", "10"); //이동약자(사용자) 메뉴구성
//				mapParameter.put("MBER_SE_CD", "10"); //이동약자(사용자) 메뉴구성
			}
			
			String cnter_cd = (String) httpSession.getAttribute("cnter_cd");
			if(cnter_cd != null && !cnter_cd.equals("")) {
				mapParameter.put("CNTER_CD", cnter_cd);
			} else {
				mapParameter.put("CNTER_CD", "NAT-1-001"); //이동약자(사용자) 메뉴구성
			}
			
			
			_checkNullParameterValue(mapParameter);
			
			List menuList = commonService.selectMenuList(mapParameter);
			resultData.put("menuList", menuList);
			
			//LINK_SITE 리스트
			//List linkSiteList = commonService.selectLinkSiteList(mapParameter);
			//resultData.put("linkSiteList", linkSiteList);
			
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