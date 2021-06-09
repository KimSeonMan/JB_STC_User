package com.neighbor.ServiceAPI.api.login;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.neighbor.ServiceAPI.api.common.MultiFileUpload;
import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.LoginService;
import com.neighbor.ServiceAPI.controller.service.SmsSendService;
import com.neighbor.ServiceAPI.controller.service.UserSiteTemplateService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 회원가입신청 클래스.<p>
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
public class MemberShipAdd extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(MemberShipAdd.class);
	
	@Resource(name="loginService")
	private LoginService loginService;
		
	@Resource(name="userSiteTemplateService")
	private UserSiteTemplateService userSiteTemplateService;
	
	@Resource(name="smsSendService")
	private SmsSendService smsSendService;
	
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1106";
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
		CNTER_CD, MBER_ID, PASSWORD, MBER_NM, SEXDSTN, BRTHDY, ZIP, ADRES, ADRES_DETAIL,
		TELNO, MBTLNUM, EMAIL, SMS_RECPTN_AT, MBER_NT_CD
	}
	
	enum OptionParam
	{
		MBER_SE_CD, TROBL_KND_CD, TROBL_GRAD, ASSTN_PERSON_ENNC,WHEELCHAIR_SE_CD, WHEELCHAIR_USE_PD,
		ASSTN_NEED_AT, DS_MLRD_POSBL_AT, ATCHMNFL_REAL_NM, attachFiles, BASIC_LIVELIHOOD_AT
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
			MultiFileUpload mfu = MultiFileUpload.getInstance(); 
			Map mapParameter = getParameterMap(req);
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) req;

			
			_checkNullParameterValue(mapParameter);
			
			mapParameter.put("MBER_SE_CD", "10"); //회원구분코드
			mapParameter.put("MBER_STTUS_CD", "01"); //회원가입 요청 상태
//			mapParameter.put("DRVER_STTUS_CD", "20");
			
			if(httpSession.getAttribute("user_id") != null) {
				mapParameter.put("REGISTER_ID", httpSession.getAttribute("user_id"));
			} else {
				mapParameter.put("REGISTER_ID", mapParameter.get(MustParam.MBER_ID.name()));
			}
			
			//회원가입 등록
			loginService.memberShipInsert(mapParameter);
			
			mRequest.setAttribute("CNTNTS_TYPE2_CD2", "70");
			mRequest.setAttribute("CNTNTS_NO", "0");
			mRequest.setAttribute("NOTICE_CLS_CD2", "0");
			List<MultipartFile> fileList = mRequest.getFiles("attachFiles[]");
			if(fileList.size() > 0) {
				List list = mfu.fileUpload(mRequest);
				String[] array;
				
				Map data = new HashMap<>();
				for(int i=0; i < list.size(); i++){
					array = list.get(i).toString().replace("{", "").replace("}", "").trim().split(",");
					for(String str : array){
						if(str.split("=")[0].trim().equals("file_url")) {
							mapParameter.put("FLPTH", str.split("=")[1]);
						} else if(str.split("=")[0].trim().equals("fileRealName")) {
							mapParameter.put("ATCHMNFL_REAL_NM", str.split("=")[1]);
						} else if(str.split("=")[0].trim().equals("fileSaveName")){
							mapParameter.put("ATCHMNFL_STRE_NM", str.split("=")[1]);
						}
					}
				}
				data.put("CNTNTS_NO", "0");
				data.put("FILE_ATTCH_ID", "70");
				data.put("CNTER_CD", mapParameter.get("CNTER_CD").toString());
				data.put("FLPTH", mapParameter.get("FLPTH").toString());
				data.put("ATCHMNFL_REAL_NM", mapParameter.get("ATCHMNFL_REAL_NM").toString());
				data.put("ATCHMNFL_STRE_NM", mapParameter.get("ATCHMNFL_STRE_NM").toString());
				
				userSiteTemplateService.fileInfoInsertNew(data);
				Map attachInfoDetail = userSiteTemplateService.attachInfoDetail(data);
				mapParameter.put("ATCHMNFL_NO", attachInfoDetail.get("ATCHMNFL_NO"));
				
				loginService.updateHaniUpdateAtchNo(mapParameter);
				
			}
			smsSendService.sendSms(1, mapParameter.get("CNTER_CD").toString(), mapParameter.get("MBER_ID").toString(),"");
			
			
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