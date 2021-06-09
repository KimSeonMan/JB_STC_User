package com.neighbor.ServiceAPI.api.login;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.LoginService;
import com.neighbor.ServiceAPI.controller.service.SmsSendService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 로그인 클래스.<p>
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
public class FindLoginId extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(FindLoginId.class);
	
	@Resource(name="loginService")
	private LoginService loginService;
	
	@Resource(name="smsSendService")
	private SmsSendService smsSendService;
		
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1113";
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
		BRTHDY, MBER_NM
	}
	
	enum OptionParam
	{
	}
	
	@Override
	public Map executeAPI(HttpServletRequest req, HttpServletResponse res,
			String trId) throws AbsException {
		// TODO Auto-generated method stub
		httpSession = req.getSession();
		String err_cd;
		SqlSession session = null;
		
		Map resultData = new HashMap();
		
		try {
			logger.info("START Query - ApiID[" + this.getApiId() + "] ");

			Map mapParameter = getParameterMap(req);

			_checkNullParameterValue(mapParameter);
			
			List<Map> findId = loginService.findLoginId(mapParameter);
			
			
			
			if(findId.size() > 1) {
				err_cd = "99";
				
			}else if(findId.size() < 1){
				err_cd = "999";
			}else {
				err_cd = "0";
				resultData.put("findId", findId);
				String mberId = (String) findId.get(0).get("MBER_ID");
				String cnterCode = (String) findId.get(0).get("CNTER_CD");
				smsSendService.sendSms(8, cnterCode, mberId,"");
			}
			
			resultData.put("err_cd", err_cd);
			
			logger.info("END Query - TXID[" + getApiId() + "] ");
			
		} catch (AbsAPIException e) {
			logger.error(e);
			throw e;
		} catch (IllegalArgumentException e) {
			logger.error(e);
			throw new ApiException("입력값을 체크 해 주세요");
		} catch (Exception e) {
			logger.error(e);
			if(e.getMessage() == null){
				throw new ApiException("입력하신 정보와 일치하는 아이디가 존재하지 않습니다.");
			}else{
				throw new ApiException("서버에서 처리 중 에러가 발생하였습니다.");
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return resultData;
	}
	
}