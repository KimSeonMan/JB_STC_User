package com.neighbor.ServiceAPI.api.mngr;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighbor.ServiceAPI.api.common.MultiFileUpload;
import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.MngrService;
import com.neighbor.ServiceAPI.exception.ApiException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 관련사이트관리등록 클래스.<p>
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
public class SiteMngAdd extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(SiteMngAdd.class);
	
	@Resource(name="mngrService")
	private MngrService mngrService;
		
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1241";
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
		SITE_NAME, SITE_SEQ, LINKED_URL, CNTER_CD
	}
	
	enum OptionParam
	{
		attachFiles, SITE_NO
	}
	
	@Override
	public Map executeAPI(HttpServletRequest req, HttpServletResponse res,
			String trId) throws AbsException {
		// TODO Auto-generated method stub
		httpSession = req.getSession();

		Map resultData = new HashMap();
		
		try {
			logger.info("START Query - ApiID[" + this.getApiId() + "] ");
			MultiFileUpload mfu = MultiFileUpload.getInstance(); 
			Map mapParameter = getParameterMap(req);
			
			String CNTNTS_TYPE2_CD2 = "LINKED_SITE";
			
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) req;

			List<MultipartFile> fileList = mRequest.getFiles("attachFiles[]");
			
			mRequest.setAttribute("CNTNTS_TYPE2_CD2", CNTNTS_TYPE2_CD2);
			
			if(fileList.size() > 0) {
				List list = mfu.fileUpload(mRequest);
				String[] array;
				
				for(int i=0; i < list.size(); i++){
					array = list.get(i).toString().replace("{", "").replace("}", "").trim().split(",");
					for(int j=0; j < 2; j++){
						if(array[j].split("=")[0].trim().equals("file_url")) {
							mapParameter.put("SITE_IMG_URL", array[j].split("=")[1]);
						} else if(array[j].split("=")[0].trim().equals("fileRealName")) {
							mapParameter.put("SITE_IMG_REAL_NAME", array[j].split("=")[1]);
						}
					}
				}
			}
			
			_checkNullParameterValue(mapParameter);
			
			mngrService.siteMngInsert(mapParameter);
			
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