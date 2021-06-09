package com.neighbor.ServiceAPI.api.userSiteTemplate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.UserSiteTemplateService;
import com.neighbor.ServiceAPI.exception.ApiException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 공지사항상세 클래스.<p>
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
public class NoticeDetail extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(NoticeDetail.class);
	
	@Resource(name="userSiteTemplateService")
	private UserSiteTemplateService userSiteTemplateService;
		
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1224";
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
		CNTNTS_TYPE2_CD, CNTNTS_CLS_CD, CNTNTS_NO, NOTICE_NO, CNTER_CD
	}
	
	enum OptionParam
	{
		HIT_CNT,TYPE
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

			Map mapParameter = getParameterMap(req);
//			String cnter_cd = (String) httpSession.getAttribute("cnter_cd");
//			if(cnter_cd == null || "".equals(cnter_cd)) {
			/****2020.03.31 모든 게시물 시스템NAT-1-001**/
			String cnter_cd = "NAT-1-001"; //이동약자(사용자) 메뉴구성
//			}
			mapParameter.put("CNTER_CD", cnter_cd);

			_checkNullParameterValue(mapParameter);
			
			Map noticeDetail = userSiteTemplateService.noticeDetail(mapParameter);
			List fileInfoList = userSiteTemplateService.fileInfoList(mapParameter);
			
			String CNTNTS_TYPE2_CD = mapParameter.get(MustParam.CNTNTS_TYPE2_CD.name()).toString();
			
			if(!CNTNTS_TYPE2_CD.equals("50")) {
				if(mapParameter.get(OptionParam.HIT_CNT.name()) != null) {
					userSiteTemplateService.noticeUpdateHitCnt(mapParameter);
				}
			}
			
			resultData.put("noticeDetail", noticeDetail);
			resultData.put("fileInfoList", fileInfoList);
			resultData.put("type",mapParameter.get("TYPE"));
			
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