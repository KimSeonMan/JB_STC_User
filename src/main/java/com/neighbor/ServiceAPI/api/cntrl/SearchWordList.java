package com.neighbor.ServiceAPI.api.cntrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.CntrlService;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

public class SearchWordList extends AbsQuery<Map> {

	private static final Log logger = LogFactory.getLog(SearchWordList.class);
	
	@Resource(name="cntrlService")
	private CntrlService cntrlService;
	
	enum MustParam{
		
	}
	enum OptionParam{
		searchWord,
		searchTarget,
		searchLocation,
		type,
		indexPage,
		pageShowRow,
	}
	
	@Override
	public Map executeAPI(HttpServletRequest req, HttpServletResponse res, String trId) throws AbsException {
		// TODO Auto-generated method stub
		Map resultData = new HashMap();
		List resultList = new ArrayList();
		Map markerMap = new HashMap();
		
		markerMap.put("carList", new ArrayList());
		markerMap.put("supportList", new ArrayList());
		markerMap.put("userList", new ArrayList());
		Map mapParameter = getParameterMap(req);
		
		String type = mapParameter.get("type").toString();
		//count
		int allCount = 0;
		int pageNum = Integer.parseInt(mapParameter.get("indexPage").toString());
		int pageSize = Integer.parseInt(mapParameter.get("pageShowRow").toString());
		int pageCount = 0;
		int startPage = 0;
		int endPage = 0;
		//pageList
		if(type.equals("car")){
			allCount = cntrlService.cntrlSearchCarWordCount(mapParameter);
			pageCount = (int)Math.ceil((double)allCount/(double)5);
			startPage = (pageNum -1)/pageSize * pageSize + 1;
			endPage = startPage + pageSize -1;
			
			int startRow = ((pageNum -1) * pageSize)+1;
			int endRow = startRow + pageSize -1;
			
			mapParameter.put("startRow", startRow);
			mapParameter.put("endRow", endRow);
			
			//페이징 해서 보여줄 정보와 마커리스트를 따로 리스트를 가져와야 한다.
			
			//페이징 리스트
			resultList = cntrlService.cntrlSearchCarWordList(mapParameter);
			//마커리스트
			markerMap.put("carList", cntrlService.cntrlSearchCarMarkerList(mapParameter));
		}else if(type.equals("support")){
			allCount = cntrlService.cntrlSearchSupportWordCount(mapParameter);
			pageCount = (int)Math.ceil((double)allCount/(double)5);
			startPage = (pageNum -1)/pageSize * pageSize + 1;
			endPage = startPage + pageSize -1;
			
			int startRow = ((pageNum -1) * pageSize)+1;
			int endRow = startRow + pageSize -1;
			mapParameter.put("startRow", startRow);
			mapParameter.put("endRow", endRow);
			resultList = cntrlService.cntrlSearchWordSupportList(mapParameter);
		}else if(type.equals("user")){
			allCount = cntrlService.cntrlSearchUserWordCount(mapParameter);
			pageCount = (int)Math.ceil((double)allCount/(double)5);
			startPage = (pageNum -1)/pageSize * pageSize + 1;
			endPage = startPage + pageSize -1;
			int startRow = ((pageNum -1) * pageSize)+1;
			int endRow = startRow + pageSize -1;
			mapParameter.put("startRow", startRow);
			mapParameter.put("endRow", endRow);
			resultList = cntrlService.cntrlSearchWordUserList(mapParameter);
		}else if(type.equals("all")){
			
		}
		
		resultData.put("list", resultList);
		resultData.put("markerMap", markerMap);
		resultData.put("allCount", allCount);
		resultData.put("type",type);
		resultData.put("currentPageIndex",mapParameter.get("indexPage").toString());
		
		return resultData;
	}

	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "2500";
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

}
