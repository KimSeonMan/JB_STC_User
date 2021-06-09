package com.neighbor.ServiceAPI.api.carUseResve;


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
import com.neighbor.ServiceAPI.controller.service.RecptSttusService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 접수예약취소 클래스.<p>
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
public class PopUpInfo extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(PopUpInfo.class);
	
	@Resource(name="recptSttusService")
	private RecptSttusService recptSttusService;
	
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "2203";
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
		MBER_ID , CNTER_CD
	}
	
	enum OptionParam
	{
		
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
			
			//최근 출발지/목적지 목록
			List startPointList = recptSttusService.startPointList(mapParameter);
			List endPointList = recptSttusService.endPointList(mapParameter);
			
			// 휠체어 구분
			List wheelChairTpList = recptSttusService.wheelChairTpList();
			
			// 이동목적 구분
			List moveTpList = recptSttusService.moveTpList();
			
			//센터 예약유형설정
			List cnterInfo = recptSttusService.getCnterInfo(mapParameter.get("CNTER_CD").toString());
			
			resultData.put("startPointList", startPointList);
			resultData.put("endPointList", endPointList);
			resultData.put("wheelChairTpList", wheelChairTpList);
			resultData.put("moveTpList", moveTpList);
			resultData.put("cnterInfo", cnterInfo.get(0));
			
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