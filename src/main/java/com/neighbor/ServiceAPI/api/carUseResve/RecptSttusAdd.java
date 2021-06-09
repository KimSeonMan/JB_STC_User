package com.neighbor.ServiceAPI.api.carUseResve;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.AllocService;
import com.neighbor.ServiceAPI.controller.service.LoginService;
import com.neighbor.ServiceAPI.controller.service.RecptSttusService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighbor.ServiceAPI.mobile.Counselor.Service.CarDispStatusService;
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
public class RecptSttusAdd extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(RecptSttusAdd.class);
	
	@Resource(name="allocService")
	private AllocService allocService;
		
	@Resource(name="recptSttusService")
	private RecptSttusService recptSttusService;
	
	@Resource(name="loginService")
	private LoginService loginService;
	
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "2201";
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
		START_LC_CRDNT_X, START_LC_CRDNT_Y, STRTPNT_ADRES, STRTPNT_FULL_ADRES
		, ARVL_LC_CRDNT_X, ARVL_LC_CRDNT_Y, ALOC_ADRES, ALOC_FULL_ADRES
		, MVMN_TY_CD, WHEELCHAIR_SE_CD, BRDNG_NMPR, ROUNDTRIP_AT, RM
		, MVMN_PURPS_CD, RCEPT_SE_CD, PBTRNSP_TRNSIT_AT, CHRG_CNTER_CD
		, EXPECT_REQRE_TIME, EXPECT_MVMN_DSTNC, EXPECT_CYCHG
		, MBER_NM2, MBTLNUM2, WHEELCHAIR_SE_CD2, SUPPORT_NUM2
		, MBER_NM3, MBTLNUM3, WHEELCHAIR_SE_CD3, SUPPORT_NUM3
		, SUPPORT_NUM, GRP_ID
		, JOIN_TYPE, RESVE_NO, RUN_TYPE, BOARDING_TIME, REQUIRE_TIME
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
			Map result = new HashMap();
			Map blackListChk = loginService.userBlackListChk(mapParameter);
			//Map validDTChk = loginService.userValidDTDhk(mapParameter);
			//if(validDTChk.get("STATUS").toString().equals("Y")) {
				if(blackListChk.get("STATUS").toString().equals("Y")) {
			    	
			    	//추가탑승이동약자확인
			    	/*
			    	 int shared_total = Integer.parseInt(mapParameter.get("BRDNG_NMPR").toString())-1;
			    	 if(shared_total >0) {
			    		Map param =new HashMap();
			    		param.put("shared_passenger_total",shared_total);
			    		param.put("mber_nm_2", mapParameter.get("MBER_NM2"));
			    		param.put("mbtlnum_2", mapParameter.get("MBTLNUM2"));
			    		if(shared_total>1) {
				    		param.put("mber_nm_3",mapParameter.get("MBER_NM3"));
				    		param.put("mbtlnum_3",mapParameter.get("MBTLNUM3"));
			    		}
			    		result  =  recptSttusService.searchHandicap(param);
			    		if ((int) result.get("err_cd") != 0) {
			    			resultData.put("result", result);
			                return resultData;
			            }
			    	}*/
					//접수예약 등록
					result = recptSttusService.rceptHistInsert(mapParameter);
					resultData.put("result", result);
				}else {
					result.put("err_cd", "9970");
					result.put("MEMO", blackListChk.get("MEMO"));
					result.put("MAX_LIMIT_ED_DT", blackListChk.get("MAX_LIMIT_ED_DT"));
					resultData.put("result", result);
				}
			/*}else {
				result.put("err_cd", "9990");
				result.put("err_msg", "장애등급 갱신이 필요합니다.\n상세 내용은 운영자에게 문의주세요.");
				resultData.put("result", result);
			}*/
			
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