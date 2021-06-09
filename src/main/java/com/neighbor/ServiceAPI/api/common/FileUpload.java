package com.neighbor.ServiceAPI.api.common;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;



import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.common.util.Prompt;
import com.neighbor.ServiceAPI.common.util.RequestUtil;
import com.neighbor.ServiceAPI.exception.ApiException;


/**
 * 1. 기능 : 파일업로드 클래스.<p>
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
public class FileUpload extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(FileUpload.class);
	
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1110";
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
//		gubn
	}
	
	enum OptionParam
	{
		name
	}
	
	@Override
	public Map executeAPI(HttpServletRequest req, HttpServletResponse res,
			String trId) throws AbsException {
		// TODO Auto-generated method stub
		httpSession = req.getSession();

		Map resultData = new HashMap();
		
		try {
			logger.info("START Query - ApiID[" + this.getApiId() + "] ");

			Map mapParameter = getParameterMap(req);

			_checkNullParameterValue(mapParameter);
			
//			String gubn = (String) mapParameter.get(MustParam.gubn.name());
			Boolean f = false;
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			fileUpload.setSizeMax(20000000);
			List items = fileUpload.parseRequest(req);
			Iterator iterator = items.iterator();
			while (iterator.hasNext()) {
				FileItem file = (FileItem) iterator.next();
				if (!file.isFormField() && "FILE".equals(file.getFieldName()) && file.getSize() > 0) {
					String originName = file.getName();
					// originName = new String (originName.getBytes
					// ("iso-8859-1"), "UTF-8");
					String FILE_EXTENSION = originName.substring(originName.lastIndexOf(".") + 1);
					String FILE_NM = originName.substring(originName.lastIndexOf("\\") + 1, originName.lastIndexOf("."));
					long curTime = System.currentTimeMillis();
					String FILE_ID = FILE_NM + Long.toString(curTime);
					String FILE_CONTENT_TYPE = file.getContentType();
					// String FILE_PATH =
					// req.getSession().getServletContext().getRealPath("/upload/BOARD_001");
					ClassPathResource resource = new ClassPathResource(getPerpertyPath());
					Properties props = PropertiesLoaderUtils.loadProperties(resource);
//					String FILE_PATH = props.getProperty("Globals.File."+gubn+".Path");
					String FILE_PATH = props.getProperty("Globals.File.FAQ.Path");
					if (RequestUtil.writeFile(file, FILE_PATH, FILE_ID, FILE_EXTENSION)) {
						f = true;
						Map fileMap = new HashMap();
						fileMap.put("FILE_ID", FILE_ID);
						fileMap.put("FILE_PATH", FILE_PATH);
						fileMap.put("FILE_NM", FILE_NM);
						fileMap.put("FILE_EXTENSION", FILE_EXTENSION);
						fileMap.put("FILE_CONTENT_TYPE", FILE_CONTENT_TYPE);
						resultData.put("FILE", fileMap);
					} else {
						throw new ApiException(Prompt.UPLOADERROR);
					}
				}
			}
			resultData.put("success", f);
			return resultData;
		} catch (AbsAPIException e) {
			logger.error(e);
			throw e;
		} catch (IllegalArgumentException e) {
			logger.error(e);
			throw new ApiException("입력값을 체크 해 주세요");
		} catch (Exception e) {
			logger.error(e);
			throw new ApiException("서버에서 처리 중 에러가 발생하였습니다.\n현상이 반복될 경우 고객센터((02)2012-9114)로 문의하시기 바랍니다.");
		}
	}
}