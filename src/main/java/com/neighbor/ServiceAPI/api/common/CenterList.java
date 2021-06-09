package com.neighbor.ServiceAPI.api.common;


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
 * 1. 기능 : 회원관리 조회 클래스.<p>
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
public class CenterList extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(CenterList.class);
	
	@Resource(name="commonService")
	private CommonService commonService;
		
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1401";
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
		
	}
	
	enum OptionParam
	{
		
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
			
			/*String user_gbn_cd = (String) httpSession.getAttribute("user_gbn_cd");
			
			String user_cnter_cd = (String) httpSession.getAttribute("cnter_cd");
			if(user_cnter_cd == null || user_cnter_cd.equals("") || user_cnter_cd.equals("null")) {
				user_cnter_cd = "NAT-1-001"; //이동약자(사용자) 메뉴구성
			}
			mapParameter.put("CNTER_CD", user_cnter_cd);
			
			//String selectCnterCd = mapParameter.get("CNTER_CD").toString();
						
			mapParameter.put("USER_GBN_CD", user_gbn_cd);
			mapParameter.put("USER_CNTER_GBN_CD", user_cnter_cd.substring(0, 3));*/
			
			_checkNullParameterValue(mapParameter);
			
			List centerList = commonService.selectCenter(mapParameter); // 센터리스트
		
			resultData.put("centerList", centerList);
			
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