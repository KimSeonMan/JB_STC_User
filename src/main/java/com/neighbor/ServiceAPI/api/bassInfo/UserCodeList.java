package com.neighbor.ServiceAPI.api.bassInfo;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.MngrService;
import com.neighbor.ServiceAPI.exception.ApiException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
public class UserCodeList extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(UserMngDetail.class);
	
	@Resource(name="mngrService")
	private MngrService mngrService;
		
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1201";
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

			_checkNullParameterValue(mapParameter);
			
			List centerList = mngrService.selectCenter(); // 센터리스트
			List memberCodeList = mngrService.selectMemberCode(); //회원구분 리스트
			List emailList = mngrService.selectEmailList(); //이메일 리스트
			List troblList = mngrService.selectselectTroblCode(); //장애 리스트
			List wheelchairList = mngrService.selectWheelchair(); //휠체어 리스트
			List areaTelNo = mngrService.selectAreaTelNo(); // 전화번호 국번 리스트
			List phoneNoList = mngrService.selectPhoneNo(); // 휴대폰 앞번호 리스트
		
			resultData.put("centerList", centerList);
			resultData.put("memberCodeList",memberCodeList);
			resultData.put("emailList", emailList);
			resultData.put("troblList", troblList);
			resultData.put("wheelchairList", wheelchairList);
			resultData.put("phoneNoList", phoneNoList);
			resultData.put("areaTelNo", areaTelNo);
			
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