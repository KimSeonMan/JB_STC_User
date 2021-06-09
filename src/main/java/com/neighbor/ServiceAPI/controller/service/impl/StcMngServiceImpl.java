package com.neighbor.ServiceAPI.controller.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.neighbor.ServiceAPI.controller.mapper.StcMngMapper;
import com.neighbor.ServiceAPI.controller.service.StcMngService;
import com.neighbor.ServiceAPI.controller.view.HomeController;
@Service("StcMngService")
public class StcMngServiceImpl implements StcMngService {
	private final Log logger = LogFactory.getLog(StcMngServiceImpl.class);
	@Resource(name="StcMngMapper")
	private StcMngMapper stcMngMapper;

	
	@Override
	public List getSctMngList(Map mapParameter) {
		return stcMngMapper.getSctMngList(mapParameter);
	}


	@Override
	public List getStcMngListTotalCount(Map mapParameter) {
		return stcMngMapper.getStcMngListTotalCount(mapParameter);
	}


	@Override
	public Map getSctMngDetailInfo(Map mapParameter) {
		return stcMngMapper.getSctMngDetailInfo(mapParameter);
	}


	@Override
	public List getUpperCnterList(Map stcMngDetail) {
		return stcMngMapper.getUpperCnterList(stcMngDetail);

	}


	@Override
	public Map getCnterCdNo() {
		Map data = new HashMap<String, Integer>();
		data.put("NAT_NO", stcMngMapper.getCnterNo("NAT-1"));
		data.put("WDR_NO", stcMngMapper.getCnterNo("WDR-2"));
		data.put("ARE_2_NO", stcMngMapper.getCnterNo("ARE-2"));
		data.put("ARE_3_NO", stcMngMapper.getCnterNo("ARE-3"));
		
		return data;
	}


	@Override
	public void stcMngAdd(Map mapParameter) {
		String apiKey = "0d275dc10b1bb95131478c3be6433bd6";
		String inputLine;
		BufferedReader bufferedReader = null;
		String resultSet = null;
		
		String address = mapParameter.get("ADRES") + " " + mapParameter.get("ADRES_DETAIL"); 
		
		String requestUrl = "http://dapi.kakao.com/v2/local/geo/addressSearch";
		requestUrl += "?apikey=" + apiKey;
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			requestUrl += "&q=" + URLEncoder.encode(address, "UTF-8");
			requestUrl += "&output=JSON";
		
			URL url;
			url = new URL(requestUrl);
		
			URLConnection conn = url.openConnection();
			bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));	
				
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(bufferedReader);
			String jsonToString = jsonObject.toJSONString();
			
			
			ObjectMapper objMapper = new ObjectMapper();
			resultMap = objMapper.readValue(jsonToString, 
					new TypeReference<HashMap<String,Object>>(){});
			
			resultMap = (Map<String,Object>)resultMap.get("channel");
			List<Map<String,Object>> resultMapList = (List<Map<String, Object>>)resultMap.get("item");
			 
			if(resultMapList.size() != 0) {
				mapParameter.put("LC_CRDNT_X", resultMapList.get(0).get("lng"));
				mapParameter.put("LC_CRDNT_Y", resultMapList.get(0).get("lat"));
			} else {
				mapParameter.put("LC_CRDNT_X", 0);
				mapParameter.put("LC_CRDNT_Y", 0);
			}
			mapParameter.put("MRDN_AT", "N");
			
			stcMngMapper.stcMngAdd(mapParameter);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}
		
	}


	@Override
	public void stcMngModify(Map mapParameter) {
		String apiKey = "0d275dc10b1bb95131478c3be6433bd6";
		String inputLine;
		BufferedReader bufferedReader = null;
		String resultSet = null;
		
		String address = mapParameter.get("ADRES") + " " + mapParameter.get("ADRES_DETAIL"); 
		
		String requestUrl = "http://http://dapi.kakao.com/v2/local/geo/addressSearch";
		requestUrl += "?apikey=" + apiKey;
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			requestUrl += "&q=" + URLEncoder.encode(address, "UTF-8");
			requestUrl += "&output=JSON";
		
			URL url;
			url = new URL(requestUrl);
		
			URLConnection conn = url.openConnection();
			bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));	
				
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(bufferedReader);
			String jsonToString = jsonObject.toJSONString();
			
			
			ObjectMapper objMapper = new ObjectMapper();
			resultMap = objMapper.readValue(jsonToString, 
					new TypeReference<HashMap<String,Object>>(){});
			
			resultMap = (Map<String,Object>)resultMap.get("channel");
			List<Map<String,Object>> resultMapList = (List<Map<String, Object>>)resultMap.get("item");
			 
			if(resultMapList.size() != 0) {
				mapParameter.put("LC_CRDNT_X", resultMapList.get(0).get("lng"));
				mapParameter.put("LC_CRDNT_Y", resultMapList.get(0).get("lat"));
			} else {
				mapParameter.put("LC_CRDNT_X", 0);
				mapParameter.put("LC_CRDNT_Y", 0);
			}
			mapParameter.put("MRDN_AT", "N");
			
			stcMngMapper.stcMngModify(mapParameter);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}		
	}


	@Override
	public void stcMngDelete(Map mapParameter) {
		String[] cnterCdArr = mapParameter.get("CNTER_CD").toString().split(",");
		
		for(String cnterCd : cnterCdArr) {
			stcMngMapper.stcMngDelete(cnterCd);
		}
	}
	
}
