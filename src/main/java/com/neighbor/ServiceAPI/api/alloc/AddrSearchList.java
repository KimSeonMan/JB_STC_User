package com.neighbor.ServiceAPI.api.alloc;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.AllocService;
import com.neighbor.ServiceAPI.controller.service.CntrlService;
import com.neighbor.ServiceAPI.exception.ApiException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 주소검색.<p>
 * 2. 처리개요 : <p>
 * 3. 주의사항 : <p>
 *  <pre>
 *  <b>History:</b> 
 *     작성자 : 최재영, 1.0, 2017/02/08  초기 작성
 *  </pre>
 *  
 * @author 최종 수정자 : 최재영
 * @version 1.0
 * @see
 * <p/>
 */
public class AddrSearchList extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(AddrSearchList.class);
	
	@Resource(name="allocService")
	private AllocService allocService;
	
	@Resource(name="cntrlService")
	private CntrlService cntrlService;
	
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1513";
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
		addr,
		x_coor,
		y_coor,
		page,
		count,
		
	}
	
	enum OptionParam
	{
		
	}
	
	@Override
	public Map executeAPI(HttpServletRequest req, HttpServletResponse res,
			String trId) throws AbsException {
		// TODO Auto-generated method stub
		httpSession = req.getSession();
		Map result = new HashMap();
		Map resultData = new HashMap();
		
		try {
			logger.info("START Query - ApiID[" + this.getApiId() + "] ");

			Map mapParameter = getParameterMap(req);
			
			String addr = mapParameter.get("addr").toString();
			String x_coor = mapParameter.get("x_coor").toString();
			String y_coor = mapParameter.get("y_coor").toString();
			String page = mapParameter.get("page").toString();
			String count = mapParameter.get("count").toString();

			resultData = cntrlService.searchAddrListByNaver(addr, page);

			List<Map> poiList = (ArrayList)resultData.get("list");

			List<Map> resultList = new ArrayList<>();
			for(int i=0; i<poiList.size(); i++){
				Map poi = new HashMap();

				String title = poiList.get(i).get("title").toString().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				poi.put("place_name",title);


				String roadAddress = poiList.get(i).get("roadAddress").toString().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				poi.put("road_address_name",roadAddress);

				String address = poiList.get(i).get("address").toString().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				poi.put("address_name",address);

				poi.put("x",poiList.get(i).get("mapx").toString() + "K");
				poi.put("y",poiList.get(i).get("mapy").toString() + "K");
				resultList.add(poi);


			}

			result.put("list",resultList);
			result.put("status","success");
			result.put("type",resultData.get("type"));
			result.put("totalCount",resultList.size());

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
		return result;
	}
}