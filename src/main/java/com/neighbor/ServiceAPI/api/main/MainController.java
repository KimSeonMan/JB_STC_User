package com.neighbor.ServiceAPI.api.main;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.MainService;
import com.neighbor.ServiceAPI.controller.service.StcMngService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighbor.ServiceAPI.mobile.domain.CnterInfo;
import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 메인페이지 클래스.<p>
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
public class MainController extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(MainController.class);
	
	@Resource(name="mainService")
	private MainService mainService;
		
	@Autowired
	private StcMngService stcMngService;
	
	
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1000";
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
			
			String cnter_cd = (String) httpSession.getAttribute("cnter_cd");
			if(cnter_cd != null && !cnter_cd.equals("")) {
				mapParameter.put("CNTER_CD", cnter_cd);
			} else {
				mapParameter.put("CNTER_CD", "NAT-1-001"); //이동약자(사용자) 메뉴구성
			}

			_checkNullParameterValue(mapParameter);
			
			//공지사항리스트
			List noticeList = mainService.selectNoticeList(mapParameter);
			resultData.put("noticeList", noticeList);
			
			//보도자료리스트
			List reportList = mainService.selectReportList(mapParameter);
			resultData.put("reportList", reportList);
			
			//QA리스트
			List QAList = mainService.selectQAList(mapParameter);
			resultData.put("QAList", QAList);
			
			//센터정보
			Map CnterInfo = stcMngService.getSctMngDetailInfo(mapParameter);
			resultData.put("CnterInfo", CnterInfo);
			
			//설문조사리스트
			//List qestnarList = mainService.selectQestnarList(mapParameter);
			//resultData.put("qestnarList", qestnarList);
			
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