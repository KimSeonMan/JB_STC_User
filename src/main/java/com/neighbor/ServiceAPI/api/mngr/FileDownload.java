package com.neighbor.ServiceAPI.api.mngr;


import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighbor.ServiceAPI.api.common.MultiFileUpload;
import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.MngrService;
import com.neighbor.ServiceAPI.exception.ApiException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 정보컨텐츠관리 등록 클래스.<p>
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
public class FileDownload extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(FileDownload.class);
	
	@Resource(name="mngrService")
	private MngrService mngrService;
	
	
			
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1218";
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
		gubn, saveName, name
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
			/*Map mapParameter = getParameterMap(req);*/
			
			String PROPERTY_PATH = "/globals.properties";
			ClassPathResource resource = new ClassPathResource(PROPERTY_PATH);
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			String file_path = "";
			String CNTNTS_TYPE2_CD2 = req.getParameter("gubn");
			
			if(CNTNTS_TYPE2_CD2 != null) {
				if(CNTNTS_TYPE2_CD2.equals("40")) { //Q&A
					file_path = props.getProperty("Globals.File.BOARD.Path");
				} else if(CNTNTS_TYPE2_CD2.equals("50")) { //FAQ
					file_path = props.getProperty("Globals.File.FAQ.Path");
				} else { //60 공지사항
					file_path = props.getProperty("Globals.File.NOTICE.Path");
				}
			}
			
			String saveName = URLDecoder.decode(req.getParameter("saveName"),"UTF-8");
			File file = new File(file_path +"/"+ saveName);

			String fileName = req.getParameter("name");
			byte[] arBytes = new byte[(int)file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(arBytes);
			res.setContentType("application/file; charset=UTF-8");
			
			res.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName,"utf-8") + "\";");
            res.setHeader("Content-Transfer-Encoding", "binary"); 
			
            OutputStream out = res.getOutputStream();
            out.write(arBytes);
            out.flush();
            out.close();
			
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