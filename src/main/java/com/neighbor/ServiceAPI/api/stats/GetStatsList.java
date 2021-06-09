package com.neighbor.ServiceAPI.api.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.StatsService;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

public class GetStatsList extends AbsQuery<Map>{

	private static final Log logger = LogFactory.getLog(GetStatsList.class);
	
	@Resource(name="statsService")
	private StatsService statsService;
	
	@Override
	public Map executeAPI(HttpServletRequest req, HttpServletResponse res, String trId) throws AbsException {
		httpSession = req.getSession();
		Map resultData = new HashMap();
		
		try{
			logger.info("START Query - ApiID[" + this.getApiId() + "] ");
			Map mapParameter = getParameterMap(req);
			List list = statsService.getStatsList(mapParameter.get("startDate").toString(), mapParameter.get("endDate").toString());
			resultData.put("list", list);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
		}
		return resultData;
	}

	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1800";
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
		// TODO Auto-generated method stub
		return null;
	}
	
	enum MustParam{
		startDate,endDate
	}
	
	enum OptionParam{
		
	}
}
