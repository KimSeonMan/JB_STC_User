package com.neighbor.ServiceAPI.api.userSiteTemplate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighbor.ServiceAPI.api.common.MultiFileUpload;
import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.UserSiteTemplateService;
import com.neighbor.ServiceAPI.exception.ApiException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 공지사항 등록 클래스.<p>
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
public class NoticeAdd extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(NoticeAdd.class);
	
	@Resource(name="userSiteTemplateService")
	private UserSiteTemplateService userSiteTemplateService;
		
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1222";
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
		TITLE, CTNT, NOTICE_CLS_CD2, CNTNTS_TYPE2_CD2, CNTNTS_NO, CNTER_CD
	}
	
	enum OptionParam
	{
		attachFiles, CNTNTS_CLS_CD, NOTICE_NO, U_CNTNTS_NO, SECRET_CHECK
	}	
	
	@Transactional
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
//			String cnter_cd = (String) httpSession.getAttribute("cnter_cd");
//			if(cnter_cd == null || "".equals(cnter_cd)) {
			/****2020.03.31 모든 게시물 시스템NAT-1-001**/
			String cnter_cd = "NAT-1-001"; //이동약자(사용자) 메뉴구성
//			}
			mapParameter.put("CNTER_CD", cnter_cd);
			
			String CNTNTS_TYPE2_CD2 = mapParameter.get(MustParam.CNTNTS_TYPE2_CD2.name()).toString();
			int noticeMaxCount = userSiteTemplateService.selectNoticeMaxCount(mapParameter);
			
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) req;

			List<MultipartFile> fileList = mRequest.getFiles("attachFiles[]");
			
			mRequest.setAttribute("CNTNTS_TYPE2_CD2", CNTNTS_TYPE2_CD2);
			mRequest.setAttribute("CNTNTS_NO", mapParameter.get(MustParam.CNTNTS_NO.name()));
			mRequest.setAttribute("NOTICE_CLS_CD2", mapParameter.get(MustParam.NOTICE_CLS_CD2.name()));
			mRequest.setAttribute("CNTER_CD", cnter_cd);
			
			if(fileList.size() > 0) {
				List list = mfu.fileUpload(mRequest);
				
				String[] array;
				
				for(int i=0; i < list.size(); i++){
					array = list.get(i).toString().replace("{", "").replace("}", "").trim().split(",");
					for(int j=0; j < 5; j++){
						if(array[j].split("=")[0].trim().equals("file_url")) {
							mapParameter.put("file_url", array[j].split("=")[1]);
						} else if(array[j].split("=")[0].trim().equals("fileRealName")) {
							mapParameter.put("fileRealName", array[j].split("=")[1]);
						} else {
							mapParameter.put("fileSaveName", array[j].split("=")[1]);
						}
					}
					mapParameter.put("board_no", noticeMaxCount+"");
					mapParameter.put("cntnts_no", mapParameter.get(MustParam.CNTNTS_NO.name()));
					mapParameter.put("file_attch_id", CNTNTS_TYPE2_CD2);
					userSiteTemplateService.fileInfoInsert(mapParameter);
				}
			}
			
			_checkNullParameterValue(mapParameter);
			
			mapParameter.put("USER_ID", httpSession.getAttribute("user_id"));
			if(mapParameter.get("CNTNTS_TYPE2_CD2") != null) {
				if(mapParameter.get("CNTNTS_TYPE2_CD2").equals("40")) { //Q&A
					mapParameter.put("INDENT", noticeMaxCount);
				}
			}
			userSiteTemplateService.noticeInsert(mapParameter);
			
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