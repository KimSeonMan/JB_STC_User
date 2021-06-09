package com.neighbor.ServiceAPI.api.login;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.common.security.AES256Cipher;
import com.neighbor.ServiceAPI.controller.service.LoginService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighbor.ServiceAPI.exception.ApiException.COMM_ERR_CODE;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 로그인 클래스.<p>
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
public class Login extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(Login.class);
	
	@Resource(name="loginService")
	private LoginService loginService;
		
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1100";
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
		user_id, pwd
	}
	
	enum OptionParam
	{
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

			Map mapParameter = getParameterMap(req);

			_checkNullParameterValue(mapParameter);
			
			//비밀번호 SHA256 암호화
			String pw = (String) mapParameter.get(MustParam.pwd.name());
			
			//아이디로 정보를 조회한 후 코드에서 비밀번호를 비교
			Map userInfo = (Map) loginService.selectUserInfo(mapParameter);
			Map userInfo_wID = loginService.selectUserInfo_wID(mapParameter);
			
			if(userInfo == null) {
				if(userInfo_wID != null){ //아이디 존재
					if(userInfo_wID.get("LOGIN_LIMIT_YN").equals("N")){ //로그인제한 N
						int limit = Integer.parseInt(userInfo_wID.get("LOGIN_LIMIT").toString());
						Map param =new HashMap();
						param.put("user_id", mapParameter.get("user_id"));
						param.put("limit", (limit+1));
						if(limit > 6) {
							param.put("limit_yn", 'Y');
						}
						loginService.updateLimit(param);
						
						if(limit > 6) {
							throw new ApiException("로그인 서비스 이용이 제한되었습니다.\n담당 센터에 문의바랍니다.\n문의사항: "+userInfo_wID.get("CNTER_TEL"), COMM_ERR_CODE.NO_RESULT);
						}else {
							throw new ApiException("비밀번호가 일치하지 않습니다.\n비밀번호 "+(7-limit)+"회 이상 오류 시 로그인이 제한됩니다.", COMM_ERR_CODE.NO_RESULT);
						}
					}else { //로그인제한 Y
						throw new ApiException("로그인 서비스 이용이 제한되었습니다.\n담당 센터에 문의바랍니다.\n문의사항: "+userInfo_wID.get("CNTER_TEL"), COMM_ERR_CODE.NO_RESULT);
					}
				}else{ //아이디 미존재 
					throw new ApiException("아이디가 존재하지 않습니다.",COMM_ERR_CODE.NO_RESULT); 
				}
			} else {
				if(userInfo.get("LOGIN_LIMIT_YN").toString().equals("N")){ //로그인제한 N
					if(userInfo.get("USER_STAT_CD").equals("00")) {
						resultData.put("msg", "탈퇴한 회원정보입니다. 다른 ID로 회원가입해주세요.");
					} else if(userInfo.get("USER_STAT_CD").equals("01")) {
						resultData.put("msg", "운영자 승인이 완료되어야 서비스 이용이 가능합니다.\n문의사항 : "+userInfo.get("CNTER_TEL"));
					} else {
						Map param =new HashMap();
						// 로그인제한풀기
						param.put("user_id", mapParameter.get("user_id"));
						param.put("limit", 0);
						param.put("limit_yn", 'N');
						loginService.updateLimit(param);
						// 사용자 세션정보 등록
						req.getSession().setAttribute("sessionId", httpSession.getId());
						req.getSession().setAttribute("user_id", userInfo.get("USER_ID"));
						req.getSession().setAttribute("name", userInfo.get("NAME"));
						req.getSession().setAttribute("mobile", userInfo.get("MOBILE"));
						
						// 회원상태코드 00:탈퇴, 10:예약, 20:대기, 30:탑승, 40:하차, 50:도보, 60:휠체어, 90:돌발
						req.getSession().setAttribute("user_stat_cd", userInfo.get("USER_STAT_CD"));
						
						// 회원구분코드 10:이동약자, 20:이동보조원, 30:운전자, 40:역무원, 50:상담원, 80:국가/광역이동지원센터운영자(운영자), 90:관리자(운영자), 91시스템관리자
						req.getSession().setAttribute("user_gbn_cd", userInfo.get("USER_GBN_CD"));
						req.getSession().setAttribute("cnter_cd", userInfo.get("CNTER_CD"));
						req.getSession().setAttribute("user_gbn_nm", userInfo.get("MBER_SE_NM"));
						req.getSession().setAttribute("cnter_nm", userInfo.get("CNTER_NM"));
						req.getSession().setAttribute("cnter_addr", userInfo.get("CNTER_ADDR"));
						req.getSession().setAttribute("cnter_zip", userInfo.get("CNTER_ZIP"));
						req.getSession().setAttribute("cnter_tel", userInfo.get("CNTER_TEL"));
						req.getSession().setAttribute("cnter_nm_en", userInfo.get("CNTER_NM_EN"));
						
						resultData.put("msg", "Y");
						resultData.put("user_gbn_cd", userInfo.get("USER_GBN_CD"));
						resultData.put("cnter_cd", userInfo.get("CNTER_CD"));
						resultData.put("user_gbn_nm", userInfo.get("MBER_SE_NM"));
						resultData.put("cnter_nm", userInfo.get("CNTER_NM"));
						resultData.put("cnter_addr", userInfo.get("CNTER_ADDR"));
						resultData.put("cnter_zip", userInfo.get("CNTER_ZIP"));
						resultData.put("cnter_tel", userInfo.get("CNTER_TEL"));
						resultData.put("cnter_nm_en", userInfo.get("CNTER_NM_EN"));
					}
				}else { //로그인제한 Y
					resultData.put("msg", "로그인 서비스 이용이 제한되었습니다.\n담당 센터에 문의바랍니다.\n문의사항: "+userInfo.get("CNTER_TEL"));
				}
			}
			
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