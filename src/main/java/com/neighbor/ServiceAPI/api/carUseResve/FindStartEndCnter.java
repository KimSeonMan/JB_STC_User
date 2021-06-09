package com.neighbor.ServiceAPI.api.carUseResve;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighbor.ServiceAPI.controller.service.CntrlService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.RecptSttusService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighbor.ServiceAPI.mobile.Counselor.Service.CarDispStatusService;
import com.neighbor.ServiceAPI.mobile.service.MobileApiService;
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
public class FindStartEndCnter extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(FindStartEndCnter.class);
	
	@Resource(name="carDispStatusService")
	private CarDispStatusService carDispStatusService;
	
	@Resource(name="recptSttusService")
	private RecptSttusService recptSttusService;

	@Resource(name="cntrlService")
	private CntrlService cntrlService;

	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "2204";
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
		MBER_ID, CNTER_CD, DEPT_X, DEPT_Y, DEST_X, DEST_Y
	}
	
	enum OptionParam
	{
		DEPT_DESC, DEST_DESC, DEPT_FULL_DESC, DEST_FULL_DESC
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

			String startX = "";
			String startY = "";
			String endX = "";
			String endY = "";
			if (mapParameter.get("DEPT_X").toString().indexOf("K") != -1 ||
					mapParameter.get("DEPT_Y").toString().indexOf("K") != -1){
				String rX = mapParameter.get("DEPT_X").toString().replace("K","");
				String rY = mapParameter.get("DEPT_Y").toString().replace("K","");

				Map convertXY = cntrlService.convertWGS(rX,rY);
				startX = convertXY.get("x").toString();
				startY = convertXY.get("y").toString();
			}else{
				startX = mapParameter.get("DEPT_X").toString();
				startY = mapParameter.get("DEPT_Y").toString();
			}

			if (mapParameter.get("DEST_X").toString().indexOf("K") != -1 ||
					mapParameter.get("DEST_Y").toString().indexOf("K") != -1){
				String rX = mapParameter.get("DEST_X").toString().replace("K","");
				String rY = mapParameter.get("DEST_Y").toString().replace("K","");

				Map convertXY = cntrlService.convertWGS(rX,rY);
				endX = convertXY.get("x").toString();
				endY = convertXY.get("y").toString();
			}else{
				endX = mapParameter.get("DEST_X").toString();
				endY = mapParameter.get("DEST_Y").toString();
			}

			Map checkResult = carDispStatusService.carReference(mapParameter.get("CNTER_CD").toString()
										, mapParameter.get("MBER_ID").toString()
										, startX
										, startY
										, endX
										, endY
										, mapParameter.get("CNTER_CD").toString()
					);	
			// start_cnter_cd
			if(checkResult.containsKey("result") && "0".equals(checkResult.get("err_cd").toString())){
				List checkResultList = (List) checkResult.get("result");
	 			if(checkResultList.size() != 0){
					List cnterInfo = recptSttusService.getCnterInfo(((Map) checkResultList.get(0)).get("chrg_cnter_cd").toString());
//					List cnterInfo = recptSttusService.getCnterInfo(((Map) checkResultList.get(0)).get("start_cnter_cd").toString());
					resultData.put("cnterInfo", cnterInfo);
				}
			}

			resultData.put("startX", startX);
			resultData.put("startY", startY);
			resultData.put("endX", endX);
			resultData.put("endY", endY);

			resultData.put("checkResult", checkResult);
			
			
			
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