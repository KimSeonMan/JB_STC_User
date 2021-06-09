package com.neighbor.ServiceAPI.mobile.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.neighbor.ServiceAPI.mobile.model.ResCode;

@Component
@Scope("singleton")
public class GuideComponent {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String appKey = "ab1a06c9-6b42-40b8-a250-19ce05115fc8";
//	private String appKey = "5b284689-8d37-4a1f-bdb3-144521599adf";

	public ModelMap getGuide(String startX, String startY, String endX, String endY) {
		
		logger.info("guide Start!");
		ModelMap result = new ModelMap();
		
		String param ="";
		param += "crs=WGS84";
		param += "&st="+startX+","+startY;
		param += "&dt="+endX+","+endY;
		
		String strUrl = "https://gis.kt.com/lbs/rp/v1.4?";
		strUrl += param;
		URL url;
		try {
			url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Bearer def2a61dc53045d4fad12964695a843b97895035512f3220c3329a0a5608f2db52060291");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			int responseCode = conn.getResponseCode();
			logger.info("\nSending 'GET' request to URL: " + strUrl);
			logger.info("Response Code: " + responseCode);

			if(responseCode != 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				
				logger.error(response.toString());
				
			}else {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
	
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
	
				logger.info(response.toString());
				String responseBody = "";
				responseBody = response.toString();
				
				if(responseBody != "" || !responseBody.equals("")){
					JSONParser jsonParser = new JSONParser();
					JSONObject jsonObject = (JSONObject)jsonParser.parse(responseBody);
					JSONObject documents = (JSONObject)jsonObject.get("result");
					JSONArray routes = (JSONArray)documents.get("routes");
					JSONObject route = (JSONObject) routes.get(0);
					
					result.put("time", Math.round((double)route.get("total_duration")));
					result.put("distance", Math.round((double) route.get("total_distance")));
					result.put("fare", Math.round((double) route.get("total_toll")));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		result.put("err_cd", ResCode.SUCCESS.getCode());
		return result;
	}
	
	private static double getDistance(String sx1, String sy1, String sx2, String sy2) {
        double x1 = Double.parseDouble(sx1);
        double y1 = Double.parseDouble(sy1);
        double x2 = Double.parseDouble(sx2);
        double y2 = Double.parseDouble(sy2);
        
        double  xm = (x1*(256*2048) - x2*(256*2048)) * 0.16853;
        double  ym = (y1*(256*2048) - y2*(256*2048)) * 0.21169;

        return Math.sqrt(Math.pow(xm,2)+Math.pow(ym,2));
    }
	
	
	/*public ModelMap reverseGeocoding(String lon, String lat){
		
		
		//lon -> x ex)126.937833 
		//lat -> y ex)37.3733333
		//API desc 주소  https://developers.skplanetx.com/apidoc/kor/t-map/geocoding/#doc157
		//https://apis.skplanetx.com/tmap/geo/reversegeocoding?version={version}&lat={lat}&lon={lon}&coordType={coordType}&addressType={addressType}&callback={callback}
		ModelMap result = new ModelMap();
		//String strUrl = "https://apis.skplanetx.com/tmap/geo/reversegeocoding?version=1";
		String strUrl = "https://api2.sktelecom.com/tmap/geo/reversegeocoding?version=1";
		String type = "application/x-www-form-urlencoded";
		String param = "";
		param += "&lat="+lat;
		param += "&lon="+lon;
		param += "&coordType=WGS84GEO";
		param += "&addressType=A03";		//주소 타입.
		param += "&callback=application/json";
		
		
		URL url;
		try {
			url = new URL(strUrl+param);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", type);
			conn.setRequestProperty("Accept-Language", "ko");
			conn.setRequestProperty("appKey", appKey);
			
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
			wr.write(param);
			wr.flush();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line="";
			String responseBody = "";
			while((line = br.readLine()) != null){
				responseBody += line;
			}
			logger.info("responseBody : {}", responseBody);
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(responseBody);
			JSONObject addressObject = (JSONObject)jsonObject.get("addressInfo");

			result.put("fullAdres", addressObject.get("fullAddress"));
			result.put("city_do", addressObject.get("city_do"));
			result.put("gu_gun", addressObject.get("gu_gun"));
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}*/
	
/*public ModelMap coord2address(String x, String y){ //카카오
		
		ModelMap result = new ModelMap();
		String kakaoAppKey ="KakaoAK 46450fb278229d9bd30928ff88a4ff80";
		String strUrl = "https://dapi.kakao.com/v2/local/geo/coord2address.json?";
		String param = "";
		param += "x="+x;
		param += "&y="+y;
		
		
		URL url;
		try {
			url = new URL(strUrl+param);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
//			conn.setRequestProperty("Accept-Language", "ko");
			conn.setRequestProperty("Authorization", kakaoAppKey);
			conn.connect();
//			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
//			wr.write(param);
//			wr.flush();
			InputStream inputStream = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String line="";
			String responseBody = "";
			while((line = br.readLine()) != null){
				responseBody += line;
			}
			logger.info("responseBody : {}", responseBody);
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(responseBody);
			JSONArray documents = (JSONArray)jsonObject.get("documents");
			JSONObject document =(JSONObject)documents.get(0); 
			JSONObject address =(JSONObject) document.get("address"); 
			
			result.put("region_1depth_name", address.get("region_1depth_name"));
			result.put("region_2depth_name", address.get("region_2depth_name"));
			result.put("address_name", address.get("address_name"));
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("MalformedURLException ",e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IOException ",e);
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("ParseException ",e);
			e.printStackTrace();
		}
		
		return result;
	}*/
	
public ModelMap coord2address(String x, String y){ //리버스지오코드
		
		ModelMap result = new ModelMap();
		String key ="Bearer def2a61dc53045d4fad12964695a843b97895035512f3220c3329a0a5608f2db52060291";
		String strUrl = "http://gis.kt.com/search/v1.0/utilities/geocode?";
		String param = "";
		param += "point.lng="+x;
		param += "&point.lat="+y;
		
		
		URL url;
		try {
			url = new URL(strUrl+param);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", key);
			conn.connect();
			InputStream inputStream = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String line="";
			String responseBody = "";
			while((line = br.readLine()) != null){
				responseBody += line;
			}
			logger.info("responseBody : {}", responseBody);
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(responseBody);
			JSONArray documents = (JSONArray)jsonObject.get("residentialAddress");
			JSONObject document =(JSONObject)documents.get(0); 
			JSONArray parcelAddress =(JSONArray) document.get("parcelAddress"); 
			JSONObject address = (JSONObject)parcelAddress.get(0);
			
			result.put("region_1depth_name", address.get("siDo"));
			result.put("region_2depth_name", address.get("siGunGu1"));
			result.put("address_name", address.get("fullAddress"));
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("MalformedURLException ",e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IOException ",e);
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("ParseException ",e);
			e.printStackTrace();
		}
		
		return result;
	}
}
