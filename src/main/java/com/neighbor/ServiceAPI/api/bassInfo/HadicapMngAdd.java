package com.neighbor.ServiceAPI.api.bassInfo;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.BassInfoService;
import com.neighbor.ServiceAPI.controller.service.LoginService;
import com.neighbor.ServiceAPI.exception.ApiException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 이용자정보 등록 클래스.<p>
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
public class HadicapMngAdd extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(HadicapMngAdd.class);
	
	@Resource(name="loginService")
	private LoginService loginService;
	
	@Resource(name="bassInfoService")
	private BassInfoService bassInfoService;
	
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1301";
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
		SMS_RECEIVE_YN, NAME, BIRTH_DATE, MOBILE, ZIP, ADDRESS, EMAIL, TOTAL_CNT
	}
	
	enum OptionParam
	{
		PHONE, ADDRESSDTL, WELFAIR_CARD_NO, DISABLE_TYPE1_CD, DISABLE_TYPE2_CD, SUPPORTER_YN, 
		COMMUNICALBE_YN, HELPER_YN, WHEELCHAIR_CD, WHEELCHAIR_USE_YEAR
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
			
			mapParameter.put("USER_GBN_CD", "10"); //이동약자
			
			//오프라인 유저의 ID/PW 생성
			mapParameter.put("USER_ID", "handicap" + (bassInfoService.hadicapMngCount(mapParameter)+1));
			mapParameter.put("PASSWORD", "tscp2017!");
			mapParameter.put("USER_STAT_CD", "02"); //회원가입 승인 상태
			mapParameter.put("REG_USER_ID", httpSession.getAttribute("user_id"));
			
			loginService.memberShipInsert(mapParameter);
			
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