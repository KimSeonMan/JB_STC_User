package com.neighbor.ServiceAPI.api.alloc;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.common.security.AES256Cipher;
import com.neighbor.ServiceAPI.controller.service.AllocService;
import com.neighbor.ServiceAPI.controller.service.LoginService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighbor.ServiceAPI.exception.ApiException.COMM_ERR_CODE;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 접수예약 클래스.<p>
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
public class CarReceiptAdd extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(CarReceiptAdd.class);
	
	@Resource(name="allocService")
	private AllocService allocService;
		
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1501";
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
		USER_ID, PASSENSERS, PURPOSE_CD, RECEIPT_DATETIME,
		DEPT_X, DEPT_Y, DEST_X, DEST_Y, DEPT_DESC, DEST_DESC
	}
	
	enum OptionParam
	{
		REMARK, EXP_SERICE_FEE, EXP_TRAVEL_DISTANCE, EXP_TRAVEL_HOURS
	}
	
	@Override
	public Map executeAPI(HttpServletRequest req, HttpServletResponse res,
			String trId) throws AbsException {
		// TODO Auto-generated method stub
		httpSession = req.getSession();

		SqlSession session = null;
		
		Map resultData = new HashMap();
		
		try {
			logger.info("START Query - ApiID[" + this.getApiId() + "] ");

			Map mapParameter = getParameterMap(req);

			_checkNullParameterValue(mapParameter);
			
			//접수예약 등록
			allocService.carReceiptInsert(mapParameter);
			
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