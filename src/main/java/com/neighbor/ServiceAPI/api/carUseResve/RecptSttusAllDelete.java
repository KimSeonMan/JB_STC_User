package com.neighbor.ServiceAPI.api.carUseResve;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.common.security.AES256Cipher;
import com.neighbor.ServiceAPI.controller.service.AllocService;
import com.neighbor.ServiceAPI.controller.service.LoginService;
import com.neighbor.ServiceAPI.controller.service.SmsSendService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighbor.ServiceAPI.exception.ApiException.COMM_ERR_CODE;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 취소예약 내역삭제 클래스.<p>
 * 2. 처리개요 : <p>
 * 3. 주의사항 : <p>
 *  <pre>
 *  <b>History:</b> 
 *     작성자 : 강보경
 *  </pre>
 *  
 * @author 최종 수정자 : 강보경
 * @see
 * <p/>
 */
public class RecptSttusAllDelete extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(RecptSttusDelete.class);
	
	@Resource(name="allocService")
	private AllocService allocService;
		
	@Autowired
	SmsSendService smsSendService;
	
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "2205";
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
		RESVE_DT, MBER_ID, CNTER_CD
	}
	
	enum OptionParam
	{
		
	}
	
	@Override
	public Map executeAPI(HttpServletRequest req, HttpServletResponse res,String trId) throws AbsException {
		// TODO Auto-generated method stub
		httpSession = req.getSession();

		SqlSession session = null;
		
		Map resultData = new HashMap();
		
		try {
			logger.info("START Query - ApiID[" + this.getApiId() + "] ");
			
			Map mapParameter = getParameterMap(req);
			Map parameters =new HashMap();
			_checkNullParameterValue(mapParameter);

			String resve_dt[]=req.getParameter(MustParam.RESVE_DT.name()).toString().split(",");
			
			for(int i=0; i<resve_dt.length;i++) {
				parameters.put("resve_dt", resve_dt[i]);
				parameters.put("mber_id", req.getParameter(MustParam.MBER_ID.name()).toString());
				parameters.put("cnter_cd", req.getParameter(MustParam.CNTER_CD.name()).toString());
				parameters.put("register_id", req.getParameter(MustParam.MBER_ID.name()).toString());
				allocService.carRecptSttusModify(parameters);
			}
			//예약취소 메세지전송
			smsSendService.sendSms(6, req.getParameter(MustParam.CNTER_CD.name()).toString(), req.getParameter(MustParam.MBER_ID.name()).toString(), "");
			logger.info("END Query - TXID[" + getApiId() + "] ");
		} catch (AbsAPIException e) {
			logger.error(e);
			throw e;
		} catch (IllegalArgumentException e) {
			logger.error(e);
			throw new ApiException("입력값을 체크 해 주세요");
		} catch (Exception e) {
			logger.error(e);
			throw new ApiException("서버에서 처리 중 에러가 발생하였습니다.");
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return resultData;
	}
	
}